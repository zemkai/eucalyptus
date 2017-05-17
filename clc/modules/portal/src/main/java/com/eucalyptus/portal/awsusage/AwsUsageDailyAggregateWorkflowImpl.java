/*************************************************************************
 * (c) Copyright 2017 Hewlett Packard Enterprise Development Company LP
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 ************************************************************************/
package com.eucalyptus.portal.awsusage;

import com.amazonaws.services.simpleworkflow.flow.annotations.Asynchronous;
import com.amazonaws.services.simpleworkflow.flow.core.Promise;
import com.amazonaws.services.simpleworkflow.flow.core.Promises;
import com.amazonaws.services.simpleworkflow.flow.core.TryCatchFinally;
import com.eucalyptus.component.annotation.ComponentPart;
import com.eucalyptus.portal.BillingProperties;
import com.eucalyptus.portal.common.Portal;
import com.eucalyptus.portal.workflow.AwsUsageActivitiesClient;
import com.eucalyptus.portal.workflow.AwsUsageActivitiesClientImpl;
import com.eucalyptus.portal.workflow.AwsUsageDailyAggregateWorkflow;
import com.eucalyptus.portal.workflow.AwsUsageRecord;
import com.eucalyptus.portal.workflow.BillingWorkflowState;
import com.eucalyptus.simpleworkflow.common.client.Daily;
import com.google.common.collect.Lists;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

@Daily(value = AwsUsageDailyWorkflowStarter.class, hour = 0, minute = 0)
@ComponentPart(Portal.class)
public class AwsUsageDailyAggregateWorkflowImpl implements AwsUsageDailyAggregateWorkflow {
  private static Logger LOG     =
          Logger.getLogger(  AwsUsageDailyAggregateWorkflowImpl.class );
  final AwsUsageActivitiesClient client =
          new AwsUsageActivitiesClientImpl();
  private BillingWorkflowState state =
          BillingWorkflowState.WORKFLOW_RUNNING;
  TryCatchFinally task = null;

  @Override
  public void aggregateDaily() {
    task = new TryCatchFinally() {
      @Override
      protected void doTry() throws Throwable {
        doAggregate();
      }

      @Override
      protected void doCatch(Throwable e) throws Throwable {
        waitCleanup(client.cleanupQueues());
        state = BillingWorkflowState.WORKFLOW_FAILED;
        LOG.error("Workflow for aggregating AWS usage daily records has failed: ", e);
      }

      @Override
      protected void doFinally() throws Throwable {
        if (state == BillingWorkflowState.WORKFLOW_RUNNING)
          state = BillingWorkflowState.WORKFLOW_SUCCESS;
      }
    };
  }

  @Asynchronous
  void doAggregate() {
    /* HOW THIS WORKFLOW WORKS?
       inspect all events and create temporary queue for each unique account
       copy (&delete) events to the temporary account queues
       for each account queue; do
         for each record types (service/operation); do
           count # of unique resources in the event
           return list of records for the type
           write the records into persistence storage (i.e., cassandra)
       delete account queue
    */
    final Promise<Map<String, String>> queueForAccounts =
            client.createAccountQueues(BillingProperties.SENSOR_QUEUE_NAME);
    final Promise<List<Void>> processed =
            writeSerialized(
                    processAccountQueues(queueForAccounts)
            );
    waitFor(
            client.deleteAccountQueues(
                    getQueues(queueForAccounts),
                    processed)
    );
  }

  @Asynchronous
  Promise<List<List<AwsUsageRecord>>> processAccountQueues(final Promise<Map<String, String>> queueForAccounts) {
    final List<Promise<List<AwsUsageRecord>>> accountRecords = Lists.newArrayList();
    for (final String accountId : queueForAccounts.get().keySet()) {
      final String queueName = queueForAccounts.get().get(accountId);
      accountRecords.add(
              client.getAwsReportDailyUsageRecord(
                      Promise.asPromise(accountId),
                      Promise.asPromise(queueName)
              )
      );
    }
    return Promises.listOfPromisesToPromise(accountRecords);
  }

  @Asynchronous
  Promise<List<Void>> writeSerialized( final Promise<List<List<AwsUsageRecord>>> accountRecords ) {
    final List<Promise<Void>> result = Lists.newArrayList();
    Promise<Void> run = Promise.Void();
    for (final List<AwsUsageRecord> accountRecord : accountRecords.get() ) {
      run = client.writeAwsReportUsage( accountRecord, run);
      result.add(run);
    }
    return Promises.listOfPromisesToPromise(result);
  }

  @Asynchronous
  Promise<List<String>> getQueues(final Promise<Map<String, String>> queueMap) {
    return Promise.asPromise(Lists.newArrayList(queueMap.get().values()));
  }

  @Asynchronous
  void waitFor(final Promise<Void> task) {
    LOG.debug("Finished writing AWS usage daily records");
  }

  @Asynchronous
  void waitCleanup(final Promise<Void> task) { LOG.error("Failed writing AWS usage daily records"); }

  @Override
  public BillingWorkflowState getState() {
    return state;
  }
}
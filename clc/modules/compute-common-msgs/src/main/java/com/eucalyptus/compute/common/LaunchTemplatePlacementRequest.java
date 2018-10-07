/*
 * Copyright 2018 AppScale Systems, Inc
 *
 * Use of this source code is governed by a BSD-2-Clause
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/BSD-2-Clause
 */
package com.eucalyptus.compute.common;

import edu.ucsb.eucalyptus.msgs.EucalyptusData;


public class LaunchTemplatePlacementRequest extends EucalyptusData {

  private String affinity;
  private String availabilityZone;
  private String groupName;
  private String hostId;
  private String spreadDomain;
  private String tenancy;

  public String getAffinity( ) {
    return affinity;
  }

  public void setAffinity( final String affinity ) {
    this.affinity = affinity;
  }

  public String getAvailabilityZone( ) {
    return availabilityZone;
  }

  public void setAvailabilityZone( final String availabilityZone ) {
    this.availabilityZone = availabilityZone;
  }

  public String getGroupName( ) {
    return groupName;
  }

  public void setGroupName( final String groupName ) {
    this.groupName = groupName;
  }

  public String getHostId( ) {
    return hostId;
  }

  public void setHostId( final String hostId ) {
    this.hostId = hostId;
  }

  public String getSpreadDomain( ) {
    return spreadDomain;
  }

  public void setSpreadDomain( final String spreadDomain ) {
    this.spreadDomain = spreadDomain;
  }

  public String getTenancy( ) {
    return tenancy;
  }

  public void setTenancy( final String tenancy ) {
    this.tenancy = tenancy;
  }

}

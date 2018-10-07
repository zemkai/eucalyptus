/*
 * Copyright 2018 AppScale Systems, Inc
 *
 * Use of this source code is governed by a BSD-2-Clause
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/BSD-2-Clause
 */
package com.eucalyptus.compute.common;

import edu.ucsb.eucalyptus.msgs.EucalyptusData;


public class LaunchTemplateOverrides extends EucalyptusData {

  private String availabilityZone;
  private String instanceType;
  private Double priority;
  private String spotPrice;
  private String subnetId;
  private Double weightedCapacity;

  public String getAvailabilityZone( ) {
    return availabilityZone;
  }

  public void setAvailabilityZone( final String availabilityZone ) {
    this.availabilityZone = availabilityZone;
  }

  public String getInstanceType( ) {
    return instanceType;
  }

  public void setInstanceType( final String instanceType ) {
    this.instanceType = instanceType;
  }

  public Double getPriority( ) {
    return priority;
  }

  public void setPriority( final Double priority ) {
    this.priority = priority;
  }

  public String getSpotPrice( ) {
    return spotPrice;
  }

  public void setSpotPrice( final String spotPrice ) {
    this.spotPrice = spotPrice;
  }

  public String getSubnetId( ) {
    return subnetId;
  }

  public void setSubnetId( final String subnetId ) {
    this.subnetId = subnetId;
  }

  public Double getWeightedCapacity( ) {
    return weightedCapacity;
  }

  public void setWeightedCapacity( final Double weightedCapacity ) {
    this.weightedCapacity = weightedCapacity;
  }

}

/*
 * Copyright 2018 AppScale Systems, Inc
 *
 * Use of this source code is governed by a BSD-2-Clause
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/BSD-2-Clause
 */
package com.eucalyptus.compute.common;

import edu.ucsb.eucalyptus.msgs.EucalyptusData;


public class LaunchTemplateBlockDeviceMappingRequest extends EucalyptusData {

  private String deviceName;
  private LaunchTemplateEbsBlockDeviceRequest ebs;
  private String noDevice;
  private String virtualName;

  public String getDeviceName( ) {
    return deviceName;
  }

  public void setDeviceName( final String deviceName ) {
    this.deviceName = deviceName;
  }

  public LaunchTemplateEbsBlockDeviceRequest getEbs( ) {
    return ebs;
  }

  public void setEbs( final LaunchTemplateEbsBlockDeviceRequest ebs ) {
    this.ebs = ebs;
  }

  public String getNoDevice( ) {
    return noDevice;
  }

  public void setNoDevice( final String noDevice ) {
    this.noDevice = noDevice;
  }

  public String getVirtualName( ) {
    return virtualName;
  }

  public void setVirtualName( final String virtualName ) {
    this.virtualName = virtualName;
  }

}

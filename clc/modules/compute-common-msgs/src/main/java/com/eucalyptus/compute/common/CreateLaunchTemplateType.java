/*
 * Copyright 2018 AppScale Systems, Inc
 *
 * Use of this source code is governed by a BSD-2-Clause
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/BSD-2-Clause
 */
package com.eucalyptus.compute.common;

import javax.annotation.Nonnull;


public class CreateLaunchTemplateType extends ComputeMessage {

  private String clientToken;
  @Nonnull
  private RequestLaunchTemplateData launchTemplateData;
  @Nonnull
  private String launchTemplateName;
  private String versionDescription;

  public String getClientToken( ) {
    return clientToken;
  }

  public void setClientToken( final String clientToken ) {
    this.clientToken = clientToken;
  }

  public RequestLaunchTemplateData getLaunchTemplateData( ) {
    return launchTemplateData;
  }

  public void setLaunchTemplateData( final RequestLaunchTemplateData launchTemplateData ) {
    this.launchTemplateData = launchTemplateData;
  }

  public String getLaunchTemplateName( ) {
    return launchTemplateName;
  }

  public void setLaunchTemplateName( final String launchTemplateName ) {
    this.launchTemplateName = launchTemplateName;
  }

  public String getVersionDescription( ) {
    return versionDescription;
  }

  public void setVersionDescription( final String versionDescription ) {
    this.versionDescription = versionDescription;
  }

}

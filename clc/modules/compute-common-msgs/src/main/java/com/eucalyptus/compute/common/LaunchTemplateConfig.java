/*
 * Copyright 2018 AppScale Systems, Inc
 *
 * Use of this source code is governed by a BSD-2-Clause
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/BSD-2-Clause
 */
package com.eucalyptus.compute.common;

import edu.ucsb.eucalyptus.msgs.EucalyptusData;


public class LaunchTemplateConfig extends EucalyptusData {

  private FleetLaunchTemplateSpecification launchTemplateSpecification;
  private LaunchTemplateOverridesList overrides;

  public FleetLaunchTemplateSpecification getLaunchTemplateSpecification( ) {
    return launchTemplateSpecification;
  }

  public void setLaunchTemplateSpecification( final FleetLaunchTemplateSpecification launchTemplateSpecification ) {
    this.launchTemplateSpecification = launchTemplateSpecification;
  }

  public LaunchTemplateOverridesList getOverrides( ) {
    return overrides;
  }

  public void setOverrides( final LaunchTemplateOverridesList overrides ) {
    this.overrides = overrides;
  }

}

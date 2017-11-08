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
package com.eucalyptus.cloudformation.resources.standard.propertytypes;

import com.eucalyptus.cloudformation.resources.ResourceProperties;
import com.eucalyptus.cloudformation.resources.annotations.Property;
import com.eucalyptus.cloudformation.resources.annotations.Required;
import com.google.common.base.MoreObjects;

public class AWSEC2VPCGatewayAttachmentProperties implements ResourceProperties {

  @Required
  @Property
  private String internetGatewayId;

  @Property
  private String vpcId;

  @Property
  private String VpnGatewayId;

  public String getInternetGatewayId( ) {
    return internetGatewayId;
  }

  public void setInternetGatewayId( String internetGatewayId ) {
    this.internetGatewayId = internetGatewayId;
  }

  public String getVpcId( ) {
    return vpcId;
  }

  public void setVpcId( String vpcId ) {
    this.vpcId = vpcId;
  }

  public String getVpnGatewayId( ) {
    return VpnGatewayId;
  }

  public void setVpnGatewayId( String VpnGatewayId ) {
    this.VpnGatewayId = VpnGatewayId;
  }

  @Override
  public String toString( ) {
    return MoreObjects.toStringHelper( this )
        .add( "internetGatewayId", internetGatewayId )
        .add( "vpcId", vpcId )
        .add( "VpnGatewayId", VpnGatewayId )
        .toString( );
  }
}

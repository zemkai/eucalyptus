/*
 * Copyright 2021 AppScale Systems, Inc
 *
 * SPDX-License-Identifier: BSD-2-Clause
 */
package com.eucalyptus.loadbalancingv2.common.msgs;

import javax.annotation.Nonnull;
import edu.ucsb.eucalyptus.msgs.EucalyptusData;


public class Matcher extends EucalyptusData {

  @Nonnull
  private String grpcCode;

  @Nonnull
  private String httpCode;

  @Nonnull public String getGrpcCode() {
    return grpcCode;
  }

  public void setGrpcCode(@Nonnull String grpcCode) {
    this.grpcCode = grpcCode;
  }

  public String getHttpCode() {
    return httpCode;
  }

  public void setHttpCode(final String httpCode) {
    this.httpCode = httpCode;
  }

}
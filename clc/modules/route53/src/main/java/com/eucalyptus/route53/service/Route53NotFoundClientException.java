/*
 * Copyright 2020 AppScale Systems, Inc
 *
 * Use of this source code is governed by a BSD-2-Clause
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/BSD-2-Clause
 */
package com.eucalyptus.route53.service;

import com.eucalyptus.ws.protocol.QueryBindingInfo;

/**
 *
 */
@QueryBindingInfo( statusCode = 404 )
public class Route53NotFoundClientException extends Route53ClientException {

  private static final long serialVersionUID = 1L;

  public Route53NotFoundClientException(final String code, final String message) {
    super(code, message);
  }
}

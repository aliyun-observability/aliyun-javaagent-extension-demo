/*
 * Copyright The OpenTelemetry Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package com.aliyun.arms.opentelemetry.example.javaagent.motan;

import io.opentelemetry.context.propagation.TextMapGetter;

enum MotanHeaderGetter implements TextMapGetter<MotanRequest> {
  INSTANCE;

  @Override
  public Iterable<String> keys(MotanRequest request) {
    return request.getRequest().getAttachments().keySet();
  }

  @Override
  public String get(MotanRequest request, String key) {
    return request.getRequest().getAttachments().get(key);
  }
}

/*
 * Copyright The OpenTelemetry Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package com.aliyun.arms.opentelemetry.example.javaagent.motan;

import io.opentelemetry.context.propagation.TextMapSetter;

enum MotanHeaderSetter implements TextMapSetter<MotanRequest> {
  INSTANCE;

  @Override
  public void set(MotanRequest request, String key, String value) {
    request.getRequest().setAttachment(key, value);
  }
}

package com.aliyun.arms.opentelemetry.example.javaagent.motan;

import com.weibo.api.motan.rpc.Response;

public class MotanResponse {
  private final Response response;

  public MotanResponse(Response response) {
    this.response = response;
  }

  public Response getResponse() {
    return response;
  }
}

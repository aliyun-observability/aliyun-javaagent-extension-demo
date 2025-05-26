package com.aliyun.arms.opentelemetry.example.javaagent.motan;

import com.weibo.api.motan.rpc.Request;
import com.weibo.api.motan.rpc.URL;

public class MotanRequest {
  private final Request request;
  private final URL remoteUrl;
  private final boolean isClient;

  public MotanRequest(Request request, URL remoteUrl, boolean isClient) {
    this.request = request;
    this.remoteUrl = remoteUrl;
    this.isClient = isClient;
  }

  public Request getRequest() {
    return request;
  }

  public URL getRemoteUrl() {
    return remoteUrl;
  }

  public boolean isClient() {
    return isClient;
  }
}

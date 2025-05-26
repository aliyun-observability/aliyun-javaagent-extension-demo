package com.aliyun.arms.opentelemetry.example.javaagent.motan;

import io.opentelemetry.api.common.AttributesBuilder;
import io.opentelemetry.context.Context;
import io.opentelemetry.instrumentation.api.instrumenter.AttributesExtractor;

import javax.annotation.Nullable;

public enum MotanAttributesExtractor implements AttributesExtractor<MotanRequest, MotanResponse> {
    INSTANCE;

    @Override
    public void onStart(AttributesBuilder attributes, Context parentContext, MotanRequest motanRequest) {
        attributes.put("motan.reqeust.parameters", motanRequest.getRequest().getParamtersDesc());
        attributes.put("motan.reqeust.url", motanRequest.getRemoteUrl().toFullStr());
        attributes.put("motan.reqeust.requestId", motanRequest.getRequest().getRequestId());
        attributes.put("motan.reqeust.retries", motanRequest.getRequest().getRetries());
    }

    @Override
    public void onEnd(AttributesBuilder attributes, Context context, MotanRequest motanRequest, @Nullable MotanResponse motanResponse, @Nullable Throwable error) {
        attributes.put("motan.response.processTime", motanResponse.getResponse().getProcessTime());
    }
}

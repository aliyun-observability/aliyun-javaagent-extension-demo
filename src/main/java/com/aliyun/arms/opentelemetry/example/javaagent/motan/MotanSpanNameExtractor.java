package com.aliyun.arms.opentelemetry.example.javaagent.motan;

import io.opentelemetry.instrumentation.api.instrumenter.SpanNameExtractor;

public class MotanSpanNameExtractor implements SpanNameExtractor<MotanRequest> {
    @Override
    public String extract(MotanRequest motanRequest) {
        int lastIdx = motanRequest.getRequest().getInterfaceName().lastIndexOf(".");
        String className = lastIdx >= 0 ? motanRequest.getRequest().getInterfaceName().substring(lastIdx + 1) : motanRequest.getRequest().getInterfaceName();
        return className + "@" + motanRequest.getRequest().getMethodName();
    }
}
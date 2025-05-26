package com.aliyun.arms.opentelemetry.example.javaagent.motan;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.instrumentation.api.instrumenter.Instrumenter;


public class MotanSingleton {

  private static final String INSTRUMENTATION_NAME = "io.opentelemetry.motan" ;

  private static final Instrumenter<MotanRequest, MotanResponse> SERVER_INSTRUMENTER;

  private static final Instrumenter<MotanRequest, MotanResponse> CLIENT_INSTRUMENTER;

  static {

    SERVER_INSTRUMENTER =
        Instrumenter.<MotanRequest, MotanResponse>builder(
                GlobalOpenTelemetry.get(),
                INSTRUMENTATION_NAME,
                new MotanSpanNameExtractor())
            .addAttributesExtractor(MotanAttributesExtractor.INSTANCE)
            .buildServerInstrumenter(MotanHeaderGetter.INSTANCE);

    CLIENT_INSTRUMENTER =
        Instrumenter.<MotanRequest, MotanResponse>builder(
                GlobalOpenTelemetry.get(),
                INSTRUMENTATION_NAME,
                new MotanSpanNameExtractor())
            .addAttributesExtractor(MotanAttributesExtractor.INSTANCE)
            .buildClientInstrumenter(MotanHeaderSetter.INSTANCE);
  }

  public static Instrumenter<MotanRequest, MotanResponse> serverInstrumenter() {
    return SERVER_INSTRUMENTER;
  }

  public static Instrumenter<MotanRequest, MotanResponse> clientInstrumenter() {
    return CLIENT_INSTRUMENTER;
  }
}

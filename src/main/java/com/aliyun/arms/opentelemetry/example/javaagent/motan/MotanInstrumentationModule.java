package com.aliyun.arms.opentelemetry.example.javaagent.motan;

import com.google.auto.service.AutoService;
import io.opentelemetry.javaagent.extension.instrumentation.InstrumentationModule;
import io.opentelemetry.javaagent.extension.instrumentation.TypeInstrumentation;

import java.util.Arrays;
import java.util.List;

@AutoService(InstrumentationModule.class)
public class MotanInstrumentationModule extends InstrumentationModule {

    public MotanInstrumentationModule() {
        super("my-motan");
    }

    @Override
    public List<TypeInstrumentation> typeInstrumentations() {
        return Arrays.asList(new MotanClientInstrumentation(), new MotanServerInstrumentation());
    }

    @Override
    public boolean isHelperClass(String className) {
        return className.startsWith("com.aliyun.arms.opentelemetry.example.javaagent");
    }
}

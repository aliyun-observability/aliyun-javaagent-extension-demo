package com.aliyun.arms.opentelemetry.example.javaagent.motan;

import com.weibo.api.motan.rpc.Request;
import com.weibo.api.motan.rpc.Response;
import com.weibo.api.motan.rpc.URL;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;
import io.opentelemetry.javaagent.extension.instrumentation.TypeInstrumentation;
import io.opentelemetry.javaagent.extension.instrumentation.TypeTransformer;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;

import static io.opentelemetry.javaagent.bootstrap.Java8BytecodeBridge.currentContext;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.takesArgument;

public class MotanClientInstrumentation implements TypeInstrumentation {
    @Override
    public ElementMatcher<TypeDescription> typeMatcher() {
        return named("com.weibo.api.motan.rpc.AbstractReferer");
    }

    @Override
    public void transform(TypeTransformer transformer) {
        transformer.applyAdviceToMethod(
                named("call")
                        .and(takesArgument(0, named("com.weibo.api.motan.rpc.Request"))),
                MotanClientInstrumentation.class.getName() + "$CallAdvice");
    }

    public static class CallAdvice {

        @Advice.OnMethodEnter(suppress = Throwable.class)
        public static void startSpan(
                @Advice.Argument(0) Request motanRequest,
                @Advice.FieldValue("serviceUrl") URL serviceUrl,
                @Advice.Local("otelRequest") MotanRequest request,
                @Advice.Local("otelContext") Context context,
                @Advice.Local("otelScope") Scope scope) {
            request = new MotanRequest(motanRequest, serviceUrl, true);
            Context parentContext = currentContext();
            if (!MotanSingleton.clientInstrumenter().shouldStart(parentContext, request)) {
                return;
            }
            context = MotanSingleton.clientInstrumenter().start(parentContext, request);
            scope = context.makeCurrent();
        }

        @Advice.OnMethodExit(onThrowable = Throwable.class, suppress = Throwable.class)
        public static void stopSpan(
                @Advice.Thrown Throwable throwable,
                @Advice.Return Response response,
                @Advice.Local("otelRequest") MotanRequest request,
                @Advice.Local("otelContext") Context context,
                @Advice.Local("otelScope") Scope scope) {
            if (scope != null) {
                scope.close();
                MotanResponse motanResponse = new MotanResponse(response);
                MotanSingleton.clientInstrumenter().end(context, request, motanResponse,
                        throwable != null ? throwable : response.getException());

            }
        }
    }
}

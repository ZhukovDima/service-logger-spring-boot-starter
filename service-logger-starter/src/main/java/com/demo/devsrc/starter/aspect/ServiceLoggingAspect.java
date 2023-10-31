package com.demo.devsrc.starter.aspect;

import com.demo.devsrc.starter.autoconfigure.LoggingProperties;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.logging.Logger;

/**
 * Аспект, перехватывающий выполнение методов класса с аннотацией {@link org.springframework.stereotype.Service}
 * и логирующий вызов метода
 */
@RequiredArgsConstructor
@Aspect
public class ServiceLoggingAspect {

    private final LoggingProperties loggingProperties;

    @Pointcut("within(@org.springframework.stereotype.Service *)")
    public void anyServiceMethod() {}

    /**
     * Advice вокруг выполнения методов класса с аннотацией {@link org.springframework.stereotype.Service}
     */
    @Around("anyServiceMethod()")
    public Object beforeAnyFileServiceMethod(ProceedingJoinPoint pjp) throws Throwable {
        if (pjp.getSignature() instanceof MethodSignature ms) {
            Logger log = Logger.getLogger(pjp.getSignature().getDeclaringType().getName());
            StringBuilder builder = new StringBuilder();
            Method method = ms.getMethod();
            builder.append(method.getName());

            Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            String[] parameterNames = ms.getParameterNames();
            builder.append(" args: [");
            for(int i = 0; i < parameterAnnotations.length; i++){
                Object[] args = pjp.getArgs();
                String parameterName = parameterNames[i];
                if (!loggingProperties.getArgs().contains(parameterName)) {
                    builder.append("\"").append(args[i]).append("\"");
                }
            }
            builder.append("]");
            log.info(builder.toString());
        }

        return pjp.proceed(pjp.getArgs());
    }
}

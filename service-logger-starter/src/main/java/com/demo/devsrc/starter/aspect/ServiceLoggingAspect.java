package com.demo.devsrc.starter.aspect;

import com.demo.devsrc.starter.annotation.IgnoreLogging;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.StringJoiner;
import java.util.logging.Logger;

/**
 * Аспект, перехватывающий выполнение методов класса с аннотацией {@link org.springframework.stereotype.Service}
 * и логирующий вызов метода
 */

@Aspect
public class ServiceLoggingAspect {

    @Pointcut("within(@org.springframework.stereotype.Service *)")
    public void anyServiceMethod() {}

    /**
     * Advice вокруг выполнения методов класса с аннотацией {@link org.springframework.stereotype.Service}
     */
    @Around("anyServiceMethod()")
    public Object beforeAnyFileServiceMethod(ProceedingJoinPoint pjp) throws Throwable {
        if (pjp.getSignature() instanceof MethodSignature ms) {
            Logger log = Logger.getLogger(pjp.getSignature().getDeclaringTypeName());
            StringBuilder builder = new StringBuilder();
            Method method = ms.getMethod();
            builder.append(method.getName());

            Parameter[] parameters = method.getParameters();
            builder.append(" args: [");
            StringJoiner joiner = new StringJoiner(", ");
            for(int i = 0; i < parameters.length; i++) {
                Parameter parameter = parameters[i];
                if (!parameter.isAnnotationPresent(IgnoreLogging.class)) {
                    Object[] args = pjp.getArgs();
                    joiner.add("\"" + args[i] + "\"");
                }
            }
            builder.append(joiner);
            builder.append("]");
            log.info(builder.toString());
        }

        return pjp.proceed(pjp.getArgs());
    }
}

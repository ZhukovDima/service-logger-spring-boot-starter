package com.demo.devsrc.starter.aspect;

import com.demo.devsrc.starter.annotation.IgnoreLogging;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.logging.Logger;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@ExtendWith(MockitoExtension.class)
public class ServiceLoggingAspectTest {

    @Mock
    private ProceedingJoinPoint joinPoint;

    @Mock
    private MethodSignature signature;

    @Mock
    private Method method;

    @Mock
    private Parameter parameter;

    @Mock
    private Logger log;

    @InjectMocks
    private ServiceLoggingAspect serviceLoggingAspect;

    @Test
    public void beforeAnyFileServiceMethodTest() throws Throwable {
        Mockito.when(joinPoint.getSignature()).thenReturn(signature);
        Mockito.when(signature.getDeclaringTypeName()).thenReturn("com.demo.service.TestService");
        Mockito.when(signature.getMethod()).thenReturn(method);
        Mockito.when(method.getName()).thenReturn("saveToDb");
        Mockito.when(method.getParameters()).thenReturn(new Parameter[]{parameter});
        Mockito.when(joinPoint.getArgs()).thenReturn(new Object[]{"file.txt"});

        MockedStatic<Logger> logger = Mockito.mockStatic(Logger.class);
        logger.when(() -> Logger.getLogger("com.demo.service.TestService")).thenReturn(log);

        serviceLoggingAspect.beforeAnyFileServiceMethod(joinPoint);
        logger.close();

        Mockito.verify(log, Mockito.times(1)).info("saveToDb args: [\"file.txt\"]");

    }

    @Test
    public void beforeAnyFileServiceMethodWithExcludeAnnotationTest() throws Throwable {
        Mockito.when(joinPoint.getSignature()).thenReturn(signature);
        Mockito.when(signature.getDeclaringTypeName()).thenReturn("com.demo.service.TestService");
        Mockito.when(signature.getMethod()).thenReturn(method);
        Mockito.when(method.getName()).thenReturn("saveToDb");
        Mockito.when(method.getParameters()).thenReturn(new Parameter[]{parameter});
        Mockito.when(parameter.isAnnotationPresent(IgnoreLogging.class)).thenReturn(true);
        Mockito.when(joinPoint.getArgs()).thenReturn(new Object[]{"file.txt"});

        MockedStatic<Logger> logger = Mockito.mockStatic(Logger.class);
        logger.when(() -> Logger.getLogger("com.demo.service.TestService")).thenReturn(log);

        serviceLoggingAspect.beforeAnyFileServiceMethod(joinPoint);
        logger.close();

        Mockito.verify(log, Mockito.times(1)).info("saveToDb args: []");
    }
}

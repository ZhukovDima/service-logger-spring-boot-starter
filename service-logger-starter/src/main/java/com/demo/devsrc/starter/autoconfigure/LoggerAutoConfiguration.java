package com.demo.devsrc.starter.autoconfigure;

import com.demo.devsrc.starter.aspect.ServiceLoggingAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@Configuration
@EnableConfigurationProperties(LoggingProperties.class)
@ConditionalOnProperty(value = "libname.enabled", havingValue = "true")
public class LoggerAutoConfiguration {

    @Bean
    public ServiceLoggingAspect serviceLoggingAspect(LoggingProperties loggingProperties) {
        return new ServiceLoggingAspect(loggingProperties);
    }
}

package com.demo.devsrc.starter.autoconfigure;

import com.demo.devsrc.starter.aspect.ServiceLoggingAspect;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;


public class LoggerAutoConfigurationIT {

    @Test
    public void loggerAutoConfigurationAppliedTest() {
        new ApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(LoggerAutoConfiguration.class))
                .withPropertyValues(
                        "libname.enabled=true"
                )
                .run(ctx -> {
                    assertThat(ctx)
                            .hasNotFailed()
                            .hasSingleBean(ServiceLoggingAspect.class);
                });
    }

    @Test
    public void loggerAutoConfigurationNotAppliedTest() {
        new ApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(LoggerAutoConfiguration.class))
                .withPropertyValues(
                        "libname.enabled=false"
                )
                .run(ctx -> {
                    assertThat(ctx)
                            .hasNotFailed()
                            .doesNotHaveBean(ServiceLoggingAspect.class);
                });
    }
}

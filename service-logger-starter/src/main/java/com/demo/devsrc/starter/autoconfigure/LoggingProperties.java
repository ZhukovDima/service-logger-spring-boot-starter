package com.demo.devsrc.starter.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashSet;
import java.util.Set;

@Data
@ConfigurationProperties("libname.exclude")
public class LoggingProperties {

    /**
     * Список названий аргументов методов, которые следует исключить из лога
     */
    private Set<String> args = new HashSet<>();
}

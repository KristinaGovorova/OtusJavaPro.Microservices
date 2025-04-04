package ru.otus.java.pro.mt.core.transfers.configs.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.time.Duration;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "integration.rest-client")
public class RestClientProperties {
    private String url;
    private Duration readTimeout;
    private Duration writeTimeout;
}
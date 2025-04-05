package ru.otus.java.pro.mt.core.transfers.configs;

import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi transfersKafkaApiV1() {
        final String[] packagesToScan = {"ru.otus.java.pro.mt.core.transfers.controllers"};
        return GroupedOpenApi
                .builder()
                .group("2. mt-core-kafka-v1")
                .packagesToScan(packagesToScan)
                .pathsToMatch("/api/v1/kafka/**")
                .addOpenApiCustomizer(
                        openApi -> openApi.info(
                                new Info()
                                        .title("Микросервис перевода средств между клиентами")
                                        .description("OTUS - МС Переводов - Переводы монет")
                                        .version("1.0.0")
                        )
                )
                .build();
    }
}

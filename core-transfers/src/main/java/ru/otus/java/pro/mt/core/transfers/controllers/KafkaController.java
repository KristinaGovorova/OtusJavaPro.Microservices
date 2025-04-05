package ru.otus.java.pro.mt.core.transfers.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/kafka")
@Tag(name = "Kafka", description = "Методы взаимодействия с Kafka")
public class KafkaController {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public KafkaController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/send")
    @Operation(
            summary = "Отправка сообщения в Kafka",
            description = "Позволяет отправить произвольное сообщение в указанный Kafka-топик",
            parameters = {
                    @Parameter(
                            name = "topic",
                            description = "Название топика Kafka",
                            required = true,
                            example = "notifications"
                    )
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Текст сообщения",
                    content = @Content(
                            mediaType = "text/plain",
                            schema = @Schema(type = "string", example = "Пример Kafka-сообщения")
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Сообщение успешно отправлено"
                    )
            }
    )
    public void sendMessage(
            @RequestParam String topic,
            @RequestBody String message
    ) {
        kafkaTemplate.send(topic, message);
    }
}

package ru.otus.java.pro.mt.core.transfers.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/kafka")
public class KafkaController {
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public KafkaController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/send")
    @Operation(
            summary = "Отправка сообщения в Kafka",
            responses = {
                    @ApiResponse(
                            description = "Сообщение успешно отправлено", responseCode = "200",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    public void sendMessage(
            @Parameter(description = "Название топика Kafka", required = true)
            @RequestParam String topic,

            @Parameter(description = "Сообщение для отправки", required = true)
            @RequestBody String message
    ) {
        kafkaTemplate.send(topic, message);
    }
}

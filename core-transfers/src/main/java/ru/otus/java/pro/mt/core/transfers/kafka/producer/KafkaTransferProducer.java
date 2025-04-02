package ru.otus.java.pro.mt.core.transfers.kafka.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaTransferProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "mt.transfers.status.info";

    public void sendTransferExecutedMessage(String transferId) {
        String message = "{\"transferId\":\"" + transferId + "\", \"status\":\"EXECUTED\"}";
        kafkaTemplate.send(TOPIC, message);
    }
}

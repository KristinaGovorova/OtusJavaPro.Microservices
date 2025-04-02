package ru.otus.java.pro.mt.core.transfers.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class KafkaTransferConsumer {
    @KafkaListener(topics = "mt.transfers.status.info", groupId = "notifications-group")
    public void listen(ConsumerRecord<String, String> record) {
        String transferId = record.value();
        log.info("По переводу {} клиенту отправлена нотификация", transferId);
    }
}

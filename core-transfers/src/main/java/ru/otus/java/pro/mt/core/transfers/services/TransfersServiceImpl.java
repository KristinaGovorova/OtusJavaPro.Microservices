package ru.otus.java.pro.mt.core.transfers.services;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.otus.java.pro.mt.core.transfers.configs.properties.TransfersProperties;
import ru.otus.java.pro.mt.core.transfers.dtos.ExecuteTransferDtoRq;
import ru.otus.java.pro.mt.core.transfers.entities.Transfer;
import ru.otus.java.pro.mt.core.transfers.exceptions_handling.BusinessLogicException;
import ru.otus.java.pro.mt.core.transfers.kafka.producer.KafkaTransferProducer;
import ru.otus.java.pro.mt.core.transfers.repositories.TransfersRepository;
import ru.otus.java.pro.mt.core.transfers.validators.TransferRequestValidator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransfersServiceImpl implements TransfersService {
    private final TransfersRepository transfersRepository;
    private final TransferRequestValidator transferRequestValidator;
    private final TransfersProperties transfersProperties;
    private final MeterRegistry meterRegistry;

    private final KafkaTransferProducer kafkaTransferProducer;

    @Override
    public Optional<Transfer> getTransferById(String id, String clientId) {
        return transfersRepository.findByIdAndClientId(id, clientId);
    }

    @Override
    public List<Transfer> getAllTransfers(String clientId) {
        return getAllTransfers(clientId, PageRequest.of(0, 20)).getContent();
    }

    @Override
    public Page<Transfer> getAllTransfers(String clientId, Pageable pageable) {

        int size = Math.min(pageable.getPageSize(), 1000);
        Pageable limitedPageable = PageRequest.of(pageable.getPageNumber(), size);

        return transfersRepository.findAllByClientId(clientId, limitedPageable);
    }

    @Override
    public void execute(String clientId, ExecuteTransferDtoRq executeTransferDtoRq) {
        meterRegistry.counter("transfer_requests_total").increment();
        transferRequestValidator.validate(executeTransferDtoRq);

        if (executeTransferDtoRq.getAmount().compareTo(transfersProperties.getMaxTransferSum()) > 0) {
            meterRegistry.counter("transfer_failure_total").increment();
            throw new BusinessLogicException("OOPS", "OOPS_CODE");
        }

        Transfer transfer = new Transfer(UUID.randomUUID().toString(), "1", "2", "1", "2", "Demo", BigDecimal.ONE);
        save(transfer);

        meterRegistry.counter("transfer_success_total").increment();

        kafkaTransferProducer.sendTransferExecutedMessage(transfer.getId());
    }

    @Override
    public void save(Transfer transfer) {
        transfersRepository.save(transfer);
    }
}

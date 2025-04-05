package ru.otus.java.pro.mt.core.transfers.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Оставшийся лимит перевода клиента")
public class RemainingLimitDto {
    @Schema(description = "Сумма оставшегося лимита", example = "500.00")
    private BigDecimal remainingLimit;
}

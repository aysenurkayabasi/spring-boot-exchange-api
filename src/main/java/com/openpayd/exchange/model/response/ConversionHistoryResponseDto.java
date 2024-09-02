package com.openpayd.exchange.model.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class ConversionHistoryResponseDto {
    private UUID id;
    private String sourceCurrency;
    private String targetCurrency;
    private Double amount;
    private Double convertedAmount;
    private LocalDateTime transactionDate;
}

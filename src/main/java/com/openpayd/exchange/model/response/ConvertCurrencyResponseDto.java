package com.openpayd.exchange.model.response;

import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class ConvertCurrencyResponseDto {
    private Double convertedAmount;
    private LocalDateTime convertedDate;
    private UUID transactionId;
}

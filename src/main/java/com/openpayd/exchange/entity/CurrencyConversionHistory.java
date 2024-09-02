package com.openpayd.exchange.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CurrencyConversionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "source_currency", nullable = false)
    private String sourceCurrency;
    @Column(name = "target_currency", nullable = false)
    private String targetCurrency;
    @Column(name = "amount", nullable = false)
    private Double amount;
    @Column(name = "converted_amount", nullable = false)
    private Double convertedAmount;
    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

}

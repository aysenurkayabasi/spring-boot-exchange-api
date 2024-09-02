package com.openpayd.exchange.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ConvertCurrencyRequestDto {
    @Schema(description = "Source currency code", example = "USD")
    private String sourceCurrency;
    @Schema(description = "Target currency code", example = "TRY")
    private String targetCurrency;
    @Schema(description = "Source currency amount", example = "10")
    @Min(value = 1, message = "Amount must be greater than or equal to 1")
    private Double amount;
}

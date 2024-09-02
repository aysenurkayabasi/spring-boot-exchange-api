package com.openpayd.exchange.model.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ExchangeRateResponseDto {
    private Double quote;
}

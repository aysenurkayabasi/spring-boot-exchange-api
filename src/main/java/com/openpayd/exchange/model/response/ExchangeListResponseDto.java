package com.openpayd.exchange.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Builder
@Data
public class ExchangeListResponseDto {
    private Map<String, String> currencies;
}

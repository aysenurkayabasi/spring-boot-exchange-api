package com.openpayd.exchange.client.currencyLayer.response;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Builder
@Data
public class ExchangeRateResponse {
    private Boolean success;
    private String source;
    private Map<String, Double> quotes;
}

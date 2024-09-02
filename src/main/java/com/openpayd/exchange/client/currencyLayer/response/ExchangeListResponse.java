package com.openpayd.exchange.client.currencyLayer.response;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Builder
@Data
public class ExchangeListResponse {
    private Boolean success;
    private Map<String, String> currencies;
}

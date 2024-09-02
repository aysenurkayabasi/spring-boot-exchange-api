package com.openpayd.exchange.adapter;

import com.openpayd.exchange.model.response.ConvertCurrencyResponseDto;
import com.openpayd.exchange.model.response.ExchangeListResponseDto;

public interface ExchangeRateStrategy {

    ExchangeListResponseDto getExchangeList();

    Double getExchangeRate(String fromCurrencyCode, String toCurrencyCode);

    ConvertCurrencyResponseDto convert(String fromCurrencyCode, String toCurrencyCode, Double amount);
}

package com.openpayd.exchange.service;

import com.openpayd.exchange.adapter.ExchangeRateManager;
import com.openpayd.exchange.adapter.ExchangeRateStrategy;
import com.openpayd.exchange.config.CacheConfig;
import com.openpayd.exchange.enums.ClientType;
import com.openpayd.exchange.model.response.ExchangeListResponseDto;
import com.openpayd.exchange.model.response.ExchangeRateResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ExchangeRateService {

    private final ExchangeRateStrategy exchangeRateStrategy;

    public ExchangeRateService(final ExchangeRateManager exchangeRateFactory) {
        this.exchangeRateStrategy = exchangeRateFactory.getClientStrategy(ClientType.CURRENCY_LAYER);
    }

    @Cacheable(value = CacheConfig.EXCHANGE_RATE_CACHE)
    public ExchangeRateResponseDto getExchangeRate(String sourceCurrency, String targetCurrency) {
        Double exchangeRate = exchangeRateStrategy.getExchangeRate(sourceCurrency, targetCurrency);
        return ExchangeRateResponseDto.builder()
                .quote(exchangeRate)
                .build();
    }

    @Cacheable(value = CacheConfig.EXCHANGE_LIST_CACHE)
    public ExchangeListResponseDto getExchangeList() {
        ExchangeListResponseDto exchangeRateResponseDto = exchangeRateStrategy.getExchangeList();
        return ExchangeListResponseDto.builder()
                .currencies(exchangeRateResponseDto.getCurrencies())
                .build();
    }

}

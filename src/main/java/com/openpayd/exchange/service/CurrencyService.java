package com.openpayd.exchange.service;

import com.openpayd.exchange.adapter.ExchangeRateManager;
import com.openpayd.exchange.adapter.ExchangeRateStrategy;
import com.openpayd.exchange.entity.CurrencyConversionHistory;
import com.openpayd.exchange.enums.ClientType;
import com.openpayd.exchange.model.response.ConvertCurrencyResponseDto;
import com.openpayd.exchange.repository.CurrencyConversionHistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CurrencyService {

    private final CurrencyConversionHistoryRepository currencyConversionHistoryRepository;

    private final ExchangeRateStrategy exchangeRateStrategy;

    public CurrencyService(CurrencyConversionHistoryRepository currencyConversionHistoryRepository, final ExchangeRateManager exchangeRateFactory) {
        this.currencyConversionHistoryRepository = currencyConversionHistoryRepository;
        this.exchangeRateStrategy = exchangeRateFactory.getClientStrategy(ClientType.CURRENCY_LAYER);
    }

    public ConvertCurrencyResponseDto convert(String sourceCurrencyCode, String targetCurrencyCode, Double amount) {
        var response = exchangeRateStrategy.convert(sourceCurrencyCode, targetCurrencyCode, amount);

        CurrencyConversionHistory currencyConversation = CurrencyConversionHistory.builder()
                .sourceCurrency(sourceCurrencyCode)
                .targetCurrency(targetCurrencyCode)
                .amount(amount)
                .convertedAmount(response.getConvertedAmount())
                .transactionDate(response.getConvertedDate())
                .build();

        currencyConversation = currencyConversionHistoryRepository.save(currencyConversation);

        log.info("Conversation history saved successfully");
        response.setTransactionId(currencyConversation.getId());
        return response;
    }

}

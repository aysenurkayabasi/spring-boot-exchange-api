package com.openpayd.exchange.adapter;


import com.openpayd.exchange.client.currencyLayer.CurrencyLayerClient;
import com.openpayd.exchange.client.currencyLayer.response.ConvertCurrencyResponse;
import com.openpayd.exchange.client.currencyLayer.response.ExchangeListResponse;
import com.openpayd.exchange.client.currencyLayer.response.ExchangeRateResponse;
import com.openpayd.exchange.exception.ErrorCode;
import com.openpayd.exchange.exception.RemoteApiCallException;
import com.openpayd.exchange.model.response.ConvertCurrencyResponseDto;
import com.openpayd.exchange.model.response.ExchangeListResponseDto;
import com.openpayd.exchange.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class CurrencyLayerApiStrategy implements ExchangeRateStrategy {

    private final CurrencyLayerClient client;

    @Value("${currency-client.api-key}")
    private String apiKey;

    @Override
    public ExchangeListResponseDto getExchangeList() {
        ExchangeListResponse exchangeRateResponse = client.getExchangeList(apiKey);
        return ExchangeListResponseDto.builder()
                .currencies(exchangeRateResponse.getCurrencies())
                .build();
    }

    @Override
    public Double getExchangeRate(String fromCurrencyCode, String toCurrencyCode) {
        ExchangeRateResponse response = null;
        try {
            response = client.getExchangeRates(apiKey, fromCurrencyCode, toCurrencyCode);
        } catch (Exception e) {
            log.error("Error occurred while calling. Error:{}", e.getMessage());
            throw new RemoteApiCallException(ErrorCode.REMOTE_ERROR_RESPONSE);
        }

        if (Objects.nonNull(response) && !response.getSuccess()) {
            log.error("Api returned unsuccessful response");
            throw new RemoteApiCallException(ErrorCode.UNSUCCESSFUL_RESPONSE);
        }

        return response.getQuotes().get(fromCurrencyCode + toCurrencyCode);
    }

    @Override
    public ConvertCurrencyResponseDto convert(String fromCurrencyCode, String toCurrencyCode, Double amount) {
        ConvertCurrencyResponse response = null;
        try {
            response = client.convert(apiKey, fromCurrencyCode, toCurrencyCode, amount);
        } catch (Exception e) {
            log.error("Error occurred while calling. Error:{}", e.getMessage());
            throw new RemoteApiCallException(ErrorCode.REMOTE_ERROR_RESPONSE);
        }

        if (Objects.nonNull(response) && !response.getSuccess()) {
            log.error("Api returned unsuccessful response");
            throw new RemoteApiCallException(ErrorCode.UNSUCCESSFUL_RESPONSE);
        }

        if (Objects.isNull(response) || Objects.isNull(response.getResult())) {
            log.error("Api returned empty response");
            throw new RemoteApiCallException(ErrorCode.EMPTY_RESPONSE);
        }

        var convertedDate = DateTimeUtil.convertTimestampToLocalDateTime(response.getInfo().getTimestamp());

        return ConvertCurrencyResponseDto.builder()
                .convertedAmount(response.getResult())
                .convertedDate(convertedDate)
                .build();
    }


}
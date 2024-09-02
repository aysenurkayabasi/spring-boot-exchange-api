package com.openpayd.exchange.adapter;

import com.openpayd.exchange.enums.ClientType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExchangeRateManager {

    private final CurrencyLayerApiStrategy currencyLayerApiStrategy;

    public ExchangeRateStrategy getClientStrategy(ClientType clientType) {
        switch (clientType) {
            case CURRENCY_LAYER:
                return currencyLayerApiStrategy;
            default:
                throw new IllegalArgumentException("Unsupported client type: " + clientType);
        }
    }
}

package com.openpayd.exchange.client.currencyLayer;

import com.openpayd.exchange.client.currencyLayer.response.ConvertCurrencyResponse;
import com.openpayd.exchange.client.currencyLayer.response.ExchangeListResponse;
import com.openpayd.exchange.client.currencyLayer.response.ExchangeRateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "currency-client", url = "${currency-client.url}")
public interface CurrencyLayerClient {

    @GetMapping("/list")
    ExchangeListResponse getExchangeList(@RequestParam("access_key") String accessKey);

    @GetMapping("/live")
    ExchangeRateResponse getExchangeRates(@RequestParam("access_key") String accessKey,
                                          @RequestParam("source") String source,
                                          @RequestParam("currencies") String currencies);

    @GetMapping("/convert")
    ConvertCurrencyResponse convert(@RequestParam("access_key") String accessKey,
                                    @RequestParam("from") String from,
                                    @RequestParam("to") String to,
                                    @RequestParam("amount") Double amount);

}

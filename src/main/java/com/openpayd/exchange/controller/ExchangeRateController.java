package com.openpayd.exchange.controller;

import com.openpayd.exchange.model.response.ExchangeListResponseDto;
import com.openpayd.exchange.model.response.ExchangeRateResponseDto;
import com.openpayd.exchange.service.ExchangeRateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;

    @Operation(summary = "Get Exchange List",
            description = "Returns the current exchange list")
    @GetMapping("/exchange-list")
    public ResponseEntity<ExchangeListResponseDto> getExchangeList() {
        var exchangeRate = exchangeRateService.getExchangeList();
        return ResponseEntity.ok(exchangeRate);
    }

    @Operation(summary = "Calculate exchange rates",
            description = "Calculates the exchange rate between two given currencies")
    @GetMapping("/exchange-rate")
    public ResponseEntity<ExchangeRateResponseDto> calculateExchangeRate(
            @Parameter(description = "Source currency code", example = "USD", required = true)
            @RequestParam String sourceCurrencyCode,
            @Parameter(description = "Target currency code", example = "TRY", required = true)
            @RequestParam String targetCurrencyCode) {

        var exchangeRate = exchangeRateService.getExchangeRate(sourceCurrencyCode, targetCurrencyCode);
        return ResponseEntity.ok(exchangeRate);
    }

}

package com.openpayd.exchange.controller;

import com.openpayd.exchange.model.request.ConvertCurrencyRequestDto;
import com.openpayd.exchange.model.response.ConvertCurrencyResponseDto;
import com.openpayd.exchange.service.CurrencyService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
public class CurrencyConversionController {

    private final CurrencyService currencyService;

    @Operation(summary = "Convert currency",
            description = "Converts a specific amount from one currency to another")
    @PostMapping("/convert")
    public ResponseEntity<ConvertCurrencyResponseDto> convertCurrency(
            @RequestBody @Valid ConvertCurrencyRequestDto request) {

        var exchangeRate = currencyService.convert(
                request.getSourceCurrency(),
                request.getTargetCurrency(),
                request.getAmount()
        );

        return ResponseEntity.ok(exchangeRate);
    }
}

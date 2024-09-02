package com.openpayd.exchange.exception.handler;


import com.openpayd.exchange.exception.BaseException;
import com.openpayd.exchange.model.ErrorDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({BaseException.class})
    public ResponseEntity<?> handleBaseException(BaseException ex) {
        return new ResponseEntity<>(ErrorDto.builder().code(ex.getErrorCode().getCode()).message(ex.getErrorCode().getMessage()).build(), ex.getErrorCode().getHttpStatus());
    }

}

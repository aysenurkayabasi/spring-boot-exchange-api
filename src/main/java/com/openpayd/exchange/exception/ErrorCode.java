package com.openpayd.exchange.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    REMOTE_ERROR_RESPONSE("REM01", "Error occurred while calling remote service", HttpStatus.INTERNAL_SERVER_ERROR),
    UNSUCCESSFUL_RESPONSE("REM02", "Api returned unsuccessful response", HttpStatus.BAD_REQUEST),
    EMPTY_RESPONSE("REM03", "Api returned empty response", HttpStatus.NOT_FOUND);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

}

package com.openpayd.exchange.exception;

public class RemoteApiCallException extends BaseException {

    public RemoteApiCallException(ErrorCode errorCode) {
        super(errorCode);
    }
}

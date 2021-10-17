package com.breadcrumbdata.locationengineserver.config.exceptions;

public class UnSupportedAuthenticationProtocolException extends RuntimeException{
    public UnSupportedAuthenticationProtocolException(String message) {
        super(message);
    }
}

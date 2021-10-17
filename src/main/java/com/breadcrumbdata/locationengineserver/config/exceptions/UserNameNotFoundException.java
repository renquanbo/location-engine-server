package com.breadcrumbdata.locationengineserver.config.exceptions;

public class UserNameNotFoundException extends RuntimeException{
    public UserNameNotFoundException(String message) {
        super(message);
    }
}

package com.breadcrumbdata.locationengineserver.config.exceptions;

public class AnchorIdOrNameAlreadyExistsException extends RuntimeException{
    public AnchorIdOrNameAlreadyExistsException(String message) {
        super(message);
    }
}

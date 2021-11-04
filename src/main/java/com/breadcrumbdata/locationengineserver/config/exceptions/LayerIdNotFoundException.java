package com.breadcrumbdata.locationengineserver.config.exceptions;

public class LayerIdNotFoundException extends RuntimeException{
    public LayerIdNotFoundException(String message) {
        super(message);
    }
}

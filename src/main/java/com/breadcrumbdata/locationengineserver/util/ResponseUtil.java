package com.breadcrumbdata.locationengineserver.util;

import com.breadcrumbdata.locationengineserver.config.CustomResponse;
import org.springframework.http.HttpStatus;

public class ResponseUtil<T> {
    public CustomResponse<T> generate(T result) {
        CustomResponse<T> customResponse = new CustomResponse<>();
        customResponse.setData(result);
        customResponse.setMsg("success");
        customResponse.setCode(HttpStatus.OK.value());
        return customResponse;
    }
}

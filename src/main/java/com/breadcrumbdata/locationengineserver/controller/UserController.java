package com.breadcrumbdata.locationengineserver.controller;

import com.breadcrumbdata.locationengineserver.config.CustomResponse;
import com.breadcrumbdata.locationengineserver.model.dto.UserDTO;
import com.breadcrumbdata.locationengineserver.model.vo.UserVO;
import com.breadcrumbdata.locationengineserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity signUp(@RequestBody @Valid UserDTO userRegisterRequest) {
        UserVO result = userService.create(userRegisterRequest);
        CustomResponse customResponse = new CustomResponse();
        customResponse.setCode(HttpStatus.OK.value());
        customResponse.setMsg("success");
        customResponse.setData(result);
        return ResponseEntity.ok(customResponse);
    }
}

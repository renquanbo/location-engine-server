package com.breadcrumbdata.locationengineserver.controller;

import java.security.interfaces.RSAPrivateKey;

import java.util.Base64;

import com.breadcrumbdata.locationengineserver.config.CustomResponse;
import com.breadcrumbdata.locationengineserver.config.exceptions.UnSupportedAuthenticationProtocolException;
import com.breadcrumbdata.locationengineserver.config.exceptions.UserNameNotFoundException;
import com.breadcrumbdata.locationengineserver.model.enums.Role;
import com.breadcrumbdata.locationengineserver.model.vo.UserVO;
import com.breadcrumbdata.locationengineserver.service.AuthService;
import com.breadcrumbdata.locationengineserver.service.UserService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Value("${jwt.private.key}")
    RSAPrivateKey key;

    private final UserService userService;
    private final AuthService authService;

    @Autowired
    public LoginController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity token(@RequestHeader("Authorization") String authToken) {
        UserVO userVO = authenticate(authToken);
        String jwtToken = authService.createJWTToken(userVO);

        CustomResponse customResponse = new CustomResponse();
        customResponse.setCode(HttpStatus.OK.value());
        customResponse.setMsg("success");
        customResponse.setData(new LoginResponse(jwtToken, userVO.getId(), userVO.getRole()));
        return ResponseEntity.ok(customResponse);
    }

    private UserVO authenticate(String authToken) {
        if(!authToken.startsWith("Basic")) {
            throw new UnSupportedAuthenticationProtocolException("unsupported authentication protocol");
        }
        String encodedUsernamePassword = authToken.replace("Basic ", "");

        byte[] decodedBytes = Base64.getDecoder().decode(encodedUsernamePassword);
        String usernamePassword = new String(decodedBytes);
        if(!usernamePassword.contains(":")) {
            throw new BadCredentialsException("The username or password is not correct");
        }
        String username = usernamePassword.split(":")[0];
        String password = usernamePassword.split(":")[1];
        UserVO userVO = userService.findUserByUsername(username);
        if(userVO == null) {
            throw new UserNameNotFoundException("The username does not exists");
        }
        if(!userService.check(password,username)) {
            throw new BadCredentialsException("The password is not correct");
        }

        return userVO;
    }

    class LoginResponse {
        String token;
        Long userId;
        Role role;

        LoginResponse(String token, Long userId, Role role) {
            this.token = token;
            this.userId = userId;
            this.role = role;
        }

        public String getToken() {
            return token;
        }

        public Long getUserId() {
            return userId;
        }

        public Role getRole() {
            return role;
        }
    }
}

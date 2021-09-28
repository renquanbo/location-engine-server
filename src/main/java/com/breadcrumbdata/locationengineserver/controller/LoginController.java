package com.breadcrumbdata.locationengineserver.controller;

import java.security.interfaces.RSAPrivateKey;
import java.time.Instant;
import java.util.Date;
import java.util.stream.Collectors;

import com.breadcrumbdata.locationengineserver.config.CustomResponse;
import com.breadcrumbdata.locationengineserver.model.enums.Role;
import com.breadcrumbdata.locationengineserver.model.vo.UserVO;
import com.breadcrumbdata.locationengineserver.service.UserService;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Value("${jwt.private.key}")
    RSAPrivateKey key;

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity token(Authentication authentication) {
        Instant now = Instant.now();
        long expiry = 36000L;
        // @formatter:off
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .issuer("self")
                .issueTime(new Date(now.toEpochMilli()))
                .expirationTime(new Date(now.plusSeconds(expiry).toEpochMilli()))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();
        // @formatter:on
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256).build();
        SignedJWT jwt = new SignedJWT(header, claims);

        String jwtString = sign(jwt).serialize();

        UserVO userVO = userService.findUserByUsername(authentication.getName());
        if(userVO == null) {
            return null;
        }
        CustomResponse customResponse = new CustomResponse();
        customResponse.setCode(HttpStatus.OK.value());
        customResponse.setMsg("success");
        customResponse.setData(new LoginResponse(jwtString, userVO.getId(), userVO.getRole()));
        return ResponseEntity.ok(customResponse);
    }

    SignedJWT sign(SignedJWT jwt) {
        try {
            jwt.sign(new RSASSASigner(this.key));
            return jwt;
        }
        catch (Exception ex) {
            throw new IllegalArgumentException(ex);
        }
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

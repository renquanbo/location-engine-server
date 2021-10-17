package com.breadcrumbdata.locationengineserver.service;

import com.breadcrumbdata.locationengineserver.model.vo.UserVO;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPrivateKey;
import java.time.Instant;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class AuthService {
    @Value("${jwt.private.key}")
    RSAPrivateKey key;

    private final UserService userService;

    @Autowired
    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public String createJWTToken(UserVO userVO) {

        Instant now = Instant.now();
        long expiry = 36000L;
        // @formatter:off
        String scope = userVO.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .issuer("self")
                .issueTime(new Date(now.toEpochMilli()))
                .expirationTime(new Date(now.plusSeconds(expiry).toEpochMilli()))
                .subject(userVO.getEmail())
                .claim("scope", scope)
                .build();
        // @formatter:on
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256).build();
        SignedJWT jwt = new SignedJWT(header, claims);

        String jwtToken = sign(jwt).serialize();
        return jwtToken;
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
}

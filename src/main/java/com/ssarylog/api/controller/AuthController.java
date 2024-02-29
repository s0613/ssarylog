package com.ssarylog.api.controller;

import com.ssarylog.api.domain.User;
import com.ssarylog.api.request.Login;
import com.ssarylog.api.response.SessionResponse;
import com.ssarylog.api.service.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.internal.util.logging.Log;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Key;
import java.time.Duration;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    

    @PostMapping("/auth/login")
    public SessionResponse login(@RequestBody Login login){
        String accessToken = authService.signin(login);

        Key key = Keys.secretKeyFor(SignatureAlgorithm.ES256);
        String jws = Jwts.builder().setSubject("joe").signWith(key).compact();

        return new SessionResponse(jws);
    }

}

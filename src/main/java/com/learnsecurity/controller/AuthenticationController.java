package com.learnsecurity.controller;

import com.learnsecurity.model.AuthenticationRequest;
import com.learnsecurity.model.AuthenticationResponse;
import com.learnsecurity.model.RegisterRequest;
import com.learnsecurity.service.impl.AuthenticationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationServiceImpl authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest registerRequest
    ) {
        AuthenticationResponse response = authenticationService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthenticationRequest request
    ) {
        AuthenticationResponse response = authenticationService.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}

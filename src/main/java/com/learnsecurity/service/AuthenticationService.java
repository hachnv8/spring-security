package com.learnsecurity.service;

import com.learnsecurity.model.AuthenticationResponse;
import com.learnsecurity.model.RegisterRequest;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);
}

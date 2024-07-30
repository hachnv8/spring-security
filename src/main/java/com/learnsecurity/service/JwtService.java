package com.learnsecurity.service;

import com.learnsecurity.entity.User;

public interface JwtService {
    String extractUsername(String token);
    String generateToken(User user);
}

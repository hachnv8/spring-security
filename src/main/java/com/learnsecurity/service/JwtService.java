package com.learnsecurity.service;

import com.learnsecurity.entity.User;

public interface JwtService {
    String generateToken(User user);
}

package com.learnsecurity.service.impl;

import com.learnsecurity.entity.Token;
import com.learnsecurity.entity.User;
import com.learnsecurity.mapper.UserMapper;
import com.learnsecurity.model.AuthenticationResponse;
import com.learnsecurity.model.RegisterRequest;
import com.learnsecurity.repository.TokenRepository;
import com.learnsecurity.repository.UserRepository;
import com.learnsecurity.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final JwtServiceImpl jwtService;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setPassword(request.getPassword());
        newUser.setEmail(request.getEmail());
        User createdUser = userRepository.save(newUser);
        String jwtToken = jwtService.generateToken(createdUser);
        Token token = Token.builder()
                .userId(createdUser.getUserId())
                .token(jwtToken)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
        return AuthenticationResponse.builder()
                .userDto(UserMapper.mapToUserDto(createdUser))
                .token(jwtToken)
                .build();
    }
}

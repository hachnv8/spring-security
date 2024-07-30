package com.learnsecurity.service.impl;

import com.learnsecurity.dto.UserDto;
import com.learnsecurity.entity.Role;
import com.learnsecurity.entity.Token;
import com.learnsecurity.entity.User;
import com.learnsecurity.mapper.UserMapper;
import com.learnsecurity.model.AuthenticationRequest;
import com.learnsecurity.model.AuthenticationResponse;
import com.learnsecurity.model.RegisterRequest;
import com.learnsecurity.repository.TokenRepository;
import com.learnsecurity.repository.UserRepository;
import com.learnsecurity.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final JwtServiceImpl jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setEmail(request.getEmail());
        newUser.setRole(Role.ADMIN);
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

    public AuthenticationResponse login(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        Token token = Token.builder()
                .userId(user.getUserId())
                .token(jwtToken)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
        UserDto userDto = UserMapper.mapToUserDto(user);
        return AuthenticationResponse.builder()
                .userDto(userDto)
                .token(jwtToken)
                .build();
    }
}

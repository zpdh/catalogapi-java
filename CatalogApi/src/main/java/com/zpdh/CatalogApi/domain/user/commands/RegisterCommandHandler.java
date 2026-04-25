package com.zpdh.CatalogApi.domain.user.commands;

import com.zpdh.CatalogApi.domain.user.Role;
import com.zpdh.CatalogApi.domain.user.User;
import com.zpdh.CatalogApi.domain.user.UserErrors;
import com.zpdh.CatalogApi.domain.user.UserRepository;
import com.zpdh.CatalogApi.domain.user.dto.AuthResponse;
import com.zpdh.CatalogApi.shared.mediator.command.CommandHandler;
import com.zpdh.CatalogApi.shared.result.Result;
import com.zpdh.CatalogApi.shared.security.JwtService;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class RegisterCommandHandler implements CommandHandler<RegisterCommand, Result<AuthResponse>> {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public RegisterCommandHandler(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    @Transactional
    public Result<AuthResponse> handle(RegisterCommand command) {
        if (userRepository.existsByEmail(command.request().email())) {
            return Result.failure(UserErrors.ALREADY_EXISTS);
        }

        String hashedPassword = passwordEncoder.encode(command.request().password());
        User user = User.create(command.request().email(), hashedPassword, Role.CLIENT);

        userRepository.save(user);

        String token = jwtService.generateToken(user.getEmail(), user.getRole());

        return Result.success(new AuthResponse(token, user.getEmail(), user.getRole()));
    }
}

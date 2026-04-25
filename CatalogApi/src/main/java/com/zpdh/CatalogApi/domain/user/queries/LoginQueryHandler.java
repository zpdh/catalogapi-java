package com.zpdh.CatalogApi.domain.user.queries;

import com.zpdh.CatalogApi.domain.user.UserErrors;
import com.zpdh.CatalogApi.domain.user.UserRepository;
import com.zpdh.CatalogApi.domain.user.dto.AuthResponse;
import com.zpdh.CatalogApi.shared.mediator.query.QueryHandler;
import com.zpdh.CatalogApi.shared.result.Result;
import com.zpdh.CatalogApi.shared.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class LoginQueryHandler implements QueryHandler<LoginQuery, Result<AuthResponse>> {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginQueryHandler(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public Result<AuthResponse> handle(LoginQuery query) {
        return userRepository.findByEmail(query.request().email())
            .filter(user -> passwordEncoder.matches(query.request().password(), user.getPassword()))
            .map(user -> {
                String token = jwtService.generateToken(user.getEmail(), user.getRole());

                return Result.success(new AuthResponse(token, user.getEmail(), user.getRole()));
            }).orElse(Result.failure(UserErrors.INVALID_CREDENTIALS));
    }
}

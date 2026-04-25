package com.zpdh.CatalogApi.domain.user;

import com.zpdh.CatalogApi.domain.user.commands.RegisterCommand;
import com.zpdh.CatalogApi.domain.user.dto.AuthRequest;
import com.zpdh.CatalogApi.domain.user.dto.AuthResponse;
import com.zpdh.CatalogApi.domain.user.dto.RegisterRequest;
import com.zpdh.CatalogApi.domain.user.queries.LoginQuery;
import com.zpdh.CatalogApi.shared.mediator.Mediator;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final Mediator mediator;

    public AuthController(Mediator mediator) {
        this.mediator = mediator;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = mediator.send(new RegisterCommand(request)).getOrThrow();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        AuthResponse response = mediator.query(new LoginQuery(request)).getOrThrow();

        return ResponseEntity.ok(response);
    }
}

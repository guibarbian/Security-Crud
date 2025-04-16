package com.db.taskcrud.controller.impl;

import com.db.taskcrud.dto.request.AuthenticationDTO;
import com.db.taskcrud.dto.request.RefreshDTO;
import com.db.taskcrud.dto.request.RegisterDTO;
import com.db.taskcrud.dto.response.AuthenticationResponse;
import com.db.taskcrud.dto.response.RefreshResponse;
import com.db.taskcrud.dto.response.RegistrationResponse;
import com.db.taskcrud.infra.security.AuthenticationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> register(@RequestBody RegisterDTO dto){
        return ResponseEntity.ok(authenticationService.register(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody AuthenticationDTO dto){
        return ResponseEntity.ok(authenticationService.login(dto));
    }

    @PostMapping("/refresh-login")
    public ResponseEntity<RefreshResponse> refreshLogin(@Valid @NotBlank RefreshDTO dto){
        return ResponseEntity.ok(authenticationService.refreshToken(dto));
    }

}

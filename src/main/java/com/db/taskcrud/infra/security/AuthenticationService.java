package com.db.taskcrud.infra.security;

import com.db.taskcrud.dto.AuthenticationDTO;
import com.db.taskcrud.dto.RegisterDTO;
import com.db.taskcrud.dto.response.AuthenticationResponse;
import com.db.taskcrud.enums.PersonRole;
import com.db.taskcrud.model.Person;
import com.db.taskcrud.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterDTO dto) {
        var person = Person.builder()
                .name(dto.name())
                .email(dto.email())
                .password(passwordEncoder.encode(dto.password()))
                .role(PersonRole.USER).build();

        personRepository.save(person);

        var jwtToken = tokenService.generateToken(person);
        return AuthenticationResponse.builder()
                .token(jwtToken).build();
    }

    public AuthenticationResponse login(AuthenticationDTO dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.email(),
                        dto.password()
                )
        );

        var person = personRepository.findByEmail(dto.email())
                .orElseThrow();
        var jwtToken = tokenService.generateToken(person);
        return AuthenticationResponse.builder()
                .token(jwtToken).build();
    }
}

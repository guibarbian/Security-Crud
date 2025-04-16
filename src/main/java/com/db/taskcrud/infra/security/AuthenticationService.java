package com.db.taskcrud.infra.security;

import com.db.taskcrud.dto.request.AuthenticationDTO;
import com.db.taskcrud.dto.request.RegisterDTO;
import com.db.taskcrud.dto.response.AuthenticationResponse;
import com.db.taskcrud.dto.request.RefreshDTO;
import com.db.taskcrud.dto.response.RefreshResponse;
import com.db.taskcrud.dto.response.RegistrationResponse;
import com.db.taskcrud.enums.PersonRole;
import com.db.taskcrud.exception.NotFoundException;
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

    public RegistrationResponse register(RegisterDTO dto) {
        var person = Person.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(PersonRole.USER).build();

        personRepository.save(person);

        var jwtToken = tokenService.generateToken(person);

        return RegistrationResponse.builder()
                .token(jwtToken).build();
    }

    public AuthenticationResponse login(AuthenticationDTO dto) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.email(), dto.password()));

        String jwtToken = tokenService.generateToken((Person) authentication.getPrincipal());
        String refreshToken = tokenService.generateRefreshToken((Person) authentication.getPrincipal());
        return new AuthenticationResponse(jwtToken, refreshToken);
    }

    public RefreshResponse refreshToken(RefreshDTO dto){
        var refreshToken = dto.refreshToken();
        Long id = Long.valueOf(tokenService.verifyToken(refreshToken));

        var person = personRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Person not found"));

        String jwtToken = tokenService.generateToken(person);
        String updateToken = tokenService.generateRefreshToken(person);
        return new RefreshResponse(jwtToken, updateToken);
    }
}

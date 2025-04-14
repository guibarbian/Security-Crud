package com.db.taskcrud.unit;

import com.db.taskcrud.dto.AuthenticationDTO;
import com.db.taskcrud.dto.RegisterDTO;
import com.db.taskcrud.dto.response.AuthenticationResponse;
import com.db.taskcrud.enums.PersonRole;
import com.db.taskcrud.infra.security.AuthenticationService;
import com.db.taskcrud.infra.security.TokenService;
import com.db.taskcrud.model.Person;
import com.db.taskcrud.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenService tokenService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    Person peter = new Person(1L, "Peter Parker", "peterp@gmail.com", "encodedPassword", PersonRole.USER);

    RegisterDTO rDto = new RegisterDTO("Peter Parker", "peterp@gmail.com", "pass123");
    AuthenticationDTO aDto = new AuthenticationDTO("peterp@gmail.com", "pass123");



    @Test
    void register_ShouldReturnToken() {
        when(passwordEncoder.encode(rDto.password())).thenReturn("encodedPassword");
        Person auxPerson = new Person(null, rDto.name(), rDto.email(), passwordEncoder.encode(rDto.password()), PersonRole.USER);

        when(tokenService.generateToken(auxPerson)).thenReturn("mockedToken");

        AuthenticationResponse response = authenticationService.register(rDto);

        assertNotNull(response);
        assertEquals("mockedToken", response.getToken());

        ArgumentCaptor<Person> captor = ArgumentCaptor.forClass(Person.class);
        verify(personRepository).save(captor.capture());
        assertEquals("peterp@gmail.com", captor.getValue().getEmail());
        assertEquals("encodedPassword", captor.getValue().getPassword());
    }

    @Test
    void login_ShouldReturnToken() {

        when(personRepository.findByEmail(aDto.email())).thenReturn(Optional.of(peter));
        when(tokenService.generateToken(peter)).thenReturn("mockedJwt");

        AuthenticationResponse response = authenticationService.login(aDto);

        assertNotNull(response);
        assertEquals("mockedJwt", response.getToken());

        verify(authenticationManager).authenticate(
                new UsernamePasswordAuthenticationToken(aDto.email(), aDto.password())
        );
    }
}

package com.db.taskcrud.unit;

import com.db.taskcrud.controller.impl.AuthenticationController;
import com.db.taskcrud.dto.AuthenticationDTO;
import com.db.taskcrud.dto.RegisterDTO;
import com.db.taskcrud.dto.response.AuthenticationResponse;
import com.db.taskcrud.infra.security.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthenticationController authenticationController;

    RegisterDTO rDto = new RegisterDTO("Peter Parker", "peterp@gmail.com", "pass123");
    AuthenticationDTO aDto = new AuthenticationDTO("peterp@gmail.com", "pass123");

    @Test
    void register_ShouldReturnOkWithToken() {
        AuthenticationResponse mockResponse = new AuthenticationResponse("jwtToken");

        when(authenticationService.register(rDto)).thenReturn(mockResponse);

        ResponseEntity<AuthenticationResponse> response = authenticationController.register(rDto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("jwtToken", response.getBody().getToken());
        verify(authenticationService).register(rDto);
    }

    @Test
    void login_ShouldReturnOkWithToken() {
        AuthenticationResponse mockResponse = new AuthenticationResponse("mockedToken");

        when(authenticationService.login(aDto)).thenReturn(mockResponse);

        ResponseEntity<AuthenticationResponse> response = authenticationController.login(aDto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("mockedToken", response.getBody().getToken());
        verify(authenticationService).login(aDto);
    }
}

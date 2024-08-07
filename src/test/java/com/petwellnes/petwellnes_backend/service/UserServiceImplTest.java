package com.petwellnes.petwellnes_backend.service;

import com.petwellnes.petwellnes_backend.infra.security.LoginRequest;
import com.petwellnes.petwellnes_backend.infra.security.TokenResponse;
import com.petwellnes.petwellnes_backend.repository.UserRepository;
import com.petwellnes.petwellnes_backend.model.dto.userDto.UserDetailsDTO;
import com.petwellnes.petwellnes_backend.model.entity.User;
import com.petwellnes.petwellnes_backend.infra.security.JwtService;
import com.petwellnes.petwellnes_backend.service.Impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void login_Success() {
        LoginRequest loginRequest = new LoginRequest("user", "password");
        User user = new User();
        user.setUsername("user");
        user.setUserId(1L);

        Authentication authentication = mock(Authentication.class);
        UserDetails userDetails = mock(UserDetails.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        when(jwtService.generateToken(userDetails, user)).thenReturn("token");

        TokenResponse response = userService.login(loginRequest);

        assertNotNull(response);
        assertEquals("token", response.getToken());
        assertEquals(1L, response.getUserId());
    }

    @Test
    void login_Failure() {
        LoginRequest loginRequest = new LoginRequest("user", "wrongPassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("Invalid credentials"));

        assertThrows(RuntimeException.class, () -> userService.login(loginRequest));
    }


    @Test
    void getUserDetails_Success() {
        User user = new User();
        user.setUserId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserDetailsDTO userDetails = userService.getUserDetails(1L);

        assertNotNull(userDetails);
        assertEquals(1L, userDetails.userId());
    }

}

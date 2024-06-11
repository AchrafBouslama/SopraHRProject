package com.example.achref;

import com.example.achref.Controller.auth.AuthenticationRequest;
import com.example.achref.Controller.auth.AuthenticationResponse;
import com.example.achref.Controller.auth.EmailService;
import com.example.achref.Controller.auth.RegisterRequest;
import com.example.achref.Entities.user.Role;
import com.example.achref.Entities.user.User;
import com.example.achref.Repositories.user.PasswordResetTokenRepository;
import com.example.achref.Repositories.user.UserRepository;
import com.example.achref.Services.AuthenticationService;
import com.example.achref.config.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private EmailService emailService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Mock
    private UserDetailsService userDetailsService;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreatePasswordResetTokenForUser() {
        User user = new User();
        String token = "randomToken";

        authenticationService.createPasswordResetTokenForUser(user, token);

        // Verify that password reset token is saved
        verify(passwordResetTokenRepository, times(1)).save(any());
    }


    @Test
    void testRegister() {
        RegisterRequest request = new RegisterRequest();
        request.setFirstname("John");
        request.setLastname("Doe");
        request.setEmail("john@example.com");

        // Mock User object with appropriate values
        User savedUser = User.builder()
                .iduserr(1) // Assuming ID is non-null and valid
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password("encodedPassword") // Assuming encoded password
                .role(Role.USER)
                .image("defaultusericon.jpg")
                .isEnabled(false)
                .verificationCode("123456") // Assuming verification code
                .build();

        // Mock UserRepository to return the mocked User object
        when(userRepository.save(any())).thenReturn(savedUser);

        // Mock JwtService and EmailService calls
        when(jwtService.generateToken(any())).thenReturn("jwtToken");
        doNothing().when(emailService).sendVerificationCodeMail(anyString(), anyInt());
        doNothing().when(emailService).sendPasswordByEmail(anyString(), anyString());


        AuthenticationResponse response = authenticationService.register(request);

        // Assertions
        assertNotNull(response);
        assertEquals("jwtToken", response.getToken());
        assertEquals("John", response.getFirstname());
        assertEquals("Doe", response.getLastname());

        // Verify method invocations
        verify(userRepository, times(1)).save(any());
        verify(emailService, times(1)).sendVerificationCodeMail(anyString(), anyInt());
        verify(emailService, times(1)).sendPasswordByEmail(anyString(), anyString());
    }




    @Test
    void testChangePassword() {
        // Créer un utilisateur
        User user = new User();
        user.setIduserr(1);
        user.setPassword("encodedPassword"); // Ancien mot de passe correct

        // Mocking la recherche de l'utilisateur
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        // Configurer le mock pour retourner true lors de la comparaison des mots de passe
        when(passwordEncoder.matches("oldPassword", "encodedPassword")).thenReturn(true);

        // Appeler la méthode pour changer le mot de passe
        boolean result = authenticationService.changePassword(1, "oldPassword", "newPassword", "newPassword");

        // Vérifier que le mot de passe est changé avec succès
        assertTrue(result);

        // Vérifier que le mot de passe est encodé et sauvegardé
        verify(passwordEncoder, times(1)).encode("newPassword");
        verify(userRepository, times(1)).save(any());
    }


    @Test
    void testAuthenticate() {
        // Créer une demande d'authentification
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("john@example.com");
        request.setPassword("password");
        User user = new User();
        user.setIduserr(1);
        user.setEmail("john@example.com");
        user.setPassword("encodedPassword");
        user.setRole(Role.USER);
        when(userRepository.findByEmail("john@example.com")).thenReturn(java.util.Optional.of(user));
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("john@example.com");
        when(authentication.getPrincipal()).thenReturn(mock(UserDetails.class));
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(jwtService.generateToken(any(UserDetails.class))).thenReturn("jwtToken");
        AuthenticationResponse response = authenticationService.authenticate(request);
        assertNotNull(response);
        assertEquals("jwtToken", response.getToken());
        assertEquals(1, response.getId());
        assertEquals(Role.USER, response.getRole());
    }
    @Test
    void testVerifyUser() {
        User user = new User();
        user.setIduserr(1);
        user.setVerificationCode("1234");

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        authenticationService.verifyUser(1, 1234);

        assertTrue(user.isEnabled());

        verify(userRepository, times(1)).save(any());
    }

    @Test
    void testResendVerificationCode() {
        // Créer un utilisateur
        User user = new User();
        user.setIduserr(1);
        user.setVerificationCode("1234");
        user.setEnabled(false);
        user.setEmail("john@example.com"); // Adresse e-mail valide

        // Mocking la recherche de l'utilisateur
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        // Mocking l'envoi du code de vérification par email
        doNothing().when(emailService).sendVerificationCodeMail(anyString(), anyInt());

        // Appeler la méthode pour renvoyer le code de vérification
        authenticationService.resendVerificationCode(1);

        // Vérifier que le code de vérification est envoyé avec succès par email
        verify(emailService, times(1)).sendVerificationCodeMail(anyString(), anyInt());

        // Vérifier que le nouveau code de vérification est sauvegardé
        verify(userRepository, times(1)).save(any());


        // Add tests for other methods of AuthenticationService here...
    }


}

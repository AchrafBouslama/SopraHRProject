package com.example.achref.Services;

import com.example.achref.Controller.auth.AuthenticationRequest;
import com.example.achref.Controller.auth.AuthenticationResponse;
import com.example.achref.Controller.auth.EmailService;
import com.example.achref.Controller.auth.RegisterRequest;
import com.example.achref.Entities.user.PasswordResetToken;
import com.example.achref.Entities.user.Role;
import com.example.achref.Entities.user.User;
import com.example.achref.Repositories.user.PasswordResetTokenRepository;
import com.example.achref.Repositories.user.UserRepository;
import com.example.achref.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor

public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;
    private final UserDetailsService userDetailsService;
    @Autowired
    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                                 JwtService jwtService, EmailService emailService, AuthenticationManager
                                 authenticationManager, PasswordResetTokenRepository
                                 passwordResetTokenRepository, UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.emailService = emailService;
        this.authenticationManager = authenticationManager;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.userDetailsService = userDetailsService;
    }










    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setUser(user);
        passwordResetToken.setToken(token);
        passwordResetToken.setExpiryDate(LocalDateTime.now().plusHours(1)); // par exemple, expire dans 1 heure
        passwordResetTokenRepository.save(passwordResetToken);
    }

    /*  public  AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        System.out.println(jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

*/
    private static final Random RANDOM=new Random();
    private int generateVerificationCode(){
        return 1000+RANDOM.nextInt(9000);//1000 9999
    }
    public AuthenticationResponse register(RegisterRequest request) {
        String generatedPassword = generateRandomPassword();
        int verificationCode = generateVerificationCode();
        User user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(generatedPassword)) // Encode the generated password
                .role(Role.USER)
                .image("defaultusericon.jpg")
                .isEnabled(false)
                .verificationCode(String.valueOf(verificationCode))
                .build();

        user = userRepository.save(user); // Save and retrieve the user with the assigned ID

        // Check if the ID is assigned correctly
        if (user.getIduserr() == null) {
            throw new IllegalStateException("The saved user does not have an assigned ID.");
        }

        emailService.sendVerificationCodeMail(request.getEmail(), verificationCode);
        emailService.sendPasswordByEmail(request.getEmail(), generatedPassword);

        var jwtToken = jwtService.generateToken(user);
        System.out.println(jwtToken);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .id(user.getIduserr()) // Ensure the ID is non-null here
                .role(user.getRole())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .build();
    }


    public boolean changePassword(int userId, String oldPassword, String newPassword, String confirmPassword) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("Invalid current password");
        }

        if (!newPassword.equals(confirmPassword)) {
            throw new RuntimeException("New password and confirm password do not match");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return true;
    }



    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        System.out.println("Received authentication request: " + request);

        // Perform authentication
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            System.out.println("Authentication successful for user: " + authentication.getName());
        } catch (AuthenticationException e) {
            System.out.println("Authentication failed: " + e.getMessage());
            throw e; // Re-throw the exception to handle it further if needed
        }

        // Generate JWT token
        String jwtToken = jwtService.generateToken((UserDetails) authentication.getPrincipal());
        System.out.println("Generated JWT token: " + jwtToken);
        User user=userRepository.findByEmail(request.getEmail()).orElse(null);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .id(user.getIduserr())
                .role(user.getRole())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .build();
    }

    private String generateRandomPassword() {
        int length = 10; // Longueur du mot de passe
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+"; // Caractères autorisés dans le mot de passe
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void verifyUser(int id,int verificationCode){
      User user=userRepository.findById(id).orElseThrow(()->new RuntimeException("User not found"));
      if(Integer.parseInt(user.getVerificationCode())==verificationCode){
          user.setEnabled(true);
          userRepository.save(user);
      }else{
          throw new RuntimeException("Invalid verification Code");
      }
    }
    public void resendVerificationCode(int id){
        User user=userRepository.findById(id).orElseThrow(()->new RuntimeException("User not found"));
        if(user.isEnabled()){
            throw new RuntimeException("Account is already verified");
        }
        int newVerificatioCode=generateVerificationCode();
        user.setVerificationCode(String.valueOf(newVerificatioCode));
        userRepository.save(user);
        emailService.sendVerificationCodeMail(user.getEmail(),newVerificatioCode);
    }








}

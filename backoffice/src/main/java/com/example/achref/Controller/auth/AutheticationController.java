package com.example.achref.Controller.auth;

import com.example.achref.Entities.user.PasswordResetToken;
import com.example.achref.Entities.user.User;
import com.example.achref.Repositories.user.PasswordResetTokenRepository;
import com.example.achref.Repositories.user.UserRepository;
import com.example.achref.Services.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;


@CrossOrigin(origins="*")
@RestController
@RequestMapping("/api/vi/auth")
@RequiredArgsConstructor
public class AutheticationController {

    private final AuthenticationService authenticationService;
    private final HttpServletRequest servletRequest;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final PasswordEncoder passwordEncoder; // Ajoutez cette ligne pour injecter le PasswordEncoder




    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        System.out.println("email"+request.getEmail());

        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authenticationService.authenticate(request));


    }
    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        try {
            authenticationService.changePassword(
                    changePasswordRequest.getUserId(),
                    changePasswordRequest.getOldPassword(),
                    changePasswordRequest.getNewPassword(),
                    changePasswordRequest.getConfirmPassword()
            );
            return ResponseEntity.ok("Password changed successfully");
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }



    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        if (email != null) {
            Optional<User> optionalUser = userRepository.findByEmail(email);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                String resetToken = UUID.randomUUID().toString();
                authenticationService.createPasswordResetTokenForUser(user, resetToken);
                // Envoyez le token de réinitialisation de mot de passe par e-mail à l'utilisateur
                String resetLink = "http://localhost:4200/newPassword/" + resetToken;
                emailService.sendPasswordResetEmail(email, resetLink);

                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest().build(); // Requête incorrecte si l'e-mail est manquant
        }
    }


    @PostMapping("/set-new-password")
    public ResponseEntity<?> setNewPassword(@RequestParam("token") String token, @RequestBody String newPassword) {
        // Valider le token de réinitialisation de mot de passe
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token);
        if (resetToken == null || resetToken.isExpired()) {
            return ResponseEntity.badRequest().build();
        }

        // Mettre à jour le mot de passe de l'utilisateur
        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        // Supprimer le token de réinitialisation de mot de passe
        passwordResetTokenRepository.delete(resetToken);

        return ResponseEntity.ok().build();
    }
    @PostMapping("/verify/{id}/{verificationCode}")
    public ResponseEntity<String> verifyUser(@PathVariable int id,@PathVariable int verificationCode){
        authenticationService.verifyUser(id,verificationCode);
        return ResponseEntity.ok("Account verified successfully");
    }
    @PostMapping("/resend-code/{id}")
    public ResponseEntity<String> resendVerificationCode(@PathVariable int id){
        authenticationService.resendVerificationCode(id);
        return ResponseEntity.ok("Account verified successfully");
    }




}
@Getter
@Setter
class ChangePasswordRequest {
    private int userId;
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
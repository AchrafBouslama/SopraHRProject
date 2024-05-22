package com.example.achref.Services;

import com.example.achref.Entities.user.User;
import com.example.achref.Repositories.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProfileService {

    @Autowired

    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserProfileService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    // Créer un profil utilisateur
    public void createUserProfile(User user) {
        // Encode le mot de passe avant de le sauvegarder
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        userRepository.save(user);
    }

    public List<User> displayUser() {
        return userRepository.findAll();
    }

    public void deleteUser(Integer idUser) {
        userRepository.deleteById(idUser);
    }

    public void updateUser(User user) {
        User existingUser = userRepository.findById(user.getIduserr()).orElse(null);
        if (existingUser == null) {
        } else {
            // Mettez à jour les champs autres que isEnabled
            existingUser.setEmail(user.getEmail());
            existingUser.setFirstname(user.getFirstname());
            existingUser.setLastname(user.getLastname());
            if(user.getImage()!=null){
                existingUser.setImage(user.getImage());
            }

            existingUser.setRole(user.getRole());


            // Mettez à jour le champ isEnabled séparément


            // Enregistrez les modifications dans la base de données
            userRepository.save(existingUser);
        }
    }
    public User getUserById(Integer idUser){
        return userRepository.findById(idUser).orElse(null);
    }



}

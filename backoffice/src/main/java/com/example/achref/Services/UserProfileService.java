package com.example.achref.Services;

import com.example.achref.Entities.user.User;
import com.example.achref.Repositories.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProfileService {


    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserProfileService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }



    public void createUserProfile(User user) {

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

            existingUser.setEmail(user.getEmail());
            existingUser.setFirstname(user.getFirstname());
            existingUser.setLastname(user.getLastname());
            if(user.getImage()!=null){
                existingUser.setImage(user.getImage());
            }

            existingUser.setRole(user.getRole());

            userRepository.save(existingUser);
        }
    }
    public User getUserById(Integer idUser){
        return userRepository.findById(idUser).orElse(null);
    }



}

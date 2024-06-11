package com.example.achref.Controller.user;

import com.example.achref.Entities.user.User;
import com.example.achref.Services.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @PostMapping("/Createprofile")
    public void createUserProfile(@RequestBody User user) {
      userProfileService.createUserProfile(user);
    }


    @GetMapping("/displayUser")
    public List<User> displayUser(){
        return userProfileService.displayUser();
    }
    @DeleteMapping("/deleteQuiz/{idUser}")
    public void deleteUser(@PathVariable Integer idUser){
        userProfileService.deleteUser(idUser);
    }

    @PutMapping("/updateUser")
    public void updateUser(@RequestBody User user){
        userProfileService.updateUser(user);
    }
    @GetMapping("/displayUser/{id}")
    public User displayUserById(@PathVariable Integer id){
        return userProfileService.getUserById(id);
    }

}

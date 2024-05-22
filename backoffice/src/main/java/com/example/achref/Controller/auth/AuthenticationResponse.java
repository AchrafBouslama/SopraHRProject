package com.example.achref.Controller.auth;


import com.example.achref.Entities.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class AuthenticationResponse {
    private String token ;
    private int id;
    private Role role;
    private  String firstname;
    private  String lastname;
}

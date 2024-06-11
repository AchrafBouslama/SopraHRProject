package com.example.achref;

import com.example.achref.Entities.user.User;
import com.example.achref.Repositories.user.UserRepository;
import com.example.achref.Services.UserProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserProfileServiceTest {


    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserProfileService userProfileService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUserProfile() {
        User user = new User();
        user.setPassword("plainPassword");

        when(passwordEncoder.encode("plainPassword")).thenReturn("hashedPassword");
        when(userRepository.save(user)).thenReturn(user);

        userProfileService.createUserProfile(user);

        assertEquals("hashedPassword", user.getPassword());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testDisplayUser() {
        User user1 = new User();
        User user2 = new User();
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        assertEquals(2, userProfileService.displayUser().size());
    }

    @Test
    void testDeleteUser() {
        Integer userId = 1;
        doNothing().when(userRepository).deleteById(userId);

        userProfileService.deleteUser(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testUpdateUser() {
        User existingUser = new User();
        existingUser.setIduserr(1);
        existingUser.setEmail("old@example.com");

        User updatedUser = new User();
        updatedUser.setIduserr(1);
        updatedUser.setEmail("new@example.com");

        when(userRepository.findById(1)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        userProfileService.updateUser(updatedUser);

        assertEquals("new@example.com", existingUser.getEmail());
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    void testGetUserById() {
        User user = new User();
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        User foundUser = userProfileService.getUserById(1);

        assertEquals(user, foundUser);
    }

    @Test
    void testGetUserById_NotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        User foundUser = userProfileService.getUserById(1);

        assertNull(foundUser);
    }


}

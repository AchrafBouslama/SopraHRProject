package com.example.achref;

import com.example.achref.Controller.user.UserProfileController;
import com.example.achref.Entities.user.User;
import com.example.achref.Services.UserProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserProfileControllerTest {

    @Mock
    private UserProfileService userProfileService;

    @InjectMocks
    private UserProfileController userProfileController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUserProfile() {
        User user = new User();
        doNothing().when(userProfileService).createUserProfile(user);

        userProfileController.createUserProfile(user);

        verify(userProfileService, times(1)).createUserProfile(user);
    }

    @Test
    void testDisplayUser() {
        List<User> userList = new ArrayList<>();
        when(userProfileService.displayUser()).thenReturn(userList);

        List<User> result = userProfileController.displayUser();

        assertEquals(userList, result);
    }

    @Test
    void testDeleteQuiz() {
        Integer userId = 1;
        doNothing().when(userProfileService).deleteUser(userId);

        userProfileController.deleteUser(userId);

        verify(userProfileService, times(1)).deleteUser(userId);
    }


    @Test
    void testUpdateUser() {
        User user = new User();
        doNothing().when(userProfileService).updateUser(user);

        userProfileController.updateUser(user);

        verify(userProfileService, times(1)).updateUser(user);
    }

    @Test
    void testDisplayUserById() {
        Integer userId = 1;
        User user = new User();
        when(userProfileService.getUserById(userId)).thenReturn(user);

        User result = userProfileController.displayUserById(userId);

        assertEquals(user, result);
    }
}

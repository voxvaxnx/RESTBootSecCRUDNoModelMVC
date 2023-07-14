package ru.kata.spring.boot_security.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.kata.spring.boot_security.demo.controllers.RestAdminController;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestAdminControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private RestAdminController restAdminController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers_UsersExist_ReturnsListOfUsersWithHttpStatusOK() {
        // Arrange
        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(new User());
        when(userService.getAllUsers()).thenReturn(expectedUsers);

        // Act
        ResponseEntity<List<User>> response = restAdminController.getAllUsers();

        // Assert
        assertEquals(expectedUsers, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetAllUsers_NoUsersExist_ReturnsHttpStatusNotFound() {
        // Arrange
        when(userService.getAllUsers()).thenReturn(new ArrayList<>());

        // Act
        ResponseEntity<List<User>> response = restAdminController.getAllUsers();

        // Assert
        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetUser_ExistingId_ReturnsUserWithHttpStatusOK() {
        // Arrange
        Long id = 1L;
        User expectedUser = new User();
        when(userService.getById(id)).thenReturn(expectedUser);

        // Act
        ResponseEntity<User> response = restAdminController.getUser(id);

        // Assert
        assertEquals(expectedUser, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetUser_NonExistingId_ReturnsHttpStatusNotFound() {
        // Arrange
        Long id = 1L;
        when(userService.getById(id)).thenReturn(null);

        // Act
        ResponseEntity<User> response = restAdminController.getUser(id);

        // Assert
        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testAddNewUser_AddsUserWithHttpStatusCreated() {
        // Arrange
        User user = new User();

        // Act
        ResponseEntity<User> response = restAdminController.addNewUser(user);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(userService, times(1)).addUser(user);
    }

    @Test
    void testUpdateUser_UpdatesUserWithHttpStatusOK() {
        // Arrange
        User user = new User();

        // Act
        ResponseEntity<User> response = restAdminController.updateUser(user);

        // Assert
        assertEquals(user, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService, times(1)).editUser(user);
    }

    @Test
    void testDeleteUser_DeletesUserWithHttpStatusOK() {
        // Arrange
        Long id = 1L;

        // Act
        ResponseEntity<String> response = restAdminController.deleteUser(id);

        // Assert
        assertEquals("User with ID = " + id + " was deleted", response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService, times(1)).deleteUser(id);
    }
}

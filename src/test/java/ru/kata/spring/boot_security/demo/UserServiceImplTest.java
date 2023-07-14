package ru.kata.spring.boot_security.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetByeMail_ExistingEmail_ReturnsUser() {
        // Arrange
        String email = "test@example.com";
        User expectedUser = new User();
        when(userRepository.getByeMail(email)).thenReturn(expectedUser);

        // Act
        User result = userService.getByeMail(email);

        // Assert
        assertEquals(expectedUser, result);
    }

    @Test
    void testGetByeMail_NonExistingEmail_ReturnsNull() {
        // Arrange
        String email = "nonexisting@example.com";
        when(userRepository.getByeMail(email)).thenReturn(null);

        // Act
        User result = userService.getByeMail(email);

        // Assert
        assertNull(result);
    }

    @Test
    void testGetById_ExistingId_ReturnsUser() {
        // Arrange
        Long id = 1L;
        User expectedUser = new User();
        when(userRepository.findById(id)).thenReturn(Optional.of(expectedUser));

        // Act
        User result = userService.getById(id);

        // Assert
        assertEquals(expectedUser, result);
    }

    @Test
    void testGetById_NonExistingId_ReturnsNull() {
        // Arrange
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        User result = userService.getById(id);

        // Assert
        assertNull(result);
    }

    @Test
    void testGetAllUsers_ReturnsListOfUsers() {
        // Arrange
        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(new User());
        expectedUsers.add(new User());
        when(userRepository.findAll()).thenReturn(expectedUsers);

        // Act
        List<User> result = userService.getAllUsers();

        // Assert
        assertEquals(expectedUsers, result);
    }

    @Test
    void testDeleteUser_ExistingId_DeletesUser() {
        // Arrange
        Long id = 1L;

        // Act
        userService.deleteUser(id);

        // Assert
        verify(userRepository, times(1)).deleteById(id);
    }

    @Test
    void testAddUser_EncodesPasswordAndSavesUser() {
        // Arrange
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        // Act
        userService.addUser(user);

        // Assert
        assertEquals("encodedPassword", user.getPassword());
        assertEquals(user.getEmail(), user.getUsername());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testEditUser_EncodesPasswordAndSavesUser() {
        // Arrange
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        // Act
        userService.editUser(user);

        // Assert
        assertEquals("encodedPassword", user.getPassword());
        assertEquals(user.getEmail(), user.getUsername());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testGetUserByUsername_ExistingUsername_ReturnsUser() {
        // Arrange
        String username = "test";
        User expectedUser = new User();
        when(userRepository.getByUsername(username)).thenReturn(expectedUser);

        // Act
        User result = userService.getUserByUsername(username);

        // Assert
        assertEquals(expectedUser, result);
    }

    @Test
    void testLoadUserByUsername_ExistingEmail_ReturnsUser() {
        // Arrange
        String email = "test@example.com";
        User expectedUser = new User();
        when(userRepository.getByeMail(email)).thenReturn(expectedUser);

        // Act
        User result = userService.loadUserByUsername(email);

        // Assert
        assertEquals(expectedUser, result);
    }

    @Test
    void testLoadUserByUsername_NonExistingEmail_ThrowsUsernameNotFoundException() {
        // Arrange
        String email = "nonexisting@example.com";
        when(userRepository.getByeMail(email)).thenReturn(null);

        // Act and Assert
        assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername(email);
        });
    }
}

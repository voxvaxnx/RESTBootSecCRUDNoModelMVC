package ru.kata.spring.boot_security.demo.services;
import ru.kata.spring.boot_security.demo.models.User;
import java.util.List;

public interface UserService {

   User getByeMail(String eMail);

    User getById(Long id);

    List<User> getAllUsers();

    void deleteUser(Long id);

    void addUser(User user);

    void editUser(User user);

    User getUserByUsername(String username);

}

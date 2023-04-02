package ru.kata.spring.boot_security.demo.services;
import ru.kata.spring.boot_security.demo.models.User;
import java.util.List;

public interface UserService {

   User getByeMail(String eMail);

    User getById(long id);

    List<User> getAllUsers();

    void deleteUser(long id);

    void addUser(User user);

    void editUser(User user);

}

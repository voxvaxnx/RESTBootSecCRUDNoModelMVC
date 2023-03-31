package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import javax.transaction.Transactional;
import java.util.List;


@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User getByUserName(String userName) {
        return userRepository.getByUserName(userName);
    }

    @Override
    public User getById(long id) {
        return userRepository.getById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteUser(long id) {
        userRepository.delete(getById(id));
    }

    @Override
    @Transactional
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(user.getRoles());
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void editUser(User user) {
        User userToEdit = getById(user.getId());
        userToEdit.setEMail(user.getEMail());
        if (!passwordEncoder.encode(user.getPassword()).equals(userToEdit.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userToEdit.setRoles(user.getRoles());
        userToEdit.setUsername(user.getUsername());
        userRepository.save(userToEdit);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        return user;
    }
}

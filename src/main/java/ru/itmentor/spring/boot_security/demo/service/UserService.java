package ru.itmentor.spring.boot_security.demo.service;


import ru.itmentor.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    void createUser(User user);

    User getUser(Long id);

    List<User> getAllUsers();

    void updateUser(User user, Long id);

    void deleteUser(Long id);
}

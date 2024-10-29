package org.example.service;

import org.example.dao.UserDao;
import org.example.model.User;

import java.util.*;

public class UserService {
    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User signUp(User user) {
        validateEmail(user.getEmail());
        validatePassword(user.getPassword());
        return userDao.signUp(user);
    }

    public User signIn(User user) {
        return userDao.signIn(user);
    }

    public User getUserById(int id) {
        return userDao.getUserById(id);
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public User updateUser(User user) {
        validateEmail(user.getEmail());
        validatePassword(user.getPassword());
        return userDao.updateUser(user);
    }

    public void deleteUser(int id) {
        userDao.deleteUser(id);
    }

    private void validateEmail(String email) {
        String requiredDomain = "@email.com";
        String requiredDomain1 = "@gmail.com";
        if (!email.endsWith(requiredDomain) && !email.endsWith(requiredDomain1)) {
            throw new IllegalArgumentException("Email must end with " + requiredDomain + " or " + requiredDomain1);
        }
    }

    private void validatePassword(String password) {
        if (password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long.");
        }
    }
}

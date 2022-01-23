package com.gunt.springboot.springboot_security.dao;

import com.gunt.springboot.springboot_security.entity.User;

import java.util.List;

public interface UserDAO {
    public List<User> getAllUsers();

    public void saveUser(User user);

    public void deleteUser(User user);

    public User getById(Long id);

    User getUserByName(String name);




}

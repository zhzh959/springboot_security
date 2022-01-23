package com.gunt.springboot.springboot_security.service;


import com.gunt.springboot.springboot_security.entity.User;

import java.util.List;

public interface UserService {

    public List<User> getAllUsers();

    public void saveUser(User user);

    public void deleteUser(User user);

    public User getById(Long id);
}

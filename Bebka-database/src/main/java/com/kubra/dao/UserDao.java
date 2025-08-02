package com.kubra.dao;

import com.kubra.user.User;

import java.util.List;

public interface UserDao {
    void createtable();
    void save(User user);
    List<User> findAll();
    void update(User user);
    void delete(int id);
}

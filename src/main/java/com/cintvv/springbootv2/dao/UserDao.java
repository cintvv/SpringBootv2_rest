package com.cintvv.springbootv2.dao;

import com.cintvv.springbootv2.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {
    void saveUser(User user);

    void removeUserById(int id);

    void removeUserByUsername(String username);

    void updateUser(User user);

    List<User> getAllUsers();

    User getUserById(int id);

    User getUserByUsername(String username);

    String getRolesByUsername(String username);
}

package com.cintvv.springbootv1_1.dao;

import com.cintvv.springbootv1_1.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {
    void saveUser(User user);

    void removeUserById(int id);

    void updateUser(User user);

    List<User> getAllUsers();

    User getUserById(int id);

    User getUserByUsername(String username);
}

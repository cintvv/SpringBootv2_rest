package com.cintvv.springbootv2.services;

import com.cintvv.springbootv2.dao.UserDao;
import com.cintvv.springbootv2.model.Role;
import com.cintvv.springbootv2.model.User;
import com.cintvv.springbootv2.dao.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserDao userDao;

    public UserServiceImpl(UserRepository userRepository, UserDao userDao) {
        this.userRepository = userRepository;
        this.userDao = userDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User" + username + " not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthorities(user.getRoles()));
    }

    public Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @Override
    public void saveUser(User user) {
        userDao.saveUser(user);
    }

    @Override
    public void removeUserById(int id) {
        userDao.removeUserById(id);
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public User getUserById(int id) {
        return userDao.getUserById(id);
    }

    @Override
    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    @Override
    public void removeUserByUsername(String username) {
        userDao.removeUserByUsername(username);
    }

    @Override
    public String getRolesByUsername(String username) {
        return userDao.getRolesByUsername(username);
    }


}

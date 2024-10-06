package com.cintvv.springbootv2.services;

import com.cintvv.springbootv2.model.Role;
import com.cintvv.springbootv2.model.User;
import jakarta.transaction.Transactional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public interface UserService extends UserDetailsService {

    @Override
    @Transactional
    UserDetails loadUserByUsername(String username);

    Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles);

    void saveUser(User user);

    void removeUserById(int id);

    void updateUser(User user);

    List<User> getAllUsers();

    User getUserById(int id);

    User getUserByUsername(String username);

    void removeUserByUsername(String username);

    String getRolesByUsername(String username);
}

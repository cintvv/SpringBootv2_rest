package com.cintvv.springbootv2.controllers;

import com.cintvv.springbootv2.dao.RoleRepository;
import com.cintvv.springbootv2.model.Role;
import com.cintvv.springbootv2.model.User;
import com.cintvv.springbootv2.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RestMainController {
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public RestMainController(UserService userService, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/admin/list")
    public ResponseEntity<List<User>> admin() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/user/list")
    public ResponseEntity<List<User>> user(Principal principal) {
        User currentUser = userService.getUserByUsername(principal.getName());
        return ResponseEntity.ok(Collections.singletonList(currentUser));
    }

    @PostMapping("/admin/add")
    public ResponseEntity<?> addAdmin(@RequestBody User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.saveUser(user);
            return ResponseEntity.ok("Пользователь успешно добавлен");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка при добавлении пользователя: " + e.getMessage());
        }
    }

    @DeleteMapping("/admin/{username}")
    public ResponseEntity<?> removeUser(@PathVariable String username) {
        if (userService.getUserByUsername(username) == null) {
            return ResponseEntity.notFound().build();
        } else {
            userService.removeUserByUsername(username);
            return ResponseEntity.ok().build();
        }
    }

    @PutMapping("/admin/update")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        User findUser = userService.getUserByUsername(user.getUsername());
        findUser.setId(userService.getUserByUsername(user.getUsername()).getId());
        findUser.setAvatar(user.getAvatar());
        findUser.setEmail(user.getEmail());
        findUser.setPhone(user.getPhone());
        findUser.setRoles(user.getRoles());
        findUser.setUsername(user.getUsername());
        if (user.getPassword().isEmpty()) {
            user.setPassword(userService.getUserByUsername(user.getUsername()).getPassword());
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        findUser.setRoles(user.getRoles());
        userService.updateUser(findUser);
        return ResponseEntity.ok(findUser);
    }

    @GetMapping("/roles")
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}

package com.cintvv.springbootv2.controllers;

import com.cintvv.springbootv2.dao.RoleRepository;
import com.cintvv.springbootv2.model.Role;
import com.cintvv.springbootv2.model.User;
import com.cintvv.springbootv2.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
public class MainController {
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public MainController(UserService userService, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

//    @RequestMapping("/")
//    public void loginPageRedirect(HttpServletRequest request, HttpServletResponse response, Authentication authResult) throws IOException {
//        String role =  authResult.getAuthorities().toString();
//        if(role.contains("ROLE_ADMIN")){
//            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/admin"));
//        }
//        else if(role.contains("ROLE_USER")) {
//            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/user"));
//        }
//    }

    @GetMapping({ "/admin"})
    public String showAllUsersFromAdmin(Model model, HttpServletRequest request) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("currentPath", request.getRequestURI());
        return "list";
    }

    @GetMapping({ "/user"})
    public String showAllUsersFromUser(Model model, Principal principal, HttpServletRequest request) {
        String username = principal.getName();
        model.addAttribute("users", userService.getUserByUsername(username));
        model.addAttribute("currentPath", request.getRequestURI());
        return "list";
    }

    @GetMapping("/admin/users/new")
    public ModelAndView newUser() {
        User user = new User();
        ModelAndView mav = new ModelAndView("add");
        mav.addObject("user", user);
        List<Role> roles = (List<Role>) roleRepository.findAll();
        mav.addObject("allRoles", roles);
        return mav;
    }

    @RequestMapping(value = "/admin/users/save", method = RequestMethod.POST)
    public String saveUser(@ModelAttribute("user") User user) {
        User newUser = new User();
        String pwd = passwordEncoder.encode(user.getPassword());
        newUser.setPassword(pwd);
        userService.saveUser(newUser);
        return "redirect:/admin";
    }


    @GetMapping("/admin/users/edit/{username}")
    public ModelAndView editUser(@PathVariable(name = "username") String username) {
        User user = userService.getUserByUsername(username);
        ModelAndView mav = new ModelAndView("edit");
        mav.addObject("user", user);
        List<Role> roles = (List<Role>) roleRepository.findAll();
        mav.addObject("allRoles", roles);
        return mav;
    }

    @RequestMapping(value = "/admin/users/update/{username}", method = RequestMethod.POST)
    public String updateUserAfterWriting(@PathVariable String username, @ModelAttribute("user") User user) {
        User user1 = userService.getUserByUsername(user.getUsername());
        user1.setId(userService.getUserByUsername(username).getId());
        user1.setEmail(user.getEmail());
        user1.setAvatar(user.getAvatar());
        user1.setPhone(user.getPhone());
        if (user.getPassword().isEmpty()) {
            user1.setPassword(userService.getUserByUsername(username).getPassword());
        } else {
            user1.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userService.updateUser(user1);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/admin/users/remove/{username}")
    public String removeUser(@PathVariable String username) {
        userService.removeUserByUsername(username);
        return "redirect:/admin";
    }
}

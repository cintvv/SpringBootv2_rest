package com.cintvv.springbootv2.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {
//    private final UserService userService;
//    private final RoleRepository roleRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    public MainController(UserService userService, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
//        this.passwordEncoder = passwordEncoder;
//        this.roleRepository = roleRepository;
//        this.userService = userService;
//    }

    @GetMapping({ "/admin"})
    public String showAllUsersFromAdmin() {
        return "list";
    }

    @GetMapping({ "/user"})
    public String showAllUsersFromUser() {
        return "user";
    }
}

//    @GetMapping("/admin/users/new")
//    public ModelAndView newUser() {
//        User user = new User();
//        ModelAndView mav = new ModelAndView("add");
//        mav.addObject("user", user);
//        List<Role> roles = (List<Role>) roleRepository.findAll();
//        mav.addObject("allRoles", roles);
//        return mav;
//    }
//
//    @RequestMapping(value = "/admin/users/save", method = RequestMethod.POST)
//    public String saveUser(@ModelAttribute("user") User user) {
//        User newUser = new User();
//        String pwd = passwordEncoder.encode(user.getPassword());
//        newUser.setUsername(user.getUsername());
//        newUser.setPassword(pwd);
//        newUser.setRoles(user.getRoles());
//        newUser.setEmail(user.getEmail());
//        newUser.setPhone(user.getPhone());
//        newUser.setAvatar(user.getAvatar());
//        userService.saveUser(newUser);
//        return "redirect:/admin";
//    }
//
//
//    @GetMapping("/admin/users/edit/{username}")
//    public ModelAndView editUser(@PathVariable(name = "username") String username) {
//        User user = userService.getUserByUsername(username);
//        ModelAndView mav = new ModelAndView("edit");
//        mav.addObject("user", user);
//        List<Role> roles = (List<Role>) roleRepository.findAll();
//        mav.addObject("allRoles", roles);
//        List<Role> userRoles = (List<Role>) user.getRoles();
//        mav.addObject("userRoles", userRoles);
//        return mav;
//    }
//
//    @RequestMapping(value = "/admin/users/update/{username}", method = RequestMethod.POST)
//    public String updateUserAfterWriting(@PathVariable String username, @ModelAttribute("user") User user, Model model) {
//        User user1 = userService.getUserByUsername(user.getUsername());
//        user1.setId(userService.getUserByUsername(username).getId());
//        user1.setEmail(user.getEmail());
//        user1.setAvatar(user.getAvatar());
//        user1.setPhone(user.getPhone());
//        user1.setRoles(user.getRoles());
//        if (user.getPassword().isEmpty()) {
//            user1.setPassword(userService.getUserByUsername(username).getPassword());
//        } else {
//            user1.setPassword(passwordEncoder.encode(user.getPassword()));
//        }
//        List<Role> roles = (List<Role>) roleRepository.findAll();
//        model.addAttribute("allOfRoles", roles);
//        userService.updateUser(user1);
//        return "redirect:/admin";
//    }
//
//    @RequestMapping(value = "/admin/users/remove/{username}")
//    public String removeUser(@PathVariable String username) {
//        userService.removeUserByUsername(username);
//        return "redirect:/admin";
//    }
package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.security.UserDetailsImpl;
import ru.itmentor.spring.boot_security.demo.service.RoleService;
import ru.itmentor.spring.boot_security.demo.service.UserService;

import java.util.Set;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping
    public String showAllUsers(@ModelAttribute("user") User user, Model model) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("authenticatedUser", userDetails.getUser());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin";
    }

//    @GetMapping("/{id}")
//    public String showUser(Model model, @PathVariable("id") long id) {
//        model.addAttribute("user", userService.getUser(id));
//        return "user";
//    }

    @PostMapping("/new")
    public String newUser(@ModelAttribute("user") User user,
                          @RequestParam("form-selected-roles") Set<Role> selectedRoles) {
        user.setRoles(selectedRoles);
        userService.createUser(user);
        return "redirect:/admin";
    }

//    @PostMapping
//    public String createUser(@ModelAttribute("user") User user) {
//        userService.createUser(user);
//        return "redirect:/admin/users";
//    }

    @PatchMapping("/{id}/edit")
    public String editUser(@ModelAttribute("user") User user, @PathVariable("id") Long id,
                           @RequestParam("form-selected-roles") Set<Role> selectedRoles) {
        user.setRoles(selectedRoles);
        userService.updateUser(user, id);
        return "redirect:/admin";
    }

//    @PatchMapping("/{id}")
//    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") long id) {
//        userService.updateUser(user, id);
//        return "redirect:/admin/users";
//    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}

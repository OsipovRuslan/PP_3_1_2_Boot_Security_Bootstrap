package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

@Controller
public class MainController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public MainController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/main")
    public String adminPage(@ModelAttribute("newUser") User user, Model model, Authentication authentication) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("rolesUser", AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
        model.addAttribute("principal", (User) authentication.getPrincipal());
        model.addAttribute("allRoles", roleService.findAll());
        return "mainPage";
    }
}

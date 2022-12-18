package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final UserValidator userValidator;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, UserValidator userValidator, RoleService roleService) {
        this.userService = userService;
        this.userValidator = userValidator;
        this.roleService = roleService;
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("newUser") User user/*, BindingResult  bindingResult*/) {
//        userValidator.validate(user, bindingResult);
//
//        if (bindingResult.hasErrors())
//            return "mainPage";

        userService.save(user);
        return "redirect:/main";
    }


    @PatchMapping("/users/edit/{id}")
    public String edit(@ModelAttribute("user") User user//, BindingResult bindingResult, RedirectAttributes redirectAttributes
            /*@PathVariable("id") Long id*/) {

//        userValidator.validate(user, bindingResult);
//        if(bindingResult.hasErrors()) {
//            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//            redirectAttributes.addFlashAttribute("bindingResult", bindingResult);
//            return "redirect:/admin/users";
//        }
        userService.save(user);
        return "redirect:/main";
    }

    @DeleteMapping("/users/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/main";
    }
}

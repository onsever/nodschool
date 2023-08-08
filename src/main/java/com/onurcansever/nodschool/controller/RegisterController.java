package com.onurcansever.nodschool.controller;

import com.onurcansever.nodschool.model.User;
import com.onurcansever.nodschool.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RegisterController {

    private final UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String displayRegisterPage(Model model) {
        model.addAttribute("user", new User());
        return "register.html";
    }

    // createUser
    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    public String createUser(@Valid @ModelAttribute("user") User user, Errors errors) {
        if (errors.hasErrors()) {
            return "register.html";
        }

        boolean isSaved = this.userService.createNewUser(user);

        if (isSaved) {
            return "redirect:/login?register=true";
        }

        return "register.html";
    }
}

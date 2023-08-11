package com.onurcansever.nodschool.controller;

import com.onurcansever.nodschool.model.User;
import com.onurcansever.nodschool.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Slf4j
@Controller
public class DashboardController {

    private final UserRepository userRepository;

    @Autowired
    public DashboardController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String displayDashboard(Model model, Authentication authentication, HttpSession session) {
        User user = this.userRepository.findByEmail(authentication.getName()).orElse(null);

        model.addAttribute("username", user.getName());
        model.addAttribute("roles", authentication.getAuthorities().toString());

        if (user.getSchoolClass() != null && user.getSchoolClass().getName() != null) {
            model.addAttribute("enrolledClass", user.getSchoolClass().getName());
        }

        session.setAttribute("loggedInUser", user);

        return "dashboard.html";
    }
}

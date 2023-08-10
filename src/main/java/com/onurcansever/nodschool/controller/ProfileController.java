package com.onurcansever.nodschool.controller;

import com.onurcansever.nodschool.model.Address;
import com.onurcansever.nodschool.model.Profile;
import com.onurcansever.nodschool.model.User;
import com.onurcansever.nodschool.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class ProfileController {

    private final UserRepository userRepository;

    @Autowired
    public ProfileController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/displayProfile", method = RequestMethod.GET)
    public ModelAndView displayProfilePage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        Profile profile = new Profile();
        profile.setName(user.getName());
        profile.setMobileNumber(user.getMobileNumber());
        profile.setEmail(user.getEmail());

        if (user.getAddress() != null && user.getAddress().getAddressId() > 0) {
            profile.setAddress1(user.getAddress().getAddress1());
            profile.setAddress2(user.getAddress().getAddress2());
            profile.setCity(user.getAddress().getCity());
            profile.setState(user.getAddress().getState());
            profile.setZipCode(user.getAddress().getZipCode());
        }

        ModelAndView modelAndView = new ModelAndView("profile.html");
        modelAndView.addObject("profile", profile);

        return modelAndView;
    }

    @RequestMapping(value = "/updateProfile", method = RequestMethod.POST)
    public String updateProfile(@Valid @ModelAttribute("profile") Profile profile, Errors errors, HttpSession session) { // @RequestParam Profile

        if (errors.hasErrors()) {
            return "profile.html";
        }

        User user = (User) session.getAttribute("loggedInUser");
        user.setName(profile.getName());
        user.setEmail(profile.getEmail());
        user.setMobileNumber(profile.getMobileNumber());

        // If there is no address information, create a new Address.
        if (user.getAddress() == null || !(user.getAddress().getAddressId() > 0)) {
            user.setAddress(new Address());
        }

        user.getAddress().setAddress1(profile.getAddress1());
        user.getAddress().setAddress2(profile.getAddress2());
        user.getAddress().setCity(profile.getCity());
        user.getAddress().setState(profile.getState());
        user.getAddress().setZipCode(profile.getZipCode());

        User savedUser = this.userRepository.save(user);
        session.setAttribute("loggedInUser", savedUser);

        return "redirect:displayProfile";
    }

}

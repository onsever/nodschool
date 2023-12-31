package com.onurcansever.nodschool.controller;

import com.onurcansever.nodschool.model.Contact;
import com.onurcansever.nodschool.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ContactController {

    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @RequestMapping("/contact")
    public String displayContactPage(Model model) {
        model.addAttribute("contact", new Contact());
        return "contact.html";
    }

    @RequestMapping(value = "/sendContactForm", method = RequestMethod.POST)
    public String sendContactForm(@Valid @ModelAttribute("contact") Contact contact, Errors errors) { // @RequestParam
        if (errors.hasErrors()) {
            return "contact.html"; // Displaying contact page again along with errors.
        }

        this.contactService.saveContactForm(contact);

        return "redirect:/contact"; // Invoking contact action again.
    }

    @RequestMapping(value = "/displayMessages", method = RequestMethod.GET)
    public ModelAndView displayMessages(Model model) {
        List<Contact> contactMessages = this.contactService.findMessagesWithOpenStatus();
        ModelAndView modelAndView = new ModelAndView("messages.html");
        modelAndView.addObject("contactMessages", contactMessages);

        return modelAndView;
    }

    @RequestMapping(value = "/closeMessage", method = RequestMethod.GET)
    public String closeMessage(@RequestParam int id) {
        this.contactService.updateMessageStatus(id);
        return "redirect:/displayMessages";
    }

}

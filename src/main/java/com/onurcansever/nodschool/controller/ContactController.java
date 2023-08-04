package com.onurcansever.nodschool.controller;

import com.onurcansever.nodschool.model.Contact;
import com.onurcansever.nodschool.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ContactController {

    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @RequestMapping("/contact")
    public String displayContactPage() {
        return "contact.html";
    }

    @RequestMapping(value = "/sendContactForm", method = RequestMethod.POST)
    public ModelAndView sendContactForm(Contact contact) { // @RequestParam
        this.contactService.saveContactForm(contact);

        return new ModelAndView("redirect:/contact");
    }

}

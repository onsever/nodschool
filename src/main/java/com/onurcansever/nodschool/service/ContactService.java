package com.onurcansever.nodschool.service;

import com.onurcansever.nodschool.model.Contact;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    /**
     * Save contact details to the database.
     * @param contact
     * @return boolean
     */
    public boolean saveContactForm(Contact contact) {
        System.out.println(contact);
        return true;
    }
}

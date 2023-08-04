package com.onurcansever.nodschool.service;

import com.onurcansever.nodschool.model.Contact;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ContactService {

    /**
     * Save contact details to the database.
     * @param contact
     * @return boolean
     */
    public boolean saveContactForm(Contact contact) {
        log.info(contact.toString());
        return true;
    }
}

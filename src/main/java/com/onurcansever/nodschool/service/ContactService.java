package com.onurcansever.nodschool.service;

import com.onurcansever.nodschool.model.Contact;
import com.onurcansever.nodschool.repository.ContactRepository;
import com.onurcansever.nodschool.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ContactService {

    private final ContactRepository contactRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    /**
     * Save contact details to the database.
     * @param contact
     * @return boolean
     */
    public boolean saveContactForm(Contact contact) {
        boolean isDataSaved = false;

        contact.setStatus(Constants.OPEN);
        contact.setCreatedBy(Constants.ANONYMOUS);
        contact.setCreatedAt(LocalDateTime.now());

        Contact savedContact = this.contactRepository.save(contact);

        if (savedContact.getContactId() > 0) {
            isDataSaved = true;
        }

        return isDataSaved;
    }

    public List<Contact> findMessagesWithOpenStatus() {
        return this.contactRepository.findByStatus(Constants.OPEN);
    }

    public boolean updateMessageStatus(int contactId, String updatedBy) {
        boolean isUpdated = false;

        Optional<Contact> contact = this.contactRepository.findById(contactId);

        contact.ifPresent(cObj -> {
            cObj.setStatus(Constants.CLOSE);
            cObj.setUpdatedBy(updatedBy);
            cObj.setUpdatedAt(LocalDateTime.now());
        });

        Contact updatedContact = this.contactRepository.save(contact.get());

        if (updatedContact.getUpdatedBy() != null) {
            isUpdated = true;
        }

        return isUpdated;
    }
}

package app.code.verse.adapter.in.builder;

import app.code.verse.adapter.in.validators.EmergencyContactValidator;
import app.code.verse.adapter.in.validators.PersonValidator;
import app.code.verse.domain.model.EmergencyContact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmergencyContactBuilder {

    @Autowired
    private EmergencyContactValidator emergencyContactValidator;

    @Autowired
    private PersonValidator personValidator;

    public EmergencyContact build(String name, String kinship, String phoneNumber) throws Exception {
        EmergencyContact emergencyContact = new EmergencyContact();
        emergencyContact.setName(personValidator.nameValidator(name));
        emergencyContact.setKinship(emergencyContactValidator.kidnipValidator(kinship));
        emergencyContact.setPhoneNumber(personValidator.phoneNumberValidator(phoneNumber));
        return emergencyContact;
    }

    public EmergencyContact update(EmergencyContact emergencyContact, String name, String kinship, String phoneNumber) throws Exception {
        emergencyContact.setName(personValidator.nameValidator(name));
        emergencyContact.setKinship(emergencyContactValidator.kidnipValidator(kinship));
        emergencyContact.setPhoneNumber(personValidator.phoneNumberValidator(phoneNumber));
        return emergencyContact;
    }
}

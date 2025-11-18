package app.code.verse.infrastructure.persistence.mapper;

import app.code.verse.domain.model.EmergencyContact;
import app.code.verse.infrastructure.persistence.entities.EmergencyContactEntity;

public class EmergencyContactMapper {

    public static EmergencyContactEntity toEntity(EmergencyContact emergencyContact) {
        if (emergencyContact == null) return null;
        EmergencyContactEntity entity = new EmergencyContactEntity();
        entity.setName(emergencyContact.getName());
        entity.setKinship(emergencyContact.getKinship());
        entity.setPhoneNumber(emergencyContact.getPhoneNumber());
        return entity;
    }

    public static EmergencyContact toDomain(EmergencyContactEntity entity) {
        if (entity == null) return null;
        EmergencyContact emergencyContact = new EmergencyContact();
        emergencyContact.setName(entity.getName());
        emergencyContact.setKinship(entity.getKinship());
        emergencyContact.setPhoneNumber(entity.getPhoneNumber());
        return emergencyContact;
    }

    public static void partialUpdate(EmergencyContact source, EmergencyContactEntity target) {
        if (source.getName() != null) target.setName(source.getName());
        if (source.getKinship() != null) target.setKinship(source.getKinship());
        if (source.getPhoneNumber() != null) target.setPhoneNumber(source.getPhoneNumber());
    }
}

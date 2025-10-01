package app.code.verse.infrastructure.persistence.mapper;

import app.code.verse.domain.model.Patient;
import app.code.verse.infrastructure.persistence.entities.PatientEntity;

public class PatientMapper {

    public static PatientEntity toEntity(Patient patient) {
        if (patient == null) return null;
        PatientEntity entity = new PatientEntity();
        entity.setId(null);
        entity.setName(patient.getName());
        entity.setIdNumber(patient.getIdNumber());
        entity.setEmail(patient.getEmail());
        entity.setPhoneNumber(patient.getPhoneNumber());
        entity.setBirthDate(patient.getBirthDate());
        entity.setAddress(patient.getAddress());
        entity.setGender(patient.getGender());
        entity.setStatus(true);
        return entity;
    }

    public static Patient toDomain(PatientEntity patientEntity) {
        if (patientEntity == null) return null;
        Patient patient = new Patient();
        patient.setId(patientEntity.getId());
        patient.setName(patientEntity.getName());
        patient.setIdNumber(patientEntity.getIdNumber());
        patient.setEmail(patientEntity.getEmail());
        patient.setPhoneNumber(patientEntity.getPhoneNumber());
        patient.setBirthDate(patientEntity.getBirthDate());
        patient.setAddress(patientEntity.getAddress());
        patient.setGender(patientEntity.getGender());
        return patient;
    }

    public static void partialUpdate(Patient source, PatientEntity patient) {
        if (source.getName() != null) patient.setName(source.getName());
        if (source.getEmail() != null) patient.setEmail(source.getEmail());
        if (source.getPhoneNumber() != null) patient.setPhoneNumber(source.getPhoneNumber());
        if (source.getBirthDate() != null) patient.setBirthDate(source.getBirthDate());
        if (source.getAddress() != null) patient.setAddress(source.getAddress());
        if (source.getGender() != null) patient.setGender(source.getGender());
    }
}

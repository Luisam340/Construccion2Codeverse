package app.code.verse.adapter.out;

import app.code.verse.domain.model.EmergencyContact;
import app.code.verse.domain.ports.EmergencyContactPort;
import app.code.verse.infrastructure.persistence.entities.EmergencyContactEntity;
import app.code.verse.infrastructure.persistence.entities.PatientEntity;
import app.code.verse.infrastructure.persistence.mapper.EmergencyContactMapper;
import app.code.verse.infrastructure.persistence.repository.EmergencyContactRepository;
import app.code.verse.infrastructure.persistence.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmergencyContactAdapter implements EmergencyContactPort {

    @Autowired
    private EmergencyContactRepository repository;

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public void register(String patientIdNumber, EmergencyContact contact) {
        PatientEntity patientEntity = patientRepository.findByIdNumber(patientIdNumber);
        if (patientEntity == null) {
            throw new IllegalArgumentException("Paciente no encontrado");
        }
        EmergencyContactEntity entity = EmergencyContactMapper.toEntity(contact);
        entity.setPatient(patientEntity);
        repository.save(entity);
    }

    @Override
    public void update(String patientIdNumber, EmergencyContact contact) {
        EmergencyContactEntity existing = repository.findByPatientIdNumber(patientIdNumber);
        if (existing == null) {
            throw new IllegalArgumentException("Contacto de emergencia no encontrado");
        }
        EmergencyContactMapper.partialUpdate(contact, existing);
        repository.save(existing);
    }

    @Override
    public void delete(String patientIdNumber) {
        EmergencyContactEntity entity = repository.findByPatientIdNumber(patientIdNumber);
        if (entity != null) {
            repository.delete(entity);
        }
    }

    @Override
    public EmergencyContact findByPatient(String patientIdNumber) {
        EmergencyContactEntity entity = repository.findByPatientIdNumber(patientIdNumber);
        return EmergencyContactMapper.toDomain(entity);
    }
}

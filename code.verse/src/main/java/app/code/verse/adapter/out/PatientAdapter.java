package app.code.verse.adapter.out;

import app.code.verse.domain.model.Patient;
import app.code.verse.domain.ports.PatientPort;
import app.code.verse.infrastructure.persistence.entities.PatientEntity;
import app.code.verse.infrastructure.persistence.mapper.PatientMapper;
import app.code.verse.infrastructure.persistence.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientAdapter implements PatientPort {

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public Patient create(Patient patient) throws Exception {
        PatientEntity entitySave = PatientMapper.toEntity(patient);
        PatientEntity save = patientRepository.save(entitySave);
        return PatientMapper.toDomain(save);
    }

    @Override
    public Patient deleteById(Patient patient) throws Exception {
        PatientEntity patientEntity = patientRepository.findByIdNumber(patient.getIdNumber());
        patientRepository.delete(patientEntity);
        return PatientMapper.toDomain(patientEntity);

    }

    @Override
    public Patient update(Patient patient) throws Exception {
        PatientEntity entityToUpdate = patientRepository.findByIdNumber(patient.getIdNumber()) ;
        PatientMapper.partialUpdate(patient, entityToUpdate);
        PatientEntity save = patientRepository.save(entityToUpdate);
        return PatientMapper.toDomain(save);
    }

    @Override
    public Patient findByIdNumber(String idNumber) {
        PatientEntity patientEntity = patientRepository.findByIdNumber(idNumber);
        return PatientMapper.toDomain(patientEntity);
    }

    @Override
    public List<Patient> findByNameContainingIgnoreCase(String name) throws Exception {
        List<PatientEntity> patientEntity = patientRepository.findByNameContainingIgnoreCase(name);
        return patientEntity.stream()
                .filter(p -> p.getName() != null &&
                        p.getName().toLowerCase().contains(name.toLowerCase()))
                .map(PatientMapper::toDomain)
                .toList();
    }

    @Override
    public List<Patient> findAll() {
        return patientRepository.findAll()
                .stream()
                .map(PatientMapper::toDomain)
                .collect(Collectors.toList());
    }
}

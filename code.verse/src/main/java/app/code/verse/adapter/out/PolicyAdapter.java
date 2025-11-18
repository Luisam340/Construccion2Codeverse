package app.code.verse.adapter.out;

import app.code.verse.domain.model.Policy;
import app.code.verse.domain.ports.PolicyPort;
import app.code.verse.infrastructure.persistence.entities.PatientEntity;
import app.code.verse.infrastructure.persistence.entities.PolicyEntity;
import app.code.verse.infrastructure.persistence.mapper.PolicyMapper;
import app.code.verse.infrastructure.persistence.repository.PatientRepository;
import app.code.verse.infrastructure.persistence.repository.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PolicyAdapter implements PolicyPort {

    @Autowired
    private PolicyRepository repository;

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public void register(String patientIdNumber, Policy policy) {
        PatientEntity patientEntity = patientRepository.findByIdNumber(patientIdNumber);
        if (patientEntity == null) {
            throw new IllegalArgumentException("Paciente no encontrado");
        }
        PolicyEntity entity = PolicyMapper.toEntity(policy);
        entity.setPatient(patientEntity);
        repository.save(entity);
    }

    @Override
    public void update(String patientIdNumber, Policy policy) {
        PolicyEntity existing = repository.findByPatientIdNumber(patientIdNumber);
        if (existing == null) {
            throw new IllegalArgumentException("Póliza no encontrada");
        }
        PolicyMapper.partialUpdate(policy, existing);
        repository.save(existing);
    }

    @Override
    public void delete(String patientIdNumber) {
        PolicyEntity entity = repository.findByPatientIdNumber(patientIdNumber);
        if (entity != null) {
            repository.delete(entity);
        }
    }

    @Override
    public Policy findByPatient(String patientIdNumber) {
        PolicyEntity entity = repository.findByPatientIdNumber(patientIdNumber);
        return PolicyMapper.toDomain(entity);
    }

    @Override
    public Policy findPolicyById(String policyIdNumber) {
        PolicyEntity entity = repository.findByPolicyNumber(policyIdNumber);
        return PolicyMapper.toDomain(entity);
    }
}

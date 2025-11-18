package app.code.verse.infrastructure.persistence.repository;

import app.code.verse.infrastructure.persistence.entities.EmergencyContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmergencyContactRepository extends JpaRepository<EmergencyContactEntity, Long> {
    public EmergencyContactEntity findByPatientId(Long patientId);
    public EmergencyContactEntity findByPatientIdNumber(String idNumber);
}

package app.code.verse.infrastructure.persistence.repository;

import app.code.verse.infrastructure.persistence.entities.VitalSignEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VitalSignRepository extends JpaRepository<VitalSignEntity, Long> {
    public List<VitalSignEntity> findByVisitRecordId(Long visitRecordId);
    public List<VitalSignEntity> findByPatientId(String patientId);
}

package app.code.verse.infrastructure.persistence.repository;

import app.code.verse.infrastructure.persistence.entities.MedicineAdministrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicineAdministrationRepository extends JpaRepository<MedicineAdministrationEntity, Long> {
    public List<MedicineAdministrationEntity> findByVisitRecordId(Long visitRecordId);
    public List<MedicineAdministrationEntity> findByPatientId(String patientId);
    public List<MedicineAdministrationEntity> findByOrderNumber(String orderNumber);
}

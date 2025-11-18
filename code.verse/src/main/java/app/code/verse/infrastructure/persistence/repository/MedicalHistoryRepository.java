package app.code.verse.infrastructure.persistence.repository;

import app.code.verse.infrastructure.persistence.entities.MedicalHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MedicalHistoryRepository extends JpaRepository<MedicalHistoryEntity, Long> {
    public List<MedicalHistoryEntity> findByPatientId(String patientId);
    public List<MedicalHistoryEntity> findByPatientIdAndVisitDate(String patientId, LocalDate visitDate);
    public List<MedicalHistoryEntity> findByDoctorId(String doctorId);
}

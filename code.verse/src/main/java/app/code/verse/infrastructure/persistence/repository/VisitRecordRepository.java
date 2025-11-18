package app.code.verse.infrastructure.persistence.repository;

import app.code.verse.infrastructure.persistence.entities.VisitRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitRecordRepository extends JpaRepository<VisitRecordEntity, Long> {
    public List<VisitRecordEntity> findByPatientId(String patientId);
    public List<VisitRecordEntity> findByDoctorId(String doctorId);
}

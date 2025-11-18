package app.code.verse.infrastructure.persistence.repository;

import app.code.verse.infrastructure.persistence.entities.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Long> {
    public List<InvoiceEntity> findByPatientId(String patientId);
    @Query("SELECT COALESCE(SUM(i.copayAmount), 0.0) FROM InvoiceEntity i WHERE i.patientId = :patientId AND YEAR(i.issueDate) = :year")
    public Double sumCopayByPatientIdAndYear(@Param("patientId") String patientId, @Param("year") int year);
    public List<InvoiceEntity> findByPatientIdAndIssueDateBetween(String patientId, LocalDate startDate, LocalDate endDate);
}

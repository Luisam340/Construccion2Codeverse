package app.code.verse.infrastructure.persistence.repository;

import app.code.verse.infrastructure.persistence.entities.DiagnosticTestInventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiagnosticTestInventoryRepository extends JpaRepository<DiagnosticTestInventoryEntity, String> {
    public DiagnosticTestInventoryEntity findByIdExam(String idExam);
    public List<DiagnosticTestInventoryEntity> findByNameContainingIgnoreCase(String name);
    public List<DiagnosticTestInventoryEntity> findAll();
}

package app.code.verse.infrastructure.persistence.repository;

import app.code.verse.infrastructure.persistence.entities.ProcedureInventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcedureInventoryRepository extends JpaRepository<ProcedureInventoryEntity, String> {
    public ProcedureInventoryEntity findByIdProcedure(String idProcedure);
    public List<ProcedureInventoryEntity> findByNameContainingIgnoreCase(String name);
    public List<ProcedureInventoryEntity> findAll();
}

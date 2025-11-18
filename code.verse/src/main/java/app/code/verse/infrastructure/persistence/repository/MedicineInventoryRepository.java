package app.code.verse.infrastructure.persistence.repository;

import app.code.verse.infrastructure.persistence.entities.MedicineInventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicineInventoryRepository extends JpaRepository<MedicineInventoryEntity, String> {
    public MedicineInventoryEntity findByIdMedicine(String idMedicine);
    public List<MedicineInventoryEntity> findByNameContainingIgnoreCase(String name);
    public List<MedicineInventoryEntity> findAll();
}

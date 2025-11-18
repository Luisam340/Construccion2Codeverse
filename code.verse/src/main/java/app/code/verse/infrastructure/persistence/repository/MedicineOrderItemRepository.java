package app.code.verse.infrastructure.persistence.repository;

import app.code.verse.infrastructure.persistence.entities.MedicineOrderItemEntity;
import app.code.verse.infrastructure.persistence.entities.OrderItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicineOrderItemRepository extends JpaRepository<MedicineOrderItemEntity, OrderItemId> {
    public List<MedicineOrderItemEntity> findByOrderNumber(String orderNumber);
}

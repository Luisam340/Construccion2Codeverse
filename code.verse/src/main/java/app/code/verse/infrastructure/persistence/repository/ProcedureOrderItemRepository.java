package app.code.verse.infrastructure.persistence.repository;

import app.code.verse.infrastructure.persistence.entities.ProcedureOrderItemEntity;
import app.code.verse.infrastructure.persistence.entities.OrderItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcedureOrderItemRepository extends JpaRepository<ProcedureOrderItemEntity, OrderItemId> {
    public List<ProcedureOrderItemEntity> findByOrderNumber(String orderNumber);
}

package app.code.verse.infrastructure.persistence.repository;

import app.code.verse.infrastructure.persistence.entities.DiagnosticTestOrderItemEntity;
import app.code.verse.infrastructure.persistence.entities.OrderItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiagnosticTestOrderItemRepository extends JpaRepository<DiagnosticTestOrderItemEntity, OrderItemId> {
    public List<DiagnosticTestOrderItemEntity> findByOrderNumber(String orderNumber);
}

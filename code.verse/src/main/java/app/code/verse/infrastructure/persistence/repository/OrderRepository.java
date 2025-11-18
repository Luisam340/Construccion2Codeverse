package app.code.verse.infrastructure.persistence.repository;

import app.code.verse.infrastructure.persistence.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, String> {
    public OrderEntity findByOrderNumber(String orderNumber);
    public boolean existsByOrderNumber(String orderNumber);
    public List<OrderEntity> findByPatientId(String patientId);
    public List<OrderEntity> findByDoctorId(String doctorId);
    public List<OrderEntity> findAll();
}

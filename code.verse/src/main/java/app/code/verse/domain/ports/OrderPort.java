package app.code.verse.domain.ports;

import app.code.verse.domain.model.Order;

import java.util.List;

public interface OrderPort {
    Order save(Order order) throws Exception;
    Order findByOrderNumber(String orderNumber);
    boolean existsByOrderNumber(String orderNumber);
    List<Order> findByPatient(String patientId);
    List<Order> findByDoctor(String doctorId);
    List<Order> findAll();
}

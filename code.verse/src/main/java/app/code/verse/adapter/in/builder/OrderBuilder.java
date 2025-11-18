package app.code.verse.adapter.in.builder;

import app.code.verse.adapter.in.validators.OrderValidator;
import app.code.verse.domain.model.Order;
import app.code.verse.domain.model.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class OrderBuilder {

    @Autowired
    private OrderValidator orderValidator;

    // Construye una orden médica validando número, paciente, doctor e ítems, asignando automáticamente la fecha actual
    public Order build(String orderNumber, String patientId, String doctorId, List<OrderItem> items) throws Exception {
        Order order = new Order();
        order.setOrderNumber(orderValidator.orderNumberValidator(orderNumber));
        order.setPatientId(orderValidator.patientIdValidator(patientId));
        order.setDoctorId(orderValidator.doctorIdValidator(doctorId));
        order.setCreationDate(LocalDate.now());
        order.setItems(orderValidator.itemsValidator(items));
        return order;
    }
}

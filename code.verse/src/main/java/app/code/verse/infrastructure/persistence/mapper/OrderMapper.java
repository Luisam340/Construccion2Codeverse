package app.code.verse.infrastructure.persistence.mapper;

import app.code.verse.domain.model.Order;
import app.code.verse.infrastructure.persistence.entities.OrderEntity;

public class OrderMapper {

    public static OrderEntity toEntity(Order order) {
        if (order == null) return null;
        OrderEntity entity = new OrderEntity();
        entity.setOrderNumber(order.getOrderNumber());
        entity.setPatientId(order.getPatientId());
        entity.setDoctorId(order.getDoctorId());
        entity.setCreationDate(order.getCreationDate());
        return entity;
    }

    public static Order toDomain(OrderEntity entity) {
        if (entity == null) return null;
        Order order = new Order();
        order.setOrderNumber(entity.getOrderNumber());
        order.setPatientId(entity.getPatientId());
        order.setDoctorId(entity.getDoctorId());
        order.setCreationDate(entity.getCreationDate());
        return order;
    }
}

package app.code.verse.infrastructure.persistence.entities;

import java.io.Serializable;
import java.util.Objects;

public class OrderItemId implements Serializable {

    private String orderNumber;
    private Integer itemNumber;

    public OrderItemId() {
    }

    public OrderItemId(String orderNumber, Integer itemNumber) {
        this.orderNumber = orderNumber;
        this.itemNumber = itemNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(Integer itemNumber) {
        this.itemNumber = itemNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItemId that = (OrderItemId) o;
        return Objects.equals(orderNumber, that.orderNumber) && Objects.equals(itemNumber, that.itemNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderNumber, itemNumber);
    }
}

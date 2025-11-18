package app.code.verse.domain.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = MedicineOrderItem.class, name = "MEDICINE"),
    @JsonSubTypes.Type(value = DiagnosticTestOrderItem.class, name = "DIAGNOSTIC_TEST"),
    @JsonSubTypes.Type(value = ProcedureOrderItem.class, name = "PROCEDURE")
})
public abstract class OrderItem {
    protected String orderNumber;
    protected int itemNumber;
    protected double cost;

    public OrderItem() {
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(int itemNumber) {
        this.itemNumber = itemNumber;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
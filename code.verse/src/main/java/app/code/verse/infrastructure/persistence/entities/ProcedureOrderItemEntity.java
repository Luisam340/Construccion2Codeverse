package app.code.verse.infrastructure.persistence.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "procedure_order_item")
@IdClass(OrderItemId.class)
public class ProcedureOrderItemEntity {

    @Id
    @Column(name = "order_number", length = 6)
    private String orderNumber;

    @Id
    @Column(name = "item_number")
    private Integer itemNumber;

    @Column(name = "procedure_name", nullable = false, length = 100)
    private String procedureName;

    @Column(name = "repetitions", nullable = false)
    private Integer repetitions;

    @Column(name = "frequency", nullable = false)
    private String frequency;

    @Column(nullable = false)
    private Double cost;

    @Column(name = "requires_specialist", nullable = false)
    private Boolean requiresSpecialist;

    @Column(name = "specialist_type", length = 100)
    private String specialistType;

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

    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public Integer getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(Integer repetitions) {
        this.repetitions = repetitions;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Boolean getRequiresSpecialist() {
        return requiresSpecialist;
    }

    public void setRequiresSpecialist(Boolean requiresSpecialist) {
        this.requiresSpecialist = requiresSpecialist;
    }

    public String getSpecialistType() {
        return specialistType;
    }

    public void setSpecialistType(String specialistType) {
        this.specialistType = specialistType;
    }
}

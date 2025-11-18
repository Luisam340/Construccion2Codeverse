package app.code.verse.infrastructure.persistence.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "procedure_inventory")
public class ProcedureInventoryEntity {

    @Id
    @Column(name = "id_procedure", length = 50)
    private String idProcedure;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private Double cost;

    public String getIdProcedure() {
        return idProcedure;
    }

    public void setIdProcedure(String idProcedure) {
        this.idProcedure = idProcedure;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
}

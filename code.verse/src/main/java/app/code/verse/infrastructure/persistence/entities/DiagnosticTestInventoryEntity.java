package app.code.verse.infrastructure.persistence.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "diagnostic_test_inventory")
public class DiagnosticTestInventoryEntity {

    @Id
    @Column(name = "id_exam", length = 50)
    private String idExam;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private Double cost;

    public String getIdExam() {
        return idExam;
    }

    public void setIdExam(String idExam) {
        this.idExam = idExam;
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

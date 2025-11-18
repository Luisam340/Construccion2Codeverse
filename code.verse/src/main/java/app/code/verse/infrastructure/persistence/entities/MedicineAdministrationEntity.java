package app.code.verse.infrastructure.persistence.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "medicine_administration")
public class MedicineAdministrationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "visit_record_id", nullable = false)
    private Long visitRecordId;

    @Column(name = "patient_id", nullable = false)
    private String patientId;

    @Column(name = "order_number", nullable = false, length = 6)
    private String orderNumber;

    @Column(name = "item_number", nullable = false)
    private Integer itemNumber;

    @Column(name = "medicine_name", nullable = false, length = 100)
    private String medicineName;

    @Column(name = "dose_administered", nullable = false)
    private String doseAdministered;

    @Column(name = "administered_at", nullable = false)
    private LocalDateTime administeredAt;

    @Column(name = "administered_by", nullable = false)
    private String administeredBy;

    @Column(name = "observations", columnDefinition = "TEXT")
    private String observations;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVisitRecordId() {
        return visitRecordId;
    }

    public void setVisitRecordId(Long visitRecordId) {
        this.visitRecordId = visitRecordId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
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

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getDoseAdministered() {
        return doseAdministered;
    }

    public void setDoseAdministered(String doseAdministered) {
        this.doseAdministered = doseAdministered;
    }

    public LocalDateTime getAdministeredAt() {
        return administeredAt;
    }

    public void setAdministeredAt(LocalDateTime administeredAt) {
        this.administeredAt = administeredAt;
    }

    public String getAdministeredBy() {
        return administeredBy;
    }

    public void setAdministeredBy(String administeredBy) {
        this.administeredBy = administeredBy;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }
}

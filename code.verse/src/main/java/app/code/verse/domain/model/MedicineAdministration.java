package app.code.verse.domain.model;

import java.time.LocalDateTime;

public class MedicineAdministration {
    private Long id;
    private Long visitRecordId;
    private String patientId;
    private String orderNumber;
    private Integer itemNumber;
    private String medicineName;
    private String doseAdministered;
    private LocalDateTime administeredAt;
    private String administeredBy;
    private String observations;

    public MedicineAdministration() {
    }

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
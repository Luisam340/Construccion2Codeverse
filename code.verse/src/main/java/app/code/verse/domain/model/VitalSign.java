package app.code.verse.domain.model;

import java.time.LocalDateTime;

public class VitalSign {
    private Long id;
    private Long visitRecordId;
    private String patientId;
    private String bloodPressure;
    private Double temperature;
    private Integer pulse;
    private Double oxygenLevel;
    private LocalDateTime recordedAt;
    private String recordedBy;

    public VitalSign() {
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

    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Integer getPulse() {
        return pulse;
    }

    public void setPulse(Integer pulse) {
        this.pulse = pulse;
    }

    public Double getOxygenLevel() {
        return oxygenLevel;
    }

    public void setOxygenLevel(Double oxygenLevel) {
        this.oxygenLevel = oxygenLevel;
    }

    public LocalDateTime getRecordedAt() {
        return recordedAt;
    }

    public void setRecordedAt(LocalDateTime recordedAt) {
        this.recordedAt = recordedAt;
    }

    public String getRecordedBy() {
        return recordedBy;
    }

    public void setRecordedBy(String recordedBy) {
        this.recordedBy = recordedBy;
    }
}
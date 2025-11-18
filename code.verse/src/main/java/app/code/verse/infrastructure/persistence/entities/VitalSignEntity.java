package app.code.verse.infrastructure.persistence.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "vital_sign")
public class VitalSignEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "visit_record_id", nullable = false)
    private Long visitRecordId;

    @Column(name = "patient_id", nullable = false)
    private String patientId;

    @Column(name = "blood_pressure", length = 20)
    private String bloodPressure;

    @Column(name = "temperature")
    private Double temperature;

    @Column(name = "pulse")
    private Integer pulse;

    @Column(name = "oxygen_level")
    private Double oxygenLevel;

    @Column(name = "recorded_at", nullable = false)
    private LocalDateTime recordedAt;

    @Column(name = "recorded_by", nullable = false)
    private String recordedBy;

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

package app.code.verse.domain.model;

import java.util.Map;

public class MedicalHistory {
    private String idPatient;
    private Map<String, VisitRecord> records;

    public MedicalHistory() {
    }

    public String getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(String idPatient) {
        this.idPatient = idPatient;
    }

    public Map<String, VisitRecord> getRecords() {
        return records;
    }

    public void setRecords(Map<String, VisitRecord> records) {
        this.records = records;
    }
}
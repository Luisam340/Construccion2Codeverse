package app.code.verse.domain.ports;

import app.code.verse.domain.model.MedicalHistory;
import app.code.verse.domain.model.VisitRecord;

import java.util.List;

public interface MedicalHistoryPort {
    void addVisit(String idPatient, VisitRecord record);
    MedicalHistory getHistory(String idPatient);
    List<MedicalHistory> findByPatient(String idPatient);
    MedicalHistory findByPatientAndDate(String idPatient, String date);
    List<MedicalHistory> findByDoctor(String doctorId);
}

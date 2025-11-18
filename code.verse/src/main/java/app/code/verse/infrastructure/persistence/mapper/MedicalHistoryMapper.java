package app.code.verse.infrastructure.persistence.mapper;

import app.code.verse.domain.model.MedicalHistory;
import app.code.verse.domain.model.VisitRecord;
import app.code.verse.infrastructure.persistence.entities.MedicalHistoryEntity;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MedicalHistoryMapper {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static MedicalHistoryEntity toEntity(VisitRecord visitRecord, String patientId) {
        if (visitRecord == null) return null;
        MedicalHistoryEntity entity = new MedicalHistoryEntity();
        entity.setId(null);
        entity.setPatientId(patientId);
        entity.setVisitDate(visitRecord.getVisitDate());
        entity.setDoctorId(visitRecord.getDoctorId());
        entity.setReason(visitRecord.getReason());
        entity.setSymptoms(visitRecord.getSymptoms());
        entity.setDiagnosis(visitRecord.getDiagnosis());
        return entity;
    }

    public static VisitRecord toVisitRecord(MedicalHistoryEntity entity) {
        if (entity == null) return null;
        VisitRecord visitRecord = new VisitRecord();
        visitRecord.setId(entity.getId());
        visitRecord.setPatientId(entity.getPatientId());
        visitRecord.setVisitDate(entity.getVisitDate());
        visitRecord.setDoctorId(entity.getDoctorId());
        visitRecord.setReason(entity.getReason());
        visitRecord.setSymptoms(entity.getSymptoms());
        visitRecord.setDiagnosis(entity.getDiagnosis());
        return visitRecord;
    }

    public static MedicalHistory toDomain(String patientId, List<MedicalHistoryEntity> entities) {
        MedicalHistory medicalHistory = new MedicalHistory();
        medicalHistory.setIdPatient(patientId);

        Map<String, VisitRecord> records = new HashMap<>();
        if (entities != null) {
            for (MedicalHistoryEntity entity : entities) {
                String dateKey = entity.getVisitDate().format(DATE_FORMATTER);
                VisitRecord visitRecord = toVisitRecord(entity);
                records.put(dateKey, visitRecord);
            }
        }

        medicalHistory.setRecords(records);
        return medicalHistory;
    }
}

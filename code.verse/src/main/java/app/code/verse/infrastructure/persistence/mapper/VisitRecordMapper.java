package app.code.verse.infrastructure.persistence.mapper;

import app.code.verse.domain.model.VisitRecord;
import app.code.verse.infrastructure.persistence.entities.VisitRecordEntity;

public class VisitRecordMapper {

    public static VisitRecordEntity toEntity(VisitRecord visitRecord) {
        if (visitRecord == null) return null;
        VisitRecordEntity entity = new VisitRecordEntity();
        entity.setId(null);
        entity.setPatientId(visitRecord.getPatientId());
        entity.setVisitDate(visitRecord.getVisitDate());
        entity.setDoctorId(visitRecord.getDoctorId());
        entity.setReason(visitRecord.getReason());
        entity.setSymptoms(visitRecord.getSymptoms());
        entity.setDiagnosis(visitRecord.getDiagnosis());
        return entity;
    }

    public static VisitRecord toDomain(VisitRecordEntity entity) {
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
}

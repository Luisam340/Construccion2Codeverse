package app.code.verse.infrastructure.persistence.mapper;

import app.code.verse.domain.model.VitalSign;
import app.code.verse.infrastructure.persistence.entities.VitalSignEntity;

import java.time.LocalDateTime;

public class VitalSignMapper {

    public static VitalSignEntity toEntity(VitalSign vitalSign) {
        if (vitalSign == null) return null;
        VitalSignEntity entity = new VitalSignEntity();
        entity.setId(null);
        entity.setVisitRecordId(vitalSign.getVisitRecordId());
        entity.setPatientId(vitalSign.getPatientId());
        entity.setBloodPressure(vitalSign.getBloodPressure());
        entity.setTemperature(vitalSign.getTemperature());
        entity.setPulse(vitalSign.getPulse());
        entity.setOxygenLevel(vitalSign.getOxygenLevel());
        entity.setRecordedAt(vitalSign.getRecordedAt() != null ? vitalSign.getRecordedAt() : LocalDateTime.now());
        entity.setRecordedBy(vitalSign.getRecordedBy());
        return entity;
    }

    public static VitalSign toDomain(VitalSignEntity entity) {
        if (entity == null) return null;
        VitalSign vitalSign = new VitalSign();
        vitalSign.setId(entity.getId());
        vitalSign.setVisitRecordId(entity.getVisitRecordId());
        vitalSign.setPatientId(entity.getPatientId());
        vitalSign.setBloodPressure(entity.getBloodPressure());
        vitalSign.setTemperature(entity.getTemperature());
        vitalSign.setPulse(entity.getPulse());
        vitalSign.setOxygenLevel(entity.getOxygenLevel());
        vitalSign.setRecordedAt(entity.getRecordedAt());
        vitalSign.setRecordedBy(entity.getRecordedBy());
        return vitalSign;
    }
}

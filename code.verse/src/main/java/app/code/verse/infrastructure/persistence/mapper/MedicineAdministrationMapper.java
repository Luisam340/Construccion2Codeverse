package app.code.verse.infrastructure.persistence.mapper;

import app.code.verse.domain.model.MedicineAdministration;
import app.code.verse.infrastructure.persistence.entities.MedicineAdministrationEntity;

import java.time.LocalDateTime;

public class MedicineAdministrationMapper {

    public static MedicineAdministrationEntity toEntity(MedicineAdministration medicineAdministration) {
        if (medicineAdministration == null) return null;
        MedicineAdministrationEntity entity = new MedicineAdministrationEntity();
        entity.setId(null);
        entity.setVisitRecordId(medicineAdministration.getVisitRecordId());
        entity.setPatientId(medicineAdministration.getPatientId());
        entity.setOrderNumber(medicineAdministration.getOrderNumber());
        entity.setItemNumber(medicineAdministration.getItemNumber());
        entity.setMedicineName(medicineAdministration.getMedicineName());
        entity.setDoseAdministered(medicineAdministration.getDoseAdministered());
        entity.setAdministeredAt(medicineAdministration.getAdministeredAt() != null ? medicineAdministration.getAdministeredAt() : LocalDateTime.now());
        entity.setAdministeredBy(medicineAdministration.getAdministeredBy());
        entity.setObservations(medicineAdministration.getObservations());
        return entity;
    }

    public static MedicineAdministration toDomain(MedicineAdministrationEntity entity) {
        if (entity == null) return null;
        MedicineAdministration medicineAdministration = new MedicineAdministration();
        medicineAdministration.setId(entity.getId());
        medicineAdministration.setVisitRecordId(entity.getVisitRecordId());
        medicineAdministration.setPatientId(entity.getPatientId());
        medicineAdministration.setOrderNumber(entity.getOrderNumber());
        medicineAdministration.setItemNumber(entity.getItemNumber());
        medicineAdministration.setMedicineName(entity.getMedicineName());
        medicineAdministration.setDoseAdministered(entity.getDoseAdministered());
        medicineAdministration.setAdministeredAt(entity.getAdministeredAt());
        medicineAdministration.setAdministeredBy(entity.getAdministeredBy());
        medicineAdministration.setObservations(entity.getObservations());
        return medicineAdministration;
    }
}

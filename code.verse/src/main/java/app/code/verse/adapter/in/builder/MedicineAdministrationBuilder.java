package app.code.verse.adapter.in.builder;

import app.code.verse.adapter.in.validators.SimpleValidator;
import app.code.verse.domain.model.MedicineAdministration;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MedicineAdministrationBuilder {

    private final SimpleValidator validator = new SimpleValidator();

    public MedicineAdministration build(Long visitRecordId, String patientId, String orderNumber, Integer itemNumber, String medicineName, String doseAdministered, String administeredBy, String observations) throws Exception {
        MedicineAdministration medicineAdmin = new MedicineAdministration();
        medicineAdmin.setVisitRecordId(visitRecordId);
        medicineAdmin.setPatientId(validator.stringValidator(patientId, "ID de paciente"));
        medicineAdmin.setOrderNumber(validator.stringValidator(orderNumber, "número de orden"));
        medicineAdmin.setItemNumber(itemNumber);
        medicineAdmin.setMedicineName(validator.stringValidator(medicineName, "nombre de medicamento"));
        medicineAdmin.setDoseAdministered(validator.stringValidator(doseAdministered, "dosis administrada"));
        medicineAdmin.setAdministeredAt(LocalDateTime.now());
        medicineAdmin.setAdministeredBy(validator.stringValidator(administeredBy, "ID de enfermera"));
        medicineAdmin.setObservations(observations);
        return medicineAdmin;
    }

    public MedicineAdministration update(MedicineAdministration medicineAdmin, String doseAdministered, String observations) {
        medicineAdmin.setDoseAdministered(doseAdministered);
        medicineAdmin.setObservations(observations);
        return medicineAdmin;
    }
}

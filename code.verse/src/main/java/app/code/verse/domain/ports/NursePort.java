package app.code.verse.domain.ports;

import app.code.verse.domain.model.MedicineAdministration;
import app.code.verse.domain.model.VitalSign;

import java.util.List;

public interface NursePort {
    VitalSign registerVitalSign(VitalSign vitalSign) throws Exception;
    List<VitalSign> findVitalSignsByPatient(String patientId);
    List<VitalSign> findVitalSignsByVisitRecord(Long visitRecordId);
    MedicineAdministration registerMedicationAdministration(MedicineAdministration medicineAdministration) throws Exception;
    List<MedicineAdministration> findMedicineAdministrationsByPatient(String patientId);
    List<MedicineAdministration> findMedicineAdministrationsByVisitRecord(Long visitRecordId);
    List<MedicineAdministration> findMedicineAdministrationsByOrder(String orderNumber);
}

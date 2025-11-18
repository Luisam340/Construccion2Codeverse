package app.code.verse.domain.services;

import app.code.verse.domain.model.MedicineAdministration;
import app.code.verse.domain.ports.NursePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterMedicineAdministration {

    @Autowired
    private NursePort nursePort;

    // Registra la administración de un medicamento a un paciente por una enfermera
    public void register(MedicineAdministration medicineAdministration) throws Exception {
        if (medicineAdministration == null) {
            throw new IllegalArgumentException("La administración de medicamento no puede ser nula");
        }
        if (medicineAdministration.getOrderNumber() == null || medicineAdministration.getOrderNumber().isEmpty()) {
            throw new IllegalArgumentException("El número de orden es requerido");
        }
        if (medicineAdministration.getPatientId() == null || medicineAdministration.getPatientId().isEmpty()) {
            throw new IllegalArgumentException("El ID del paciente es requerido");
        }
        if (medicineAdministration.getMedicineName() == null || medicineAdministration.getMedicineName().isEmpty()) {
            throw new IllegalArgumentException("El nombre del medicamento es requerido");
        }

        nursePort.registerMedicationAdministration(medicineAdministration);
    }
}

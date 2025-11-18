package app.code.verse.domain.services;

import app.code.verse.domain.model.MedicalHistory;
import app.code.verse.domain.ports.MedicalHistoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateMedicalHistory {

    @Autowired
    private MedicalHistoryPort medicalHistoryPort;

    // Crea una historia clínica para un paciente registrando sus visitas médicas
    public void create(MedicalHistory medicalHistory) throws Exception {
        if (medicalHistory == null) {
            throw new IllegalArgumentException("La historia clínica no puede ser nula");
        }
        if (medicalHistory.getIdPatient() == null || medicalHistory.getIdPatient().isEmpty()) {
            throw new IllegalArgumentException("El ID del paciente es requerido");
        }

        if (medicalHistory.getRecords() != null && !medicalHistory.getRecords().isEmpty()) {
            for (String date : medicalHistory.getRecords().keySet()) {
                medicalHistoryPort.addVisit(medicalHistory.getIdPatient(), medicalHistory.getRecords().get(date));
            }
        }
    }
}

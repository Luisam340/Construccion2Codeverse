package app.code.verse.domain.services;

import app.code.verse.domain.model.VitalSign;
import app.code.verse.domain.ports.VitalSignPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterVitalSign {

    @Autowired
    private VitalSignPort vitalSignPort;

    // Registra los signos vitales de un paciente en una visita específica
    public void register(VitalSign vitalSign) throws Exception {
        if (vitalSign == null) {
            throw new IllegalArgumentException("Los signos vitales no pueden ser nulos");
        }
        if (vitalSign.getVisitRecordId() == null) {
            throw new IllegalArgumentException("El ID del registro de visita es requerido");
        }
        if (vitalSign.getPatientId() == null || vitalSign.getPatientId().isEmpty()) {
            throw new IllegalArgumentException("El ID del paciente es requerido");
        }

        vitalSignPort.save(vitalSign);
    }
}

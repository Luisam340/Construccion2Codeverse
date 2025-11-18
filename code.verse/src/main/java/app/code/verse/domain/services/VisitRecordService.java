package app.code.verse.domain.services;

import app.code.verse.domain.model.VisitRecord;
import app.code.verse.domain.ports.VisitRecordServicePort;
import app.code.verse.adapter.out.VisitRecordAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class VisitRecordService implements VisitRecordServicePort {

    @Autowired
    private VisitRecordAdapter visitRecordAdapter;

    // Crea un registro de visita médica validando paciente, fecha, doctor, motivo y reglas de diagnóstico
    @Override
    public VisitRecord createVisitRecord(VisitRecord record) {
        validatePatient(record.getPatientId());
        validateDate(record.getPatientId(), record.getVisitDate());
        validateDoctor(record.getDoctorId());
        validateReason(record.getReason());
        validateDiagnosisRules(record);
        try {
            return visitRecordAdapter.save(record);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el registro de visita: " + e.getMessage(), e);
        }
    }

    // Obtiene el registro de visita de un paciente en una fecha específica
    @Override
    public VisitRecord getVisitByDate(String idPatient, String dateKey) {
        // Buscar por paciente y filtrar por fecha
        List<VisitRecord> records = visitRecordAdapter.findByPatient(idPatient);
        return records.stream().filter(r -> r.getVisitDate() != null && r.getVisitDate().toString().equals(dateKey)).findFirst().orElse(null);
    }

    // Valida que el identificador del paciente sea válido y no vacío
    private void validatePatient(String idPatient) {
        if (idPatient == null || idPatient.isEmpty()) {
            throw new IllegalArgumentException("La visita debe estar asociada a un idPatient válido.");
        }
    }

    // Valida que la fecha sea válida y que no exista otra visita del paciente en esa misma fecha
    private void validateDate(String idPatient, LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("La visita debe tener una fecha.");
        }
        // Buscar registros existentes del paciente
        List<VisitRecord> records = visitRecordAdapter.findByPatient(idPatient);
        boolean exists = records.stream().anyMatch(r -> r.getVisitDate() != null && r.getVisitDate().equals(date));
        if (exists) {
            throw new IllegalArgumentException("Ya existe una visita registrada para este paciente en esa fecha.");
        }
    }

    // Valida que el identificador del doctor sea válido y no vacío
    private void validateDoctor(String doctorId) {
        if (doctorId == null || doctorId.isEmpty()) {
            throw new IllegalArgumentException("La visita debe estar asociada a un médico válido.");
        }
    }

    // Valida que el motivo de la consulta esté presente
    private void validateReason(String reason) {
        if (reason == null || reason.isEmpty()) {
            throw new IllegalArgumentException("El motivo de la consulta es obligatorio.");
        }
    }

    // Valida que los signos vitales incluyan presión, temperatura, pulso y oxígeno
    private void validateVitalSigns(Map<String, String> vitalSigns) {
        if (vitalSigns == null || vitalSigns.isEmpty()) {
            throw new IllegalArgumentException("Se deben registrar signos vitales en la visita.");
        }
        if (!vitalSigns.containsKey("pressure") || !vitalSigns.containsKey("temperature") || !vitalSigns.containsKey("pulse") || !vitalSigns.containsKey("oxygen")) {
            throw new IllegalArgumentException("Los signos vitales deben incluir presión, temperatura, pulso y oxígeno.");
        }
    }

    // Valida reglas de negocio: visitas de ayuda diagnóstica no pueden incluir medicamentos ni procedimientos
    private void validateDiagnosisRules(VisitRecord record) {
        boolean hasDiagnosis = record.getDiagnosis() != null && !record.getDiagnosis().isEmpty();

        if (!hasDiagnosis && (record.getSymptoms() != null && !record.getSymptoms().isEmpty())) {
            throw new IllegalArgumentException("Si la visita es solo por ayuda diagnóstica, no puede incluir medicamentos ni procedimientos.");
        }
    }

    public VisitRecord findById(Long id) {
        return visitRecordAdapter.findById(id);
    }

    public List<VisitRecord> findByPatient(String patientId) {
        return visitRecordAdapter.findByPatient(patientId);
    }

    public List<VisitRecord> findByDoctor(String doctorId) {
        return visitRecordAdapter.findByDoctor(doctorId);
    }
}

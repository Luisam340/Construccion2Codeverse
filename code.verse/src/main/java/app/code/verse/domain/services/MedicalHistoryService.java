package app.code.verse.domain.services;

import app.code.verse.domain.model.MedicalHistory;
import app.code.verse.domain.model.VisitRecord;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class MedicalHistoryService {
    private final HashMap<String, MedicalHistory> storage = new HashMap<>();
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Agrega un registro de visita a la historia clínica de un paciente
    public void addVisit(String idPatient, VisitRecord record) {
        validatePatient(idPatient);
        validateVisitDate(idPatient, record);

        MedicalHistory history = storage.getOrDefault(idPatient, new MedicalHistory());
        history.setIdPatient(idPatient);

        if (history.getRecords() == null) {
            history.setRecords(new HashMap<>());
        }

        String dateKey = record.getVisitDate().format(DATE_FORMATTER);
        history.getRecords().put(dateKey, record);

        storage.put(idPatient, history);
    }

    // Obtiene la historia clínica completa de un paciente
    public MedicalHistory getHistory(String idPatient) {
        return storage.get(idPatient);
    }

    // Busca todas las historias clínicas de un paciente específico
    public List<MedicalHistory> findByPatient(String idPatient) {
        List<MedicalHistory> result = new ArrayList<>();
        MedicalHistory history = storage.get(idPatient);
        if (history != null) {
            result.add(history);
        }
        return result;
    }

    // Busca la historia clínica de un paciente filtrada por una fecha específica
    public MedicalHistory findByPatientAndDate(String idPatient, String date) {
        MedicalHistory history = storage.get(idPatient);
        if (history != null && history.getRecords() != null && history.getRecords().containsKey(date)) {
            MedicalHistory result = new MedicalHistory();
            result.setIdPatient(idPatient);
            HashMap<String, VisitRecord> singleRecord = new HashMap<>();
            singleRecord.put(date, history.getRecords().get(date));
            result.setRecords(singleRecord);
            return result;
        }
        return null;
    }

    // Busca todas las historias clínicas que contengan visitas de un médico específico
    public List<MedicalHistory> findByDoctor(String doctorId) {
        List<MedicalHistory> result = new ArrayList<>();
        for (MedicalHistory history : storage.values()) {
            if (history.getRecords() != null) {
                HashMap<String, VisitRecord> matchingRecords = new HashMap<>();
                for (String date : history.getRecords().keySet()) {
                    VisitRecord record = history.getRecords().get(date);
                    if (record != null && doctorId.equals(record.getDoctorId())) {
                        matchingRecords.put(date, record);
                    }
                }
                if (!matchingRecords.isEmpty()) {
                    MedicalHistory filteredHistory = new MedicalHistory();
                    filteredHistory.setIdPatient(history.getIdPatient());
                    filteredHistory.setRecords(matchingRecords);
                    result.add(filteredHistory);
                }
            }
        }
        return result;
    }

    // Valida que el identificador del paciente sea válido y no vacío
    private void validatePatient(String idPatient) {
        if (idPatient == null || idPatient.isEmpty()) {
            throw new IllegalArgumentException("La historia clínica debe estar asociada a un paciente válido.");
        }
    }

    // Valida que la fecha de visita sea válida y no exista otra visita en esa fecha
    private void validateVisitDate(String idPatient, VisitRecord record) {
        if (record.getVisitDate() == null) {
            throw new IllegalArgumentException("Cada registro de visita debe tener una fecha.");
        }

        MedicalHistory history = storage.get(idPatient);
        if (history != null && history.getRecords() != null) {
            String dateKey = record.getVisitDate().format(DATE_FORMATTER);
            if (history.getRecords().containsKey(dateKey)) {
                throw new IllegalArgumentException("Ya existe una visita registrada en esa fecha.");
            }
        }
    }
}

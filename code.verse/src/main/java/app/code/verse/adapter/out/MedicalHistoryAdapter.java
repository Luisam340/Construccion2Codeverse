package app.code.verse.adapter.out;

import app.code.verse.domain.model.MedicalHistory;
import app.code.verse.domain.model.VisitRecord;
import app.code.verse.domain.ports.MedicalHistoryPort;
import app.code.verse.infrastructure.persistence.entities.MedicalHistoryEntity;
import app.code.verse.infrastructure.persistence.mapper.MedicalHistoryMapper;
import app.code.verse.infrastructure.persistence.repository.MedicalHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class MedicalHistoryAdapter implements MedicalHistoryPort {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Autowired
    private MedicalHistoryRepository repository;

    @Override
    public void addVisit(String idPatient, VisitRecord record) {
        validatePatient(idPatient);
        validateVisitDate(idPatient, record);

        // Guardar el registro de visita en la tabla medical_history
        MedicalHistoryEntity entity = MedicalHistoryMapper.toEntity(record, idPatient);
        repository.save(entity);
    }

    private void validatePatient(String idPatient) {
        if (idPatient == null || idPatient.isEmpty()) {
            throw new IllegalArgumentException("La historia clínica debe estar asociada a un paciente válido.");
        }
    }

    private void validateVisitDate(String idPatient, VisitRecord record) {
        if (record.getVisitDate() == null) {
            throw new IllegalArgumentException("Cada registro de visita debe tener una fecha.");
        }

        List<MedicalHistoryEntity> existingRecords = repository.findByPatientIdAndVisitDate(idPatient, record.getVisitDate());
        if (existingRecords != null && !existingRecords.isEmpty()) {
            throw new IllegalArgumentException("Ya existe una visita registrada en esa fecha.");
        }
    }

    @Override
    public MedicalHistory getHistory(String idPatient) {
        List<MedicalHistoryEntity> entities = repository.findByPatientId(idPatient);

        if (entities == null || entities.isEmpty()) {
            MedicalHistory newHistory = new MedicalHistory();
            newHistory.setIdPatient(idPatient);
            newHistory.setRecords(new HashMap<>());
            return newHistory;
        }

        // Construir el MedicalHistory con todas las visitas
        return MedicalHistoryMapper.toDomain(idPatient, entities);
    }

    @Override
    public List<MedicalHistory> findByPatient(String idPatient) {
        List<MedicalHistoryEntity> entities = repository.findByPatientId(idPatient);
        List<MedicalHistory> result = new ArrayList<>();

        if (entities != null && !entities.isEmpty()) {
            MedicalHistory history = MedicalHistoryMapper.toDomain(idPatient, entities);
            result.add(history);
        }

        return result;
    }

    @Override
    public MedicalHistory findByPatientAndDate(String idPatient, String date) {
        LocalDate visitDate = LocalDate.parse(date, DATE_FORMATTER);
        List<MedicalHistoryEntity> entities = repository.findByPatientIdAndVisitDate(idPatient, visitDate);

        if (entities == null || entities.isEmpty()) {
            return null;
        }

        return MedicalHistoryMapper.toDomain(idPatient, entities);
    }

    @Override
    public List<MedicalHistory> findByDoctor(String doctorId) {
        List<MedicalHistoryEntity> entities = repository.findByDoctorId(doctorId);
        List<MedicalHistory> result = new ArrayList<>();

        if (entities == null || entities.isEmpty()) {
            return result;
        }

        // Agrupar visitas por paciente
        HashMap<String, List<MedicalHistoryEntity>> groupedByPatient = new HashMap<>();
        for (MedicalHistoryEntity entity : entities) {
            String patientId = entity.getPatientId();
            groupedByPatient.computeIfAbsent(patientId, k -> new ArrayList<>()).add(entity);
        }

        // Construir MedicalHistory para cada paciente
        for (String patientId : groupedByPatient.keySet()) {
            MedicalHistory history = MedicalHistoryMapper.toDomain(patientId, groupedByPatient.get(patientId));
            result.add(history);
        }

        return result;
    }

    public VisitRecord addVisitRecord(String patientId, VisitRecord visitRecord) {
        MedicalHistoryEntity entity = MedicalHistoryMapper.toEntity(visitRecord, patientId);
        MedicalHistoryEntity saved = repository.save(entity);
        return MedicalHistoryMapper.toVisitRecord(saved);
    }

    public List<VisitRecord> findVisitsByPatient(String patientId) {
        return repository.findByPatientId(patientId).stream().map(MedicalHistoryMapper::toVisitRecord).toList();
    }

    public List<VisitRecord> findVisitsByDoctor(String doctorId) {
        return repository.findByDoctorId(doctorId).stream().map(MedicalHistoryMapper::toVisitRecord).toList();
    }
}

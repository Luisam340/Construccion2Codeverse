package app.code.verse.adapter.out;

import app.code.verse.domain.model.DiagnosticTestInventory;
import app.code.verse.domain.ports.DiagnosticTestInventoryPort;
import app.code.verse.infrastructure.persistence.entities.DiagnosticTestInventoryEntity;
import app.code.verse.infrastructure.persistence.mapper.DiagnosticTestInventoryMapper;
import app.code.verse.infrastructure.persistence.repository.DiagnosticTestInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiagnosticTestInventoryAdapter implements DiagnosticTestInventoryPort {

    @Autowired
    private DiagnosticTestInventoryRepository repository;

    @Override
    public DiagnosticTestInventory save(DiagnosticTestInventory diagnosticTest) throws Exception {
        DiagnosticTestInventoryEntity entity = DiagnosticTestInventoryMapper.toEntity(diagnosticTest);
        DiagnosticTestInventoryEntity saved = repository.save(entity);
        return DiagnosticTestInventoryMapper.toDomain(saved);
    }

    public DiagnosticTestInventory update(DiagnosticTestInventory diagnosticTest) throws Exception {
        DiagnosticTestInventoryEntity existing = repository.findByIdExam(diagnosticTest.getIdExam());
        if (existing == null) {
            throw new IllegalArgumentException("Examen diagnóstico no encontrado");
        }
        DiagnosticTestInventoryMapper.partialUpdate(diagnosticTest, existing);
        DiagnosticTestInventoryEntity updated = repository.save(existing);
        return DiagnosticTestInventoryMapper.toDomain(updated);
    }

    public void delete(String examId) throws Exception {
        DiagnosticTestInventoryEntity entity = repository.findByIdExam(examId);
        if (entity != null) {
            repository.delete(entity);
        }
    }

    @Override
    public DiagnosticTestInventory findById(String examId) {
        DiagnosticTestInventoryEntity entity = repository.findByIdExam(examId);
        return DiagnosticTestInventoryMapper.toDomain(entity);
    }

    @Override
    public List<DiagnosticTestInventory> findByNameContaining(String name) {
        return repository.findByNameContainingIgnoreCase(name).stream().map(DiagnosticTestInventoryMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<DiagnosticTestInventory> findAll() {
        return repository.findAll().stream().map(DiagnosticTestInventoryMapper::toDomain).collect(Collectors.toList());
    }

    public boolean existsById(String examId) {
        return repository.findByIdExam(examId) != null;
    }

    @Override
    public void saveDelete(DiagnosticTestInventory diagnosticTest) throws Exception {
        delete(diagnosticTest.getIdExam());
    }

    @Override
    public void createDiagnosticTestInventory(DiagnosticTestInventory diagnosticTest) throws Exception {
        save(diagnosticTest);
    }

    @Override
    public void updateDiagnosticTestInventory(String idExam, String name, double cost) throws Exception {
        DiagnosticTestInventory diagnosticTest = findById(idExam);
        if (diagnosticTest == null) {
            throw new IllegalArgumentException("El examen diagnóstico no existe");
        }
        diagnosticTest.setName(name);
        diagnosticTest.setCost(cost);
        update(diagnosticTest);
    }

    @Override
    public void removeDiagnosticTestInventory(String idExam) throws Exception {
        delete(idExam);
    }

    @Override
    public boolean existsByIdNumber(String idExam) {
        return existsById(idExam);
    }
}

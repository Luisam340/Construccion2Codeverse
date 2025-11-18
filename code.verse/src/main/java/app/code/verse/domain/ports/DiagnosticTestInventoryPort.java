package app.code.verse.domain.ports;

import app.code.verse.domain.model.DiagnosticTestInventory;

import java.util.List;

public interface DiagnosticTestInventoryPort {
    void createDiagnosticTestInventory(DiagnosticTestInventory diagnosticTestInventory) throws Exception;
    void updateDiagnosticTestInventory(String idExam, String name, double cost) throws Exception;
    DiagnosticTestInventory findById(String idExam);
    List<DiagnosticTestInventory> findAll();
    List<DiagnosticTestInventory> findByNameContaining(String name);
    void removeDiagnosticTestInventory(String idExam) throws Exception;
    boolean existsByIdNumber(String idExam);
    DiagnosticTestInventory save(DiagnosticTestInventory diagnosticTestInventory) throws Exception;
    void saveDelete(DiagnosticTestInventory diagnosticTestInventory) throws Exception;
}

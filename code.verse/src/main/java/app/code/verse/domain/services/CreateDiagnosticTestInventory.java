package app.code.verse.domain.services;

import app.code.verse.domain.model.DiagnosticTestInventory;
import app.code.verse.domain.ports.DiagnosticTestInventoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateDiagnosticTestInventory {
    @Autowired
    private DiagnosticTestInventoryPort diagnosticTestInventoryPort;

    // Crea un nuevo registro de inventario de examen diagnóstico validando su unicidad
    public void createDiagnosticTestInventory(DiagnosticTestInventory diagnosticTestInventory) throws Exception {
        if (diagnosticTestInventoryPort.existsByIdNumber(diagnosticTestInventory.getIdExam())) {
            throw new IllegalArgumentException("El inventario de examen diagnóstico ya existe");
        }
        diagnosticTestInventoryPort.save(diagnosticTestInventory);
    }
}

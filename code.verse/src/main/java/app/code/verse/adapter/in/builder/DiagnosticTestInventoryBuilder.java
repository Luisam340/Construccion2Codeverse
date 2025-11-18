package app.code.verse.adapter.in.builder;

import app.code.verse.adapter.in.validators.InventoryValidator;
import app.code.verse.domain.model.DiagnosticTestInventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DiagnosticTestInventoryBuilder {

    @Autowired
    private InventoryValidator validator;

    public DiagnosticTestInventory build(String idExam, String name, double cost) throws Exception {
        DiagnosticTestInventory diagnosticTest = new DiagnosticTestInventory();
        diagnosticTest.setIdExam(validator.stringValidator(idExam, "ID de examen"));
        diagnosticTest.setName(validator.stringValidator(name, "nombre del examen"));
        diagnosticTest.setCost(cost);
        return diagnosticTest;
    }

    public DiagnosticTestInventory update(DiagnosticTestInventory diagnosticTest, String name, double cost) {
        diagnosticTest.setName(name);
        diagnosticTest.setCost(cost);
        return diagnosticTest;
    }
}

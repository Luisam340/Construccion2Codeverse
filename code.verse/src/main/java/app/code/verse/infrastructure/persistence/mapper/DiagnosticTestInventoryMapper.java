package app.code.verse.infrastructure.persistence.mapper;

import app.code.verse.domain.model.DiagnosticTestInventory;
import app.code.verse.infrastructure.persistence.entities.DiagnosticTestInventoryEntity;

public class DiagnosticTestInventoryMapper {

    public static DiagnosticTestInventoryEntity toEntity(DiagnosticTestInventory diagnosticTest) {
        if (diagnosticTest == null) return null;
        DiagnosticTestInventoryEntity entity = new DiagnosticTestInventoryEntity();
        entity.setIdExam(diagnosticTest.getIdExam());
        entity.setName(diagnosticTest.getName());
        entity.setCost(diagnosticTest.getCost());
        return entity;
    }

    public static DiagnosticTestInventory toDomain(DiagnosticTestInventoryEntity entity) {
        if (entity == null) return null;
        DiagnosticTestInventory diagnosticTest = new DiagnosticTestInventory();
        diagnosticTest.setIdExam(entity.getIdExam());
        diagnosticTest.setName(entity.getName());
        diagnosticTest.setCost(entity.getCost() != null ? entity.getCost() : 0.0);
        return diagnosticTest;
    }

    public static void partialUpdate(DiagnosticTestInventory source, DiagnosticTestInventoryEntity target) {
        if (source.getName() != null) target.setName(source.getName());
        if (source.getCost() != 0.0) target.setCost(source.getCost());
    }
}

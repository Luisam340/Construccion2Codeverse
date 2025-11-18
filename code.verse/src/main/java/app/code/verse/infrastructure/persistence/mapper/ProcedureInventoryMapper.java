package app.code.verse.infrastructure.persistence.mapper;

import app.code.verse.domain.model.ProcedureInventory;
import app.code.verse.infrastructure.persistence.entities.ProcedureInventoryEntity;

public class ProcedureInventoryMapper {

    public static ProcedureInventoryEntity toEntity(ProcedureInventory procedure) {
        if (procedure == null) return null;
        ProcedureInventoryEntity entity = new ProcedureInventoryEntity();
        entity.setIdProcedure(procedure.getIdProcedure());
        entity.setName(procedure.getName());
        entity.setCost(procedure.getCost());
        return entity;
    }

    public static ProcedureInventory toDomain(ProcedureInventoryEntity entity) {
        if (entity == null) return null;
        ProcedureInventory procedure = new ProcedureInventory();
        procedure.setIdProcedure(entity.getIdProcedure());
        procedure.setName(entity.getName());
        procedure.setCost(entity.getCost() != null ? entity.getCost() : 0.0);
        return procedure;
    }

    public static void partialUpdate(ProcedureInventory source, ProcedureInventoryEntity target) {
        if (source.getName() != null) target.setName(source.getName());
        if (source.getCost() != 0.0) target.setCost(source.getCost());
    }
}

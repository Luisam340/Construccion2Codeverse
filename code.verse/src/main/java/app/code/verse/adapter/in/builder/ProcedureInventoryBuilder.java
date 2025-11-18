package app.code.verse.adapter.in.builder;

import app.code.verse.adapter.in.validators.InventoryValidator;
import app.code.verse.domain.model.ProcedureInventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProcedureInventoryBuilder {

    @Autowired
    private InventoryValidator validator;

    public ProcedureInventory build(String idProcedure, String name, double cost) throws Exception {
        ProcedureInventory procedure = new ProcedureInventory();
        procedure.setIdProcedure(validator.stringValidator(idProcedure, "ID de procedimiento"));
        procedure.setName(validator.stringValidator(name, "nombre del procedimiento"));
        procedure.setCost(cost);
        return procedure;
    }

    public ProcedureInventory update(ProcedureInventory procedure, String name, double cost) {
        procedure.setName(name);
        procedure.setCost(cost);
        return procedure;
    }
}

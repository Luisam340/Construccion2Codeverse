package app.code.verse.domain.services;

import app.code.verse.domain.model.ProcedureInventory;
import app.code.verse.domain.ports.ProcedureInventoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateProcedureInventory {
    @Autowired
    private ProcedureInventoryPort procedureInventoryPort;

    // Crea un nuevo registro de inventario de procedimiento validando su unicidad
    public void createProcedureInventory(ProcedureInventory procedureInventory) throws Exception {
        if (procedureInventoryPort.existsByIdNumber(procedureInventory.getIdProcedure())) {
            throw new IllegalArgumentException("El inventario de procedimiento ya existe");
        }
        procedureInventoryPort.save(procedureInventory);
    }
}

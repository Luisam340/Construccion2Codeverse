package app.code.verse.domain.services;

import app.code.verse.domain.model.MedicineInventory;
import app.code.verse.domain.ports.MedicineInventoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateMedicineInventory {
    @Autowired
    private MedicineInventoryPort medicineInventoryPort;

    // Crea un nuevo registro de inventario de medicamento validando su unicidad
    public void createMedicineInventory(MedicineInventory medicineInventory) throws Exception {
        if (medicineInventoryPort.existsByIdNumber(medicineInventory.getIdMedicine())) {
            throw new IllegalArgumentException("El inventario de medicamento ya existe");
        }
        medicineInventoryPort.save(medicineInventory);
    }
}
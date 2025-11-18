package app.code.verse.adapter.in.builder;

import app.code.verse.adapter.in.validators.InventoryValidator;
import app.code.verse.domain.model.MedicineInventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MedicineInventoryBuilder {

    @Autowired
    private InventoryValidator validator;

    public MedicineInventory build(String idMedicine, String name, int stock, double price) throws Exception {
        MedicineInventory medicine = new MedicineInventory();

        medicine.setIdMedicine(validator.stringValidator(idMedicine, "ID de medicamento"));
        medicine.setName(validator.stringValidator(name, "nombre del medicamento"));
        medicine.setStock(stock);
        medicine.setPrice(price);
        return medicine;
    }

    public MedicineInventory update(MedicineInventory medicine, String name, int stock, double price) {
        medicine.setName(name);
        medicine.setStock(stock);
        medicine.setPrice(price);
        return medicine;
    }
}

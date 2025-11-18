package app.code.verse.infrastructure.persistence.mapper;

import app.code.verse.domain.model.MedicineInventory;
import app.code.verse.infrastructure.persistence.entities.MedicineInventoryEntity;

public class MedicineInventoryMapper {

    public static MedicineInventoryEntity toEntity(MedicineInventory medicine) {
        if (medicine == null) return null;
        MedicineInventoryEntity entity = new MedicineInventoryEntity();
        entity.setIdMedicine(medicine.getIdMedicine());
        entity.setName(medicine.getName());
        entity.setStock(medicine.getStock());
        entity.setPrice(medicine.getPrice());
        return entity;
    }

    public static MedicineInventory toDomain(MedicineInventoryEntity entity) {
        if (entity == null) return null;
        MedicineInventory medicine = new MedicineInventory();
        medicine.setIdMedicine(entity.getIdMedicine());
        medicine.setName(entity.getName());
        medicine.setStock(entity.getStock() != null ? entity.getStock() : 0);
        medicine.setPrice(entity.getPrice() != null ? entity.getPrice() : 0.0);
        return medicine;
    }

    public static void partialUpdate(MedicineInventory source, MedicineInventoryEntity target) {
        if (source.getName() != null) target.setName(source.getName());
        if (source.getStock() != 0) target.setStock(source.getStock());
        if (source.getPrice() != 0.0) target.setPrice(source.getPrice());
    }
}

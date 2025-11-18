package app.code.verse.adapter.out;

import app.code.verse.domain.model.MedicineInventory;
import app.code.verse.domain.ports.MedicineInventoryPort;
import app.code.verse.infrastructure.persistence.entities.MedicineInventoryEntity;
import app.code.verse.infrastructure.persistence.mapper.MedicineInventoryMapper;
import app.code.verse.infrastructure.persistence.repository.MedicineInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicineInventoryAdapter implements MedicineInventoryPort {

    @Autowired
    private MedicineInventoryRepository repository;

    @Override
    public MedicineInventory save(MedicineInventory medicine) throws Exception {
        MedicineInventoryEntity entity = MedicineInventoryMapper.toEntity(medicine);
        MedicineInventoryEntity saved = repository.save(entity);
        return MedicineInventoryMapper.toDomain(saved);
    }

    public MedicineInventory update(MedicineInventory medicine) throws Exception {
        MedicineInventoryEntity existing = repository.findByIdMedicine(medicine.getIdMedicine());
        if (existing == null) {
            throw new IllegalArgumentException("Medicamento no encontrado");
        }
        MedicineInventoryMapper.partialUpdate(medicine, existing);
        MedicineInventoryEntity updated = repository.save(existing);
        return MedicineInventoryMapper.toDomain(updated);
    }

    public void delete(String medicineId) throws Exception {
        MedicineInventoryEntity entity = repository.findByIdMedicine(medicineId);
        if (entity != null) {
            repository.delete(entity);
        }
    }

    @Override
    public MedicineInventory findById(String medicineId) {
        MedicineInventoryEntity entity = repository.findByIdMedicine(medicineId);
        return MedicineInventoryMapper.toDomain(entity);
    }

    @Override
    public List<MedicineInventory> findByNameContaining(String name) {
        return repository.findByNameContainingIgnoreCase(name).stream().map(MedicineInventoryMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<MedicineInventory> findAll() {
        return repository.findAll().stream().map(MedicineInventoryMapper::toDomain).collect(Collectors.toList());
    }

    public boolean existsById(String medicineId) {
        return repository.findByIdMedicine(medicineId) != null;
    }

    @Override
    public void saveDelete(MedicineInventory medicine) throws Exception {
        delete(medicine.getIdMedicine());
    }

    @Override
    public void createMedicineInventory(MedicineInventory medicine) throws Exception {
        save(medicine);
    }

    @Override
    public void updateMedicineInventory(String idMedicine, String name, int stock, double price) throws Exception {
        MedicineInventory medicine = findById(idMedicine);
        if (medicine == null) {
            throw new IllegalArgumentException("El medicamento no existe");
        }
        medicine.setName(name);
        medicine.setStock(stock);
        medicine.setPrice(price);
        update(medicine);
    }

    @Override
    public void removeMedicineInventory(String idMedicine) throws Exception {
        delete(idMedicine);
    }

    @Override
    public boolean existsByIdNumber(String idMedicine) {
        return existsById(idMedicine);
    }
}

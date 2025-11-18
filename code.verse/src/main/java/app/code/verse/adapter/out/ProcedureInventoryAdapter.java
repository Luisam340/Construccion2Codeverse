package app.code.verse.adapter.out;

import app.code.verse.domain.model.ProcedureInventory;
import app.code.verse.domain.ports.ProcedureInventoryPort;
import app.code.verse.infrastructure.persistence.entities.ProcedureInventoryEntity;
import app.code.verse.infrastructure.persistence.mapper.ProcedureInventoryMapper;
import app.code.verse.infrastructure.persistence.repository.ProcedureInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProcedureInventoryAdapter implements ProcedureInventoryPort {

    @Autowired
    private ProcedureInventoryRepository repository;

    @Override
    public ProcedureInventory save(ProcedureInventory procedure) throws Exception {
        ProcedureInventoryEntity entity = ProcedureInventoryMapper.toEntity(procedure);
        ProcedureInventoryEntity saved = repository.save(entity);
        return ProcedureInventoryMapper.toDomain(saved);
    }

    public ProcedureInventory update(ProcedureInventory procedure) throws Exception {
        ProcedureInventoryEntity existing = repository.findByIdProcedure(procedure.getIdProcedure());
        if (existing == null) {
            throw new IllegalArgumentException("Procedimiento no encontrado");
        }
        ProcedureInventoryMapper.partialUpdate(procedure, existing);
        ProcedureInventoryEntity updated = repository.save(existing);
        return ProcedureInventoryMapper.toDomain(updated);
    }

    public void delete(String procedureId) throws Exception {
        ProcedureInventoryEntity entity = repository.findByIdProcedure(procedureId);
        if (entity != null) {
            repository.delete(entity);
        }
    }

    @Override
    public ProcedureInventory findById(String procedureId) {
        ProcedureInventoryEntity entity = repository.findByIdProcedure(procedureId);
        return ProcedureInventoryMapper.toDomain(entity);
    }

    @Override
    public List<ProcedureInventory> findByNameContaining(String name) {
        return repository.findByNameContainingIgnoreCase(name).stream().map(ProcedureInventoryMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<ProcedureInventory> findAll() {
        return repository.findAll().stream().map(ProcedureInventoryMapper::toDomain).collect(Collectors.toList());
    }

    public boolean existsById(String procedureId) {
        return repository.findByIdProcedure(procedureId) != null;
    }

    @Override
    public void saveDelete(ProcedureInventory procedure) throws Exception {
        delete(procedure.getIdProcedure());
    }

    @Override
    public void createProcedureInventory(ProcedureInventory procedure) throws Exception {
        save(procedure);
    }

    @Override
    public void updateProcedureInventory(String idProcedure, String name, double cost) throws Exception {
        ProcedureInventory procedure = findById(idProcedure);
        if (procedure == null) {
            throw new IllegalArgumentException("El procedimiento no existe");
        }
        procedure.setName(name);
        procedure.setCost(cost);
        update(procedure);
    }

    @Override
    public void removeProcedureInventory(String idProcedure) throws Exception {
        delete(idProcedure);
    }

    @Override
    public boolean existsByIdNumber(String idProcedure) {
        return existsById(idProcedure);
    }
}

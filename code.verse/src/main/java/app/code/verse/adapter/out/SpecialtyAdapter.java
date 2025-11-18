package app.code.verse.adapter.out;

import app.code.verse.domain.model.Specialty;
import app.code.verse.domain.ports.SpecialtyPort;
import app.code.verse.infrastructure.persistence.entities.SpecialtyEntity;
import app.code.verse.infrastructure.persistence.mapper.SpecialtyMapper;
import app.code.verse.infrastructure.persistence.repository.SpecialtyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpecialtyAdapter implements SpecialtyPort {

    @Autowired
    private SpecialtyRepository repository;

    @Autowired
    private SpecialtyMapper mapper;

    @Override
    public Specialty save(Specialty specialty) throws Exception {
        SpecialtyEntity entity = mapper.toEntity(specialty);
        if (specialty.getId() != null) {
            entity.setId(specialty.getId());
        }
        SpecialtyEntity saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public void saveDelete(Specialty specialty) throws Exception {
        SpecialtyEntity entity = repository.findById(specialty.getId()).orElse(null);
        if (entity != null) {
            entity.setStatus(false);
            repository.save(entity);
        }
    }

    @Override
    public Specialty findById(Long id) {
        return repository.findById(id).map(mapper::toDomain).orElse(null);
    }

    @Override
    public List<Specialty> findAll() {
        return repository.findByStatusTrue().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Specialty> findByNameContaining(String name) {
        return repository.findByStatusTrue().stream().filter(e -> e.getName().toLowerCase().contains(name.toLowerCase())).map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public boolean existsByName(String name) {
        return repository.existsByName(name);
    }

    @Override
    public void createSpecialty(Specialty specialty) throws Exception {
        save(specialty);
    }

    @Override
    public void updateSpecialty(Long id, String name, String description) throws Exception {
        Specialty specialty = findById(id);
        if (specialty == null) {
            throw new IllegalArgumentException("Especialidad no encontrada");
        }
        specialty.setName(name);
        specialty.setDescription(description);
        save(specialty);
    }

    @Override
    public void removeSpecialty(Long id) throws Exception {
        Specialty specialty = findById(id);
        if (specialty != null) {
            saveDelete(specialty);
        }
    }
}

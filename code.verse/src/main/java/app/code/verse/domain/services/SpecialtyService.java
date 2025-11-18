package app.code.verse.domain.services;

import app.code.verse.domain.model.Specialty;
import app.code.verse.domain.ports.SpecialtyPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpecialtyService {

    @Autowired
    private SpecialtyPort specialtyPort;

    // Crea una nueva especialidad médica validando su unicidad por nombre
    public void create(Specialty specialty) throws Exception {
        if (specialtyPort.existsByName(specialty.getName())) {
            throw new IllegalArgumentException("Ya existe una especialidad con ese nombre");
        }
        specialtyPort.save(specialty);
    }

    // Actualiza una especialidad médica existente validando su existencia
    public void update(Specialty specialty) throws Exception {
        Specialty existing = specialtyPort.findById(specialty.getId());
        if (existing == null) {
            throw new IllegalArgumentException("La especialidad no existe");
        }
        specialtyPort.save(specialty);
    }

    // Marca una especialidad como eliminada cambiando su estado a inactivo
    public void delete(Long id) throws Exception {
        Specialty specialty = specialtyPort.findById(id);
        if (specialty == null) {
            throw new IllegalArgumentException("La especialidad no existe");
        }
        specialty.setStatus(false);
        specialtyPort.saveDelete(specialty);
    }
}

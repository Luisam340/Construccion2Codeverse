package app.code.verse.domain.ports;

import app.code.verse.domain.model.Specialty;

import java.util.List;

public interface SpecialtyPort {
    void createSpecialty(Specialty specialty) throws Exception;
    void updateSpecialty(Long id, String name, String description) throws Exception;
    Specialty findById(Long id);
    List<Specialty> findAll();
    List<Specialty> findByNameContaining(String name);
    void removeSpecialty(Long id) throws Exception;
    boolean existsByName(String name);
    Specialty save(Specialty specialty) throws Exception;
    void saveDelete(Specialty specialty) throws Exception;
}

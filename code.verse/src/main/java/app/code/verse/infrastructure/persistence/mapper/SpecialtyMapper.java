package app.code.verse.infrastructure.persistence.mapper;

import app.code.verse.domain.model.Specialty;
import app.code.verse.infrastructure.persistence.entities.SpecialtyEntity;
import org.springframework.stereotype.Component;

@Component
public class SpecialtyMapper {

    public SpecialtyEntity toEntity(Specialty specialty) {
        if (specialty == null) {
            return null;
        }

        SpecialtyEntity entity = new SpecialtyEntity();
        entity.setId(null);
        entity.setName(specialty.getName());
        entity.setDescription(specialty.getDescription());
        entity.setStatus(specialty.getStatus());

        return entity;
    }

    public Specialty toDomain(SpecialtyEntity entity) {
        if (entity == null) {
            return null;
        }

        Specialty specialty = new Specialty();
        specialty.setId(entity.getId());
        specialty.setName(entity.getName());
        specialty.setDescription(entity.getDescription());
        specialty.setStatus(entity.getStatus());

        return specialty;
    }
}

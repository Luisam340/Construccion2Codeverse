package app.code.verse.adapter.in.builder;

import app.code.verse.adapter.in.validators.SpecialtyValidator;
import app.code.verse.domain.model.Specialty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SpecialtyBuilder {

    @Autowired
    private SpecialtyValidator specialtyValidator;

    public Specialty build(String name, String description) throws Exception {
        Specialty specialty = new Specialty();
        specialty.setName(specialtyValidator.nameValidator(name));
        specialty.setDescription(specialtyValidator.descriptionValidator(description));
        specialty.setStatus(true);
        return specialty;
    }

    public Specialty update(Specialty specialty, String name, String description) throws Exception {
        specialty.setName(specialtyValidator.nameValidator(name));
        specialty.setDescription(specialtyValidator.descriptionValidator(description));
        return specialty;
    }
}

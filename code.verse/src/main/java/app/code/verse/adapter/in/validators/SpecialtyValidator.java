package app.code.verse.adapter.in.validators;

import org.springframework.stereotype.Component;

@Component
public class SpecialtyValidator extends SimpleValidator {

    public String nameValidator(String value) throws Exception {
        maximumSizeValidator(value, "nombre de especialidad", 100);
        return stringValidator(value, "nombre de especialidad");
    }

    public String descriptionValidator(String value) throws Exception {
        if (value != null && !value.isBlank()) {
            maximumSizeValidator(value, "descripción", 500);
        }
        return value;
    }
}

package app.code.verse.adapter.in.validators;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class PersonValidator extends SimpleValidator {
    public String nameValidator(String value) throws Exception {
        return stringValidator(value, "Nombre");
    }

    public String idNumberValidator(String value) throws Exception {
        return stringValidator(value, "Número de identificación");
    }

    public LocalDate birthDateValidator(LocalDate value) {
        return localDateValidator(value, "fecha de nacimiento");
    }

    public String emailValidator(String value) throws Exception {
        emailFormatValidator(value);
        return stringValidator(value, "correo electrónico");
    }

    public String addressValidator(String value) throws Exception {
        maximumSizeValidator(value, "dirección", 30);
        return stringValidator(value, "dirección");
    }

    public String phoneNumberValidator(String value) throws Exception {
        phoneSizeValidator(value);
        return stringValidator(value, "número de teléfono");
    }
}

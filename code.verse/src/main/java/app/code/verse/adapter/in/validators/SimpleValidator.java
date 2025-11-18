package app.code.verse.adapter.in.validators;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class SimpleValidator {

    // Expresión regular para validar formato de correo electrónico
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    // Valida que un string no sea nulo ni vacío
    public String stringValidator(String value, String fieldName) throws Exception {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " no puede ser nulo o vacío");
        }
        return value;
    }

    // Valida que una fecha no sea nula y esté dentro de un rango razonable (±150 años)
    public LocalDate localDateValidator(LocalDate localDate, String fieldName) {
        if (localDate == null) {
            throw new IllegalArgumentException(fieldName + " no puede ser nula o vacía");
        }

        if (localDate.isBefore(LocalDate.now().minusYears(150)) || localDate.isAfter(LocalDate.now().plusYears(150))) {
            throw new IllegalArgumentException("La fecha no puede ser mayor o menor a 150 años");
        }

        if (localDate.isBefore(LocalDate.now()) && fieldName.contains("póliza")) {
            throw new IllegalArgumentException("La fecha no puede ser futura");
        }
        return localDate;
    }

    // Valida que la fecha de nacimiento sea válida, no futura y no mayor a 150 años
    public LocalDate birthDateValidator(LocalDate birthDate) {
        if (birthDate == null) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser nula o vacía");
        }

        if (birthDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser futura");
        }

        if (birthDate.isBefore(LocalDate.now().minusYears(150))) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser mayor a 150 años");
        }

        return birthDate;
    }

    // Valida fecha de nacimiento y verifica que la persona tenga al menos la edad mínima requerida
    public LocalDate birthDateValidatorWithMinAge(LocalDate birthDate, int minAge) {
        birthDateValidator(birthDate);

        if (birthDate.isAfter(LocalDate.now().minusYears(minAge))) {
            throw new IllegalArgumentException("La persona debe tener al menos " + minAge + " años de edad");
        }

        return birthDate;
    }

    // Valida que el teléfono no sea vacío y tenga exactamente 10 dígitos
    public String phoneValidator(String phone) throws Exception {
        stringValidator(phone, "teléfono");
        phoneSizeValidator(phone);
        return phone;
    }

    // Valida que un campo no exceda el tamaño máximo permitido
    public void maximumSizeValidator(String field, String fieldName, int maxSize) {
        if (field.length() > maxSize) {
            throw new IllegalArgumentException(
                    "El campo " + fieldName + " no puede tener más de " + maxSize + " caracteres");
        }
    }

    // Valida que un campo tenga al menos el tamaño mínimo requerido
    public void minimumSizeValidator(String field, String fieldName, int minSize) {
        if (field.length() < minSize) {
            throw new IllegalArgumentException(
                    "El campo " + fieldName + " no puede tener menos de " + minSize + " caracteres");
        }
    }

    // Valida que el correo electrónico cumpla con el formato estándar (regex)
    public void emailFormatValidator(String email) {
        if (!Pattern.matches(EMAIL_REGEX, email)) {
            throw new IllegalArgumentException("El correo electrónico debe tener un formato válido");
        }
    }

    // Valida que el número de teléfono tenga exactamente 10 dígitos numéricos
    public static void phoneSizeValidator(String phone){
        if (!phone.matches("\\d{10}")) {
            throw new IllegalArgumentException("El número de teléfono debe tener 10 dígitos");
        }
    }

    // Valida que el género no sea nulo
    public static void genderSizeValidator(String gender){
        if (gender == null) {
            throw new IllegalArgumentException(gender + " no puede ser vacío");
        }
    }

}
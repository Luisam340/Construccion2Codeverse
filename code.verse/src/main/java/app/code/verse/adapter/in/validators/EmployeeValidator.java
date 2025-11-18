package app.code.verse.adapter.in.validators;

import app.code.verse.domain.model.enums.Rol;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class EmployeeValidator extends SimpleValidator {

    // Edad mínima requerida para ser empleado (18 años)
    private static final int MIN_EMPLOYEE_AGE = 18;

    // Valida que el nombre de usuario tenga entre 8 y 15 caracteres alfanuméricos
    public String userNameValidator(String value) throws Exception {
        maximumSizeValidator(value, "nombre de usuario", 15);
        minimumSizeValidator(value,"nombre de usuario ", 8);
        if (!value.matches("^[a-zA-Z0-9]+$")) {
            throw new IllegalArgumentException("El nombre de usuario solo puede contener letras y números");
        }
        return stringValidator(value, "nombre de usuario");
    }

    // Valida que la contraseña tenga al menos 8 caracteres, una mayúscula, un número y un carácter especial
    public String passwordValidator(String value) throws Exception {
        minimumSizeValidator(value, "contraseña", 8);
        if (!value.matches(".*[A-Z].*") || !value.matches(".*\\d.*") || !value.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            throw new IllegalArgumentException("La contraseña debe contener al menos una letra mayúscula, un número y un carácter especial");
        }
        return stringValidator(value, "contraseña");
    }

    // Convierte abreviaturas de rol a sus valores completos (HR, N, D, AS, IS) según el enum Rol
    public String rolValidator(String value) throws Exception {
        switch (value.toUpperCase()) {
            case "HR":
                value = Rol.HR.getRol();
                break;
            case "N":
                value = Rol.NURSE.getRol();
                break;
            case "D":
                value = Rol.DOCTOR.getRol();
                break;
            case "AS":
                value = Rol.ADMINISTRATIVE_STAFF.getRol();
                break;
            case "IS":
                value = Rol.INFORMATION_SUPPORT.getRol();
                break;
        }
        return stringValidator(value, "rol");
    }

    // Valida que el empleado tenga al menos 18 años de edad
    public LocalDate employeeBirthDateValidator(LocalDate birthDate) {
        return birthDateValidatorWithMinAge(birthDate, MIN_EMPLOYEE_AGE);
    }

    public String employeePhoneValidator(String phone) throws Exception {
        return phoneValidator(phone);
    }

    public String employeeEmailValidator(String email) throws Exception {
        stringValidator(email, "correo electrónico");
        emailFormatValidator(email);
        return email;
    }
}

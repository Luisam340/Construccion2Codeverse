package app.code.verse.adapter.in.validators;

import app.code.verse.domain.model.enums.Gender;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class PatientValidator extends SimpleValidator {

    // Convierte abreviaturas de género (F/M) a sus valores completos (FEMALE/MALE) según el enum Gender
    public String genderValidator(String value) throws Exception {
        genderSizeValidator(value);
        if (value.equals("F") || value.equals("f") ) {
            value = Gender.FEMALE.gender();
        } else if (value.equals("M") || value.equals("m") ) {
            value = Gender.MALE.gender();
        } else {
            throw new IllegalArgumentException("Género incorrecto");
        }
        return stringValidator(value, "género");
    }

    public LocalDate patientBirthDateValidator(LocalDate birthDate) {
        return birthDateValidator(birthDate);
    }

    public String patientPhoneValidator(String phone) throws Exception {
        return phoneValidator(phone);
    }

    public String patientEmailValidator(String email) throws Exception {
        stringValidator(email, "correo electrónico");
        emailFormatValidator(email);
        return email;
    }
}

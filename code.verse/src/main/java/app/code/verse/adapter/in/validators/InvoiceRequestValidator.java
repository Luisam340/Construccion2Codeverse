package app.code.verse.adapter.in.validators;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InvoiceRequestValidator extends SimpleValidator {

    public String patientIdValidator(String value) throws Exception {
        return stringValidator(value, "ID de paciente");
    }

    public String doctorNameValidator(String value) throws Exception {
        return stringValidator(value, "nombre del médico");
    }

    public List<String> orderNumbersValidator(List<String> orderNumbers) {
        if (orderNumbers == null || orderNumbers.isEmpty()) {
            throw new IllegalArgumentException("Debe proporcionar al menos un número de orden");
        }
        return orderNumbers;
    }
}

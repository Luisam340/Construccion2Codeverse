package app.code.verse.adapter.in.builder;

import app.code.verse.adapter.in.validators.InvoiceRequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InvoiceRequestBuilder {

    @Autowired
    private InvoiceRequestValidator invoiceRequestValidator;

    public Map<String, Object> build(String patientId, String doctorName, List<String> orderNumbers) throws Exception {
        Map<String, Object> validatedRequest = new HashMap<>();
        validatedRequest.put("patientId", invoiceRequestValidator.patientIdValidator(patientId));
        validatedRequest.put("doctorName", invoiceRequestValidator.doctorNameValidator(doctorName));
        validatedRequest.put("orderNumbers", invoiceRequestValidator.orderNumbersValidator(orderNumbers));
        return validatedRequest;
    }
}

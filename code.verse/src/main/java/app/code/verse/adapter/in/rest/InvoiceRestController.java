package app.code.verse.adapter.in.rest;

import app.code.verse.adapter.in.builder.InvoiceRequestBuilder;
import app.code.verse.application.usecases.AdministrativeStaffUseCase;
import app.code.verse.application.usecases.InvoiceUseCase;
import app.code.verse.domain.model.Invoice;
import app.code.verse.domain.model.Patient;
import app.code.verse.domain.model.Policy;
import app.code.verse.domain.ports.PatientPort;
import app.code.verse.domain.ports.PolicyPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/hospital/san-rafael/invoices")
@CrossOrigin(origins = "*")
public class InvoiceRestController {

    @Autowired
    private InvoiceUseCase invoiceUseCase;

    @Autowired
    private InvoiceRequestBuilder invoiceRequestBuilder;

    @Autowired
    private PatientPort patientPort;

    @Autowired
    private PolicyPort policyPort;

    @Autowired
    private AdministrativeStaffUseCase staffUseCase;
    @PostMapping("/generate")
    @PreAuthorize("hasRole('PERSONAL_ADMINISTRATIVO')")
    public ResponseEntity<Map<String, Object>> generateInvoice(@RequestBody Map<String, Object> requestBody) {
        Map<String, Object> response = new HashMap<>();
        try {
            String patientId = (String) requestBody.get("patientId");
            String doctorName = (String) requestBody.get("doctorName");
            List<String> orderNumbers = (List<String>) requestBody.get("orderNumbers");
            Map<String, Object> validatedRequest = invoiceRequestBuilder.build(patientId, doctorName, orderNumbers);
            Invoice invoice = invoiceUseCase.generateInvoice((String) validatedRequest.get("patientId"), (String) validatedRequest.get("doctorName"), (List<String>) validatedRequest.get("orderNumbers"));
            Map<String, Object> enrichedInvoice = buildEnrichedInvoiceResponse(invoice);

            response.put("success", true);
            response.put("message", "Factura generada exitosamente");
            response.put("data", enrichedInvoice);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al generar factura: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAnyRole('PERSONAL_ADMINISTRATIVO', 'DOCTOR')")
    public ResponseEntity<Map<String, Object>> getInvoicesByPatient(@PathVariable String patientId) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Invoice> invoices = invoiceUseCase.getInvoicesByPatient(patientId);
            response.put("success", true);
            response.put("count", invoices.size());
            response.put("data", invoices);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al obtener facturas: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/patient/{patientId}/date-range")
    @PreAuthorize("hasAnyRole('PERSONAL_ADMINISTRATIVO', 'DOCTOR')")
    public ResponseEntity<Map<String, Object>> getInvoicesByPatientAndDateRange(@PathVariable String patientId, @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate, @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Invoice> invoices = invoiceUseCase.getInvoicesByPatientAndDateRange(patientId, startDate, endDate);
            response.put("success", true);
            response.put("count", invoices.size());
            response.put("data", invoices);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al obtener facturas: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{invoiceId}")
    @PreAuthorize("hasAnyRole('PERSONAL_ADMINISTRATIVO', 'DOCTOR')")
    public ResponseEntity<Map<String, Object>> getInvoiceById(@PathVariable Long invoiceId) {
        Map<String, Object> response = new HashMap<>();
        try {
            Invoice invoice = invoiceUseCase.getInvoiceById(invoiceId);
            Map<String, Object> enrichedInvoice = buildEnrichedInvoiceResponse(invoice);

            response.put("success", true);
            response.put("data", enrichedInvoice);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al obtener factura: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/patient/{patientId}/copay-total/{year}")
    @PreAuthorize("hasAnyRole('PERSONAL_ADMINISTRATIVO', 'DOCTOR')")
    public ResponseEntity<Map<String, Object>> getYearCopayTotal(@PathVariable String patientId, @PathVariable int year) {
        Map<String, Object> response = new HashMap<>();
        try {
            Double copayTotal = invoiceUseCase.getYearCopayTotal(patientId, year);
            response.put("success", true);
            response.put("patientId", patientId);
            response.put("year", year);
            response.put("copayTotal", copayTotal);
            response.put("remainingBeforeLimit", Math.max(0, 1000000.0 - copayTotal));
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al obtener total de copagos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    private Map<String, Object> buildEnrichedInvoiceResponse(Invoice invoice) throws Exception {
        Map<String, Object> enriched = new HashMap<>();
        enriched.put("invoiceId", invoice.getIdInvoice());
        enriched.put("issueDate", invoice.getIssueDate());

        Patient patient = patientPort.findByIdNumber(invoice.getIdPatient());
        if (patient != null) {
            enriched.put("patientId", invoice.getIdPatient());
            enriched.put("patientName", invoice.getPatientName());
            enriched.put("patientAge", calculateAge(patient.getBirthDate()));
        }
        enriched.put("doctorName", invoice.getDoctorName());
        Policy policy = policyPort.findByPatient(invoice.getIdPatient());
        if (policy != null && policy.isActive()) {
            enriched.put("insuranceCompany", policy.getCompanyName());
            enriched.put("policyNumber", policy.getPolicyNumber());
            enriched.put("policyActive", policy.isActive());
            enriched.put("policyExpirationDate", policy.getExpirationDate());
            long daysUntilExpiration = ChronoUnit.DAYS.between(LocalDate.now(), policy.getExpirationDate());
            enriched.put("policyDaysUntilExpiration", daysUntilExpiration);
        } else {
            enriched.put("insuranceCompany", "Sin seguro");
            enriched.put("policyNumber", "N/A");
            enriched.put("policyActive", false);
            enriched.put("policyDaysUntilExpiration", 0);
        }

        enriched.put("servicesIncluded", invoice.getServicesIncluded());
        enriched.put("totalAmount", invoice.getTotalAmount());
        enriched.put("copayAmount", invoice.getCopayAmount());
        enriched.put("insuranceCoverage", invoice.getInsuranceCoverage());

        return enriched;
    }

    private int calculateAge(LocalDate birthDate) {
        if (birthDate == null) {
            return 0;
        }
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}

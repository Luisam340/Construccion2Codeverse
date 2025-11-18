package app.code.verse.adapter.in.rest;

import app.code.verse.adapter.in.builder.EmergencyContactBuilder;
import app.code.verse.adapter.in.builder.PatientBuilder;
import app.code.verse.adapter.in.builder.PolicyBuilder;
import app.code.verse.application.usecases.AdministrativeStaffUseCase;
import app.code.verse.domain.model.EmergencyContact;
import app.code.verse.domain.model.Patient;
import app.code.verse.domain.model.Policy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/hospital/san-rafael/patients")
@CrossOrigin(origins = "*")
public class PatientRestController {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Autowired
    private AdministrativeStaffUseCase staffUseCase;
    @Autowired
    private PatientBuilder patientBuilder;
    @Autowired
    private PolicyBuilder policyBuilder;
    @Autowired
    private EmergencyContactBuilder emergencyContactBuilder;

    @PostMapping
    @PreAuthorize("hasRole('PERSONAL_ADMINISTRATIVO')")
    public ResponseEntity<Map<String, Object>> createPatient(@RequestBody Map<String, Object> requestBody) {
        Map<String, Object> response = new HashMap<>();
        try {
            Map<String, Object> patientData = (Map<String, Object>) requestBody.get("patient");
            Patient patient = patientBuilder.build(
                (String) patientData.get("idNumber"),
                (String) patientData.get("name"),
                LocalDate.parse((String) patientData.get("birthDate"), DATE_FORMATTER),
                (String) patientData.get("gender"),
                (String) patientData.get("address"),
                (String) patientData.get("phoneNumber"),
                (String) patientData.get("email")
            );
            Policy policy = null;
            if (requestBody.containsKey("policy") && requestBody.get("policy") != null) {
                Map<String, Object> policyData = (Map<String, Object>) requestBody.get("policy");
                policy = policyBuilder.build(
                    (String) policyData.get("companyName"),
                    (String) policyData.get("policyNumber"),
                    (Boolean) policyData.get("active"),
                    LocalDate.parse((String) policyData.get("expirationDate"), DATE_FORMATTER)
                );
            }
            EmergencyContact emergencyContact = null;
            if (requestBody.containsKey("emergencyContact") && requestBody.get("emergencyContact") != null) {
                Map<String, Object> contactData = (Map<String, Object>) requestBody.get("emergencyContact");
                emergencyContact = emergencyContactBuilder.build(
                    (String) contactData.get("name"),
                    (String) contactData.get("kinship"),
                    (String) contactData.get("phoneNumber")
                );
            }

            staffUseCase.create(patient, policy, emergencyContact);
            response.put("success", true);
            response.put("message", "Paciente creado exitosamente");
            response.put("data", patient);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al crear paciente: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('PERSONAL_ADMINISTRATIVO', 'DOCTOR', 'ENFERMERA')")
    public ResponseEntity<Map<String, Object>> getAllPatients() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Patient> patients = staffUseCase.getAllPatient();
            List<Map<String, Object>> patientsWithDetails = patients.stream().map(patient -> {
                Map<String, Object> patientData = new HashMap<>();
                patientData.put("patient", patient);
                patientData.put("policy", staffUseCase.getPolicy(patient.getIdNumber()));
                patientData.put("emergencyContact", staffUseCase.getEmergencyContact(patient.getIdNumber()));
                return patientData;
            }).toList();

            response.put("success", true);
            response.put("count", patientsWithDetails.size());
            response.put("data", patientsWithDetails);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al obtener pacientes: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{idNumber}")
    @PreAuthorize("hasAnyRole('PERSONAL_ADMINISTRATIVO', 'DOCTOR', 'ENFERMERA')")
    public ResponseEntity<Map<String, Object>> getPatientById(@PathVariable String idNumber) {
        Map<String, Object> response = new HashMap<>();
        try {
            Patient patient = staffUseCase.findById(idNumber);
            Policy policy = staffUseCase.getPolicy(idNumber);
            EmergencyContact emergencyContact = staffUseCase.getEmergencyContact(idNumber);

            Map<String, Object> patientData = new HashMap<>();
            patientData.put("patient", patient);
            patientData.put("policy", policy);
            patientData.put("emergencyContact", emergencyContact);

            response.put("success", true);
            response.put("data", patientData);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al buscar paciente: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('PERSONAL_ADMINISTRATIVO', 'DOCTOR', 'ENFERMERA')")
    public ResponseEntity<Map<String, Object>> searchPatientsByName(@RequestParam String name) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Patient> patients = staffUseCase.findByName(name);
            response.put("success", true);
            response.put("count", patients.size());
            response.put("data", patients);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al buscar pacientes: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{idNumber}")
    @PreAuthorize("hasRole('PERSONAL_ADMINISTRATIVO')")
    public ResponseEntity<Map<String, Object>> updatePatient(@PathVariable String idNumber, @RequestBody Patient patient) {
        Map<String, Object> response = new HashMap<>();
        try {
            patient.setIdNumber(idNumber);
            patient = patientBuilder.update(patient, patient.getName(), patient.getBirthDate(), patient.getGender(), patient.getAddress(), patient.getPhoneNumber(), patient.getEmail());
            staffUseCase.updatePatient(patient);
            response.put("success", true);
            response.put("message", "Paciente actualizado exitosamente");
            response.put("data", patient);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al actualizar paciente: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{idNumber}/emergency-contact")
    @PreAuthorize("hasRole('PERSONAL_ADMINISTRATIVO')")
    public ResponseEntity<Map<String, Object>> updateEmergencyContact(@PathVariable String idNumber, @RequestBody EmergencyContact emergencyContact) {
        Map<String, Object> response = new HashMap<>();
        try {
            staffUseCase.findById(idNumber);
            emergencyContact = emergencyContactBuilder.build(
                emergencyContact.getName(),
                emergencyContact.getKinship(),
                emergencyContact.getPhoneNumber()
            );
            staffUseCase.updateEmergencyContact(idNumber, emergencyContact);
            response.put("success", true);
            response.put("message", "Contacto de emergencia actualizado exitosamente");
            response.put("data", emergencyContact);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al actualizar contacto de emergencia: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{idNumber}/policy")
    @PreAuthorize("hasRole('PERSONAL_ADMINISTRATIVO')")
    public ResponseEntity<Map<String, Object>> updatePolicy(@PathVariable String idNumber, @RequestBody Map<String, Object> policyData) {
        Map<String, Object> response = new HashMap<>();
        try {
            staffUseCase.findById(idNumber);
            Policy policy = policyBuilder.build(
                (String) policyData.get("companyName"),
                (String) policyData.get("policyNumber"),
                (Boolean) policyData.get("active"),
                LocalDate.parse((String) policyData.get("expirationDate"), DATE_FORMATTER)
            );
            staffUseCase.updatePolicy(idNumber, policy);
            response.put("success", true);
            response.put("message", "Póliza actualizada exitosamente");
            response.put("data", policy);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al actualizar póliza: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{idNumber}")
    @PreAuthorize("hasRole('PERSONAL_ADMINISTRATIVO')")
    public ResponseEntity<Map<String, Object>> deletePatient(@PathVariable String idNumber) {
        Map<String, Object> response = new HashMap<>();
        try {
            Patient patient = staffUseCase.findById(idNumber);
            staffUseCase.deletePatient(patient);
            response.put("success", true);
            response.put("message", "Paciente eliminado exitosamente");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al eliminar paciente: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

package app.code.verse.adapter.in.rest;

import app.code.verse.adapter.in.builder.MedicineAdministrationBuilder;
import app.code.verse.adapter.in.builder.VitalSignBuilder;
import app.code.verse.adapter.in.builder.VisitRecordBuilder;
import app.code.verse.application.usecases.NurseUseCase;
import app.code.verse.domain.model.MedicineAdministration;
import app.code.verse.domain.model.VitalSign;
import app.code.verse.domain.model.VisitRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/hospital/san-rafael/nurses")
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('ENFERMERA')")
public class NurseRestController {

    @Autowired
    private NurseUseCase nurseUseCase;

    @Autowired
    private VitalSignBuilder vitalSignBuilder;

    @Autowired
    private MedicineAdministrationBuilder medicineAdministrationBuilder;

    @Autowired
    private VisitRecordBuilder visitRecordBuilder;

    @PostMapping("/vital-signs")
    public ResponseEntity<Map<String, Object>> registerVitalSigns(@RequestBody Map<String, Object> requestBody) {
        Map<String, Object> response = new HashMap<>();
        try {
            VitalSign vitalSign = vitalSignBuilder.build(requestBody.get("visitRecordId") != null ? Long.valueOf(requestBody.get("visitRecordId").toString()) : null, (String) requestBody.get("patientId"), (String) requestBody.get("bloodPressure"), requestBody.get("temperature") != null ? Double.valueOf(requestBody.get("temperature").toString()) : null, requestBody.get("pulse") != null ? Integer.valueOf(requestBody.get("pulse").toString()) : null, requestBody.get("oxygenLevel") != null ? Double.valueOf(requestBody.get("oxygenLevel").toString()) : null, (String) requestBody.get("recordedBy"));
            nurseUseCase.registerVitalSigns(vitalSign);
            response.put("success", true);
            response.put("message", "Signos vitales registrados exitosamente");
            response.put("data", vitalSign);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al registrar signos vitales: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/vital-signs/patient/{patientId}")
    public ResponseEntity<Map<String, Object>> getVitalSignsByPatient(@PathVariable String patientId) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<VitalSign> vitalSigns = nurseUseCase.getVitalSignsByPatient(patientId);
            response.put("success", true);
            response.put("count", vitalSigns.size());
            response.put("data", vitalSigns);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al obtener signos vitales: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/vital-signs/visit-record/{visitRecordId}")
    public ResponseEntity<Map<String, Object>> getVitalSignsByVisitRecord(@PathVariable Long visitRecordId) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<VitalSign> vitalSigns = nurseUseCase.getVitalSignsByVisitRecord(visitRecordId);
            response.put("success", true);
            response.put("count", vitalSigns.size());
            response.put("data", vitalSigns);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al obtener signos vitales: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/medicine-administration")
    public ResponseEntity<Map<String, Object>> registerMedicineAdministration(@RequestBody Map<String, Object> requestBody) {
        Map<String, Object> response = new HashMap<>();
        try {
            MedicineAdministration medicineAdmin = medicineAdministrationBuilder.build(requestBody.get("visitRecordId") != null ? Long.valueOf(requestBody.get("visitRecordId").toString()) : null, (String) requestBody.get("patientId"), (String) requestBody.get("orderNumber"), requestBody.get("itemNumber") != null ? Integer.valueOf(requestBody.get("itemNumber").toString()) : null, (String) requestBody.get("medicineName"), (String) requestBody.get("doseAdministered"), (String) requestBody.get("administeredBy"), (String) requestBody.get("observations"));

            nurseUseCase.registerMedicineAdministration(medicineAdmin);
            response.put("success", true);
            response.put("message", "Administración de medicamento registrada exitosamente");
            response.put("data", medicineAdmin);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al registrar administración de medicamento: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/medicine-administration/patient/{patientId}")
    public ResponseEntity<Map<String, Object>> getMedicineAdministrationsByPatient(@PathVariable String patientId) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<MedicineAdministration> administrations = nurseUseCase.getMedicineAdministrationsByPatient(patientId);
            response.put("success", true);
            response.put("count", administrations.size());
            response.put("data", administrations);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al obtener administraciones de medicamento: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/medicine-administration/visit-record/{visitRecordId}")
    public ResponseEntity<Map<String, Object>> getMedicineAdministrationsByVisitRecord(@PathVariable Long visitRecordId) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<MedicineAdministration> administrations = nurseUseCase.getMedicineAdministrationsByVisitRecord(visitRecordId);
            response.put("success", true);
            response.put("count", administrations.size());
            response.put("data", administrations);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al obtener administraciones de medicamento: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/medicine-administration/order/{orderNumber}")
    public ResponseEntity<Map<String, Object>> getMedicineAdministrationsByOrder(@PathVariable String orderNumber) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<MedicineAdministration> administrations = nurseUseCase.getMedicineAdministrationsByOrder(orderNumber);
            response.put("success", true);
            response.put("count", administrations.size());
            response.put("data", administrations);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al obtener administraciones de medicamento: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/visit-records")
    public ResponseEntity<Map<String, Object>> createVisitRecord(@RequestBody Map<String, String> requestBody) {
        Map<String, Object> response = new HashMap<>();
        try {
            VisitRecord visitRecord = visitRecordBuilder.build(requestBody.get("patientId"), requestBody.get("doctorId"), requestBody.get("reason"), requestBody.get("symptoms"), requestBody.get("diagnosis"));

            nurseUseCase.createVisitRecord(visitRecord);
            response.put("success", true);
            response.put("message", "Registro de visita creado exitosamente");
            response.put("data", visitRecord);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al crear registro de visita: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/visit-records/{visitRecordId}")
    public ResponseEntity<Map<String, Object>> getVisitRecordById(@PathVariable Long visitRecordId) {
        Map<String, Object> response = new HashMap<>();
        try {
            VisitRecord visitRecord = nurseUseCase.getVisitRecordById(visitRecordId);
            response.put("success", true);
            response.put("data", visitRecord);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al obtener registro de visita: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/visit-records/patient/{patientId}")
    public ResponseEntity<Map<String, Object>> getVisitRecordsByPatient(@PathVariable String patientId) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<VisitRecord> visitRecords = nurseUseCase.getVisitRecordsByPatient(patientId);
            response.put("success", true);
            response.put("count", visitRecords.size());
            response.put("data", visitRecords);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al obtener registros de visita: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/visit-records/doctor/{doctorId}")
    public ResponseEntity<Map<String, Object>> getVisitRecordsByDoctor(@PathVariable String doctorId) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<VisitRecord> visitRecords = nurseUseCase.getVisitRecordsByDoctor(doctorId);
            response.put("success", true);
            response.put("count", visitRecords.size());
            response.put("data", visitRecords);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al obtener registros de visita: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

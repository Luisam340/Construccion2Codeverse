package app.code.verse.adapter.in.rest;

import app.code.verse.adapter.in.builder.OrderBuilder;
import app.code.verse.adapter.in.builder.VisitRecordBuilder;
import app.code.verse.application.usecases.AdministrativeStaffUseCase;
import app.code.verse.application.usecases.DoctorUseCase;
import app.code.verse.domain.model.*;
import app.code.verse.domain.ports.MedicalHistoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/hospital/san-rafael/doctors")
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('DOCTOR')")
public class DoctorRestController {

    @Autowired
    private DoctorUseCase doctorUseCase;

    @Autowired
    private AdministrativeStaffUseCase administrativeStaffUseCase;

    @Autowired
    private VisitRecordBuilder visitRecordBuilder;

    @Autowired
    private OrderBuilder orderBuilder;

    @Autowired
    private MedicalHistoryPort medicalHistoryPort;

    @PostMapping("/visit-records")
    public ResponseEntity<Map<String, Object>> createVisitRecord(@RequestBody Map<String, String> requestBody) {
        Map<String, Object> response = new HashMap<>();
        try {
            VisitRecord visitRecord = visitRecordBuilder.build(requestBody.get("patientId"), requestBody.get("doctorId"), requestBody.get("reason"), requestBody.get("symptoms"), requestBody.get("diagnosis"));

            medicalHistoryPort.addVisit(visitRecord.getPatientId(), visitRecord);

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

    @GetMapping("/medical-history/patient/{patientId}")
    public ResponseEntity<Map<String, Object>> getMedicalHistoryByPatient(@PathVariable String patientId) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Obtener información del paciente
            Patient patient = administrativeStaffUseCase.findById(patientId);
            Policy policy = administrativeStaffUseCase.getPolicy(patientId);
            EmergencyContact emergencyContact = administrativeStaffUseCase.getEmergencyContact(patientId);

            // Obtener historia clínica
            List<MedicalHistory> history = doctorUseCase.getMedicalHistoryByPatient(patientId);

            // Construir respuesta completa
            Map<String, Object> completeData = new HashMap<>();
            completeData.put("patient", patient);
            completeData.put("policy", policy);
            completeData.put("emergencyContact", emergencyContact);
            completeData.put("medicalHistory", history);

            response.put("success", true);
            response.put("data", completeData);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al obtener historia clínica: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/medical-history/patient/{patientId}/date/{date}")
    public ResponseEntity<Map<String, Object>> getMedicalHistoryByPatientAndDate(@PathVariable String patientId, @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Obtener información del paciente
            Patient patient = administrativeStaffUseCase.findById(patientId);
            Policy policy = administrativeStaffUseCase.getPolicy(patientId);
            EmergencyContact emergencyContact = administrativeStaffUseCase.getEmergencyContact(patientId);

            // Obtener historia clínica filtrada por fecha
            List<MedicalHistory> history = doctorUseCase.getMedicalHistoryByPatientAndDate(patientId, date);

            // Construir respuesta completa
            Map<String, Object> completeData = new HashMap<>();
            completeData.put("patient", patient);
            completeData.put("policy", policy);
            completeData.put("emergencyContact", emergencyContact);
            completeData.put("medicalHistory", history);
            completeData.put("filterDate", date);

            response.put("success", true);
            response.put("data", completeData);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al obtener historia clínica: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/medical-history/doctor/{doctorId}")
    public ResponseEntity<Map<String, Object>> getMedicalHistoryByDoctor(@PathVariable String doctorId) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<MedicalHistory> history = doctorUseCase.getMedicalHistoryByDoctor(doctorId);
            response.put("success", true);
            response.put("count", history.size());
            response.put("data", history);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al obtener historia clínica: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/orders")
    public ResponseEntity<Map<String, Object>> createOrder(@RequestBody Order orderRequest) {
        Map<String, Object> response = new HashMap<>();
        try {
            Order order = orderBuilder.build(orderRequest.getOrderNumber(), orderRequest.getPatientId(), orderRequest.getDoctorId(), orderRequest.getItems());
            doctorUseCase.createOrder(order);

            response.put("success", true);
            response.put("message", "Orden médica creada exitosamente");
            response.put("data", order);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException | IllegalStateException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al crear orden: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/orders")
    public ResponseEntity<Map<String, Object>> getAllOrders() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Order> orders = doctorUseCase.getAllOrders();
            response.put("success", true);
            response.put("count", orders.size());
            response.put("data", orders);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al obtener órdenes: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/orders/{orderNumber}")
    public ResponseEntity<Map<String, Object>> getOrderByNumber(@PathVariable String orderNumber) {
        Map<String, Object> response = new HashMap<>();
        try {
            Order order = doctorUseCase.getOrderByNumber(orderNumber);
            response.put("success", true);
            response.put("data", order);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al buscar orden: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/orders/patient/{patientId}")
    public ResponseEntity<Map<String, Object>> getOrdersByPatient(@PathVariable String patientId) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Order> orders = doctorUseCase.getOrdersByPatient(patientId);
            response.put("success", true);
            response.put("count", orders.size());
            response.put("data", orders);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al obtener órdenes: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/orders/doctor/{doctorId}")
    public ResponseEntity<Map<String, Object>> getOrdersByDoctor(@PathVariable String doctorId) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Order> orders = doctorUseCase.getOrdersByDoctor(doctorId);
            response.put("success", true);
            response.put("count", orders.size());
            response.put("data", orders);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al obtener órdenes: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

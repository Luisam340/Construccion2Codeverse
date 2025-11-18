package app.code.verse.adapter.in.rest;

import app.code.verse.adapter.in.builder.AppointmentBuilder;
import app.code.verse.adapter.in.validators.AppointmentValidator;
import app.code.verse.application.usecases.AdministrativeStaffUseCase;
import app.code.verse.domain.model.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/hospital/san-rafael/appointments")
@CrossOrigin(origins = "*")
public class AppointmentRestController {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    @Autowired
    private AdministrativeStaffUseCase administrativeStaffUseCase;

    @Autowired
    private AppointmentBuilder appointmentBuilder;

    @Autowired
    private AppointmentValidator appointmentValidator;

    @PostMapping
    @PreAuthorize("hasRole('PERSONAL_ADMINISTRATIVO')")
    public ResponseEntity<Map<String, Object>> createAppointment(@RequestBody Map<String, String> requestBody) {
        Map<String, Object> response = new HashMap<>();
        try {
            Appointment appointment = appointmentBuilder.build(
                    requestBody.get("patientId"),
                    requestBody.get("doctorId"),
                    LocalDate.parse(requestBody.get("appointmentDate"), DATE_FORMATTER),
                    LocalTime.parse(requestBody.get("appointmentTime"), TIME_FORMATTER),
                    requestBody.get("reason"),
                    requestBody.get("status"),
                    requestBody.get("notes"),
                    requestBody.get("createdBy")
            );

            Appointment createdAppointment = administrativeStaffUseCase.createAppointment(appointment);

            response.put("success", true);
            response.put("message", "Cita creada exitosamente");
            response.put("data", createdAppointment);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException | IllegalStateException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al crear cita: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('PERSONAL_ADMINISTRATIVO', 'DOCTOR', 'ENFERMERA')")
    public ResponseEntity<Map<String, Object>> getAllAppointments() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Appointment> appointments = administrativeStaffUseCase.getAllAppointments();
            response.put("success", true);
            response.put("count", appointments.size());
            response.put("data", appointments);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al obtener citas: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('PERSONAL_ADMINISTRATIVO', 'DOCTOR', 'ENFERMERA')")
    public ResponseEntity<Map<String, Object>> getAppointmentById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Appointment appointment = administrativeStaffUseCase.getAppointmentById(id);
            response.put("success", true);
            response.put("data", appointment);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al buscar cita: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAnyRole('PERSONAL_ADMINISTRATIVO', 'DOCTOR', 'ENFERMERA')")
    public ResponseEntity<Map<String, Object>> getAppointmentsByPatient(@PathVariable String patientId) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Appointment> appointments = administrativeStaffUseCase.getAppointmentsByPatient(patientId);
            response.put("success", true);
            response.put("count", appointments.size());
            response.put("data", appointments);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al obtener citas: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/doctor/{doctorId}")
    @PreAuthorize("hasAnyRole('PERSONAL_ADMINISTRATIVO', 'DOCTOR', 'ENFERMERA')")
    public ResponseEntity<Map<String, Object>> getAppointmentsByDoctor(@PathVariable String doctorId) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Appointment> appointments = administrativeStaffUseCase.getAppointmentsByDoctor(doctorId);
            response.put("success", true);
            response.put("count", appointments.size());
            response.put("data", appointments);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al obtener citas: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/date/{date}")
    @PreAuthorize("hasAnyRole('PERSONAL_ADMINISTRATIVO', 'DOCTOR', 'ENFERMERA')")
    public ResponseEntity<Map<String, Object>> getAppointmentsByDate(
            @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Appointment> appointments = administrativeStaffUseCase.getAppointmentsByDate(date);
            response.put("success", true);
            response.put("count", appointments.size());
            response.put("data", appointments);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al obtener citas: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/doctor/{doctorId}/date/{date}")
    @PreAuthorize("hasAnyRole('PERSONAL_ADMINISTRATIVO', 'DOCTOR', 'ENFERMERA')")
    public ResponseEntity<Map<String, Object>> getAppointmentsByDoctorAndDate(
            @PathVariable String doctorId,
            @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Appointment> appointments = administrativeStaffUseCase.getAppointmentsByDoctorAndDate(doctorId, date);
            response.put("success", true);
            response.put("count", appointments.size());
            response.put("data", appointments);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al obtener citas: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('PERSONAL_ADMINISTRATIVO')")
    public ResponseEntity<Map<String, Object>> updateAppointment(
            @PathVariable Long id,
            @RequestBody Map<String, String> requestBody) {
        Map<String, Object> response = new HashMap<>();
        try {
            Appointment existingAppointment = administrativeStaffUseCase.getAppointmentById(id);

            LocalDate appointmentDate = requestBody.containsKey("appointmentDate") && requestBody.get("appointmentDate") != null
                    ? LocalDate.parse(requestBody.get("appointmentDate"), DATE_FORMATTER)
                    : existingAppointment.getAppointmentDate();

            LocalTime appointmentTime = requestBody.containsKey("appointmentTime") && requestBody.get("appointmentTime") != null
                    ? LocalTime.parse(requestBody.get("appointmentTime"), TIME_FORMATTER)
                    : existingAppointment.getAppointmentTime();

            String reason = requestBody.getOrDefault("reason", existingAppointment.getReason());
            String status = requestBody.getOrDefault("status", existingAppointment.getStatus());
            String notes = requestBody.getOrDefault("notes", existingAppointment.getNotes());

            Appointment updatedAppointment = appointmentBuilder.update(
                    existingAppointment,
                    appointmentDate,
                    appointmentTime,
                    reason,
                    status,
                    notes
            );

            Appointment result = administrativeStaffUseCase.updateAppointment(updatedAppointment);

            response.put("success", true);
            response.put("message", "Cita actualizada exitosamente");
            response.put("data", result);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al actualizar cita: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}/cancel")
    @PreAuthorize("hasRole('PERSONAL_ADMINISTRATIVO')")
    public ResponseEntity<Map<String, Object>> cancelAppointment(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            administrativeStaffUseCase.cancelAppointment(id);
            response.put("success", true);
            response.put("message", "Cita cancelada exitosamente");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al cancelar cita: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}/complete")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ENFERMERA')")
    public ResponseEntity<Map<String, Object>> completeAppointment(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            administrativeStaffUseCase.completeAppointment(id);
            response.put("success", true);
            response.put("message", "Cita marcada como completada");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al completar cita: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('PERSONAL_ADMINISTRATIVO')")
    public ResponseEntity<Map<String, Object>> deleteAppointment(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Appointment appointment = administrativeStaffUseCase.getAppointmentById(id);
            administrativeStaffUseCase.deleteAppointment(appointment);
            response.put("success", true);
            response.put("message", "Cita eliminada exitosamente");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al eliminar cita: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/available-times")
    @PreAuthorize("hasAnyRole('PERSONAL_ADMINISTRATIVO', 'DOCTOR', 'ENFERMERA')")
    public ResponseEntity<Map<String, Object>> getAvailableTimes() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<LocalTime> availableTimes = AppointmentValidator.getAvailableTimes();
            response.put("success", true);
            response.put("data", availableTimes);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al obtener horarios disponibles: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

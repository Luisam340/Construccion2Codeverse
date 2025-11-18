package app.code.verse.adapter.in.validators;

import app.code.verse.domain.model.enums.AppointmentStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@Component
public class AppointmentValidator extends SimpleValidator {

    // Horarios disponibles: de 8:00 AM a 6:00 PM cada hora
    private static final List<LocalTime> AVAILABLE_TIMES = Arrays.asList(
            LocalTime.of(8, 0),
            LocalTime.of(9, 0),
            LocalTime.of(10, 0),
            LocalTime.of(11, 0),
            LocalTime.of(12, 0),
            LocalTime.of(13, 0),
            LocalTime.of(14, 0),
            LocalTime.of(15, 0),
            LocalTime.of(16, 0),
            LocalTime.of(17, 0),
            LocalTime.of(18, 0)
    );

    public LocalDate appointmentDateValidator(LocalDate appointmentDate) {
        if (appointmentDate == null) {
            throw new IllegalArgumentException("La fecha de la cita no puede ser nula");
        }

        if (appointmentDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de la cita no puede ser en el pasado");
        }

        if (appointmentDate.isAfter(LocalDate.now().plusMonths(6))) {
            throw new IllegalArgumentException("La fecha de la cita no puede ser mayor a 6 meses en el futuro");
        }

        return appointmentDate;
    }

    public LocalTime appointmentTimeValidator(LocalTime appointmentTime) {
        if (appointmentTime == null) {
            throw new IllegalArgumentException("La hora de la cita no puede ser nula");
        }

        if (!AVAILABLE_TIMES.contains(appointmentTime)) {
            throw new IllegalArgumentException(
                    "La hora de la cita debe ser una hora en punto entre las 8:00 AM y las 6:00 PM (8:00, 9:00, 10:00, ..., 18:00)"
            );
        }

        return appointmentTime;
    }

    public String reasonValidator(String reason) throws Exception {
        stringValidator(reason, "motivo de la cita");
        maximumSizeValidator(reason, "motivo de la cita", 200);
        return reason;
    }

    public String statusValidator(String value) {
        if (value == null || value.isBlank()) {
            return AppointmentStatus.SCHEDULED.getStatus();
        }

        switch (value.toUpperCase()) {
            case "SCHEDULED":
            case "PROGRAMADA":
                return AppointmentStatus.SCHEDULED.getStatus();
            case "COMPLETED":
            case "COMPLETADA":
                return AppointmentStatus.COMPLETED.getStatus();
            case "CANCELLED":
            case "CANCELADA":
                return AppointmentStatus.CANCELLED.getStatus();
            default:
                throw new IllegalArgumentException(
                        "Estado inválido. Debe ser: SCHEDULED, COMPLETED o CANCELLED"
                );
        }
    }

    public String notesValidator(String notes) {
        if (notes != null && !notes.isBlank()) {
            if (notes.length() > 500) {
                throw new IllegalArgumentException("Las notas no pueden exceder 500 caracteres");
            }
        }
        return notes;
    }

    public String patientIdValidator(String patientId) throws Exception {
        return stringValidator(patientId, "ID del paciente");
    }

    public String doctorIdValidator(String doctorId) throws Exception {
        return stringValidator(doctorId, "ID del médico");
    }

    public String createdByValidator(String createdBy) throws Exception {
        return stringValidator(createdBy, "creador de la cita");
    }

    public static List<LocalTime> getAvailableTimes() {
        return AVAILABLE_TIMES;
    }
}

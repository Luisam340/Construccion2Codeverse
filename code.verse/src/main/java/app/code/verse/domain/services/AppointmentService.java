package app.code.verse.domain.services;

import app.code.verse.domain.model.Appointment;
import app.code.verse.domain.model.enums.AppointmentStatus;
import app.code.verse.domain.ports.AppointmentPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentPort appointmentPort;

    // Crea una cita médica validando que el doctor esté disponible en la fecha y hora especificada
    public Appointment createAppointment(Appointment appointment) {
        validateDoctorAvailability(appointment.getDoctorId(), appointment.getAppointmentDate(), appointment.getAppointmentTime());
        return appointmentPort.save(appointment);
    }

    // Actualiza una cita médica validando disponibilidad del doctor si cambió fecha, hora o doctor
    public Appointment updateAppointment(Appointment appointment) throws Exception {
        Appointment existingAppointment = appointmentPort.findById(appointment.getIdAppointment());

        if (existingAppointment == null) {
            throw new IllegalArgumentException("Cita no encontrada con ID: " + appointment.getIdAppointment());
        }

        // Verifica si cambió la fecha, hora o el doctor asignado
        boolean dateOrTimeChanged = !existingAppointment.getAppointmentDate().equals(appointment.getAppointmentDate()) || !existingAppointment.getAppointmentTime().equals(appointment.getAppointmentTime());
        boolean doctorChanged = !existingAppointment.getDoctorId().equals(appointment.getDoctorId());

        if (dateOrTimeChanged || doctorChanged) {
            validateDoctorAvailability(appointment.getDoctorId(), appointment.getAppointmentDate(), appointment.getAppointmentTime());
        }

        return appointmentPort.save(appointment);
    }

    // Cancela una cita médica cambiando su estado a "Cancelada"
    public void cancelAppointment(Long appointmentId) throws Exception {
        Appointment appointment = appointmentPort.findById(appointmentId);
        if (appointment == null) {
            throw new IllegalArgumentException("Cita no encontrada con ID: " + appointmentId);
        }
        appointment.setStatus(AppointmentStatus.CANCELLED.getStatus());
        appointmentPort.save(appointment);
    }

    // Marca una cita médica como completada cambiando su estado a "Completada"
    public void completeAppointment(Long appointmentId) throws Exception {
        Appointment appointment = appointmentPort.findById(appointmentId);
        if (appointment == null) {
            throw new IllegalArgumentException("Cita no encontrada con ID: " + appointmentId);
        }
        appointment.setStatus(AppointmentStatus.COMPLETED.getStatus());
        appointmentPort.save(appointment);
    }

    // Valida que el doctor no tenga otra cita programada en la misma fecha y hora
    private void validateDoctorAvailability(String doctorId, LocalDate date, LocalTime time) {
        boolean exists = appointmentPort.existsByDoctorAndDateTime(doctorId, date, time);
        if (exists) {
            throw new IllegalStateException(String.format("El doctor ya tiene una cita programada para el %s a las %s", date, time));
        }
    }
}

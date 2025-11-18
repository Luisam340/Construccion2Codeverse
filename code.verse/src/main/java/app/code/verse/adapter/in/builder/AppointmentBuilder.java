package app.code.verse.adapter.in.builder;

import app.code.verse.adapter.in.validators.AppointmentValidator;
import app.code.verse.domain.model.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class AppointmentBuilder {

    @Autowired
    private AppointmentValidator appointmentValidator;

    public Appointment build(String patientId, String doctorId, LocalDate appointmentDate, LocalTime appointmentTime, String reason, String status, String notes, String createdBy) throws Exception {
        Appointment appointment = new Appointment();
        appointment.setPatientId(appointmentValidator.patientIdValidator(patientId));
        appointment.setDoctorId(appointmentValidator.doctorIdValidator(doctorId));
        appointment.setAppointmentDate(appointmentValidator.appointmentDateValidator(appointmentDate));
        appointment.setAppointmentTime(appointmentValidator.appointmentTimeValidator(appointmentTime));
        appointment.setReason(appointmentValidator.reasonValidator(reason));
        appointment.setStatus(appointmentValidator.statusValidator(status));
        appointment.setNotes(appointmentValidator.notesValidator(notes));
        appointment.setCreatedAt(LocalDateTime.now());
        appointment.setCreatedBy(appointmentValidator.createdByValidator(createdBy));
        return appointment;
    }

    public Appointment update(Appointment appointment, LocalDate appointmentDate, LocalTime appointmentTime, String reason, String status, String notes) throws Exception {
        if (appointmentDate != null) {
            appointment.setAppointmentDate(appointmentValidator.appointmentDateValidator(appointmentDate));
        }
        if (appointmentTime != null) {
            appointment.setAppointmentTime(appointmentValidator.appointmentTimeValidator(appointmentTime));
        }
        if (reason != null) {
            appointment.setReason(appointmentValidator.reasonValidator(reason));
        }
        if (status != null) {
            appointment.setStatus(appointmentValidator.statusValidator(status));
        }
        if (notes != null) {
            appointment.setNotes(appointmentValidator.notesValidator(notes));
        }
        return appointment;
    }
}

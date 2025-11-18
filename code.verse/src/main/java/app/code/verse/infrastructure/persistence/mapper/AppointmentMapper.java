package app.code.verse.infrastructure.persistence.mapper;

import app.code.verse.domain.model.Appointment;
import app.code.verse.infrastructure.persistence.entities.AppointmentEntity;
import org.springframework.stereotype.Component;

@Component
public class AppointmentMapper {

    public AppointmentEntity toEntity(Appointment appointment) {
        if (appointment == null) {
            return null;
        }

        AppointmentEntity entity = new AppointmentEntity();
        entity.setIdAppointment(appointment.getIdAppointment());
        entity.setPatientId(appointment.getPatientId());
        entity.setDoctorId(appointment.getDoctorId());
        entity.setAppointmentDate(appointment.getAppointmentDate());
        entity.setAppointmentTime(appointment.getAppointmentTime());
        entity.setReason(appointment.getReason());
        entity.setStatus(appointment.getStatus());
        entity.setNotes(appointment.getNotes());
        entity.setCreatedAt(appointment.getCreatedAt());
        entity.setCreatedBy(appointment.getCreatedBy());
        return entity;
    }

    public Appointment toDomain(AppointmentEntity entity) {
        if (entity == null) {
            return null;
        }

        Appointment appointment = new Appointment();
        appointment.setIdAppointment(entity.getIdAppointment());
        appointment.setPatientId(entity.getPatientId());
        appointment.setDoctorId(entity.getDoctorId());
        appointment.setAppointmentDate(entity.getAppointmentDate());
        appointment.setAppointmentTime(entity.getAppointmentTime());
        appointment.setReason(entity.getReason());
        appointment.setStatus(entity.getStatus());
        appointment.setNotes(entity.getNotes());
        appointment.setCreatedAt(entity.getCreatedAt());
        appointment.setCreatedBy(entity.getCreatedBy());
        return appointment;
    }
}

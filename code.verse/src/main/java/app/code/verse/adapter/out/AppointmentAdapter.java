package app.code.verse.adapter.out;

import app.code.verse.domain.model.Appointment;
import app.code.verse.domain.ports.AppointmentPort;
import app.code.verse.infrastructure.persistence.entities.AppointmentEntity;
import app.code.verse.infrastructure.persistence.mapper.AppointmentMapper;
import app.code.verse.infrastructure.persistence.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AppointmentAdapter implements AppointmentPort {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AppointmentMapper appointmentMapper;

    @Override
    public Appointment save(Appointment appointment) {
        AppointmentEntity entity = appointmentMapper.toEntity(appointment);
        AppointmentEntity savedEntity = appointmentRepository.save(entity);
        return appointmentMapper.toDomain(savedEntity);
    }

    @Override
    public Appointment findById(Long id) throws Exception {
        AppointmentEntity entity = appointmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cita no encontrada con ID: " + id));
        return appointmentMapper.toDomain(entity);
    }

    @Override
    public List<Appointment> findAll() {
        return appointmentRepository.findAll()
                .stream()
                .map(appointmentMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Appointment> findByPatientId(String patientId) {
        return appointmentRepository.findByPatientId(patientId)
                .stream()
                .map(appointmentMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Appointment> findByDoctorId(String doctorId) {
        return appointmentRepository.findByDoctorId(doctorId)
                .stream()
                .map(appointmentMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Appointment> findByDate(LocalDate date) {
        return appointmentRepository.findByAppointmentDate(date)
                .stream()
                .map(appointmentMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Appointment> findByDoctorAndDate(String doctorId, LocalDate date) {
        return appointmentRepository.findByDoctorIdAndAppointmentDate(doctorId, date)
                .stream()
                .map(appointmentMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByDoctorAndDateTime(String doctorId, LocalDate date, LocalTime time) {
        List<AppointmentEntity> appointments = appointmentRepository.findByDoctorIdAndAppointmentDate(doctorId, date);
        return appointments.stream()
                .anyMatch(a -> a.getAppointmentTime().equals(time) && !a.getStatus().equals("Cancelada"));
    }

    @Override
    public void delete(Appointment appointment) {
        AppointmentEntity entity = appointmentMapper.toEntity(appointment);
        appointmentRepository.delete(entity);
    }
}

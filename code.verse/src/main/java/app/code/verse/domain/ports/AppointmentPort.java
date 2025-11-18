package app.code.verse.domain.ports;

import app.code.verse.domain.model.Appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentPort {
    Appointment save(Appointment appointment);
    Appointment findById(Long id) throws Exception;
    List<Appointment> findAll();
    List<Appointment> findByPatientId(String patientId);
    List<Appointment> findByDoctorId(String doctorId);
    List<Appointment> findByDate(LocalDate date);
    List<Appointment> findByDoctorAndDate(String doctorId, LocalDate date);
    boolean existsByDoctorAndDateTime(String doctorId, LocalDate date, LocalTime time);
    void delete(Appointment appointment);
}

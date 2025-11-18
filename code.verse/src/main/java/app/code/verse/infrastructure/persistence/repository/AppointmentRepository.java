package app.code.verse.infrastructure.persistence.repository;

import app.code.verse.infrastructure.persistence.entities.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {
    List<AppointmentEntity> findByPatientId(String patientId);
    List<AppointmentEntity> findByDoctorId(String doctorId);
    List<AppointmentEntity> findByAppointmentDate(LocalDate appointmentDate);
    List<AppointmentEntity> findByDoctorIdAndAppointmentDate(String doctorId, LocalDate appointmentDate);
}

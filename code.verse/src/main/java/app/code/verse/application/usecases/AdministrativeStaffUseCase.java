package app.code.verse.application.usecases;

import app.code.verse.domain.model.Appointment;
import app.code.verse.domain.model.EmergencyContact;
import app.code.verse.domain.model.Patient;
import app.code.verse.domain.model.Policy;
import app.code.verse.domain.ports.AppointmentPort;
import app.code.verse.domain.ports.EmergencyContactPort;
import app.code.verse.domain.ports.PatientPort;
import app.code.verse.domain.ports.PolicyPort;
import app.code.verse.domain.services.AppointmentService;
import app.code.verse.domain.services.RegisterEmergencyContact;
import app.code.verse.domain.services.RegisterPatient;
import app.code.verse.domain.services.RegisterPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class AdministrativeStaffUseCase {
    @Autowired
    private PatientPort patientPort;
    @Autowired
    private RegisterPatient registerPatient;
    @Autowired
    private RegisterPolicy registerPolicy;
    @Autowired
    private RegisterEmergencyContact registerEmergencyContact;
    @Autowired
    private PolicyPort policyPort;
    @Autowired
    private EmergencyContactPort emergencyContactPort;
    @Autowired
    private AppointmentPort appointmentPort;
    @Autowired
    private AppointmentService appointmentService;

    // Crea un paciente con su póliza y contacto de emergencia asociados
    public void create(Patient patient, Policy policy, EmergencyContact emergencyContact) throws Exception {
        registerPatient.create(patient);
        if (policy != null) {
            registerPolicy.create(patient.getIdNumber(), policy);
        }
        if (emergencyContact != null) {
            registerEmergencyContact.create(patient.getIdNumber(), emergencyContact);
        }
    }

    // Obtiene todos los pacientes registrados con sus pólizas y contactos de emergencia
    public List<Patient> getAllPatient() throws Exception {
        return patientPort.findAll();
    }

    // Busca pacientes por nombre (búsqueda parcial, insensible a mayúsculas)
    public List<Patient> findByName(String name) throws Exception {
        List<Patient> patients = patientPort.findByNameContainingIgnoreCase(name);
        if (patients == null || patients.isEmpty()) {
            throw new IllegalArgumentException("Paciente no encontrado123");
        }
        return patients;
    }

    // Busca un paciente por su número de documento de identidad
    public Patient findById(String document) throws Exception {
        Patient patient = patientPort.findByIdNumber(document);
        if (patient == null) {
            throw new IllegalArgumentException("Paciente no encontrado");
        }
        return patient;
    }

    // Actualiza los datos de un paciente existente
    public Patient updatePatient(Patient patient) throws Exception {
        if (findById(patient.getIdNumber()) == null) {
            throw new IllegalArgumentException("Paciente no encontrado");
        }
        patientPort.update(patient);
        return patient;
    }

    // Elimina un paciente y sus datos asociados (póliza y contacto de emergencia)
    public void deletePatient(Patient patient) throws Exception {
        if (findById(patient.getIdNumber()) == null) {
            throw new IllegalArgumentException("Paciente no encontrado");
        }
        deletePolicy(patient.getIdNumber());
        deleteEmergencyContact(patient.getIdNumber());
        patientPort.deleteById(patient);
    }

    // Crea una póliza de seguro para un paciente
    public void createPolicy(Policy policy, Patient patient) throws Exception {
        registerPolicy.create(patient.getIdNumber(), policy);
    }

    // Crea un contacto de emergencia para un paciente
    public void createEmergencyContact(Patient patient, EmergencyContact emergencyContact) throws Exception {
        registerEmergencyContact.create(patient.getIdNumber(), emergencyContact);
    }

    // Actualiza la póliza de seguro de un paciente
    public void updatePolicy(String patientIdNumber, Policy policy) {
        policyPort.update(patientIdNumber, policy);
    }

    // Actualiza el contacto de emergencia de un paciente
    public void updateEmergencyContact(String patientIdNumber, EmergencyContact emergencyContact) {
        emergencyContactPort.update(patientIdNumber, emergencyContact);
    }

    // Elimina la póliza de seguro de un paciente
    public void deletePolicy(String patientIdNumber) {
        if (policyPort.findByPatient(patientIdNumber) != null) {
            policyPort.delete(patientIdNumber);
        }
    }

    // Elimina el contacto de emergencia de un paciente
    public void deleteEmergencyContact(String patientIdNumber) {
        if (emergencyContactPort.findByPatient(patientIdNumber) != null) {
            emergencyContactPort.delete(patientIdNumber);
        }
    }

    // Obtiene la póliza de seguro de un paciente
    public Policy getPolicy(String patientIdNumber) {
        return policyPort.findByPatient(patientIdNumber);
    }

    // Obtiene el contacto de emergencia de un paciente
    public EmergencyContact getEmergencyContact(String patientIdNumber) {
        return emergencyContactPort.findByPatient(patientIdNumber);
    }

    // Crea una cita médica para un paciente con un doctor
    public Appointment createAppointment(Appointment appointment) {
        return appointmentService.createAppointment(appointment);
    }

    // Actualiza los datos de una cita médica existente
    public Appointment updateAppointment(Appointment appointment) throws Exception {
        return appointmentService.updateAppointment(appointment);
    }

    // Cancela una cita médica cambiando su estado a "Cancelada"
    public void cancelAppointment(Long appointmentId) throws Exception {
        appointmentService.cancelAppointment(appointmentId);
    }

    // Marca una cita médica como completada
    public void completeAppointment(Long appointmentId) throws Exception {
        appointmentService.completeAppointment(appointmentId);
    }

    // Obtiene una cita médica por su ID
    public Appointment getAppointmentById(Long id) throws Exception {
        return appointmentPort.findById(id);
    }

    // Obtiene todas las citas médicas registradas en el sistema
    public List<Appointment> getAllAppointments() {
        return appointmentPort.findAll();
    }

    // Obtiene todas las citas médicas de un paciente específico
    public List<Appointment> getAppointmentsByPatient(String patientId) {
        return appointmentPort.findByPatientId(patientId);
    }

    // Obtiene todas las citas médicas de un doctor específico
    public List<Appointment> getAppointmentsByDoctor(String doctorId) {
        return appointmentPort.findByDoctorId(doctorId);
    }

    // Obtiene todas las citas médicas programadas para una fecha específica
    public List<Appointment> getAppointmentsByDate(LocalDate date) {
        return appointmentPort.findByDate(date);
    }

    // Obtiene las citas de un doctor en una fecha específica
    public List<Appointment> getAppointmentsByDoctorAndDate(String doctorId, LocalDate date) {
        return appointmentPort.findByDoctorAndDate(doctorId, date);
    }

    // Elimina permanentemente una cita médica del sistema
    public void deleteAppointment(Appointment appointment) {
        appointmentPort.delete(appointment);
    }
}
package app.code.verse.application.usecases;

import app.code.verse.domain.model.MedicalHistory;
import app.code.verse.domain.model.Order;
import app.code.verse.domain.ports.DoctorPort;
import app.code.verse.domain.ports.OrderPort;
import app.code.verse.domain.ports.MedicalHistoryPort;
import app.code.verse.domain.services.CreateMedicalHistory;
import app.code.verse.domain.services.AddOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorUseCase {

    @Autowired
    private DoctorPort doctorPort;

    @Autowired
    private OrderPort orderPort;

    @Autowired
    private MedicalHistoryPort medicalHistoryPort;

    @Autowired
    private CreateMedicalHistory createMedicalHistory;

    @Autowired
    private AddOrder addOrder;

    // Crea un registro en la historia clínica de un paciente
    public void createMedicalHistoryRecord(MedicalHistory medicalHistory) throws Exception {
        if (medicalHistory == null) {
            throw new IllegalArgumentException("La historia clínica no puede ser nula");
        }
        if (medicalHistory.getIdPatient() == null || medicalHistory.getIdPatient().isEmpty()) {
            throw new IllegalArgumentException("El ID del paciente es requerido");
        }
        createMedicalHistory.create(medicalHistory);
    }

    // Obtiene la historia clínica completa de un paciente por su ID
    public List<MedicalHistory> getMedicalHistoryByPatient(String patientId) throws Exception {
        if (patientId == null || patientId.isEmpty()) {
            throw new IllegalArgumentException("ID de paciente inválido");
        }
        List<MedicalHistory> history = medicalHistoryPort.findByPatient(patientId);
        if (history == null || history.isEmpty()) {
            throw new IllegalArgumentException("No se encontró historia clínica para este paciente");
        }
        return history;
    }

    // Obtiene la historia clínica de un paciente filtrada por una fecha específica
    public List<MedicalHistory> getMedicalHistoryByPatientAndDate(String patientId, LocalDate date) throws Exception {
        if (patientId == null || patientId.isEmpty()) {
            throw new IllegalArgumentException("ID de paciente inválido");
        }
        if (date == null) {
            throw new IllegalArgumentException("La fecha es requerida");
        }
        String dateStr = date.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        MedicalHistory history = medicalHistoryPort.findByPatientAndDate(patientId, dateStr);
        List<MedicalHistory> result = new ArrayList<>();
        if (history != null) {
            result.add(history);
        }
        return result;
    }

    // Obtiene todas las historias clínicas de los pacientes atendidos por un doctor específico
    public List<MedicalHistory> getMedicalHistoryByDoctor(String doctorId) throws Exception {
        if (doctorId == null || doctorId.isEmpty()) {
            throw new IllegalArgumentException("ID de médico inválido");
        }
        return medicalHistoryPort.findByDoctor(doctorId);
    }

    // Crea una orden médica (medicamentos, procedimientos o exámenes diagnósticos)
    public void createOrder(Order order) throws Exception {
        if (order == null) {
            throw new IllegalArgumentException("La orden no puede ser nula");
        }
        if (order.getOrderNumber() == null || order.getOrderNumber().isEmpty()) {
            throw new IllegalArgumentException("El número de orden es requerido");
        }
        if (order.getOrderNumber().length() > 6) {
            throw new IllegalArgumentException("El número de orden no puede exceder 6 dígitos");
        }
        if (order.getPatientId() == null || order.getPatientId().isEmpty()) {
            throw new IllegalArgumentException("El ID del paciente es requerido");
        }
        if (order.getDoctorId() == null || order.getDoctorId().isEmpty()) {
            throw new IllegalArgumentException("El ID del médico es requerido");
        }
        addOrder.add(order);
    }

    // Obtiene una orden médica por su número único
    public Order getOrderByNumber(String orderNumber) throws Exception {
        if (orderNumber == null || orderNumber.isEmpty()) {
            throw new IllegalArgumentException("Número de orden inválido");
        }
        Order order = orderPort.findByOrderNumber(orderNumber);
        if (order == null) {
            throw new IllegalArgumentException("Orden no encontrada");
        }
        return order;
    }

    // Obtiene todas las órdenes médicas de un paciente específico
    public List<Order> getOrdersByPatient(String patientId) throws Exception {
        if (patientId == null || patientId.isEmpty()) {
            throw new IllegalArgumentException("ID de paciente inválido");
        }
        return orderPort.findByPatient(patientId);
    }

    // Obtiene todas las órdenes médicas creadas por un doctor específico
    public List<Order> getOrdersByDoctor(String doctorId) throws Exception {
        if (doctorId == null || doctorId.isEmpty()) {
            throw new IllegalArgumentException("ID de médico inválido");
        }
        return orderPort.findByDoctor(doctorId);
    }

    // Obtiene todas las órdenes médicas registradas en el sistema
    public List<Order> getAllOrders() {
        return orderPort.findAll();
    }
}

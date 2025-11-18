package app.code.verse.application.usecases;

import app.code.verse.domain.model.VitalSign;
import app.code.verse.domain.model.MedicineAdministration;
import app.code.verse.domain.model.VisitRecord;
import app.code.verse.domain.ports.NursePort;
import app.code.verse.domain.services.RegisterVitalSign;
import app.code.verse.domain.services.RegisterMedicineAdministration;
import app.code.verse.domain.services.VisitRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NurseUseCase {

    @Autowired
    private NursePort nursePort;

    @Autowired
    private RegisterVitalSign registerVitalSign;

    @Autowired
    private RegisterMedicineAdministration registerMedicineAdministration;

    @Autowired
    private VisitRecordService visitRecordService;

    // Registra los signos vitales de un paciente validando que no sean nulos
    public void registerVitalSigns(VitalSign vitalSign) throws Exception {
        if (vitalSign == null) {
            throw new IllegalArgumentException("Los signos vitales no pueden ser nulos");
        }
        registerVitalSign.register(vitalSign);
    }

    // Obtiene los signos vitales registrados para un paciente específico
    public List<VitalSign> getVitalSignsByPatient(String patientId) throws Exception {
        if (patientId == null || patientId.isEmpty()) {
            throw new IllegalArgumentException("ID de paciente inválido");
        }
        return nursePort.findVitalSignsByPatient(patientId);
    }

    // Obtiene los signos vitales asociados a un registro de visita específico
    public List<VitalSign> getVitalSignsByVisitRecord(Long visitRecordId) throws Exception {
        if (visitRecordId == null) {
            throw new IllegalArgumentException("ID de registro de visita inválido");
        }
        return nursePort.findVitalSignsByVisitRecord(visitRecordId);
    }

    // Registra la administración de un medicamento a un paciente validando que no sea nulo
    public void registerMedicineAdministration(MedicineAdministration medicineAdministration) throws Exception {
        if (medicineAdministration == null) {
            throw new IllegalArgumentException("La administración de medicamento no puede ser nula");
        }
        registerMedicineAdministration.register(medicineAdministration);
    }

    // Obtiene el historial de administración de medicamentos para un paciente específico
    public List<MedicineAdministration> getMedicineAdministrationsByPatient(String patientId) throws Exception {
        if (patientId == null || patientId.isEmpty()) {
            throw new IllegalArgumentException("ID de paciente inválido");
        }
        return nursePort.findMedicineAdministrationsByPatient(patientId);
    }

    // Obtiene la administración de medicamentos asociados a un registro de visita específico
    public List<MedicineAdministration> getMedicineAdministrationsByVisitRecord(Long visitRecordId) throws Exception {
        if (visitRecordId == null) {
            throw new IllegalArgumentException("ID de registro de visita inválido");
        }
        return nursePort.findMedicineAdministrationsByVisitRecord(visitRecordId);
    }

    // Obtiene la administración de medicamentos asociados a una orden específica
    public List<MedicineAdministration> getMedicineAdministrationsByOrder(String orderNumber) throws Exception {
        if (orderNumber == null || orderNumber.isEmpty()) {
            throw new IllegalArgumentException("Número de orden inválido");
        }
        return nursePort.findMedicineAdministrationsByOrder(orderNumber);
    }

    // Crea un nuevo registro de visita para documentar la atención de un paciente
    public void createVisitRecord(VisitRecord visitRecord) throws Exception {
        if (visitRecord == null) {
            throw new IllegalArgumentException("El registro de visita no puede ser nulo");
        }
        visitRecordService.createVisitRecord(visitRecord);
    }

    // Obtiene un registro de visita específico por su identificador
    public VisitRecord getVisitRecordById(Long visitRecordId) throws Exception {
        if (visitRecordId == null) {
            throw new IllegalArgumentException("ID de registro de visita inválido");
        }
        VisitRecord visitRecord = visitRecordService.findById(visitRecordId);
        if (visitRecord == null) {
            throw new IllegalArgumentException("Registro de visita no encontrado");
        }
        return visitRecord;
    }

    // Obtiene todos los registros de visita asociados a un paciente específico
    public List<VisitRecord> getVisitRecordsByPatient(String patientId) throws Exception {
        if (patientId == null || patientId.isEmpty()) {
            throw new IllegalArgumentException("ID de paciente inválido");
        }
        return visitRecordService.findByPatient(patientId);
    }

    // Obtiene todos los registros de visita realizados por un médico específico
    public List<VisitRecord> getVisitRecordsByDoctor(String doctorId) throws Exception {
        if (doctorId == null || doctorId.isEmpty()) {
            throw new IllegalArgumentException("ID de médico inválido");
        }
        return visitRecordService.findByDoctor(doctorId);
    }
}

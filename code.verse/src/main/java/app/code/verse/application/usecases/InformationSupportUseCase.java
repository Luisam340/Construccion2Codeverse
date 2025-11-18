package app.code.verse.application.usecases;

import app.code.verse.domain.model.MedicineInventory;
import app.code.verse.domain.model.ProcedureInventory;
import app.code.verse.domain.model.DiagnosticTestInventory;
import app.code.verse.domain.model.Specialty;
import app.code.verse.domain.ports.MedicineInventoryPort;
import app.code.verse.domain.ports.ProcedureInventoryPort;
import app.code.verse.domain.ports.DiagnosticTestInventoryPort;
import app.code.verse.domain.ports.SpecialtyPort;
import app.code.verse.domain.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InformationSupportUseCase {

    @Autowired
    private MedicineInventoryPort medicineInventoryPort;

    @Autowired
    private ProcedureInventoryPort procedureInventoryPort;

    @Autowired
    private DiagnosticTestInventoryPort diagnosticTestInventoryPort;

    @Autowired
    private SpecialtyPort specialtyPort;

    @Autowired
    private SpecialtyService specialtyService;

    @Autowired
    private CreateMedicineInventory createMedicineInventory;

    @Autowired
    private CreateProcedureInventory createProcedureInventory;

    @Autowired
    private CreateDiagnosticTestInventory createDiagnosticTestInventory;

    // Crea un nuevo medicamento en el inventario del sistema
    public void createMedicine(MedicineInventory medicine) throws Exception {
        createMedicineInventory.createMedicineInventory(medicine);
    }

    // Actualiza la información de un medicamento existente en el inventario validando su existencia
    public void updateMedicine(MedicineInventory medicine) throws Exception {
        MedicineInventory existing = medicineInventoryPort.findById(medicine.getIdMedicine());
        if (existing == null) {
            throw new IllegalArgumentException("El medicamento no existe");
        }
        medicineInventoryPort.save(medicine);
    }

    // Elimina un medicamento del inventario del sistema validando su existencia
    public void deleteMedicine(String medicineId) throws Exception {
        MedicineInventory medicineInventory = medicineInventoryPort.findById(medicineId);
        if (medicineInventory == null) {
            throw new IllegalArgumentException("El medicamento no existe");
        }
        medicineInventoryPort.saveDelete(medicineInventory);
    }

    // Busca un medicamento específico por su identificador en el inventario
    public MedicineInventory findMedicineById(String medicineId) throws Exception {
        MedicineInventory medicine = medicineInventoryPort.findById(medicineId);
        if (medicine == null) {
            throw new IllegalArgumentException("Medicamento no encontrado");
        }
        return medicine;
    }

    // Obtiene la lista completa de todos los medicamentos disponibles en el inventario
    public List<MedicineInventory> getAllMedicines() {
        return medicineInventoryPort.findAll();
    }

    // Busca medicamentos por nombre, retornando todos los que contengan el término de búsqueda
    public List<MedicineInventory> searchMedicinesByName(String name) {
        return medicineInventoryPort.findByNameContaining(name);
    }

    // Crea un nuevo procedimiento en el inventario del sistema
    public void createProcedure(ProcedureInventory procedure) throws Exception {
        createProcedureInventory.createProcedureInventory(procedure);
    }

    // Actualiza la información de un procedimiento existente en el inventario validando su existencia
    public void updateProcedure(ProcedureInventory procedure) throws Exception {
        ProcedureInventory existing = procedureInventoryPort.findById(procedure.getIdProcedure());
        if (existing == null) {
            throw new IllegalArgumentException("El procedimiento no existe");
        }
        procedureInventoryPort.save(procedure);
    }

    // Elimina un procedimiento del inventario del sistema validando su existencia
    public void deleteProcedure(String procedureId) throws Exception {
        ProcedureInventory procedureInventory = procedureInventoryPort.findById(procedureId);
        if (procedureInventory == null) {
            throw new IllegalArgumentException("El procedimiento no existe");
        }
        procedureInventoryPort.saveDelete(procedureInventory);
    }

    // Busca un procedimiento específico por su identificador en el inventario
    public ProcedureInventory findProcedureById(String procedureId) throws Exception {
        ProcedureInventory procedure = procedureInventoryPort.findById(procedureId);
        if (procedure == null) {
            throw new IllegalArgumentException("Procedimiento no encontrado");
        }
        return procedure;
    }

    // Obtiene la lista completa de todos los procedimientos disponibles en el inventario
    public List<ProcedureInventory> getAllProcedures() {
        return procedureInventoryPort.findAll();
    }

    // Busca procedimientos por nombre, retornando todos los que contengan el término de búsqueda
    public List<ProcedureInventory> searchProceduresByName(String name) {
        return procedureInventoryPort.findByNameContaining(name);
    }

    // Crea un nuevo examen diagnóstico en el inventario del sistema
    public void createDiagnosticTest(DiagnosticTestInventory diagnosticTest) throws Exception {
        createDiagnosticTestInventory.createDiagnosticTestInventory(diagnosticTest);
    }

    // Actualiza la información de un examen diagnóstico existente en el inventario validando su existencia
    public void updateDiagnosticTest(DiagnosticTestInventory diagnosticTest) throws Exception {
        DiagnosticTestInventory existing = diagnosticTestInventoryPort.findById(diagnosticTest.getIdExam());
        if (existing == null) {
            throw new IllegalArgumentException("El examen diagnóstico no existe");
        }
        diagnosticTestInventoryPort.save(diagnosticTest);
    }

    // Elimina un examen diagnóstico del inventario del sistema validando su existencia
    public void deleteDiagnosticTest(String examId) throws Exception {
        DiagnosticTestInventory diagnosticTestInventory = diagnosticTestInventoryPort.findById(examId);
        if (diagnosticTestInventory == null) {
            throw new IllegalArgumentException("El examen diagnóstico no existe");
        }
        diagnosticTestInventoryPort.saveDelete(diagnosticTestInventory);
    }

    // Busca un examen diagnóstico específico por su identificador en el inventario
    public DiagnosticTestInventory findDiagnosticTestById(String examId) throws Exception {
        DiagnosticTestInventory diagnosticTest = diagnosticTestInventoryPort.findById(examId);
        if (diagnosticTest == null) {
            throw new IllegalArgumentException("Examen diagnóstico no encontrado");
        }
        return diagnosticTest;
    }

    // Obtiene la lista completa de todos los exámenes diagnósticos disponibles en el inventario
    public List<DiagnosticTestInventory> getAllDiagnosticTests() {
        return diagnosticTestInventoryPort.findAll();
    }

    // Busca exámenes diagnósticos por nombre, retornando todos los que contengan el término de búsqueda
    public List<DiagnosticTestInventory> searchDiagnosticTestsByName(String name) {
        return diagnosticTestInventoryPort.findByNameContaining(name);
    }

    // Crea una nueva especialidad médica en el sistema
    public void createSpecialty(Specialty specialty) throws Exception {
        specialtyService.create(specialty);
    }

    // Actualiza la información de una especialidad médica existente
    public void updateSpecialty(Specialty specialty) throws Exception {
        specialtyService.update(specialty);
    }

    // Elimina una especialidad médica del sistema
    public void deleteSpecialty(Long id) throws Exception {
        specialtyService.delete(id);
    }

    // Busca una especialidad médica específica por su identificador
    public Specialty findSpecialtyById(Long id) throws Exception {
        Specialty specialty = specialtyPort.findById(id);
        if (specialty == null) {
            throw new IllegalArgumentException("Especialidad no encontrada");
        }
        return specialty;
    }

    // Obtiene la lista completa de todas las especialidades médicas registradas en el sistema
    public List<Specialty> getAllSpecialties() {
        return specialtyPort.findAll();
    }

    // Busca especialidades por nombre, retornando todas las que contengan el término de búsqueda
    public List<Specialty> searchSpecialtiesByName(String name) {
        return specialtyPort.findByNameContaining(name);
    }
}

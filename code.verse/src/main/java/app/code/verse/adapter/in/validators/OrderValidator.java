package app.code.verse.adapter.in.validators;

import app.code.verse.domain.model.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class OrderValidator extends SimpleValidator {

    // Valida que el número de orden tenga exactamente 6 caracteres
    public String orderNumberValidator(String value) throws Exception {
        stringValidator(value, "número de orden");
        maximumSizeValidator(value, "número de orden", 6);
        minimumSizeValidator(value, "número de orden", 6);
        return value;
    }

    public String patientIdValidator(String value) throws Exception {
        return stringValidator(value, "ID de paciente");
    }

    public String doctorIdValidator(String value) throws Exception {
        return stringValidator(value, "ID de médico");
    }

    // Valida que la orden contenga al menos un ítem y valida cada ítem individualmente
    public List<OrderItem> itemsValidator(List<OrderItem> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("La orden debe contener al menos un ítem");
        }
        for (OrderItem item : items) {
            validateOrderItem(item);
        }

        return items;
    }

    // Valida que la fecha de creación no sea nula ni futura
    public LocalDate creationDateValidator(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("La fecha de creación no puede ser nula");
        }

        if (date.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de creación no puede ser futura");
        }

        return date;
    }

    // Valida que el costo sea mayor que 0
    public double costValidator(double cost, String fieldName) {
        if (cost <= 0) {
            throw new IllegalArgumentException("El " + fieldName + " debe ser mayor que 0");
        }
        return cost;
    }

    // Valida que la cantidad sea mayor que 0
    public int quantityValidator(int quantity, String fieldName) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("La " + fieldName + " debe ser mayor que 0");
        }
        return quantity;
    }

    // Valida un ítem de orden según su tipo específico (Medicamento, Examen o Procedimiento)
    private void validateOrderItem(OrderItem item) {
        costValidator(item.getCost(), "costo");

        if (item.getItemNumber() <= 0) {
            throw new IllegalArgumentException("El número de ítem debe ser mayor que 0");
        }
        if (item instanceof MedicineOrderItem) {
            validateMedicineOrderItem((MedicineOrderItem) item);
        } else if (item instanceof DiagnosticTestOrderItem) {
            validateDiagnosticTestOrderItem((DiagnosticTestOrderItem) item);
        } else if (item instanceof ProcedureOrderItem) {
            validateProcedureOrderItem((ProcedureOrderItem) item);
        }
    }

    // Valida que un ítem de medicamento tenga nombre, dosis y duración del tratamiento
    private void validateMedicineOrderItem(MedicineOrderItem item) {
        if (item.getMedicineName() == null || item.getMedicineName().isBlank()) {
            throw new IllegalArgumentException("El nombre del medicamento no puede ser nulo o vacío");
        }

        if (item.getDose() == null || item.getDose().isBlank()) {
            throw new IllegalArgumentException("La dosis no puede ser nula o vacía");
        }

        if (item.getTreatmentDuration() == null || item.getTreatmentDuration().isBlank()) {
            throw new IllegalArgumentException("La duración del tratamiento no puede ser nula o vacía");
        }
    }

    // Valida que un ítem de examen diagnóstico tenga nombre y cantidad, y si requiere especialista que especifique el tipo
    private void validateDiagnosticTestOrderItem(DiagnosticTestOrderItem item) {
        if (item.getTestName() == null || item.getTestName().isBlank()) {
            throw new IllegalArgumentException("El nombre del examen no puede ser nulo o vacío");
        }

        quantityValidator(item.getQuantity(), "cantidad");

        if (item.isRequiresSpecialist() && (item.getSpecialistType() == null || item.getSpecialistType().isBlank())) {
            throw new IllegalArgumentException("Si requiere especialista, debe especificar el tipo de especialista");
        }
    }

    // Valida que un ítem de procedimiento tenga nombre, repeticiones y frecuencia, y si requiere especialista que especifique el tipo
    private void validateProcedureOrderItem(ProcedureOrderItem item) {
        if (item.getProcedureName() == null || item.getProcedureName().isBlank()) {
            throw new IllegalArgumentException("El nombre del procedimiento no puede ser nulo o vacío");
        }

        quantityValidator(item.getRepetitions(), "cantidad de repeticiones");

        if (item.getFrequency() == null || item.getFrequency().isBlank()) {
            throw new IllegalArgumentException("La frecuencia no puede ser nula o vacía");
        }

        if (item.isRequiresSpecialist() && (item.getSpecialistType() == null || item.getSpecialistType().isBlank())) {
            throw new IllegalArgumentException("Si requiere especialista, debe especificar el tipo de especialista");
        }
    }
}

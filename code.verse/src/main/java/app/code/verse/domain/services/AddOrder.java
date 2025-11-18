package app.code.verse.domain.services;

import app.code.verse.domain.model.DiagnosticTestOrderItem;
import app.code.verse.domain.model.MedicineOrderItem;
import app.code.verse.domain.model.Order;
import app.code.verse.domain.model.OrderItem;
import app.code.verse.domain.model.ProcedureOrderItem;
import app.code.verse.domain.ports.OrderPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
public class AddOrder {

    @Autowired
    private OrderPort orderPort;

    // Crea una nueva orden médica validando todos los datos requeridos y ítems
    public void add(Order order) throws Exception {
        if (order == null) {
            throw new IllegalArgumentException("La orden no puede ser nula");
        }

        if (order.getOrderNumber() == null || order.getOrderNumber().isEmpty()) {
            order.setOrderNumber(generateOrderNumber());
        }

        if (order.getOrderNumber().length() != 6) {
            throw new IllegalArgumentException("El número de orden debe tener exactamente 6 dígitos");
        }

        if (orderPort.existsByOrderNumber(order.getOrderNumber())) {
            throw new IllegalArgumentException("El número de orden " + order.getOrderNumber() + " ya existe");
        }

        if (order.getPatientId() == null || order.getPatientId().isEmpty()) {
            throw new IllegalArgumentException("El ID del paciente es requerido");
        }
        if (order.getDoctorId() == null || order.getDoctorId().isEmpty()) {
            throw new IllegalArgumentException("El ID del médico es requerido");
        }

        if (order.getCreationDate() == null) {
            order.setCreationDate(LocalDate.now());
        }

        if (order.getItems() == null) {
            order.setItems(new ArrayList<>());
        }

        assignOrderNumberToItems(order);

        validateOrderItems(order.getItems());

        orderPort.save(order);
    }

    // Genera un número de orden único de 6 dígitos de forma aleatoria
    private String generateOrderNumber() {
        Random random = new Random();
        String orderNumber;
        int attempts = 0;
        int maxAttempts = 100;

        do {
            int number = 100000 + random.nextInt(900000);
            orderNumber = String.valueOf(number);
            attempts++;

            if (attempts > maxAttempts) {
                throw new RuntimeException("No se pudo generar un número de orden único después de " + maxAttempts + " intentos");
            }
        } while (orderPort.existsByOrderNumber(orderNumber));

        return orderNumber;
    }

    // Asigna el número de orden a todos los ítems de la orden
    private void assignOrderNumberToItems(Order order) {
        if (order.getItems() != null) {
            for (OrderItem item : order.getItems()) {
                item.setOrderNumber(order.getOrderNumber());
            }
        }
    }

    // Valida que los ítems de la orden sean válidos y cumplan con las restricciones
    private void validateOrderItems(List<OrderItem> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("La orden debe contener al menos un ítem");
        }

        validateUniqueItemNumbers(items);

        validateDiagnosticTestRestriction(items);

        validateItemTypeMixing(items);
    }

    // Verifica que no existan ítems duplicados con el mismo número en la orden
    private void validateUniqueItemNumbers(List<OrderItem> items) {
        Set<Integer> itemNumbers = new HashSet<>();
        for (OrderItem item : items) {
            if (!itemNumbers.add(item.getItemNumber())) {
                throw new IllegalArgumentException("No puede existir dos elementos con el mismo número de ítem (" + item.getItemNumber() + ") en una orden, incluso si son de diferentes tipos");
            }
        }
    }

    // Valida que no se mezclen pruebas diagnósticas con medicamentos o procedimientos en la misma orden
    private void validateDiagnosticTestRestriction(List<OrderItem> items) {
        boolean hasDiagnosticTest = false;
        boolean hasMedicineOrProcedure = false;

        for (OrderItem item : items) {
            if (item instanceof DiagnosticTestOrderItem) {
                hasDiagnosticTest = true;
            } else if (item instanceof MedicineOrderItem || item instanceof ProcedureOrderItem) {
                hasMedicineOrProcedure = true;
            }
        }

        if (hasDiagnosticTest && hasMedicineOrProcedure) {
            throw new IllegalArgumentException("Cuando se receta una ayuda diagnóstica no se puede recetar medicamentos ni procedimientos " + "en la misma orden, ya que no se tiene certeza del diagnóstico");
        }
    }

    // Valida las restricciones de combinación de tipos de ítems en la orden
    private void validateItemTypeMixing(List<OrderItem> items) {
        // Esta regla se cumple automáticamente al permitir MedicineOrderItem y ProcedureOrderItem junto pero no DiagnosticTestOrderItem con otros (validado en validateDiagnosticTestRestriction)
    }

    // Método execute vacío (puede ser deprecado)
    void execute(Order order) {
    }
}

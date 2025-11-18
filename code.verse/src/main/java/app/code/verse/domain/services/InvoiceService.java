package app.code.verse.domain.services;

import app.code.verse.domain.model.Invoice;

import java.util.List;
import java.util.UUID;

public class InvoiceService {
    // Verifica si la factura incluye al menos un servicio médico
    private boolean hasServices(List<String> servicesIncluded) {
        return servicesIncluded != null && !servicesIncluded.isEmpty();
    }

    // Calcula el copago según la póliza: sin póliza se paga todo, con póliza máximo 50,000
    private double calculateCopay(double totalAmount, boolean policyActive) {
        if (!policyActive) {
            return totalAmount;
        }
        return Math.min(50000, totalAmount);
    }

    // Verifica si el paciente ha excedido el límite anual de copago (1,000,000)
    private boolean hasExceededAnnualCopayLimit(double accumulatedCopays, int year) {
        return accumulatedCopays >= 1_000_000;
    }

    // Determina si el año actual es posterior al año de la última factura
    private boolean isNewYear(int lastInvoiceYear, int currentYear) {
        return lastInvoiceYear < currentYear;
    }

    // Valida que la factura contenga todos los datos requeridos: ID, paciente, doctor, fecha y servicios
    private boolean validateInvoiceData(Invoice invoice) {
        return invoice.getIdInvoice() != null && invoice.getPatientName() != null && invoice.getDoctorName() != null
                && invoice.getIssueDate() != null && hasServices(invoice.getServicesIncluded());
    }

    // Agrega la lista de servicios médicos a la factura
    private void addServiceDetails(Invoice invoice, List<String> services) {
        invoice.setServicesIncluded(services);
    }

    // Genera un identificador único para la factura usando UUID
    private String generateInvoiceId() {
        return UUID.randomUUID().toString();
    }
}

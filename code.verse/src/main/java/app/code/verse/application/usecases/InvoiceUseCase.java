package app.code.verse.application.usecases;

import app.code.verse.domain.model.Invoice;
import app.code.verse.domain.model.Order;
import app.code.verse.domain.model.Patient;
import app.code.verse.domain.model.Policy;
import app.code.verse.domain.ports.InvoiceRepositoryPort;
import app.code.verse.domain.ports.OrderPort;
import app.code.verse.domain.ports.PatientPort;
import app.code.verse.domain.ports.PolicyPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class InvoiceUseCase {

    @Autowired
    private InvoiceRepositoryPort invoiceRepositoryPort;

    @Autowired
    private OrderPort orderPort;

    @Autowired
    private PatientPort patientPort;

    @Autowired
    private PolicyPort policyPort;

    private static final double COPAY_AMOUNT = 50000.0;
    private static final double ANNUAL_COPAY_LIMIT = 1000000.0;

    // Genera una factura basada en las órdenes de un paciente, calculando montos con cobertura de seguros
    public Invoice generateInvoice(String patientId, String doctorName, List<String> orderNumbers) throws Exception {
        if (patientId == null || patientId.isEmpty()) {
            throw new IllegalArgumentException("ID de paciente requerido");
        }
        if (orderNumbers == null || orderNumbers.isEmpty()) {
            throw new IllegalArgumentException("Debe proporcionar al menos una orden");
        }

        Patient patient = patientPort.findByIdNumber(patientId);
        if (patient == null) {
            throw new IllegalArgumentException("Paciente no encontrado");
        }

        Policy policy = policyPort.findByPatient(patientId);

        int patientAge = calculateAge(patient.getBirthDate());

        double totalAmount = 0.0;
        java.util.List<String> servicesList = new java.util.ArrayList<>();

        for (String orderNumber : orderNumbers) {
            Order order = orderPort.findByOrderNumber(orderNumber);
            if (order == null) {
                throw new IllegalArgumentException("Orden no encontrada: " + orderNumber);
            }

            if (order.getItems() != null) {
                for (app.code.verse.domain.model.OrderItem item : order.getItems()) {
                    totalAmount += item.getCost();

                    String itemDetail;
                    if (item instanceof app.code.verse.domain.model.MedicineOrderItem) {
                        app.code.verse.domain.model.MedicineOrderItem medItem = (app.code.verse.domain.model.MedicineOrderItem) item;
                        itemDetail = String.format("Medicamento: %s - Dosis: %s - $%.2f", medItem.getMedicineName(), medItem.getDose(), item.getCost());
                    } else if (item instanceof app.code.verse.domain.model.ProcedureOrderItem) {
                        app.code.verse.domain.model.ProcedureOrderItem procItem = (app.code.verse.domain.model.ProcedureOrderItem) item;
                        itemDetail = String.format("Procedimiento: %s - $%.2f", procItem.getProcedureName(), item.getCost());
                    } else if (item instanceof app.code.verse.domain.model.DiagnosticTestOrderItem) {
                        app.code.verse.domain.model.DiagnosticTestOrderItem diagItem = (app.code.verse.domain.model.DiagnosticTestOrderItem) item;
                        itemDetail = String.format("Examen: %s - $%.2f", diagItem.getTestName(), item.getCost());
                    } else {
                        itemDetail = String.format("Servicio - $%.2f", item.getCost());
                    }
                    servicesList.add(itemDetail);
                }
            }
        }

        Invoice invoice = new Invoice();
        invoice.setIdPatient(patientId);
        invoice.setPatientName(patient.getName());
        invoice.setDoctorName(doctorName);
        invoice.setIssueDate(LocalDate.now());
        invoice.setTotalAmount(totalAmount);
        invoice.setServicesIncluded(servicesList);

        calculatePaymentAmounts(invoice, policy, patientId);

        invoiceRepositoryPort.save(invoice);

        return invoice;
    }

    // Calcula los montos de pago incluyendo el copago y cobertura del seguro basado en la póliza del paciente
    private void calculatePaymentAmounts(Invoice invoice, Policy policy, String patientId) throws Exception {
        double totalAmount = invoice.getTotalAmount();
        int currentYear = LocalDate.now().getYear();

        boolean hasPolicyActive = policy != null && policy.isActive() && policy.getExpirationDate().isAfter(LocalDate.now());

        if (!hasPolicyActive) {
            invoice.setCopayAmount(totalAmount);
            invoice.setInsuranceCoverage(0.0);
            invoice.setInsuranceCompany(null);
            invoice.setPolicyNumber(null);
            return;
        }

        invoice.setInsuranceCompany(policy.getCompanyName());
        invoice.setPolicyNumber(policy.getPolicyNumber());

        LocalDate today = LocalDate.now();
        long daysUntilExpiration = Period.between(today, policy.getExpirationDate()).getDays();
        invoice.setPolicyNumber(policy.getPolicyNumber());

        Double yearCopayTotal = invoiceRepositoryPort.sumCopayByPatientAndYear(patientId, currentYear);
        if (yearCopayTotal == null) {
            yearCopayTotal = 0.0;
        }

        if (yearCopayTotal >= ANNUAL_COPAY_LIMIT) {
            invoice.setCopayAmount(0.0);
            invoice.setInsuranceCoverage(totalAmount);
        } else {
            double copay = Math.min(COPAY_AMOUNT, totalAmount);

            if (yearCopayTotal + copay > ANNUAL_COPAY_LIMIT) {
                copay = ANNUAL_COPAY_LIMIT - yearCopayTotal;
            }

            invoice.setCopayAmount(copay);
            invoice.setInsuranceCoverage(totalAmount - copay);
        }
    }

    // Calcula la edad de una persona basada en su fecha de nacimiento
    private int calculateAge(LocalDate birthDate) {
        if (birthDate == null) {
            return 0;
        }
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    // Obtiene todas las facturas generadas para un paciente específico
    public List<Invoice> getInvoicesByPatient(String patientId) throws Exception {
        if (patientId == null || patientId.isEmpty()) {
            throw new IllegalArgumentException("ID de paciente inválido");
        }
        return invoiceRepositoryPort.findByPatientId(patientId);
    }

    // Obtiene las facturas de un paciente dentro de un rango de fechas especificado
    public List<Invoice> getInvoicesByPatientAndDateRange(String patientId, LocalDate startDate, LocalDate endDate) throws Exception {
        if (patientId == null || patientId.isEmpty()) {
            throw new IllegalArgumentException("ID de paciente inválido");
        }
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Las fechas son requeridas");
        }
        return invoiceRepositoryPort.findByPatientAndDateRange(patientId, startDate, endDate);
    }

    // Calcula el total de copagos pagados por un paciente durante un año específico
    public Double getYearCopayTotal(String patientId, int year) throws Exception {
        if (patientId == null || patientId.isEmpty()) {
            throw new IllegalArgumentException("ID de paciente inválido");
        }
        Double total = invoiceRepositoryPort.sumCopayByPatientAndYear(patientId, year);
        return total != null ? total : 0.0;
    }

    // Obtiene una factura específica por su identificador único
    public Invoice getInvoiceById(Long invoiceId) throws Exception {
        if (invoiceId == null) {
            throw new IllegalArgumentException("ID de factura inválido");
        }
        Invoice invoice = invoiceRepositoryPort.findById(String.valueOf(invoiceId));
        if (invoice == null) {
            throw new IllegalArgumentException("Factura no encontrada");
        }
        return invoice;
    }
}

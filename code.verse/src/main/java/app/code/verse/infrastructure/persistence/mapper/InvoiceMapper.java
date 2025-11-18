package app.code.verse.infrastructure.persistence.mapper;

import app.code.verse.domain.model.Invoice;
import app.code.verse.infrastructure.persistence.entities.InvoiceEntity;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InvoiceMapper {

    public static InvoiceEntity toEntity(Invoice invoice) {
        if (invoice == null) return null;
        InvoiceEntity entity = new InvoiceEntity();
        entity.setId(null);
        entity.setPatientId(invoice.getIdPatient());
        entity.setPatientName(invoice.getPatientName());
        entity.setDoctorName(invoice.getDoctorName());
        entity.setInsuranceCompany(invoice.getInsuranceCompany());
        entity.setPolicyNumber(invoice.getPolicyNumber());
        entity.setIssueDate(invoice.getIssueDate());
        entity.setTotalAmount(invoice.getTotalAmount());
        entity.setCopayAmount(invoice.getCopayAmount());
        entity.setInsuranceCoverage(invoice.getInsuranceCoverage());

        // Inicializar campos requeridos por la entidad
        entity.setCopayExempt(false); // Por defecto el copago no está exento
        entity.setPatientPays(invoice.getCopayAmount()); // El paciente paga el copago
        entity.setPatientAge(0); // Se debe calcular en el caso de uso
        entity.setYearCopayTotal(invoice.getCopayAmount());

        // Convertir List<String> a String separado por ;
        if (invoice.getServicesIncluded() != null) {
            entity.setServicesIncluded(String.join("; ", invoice.getServicesIncluded()));
        }

        return entity;
    }

    public static Invoice toDomain(InvoiceEntity entity) {
        if (entity == null) return null;
        Invoice invoice = new Invoice();
        invoice.setIdInvoice(entity.getId() != null ? entity.getId().toString() : null);
        invoice.setIdPatient(entity.getPatientId());
        invoice.setPatientName(entity.getPatientName());
        invoice.setDoctorName(entity.getDoctorName());
        invoice.setInsuranceCompany(entity.getInsuranceCompany());
        invoice.setPolicyNumber(entity.getPolicyNumber());
        invoice.setIssueDate(entity.getIssueDate());
        invoice.setTotalAmount(entity.getTotalAmount());
        invoice.setCopayAmount(entity.getCopayAmount());
        invoice.setInsuranceCoverage(entity.getInsuranceCoverage());

        // Convertir String separado por ; a List<String>
        if (entity.getServicesIncluded() != null) {
            List<String> services = Arrays.stream(entity.getServicesIncluded().split(";")).map(String::trim).collect(Collectors.toList());
            invoice.setServicesIncluded(services);
        }

        return invoice;
    }
}

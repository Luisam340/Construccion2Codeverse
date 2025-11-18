package app.code.verse.domain.ports;

import app.code.verse.domain.model.Invoice;

import java.time.LocalDate;
import java.util.List;

public interface InvoiceRepositoryPort {
    void save(Invoice invoice);
    Invoice findById(String idInvoice);
    List<Invoice> findByPatientId(String idPatient);
    List<Invoice> findByPatientAndDateRange(String patientId, LocalDate startDate, LocalDate endDate);
    Double sumCopayByPatientAndYear(String patientId, int year);
}

package app.code.verse.adapter.out;

import app.code.verse.domain.model.Invoice;
import app.code.verse.domain.ports.InvoiceRepositoryPort;
import app.code.verse.infrastructure.persistence.entities.InvoiceEntity;
import app.code.verse.infrastructure.persistence.mapper.InvoiceMapper;
import app.code.verse.infrastructure.persistence.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceAdapter implements InvoiceRepositoryPort {

    @Autowired
    private InvoiceRepository repository;

    @Override
    public void save(Invoice invoice) {
        InvoiceEntity entity = InvoiceMapper.toEntity(invoice);
        repository.save(entity);
    }

    @Override
    public Invoice findById(String idInvoice) {
        try {
            Long id = Long.parseLong(idInvoice);
            InvoiceEntity entity = repository.findById(id).orElse(null);
            return InvoiceMapper.toDomain(entity);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public List<Invoice> findByPatientId(String idPatient) {
        return repository.findByPatientId(idPatient).stream().map(InvoiceMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Invoice> findByPatientAndDateRange(String patientId, LocalDate startDate, LocalDate endDate) {
        return repository.findByPatientIdAndIssueDateBetween(patientId, startDate, endDate).stream().map(InvoiceMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public Double sumCopayByPatientAndYear(String patientId, int year) {
        return repository.sumCopayByPatientIdAndYear(patientId, year);
    }
}

package app.code.verse.adapter.out;

import app.code.verse.domain.model.VisitRecord;
import app.code.verse.infrastructure.persistence.entities.VisitRecordEntity;
import app.code.verse.infrastructure.persistence.mapper.VisitRecordMapper;
import app.code.verse.infrastructure.persistence.repository.VisitRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VisitRecordAdapter {

    @Autowired
    private VisitRecordRepository repository;

    public VisitRecord save(VisitRecord visitRecord) throws Exception {
        VisitRecordEntity entity = VisitRecordMapper.toEntity(visitRecord);
        VisitRecordEntity saved = repository.save(entity);
        return VisitRecordMapper.toDomain(saved);
    }

    public VisitRecord findById(Long id) {
        VisitRecordEntity entity = repository.findById(id).orElse(null);
        return VisitRecordMapper.toDomain(entity);
    }

    public List<VisitRecord> findByPatient(String patientId) {
        return repository.findByPatientId(patientId).stream().map(VisitRecordMapper::toDomain).collect(Collectors.toList());
    }

    public List<VisitRecord> findByDoctor(String doctorId) {
        return repository.findByDoctorId(doctorId).stream().map(VisitRecordMapper::toDomain).collect(Collectors.toList());
    }
}

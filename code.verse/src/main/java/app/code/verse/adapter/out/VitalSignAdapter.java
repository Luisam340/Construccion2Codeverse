package app.code.verse.adapter.out;

import app.code.verse.domain.model.VitalSign;
import app.code.verse.domain.ports.VitalSignPort;
import app.code.verse.infrastructure.persistence.entities.VitalSignEntity;
import app.code.verse.infrastructure.persistence.mapper.VitalSignMapper;
import app.code.verse.infrastructure.persistence.repository.VitalSignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VitalSignAdapter implements VitalSignPort {

    @Autowired
    private VitalSignRepository repository;

    @Override
    public VitalSign save(VitalSign vitalSign) throws Exception {
        VitalSignEntity entity = VitalSignMapper.toEntity(vitalSign);
        VitalSignEntity saved = repository.save(entity);
        return VitalSignMapper.toDomain(saved);
    }

    @Override
    public List<VitalSign> findByVisitRecordId(Long visitRecordId) {
        return repository.findByVisitRecordId(visitRecordId).stream().map(VitalSignMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<VitalSign> findByPatientId(String patientId) {
        return repository.findByPatientId(patientId).stream().map(VitalSignMapper::toDomain).collect(Collectors.toList());
    }
}

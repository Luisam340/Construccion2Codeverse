package app.code.verse.adapter.out;

import app.code.verse.domain.model.VitalSign;
import app.code.verse.domain.model.MedicineAdministration;
import app.code.verse.domain.ports.NursePort;
import app.code.verse.infrastructure.persistence.entities.VitalSignEntity;
import app.code.verse.infrastructure.persistence.entities.MedicineAdministrationEntity;
import app.code.verse.infrastructure.persistence.mapper.VitalSignMapper;
import app.code.verse.infrastructure.persistence.mapper.MedicineAdministrationMapper;
import app.code.verse.infrastructure.persistence.repository.VitalSignRepository;
import app.code.verse.infrastructure.persistence.repository.MedicineAdministrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NurseAdapter implements NursePort {

    @Autowired
    private VitalSignRepository vitalSignRepository;

    @Autowired
    private MedicineAdministrationRepository medicineAdministrationRepository;

    @Override
    public VitalSign registerVitalSign(VitalSign vitalSign) throws Exception {
        VitalSignEntity entity = VitalSignMapper.toEntity(vitalSign);
        VitalSignEntity saved = vitalSignRepository.save(entity);
        return VitalSignMapper.toDomain(saved);
    }

    @Override
    public List<VitalSign> findVitalSignsByPatient(String patientId) {
        return vitalSignRepository.findByPatientId(patientId).stream().map(VitalSignMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<VitalSign> findVitalSignsByVisitRecord(Long visitRecordId) {
        return vitalSignRepository.findByVisitRecordId(visitRecordId).stream().map(VitalSignMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public MedicineAdministration registerMedicationAdministration(MedicineAdministration medicineAdministration) throws Exception {
        MedicineAdministrationEntity entity = MedicineAdministrationMapper.toEntity(medicineAdministration);
        MedicineAdministrationEntity saved = medicineAdministrationRepository.save(entity);
        return MedicineAdministrationMapper.toDomain(saved);
    }

    @Override
    public List<MedicineAdministration> findMedicineAdministrationsByPatient(String patientId) {
        return medicineAdministrationRepository.findByPatientId(patientId).stream().map(MedicineAdministrationMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<MedicineAdministration> findMedicineAdministrationsByVisitRecord(Long visitRecordId) {
        return medicineAdministrationRepository.findByVisitRecordId(visitRecordId).stream().map(MedicineAdministrationMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<MedicineAdministration> findMedicineAdministrationsByOrder(String orderNumber) {
        return medicineAdministrationRepository.findByOrderNumber(orderNumber).stream().map(MedicineAdministrationMapper::toDomain).collect(Collectors.toList());
    }
}

package app.code.verse.domain.ports;

import app.code.verse.domain.model.VitalSign;

import java.util.List;

public interface VitalSignPort {
    VitalSign save(VitalSign vitalSign) throws Exception;
    List<VitalSign> findByVisitRecordId(Long visitRecordId);
    List<VitalSign> findByPatientId(String patientId);
}

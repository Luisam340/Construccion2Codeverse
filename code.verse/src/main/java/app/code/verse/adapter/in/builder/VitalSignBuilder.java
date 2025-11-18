package app.code.verse.adapter.in.builder;

import app.code.verse.adapter.in.validators.VitalSignValidator;
import app.code.verse.domain.model.VitalSign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class VitalSignBuilder {

    @Autowired
    private VitalSignValidator validator;

    public VitalSign build(Long visitRecordId, String patientId, String bloodPressure, Double temperature, Integer pulse, Double oxygenLevel, String recordedBy) throws Exception {
        VitalSign vitalSign = new VitalSign();
        vitalSign.setVisitRecordId(visitRecordId);
        vitalSign.setPatientId(validator.stringValidator(patientId, "ID de paciente"));
        vitalSign.setBloodPressure(bloodPressure);
        vitalSign.setTemperature(temperature);
        vitalSign.setPulse(pulse);
        vitalSign.setOxygenLevel(oxygenLevel);
        vitalSign.setRecordedAt(LocalDateTime.now());
        vitalSign.setRecordedBy(validator.stringValidator(recordedBy, "ID de enfermera"));
        return vitalSign;
    }

    public VitalSign update(VitalSign vitalSign, String bloodPressure, Double temperature, Integer pulse, Double oxygenLevel) {
        vitalSign.setBloodPressure(bloodPressure);
        vitalSign.setTemperature(temperature);
        vitalSign.setPulse(pulse);
        vitalSign.setOxygenLevel(oxygenLevel);
        return vitalSign;
    }
}

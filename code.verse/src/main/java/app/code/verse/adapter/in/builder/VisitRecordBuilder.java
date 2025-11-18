package app.code.verse.adapter.in.builder;

import app.code.verse.adapter.in.validators.SimpleValidator;
import app.code.verse.domain.model.VisitRecord;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class VisitRecordBuilder {

    private final SimpleValidator validator = new SimpleValidator();

    public VisitRecord build(String patientId, String doctorId, String reason, String symptoms, String diagnosis) throws Exception {
        VisitRecord visitRecord = new VisitRecord();
        visitRecord.setPatientId(validator.stringValidator(patientId, "ID de paciente"));
        visitRecord.setVisitDate(LocalDate.now());
        visitRecord.setDoctorId(validator.stringValidator(doctorId, "ID de médico"));
        visitRecord.setReason(reason);
        visitRecord.setSymptoms(symptoms);
        visitRecord.setDiagnosis(diagnosis);
        return visitRecord;
    }

    public VisitRecord update(VisitRecord visitRecord, String reason, String symptoms, String diagnosis) {
        visitRecord.setReason(reason);
        visitRecord.setSymptoms(symptoms);
        visitRecord.setDiagnosis(diagnosis);
        return visitRecord;
    }
}

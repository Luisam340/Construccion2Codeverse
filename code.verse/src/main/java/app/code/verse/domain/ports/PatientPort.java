package app.code.verse.domain.ports;

import app.code.verse.domain.model.Patient;

import java.util.List;

public interface PatientPort{
    Patient create(Patient patient) throws Exception;
    Patient deleteById(Patient patient) throws Exception;
    Patient update(Patient patient) throws Exception;
    Patient findByIdNumber(String idNumber) throws Exception;
    List<Patient> findByNameContainingIgnoreCase(String name) throws Exception;
    List<Patient> findAll() throws Exception;
}

package app.code.verse.adapter.in.client;

import app.code.verse.adapter.in.builder.PatientBuilder;
import app.code.verse.application.usecases.AdministrativeStaffUseCase;
import app.code.verse.domain.model.Patient;
import app.code.verse.domain.model.enums.Gender;
import app.code.verse.domain.model.enums.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

@Controller
public class AdministrativeStaffClient {

    @Autowired
    private AdministrativeStaffUseCase staffUseCase;
    @Autowired
    private PatientBuilder patientBuilder;
    Patient patient;
    Scanner reader = new Scanner(System.in);
    private static final String HEADER = "| %-20s | %-20s | %-20s | %-20s |%n";
    private static final String ROW_FORMAT = "| %-20s | %-20s | %-20s | %-20s |%n";
    private static final String MENU = "Ingrese una opción:\n1. Registrar paciente.\n2. Mostar todos los pacientes. \n3. Buscar paciente por número de documento.\n4. Buscar paciente por nombre.\n5. Actualizar paciente.\n6. Eliminar paciente.\n7. Salir.";

    public void session() {
        boolean session = true;
        while (session) {
            session = menu();
        }
    }

    private boolean menu() {
        try {
            System.out.println(MENU);
            String option = reader.nextLine();
            return options(option);
        } catch (Exception e) {
            System.out.println();
            return true;
        }
    }

    private boolean options(String op) throws Exception {

        String idNumber;
        switch (op) {
            case "1":
                patient = registerPatientInformation();
                staffUseCase.create(patient);
                return true;
            case "2":
                getAllPatient();
                return true;
            case "3":
                System.out.println("Ingrese el número de documento del paciente");
                idNumber = reader.nextLine();
                getPatientById(idNumber);
                return true;
            case "4":
                System.out.println("Ingrese el nombre del paciente");
                String name = reader.nextLine();
                getPatientByName(name);
                return true;
            case "5":
                System.out.println("Ingrese el número de documento del paciente que desea actualizar");
                idNumber = reader.nextLine();
                patient = updatePatientInformtion(idNumber);
                staffUseCase.updatePatient(patient);

                return true;
            case "6":
                System.out.println("Ingrese el número de documento del paciente que desea eliminar");
                idNumber = reader.nextLine();
                deletePatient(idNumber);

                return true;

            default:
                return true;
        }
    }

    private Patient registerPatientInformation() throws Exception {
        String name, idNumber, email, phoneNumber, address, gender;
        LocalDate birthDate;
        System.out.println("Ingrese el nombre del paciente.");
        name = reader.nextLine();
        System.out.println("Ingrese el número de documento del paciente.");
        idNumber = reader.nextLine();
        System.out.println("Ingrese el correo electrónico del paciente.");
        email = reader.nextLine();
        System.out.println("Ingrese el número de telefono del paciente.");
        phoneNumber = reader.nextLine();
        System.out.println("Seleccione el género del paciente");
        gender = selectGender();
        System.out.println("Ingrese la fecha de nacimiento del paciente. (Formato: yyyy-MM-dd))");
        birthDate = LocalDate.parse(reader.nextLine());
        System.out.println("Ingrese la dirección del paciente.");
        address = reader.nextLine();
        return patientBuilder.build(idNumber, name, birthDate, gender, address, phoneNumber, email);
    }

    private Patient updatePatientInformtion(String idNumber) throws Exception {
        Patient patient = getPatientById(idNumber);
        String name, email, phoneNumber, address, gender, date;
        LocalDate birthDate;

        System.out.println("Ingrese el nombre del paciente.");
        name = reader.nextLine();
        name = (name == null || name.isEmpty()) ? patient.getName() : name;

        System.out.println("Ingrese el correo electrónico del paciente.");
        email = reader.nextLine();
        email = (email == null || email.isEmpty()) ? patient.getEmail() : email;

        System.out.println("Ingrese el número de telefono del paciente.");
        phoneNumber = reader.nextLine();
        phoneNumber = (phoneNumber == null || phoneNumber.isEmpty() ? patient.getPhoneNumber() : phoneNumber);

        System.out.println("Seleccione el género del paciente");
        gender = selectGender();
        gender = (gender == null || gender.isEmpty() ? patient.getGender() : gender);

        System.out.println("Ingrese la fecha de nacimiento del paciente. (Formato: yyyy-MM-dd))");
        date = reader.nextLine();
        birthDate = (date == null || date.isEmpty() ? patient.getBirthDate() : LocalDate.parse(date));

        System.out.println("Ingrese la dirección del paciente.");
        address = reader.nextLine();
        address = (address == null || address.isEmpty() ? patient.getAddress() : address);

        return patientBuilder.update(patient, name, birthDate, gender, address, phoneNumber, email);
    }

    private String selectGender() {
        System.out.println("1. Femenino\n2. Masculino\n3. Otro");
        String op = reader.nextLine();
        String rol = "";
        switch (op) {
            case "1":
                rol = Gender.FEMALE.gender();
                break;
            case "2":
                rol = Gender.MALE.gender();
                break;
            case "3":
                System.out.println("Ingrese el género");
                rol = reader.nextLine();
                //rol = Gender.OTHER.gender();
                break;
            case "4":
                rol = Rol.ADMINISTRATIVE_STAFF.getRol();
                break;
            case "5":
                rol = Rol.INFORMATION_SUPPORT.getRol();
                break;
            default:
                System.out.println("Opción incorrecta");
        }
        return rol;
    }

    private void getAllPatient() throws Exception {
        System.out.println("Pacientes:");
        List<Patient> patients = staffUseCase.getAllPatient();
        if (patients.isEmpty()) {
            System.out.println("No hay pacientes registrados.");
        } else {
            System.out.printf(HEADER, "Documento", "Nombre", "Teléfono", "Dirección");
            patients.forEach(pat -> System.out.printf(ROW_FORMAT, pat.getIdNumber(), pat.getName(), pat.getPhoneNumber(), pat.getAddress()));
        }
    }

    private void getPatientByName(String name) throws Exception {
        System.out.println("Pacientes");
        List<Patient> patients = staffUseCase.findByName(name);
        if (patients.isEmpty()) {
            System.out.println("No hay pacientes registrados.");
        } else {
            System.out.printf(HEADER, "Documento", "Nombre", "Teléfono", "Dirección");
            patients.forEach(pat -> System.out.printf(ROW_FORMAT, pat.getIdNumber(), pat.getName(), pat.getPhoneNumber(), pat.getAddress()));
        }
    }

    private Patient getPatientById(String document) throws Exception {
        System.out.println("Pacientes");
        Patient patient = staffUseCase.findById(document);
        if (patient == null) {
            System.out.println("No hay pacientes registrados.");
        } else {
            System.out.printf(HEADER, "Documento", "Nombre", "Teléfono", "Dirección");
            System.out.printf(ROW_FORMAT, patient.getIdNumber(), patient.getName(), patient.getPhoneNumber(), patient.getAddress());
        }
        return patient;
    }

    private void deletePatient(String document) throws Exception {
        Patient patient = getPatientById(document);
        staffUseCase.deletePatient(patient);
    }


}

package app.code.verse.domain.services;

import app.code.verse.domain.model.Employee;
import app.code.verse.domain.ports.EmployeePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterEmployee {

    @Autowired
    private EmployeePort employeePort;

    // Registra un nuevo empleado en el sistema persistiendo sus datos
    public void create(Employee employee) throws Exception {
        checkIfEmployeeExists(employee);
        employeePort.create(employee);
    }

    // Verifica que el empleado no exista por su número de identificación o nombre de usuario
    private void checkIfEmployeeExists(Employee employee) throws Exception {
        if (employeePort.findByIdNumber(employee.getIdNumber()) != null) {
            throw new IllegalArgumentException("Ya existe un empleado con esa cédula");
        }
        if (employeePort.findByUserName(employee.getUserName()) != null) {
            throw new IllegalArgumentException("Ese nombre de usuario ya está en uso");
        }
    }
}
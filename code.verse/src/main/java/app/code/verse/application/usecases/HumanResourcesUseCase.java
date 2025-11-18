package app.code.verse.application.usecases;

import app.code.verse.domain.model.Employee;
import app.code.verse.domain.ports.EmployeePort;
import app.code.verse.domain.services.RegisterEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HumanResourcesUseCase {
    @Autowired
    private RegisterEmployee registerEmployee;
    @Autowired
    private EmployeePort employeePort;

    // Crea un nuevo empleado en el sistema
    public void create(Employee employee) throws Exception {
        registerEmployee.create(employee);
    }

    // Actualiza los datos de un empleado existente, validando que el username no esté en uso
    public Employee update(Employee employee) throws Exception {
        Employee existingEmployee = employeePort.findByUserName(employee.getUserName());
        if (existingEmployee != null && !existingEmployee.getIdNumber().equals(employee.getIdNumber())) {
            throw new IllegalArgumentException("Ese nombre de usuario ya está en uso por otro empleado");
        }
        employeePort.update(employee);
        return employee;
    }

    // Elimina un empleado del sistema
    public void delete(Employee employee) throws Exception {
        employeePort.deleteById(employee);
    }

    // Obtiene la lista completa de todos los empleados registrados
    public List<Employee> getAllEmployees() {
        return employeePort.findAll();
    }

    // Busca un empleado por su número de identificación
    public Employee findById(String idNumber) {
        Employee employee = employeePort.findByIdNumber(idNumber);
        if (employee == null) {
            throw new IllegalArgumentException("Empleado no encontrado");
        }
        return employee;
    }

    // Busca un empleado por su nombre de usuario
    public Employee findByUserName(String userName) {
        Employee employee = employeePort.findByUserName(userName);
        if (employee == null) {
            throw new IllegalArgumentException("Empleado no encontrado");
        }
        return employee;
    }
}
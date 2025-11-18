package app.code.verse.domain.services;

import app.code.verse.domain.model.Employee;
import app.code.verse.domain.ports.EmployeePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private EmployeePort employeePort;

    // Autentica un empleado validando su usuario y contraseña
    public Employee authenticate(String userName, String password) throws Exception {
        if (userName == null || userName.isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario es requerido");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("La contraseña es requerida");
        }

        Employee employee = employeePort.findByUserName(userName);

        if (employee == null) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }

        if (!employee.getPassword().equals(password)) {
            throw new IllegalArgumentException("Contraseña incorrecta");
        }

        if (employee.getStatus() == null || !employee.getStatus()) {
            throw new IllegalArgumentException("Usuario inactivo");
        }

        return employee;
    }

    // Valida que la sesión del empleado sea válida y esté activa
    public boolean validateSession(Employee employee) {
        return employee != null && employee.getStatus();
    }

    // Convierte el código del rol a su nombre descriptivo en español
    public String getRoleName(String rol) {
        if (rol == null) return "Desconocido";

        switch (rol.toUpperCase()) {
            case "RECURSOS HUMANOS":
            case "RRHH":
                return "Recursos Humanos";
            case "PERSONAL ADMINISTRATIVO":
            case "ADMINISTRATIVO":
                return "Personal Administrativo";
            case "SOPORTE DE INFORMACION":
            case "SOPORTE":
                return "Soporte de Información";
            case "ENFERMERA":
            case "ENFERMERO":
                return "Enfermera";
            case "MEDICO":
            case "DOCTOR":
                return "Médico";
            default:
                return rol;
        }
    }
}

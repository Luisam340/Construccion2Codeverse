package app.code.verse.application.usecases;

import app.code.verse.domain.model.Employee;
import app.code.verse.domain.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationUseCase {

    @Autowired
    private AuthenticationService authenticationService;

    // Autentica un usuario validando sus credenciales de acceso
    public Employee login(String userName, String password) throws Exception {
        return authenticationService.authenticate(userName, password);
    }

    // Verifica si un usuario actualmente tiene una sesión activa válida
    public boolean isAuthenticated(Employee employee) {
        return authenticationService.validateSession(employee);
    }

    // Obtiene el nombre del rol asociado al usuario autenticado
    public String getRoleName(Employee employee) {
        if (employee == null) {
            return "Sin autenticar";
        }
        return authenticationService.getRoleName(employee.getRol());
    }

    // Cierra la sesión de un usuario autenticado
    public void logout(Employee employee) {
        System.out.println("Sesión cerrada para: " + employee.getName());
    }
}

package app.code.verse.adapter.in.rest;

import app.code.verse.application.usecases.AuthenticationUseCase;
import app.code.verse.domain.model.Employee;
import app.code.verse.infrastructure.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/hospital/san-rafael/auth")
@CrossOrigin(origins = "*")
public class AuthRestController {

    @Autowired
    private AuthenticationUseCase authenticationUseCase;

    @Autowired
    private JwtUtil jwtUtil;

    // Autentica un usuario mediante username y password, retorna JWT token
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> credentials) {
        Map<String, Object> response = new HashMap<>();
        try {
            String username = credentials.get("username");
            String password = credentials.get("password");

            if (username == null || username.isEmpty()) {
                response.put("success", false);
                response.put("error", "El nombre de usuario es requerido");
                return ResponseEntity.badRequest().body(response);
            }

            if (password == null || password.isEmpty()) {
                response.put("success", false);
                response.put("error", "La contraseña es requerida");
                return ResponseEntity.badRequest().body(response);
            }

            Employee employee = authenticationUseCase.login(username, password);

            String token = jwtUtil.generateToken(
                    employee.getUserName(),
                    employee.getRol(),
                    employee.getIdNumber()
            );

            Map<String, Object> data = new HashMap<>();
            data.put("token", token);
            data.put("user", Map.of(
                    "idNumber", employee.getIdNumber(),
                    "name", employee.getName(),
                    "userName", employee.getUserName(),
                    "role", employee.getRol(),
                    "roleName", authenticationUseCase.getRoleName(employee)
            ));

            response.put("success", true);
            response.put("message", "Login exitoso");
            response.put("data", data);
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al autenticar: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Valida que un JWT token sea válido y retorna los datos del usuario
    @PostMapping("/validate")
    public ResponseEntity<Map<String, Object>> validateToken(@RequestHeader("Authorization") String authHeader) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.put("success", false);
                response.put("error", "Token no proporcionado o formato inválido");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            String token = authHeader.substring(7);

            if (jwtUtil.validateToken(token)) {
                String username = jwtUtil.extractUsername(token);
                String role = jwtUtil.extractRole(token);
                String idNumber = jwtUtil.extractIdNumber(token);

                Map<String, Object> userData = new HashMap<>();
                userData.put("username", username);
                userData.put("role", role);
                userData.put("idNumber", idNumber);

                response.put("success", true);
                response.put("valid", true);
                response.put("data", userData);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("valid", false);
                response.put("error", "Token inválido o expirado");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("valid", false);
            response.put("error", "Error al validar token: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

}

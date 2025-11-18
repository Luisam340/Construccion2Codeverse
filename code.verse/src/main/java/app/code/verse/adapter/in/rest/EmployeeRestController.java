package app.code.verse.adapter.in.rest;


import app.code.verse.adapter.in.builder.EmployeeBuilder;
import app.code.verse.application.usecases.HumanResourcesUseCase;
import app.code.verse.domain.model.Employee;
import app.code.verse.domain.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/hospital/san-rafael/employees")
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('RECURSO_HUMANOS')")
public class EmployeeRestController {

    @Autowired
    private HumanResourcesUseCase humanResourcesUseCase;
    @Autowired
    private EmployeeBuilder employeeBuilder;

    @PostMapping
    public ResponseEntity<Map<String, Object>> createEmployee(@RequestBody Employee employee) {
        Map<String, Object> response = new HashMap<>();
        try {
            employee = employeeBuilder.build(employee.getName(), employee.getIdNumber(), employee.getEmail(), employee.getPhoneNumber(), employee.getBirthDate(), employee.getAddress(), employee.getRol(), employee.getUserName(), employee.getPassword(), employee.getStatus());
            humanResourcesUseCase.create(employee);
            response.put("success", true);
            response.put("message", "Empleado creado exitosamente");
            response.put("data", employee);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al crear empleado: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllEmployees() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Employee> employees = humanResourcesUseCase.getAllEmployees();
            response.put("success", true);
            response.put("count", employees.size());
            response.put("data", employees);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al obtener empleados: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{idNumber}")
    public ResponseEntity<Map<String, Object>> getEmployeeById(@PathVariable String idNumber) {
        Map<String, Object> response = new HashMap<>();
        try {
            Employee employee = humanResourcesUseCase.findById(idNumber);
            response.put("success", true);
            response.put("data", employee);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al buscar empleado: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchEmployeeByUserName(@RequestParam String userName) {
        Map<String, Object> response = new HashMap<>();
        try {
            Employee employee = humanResourcesUseCase.findByUserName(userName);
            employee.setPassword("****");
            response.put("success", true);
            response.put("data", employee);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al buscar empleado: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{idNumber}")
    public ResponseEntity<Map<String, Object>> updateEmployee(@PathVariable String idNumber, @RequestBody Employee employee) {
        Map<String, Object> response = new HashMap<>();
        try {
            employee.setIdNumber(idNumber);
            employee = employeeBuilder.update(employee, employee.getName(), employee.getEmail(), employee.getPhoneNumber(), employee.getBirthDate(), employee.getAddress(), employee.getRol(), employee.getUserName(), employee.getPassword());
            humanResourcesUseCase.update(employee);
            response.put("success", true);
            response.put("message", "Empleado actualizado exitosamente");
            response.put("data", employee);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al actualizar Empleado: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{idNumber}")
    public ResponseEntity<Map<String, Object>> deleteEmployee(@PathVariable String idNumber) {
        Map<String, Object> response = new HashMap<>();
        try {
            Employee employee = humanResourcesUseCase.findById(idNumber);
            humanResourcesUseCase.delete(employee);
            response.put("success", true);
            response.put("message", "Empleado eliminado exitosamente");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al eliminar empleado: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


}

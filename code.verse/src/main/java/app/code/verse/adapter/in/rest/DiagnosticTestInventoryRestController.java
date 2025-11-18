package app.code.verse.adapter.in.rest;

import app.code.verse.adapter.in.builder.DiagnosticTestInventoryBuilder;
import app.code.verse.application.usecases.InformationSupportUseCase;
import app.code.verse.domain.model.DiagnosticTestInventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/hospital/san-rafael/diagnostic-tests")
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('SOPORTE')")
public class DiagnosticTestInventoryRestController {

    @Autowired
    private InformationSupportUseCase informationSupportUseCase;

    @Autowired
    private DiagnosticTestInventoryBuilder diagnosticTestBuilder;

    @PostMapping
    public ResponseEntity<Map<String, Object>> createDiagnosticTest(@RequestBody DiagnosticTestInventory diagnosticTest) {
        Map<String, Object> response = new HashMap<>();
        try {
            diagnosticTest = diagnosticTestBuilder.build(diagnosticTest.getIdExam(), diagnosticTest.getName(), diagnosticTest.getCost());
            informationSupportUseCase.createDiagnosticTest(diagnosticTest);
            response.put("success", true);
            response.put("message", "Examen diagnóstico creado exitosamente");
            response.put("data", diagnosticTest);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al crear examen diagnóstico: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllDiagnosticTests() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<DiagnosticTestInventory> diagnosticTests = informationSupportUseCase.getAllDiagnosticTests();
            response.put("success", true);
            response.put("count", diagnosticTests.size());
            response.put("data", diagnosticTests);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al obtener exámenes diagnósticos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{idExam}")
    public ResponseEntity<Map<String, Object>> getDiagnosticTestById(@PathVariable String idExam) {
        Map<String, Object> response = new HashMap<>();
        try {
            DiagnosticTestInventory diagnosticTest = informationSupportUseCase.findDiagnosticTestById(idExam);
            response.put("success", true);
            response.put("data", diagnosticTest);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al buscar examen diagnóstico: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchDiagnosticTestsByName(@RequestParam String name) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<DiagnosticTestInventory> diagnosticTests = informationSupportUseCase.searchDiagnosticTestsByName(name);
            response.put("success", true);
            response.put("count", diagnosticTests.size());
            response.put("data", diagnosticTests);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al buscar exámenes diagnósticos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{idExam}")
    public ResponseEntity<Map<String, Object>> updateDiagnosticTest(@PathVariable String idExam, @RequestBody DiagnosticTestInventory diagnosticTest) {
        Map<String, Object> response = new HashMap<>();
        try {
            diagnosticTest.setIdExam(idExam);
            diagnosticTest = diagnosticTestBuilder.update(diagnosticTest, diagnosticTest.getName(), diagnosticTest.getCost());
            informationSupportUseCase.updateDiagnosticTest(diagnosticTest);
            response.put("success", true);
            response.put("message", "Examen diagnóstico actualizado exitosamente");
            response.put("data", diagnosticTest);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al actualizar examen diagnóstico: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{idExam}")
    public ResponseEntity<Map<String, Object>> deleteDiagnosticTest(@PathVariable String idExam) {
        Map<String, Object> response = new HashMap<>();
        try {
            informationSupportUseCase.deleteDiagnosticTest(idExam);
            response.put("success", true);
            response.put("message", "Examen diagnóstico eliminado exitosamente");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al eliminar examen diagnóstico: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

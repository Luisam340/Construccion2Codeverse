package app.code.verse.adapter.in.rest;

import app.code.verse.adapter.in.builder.ProcedureInventoryBuilder;
import app.code.verse.application.usecases.InformationSupportUseCase;
import app.code.verse.domain.model.ProcedureInventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/hospital/san-rafael/procedures")
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('SOPORTE')")
public class ProcedureInventoryRestController {

    @Autowired
    private InformationSupportUseCase informationSupportUseCase;

    @Autowired
    private ProcedureInventoryBuilder procedureBuilder;

    @PostMapping
    public ResponseEntity<Map<String, Object>> createProcedure(@RequestBody ProcedureInventory procedure) {
        Map<String, Object> response = new HashMap<>();
        try {
            procedure = procedureBuilder.build(procedure.getIdProcedure(), procedure.getName(), procedure.getCost());
            informationSupportUseCase.createProcedure(procedure);
            response.put("success", true);
            response.put("message", "Procedimiento creado exitosamente");
            response.put("data", procedure);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al crear procedimiento: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllProcedures() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<ProcedureInventory> procedures = informationSupportUseCase.getAllProcedures();
            response.put("success", true);
            response.put("count", procedures.size());
            response.put("data", procedures);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al obtener procedimientos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{idProcedure}")
    public ResponseEntity<Map<String, Object>> getProcedureById(@PathVariable String idProcedure) {
        Map<String, Object> response = new HashMap<>();
        try {
            ProcedureInventory procedure = informationSupportUseCase.findProcedureById(idProcedure);
            response.put("success", true);
            response.put("data", procedure);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al buscar procedimiento: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchProceduresByName(@RequestParam String name) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<ProcedureInventory> procedures = informationSupportUseCase.searchProceduresByName(name);
            response.put("success", true);
            response.put("count", procedures.size());
            response.put("data", procedures);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al buscar procedimientos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{idProcedure}")
    public ResponseEntity<Map<String, Object>> updateProcedure(@PathVariable String idProcedure, @RequestBody ProcedureInventory procedure) {
        Map<String, Object> response = new HashMap<>();
        try {
            procedure.setIdProcedure(idProcedure);
            procedure = procedureBuilder.update(procedure, procedure.getName(), procedure.getCost());
            informationSupportUseCase.updateProcedure(procedure);
            response.put("success", true);
            response.put("message", "Procedimiento actualizado exitosamente");
            response.put("data", procedure);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al actualizar procedimiento: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{idProcedure}")
    public ResponseEntity<Map<String, Object>> deleteProcedure(@PathVariable String idProcedure) {
        Map<String, Object> response = new HashMap<>();
        try {
            informationSupportUseCase.deleteProcedure(idProcedure);
            response.put("success", true);
            response.put("message", "Procedimiento eliminado exitosamente");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al eliminar procedimiento: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

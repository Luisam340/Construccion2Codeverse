package app.code.verse.adapter.in.rest;

import app.code.verse.adapter.in.builder.SpecialtyBuilder;
import app.code.verse.application.usecases.InformationSupportUseCase;
import app.code.verse.domain.model.Specialty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/hospital/san-rafael/specialties")
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('SOPORTE')")
public class SpecialtyRestController {

    @Autowired
    private InformationSupportUseCase informationSupportUseCase;

    @Autowired
    private SpecialtyBuilder specialtyBuilder;

    @PostMapping
    public ResponseEntity<Map<String, Object>> createSpecialty(@RequestBody Specialty specialty) {
        Map<String, Object> response = new HashMap<>();
        try {
            specialty = specialtyBuilder.build(specialty.getName(), specialty.getDescription());
            informationSupportUseCase.createSpecialty(specialty);
            response.put("success", true);
            response.put("message", "Especialidad creada exitosamente");
            response.put("data", specialty);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al crear especialidad: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllSpecialties() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Specialty> specialties = informationSupportUseCase.getAllSpecialties();
            response.put("success", true);
            response.put("count", specialties.size());
            response.put("data", specialties);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al obtener especialidades: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getSpecialtyById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Specialty specialty = informationSupportUseCase.findSpecialtyById(id);
            response.put("success", true);
            response.put("data", specialty);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al buscar especialidad: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchSpecialtiesByName(@RequestParam String name) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Specialty> specialties = informationSupportUseCase.searchSpecialtiesByName(name);
            response.put("success", true);
            response.put("count", specialties.size());
            response.put("data", specialties);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al buscar especialidades: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateSpecialty(@PathVariable Long id, @RequestBody Specialty specialty) {
        Map<String, Object> response = new HashMap<>();
        try {
            specialty.setId(id);
            specialty = specialtyBuilder.update(specialty, specialty.getName(), specialty.getDescription());
            informationSupportUseCase.updateSpecialty(specialty);
            response.put("success", true);
            response.put("message", "Especialidad actualizada exitosamente");
            response.put("data", specialty);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al actualizar especialidad: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteSpecialty(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            informationSupportUseCase.deleteSpecialty(id);
            response.put("success", true);
            response.put("message", "Especialidad eliminada exitosamente");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al eliminar especialidad: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

package app.code.verse.adapter.in.rest;

import app.code.verse.adapter.in.builder.MedicineInventoryBuilder;
import app.code.verse.application.usecases.InformationSupportUseCase;
import app.code.verse.domain.model.MedicineInventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/hospital/san-rafael/medicines")
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('SOPORTE')")
public class MedicineInventoryRestController {

    @Autowired
    private InformationSupportUseCase informationSupportUseCase;

    @Autowired
    private MedicineInventoryBuilder medicineBuilder;

    @PostMapping
    public ResponseEntity<Map<String, Object>> createMedicine(@RequestBody MedicineInventory medicine) {
        Map<String, Object> response = new HashMap<>();
        try {
            medicine = medicineBuilder.build(medicine.getIdMedicine(), medicine.getName(), medicine.getStock(), medicine.getPrice());
            informationSupportUseCase.createMedicine(medicine);
            response.put("success", true);
            response.put("message", "Medicamento creado exitosamente");
            response.put("data", medicine);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al crear medicamento: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllMedicines() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<MedicineInventory> medicines = informationSupportUseCase.getAllMedicines();
            response.put("success", true);
            response.put("count", medicines.size());
            response.put("data", medicines);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al obtener medicamentos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{idMedicine}")
    public ResponseEntity<Map<String, Object>> getMedicineById(@PathVariable String idMedicine) {
        Map<String, Object> response = new HashMap<>();
        try {
            MedicineInventory medicine = informationSupportUseCase.findMedicineById(idMedicine);
            response.put("success", true);
            response.put("data", medicine);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al buscar medicamento: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchMedicinesByName(@RequestParam String name) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<MedicineInventory> medicines = informationSupportUseCase.searchMedicinesByName(name);
            response.put("success", true);
            response.put("count", medicines.size());
            response.put("data", medicines);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al buscar medicamentos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{idMedicine}")
    public ResponseEntity<Map<String, Object>> updateMedicine(@PathVariable String idMedicine, @RequestBody MedicineInventory medicine) {
        Map<String, Object> response = new HashMap<>();
        try {
            medicine.setIdMedicine(idMedicine);
            medicine = medicineBuilder.update(medicine, medicine.getName(), medicine.getStock(), medicine.getPrice());
            informationSupportUseCase.updateMedicine(medicine);
            response.put("success", true);
            response.put("message", "Medicamento actualizado exitosamente");
            response.put("data", medicine);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al actualizar medicamento: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{idMedicine}")
    public ResponseEntity<Map<String, Object>> deleteMedicine(@PathVariable String idMedicine) {
        Map<String, Object> response = new HashMap<>();
        try {
            informationSupportUseCase.deleteMedicine(idMedicine);
            response.put("success", true);
            response.put("message", "Medicamento eliminado exitosamente");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error al eliminar medicamento: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

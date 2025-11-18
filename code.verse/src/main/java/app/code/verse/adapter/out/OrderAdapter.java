package app.code.verse.adapter.out;

import app.code.verse.domain.model.*;
import app.code.verse.domain.ports.OrderPort;
import app.code.verse.infrastructure.persistence.entities.*;
import app.code.verse.infrastructure.persistence.mapper.OrderMapper;
import app.code.verse.infrastructure.persistence.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderAdapter implements OrderPort {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private MedicineOrderItemRepository medicineItemRepository;

    @Autowired
    private DiagnosticTestOrderItemRepository diagnosticTestItemRepository;

    @Autowired
    private ProcedureOrderItemRepository procedureItemRepository;

    @Override
    public Order save(Order order) throws Exception {
        // Guardar la orden
        OrderEntity entity = OrderMapper.toEntity(order);
        OrderEntity saved = repository.save(entity);

        // Guardar los items de la orden
        if (order.getItems() != null) {
            for (OrderItem item : order.getItems()) {
                if (item instanceof MedicineOrderItem) {
                    MedicineOrderItem medItem = (MedicineOrderItem) item;
                    MedicineOrderItemEntity itemEntity = new MedicineOrderItemEntity();
                    itemEntity.setOrderNumber(order.getOrderNumber());
                    itemEntity.setItemNumber(item.getItemNumber());
                    itemEntity.setMedicineName(medItem.getMedicineName());
                    itemEntity.setDose(medItem.getDose());
                    itemEntity.setTreatmentDuration(medItem.getTreatmentDuration());
                    itemEntity.setCost(item.getCost());
                    medicineItemRepository.save(itemEntity);
                } else if (item instanceof DiagnosticTestOrderItem) {
                    DiagnosticTestOrderItem diagItem = (DiagnosticTestOrderItem) item;
                    DiagnosticTestOrderItemEntity itemEntity = new DiagnosticTestOrderItemEntity();
                    itemEntity.setOrderNumber(order.getOrderNumber());
                    itemEntity.setItemNumber(item.getItemNumber());
                    itemEntity.setTestName(diagItem.getTestName());
                    itemEntity.setQuantity(diagItem.getQuantity());
                    itemEntity.setRequiresSpecialist(diagItem.isRequiresSpecialist());
                    itemEntity.setSpecialistType(diagItem.getSpecialistType());
                    itemEntity.setCost(item.getCost());
                    diagnosticTestItemRepository.save(itemEntity);
                } else if (item instanceof ProcedureOrderItem) {
                    ProcedureOrderItem procItem = (ProcedureOrderItem) item;
                    ProcedureOrderItemEntity itemEntity = new ProcedureOrderItemEntity();
                    itemEntity.setOrderNumber(order.getOrderNumber());
                    itemEntity.setItemNumber(item.getItemNumber());
                    itemEntity.setProcedureName(procItem.getProcedureName());
                    itemEntity.setRepetitions(procItem.getRepetitions());
                    itemEntity.setFrequency(procItem.getFrequency());
                    itemEntity.setRequiresSpecialist(procItem.isRequiresSpecialist());
                    itemEntity.setSpecialistType(procItem.getSpecialistType());
                    itemEntity.setCost(item.getCost());
                    procedureItemRepository.save(itemEntity);
                }
            }
        }

        return OrderMapper.toDomain(saved);
    }

    @Override
    public Order findByOrderNumber(String orderNumber) {
        OrderEntity entity = repository.findByOrderNumber(orderNumber);
        Order order = OrderMapper.toDomain(entity);

        if (order != null) {
            // Cargar los items de la orden
            List<OrderItem> items = new ArrayList<>();

            // Cargar items de medicamentos
            List<MedicineOrderItemEntity> medItems = medicineItemRepository.findByOrderNumber(orderNumber);
            for (MedicineOrderItemEntity itemEntity : medItems) {
                MedicineOrderItem item = new MedicineOrderItem();
                item.setOrderNumber(itemEntity.getOrderNumber());
                item.setItemNumber(itemEntity.getItemNumber());
                item.setMedicineName(itemEntity.getMedicineName());
                item.setDose(itemEntity.getDose());
                item.setTreatmentDuration(itemEntity.getTreatmentDuration());
                item.setCost(itemEntity.getCost());
                items.add(item);
            }

            // Cargar items de exámenes diagnósticos
            List<DiagnosticTestOrderItemEntity> diagItems = diagnosticTestItemRepository.findByOrderNumber(orderNumber);
            for (DiagnosticTestOrderItemEntity itemEntity : diagItems) {
                DiagnosticTestOrderItem item = new DiagnosticTestOrderItem();
                item.setOrderNumber(itemEntity.getOrderNumber());
                item.setItemNumber(itemEntity.getItemNumber());
                item.setTestName(itemEntity.getTestName());
                item.setQuantity(itemEntity.getQuantity());
                item.setRequiresSpecialist(itemEntity.getRequiresSpecialist());
                item.setSpecialistType(itemEntity.getSpecialistType());
                item.setCost(itemEntity.getCost());
                items.add(item);
            }

            // Cargar items de procedimientos
            List<ProcedureOrderItemEntity> procItems = procedureItemRepository.findByOrderNumber(orderNumber);
            for (ProcedureOrderItemEntity itemEntity : procItems) {
                ProcedureOrderItem item = new ProcedureOrderItem();
                item.setOrderNumber(itemEntity.getOrderNumber());
                item.setItemNumber(itemEntity.getItemNumber());
                item.setProcedureName(itemEntity.getProcedureName());
                item.setRepetitions(itemEntity.getRepetitions());
                item.setFrequency(itemEntity.getFrequency());
                item.setRequiresSpecialist(itemEntity.getRequiresSpecialist());
                item.setSpecialistType(itemEntity.getSpecialistType());
                item.setCost(itemEntity.getCost());
                items.add(item);
            }

            order.setItems(items);
        }

        return order;
    }

    @Override
    public boolean existsByOrderNumber(String orderNumber) {
        return repository.existsByOrderNumber(orderNumber);
    }

    @Override
    public List<Order> findByPatient(String patientId) {
        return repository.findByPatientId(patientId).stream().map(OrderMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Order> findByDoctor(String doctorId) {
        return repository.findByDoctorId(doctorId).stream().map(OrderMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Order> findAll() {
        return repository.findAll().stream().map(OrderMapper::toDomain).collect(Collectors.toList());
    }
}

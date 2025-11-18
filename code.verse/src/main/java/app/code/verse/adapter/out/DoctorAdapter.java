package app.code.verse.adapter.out;

import app.code.verse.domain.model.MedicalHistory;
import app.code.verse.domain.model.Order;
import app.code.verse.domain.ports.DoctorPort;
import app.code.verse.domain.services.CreateMedicalHistory;
import app.code.verse.domain.services.AddOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorAdapter implements DoctorPort {

    @Autowired
    private CreateMedicalHistory createMedicalHistory;

    @Autowired
    private AddOrder addOrder;

    @Override
    public void createMedicalHistory(MedicalHistory history) throws Exception {
        createMedicalHistory.create(history);
    }

    @Override
    public void addOrder(Order order) throws Exception {
        addOrder.add(order);
    }
}

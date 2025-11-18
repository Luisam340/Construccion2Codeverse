package app.code.verse.adapter.out;

import app.code.verse.domain.model.Employee;
import app.code.verse.domain.ports.EmployeePort;
import app.code.verse.infrastructure.persistence.entities.EmployeeEntity;
import app.code.verse.infrastructure.persistence.mapper.EmployeeMapper;
import app.code.verse.infrastructure.persistence.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeAdapter implements EmployeePort {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee create(Employee employee) throws Exception {
        EmployeeEntity entitySave = EmployeeMapper.toEntity(employee);
        EmployeeEntity save = employeeRepository.save(entitySave);
        return EmployeeMapper.toDomain(save);
    }

    @Override
    public Employee deleteById(Employee employee) throws Exception {
        EmployeeEntity entityToDelete = employeeRepository.findByIdNumber(employee.getIdNumber());
        employeeRepository.delete(entityToDelete);
        return EmployeeMapper.toDomain(entityToDelete);
    }

    @Override
    public Employee update(Employee employee) throws Exception {
        EmployeeEntity entityToUpdate = employeeRepository.findByIdNumber(employee.getIdNumber());
        EmployeeMapper.partialUpdate(employee, entityToUpdate);
        EmployeeEntity save = employeeRepository.save(entityToUpdate);
        return EmployeeMapper.toDomain(save);
    }

    @Override
    public Employee findByIdNumber(String idNumber) {
        EmployeeEntity employeeEntity = employeeRepository.findByIdNumber(idNumber);
        return EmployeeMapper.toDomain(employeeEntity);
    }

    @Override
    public Employee findByUserName(String userName) {
        EmployeeEntity employeeEntity = employeeRepository.findByUserName(userName);
        return EmployeeMapper.toDomain(employeeEntity);
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll().stream().map(EmployeeMapper::toDomain).collect(Collectors.toList());
    }

}

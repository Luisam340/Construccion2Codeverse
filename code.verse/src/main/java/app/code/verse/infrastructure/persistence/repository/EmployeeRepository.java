package app.code.verse.infrastructure.persistence.repository;

import app.code.verse.infrastructure.persistence.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
    public EmployeeEntity findByIdNumber(String document);
    public List<EmployeeEntity> findAll();
    public EmployeeEntity findByUserName(String userName);
}

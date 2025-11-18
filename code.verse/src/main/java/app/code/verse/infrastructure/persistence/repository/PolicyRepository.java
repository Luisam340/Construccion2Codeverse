package app.code.verse.infrastructure.persistence.repository;

import app.code.verse.infrastructure.persistence.entities.PolicyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolicyRepository extends JpaRepository<PolicyEntity, Long> {
    public PolicyEntity findByPolicyNumber(String policyNumber);
    public PolicyEntity findByPatientId(Long patientId);
    public PolicyEntity findByPatientIdNumber(String idNumber);
}

package app.code.verse.adapter.in.builder;

import app.code.verse.adapter.in.validators.PolicyValidator;
import app.code.verse.domain.model.Policy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class PolicyBuilder {

    @Autowired
    private PolicyValidator policyValidator;

    public Policy build(String companyName, String policyNumber, Boolean active, LocalDate expirationDate) throws Exception {
        Policy policy = new Policy();
        policy.setCompanyName(policyValidator.policyNameValidator(companyName));
        policy.setPolicyNumber(policyValidator.policyNumberValidator(policyNumber));
        policy.setActive(active != null ? active : true);
        policy.setExpirationDate(policyValidator.policyDateValidator(expirationDate));
        return policy;
    }

    public Policy update(Policy policy, String companyName, String policyNumber, Boolean active, LocalDate expirationDate) throws Exception {
        policy.setCompanyName(policyValidator.policyNameValidator(companyName));
        policy.setPolicyNumber(policyValidator.policyNumberValidator(policyNumber));
        policy.setActive(active != null ? active : policy.isActive());
        policy.setExpirationDate(policyValidator.policyDateValidator(expirationDate));
        return policy;
    }
}

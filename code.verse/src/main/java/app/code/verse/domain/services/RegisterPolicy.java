package app.code.verse.domain.services;

import app.code.verse.domain.model.Policy;
import app.code.verse.domain.ports.PolicyPort;

public class RegisterPolicy {
    private PolicyPort policyPort;

    public RegisterPolicy(PolicyPort policyPort){
        this.policyPort = policyPort;
    }

    public void create(String patientIdNumber, Policy policy) throws Exception {
        checkIfPolicytExists(policy);
        policyPort.register(patientIdNumber, policy);
    }

    private void checkIfPolicytExists(Policy policy) throws Exception {
        if (policyPort.findPolicyById(policy.getPolicyNumber())!=null) {
            throw new IllegalArgumentException("Ya hay un cliente con esta póliza registrada");
        }
    }
}

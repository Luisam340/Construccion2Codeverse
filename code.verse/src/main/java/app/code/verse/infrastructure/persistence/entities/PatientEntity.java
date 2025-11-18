package app.code.verse.infrastructure.persistence.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "patient")
public class PatientEntity extends PersonEntity {

    @Column(nullable = false)
    private String gender;

    @OneToOne(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private EmergencyContactEntity emergencyContact;

    @OneToOne(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private PolicyEntity policy;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public EmergencyContactEntity getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(EmergencyContactEntity emergencyContact) {
        this.emergencyContact = emergencyContact;
        if (emergencyContact != null) {
            emergencyContact.setPatient(this);
        }
    }

    public PolicyEntity getPolicy() {
        return policy;
    }

    public void setPolicy(PolicyEntity policy) {
        this.policy = policy;
        if (policy != null) {
            policy.setPatient(this);
        }
    }
}

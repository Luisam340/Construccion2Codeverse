package app.code.verse.infrastructure.persistence.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "invoice")
public class InvoiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "patient_id", nullable = false)
    private String patientId;

    @Column(name = "patient_name", nullable = false, length = 100)
    private String patientName;

    @Column(name = "patient_age", nullable = false)
    private Integer patientAge;

    @Column(name = "doctor_name", nullable = false, length = 100)
    private String doctorName;

    @Column(name = "insurance_company", length = 100)
    private String insuranceCompany;

    @Column(name = "policy_number", length = 50)
    private String policyNumber;

    @Column(name = "policy_validity_days")
    private Integer policyValidityDays;

    @Column(name = "policy_expiration_date")
    private LocalDate policyExpirationDate;

    @Column(name = "issue_date", nullable = false)
    private LocalDate issueDate;

    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;

    @Column(name = "copay_amount")
    private Double copayAmount;

    @Column(name = "insurance_coverage")
    private Double insuranceCoverage;

    @Column(name = "patient_pays", nullable = false)
    private Double patientPays;

    @Column(name = "services_included", columnDefinition = "TEXT")
    private String servicesIncluded;

    @Column(name = "year_copay_total")
    private Double yearCopayTotal;

    @Column(name = "copay_exempt", nullable = false)
    private Boolean copayExempt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Integer getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(Integer patientAge) {
        this.patientAge = patientAge;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getInsuranceCompany() {
        return insuranceCompany;
    }

    public void setInsuranceCompany(String insuranceCompany) {
        this.insuranceCompany = insuranceCompany;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public Integer getPolicyValidityDays() {
        return policyValidityDays;
    }

    public void setPolicyValidityDays(Integer policyValidityDays) {
        this.policyValidityDays = policyValidityDays;
    }

    public LocalDate getPolicyExpirationDate() {
        return policyExpirationDate;
    }

    public void setPolicyExpirationDate(LocalDate policyExpirationDate) {
        this.policyExpirationDate = policyExpirationDate;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getCopayAmount() {
        return copayAmount;
    }

    public void setCopayAmount(Double copayAmount) {
        this.copayAmount = copayAmount;
    }

    public Double getInsuranceCoverage() {
        return insuranceCoverage;
    }

    public void setInsuranceCoverage(Double insuranceCoverage) {
        this.insuranceCoverage = insuranceCoverage;
    }

    public Double getPatientPays() {
        return patientPays;
    }

    public void setPatientPays(Double patientPays) {
        this.patientPays = patientPays;
    }

    public String getServicesIncluded() {
        return servicesIncluded;
    }

    public void setServicesIncluded(String servicesIncluded) {
        this.servicesIncluded = servicesIncluded;
    }

    public Double getYearCopayTotal() {
        return yearCopayTotal;
    }

    public void setYearCopayTotal(Double yearCopayTotal) {
        this.yearCopayTotal = yearCopayTotal;
    }

    public Boolean getCopayExempt() {
        return copayExempt;
    }

    public void setCopayExempt(Boolean copayExempt) {
        this.copayExempt = copayExempt;
    }
}

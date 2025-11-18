# Functional Guide - San Rafael Clinic Management System

## Table of Contents

1. [Authentication and Authorization](#1-authentication-and-authorization)
2. [Human Resources Management](#2-human-resources-management)
3. [Patient Management](#3-patient-management)
4. [Doctor Module](#4-doctor-module)
5. [Nursing Module](#5-nursing-module)
6. [Information Support - Inventory](#6-information-support---inventory)
7. [Billing System](#7-billing-system)
8. [Medical Appointments System](#8-medical-appointments-system)

---

## 1. Authentication and Authorization

### 1.1 Introduction

The San Rafael Clinic system implements a robust authentication and authorization system based on **JSON Web Tokens (JWT)** and **Spring Security**, allowing granular access control based on user roles.

### 1.2 Security Architecture

The system uses a layered security architecture:

```
┌─────────────────────────────────────┐
│   Client (Postman/Frontend)         │
└──────────────┬──────────────────────┘
               │
               ▼
┌─────────────────────────────────────┐
│   Authentication Layer              │
│   - AuthRestController              │
│   - JWT Token Generation            │
└──────────────┬──────────────────────┘
               │
               ▼
┌─────────────────────────────────────┐
│   Spring Security Filter Chain      │
│   - JwtAuthenticationFilter         │
│   - Token Validation                │
└──────────────┬──────────────────────┘
               │
               ▼
┌─────────────────────────────────────┐
│   Authorization Layer               │
│   - @PreAuthorize Annotations       │
│   - Role-Based Access Control       │
└──────────────┬──────────────────────┘
               │
               ▼
┌─────────────────────────────────────┐
│   Controllers (Endpoints)           │
│   - Business Logic                  │
└─────────────────────────────────────┘
```

### 1.3 Complete Authentication Flow

#### Step 1: User Login

**Endpoint:** `POST /hospital/san-rafael/auth/login`

**Request:**
```json
{
    "username": "tbermudez",
    "password": "lui123M*"
}
```

**Internal Process:**

1. Client sends credentials (username + password)
2. `AuthRestController` receives the request
3. `AuthenticationService` validates credentials:
   ```java
   public Map<String, Object> login(String username, String password) {
       // 1. Search for user in database
       Employee employee = employeePort.findByUserName(username);

       // 2. Validate user exists
       if (employee == null) {
           throw new IllegalArgumentException("User not found");
       }

       // 3. Verify password
       if (!employee.getPassword().equals(password)) {
           throw new IllegalArgumentException("Incorrect password");
       }

       // 4. Verify user is active
       if (!employee.getStatus()) {
           throw new IllegalArgumentException("Inactive user");
       }

       // 5. Generate JWT token
       String token = jwtUtil.generateToken(employee.getUserName(),
                                           employee.getRole(),
                                           employee.getIdNumber());

       // 6. Return response with token and user data
       return response;
   }
   ```

**Successful Response:**
```json
{
    "success": true,
    "message": "Successful login",
    "data": {
        "token": "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiRG9jdG9yIiwiaWROdW1iZXIiOiIxMDEwMTM5NzQiLCJzdWIiOiJ0YmVybXVkZXoiLCJpYXQiOjE3NjM0NDExOTIsImV4cCI6MTc2MzUyNzU5Mn0.Kvs4xcfbvroetwvB76ll7dA6zp807pUbvDfyArdmJFg",
        "user": {
            "idNumber": "101013974",
            "name": "Tomás Bermudez",
            "email": "tomasbm@hospital.com",
            "role": "Doctor",
            "userName": "tbermudez",
            "status": true
        }
    }
}
```

#### Step 2: JWT Token Structure

The generated JWT token has three parts separated by dots:

```
eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiRG9jdG9yIiwiaWROdW1iZXIiOiIxMDEwMTM5NzQiLCJzdWIiOiJ0YmVybXVkZXoiLCJpYXQiOjE3NjM0NDExOTIsImV4cCI6MTc2MzUyNzU5Mn0.Kvs4xcfbvroetwvB76ll7dA6zp807pUbvDfyArdmJFg
    │                           │                                                                                                              │
    └── Header                  └── Payload                                                                                                    └── Signature
```

**Header:**
```json
{
    "alg": "HS256",
    "typ": "JWT"
}
```

**Payload (Data):**
```json
{
    "role": "Doctor",
    "idNumber": "101013974",
    "sub": "tbermudez",
    "iat": 1763441192,  // Issue date (timestamp)
    "exp": 1763527592   // Expiration date (timestamp) - 24 hours later
}
```

**Signature:**
```
HMACSHA256(
    base64UrlEncode(header) + "." +
    base64UrlEncode(payload),
    secret_key
)
```

#### Step 3: Using Token in Subsequent Requests

Once the token is obtained, the client must include it in the `Authorization` header of each request:

```http
GET /hospital/san-rafael/patients HTTP/1.1
Host: localhost:8080
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiRG9jdG9yIi...
Content-Type: application/json
```

#### Step 4: Token Validation (Spring Security Filter)

Each request passes through the security filter that validates the token:

```java
// JwtAuthenticationFilter (simplified pseudocode)
public void doFilterInternal(HttpServletRequest request) {
    // 1. Extract token from Authorization header
    String authHeader = request.getHeader("Authorization");
    String token = authHeader.substring(7); // Remove "Bearer "

    // 2. Validate token
    if (jwtUtil.validateToken(token)) {
        // 3. Extract information from token
        String username = jwtUtil.extractUsername(token);
        String role = jwtUtil.extractRole(token);

        // 4. Create authentication object
        Authentication auth = new UsernamePasswordAuthenticationToken(
            username, null, getAuthorities(role)
        );

        // 5. Set authentication in security context
        SecurityContextHolder.getContext().setAuthentication(auth);
    } else {
        // Invalid or expired token
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }

    // 6. Continue with filter chain
    filterChain.doFilter(request, response);
}
```

#### Step 5: Authorization Verification (@PreAuthorize)

Once authenticated, Spring Security verifies if the user has the required role:

```java
@RestController
@RequestMapping("/hospital/san-rafael/patients")
public class PatientRestController {

    @PostMapping
    @PreAuthorize("hasRole('PERSONAL_ADMINISTRATIVO')")
    public ResponseEntity<Map<String, Object>> createPatient(@RequestBody Map<String, Object> requestBody) {
        // Only users with "Personal Administrativo" role can execute this method
        // If token has another role, Spring Security returns 403 Forbidden
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('PERSONAL_ADMINISTRATIVO', 'DOCTOR', 'ENFERMERA')")
    public ResponseEntity<Map<String, Object>> getAllPatients() {
        // Users with any of these 3 roles can execute this method
    }
}
```

### 1.4 System Roles

The system defines 5 main roles:

| Role | Main Permissions |
|------|------------------|
| **Human Resources** | Full CRUD of employees, no access to patients or inventory |
| **Administrative Staff** | CRUD of patients, policies, emergency contacts, invoice generation, appointment management |
| **Doctor** | Create medical orders, visit records, consult medical history, consult invoices |
| **Nurse** | Register vital signs, administer medications, create visit records, consult patients |
| **Support** | Full CRUD of inventories (medications, diagnostic tests, procedures) |

### 1.5 Password Validation

Passwords must comply with robust security policies:

```java
public String passwordValidator(String value) throws Exception {
    // Minimum 8 characters
    minimumSizeValidator(value, "password", 8);

    // Must contain at least:
    // - One uppercase letter
    // - One number
    // - One special character
    if (!value.matches(".*[A-Z].*") ||
        !value.matches(".*\\d.*") ||
        !value.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
        throw new IllegalArgumentException(
            "Password must contain at least one uppercase letter, " +
            "one number and one special character"
        );
    }

    return value;
}
```

**Examples:**
- ✅ `Pass*123` - Valid
- ✅ `lui123M*` - Valid
- ❌ `password` - No uppercase, number or special character
- ❌ `Pass123` - No special character
- ❌ `Pass*12` - Less than 8 characters

### 1.6 Token Expiration and Renewal

- **Token duration:** 24 hours from generation
- **Automatic validation:** Spring Security rejects expired tokens with 401 error
- **Renewal:** User must login again to obtain a new token

### 1.7 Additional Security

#### Prevention of common attacks:

1. **SQL Injection:** JPA/Hibernate uses parameterized queries automatically
2. **XSS:** Input data validation, frontend sanitization
3. **CSRF:** Disabled since stateless authentication with JWT is used
4. **Session Fixation:** No sessions used, everything is stateless
5. **Brute Force:** Rate limiting can be implemented (not currently included)

---

## 2. Human Resources Management

### 2.1 General Description

The Human Resources module manages the entire employee lifecycle of the hospital, from creation to deletion. Only users with **"Human Resources"** role have full access to this module.

### 2.2 Functionalities

#### 2.2.1 Create Employee

**Endpoint:** `POST /hospital/san-rafael/employees`

**Required Authentication:** `@PreAuthorize("hasRole('RECURSOS_HUMANOS')")`

**Required Data:**
```json
{
    "name": "Juan Pérez González",
    "idNumber": "1234567890",
    "email": "juan.perez@hospital.com",
    "phoneNumber": "3001234567",
    "birthDate": "15/05/1990",
    "address": "Calle 123 #45-67",
    "gender": "M",
    "rol": "Doctor",
    "userName": "jperez",
    "password": "JPerez123*",
    "status": true
}
```

**Applied Validations:**

1. **Name:** Cannot be empty
2. **ID Number:** Must be unique in the system
3. **Email:** Valid format (regex: `^[A-Za-z0-9+_.-]+@(.+)$`)
4. **Phone:** Exactly 10 digits
5. **Birth Date:**
   - Format: DD/MM/YYYY
   - Maximum age: 150 years
6. **Gender:** "M" (Male) or "F" (Female)
7. **Username:**
   - Unique
   - Maximum 15 characters
   - Only letters and numbers
8. **Password:**
   - Minimum 8 characters
   - At least one uppercase letter
   - At least one number
   - At least one special character

**Valid Roles:**
- Doctor
- Enfermera (Nurse)
- Personal Administrativo (Administrative Staff)
- Recursos Humanos (Human Resources)
- Soporte (Support)

**Internal Flow:**
```java
public void create(Employee employee) throws Exception {
    // 1. Validate employee data
    employeeValidator.validate(employee);

    // 2. Verify username is unique
    if (employeePort.existsByUserName(employee.getUserName())) {
        throw new IllegalArgumentException("Username already exists");
    }

    // 3. Verify email is unique
    if (employeePort.existsByEmail(employee.getEmail())) {
        throw new IllegalArgumentException("Email already registered");
    }

    // 4. Verify idNumber is unique
    if (employeePort.existsByIdNumber(employee.getIdNumber())) {
        throw new IllegalArgumentException("ID number already registered");
    }

    // 5. Set active status by default
    employee.setStatus(true);

    // 6. Save to database
    employeePort.save(employee);
}
```

#### 2.2.2 List All Employees

**Endpoint:** `GET /hospital/san-rafael/employees`

**Returns:** Complete list of employees with all their data (except password)

#### 2.2.3 Search Employee by ID

**Endpoint:** `GET /hospital/san-rafael/employees/{idNumber}`

**Parameter:** `idNumber` - Employee's document number

#### 2.2.4 Search Employee by Username

**Endpoint:** `GET /hospital/san-rafael/employees/search?userName=tbermudez`

**Query Parameter:** `userName`

#### 2.2.5 Update Employee

**Endpoint:** `PUT /hospital/san-rafael/employees/{idNumber}`

Allows updating all fields except:
- `idNumber` (cannot be changed)
- `status` (updated with another endpoint if required)

#### 2.2.6 Delete Employee

**Endpoint:** `DELETE /hospital/san-rafael/employees/{idNumber}`

Performs logical deletion (marks as inactive) or physical depending on configuration.

### 2.3 Security Restrictions

Users with "Human Resources" role **CANNOT**:
- View or manage patients
- View or manage inventories (medications, tests, procedures)
- Generate invoices
- Create medical orders

If they attempt to access these endpoints, they will receive **403 Forbidden** error.

---

## 3. Patient Management

### 3.1 General Description

The patient management module allows registering and administering patient information, including personal data, insurance policies, and emergency contacts.

### 3.2 Roles with Access

- **Write (Full CRUD):** Administrative Staff
- **Read (Query):** Administrative Staff, Doctor, Nurse

### 3.3 Functionalities

#### 3.3.1 Register Complete Patient

**Endpoint:** `POST /hospital/san-rafael/patients`

**Authentication:** `@PreAuthorize("hasRole('PERSONAL_ADMINISTRATIVO')")`

**Data Structure:**
```json
{
    "patient": {
        "idNumber": "1020234567",
        "name": "Carlos Mendoza",
        "birthDate": "15/03/1985",
        "gender": "M",
        "address": "Calle 45 #23-67",
        "phoneNumber": "3145678901",
        "email": "carlos@test.com"
    },
    "policy": {
        "companyName": "Seguros Bolivar",
        "policyNumber": "POL123456",
        "active": true,
        "expirationDate": "31/12/2026"
    },
    "emergencyContact": {
        "name": "Maria Mendoza",
        "kinship": "Sister",
        "phoneNumber": "3156789012"
    }
}
```

**Features:**
- Policy and emergency contact are **optional**
- If policy is provided, it's automatically associated with the patient
- If emergency contact is provided, it's automatically associated with the patient
- All data validations are applied automatically

**Patient Validations:**

1. **ID Number:** Unique in the system
2. **Email:** Valid format
3. **Phone:** 10 digits
4. **Birth Date:**
   - Format DD/MM/YYYY
   - Maximum age 150 years
5. **Gender:** "M" or "F"

**Policy Validations:**

1. **Policy Number:** Maximum 20 characters
2. **Company:** Cannot be empty
3. **Expiration Date:**
   - Format DD/MM/YYYY
   - Cannot be before today

**Emergency Contact Validations:**

1. **Name:** Cannot be empty
2. **Kinship:** Cannot be empty
3. **Phone:** 10 digits

**Internal Flow:**
```java
public void create(Patient patient, Policy policy, EmergencyContact contact) throws Exception {
    // 1. Validate patient data
    patientValidator.validate(patient);

    // 2. Verify ID uniqueness
    if (patientPort.existsByIdNumber(patient.getIdNumber())) {
        throw new IllegalArgumentException("Patient already registered");
    }

    // 3. Set active status
    patient.setStatus(true);

    // 4. Save patient
    patientPort.save(patient);

    // 5. If there's a policy, validate and save
    if (policy != null) {
        policyValidator.validate(policy);
        policy.setPatientId(patient.getIdNumber());
        policyPort.save(policy);
    }

    // 6. If there's an emergency contact, validate and save
    if (contact != null) {
        contactValidator.validate(contact);
        contact.setPatientId(patient.getIdNumber());
        emergencyContactPort.save(contact);
    }
}
```

#### 3.3.2 List All Patients

**Endpoint:** `GET /hospital/san-rafael/patients`

**Authentication:** `@PreAuthorize("hasAnyRole('PERSONAL_ADMINISTRATIVO', 'DOCTOR', 'ENFERMERA')")`

**Response:**
```json
{
    "success": true,
    "count": 2,
    "data": [
        {
            "patient": {
                "id": 1,
                "idNumber": "1020234567",
                "name": "Carlos Mendoza",
                "email": "carlos@test.com",
                "phoneNumber": "3145678901",
                "birthDate": "15/03/1985",
                "address": "Calle 45",
                "status": true,
                "gender": "Male"
            },
            "policy": {
                "companyName": "Seguros Bolivar",
                "policyNumber": "POL123456",
                "active": true,
                "expirationDate": "31/12/2026"
            },
            "emergencyContact": {
                "name": "Maria Mendoza",
                "kinship": "Sister",
                "phoneNumber": "3156789012"
            }
        }
    ]
}
```

#### 3.3.3 Search Patient by ID

**Endpoint:** `GET /hospital/san-rafael/patients/{idNumber}`

Returns the patient with their policy and emergency contact.

#### 3.3.4 Search Patients by Name

**Endpoint:** `GET /hospital/san-rafael/patients/search?name=Carlos`

**Features:**
- Partial search (case insensitive)
- Returns all patients whose name contains the searched text

#### 3.3.5 Update Patient Data

**Endpoint:** `PUT /hospital/san-rafael/patients/{idNumber}`

**Authentication:** Administrative Staff only

Allows updating: name, email, phone, address, birth date

#### 3.3.6 Update Policy

**Endpoint:** `PUT /hospital/san-rafael/patients/{idNumber}/policy`

**Body:**
```json
{
    "companyName": "Seguros XYZ",
    "policyNumber": "POL-UPDATED",
    "active": true,
    "expirationDate": "31/01/2026"
}
```

#### 3.3.7 Update Emergency Contact

**Endpoint:** `PUT /hospital/san-rafael/patients/{idNumber}/emergency-contact`

#### 3.3.8 Delete Patient

**Endpoint:** `DELETE /hospital/san-rafael/patients/{idNumber}`

Deletes the patient and all associated data (policy, emergency contact).

---

## 4. Doctor Module

### 4.1 General Description

The doctor module manages the creation of medical histories, visit records, and medical orders. It is the core of the medical care process.

### 4.2 Main Functionalities

#### 4.2.1 Create Visit Record

**Endpoint:** `POST /hospital/san-rafael/doctors/visit-records`

**Authentication:** `@PreAuthorize("hasRole('DOCTOR')")`

**Required Data:**
```json
{
    "patientId": "1020234567",
    "doctorId": "101013974",
    "reason": "Consultation for abdominal pain",
    "symptoms": "Pain in lower right quadrant, fever",
    "diagnosis": "Acute appendicitis"
}
```

**Process:**
1. Validates patient exists
2. Validates doctor exists
3. Automatically generates current date and time
4. Creates visit record
5. Adds record to patient's medical history

#### 4.2.2 Consult Complete Medical History

**Endpoint:** `GET /hospital/san-rafael/doctors/medical-history/patient/{patientId}`

**Response includes:**
```json
{
    "success": true,
    "data": {
        "patient": {
            "idNumber": "1020234567",
            "name": "Carlos Mendoza",
            "birthDate": "15/03/1985",
            "age": 40
        },
        "policy": {
            "companyName": "Seguros Bolivar",
            "policyNumber": "POL123456",
            "active": true,
            "expirationDate": "31/12/2026",
            "daysUntilExpiration": 408
        },
        "emergencyContact": {
            "name": "Maria Mendoza",
            "kinship": "Sister",
            "phoneNumber": "3156789012"
        },
        "medicalHistory": [
            {
                "visitDate": "18/11/2025",
                "doctorName": "Dr. Tomás Bermudez",
                "reason": "Consultation for abdominal pain",
                "symptoms": "Pain in lower right quadrant, fever",
                "diagnosis": "Acute appendicitis",
                "vitalSigns": [
                    {
                        "bloodPressure": "120/80",
                        "temperature": 38.5,
                        "pulse": 95,
                        "oxygenLevel": 97.0
                    }
                ],
                "orders": ["ORD-001"],
                "medicinesAdministered": []
            }
        ]
    }
}
```

#### 4.2.3 Consult Medical History by Date

**Endpoint:** `GET /hospital/san-rafael/doctors/medical-history/patient/{patientId}/date/{date}`

**Date Format:** `dd-MM-yyyy` (Example: `18-11-2025`)

Returns only records from the specific date.

#### 4.2.4 Consult Doctor's Medical Histories

**Endpoint:** `GET /hospital/san-rafael/doctors/medical-history/doctor/{doctorId}`

Returns all medical histories created by the doctor.

### 4.3 Medical Orders System

#### 4.3.1 Order Types

The system supports 3 types of items in orders:

1. **Medications** (`MedicineOrderItem`)
   - Medicine name
   - Dose (includes application instructions)
   - Treatment duration
   - Cost

2. **Diagnostic Tests** (`DiagnosticTestOrderItem`)
   - Test name
   - Quantity
   - Requires specialist (yes/no)
   - Specialist type (if applicable)
   - Cost

3. **Procedures** (`ProcedureOrderItem`)
   - Procedure name
   - Repetitions
   - Frequency
   - Requires specialist (yes/no)
   - Specialist type (if applicable)
   - Cost

#### 4.3.2 Create Medical Order - Medications

**Endpoint:** `POST /hospital/san-rafael/doctors/orders`

**Example - Medication Order:**
```json
{
    "orderNumber": "ORD001",
    "patientId": "1020234567",
    "doctorId": "101013974",
    "items": [
        {
            "type": "MEDICINE",
            "itemNumber": 1,
            "cost": 25000.0,
            "medicineName": "Acetaminophen",
            "dose": "500mg every 8 hours orally",
            "treatmentDuration": "5 days"
        },
        {
            "type": "MEDICINE",
            "itemNumber": 2,
            "cost": 80000.0,
            "medicineName": "Amoxicillin",
            "dose": "500mg every 12 hours orally, take with food",
            "treatmentDuration": "7 days"
        }
    ]
}
```

**Validations:**
- Order number: exactly 6 characters
- Unique order number in the system
- At least 1 item in the order
- Unique item numbers within the order
- Cost greater than 0

#### 4.3.3 Create Medical Order - Diagnostic Tests

**Example:**
```json
{
    "orderNumber": "EXAM01",
    "patientId": "1020234567",
    "doctorId": "101013974",
    "items": [
        {
            "type": "DIAGNOSTIC_TEST",
            "itemNumber": 1,
            "cost": 150000.0,
            "testName": "Brain MRI",
            "quantity": 1,
            "requiresSpecialist": true,
            "specialistType": "Radiologist"
        },
        {
            "type": "DIAGNOSTIC_TEST",
            "itemNumber": 2,
            "cost": 50000.0,
            "testName": "Complete blood count",
            "quantity": 1,
            "requiresSpecialist": false,
            "specialistType": ""
        }
    ]
}
```

**Important Business Rule:**
> When an order contains diagnostic tests, it **CANNOT contain medications or procedures** in the same order, as there is no diagnostic certainty until results are obtained.

#### 4.3.4 Create Medical Order - Procedures

**Example:**
```json
{
    "orderNumber": "PROC01",
    "patientId": "1020234567",
    "doctorId": "101013974",
    "items": [
        {
            "type": "PROCEDURE",
            "itemNumber": 1,
            "cost": 300000.0,
            "procedureName": "Advanced wound care",
            "repetitions": 3,
            "frequency": "Every 2 days",
            "requiresSpecialist": false,
            "specialistType": ""
        }
    ]
}
```

**Orders can mix medications and procedures:**
```json
{
    "orderNumber": "MIX001",
    "patientId": "1020234567",
    "doctorId": "101013974",
    "items": [
        {
            "type": "PROCEDURE",
            "itemNumber": 1,
            "cost": 50000.0,
            "procedureName": "Vital signs monitoring by nurse",
            "repetitions": 9,
            "frequency": "Every 8 hours during hospitalization"
        },
        {
            "type": "MEDICINE",
            "itemNumber": 2,
            "cost": 150000.0,
            "medicineName": "Ceftriaxone",
            "dose": "2g IV every 12 hours, dilute in 100ml saline solution",
            "treatmentDuration": "5 days"
        }
    ]
}
```

#### 4.3.5 Hospitalization as Procedure

**According to system specification**, hospitalization is handled as a procedure:

**Example of complete hospitalization order:**
```json
{
    "orderNumber": "HOS004",
    "patientId": "1020234567",
    "doctorId": "101013974",
    "items": [
        {
            "type": "PROCEDURE",
            "itemNumber": 1,
            "cost": 800000.0,
            "procedureName": "Hospitalization for acute respiratory infection",
            "repetitions": 5,
            "frequency": "5 days"
        },
        {
            "type": "PROCEDURE",
            "itemNumber": 2,
            "cost": 50000.0,
            "procedureName": "Vital signs monitoring by nurse",
            "repetitions": 15,
            "frequency": "Every 8 hours during hospitalization"
        },
        {
            "type": "PROCEDURE",
            "itemNumber": 3,
            "cost": 30000.0,
            "procedureName": "IV medication administration by nurse",
            "repetitions": 15,
            "frequency": "Every 8 hours during hospitalization"
        },
        {
            "type": "PROCEDURE",
            "itemNumber": 4,
            "cost": 25000.0,
            "procedureName": "Wound care and dressing changes by nurse",
            "repetitions": 5,
            "frequency": "Once daily during hospitalization"
        },
        {
            "type": "MEDICINE",
            "itemNumber": 5,
            "cost": 150000.0,
            "medicineName": "Ceftriaxone",
            "dose": "2g IV every 12 hours, dilute in 100ml saline solution, administer in 30 minutes",
            "treatmentDuration": "5 days"
        },
        {
            "type": "MEDICINE",
            "itemNumber": 6,
            "cost": 80000.0,
            "medicineName": "Dipyrone",
            "dose": "2g IV every 8 hours PRN for fever above 38C, administer slowly",
            "treatmentDuration": "5 days"
        }
    ]
}
```

**Features:**
- Item #1: Hospitalization (main procedure)
- Items #2-4: Nursing interventions (procedures)
- Items #5-6: Medications with detailed application instructions

#### 4.3.6 Query Orders

**All orders:**
```
GET /hospital/san-rafael/doctors/orders
```

**Order by number:**
```
GET /hospital/san-rafael/doctors/orders/{orderNumber}
```

**Orders by patient:**
```
GET /hospital/san-rafael/doctors/orders/patient/{patientId}
```

**Orders by doctor:**
```
GET /hospital/san-rafael/doctors/orders/doctor/{doctorId}
```

---

## 5. Nursing Module

### 5.1 General Description

The nursing module allows registering vital signs, administering medications, and creating preliminary visit records.

### 5.2 Functionalities

#### 5.2.1 Register Vital Signs

**Endpoint:** `POST /hospital/san-rafael/nurses/vital-signs`

**Authentication:** `@PreAuthorize("hasRole('ENFERMERA')")`

**Required Data:**
```json
{
    "visitRecordId": 1,
    "patientId": "1020234567",
    "bloodPressure": "120/80",
    "temperature": 36.5,
    "pulse": 72,
    "oxygenLevel": 98.0,
    "recordedBy": "1030789012"
}
```

**Validations:**
- Blood pressure: format "XXX/XX" (Example: 120/80)
- Temperature: valid range 35.0 - 42.0°C
- Pulse: valid range 40 - 200 bpm
- Oxygen level: valid range 70.0 - 100.0%

**Features:**
- Date and time are automatically recorded
- Associated with patient's visit record
- Multiple vital sign records can be associated with the same visit

#### 5.2.2 Query Vital Signs

**By patient:**
```
GET /hospital/san-rafael/nurses/vital-signs/patient/{patientId}
```

**By visit record:**
```
GET /hospital/san-rafael/nurses/vital-signs/visit-record/{visitRecordId}
```

#### 5.2.3 Register Medication Administration

**Endpoint:** `POST /hospital/san-rafael/nurses/medicine-administration`

**Required Data:**
```json
{
    "visitRecordId": 1,
    "patientId": "1020234567",
    "orderNumber": "ORD001",
    "itemNumber": 1,
    "medicineName": "Ceftriaxone",
    "doseAdministered": "2g IV",
    "administeredBy": "1030789012",
    "observations": "Patient tolerated medication well, no adverse reactions"
}
```

**Process:**
1. Validates medical order exists
2. Validates item corresponds to a medication
3. Records administration date and time
4. Saves nurse's observations
5. Associates with visit record

**Features:**
- Complete traceability of administered medications
- Allows detailed observations
- Record of who administered the medication

#### 5.2.4 Query Medication Administrations

**By patient:**
```
GET /hospital/san-rafael/nurses/medicine-administration/patient/{patientId}
```

**By visit record:**
```
GET /hospital/san-rafael/nurses/medicine-administration/visit-record/{visitRecordId}
```

**By medical order:**
```
GET /hospital/san-rafael/nurses/medicine-administration/order/{orderNumber}
```

#### 5.2.5 Create Visit Record (Nursing)

**Endpoint:** `POST /hospital/san-rafael/nurses/visit-records`

Similar to doctor's, but typically without complete diagnosis:

```json
{
    "patientId": "1020234567",
    "doctorId": "101013974",
    "reason": "Post-surgical nursing control",
    "symptoms": "Mild pain at incision site",
    "diagnosis": ""
}
```

---

## 6. Information Support - Inventory

### 6.1 General Description

The Support module manages three independent inventories:
1. Medication Inventory
2. Diagnostic Tests Inventory
3. Procedures Inventory

Only users with **"Support"** role have access to these modules.

### 6.2 Medication Inventory

#### 6.2.1 Create Medication

**Endpoint:** `POST /hospital/san-rafael/medicines`

**Authentication:** `@PreAuthorize("hasRole('SOPORTE')")`

```json
{
    "idMedicine": "MED001",
    "name": "Acetaminophen 500mg",
    "stock": 150,
    "price": 2500.00
}
```

**Validations:**
- Unique ID
- Name not empty
- Stock >= 0
- Price > 0

#### 6.2.2 CRUD Operations

```
GET    /hospital/san-rafael/medicines           - List all
GET    /hospital/san-rafael/medicines/{id}      - Search by ID
PUT    /hospital/san-rafael/medicines/{id}      - Update
DELETE /hospital/san-rafael/medicines/{id}      - Delete
```

### 6.3 Diagnostic Tests Inventory

#### 6.3.1 Create Test

**Endpoint:** `POST /hospital/san-rafael/diagnostic-tests`

```json
{
    "idExam": "EXAM001",
    "name": "Complete blood count",
    "cost": 50000.00
}
```

#### 6.3.2 CRUD Operations

```
GET    /hospital/san-rafael/diagnostic-tests           - List all
GET    /hospital/san-rafael/diagnostic-tests/{id}      - Search by ID
PUT    /hospital/san-rafael/diagnostic-tests/{id}      - Update
DELETE /hospital/san-rafael/diagnostic-tests/{id}      - Delete
```

### 6.4 Procedures Inventory

#### 6.4.1 Create Procedure

**Endpoint:** `POST /hospital/san-rafael/procedures`

```json
{
    "idProcedure": "PROC001",
    "name": "Wound care",
    "cost": 50000.00
}
```

#### 6.4.2 CRUD Operations

```
GET    /hospital/san-rafael/procedures           - List all
GET    /hospital/san-rafael/procedures/{id}      - Search by ID
PUT    /hospital/san-rafael/procedures/{id}      - Update
DELETE /hospital/san-rafael/procedures/{id}      - Delete
```

### 6.5 Security Restrictions

The "Support" role can **ONLY** manage inventories. No access to:
- Patients
- Employees
- Medical orders
- Medical histories
- Invoices

---

## 7. Billing System

### 7.1 General Description

The billing system automatically generates invoices based on medical orders, applying copay logic according to the patient's policy.

### 7.2 Roles with Access

- **Generate invoices:** Administrative Staff
- **Consult invoices:** Administrative Staff, Doctor

### 7.3 Generate Invoice

**Endpoint:** `POST /hospital/san-rafael/invoices/generate`

**Authentication:** `@PreAuthorize("hasRole('PERSONAL_ADMINISTRATIVO')")`

**Request:**
```json
{
    "patientId": "1020234567",
    "doctorName": "Tomas Bermudez",
    "orderNumbers": ["FIX001"]
}
```

### 7.4 Calculation Logic

#### 7.4.1 Patient WITH Active Policy

```
Total Amount = Sum of costs of all order items
Copay = min($50,000, Total Amount)
Insurance Coverage = Total Amount - Copay
```

**Example:**
- Order with total cost: $250,000
- Patient copay: $50,000
- Insurance coverage: $200,000

**Annual Validation:**
- Annual copay limit: $1,000,000
- System validates accumulated copay for the year doesn't exceed this limit

```java
public Map<String, Double> calculatePaymentAmounts(double totalAmount, Policy policy, String patientId, int year) {
    double copay = 0.0;
    double coverage = 0.0;

    if (policy != null && policy.isActive()) {
        // Get accumulated copays for the year
        double yearCopayTotal = invoicePort.getYearCopayTotal(patientId, year);

        // Calculate copay (maximum $50,000 per visit)
        copay = Math.min(50000.0, totalAmount);

        // Validate annual limit
        if (yearCopayTotal + copay > 1000000.0) {
            double availableCopay = 1000000.0 - yearCopayTotal;
            if (availableCopay <= 0) {
                // Already reached annual limit, insurance covers all
                copay = 0.0;
                coverage = totalAmount;
            } else {
                // Adjust copay to available remainder
                copay = Math.min(copay, availableCopay);
                coverage = totalAmount - copay;
            }
        } else {
            coverage = totalAmount - copay;
        }
    } else {
        // No policy or inactive policy: patient pays all
        copay = totalAmount;
        coverage = 0.0;
    }

    return Map.of("copay", copay, "coverage", coverage);
}
```

#### 7.4.2 Patient WITHOUT Policy

```
Copay = Total Amount
Insurance Coverage = $0
```

Patient pays 100% of the cost.

### 7.5 Information Included in Invoice

**Generation Response:**
```json
{
    "success": true,
    "message": "Invoice generated successfully",
    "data": {
        "invoiceId": "2",
        "issueDate": "2025-11-18",
        "patientId": "1020234567",
        "patientName": "Carlos Mendoza",
        "patientAge": 40,
        "doctorName": "Tomas Bermudez",
        "insuranceCompany": "Seguros Bolivar",
        "policyNumber": "POL123456",
        "policyActive": true,
        "policyExpirationDate": "2026-12-31",
        "policyDaysUntilExpiration": 408,
        "servicesIncluded": [
            "Medication: Acetaminophen - Dose: 500mg every 8 hours - $25,000.00"
        ],
        "totalAmount": 25000.0,
        "copayAmount": 25000.0,
        "insuranceCoverage": 0.0
    }
}
```

### 7.6 Service Details

The system generates detailed descriptions according to item type:

**Medications:**
```
"Medication: Ceftriaxone - Dose: 2g IV every 12 hours - $150,000.00"
```

**Procedures:**
```
"Procedure: Hospitalization for 5 days - $800,000.00"
```

**Tests:**
```
"Test: Brain MRI - $150,000.00"
```

### 7.7 Policy Validity Days Calculation

The system correctly calculates remaining days until policy expiration:

```java
// InvoiceRestController.java:176
long daysUntilExpiration = ChronoUnit.DAYS.between(LocalDate.now(), policy.getExpirationDate());
```

**Example:**
- Current date: 11/18/2025
- Expiration date: 12/31/2026
- Days until expiration: 408 days

### 7.8 Query Invoices

#### 7.8.1 By Patient

```
GET /hospital/san-rafael/invoices/patient/{patientId}
```

Returns all patient invoices.

#### 7.8.2 By Date Range

```
GET /hospital/san-rafael/invoices/patient/{patientId}/date-range?startDate=01-01-2024&endDate=31-12-2025
```

**Date Format:** `dd-MM-yyyy`

#### 7.8.3 By Invoice ID

```
GET /hospital/san-rafael/invoices/{invoiceId}
```

#### 7.8.4 Year Copay Total

```
GET /hospital/san-rafael/invoices/patient/{patientId}/copay-total/{year}
```

**Response:**
```json
{
    "success": true,
    "patientId": "1020234567",
    "year": 2025,
    "copayTotal": 275000.0,
    "remainingBeforeLimit": 725000.0
}
```

---

## 8. Medical Appointments System

### 8.1 General Description

The appointments system allows scheduling, querying, updating, and canceling medical appointments.

### 8.2 Roles with Access

- **Full CRUD:** Administrative Staff
- **Query:** Doctor, Nurse

### 8.3 Create Appointment

**Endpoint:** `POST /hospital/san-rafael/appointments`

**Authentication:** `@PreAuthorize("hasRole('PERSONAL_ADMINISTRATIVO')")`

```json
{
    "patientId": "1020234567",
    "doctorId": "101013974",
    "appointmentDate": "25/11/2025",
    "appointmentTime": "10:00",
    "reason": "General checkup",
    "status": "Scheduled",
    "notes": "First appointment",
    "createdBy": "laurpo037"
}
```

**Validations:**
- Patient must exist
- Doctor must exist
- Date cannot be before today
- Time in HH:mm format
- Valid status: "Scheduled", "Confirmed", "In_Progress", "Completed", "Cancelled", "No_Show"

### 8.4 Appointment States

| State | Description |
|-------|-------------|
| **Scheduled** | Appointment created, pending confirmation |
| **Confirmed** | Patient confirmed attendance |
| **In_Progress** | Patient is being attended |
| **Completed** | Appointment finished successfully |
| **Cancelled** | Appointment cancelled by patient or hospital |
| **No_Show** | Patient did not attend scheduled appointment |

### 8.5 Query Appointments

**All appointments:**
```
GET /hospital/san-rafael/appointments
```

**By ID:**
```
GET /hospital/san-rafael/appointments/{id}
```

**By patient:**
```
GET /hospital/san-rafael/appointments/patient/{patientId}
```

**By doctor:**
```
GET /hospital/san-rafael/appointments/doctor/{doctorId}
```

**By date:**
```
GET /hospital/san-rafael/appointments/date/{date}
```

**By date and doctor:**
```
GET /hospital/san-rafael/appointments/doctor/{doctorId}/date/{date}
```

**By date range:**
```
GET /hospital/san-rafael/appointments/date-range?startDate=01-11-2025&endDate=30-11-2025
```

### 8.6 Update Appointment

**Endpoint:** `PUT /hospital/san-rafael/appointments/{id}`

Allows updating:
- Date and time
- Reason
- Status
- Notes

### 8.7 Cancel Appointment

**Endpoint:** `PUT /hospital/san-rafael/appointments/{id}/cancel`

Changes status to "Cancelled".

### 8.8 Delete Appointment

**Endpoint:** `DELETE /hospital/san-rafael/appointments/{id}`

Permanently deletes the appointment from the system.

---

## Conclusion

This system integrates all necessary functionalities for clinic management, from secure authentication to automatic billing, including complete management of medical histories and medical orders.

**Key Features:**
- ✅ JWT authentication with 24-hour expiration
- ✅ Role-based access control (5 roles)
- ✅ Robust validations on all endpoints
- ✅ Automatic billing with copay logic
- ✅ Complete hospitalization support
- ✅ Complete traceability of administered medications
- ✅ Independent inventory management
- ✅ Appointment system with multiple states

**Security:**
- JWT tokens signed with secret key
- Token validation on each request
- Strict role-based restrictions
- Exhaustive input data validation
- Protection against SQL injection, XSS and other common attacks

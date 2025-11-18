# Construccion2CodeVerse

University project - Software Development 2 | Java + Spring Boot

## Team members

- [Luisa Mosquera](https://github.com/Luisam340)
- [Santiago Arboleda](https://github.com/SantiCadavid)
- [Marlon Castañeda](https://github.com/MarlonC102)

## Technology in which it was developed

- Java 17
- Spring Boot
- MySQL
- Maven

---

## Inventory Management System

System that allows you to record, update, and consult a restaurant's inventory, including ingredients, quantities,
expiration dates, and low stock alerts.

# How to run the project

### 1. Clone the repository:

```bash
   git clone https://github.com/Luisam340/Construccion2CodeVerse.git
```

### 2. Run Backend

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/hospital
spring.datasource.username=root
spring.datasource.password=
```

**Note:** If your MySQL uses a different password, update the `spring.datasource.password` field in the Spring Boot
project.

Option 1:
- Run from the Application class

Option 2:
```bash
   mvn spring-boot:run
```

## Endpoints

Base URL: `http://localhost:8080/hospital/san-rafael`

### Authentication

#### Login
- `POST /auth/login` - Authenticate user

**Request Body:**
```json
{
    "username": "tbermudez",
    "password": "lui123M*"
}
```

**Response:**
```json
{
    "success": true,
    "message": "Login exitoso",
    "data": {
        "token": "eyJhbGciOiJIUzI1NiJ9...",
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

### Employees

#### Create Employee
- `POST /employees` - Create employee

**Request Body:**
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

#### Query Employees
- `GET /employees` - Get all employees
- `GET /employees/{idNumber}` - Get employee by document number
- `GET /employees/search?userName={userName}` - Get employee by user name

#### Update Employee
- `PUT /employees/{idNumber}` - Update employee

**Request Body:**
```json
{
    "name": "Juan Pérez Actualizado",
    "idNumber": "1234567890",
    "email": "juan.perez.updated@hospital.com",
    "phoneNumber": "3001234567",
    "birthDate": "15/05/1990",
    "address": "Calle 456 Actualizada",
    "rol": "Doctor",
    "userName": "jperez",
    "password": "JPerez123*"
}
```

#### Delete Employee
- `DELETE /employees/{idNumber}` - Delete employee

### Patients

#### Create Patient
- `POST /patients` - Create patient with policy and emergency contact

**Request Body:**
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
        "kinship": "Hermana",
        "phoneNumber": "3156789012"
    }
}
```

#### Query Patients
- `GET /patients` - Get all patients
- `GET /patients/{idNumber}` - Get patient by document number
- `GET /patients/search?name={name}` - Search patients by name

#### Update Patient
- `PUT /patients/{idNumber}` - Update patient basic information

**Request Body:**
```json
{
    "name": "Carlos Mendoza Actualizado",
    "idNumber": "1020234567",
    "email": "carlos.updated@test.com",
    "phoneNumber": "3145678901",
    "birthDate": "15/03/1985",
    "address": "Calle 45 Actualizada",
    "gender": "M"
}
```

#### Update Emergency Contact
- `PUT /patients/{idNumber}/emergency-contact` - Update emergency contact

**Request Body:**
```json
{
    "name": "Maria Mendoza",
    "kinship": "Hermana",
    "phoneNumber": "3156789012"
}
```

#### Update Policy
- `PUT /patients/{idNumber}/policy` - Update insurance policy

**Request Body:**
```json
{
    "companyName": "Seguros Bolivar",
    "policyNumber": "POL123456",
    "active": true,
    "expirationDate": "31/12/2026"
}
```

#### Delete Patient
- `DELETE /patients/{idNumber}` - Delete patient

### Medicines

#### Create Medicine
- `POST /medicines` - Create medicine in inventory

**Request Body:**
```json
{
    "idMedicine": "MED001",
    "name": "Acetaminofen 500mg",
    "stock": 150,
    "price": 2500.00
}
```

#### Query Medicines
- `GET /medicines` - Get all medicines
- `GET /medicines/{idMedicine}` - Get medicine by id
- `GET /medicines/search?name={name}` - Search medicines by name

#### Update Medicine
- `PUT /medicines/{idMedicine}` - Update medicine

**Request Body:**
```json
{
    "name": "Acetaminofen 500mg",
    "stock": 200,
    "price": 2800.00
}
```

#### Delete Medicine
- `DELETE /medicines/{idMedicine}` - Delete medicine

### Procedures

#### Create Procedure
- `POST /procedures` - Create procedure in inventory

**Request Body:**
```json
{
    "idProcedure": "PROC001",
    "name": "Curacion de heridas",
    "cost": 50000.00
}
```

#### Query Procedures
- `GET /procedures` - Get all procedures
- `GET /procedures/{idProcedure}` - Get procedure by id
- `GET /procedures/search?name={name}` - Search procedures by name

#### Update Procedure
- `PUT /procedures/{idProcedure}` - Update procedure

**Request Body:**
```json
{
    "name": "Curacion avanzada de heridas",
    "cost": 75000.00
}
```

#### Delete Procedure
- `DELETE /procedures/{idProcedure}` - Delete procedure

### Diagnostic Tests

#### Create Diagnostic Test
- `POST /diagnostic-tests` - Create diagnostic test in inventory

**Request Body:**
```json
{
    "idExam": "EXAM001",
    "name": "Hemograma completo",
    "cost": 50000.00
}
```

#### Query Diagnostic Tests
- `GET /diagnostic-tests` - Get all diagnostic tests
- `GET /diagnostic-tests/{idExam}` - Get diagnostic test by id
- `GET /diagnostic-tests/search?name={name}` - Search diagnostic tests by name

#### Update Diagnostic Test
- `PUT /diagnostic-tests/{idExam}` - Update diagnostic test

**Request Body:**
```json
{
    "name": "Hemograma completo actualizado",
    "cost": 60000.00
}
```

#### Delete Diagnostic Test
- `DELETE /diagnostic-tests/{idExam}` - Delete diagnostic test

### Doctors

#### Create Visit Record
- `POST /doctors/visit-records` - Create medical visit record

**Request Body:**
```json
{
    "patientId": "1020234567",
    "doctorId": "101013974",
    "reason": "Consulta por dolor abdominal",
    "symptoms": "Dolor en cuadrante inferior derecho, fiebre",
    "diagnosis": "Apendicitis aguda"
}
```

#### Query Medical History
- `GET /doctors/medical-history/patient/{patientId}` - Get complete medical history
- `GET /doctors/medical-history/patient/{patientId}/date/{date}` - Get medical history by date (format: dd-MM-yyyy)
- `GET /doctors/medical-history/doctor/{doctorId}` - Get medical histories by doctor

#### Create Medical Orders
- `POST /doctors/orders` - Create medical order

**Request Body - Medication Order:**
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
            "medicineName": "Acetaminofen",
            "dose": "500mg cada 8 horas",
            "treatmentDuration": "3 dias"
        }
    ]
}
```

**Request Body - Hospitalization Order:**
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
            "procedureName": "Hospitalizacion por infeccion respiratoria aguda",
            "repetitions": 5,
            "frequency": "5 dias"
        },
        {
            "type": "PROCEDURE",
            "itemNumber": 2,
            "cost": 50000.0,
            "procedureName": "Control de signos vitales por enfermera",
            "repetitions": 15,
            "frequency": "Cada 8 horas durante hospitalizacion"
        },
        {
            "type": "MEDICINE",
            "itemNumber": 3,
            "cost": 150000.0,
            "medicineName": "Ceftriaxona",
            "dose": "2g IV cada 12 horas, diluir en 100ml de solucion salina",
            "treatmentDuration": "5 dias"
        }
    ]
}
```

#### Query Medical Orders
- `GET /doctors/orders` - Get all medical orders
- `GET /doctors/orders/{orderNumber}` - Get order by number
- `GET /doctors/orders/patient/{patientId}` - Get orders by patient
- `GET /doctors/orders/doctor/{doctorId}` - Get orders by doctor

### Nurses

#### Record Vital Signs
- `POST /nurses/vital-signs` - Record patient vital signs

**Request Body:**
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

#### Record Medication Administration
- `POST /nurses/medicine-administration` - Record medication administration

**Request Body:**
```json
{
    "visitRecordId": 1,
    "patientId": "1020234567",
    "orderNumber": "ORD001",
    "itemNumber": 1,
    "medicineName": "Ceftriaxona",
    "doseAdministered": "2g IV",
    "administeredBy": "1030789012",
    "observations": "Paciente tolero bien el medicamento"
}
```

#### Create Visit Record
- `POST /nurses/visit-records` - Create nursing visit record

**Request Body:**
```json
{
    "patientId": "1020234567",
    "doctorId": "101013974",
    "reason": "Control de enfermeria",
    "symptoms": "Dolor leve",
    "diagnosis": ""
}
```

#### Query Vital Signs
- `GET /nurses/vital-signs/patient/{patientId}` - Get vital signs by patient
- `GET /nurses/vital-signs/visit-record/{visitRecordId}` - Get vital signs by visit record

#### Query Medication Administration
- `GET /nurses/medicine-administration/patient/{patientId}` - Get administrations by patient
- `GET /nurses/medicine-administration/visit-record/{visitRecordId}` - Get administrations by visit record
- `GET /nurses/medicine-administration/order/{orderNumber}` - Get administrations by order

### Invoices

#### Generate Invoice
- `POST /invoices/generate` - Generate invoice from medical orders

**Request Body:**
```json
{
    "patientId": "1020234567",
    "doctorName": "Tomas Bermudez",
    "orderNumbers": ["FIX001"]
}
```

**Response:**
```json
{
    "success": true,
    "message": "Factura generada exitosamente",
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
            "Medicamento: Acetaminofen - Dosis: 500mg cada 8 horas - $25,000.00"
        ],
        "totalAmount": 25000.0,
        "copayAmount": 25000.0,
        "insuranceCoverage": 0.0
    }
}
```

#### Query Invoices
- `GET /invoices/patient/{patientId}` - Get all patient invoices
- `GET /invoices/patient/{patientId}/date-range?startDate={startDate}&endDate={endDate}` - Get invoices by date range (format: dd-MM-yyyy)
- `GET /invoices/{invoiceId}` - Get invoice by id
- `GET /invoices/patient/{patientId}/copay-total/{year}` - Get total copay for year

### Appointments

#### Create Appointment
- `POST /appointments` - Create medical appointment

**Request Body:**
```json
{
    "patientId": "1020234567",
    "doctorId": "101013974",
    "appointmentDate": "25/11/2025",
    "appointmentTime": "10:00",
    "reason": "Control general",
    "status": "Programada",
    "notes": "Primera cita",
    "createdBy": "laurpo037"
}
```

#### Query Appointments
- `GET /appointments` - Get all appointments
- `GET /appointments/{id}` - Get appointment by id
- `GET /appointments/patient/{patientId}` - Get appointments by patient
- `GET /appointments/doctor/{doctorId}` - Get appointments by doctor
- `GET /appointments/date/{date}` - Get appointments by date (format: dd-MM-yyyy)
- `GET /appointments/doctor/{doctorId}/date/{date}` - Get appointments by doctor and date
- `GET /appointments/date-range?startDate={startDate}&endDate={endDate}` - Get appointments by date range

#### Update Appointment
- `PUT /appointments/{id}` - Update appointment

**Request Body:**
```json
{
    "appointmentDate": "26/11/2025",
    "appointmentTime": "14:00",
    "reason": "Control general actualizado",
    "status": "Confirmada",
    "notes": "Paciente confirmó asistencia"
}
```

#### Cancel Appointment
- `PUT /appointments/{id}/cancel` - Cancel appointment (changes status to "Cancelada")

#### Delete Appointment
- `DELETE /appointments/{id}` - Delete appointment

---

## Testing

- Import the collection `Clinica San Rafael.postman_collection.json` and environment `Variables Clinica San Rafael.postman_environment.json`

## Documentation

- `SYSTEM_FUNCTIONAL_GUIDE.md` - Complete functional guide in English

## License

University Project - Software Development 2

-- Datos de prueba para employee
-- Recurso Humanos
INSERT INTO employee (address, birth_date, email, id_number, name, phone_number, password, role, user_name, status)
SELECT 'Avenida Siempre Viva', '2000-11-11', 'lucianabm@hospital.com', '101013975', 'Luciana Bermudez', '3123456788', 'luci123M*', 'Recurso Humanos', 'lbermudez', true
WHERE NOT EXISTS (
    SELECT 1 FROM employee WHERE email = 'lucianabm@hospital.com' OR user_name = 'lbermudez'
);

INSERT INTO employee (address, birth_date, email, id_number, name, phone_number, password, role, user_name, status)
SELECT 'Calle L 123', '1999-11-11', '1010@1010.com', '1010101010', 'Luisa Gómez', '1010101010', '10101Ml*luisa', 'Recursos Humanos', '101Mosquera', true
WHERE NOT EXISTS (
    SELECT 1 FROM employee WHERE email = '1010@1010.com' OR user_name = '101Mosquera'
);

INSERT INTO employee (address, birth_date, email, id_number, name, phone_number, password, role, user_name, status)
SELECT 'Calle 123', '1990-05-15', 'juan@example.com', '1234567890', 'Juan Pérez', '3001234567', 'jPerez*123', 'Recursos Humanos', 'jupe123', true
WHERE NOT EXISTS (
    SELECT 1 FROM employee WHERE email = 'juan@example.com' OR user_name = 'jupe123'
);



-- Doctor
INSERT INTO employee (address, birth_date, email, id_number, name, phone_number, password, role, user_name, status)
SELECT 'Calle 123', '2001-11-11', 'tomasbm@hospital.com', '101013974', 'Tomás Bermudez', '3123456789', 'lui123M*', 'Doctor', 'tbermudez', true
WHERE NOT EXISTS (
    SELECT 1 FROM employee WHERE email = 'tomasbm@hospital.com' OR user_name = 'tbermudez'
);

INSERT INTO employee (address, birth_date, email, id_number, name, phone_number, password, role, user_name, status)
SELECT 'Carrera 15 #45-30', '1988-07-12', 'maria.lopez@hospital.com', '1020456789', 'María López Torres', '3109876543', 'Maria456#', 'Doctor', 'mlopez02', true
WHERE NOT EXISTS (
    SELECT 1 FROM employee WHERE email = 'maria.lopez@hospital.com' OR user_name = 'mlopez02'
);

-- Soporte
INSERT INTO employee (address, birth_date, email, id_number, name, phone_number, password, role, user_name, status)
SELECT 'Calle 45 #23-67', '1985-03-15', 'carlos.mendoza@hospital.com', '1020234567', 'Carlos Mendoza', '3145678901', 'CarlosRH456!', 'Soporte', 'cmendoza', true
WHERE NOT EXISTS (
    SELECT 1 FROM employee WHERE email = 'carlos.mendoza@hospital.com' OR user_name = 'mlopez02'
);

-- Enfermera
INSERT INTO employee (address, birth_date, email, id_number, name, phone_number, password, role, user_name, status)
SELECT 'Avenida 68 #20-15', '1992-11-08', 'ana.martinez@hospital.com', '1030789012', 'Ana Martínez Silva', '3156789012', 'Ana789$', 'Enfermera', 'amartinez', true
WHERE NOT EXISTS (
    SELECT 1 FROM employee WHERE email = 'ana.martinez@hospital.com' OR user_name = 'amartinez'
);

INSERT INTO employee (address, birth_date, email, id_number, name, phone_number, password, role, user_name, status)
SELECT 'Calle 80 #12-34', '1995-02-25', 'laura.gomez@hospital.com', '1040123456', 'Laura Gómez Pérez', '3187654321', 'Laura321@', 'Enfermera', 'lgomez03', true
WHERE NOT EXISTS (
    SELECT 1 FROM employee WHERE email = 'laura.gomez@hospital.com' OR user_name = 'lgomez03'
);

INSERT INTO employee (address, birth_date, email, id_number, name, phone_number, password, role, user_name, status)
SELECT 'Carrera 45 #23-11', '1995-03-15', 'mgarcia@hospital.com', '1020304050', 'María García', '3209876543', 'maria123M*', 'Enfermera', 'mgarcia', true
WHERE NOT EXISTS (
    SELECT 1 FROM employee WHERE email = 'mgarcia@hospital.com' OR user_name = 'mgarcia'
);


-- Personal Administrativo
INSERT INTO employee (address, birth_date, email, id_number, name, phone_number, password, role, user_name, status)
SELECT 'Calle 80 #12-34', '1995-02-25', 'laura.r@hospital.com', '1041123456', 'Laura Restrepo', '3187654322', 'Laura321@', 'Personal Administrativo', 'laurpo037', true
WHERE NOT EXISTS (
    SELECT 1 FROM employee WHERE email = 'laura.r@hospital.com' OR user_name = 'laurpo037'
);

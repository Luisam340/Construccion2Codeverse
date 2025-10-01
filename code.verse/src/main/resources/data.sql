INSERT INTO employee (address, birth_date, email, id_number, name, phone_number, password, role, user_name, status)
SELECT 'Avenida Siempre Viva', '2000-11-11', 'lucianabm@hospital.com', '101013975', 'Luciana Bermudez', '3123456788', 'luci123M*', 'Recursos Humanos', 'lbermudez1', true
WHERE NOT EXISTS (
    SELECT 1 FROM employee WHERE email = 'lucianabm@hospital.com' OR user_name = 'lbermudez1'
);
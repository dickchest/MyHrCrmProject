INSERT INTO contact_details (email, home_phone, mobile_phone)
VALUES
    ('testemail1@example.com', '+111111111', '+11111112'),
    ('testemail2@example.com', '+111111121', '+11111122'),
    ('testemail3@example.com', '+111111131', '+11111132'),
    ('john.doe1@example.com', '123-456-7890', '1234'),
    ('john.doe2@example.com', '123-456-7891', '1235'),
    ('john.doe3@example.com', '123-456-7892', '1236');

INSERT INTO address_details (street, city, country, state, zip)
VALUES
    ('Example str. 1', 'city1', 'country1', 'state1', '45001'),
    ('Example str. 2', 'city2', 'country2', 'state2', '45002'),
    ('Example str. 3', 'city3', 'country3', 'state3', '45003');

INSERT INTO employee (first_name, last_name, position, contact_details_id)
VALUES
    ('TestName1', 'TestLastname1', 'position1', 2),
    ('TestName2', 'TestLastname2', 'position1', 3),
    ('TestName3', 'TestLastname3', 'position1', 4);

INSERT INTO user_details (user_name, password, employee_id, role_id, created_date, updated_date)
VALUES
    ('TestUser1', '$2a$10$.atBXCEDmYVAHoiL8qHiSOrZjfrXtdS03.P.DmAR8Ae1bXph1nUAy', 2, 1, current_timestamp, current_timestamp),
    ('TestUser2', '$2a$10$.atBXCEDmYVAHoiL8qHiSOrZjfrXtdS03.P.DmAR8Ae1bXph1nUAy', 3, 2, current_timestamp, current_timestamp),
    ('TestUser3', '$2a$10$.atBXCEDmYVAHoiL8qHiSOrZjfrXtdS03.P.DmAR8Ae1bXph1nUAy', 4, 3, current_timestamp, current_timestamp);

INSERT INTO vacancy (job_title, description, salary, start_date, end_date, status, responsible_employee_id)
VALUES
    ('Software Engineer', 'Java Developer Position', 80000.00, '2023-10-25', '2023-12-31', 'OPEN', 1),
    ('Data Scientist', 'Machine Learning Specialist', 95000.00, '2023-11-15', '2024-01-30', 'OPEN', 2),
    ('Project Manager', 'IT Project Manager Position', 75000.00, '2023-12-01', '2024-02-28', 'OPEN', 3);

INSERT INTO candidate (first_name, last_name, date_of_birth, address_id, contact_details_id, vacancy_id, status, create_date, update_date)
VALUES
    ('John', 'Doe', '1990-05-15', 1, 5, 1, 'ACTIVE', '2023-10-26 10:00:00.000000', '2023-10-26 10:00:00.000000'),
    ('Jack', 'Dew', '1980-04-16', 2, 6, 2, 'ACTIVE', '2023-10-30 14:01:30.000000', '2023-10-30 14:01:30.000000'),
    ('Peter', 'Song', '1985-01-11', 3, 7, 2, 'ACTIVE', '2023-11-06 10:00:00.000000', '2023-12-16 10:00:00.000000');

INSERT INTO interview (date, time, location, comments, candidate_id, employee_id, status, create_date, update_date)
VALUES
    ('2023-10-28', '14:00:00', 'room 1', 'Scheduled interview for John Doe', 1, 2, 'SCHEDULED', '2023-10-26 10:30:00', '2023-10-26 10:30:00'),
    ('2023-10-29', '15:30:00', 'room 2', 'Scheduled interview for Jack Dew', 2, 3, 'SCHEDULED', '2023-10-26 11:00:00', '2023-10-26 11:00:00'),
    ('2023-10-30', '16:45:00', 'room 3', 'Scheduled interview for Peter Song', 3, 2, 'SCHEDULED', '2023-10-26 11:30:00', '2023-10-26 11:30:00');

INSERT INTO Client (company_name, description)
VALUES
    ('Company 1', 'Description 1'),
    ('Company 2', 'Description 2'),
    ('Company 3', 'Description 3');

INSERT INTO communication (communication_date_time, communication_type, client_id, candidate_id, vacancy_id, responsible_employee_id, create_date, update_date)
VALUES
    ('2023-10-26 10:00:00', 'PHONE', 1, 1, 1, 1, '2023-10-26 10:00:00', '2023-10-26 10:00:00'),
    ('2023-10-26 11:30:00', 'EMAIL', 2, 2, 2, 2, '2023-10-26 11:30:00', '2023-10-26 11:30:00'),
    ('2023-10-26 15:15:00', 'CHAT', 3, 3, 3, 3, '2023-10-26 15:15:00', '2023-10-26 15:15:00');

INSERT INTO contract (start_date, end_date, salary, contract_type, candidate_id, responsible_employee_id, client_id, create_date, update_date)
VALUES
    ('2023-10-26', '2023-12-31', 50000.00, 'FULL_TIME', 1, 1, 1, '2023-10-26 10:00:00', '2023-10-26 10:00:00'),
    ('2023-11-15', '2024-01-15', 60000.00, 'FULL_TIME', 2, 2, 2, '2023-11-15 09:30:00', '2023-11-15 09:30:00'),
    ('2023-10-30', '2023-12-30', 55000.00, 'PART_TIME', 3, 3, 3, '2023-10-30 14:45:00', '2023-10-30 14:45:00');

INSERT INTO compensation (candidate_id, salary, payment_date, comments, contract_id, create_date, update_date)
VALUES
    (1, 5000.00, '2023-10-28', 'October salary', 1, '2023-10-28 14:30:00', '2023-10-28 14:30:00'),
    (2, 6000.00, '2023-11-15', 'November salary', 2, '2023-11-15 16:00:00', '2023-11-15 16:00:00'),
    (3, 5500.00, '2023-10-30', 'October salary', 3, '2023-10-30 10:15:00', '2023-10-30 10:15:00');

INSERT INTO task (title, description, start_date, end_date, status, responsible_employee_id, candidate_id, vacancy_id, create_date, update_date)
VALUES
    ('Task 1', 'Complete project report', '2023-10-28', '2023-11-10', 'IN_PROCESS', 1, 1, 1, '2023-10-28 10:00:00', '2023-10-28 10:00:00'),
    ('Task 2', 'Schedule interviews', '2023-11-01', '2023-11-05', 'OPENED', 2, 2, 2, '2023-11-01 12:30:00', '2023-11-01 12:30:00'),
    ('Task 3', 'Prepare presentation', '2023-10-30', '2023-11-15', 'DONE', 3, 3, 3, '2023-10-30 09:45:00', '2023-10-30 09:45:00');

CREATE DATABASE employer_api;
\connect employer_api

CREATE schema employer;


CREATE TABLE IF NOT EXISTS employer.departments (
  department_id                        BIGSERIAL,
  department_name                     VARCHAR(100) NOT NULL,
  description                     VARCHAR(500) NOT NULL,
  created_by VARCHAR(32) NOT NULL, 
  create_date TIMESTAMP(6) NOT NULL, 
  updated_by VARCHAR(32) NOT NULL, 
  last_update_date TIMESTAMP(6) NOT NULL, 
  CONSTRAINT pk_departments PRIMARY KEY (department_id));

CREATE TABLE IF NOT EXISTS employer.employees (
  employee_id                        BIGSERIAL, 
  department_id                    BIGINT NOT NULL,
  first_name                       VARCHAR(100) NOT NULL,
  last_name                      VARCHAR(100) NOT NULL,
  gender                          CHAR(1) NOT NULL,
  date_of_birth                   DATE,
  created_by VARCHAR(32) NOT NULL, 
  create_date TIMESTAMP(6) NOT NULL, 
  updated_by VARCHAR(32) NOT NULL, 
  last_update_date TIMESTAMP(6) NOT NULL, 
  CONSTRAINT pk_employees PRIMARY KEY (employee_id),
   CONSTRAINT fk_employees_di 
	     FOREIGN KEY (department_id) 
	     REFERENCES employer.departments(department_id));


INSERT INTO employer.departments (department_id, department_name, description, created_by, create_date, updated_by, last_update_date) 
VALUES (1, 'Human Resource', 'Human resource department', 'SEEDED_DURING_BUILD', CURRENT_TIMESTAMP, 'SEEDED_DURING_BUILD', CURRENT_TIMESTAMP);
INSERT INTO employer.departments (department_id, department_name, description, created_by, create_date, updated_by, last_update_date) 
VALUES (2, 'Information Technology', 'Information technology department', 'SEEDED_DURING_BUILD', CURRENT_TIMESTAMP, 'SEEDED_DURING_BUILD', CURRENT_TIMESTAMP);
INSERT INTO employer.departments (department_id, department_name, description, created_by, create_date, updated_by, last_update_date) 
VALUES (3, 'Finance', 'Finance department', 'SEEDED_DURING_BUILD', CURRENT_TIMESTAMP, 'SEEDED_DURING_BUILD', CURRENT_TIMESTAMP);


INSERT INTO employer.employees (employee_id, department_id, first_name, last_name, gender, date_of_birth, created_by, create_date, updated_by, last_update_date) VALUES (1, 1, 'Alyson', 'Kelly', 'F', '1987-03-05', 'SEEDED_DURING_BUILD', CURRENT_TIMESTAMP, 'SEEDED_DURING_BUILD', CURRENT_TIMESTAMP);
INSERT INTO employer.employees (employee_id, department_id, first_name, last_name, gender, date_of_birth, created_by, create_date, updated_by, last_update_date) VALUES (2, 1, 'Mike', 'Phillip', 'M', '1982-11-25', 'SEEDED_DURING_BUILD', CURRENT_TIMESTAMP, 'SEEDED_DURING_BUILD', CURRENT_TIMESTAMP);
INSERT INTO employer.employees (employee_id, department_id, first_name, last_name, gender, date_of_birth, created_by, create_date, updated_by, last_update_date) VALUES (3, 2, 'Rob', 'Curtin', 'M', '1970-08-15', 'SEEDED_DURING_BUILD', CURRENT_TIMESTAMP, 'SEEDED_DURING_BUILD', CURRENT_TIMESTAMP);
INSERT INTO employer.employees (employee_id, department_id, first_name, last_name, gender, date_of_birth, created_by, create_date, updated_by, last_update_date) VALUES (4, 3, 'Mike', 'Parrish', 'M', '1980-05-18', 'SEEDED_DURING_BUILD', CURRENT_TIMESTAMP, 'SEEDED_DURING_BUILD', CURRENT_TIMESTAMP);
INSERT INTO employer.employees (employee_id, department_id, first_name, last_name, gender, date_of_birth, created_by, create_date, updated_by, last_update_date) VALUES (5, 3, 'Shaelyn', 'Ellis', 'F', '1990-07-06', 'SEEDED_DURING_BUILD', CURRENT_TIMESTAMP, 'SEEDED_DURING_BUILD', CURRENT_TIMESTAMP);
INSERT INTO employer.employees (employee_id, department_id, first_name, last_name, gender, date_of_birth, created_by, create_date, updated_by, last_update_date) VALUES (6, 3, 'Darrel', 'Bush', 'F', '1990-07-25', 'SEEDED_DURING_BUILD', CURRENT_TIMESTAMP, 'SEEDED_DURING_BUILD', CURRENT_TIMESTAMP);

SELECT setval('employer.employees_employee_id_seq', COALESCE((SELECT MAX(employee_id)+1 FROM employer.employees), 1), false);

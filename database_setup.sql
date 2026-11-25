-- =====================================================================
-- PayTrack – Employee Payroll and Salary Management System
-- Database Creation Script
-- Database Name: paytrack_db
-- =====================================================================

-- Step 1: Create Database
CREATE DATABASE IF NOT EXISTS paytrack_db;
USE paytrack_db;

-- Step 2: Drop existing tables if needed (in reverse foreign key order)
DROP TABLE IF EXISTS salary;
DROP TABLE IF EXISTS attendance;
DROP TABLE IF EXISTS employees;
DROP TABLE IF EXISTS departments;

-- ---------------------------------------------------------------------
-- Table 1: departments
-- ---------------------------------------------------------------------
CREATE TABLE departments (
    dept_id INT AUTO_INCREMENT PRIMARY KEY,
    dept_name VARCHAR(50) NOT NULL UNIQUE
);

-- ---------------------------------------------------------------------
-- Table 2: employees
-- ---------------------------------------------------------------------
CREATE TABLE employees (
    emp_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    gender VARCHAR(10) NOT NULL,
    department VARCHAR(50) NOT NULL,
    phone VARCHAR(15) NOT NULL,
    basic_salary DOUBLE NOT NULL
);

-- ---------------------------------------------------------------------
-- Table 3: attendance
-- ---------------------------------------------------------------------
CREATE TABLE attendance (
    att_id INT AUTO_INCREMENT PRIMARY KEY,
    emp_id INT NOT NULL,
    att_date VARCHAR(20) NOT NULL,
    status VARCHAR(10) NOT NULL, -- 'Present' or 'Absent'
    CONSTRAINT fk_att_employee FOREIGN KEY (emp_id) 
        REFERENCES employees(emp_id) ON DELETE CASCADE
);

-- ---------------------------------------------------------------------
-- Table 4: salary
-- ---------------------------------------------------------------------
CREATE TABLE salary (
    sal_id INT AUTO_INCREMENT PRIMARY KEY,
    emp_id INT NOT NULL,
    basic_salary DOUBLE NOT NULL,
    allowances DOUBLE DEFAULT 0.0,
    deductions DOUBLE DEFAULT 0.0,
    gross_salary DOUBLE NOT NULL,
    net_salary DOUBLE NOT NULL,
    total_working_days INT DEFAULT 30,
    present_days INT DEFAULT 0,
    daily_salary DOUBLE DEFAULT 0.0,
    CONSTRAINT fk_sal_employee FOREIGN KEY (emp_id) 
        REFERENCES employees(emp_id) ON DELETE CASCADE
);

-- ---------------------------------------------------------------------
-- Sample Data Insertion for Testing and Viva Demo
-- ---------------------------------------------------------------------

-- Insert initial departments
INSERT INTO departments (dept_name) VALUES 
('Information Technology'),
('Human Resources'),
('Finance'),
('Marketing');

-- Insert initial employees
INSERT INTO employees (name, age, gender, department, phone, basic_salary) VALUES 
('Rahul Sharma', 28, 'Male', 'Information Technology', '9876543210', 45000.00),
('Priya Patel', 26, 'Female', 'Human Resources', '9876543211', 38000.00),
('Amit Verma', 32, 'Male', 'Finance', '9876543212', 52000.00),
('Sneha Rao', 25, 'Female', 'Marketing', '9876543213', 35000.00);

-- Insert initial attendance records
INSERT INTO attendance (emp_id, att_date, status) VALUES 
(1, '2026-11-01', 'Present'),
(1, '2026-11-02', 'Present'),
(1, '2026-11-03', 'Absent'),
(1, '2026-11-04', 'Present'),
(2, '2026-11-01', 'Present'),
(2, '2026-11-02', 'Present'),
(3, '2026-11-01', 'Present');

-- Insert initial salary calculation records
INSERT INTO salary (emp_id, basic_salary, allowances, deductions, gross_salary, net_salary) VALUES 
(1, 45000.00, 5000.00, 2500.00, 50000.00, 47500.00),
(2, 38000.00, 4000.00, 2000.00, 42000.00, 40000.00);

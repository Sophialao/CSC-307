DROP DATABASE IF EXISTS payroll;
CREATE DATABASE payroll;
USE payroll;


CREATE TABLE Employee(
	id CHAR(36) PRIMARY KEY,
	name VARCHAR(50),
    address VARCHAR(50),
    ssn INT(20),
    gender CHAR(1),
    type ENUM('Salary', 'Hourly'),
	sickDays INT DEFAULT 0, 
    rate DOUBLE(8, 2) DEFAULT 0,
    commission DOUBLE(8, 2) DEFAULT NULL,
    sales DOUBLE(8, 2) DEFAULT NULL
);

CREATE TABLE Loan(
	id CHAR(36) PRIMARY KEY,
    amount DOUBLE(10, 2),
    interestRate DOUBLE(2, 2),
    duration INT(2),
    employeeId CHAR(36),
    CONSTRAINT FKLoan_employeeId FOREIGN KEY (employeeId)
		REFERENCES Employee (id) 
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Payment(
	id CHAR(36) PRIMARY KEY,
    employeeId CHAR(36),
    amount DOUBLE(8, 2),
    date DATETIME,
	CONSTRAINT FKPayment_employeeId FOREIGN KEY (employeeId)
		REFERENCES Employee (id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Timecard(
	id CHAR(36) PRIMARY KEY,
    timeIn DATETIME,
    timeOut DATETIME, 
    employeeId CHAR(36),
	CONSTRAINT FKTimecard_employeeId FOREIGN KEY (employeeId)
		REFERENCES Employee (id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

 
    
CREATE TABLE IF NOT EXISTS Employee (
	id CHAR(36) PRIMARY KEY,
	name VARCHAR(50),
    address VARCHAR(50),
    ssn INT(20),
    gender CHAR(1),
    type VARCHAR(50),
	sickDays INT DEFAULT 0,
    rate DOUBLE(8, 2) DEFAULT 0,
    commission DOUBLE(8, 2) DEFAULT NULL,
    sales DOUBLE(8, 2) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS Loan(
	id CHAR(36) PRIMARY KEY,
    amount DOUBLE(10, 2),
    interestRate DOUBLE(2, 2),
    duration INT(2),
    employeeId CHAR(36),
    CONSTRAINT FKLoan_employeeId FOREIGN KEY (employeeId)
		REFERENCES Employee (id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS Payment(
	id CHAR(36) PRIMARY KEY,
    employeeId CHAR(36),
    amount DOUBLE(8, 2),
    date DATETIME,
	CONSTRAINT FKPayment_employeeId FOREIGN KEY (employeeId)
		REFERENCES Employee (id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS Timecard(
	id CHAR(36) PRIMARY KEY,
    timeIn DATETIME,
    timeOut DATETIME,
    employeeId CHAR(36),
	CONSTRAINT FKTimecard_employeeId FOREIGN KEY (employeeId)
		REFERENCES Employee (id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS Login(
	id VARCHAR(30) PRIMARY KEY,
    password VARCHAR(30)
);



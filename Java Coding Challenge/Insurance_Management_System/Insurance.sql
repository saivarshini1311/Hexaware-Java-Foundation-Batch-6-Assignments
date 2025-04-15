CREATE DATABASE InsuranceDB;
USE InsuranceDB;

-- Table for User
CREATE TABLE User (
    userId INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    role VARCHAR(20) NOT NULL
);

-- Insert data into User table
INSERT INTO User (username, password, role) VALUES
('john_doe', 'password123', 'admin'),
('jane_doe', 'password456', 'client');

SELECT * FROM User;

-- Table for Client
CREATE TABLE Client (
    clientId INT AUTO_INCREMENT PRIMARY KEY,
    clientName VARCHAR(100) NOT NULL,
    contactInfo VARCHAR(100) NOT NULL,
    policyId INT,
    FOREIGN KEY (policyId) REFERENCES Policy(policyId)
);

-- Insert data into Client table
INSERT INTO Client (clientName, contactInfo, policyId) VALUES
('John Smith', 'john@example.com', 1),
('Jane Smith', 'jane@example.com', 2);

SELECT * FROM Client;

-- Table for Policy
CREATE TABLE Policy (
    policyId INT AUTO_INCREMENT PRIMARY KEY,
    policyName VARCHAR(100) NOT NULL,
    coverageAmount DECIMAL(10, 2) NOT NULL,
    premium DECIMAL(10, 2) NOT NULL
);

-- Insert data into Policy table
INSERT INTO Policy (policyName, coverageAmount, premium) VALUES
('Health Insurance', 500000, 12000),
('Vehicle Insurance', 200000, 8000);

SELECT * FROM Policy;

-- Table for Claim
CREATE TABLE Claim (
    claimId INT AUTO_INCREMENT PRIMARY KEY,
    claimNumber VARCHAR(50) NOT NULL,
    dateFiled DATE NOT NULL,
    claimAmount DECIMAL(10, 2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    policyId INT,
    clientId INT,
    FOREIGN KEY (policyId) REFERENCES Policy(policyId),
    FOREIGN KEY (clientId) REFERENCES Client(clientId)
);

-- Insert data into Claim table
INSERT INTO Claim (claimNumber, dateFiled, claimAmount, status, policyId, clientId) VALUES
('CLM123456', '2024-10-01', 15000, 'Pending', 1, 1),
('CLM987654', '2024-10-05', 5000, 'Approved', 2, 2);

SELECT * FROM Claim;

-- Table for Payment
CREATE TABLE Payment (
    paymentId INT AUTO_INCREMENT PRIMARY KEY,
    paymentDate DATE NOT NULL,
    paymentAmount DECIMAL(10, 2) NOT NULL,
    clientId INT,
    FOREIGN KEY (clientId) REFERENCES Client(clientId)
);

-- Insert data into Payment table
INSERT INTO Payment (paymentDate, paymentAmount, clientId) VALUES
('2024-10-10', 12000, 1),
('2024-10-12', 8000, 2);

SELECT * FROM Payment;










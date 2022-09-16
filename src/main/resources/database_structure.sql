CREATE DATABASE IF NOT EXISTS JAVA_house_management_system;

USE JAVA_house_management_system;

CREATE TABLE IF NOT EXISTS apartments(
apartmentID int not null auto_increment,
sqm double not null,
floorNO int not null,
roomCount int not null,
PRIMARY KEY(apartmentID)
);

CREATE TABLE IF NOT EXISTS users(
userID int not null auto_increment,
apartmentID int,
userType varchar(10) not null,
firstName varchar(50) not null,
lastName varchar(50) not null,
password varchar(50) not null,
email varchar(50) not null,
phoneNumber int not null,
PRIMARY KEY(userID),
FOREIGN KEY(apartmentID) REFERENCES apartments(apartmentID)
);

CREATE TABLE IF NOT EXISTS waterMeasurements(
measurementID int not null AUTO_INCREMENT,
coldWaterMeasurementCurrent DOUBLE not null,
coldWaterConsumption DOUBLE not null,
hotWaterMeasurementCurrent DOUBLE not null,
hotWaterConsumption DOUBLE not null,
submitAt TIMESTAMP default current_timestamp,
userID int,
apartmentNo int,
PRIMARY KEY(measurementID),
FOREIGN KEY(userID) REFERENCES users(userID),
FOREIGN KEY(apartmentNo) REFERENCES apartments(apartmentID)
);

CREATE TABLE IF NOT EXISTS voting(
votingID int not null AUTO_INCREMENT,
votingTitle VARCHAR (255) not null,
votingAnswer VARCHAR (100),
votingStatus VARCHAR (100),
votingAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
userID int,
apartmentNo int,
PRIMARY KEY(votingID),
FOREIGN KEY(userID) REFERENCES users(userID),
FOREIGN KEY(apartmentNo) REFERENCES apartments (apartmentID)
);

CREATE TABLE IF NOT EXISTS messages(
messageID int not null AUTO_INCREMENT,
messageTitle VARCHAR (500),
messageStatus VARCHAR (100),
messageComment VARCHAR (700),
commentOnMessageID int,
createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
userID int,
apartmentNo int,
PRIMARY KEY(messageID),
FOREIGN KEY(userID) REFERENCES users(userID),
FOREIGN KEY(apartmentNo) REFERENCES apartments (apartmentID)
);

CREATE TABLE IF NOT EXISTS invoices(
invoiceID int not null AUTO_INCREMENT,
invoiceNo VARCHAR (100),
invoiceTitle VARCHAR (255),
invoiceCompany VARCHAR (255),
invoiceIssueDate VARCHAR (100),
invoiceDescription VARCHAR (500),
invoiceSubTotal DOUBLE,
invoiceTax DOUBLE,
invoiceTotalAmount DOUBLE,
invoiceStatus VARCHAR (100),
invoicePaidOn VARCHAR (100),
PRIMARY KEY(invoiceID)
);

INSERT INTO users (userType, firstName, lastName, password, email, phoneNumber) VALUES ("MANAGER","John", "Smith", "john", "john@gmail.com", 555);

INSERT INTO apartments (apartmentNo, sqm, floorNo, roomCount) VALUES (1, 29.00, 1, 2);
INSERT INTO apartments (apartmentNo, sqm, floorNo, roomCount) VALUES (1, 29.00, 1, 2);
INSERT INTO apartments (apartmentNo, sqm, floorNo, roomCount) VALUES (2, 29.00, 1, 2);
INSERT INTO apartments (apartmentNo, sqm, floorNo, roomCount) VALUES (3, 29.00, 1, 2);
INSERT INTO apartments (apartmentNo, sqm, floorNo, roomCount) VALUES (4, 29.00, 2, 2);
INSERT INTO apartments (apartmentNo, sqm, floorNo, roomCount) VALUES (5, 29.00, 2, 2);
INSERT INTO apartments (apartmentNo, sqm, floorNo, roomCount) VALUES (6, 29.00, 2, 2);
INSERT INTO apartments (apartmentNo, sqm, floorNo, roomCount) VALUES (7, 29.00, 3, 2);
INSERT INTO apartments (apartmentNo, sqm, floorNo, roomCount) VALUES (8, 29.00, 3, 2);
INSERT INTO apartments (apartmentNo, sqm, floorNo, roomCount) VALUES (9, 29.00, 3, 2);
INSERT INTO apartments (apartmentNo, sqm, floorNo, roomCount) VALUES (10, 29.00, 4, 2);
INSERT INTO apartments (apartmentNo, sqm, floorNo, roomCount) VALUES (11, 29.00, 4, 2);
INSERT INTO apartments (apartmentNo, sqm, floorNo, roomCount) VALUES (12, 29.00, 4, 2);
INSERT INTO apartments (apartmentNo, sqm, floorNo, roomCount) VALUES (13, 29.00, 5, 2);
INSERT INTO apartments (apartmentNo, sqm, floorNo, roomCount) VALUES (14, 29.00, 5, 2);
INSERT INTO apartments (apartmentNo, sqm, floorNo, roomCount) VALUES (15, 29.00, 5, 2);

INSERT INTO users (apartmentNo, userType, firstName, lastName, password, email, phoneNumber) VALUES (1, "OWNER","Peter", "White", "peter", "peter@gmail.com", 444);

SELECT * FROM users;
SELECT * FROM apartments;
SELECT * FROM waterMeasurements;
SELECT * FROM voting;
SELECT * FROM messages;
SELECT * FROM invoices;
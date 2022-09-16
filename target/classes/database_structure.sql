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
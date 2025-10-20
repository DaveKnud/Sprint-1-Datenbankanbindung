DROP TABLE IF EXISTS Customer;
DROP TABLE IF EXISTS Gender;


-- Table 1: Gender
CREATE TABLE Gender (
    GenderID INT PRIMARY KEY AUTO_INCREMENT,
    Description VARCHAR(50) NOT NULL UNIQUE
);

-- Table 2: Customer
CREATE TABLE Customer (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    FirstName VARCHAR(100) NOT NULL,
    LastName VARCHAR(100) NOT NULL,
    Gender ENUM ,
    BirthDate DATE,
    FOREIGN KEY (GenderID) REFERENCES Gender(GenderID)
);

INSERT INTO Gender (Description) VALUES
('M'),
('F'),
('U'),
('W');
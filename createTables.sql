DROP TABLE IF EXISTS Reservations; 
DROP TABLE IF EXISTS Transactions; 
DROP TABLE IF EXISTS Ownerships; 
DROP TABLE IF EXISTS CreditCards; 
DROP TABLE IF EXISTS Flights; 
DROP TABLE IF EXISTS Users; 

CREATE TABLE Users ( 
   id INTEGER NOT NULL AUTO_INCREMENT, 
   firstName VARCHAR(50) NOT NULL, 
   lastName VARCHAR(50) NOT NULL, 
   address VARCHAR(100) NOT NULL, 
   PRIMARY KEY (id)
); 

CREATE TABLE Flights ( 
   id INTEGER NOT NULL AUTO_INCREMENT, 
   departureTime DATE NOT NULL, 
   arrivalTime DATE NOT NULL, 
   firstCount SMALLINT NOT NULL, 
   busCount SMALLINT NOT NULL, 
   econCount SMALLINT NOT NULL, 
   windowCount SMALLINT NOT NULL, 
   aisleCount SMALLINT NOT NULL, 
   totalSeats SMALLINT NOT NULL, 
   origin VARCHAR(3) NOT NULL, 
   dest VARCHAR(3) NOT NULL, 
   PRIMARY KEY (id), 
   CONSTRAINT checkSeats CHECK (totalSeats = firstCount + busCount + econCount AND totalSeats >= windowCount + aisleCount)
);

CREATE TABLE CreditCards ( 
   id INTEGER NOT NULL AUTO_INCREMENT, 
   number BIGINT NOT NULL, 
   balance INTEGER NOT NULL, 
   creditLimit INTEGER NOT NULL, 
   PRIMARY KEY (id), 
   CONSTRAINT checkPositive CHECK (balance >= 0 and creditLimit >= 0), 
   CONSTRAINT checkUnder CHECK (balance <= creditLimit)
); 

CREATE TABLE Ownerships ( 
   id INTEGER NOT NULL AUTO_INCREMENT, 
   userID INTEGER NOT NULL, 
   cardID INTEGER NOT NULL, 
   PRIMARY KEY (id), 
   FOREIGN KEY (userID) REFERENCES Users(id), 
   FOREIGN KEY (cardID) REFERENCES CreditCards(id)
); 

CREATE TABLE Transactions (
   id INTEGER NOT NULL AUTO_INCREMENT, 
   ownershipID INTEGER NOT NULL, 
   amount INTEGER NOT NULL, 
   time DATE NOT NULL, 
   PRIMARY KEY (id), 
   FOREIGN KEY (ownershipID) REFERENCES Ownerships(id), 
   CONSTRAINT checkAmopuntPositive CHECK (amount >= 0)
); 

CREATE TABLE Reservations ( 
   transactionID INTEGER NOT NULL AUTO_INCREMENT, 
   flightID INTEGER NOT NULL,
   classType ENUM('First', 'Business', 'Economy') NOT NULL,
   seatType ENUM('Window', 'Aisle', 'Middle') NOT NULL,
   active BOOLEAN NOT NULL, 
   PRIMARY KEY (transactionID, flightID), 
   FOREIGN KEY (transactionID) REFERENCES Transactions(id), 
   FOREIGN KEY (flightID) REFERENCES Flights(id)
); 



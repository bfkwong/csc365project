DROP TABLE IF EXISTS Reservations;
DROP TABLE IF EXISTS Transactions;
DROP TABLE IF EXISTS Ownerships;
DROP TABLE IF EXISTS CreditCards;
DROP TABLE IF EXISTS Seats;
DROP TABLE IF EXISTS Flights;
DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Airlines;
DROP TABLE IF EXISTS Airports;

CREATE TABLE Users (
   id INTEGER NOT NULL AUTO_INCREMENT,
   firstName VARCHAR(50) NOT NULL,
   lastName VARCHAR(50) NOT NULL,
   PRIMARY KEY (id)
);

CREATE TABLE Airlines (
  id INTEGER NOT NULL AUTO_INCREMENT,
  airline VARCHAR(50) NOT NULL,
  abbrv VARCHAR(20) NOT NULL,
  country VARCHAR(20) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE Airports (
  city VARCHAR(50) NOT NULL,
  airportCode VARCHAR(3) NOT NULL,
  airportName VARCHAR(50) NOT NULL,
  country VARCHAR(20) NOT NULL,
  countryAbbrv VARCHAR(10) NOT NULL,
  PRIMARY KEY(airportCode)
);

CREATE TABLE Flights (
   flightNo INTEGER NOT NULL,
   airline INTEGER NOT NULL,
   departureTime DATETIME NOT NULL,
   arrivalTime DATETIME NOT NULL,
   origin VARCHAR(3) NOT NULL,
   dest VARCHAR(3) NOT NULL,
   PRIMARY KEY (flightNo, airline),

   FOREIGN KEY (origin) REFERENCES Airports(airportCode),
   FOREIGN KEY (dest) REFERENCES Airports(airportCode),
   FOREIGN KEY (airline) REFERENCES Airlines(id)
);

CREATE TABLE Seats (
  flightNo INTEGER NOT NULL,
  airline INTEGER NOT NULL,
  firstWindow INTEGER NOT NULL,
  firstMiddle INTEGER NOT NULL,
  firstAisle INTEGER NOT NULL,
  busWindow INTEGER NOT NULL,
  busMiddle INTEGER NOT NULL,
  busAisle INTEGER NOT NULL,
  econWindow INTEGER NOT NULL,
  econMiddle INTEGER NOT NULL,
  econAisle INTEGER NOT NULL,
  totalSeats INTEGER NOT NULL,
  firstCost INTEGER NOT NULL,
  busCost INTEGER NOT NULL,
  econCost INTEGER NOT NULL, 
  PRIMARY KEY (flightNo, airline),

  FOREIGN KEY (flightNo, airline) REFERENCES Flights(flightNo, airline)
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
   time DATETIME NOT NULL,
   PRIMARY KEY (id),
   FOREIGN KEY (ownershipID) REFERENCES Ownerships(id),
   CONSTRAINT checkAmopuntPositive CHECK (amount >= 0)
);

CREATE TABLE Reservations (
   id INTEGER NOT NULL,
   transactionID INTEGER NOT NULL,
   flightNo INTEGER NOT NULL,
   airline INTEGER NOT NULL,
   classType ENUM('First', 'Business', 'Economy') NOT NULL,
   seatType ENUM('Window', 'Aisle', 'Middle') NOT NULL,
   active BOOLEAN NOT NULL,
   PRIMARY KEY (id),
   FOREIGN KEY (transactionID) REFERENCES Transactions(id),
   FOREIGN KEY (flightNo, airline) REFERENCES Flights(flightNo, airline)
);

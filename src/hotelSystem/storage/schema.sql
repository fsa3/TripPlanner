CREATE TABLE IF NOT EXISTS Accommodation (
     id INTEGER PRIMARY KEY AUTOINCREMENT
    ,name VARCHAR(128) UNIQUE NOT NULL
    ,location VARCHAR(128) NOT NULL
    ,rating DECIMAL(1,1)
    ,description VARCHAR(2048)
);
CREATE TABLE IF NOT EXISTS Room (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    accId INTEGER,
    roomType VARCHAR(32),
    price DECIMAL(12,2),
    cap INTEGER,
    FOREIGN KEY (accId)
    REFERENCES Accommodation (id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Occupancy (
    roomId INTEGER NOT NULL,
    dateFrom DATE NOT NULL,
    dateTo DATE NOT NULL,
    FOREIGN KEY (roomId)
    REFERENCES Room (id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name VARCHAR(128) NOT NULL,
    manager INTEGER
);

CREATE TABLE IF NOT EXISTS Review (
    id INTEGER PRIMARY KEY AUTOINCREMENT
);



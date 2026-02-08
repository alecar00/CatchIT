CREATE DATABASE IF NOT EXISTS CatchItDB;
USE CatchItDB;

-- 1. RIDER
CREATE TABLE Rider (
    id_rider VARCHAR(50) PRIMARY KEY
    name VARCHAR(100) NOT NULL,
    permit_ztl BOOLEAN DEFAULT FALSE
);

-- 2. RESTAURANT
CREATE TABLE Restaurant (
    name VARCHAR(100) PRIMARY KEY
);

-- 3. Tabella ORDERS
CREATE TABLE Orders (
    id_order VARCHAR(50) PRIMARY KEY,
    address VARCHAR(255) NOT NULL,
    consumer VARCHAR(100) NOT NULL,
    tel_number VARCHAR(20),
    order_time DATETIME,

    -- Gestione dello STATO (Enum OrderStatus)
    status ENUM('PENDING', 'ASSIGNED', 'IN_DELIVERY', 'COMPLETED') DEFAULT 'PENDING',

    -- Chiavi Esterne (Relazioni)
    id_rider VARCHAR(50),
    id_restaurant VARCHAR(50),


    FOREIGN KEY (id_rider) REFERENCES Rider(id_rider)
        ON DELETE SET NULL
        ON UPDATE CASCADE,

    FOREIGN KEY (id_restaurant) REFERENCES Restaurant(id_restaurant)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- ==========================================
-- POPULATION DB
-- ==========================================

INSERT INTO Rider (id_rider, name, permit_ztl) VALUES
('R1', 'Mario Rossi', TRUE),
('R2', 'Luigi Verdi', FALSE),
('R3', 'Giovanni Bianchi', TRUE);

INSERT INTO Restaurant (id_restaurant, name) VALUES
('REST-01', 'Pizzeria Da Alessandro');


-- Ordine 1: PENDING (Deve apparire nella GUI!)
INSERT INTO Orders (id_order, address, consumer, tel_number, status, id_restaurant) VALUES
('ORD-1001', 'Via Roma 10', 'Giulia', '3331234567', 'PENDING', 'REST-01');

-- Ordine 2: PENDING (Assegnato a Mario Rossi)
INSERT INTO Orders (id_order, address, consumer, tel_number, status, id_restaurant, id_rider) VALUES
('ORD-1002', 'Piazza Dante 5', 'Marco', '3339876543', 'DELIVERING', 'REST-01');

-- Ordine 3: PENDING (Deve apparire nella GUI!)
INSERT INTO Orders (id_order, address, consumer, tel_number, status, id_restaurant)
VALUES ('ORD-1003', 'Via Garibaldi 20', 'Luca Bianchi', '3335550001', 'PENDING', 'REST-01');

-- Ordine 4: PENDING (Deve apparire nella GUI!)
INSERT INTO Orders (id_order, address, consumer, tel_number, status, id_restaurant)
VALUES ('ORD-1004', 'Corso Italia 100', 'Anna Verdi', '3401239876', 'PENDING', 'REST-01');

-- Ordine 5: ASSIGNED
INSERT INTO Orders (id_order, address, consumer, tel_number, status, id_restaurant)
VALUES ('ORD-1005', 'Viale Kennedy 12', 'Paolo Neri', '3387776666', 'PREPARING', 'REST-01', 'R1');

-- Ordine 6: IN_DELIVERY (Già assegnato al Rider R2 - Luigi)
INSERT INTO Orders (id_order, address, consumer, tel_number, status, id_restaurant, id_rider)
VALUES ('ORD-1006', 'Piazza Venezia 1', 'Sofia Gialli', '3394445555', 'DELIVERING', 'REST-01', 'R2');

-- Ordine 7: COMPLETED (Già consegnato da R1)
INSERT INTO Orders (id_order, address, consumer, tel_number, status, id_restaurant, id_rider)
VALUES ('ORD-1007', 'Via Napoli 88', 'Davide Blu', '3319998888', 'DELIVERED', 'REST-01', 'R1');
CREATE DATABASE IF NOT EXISTS CatchItDB;
USE CatchItDB;

-- 1. RIDER
CREATE TABLE IF NOT EXISTS Rider (
                       id_rider VARCHAR(50) PRIMARY KEY,
                           name VARCHAR(100) NOT NULL,
                       permit_ztl BOOLEAN DEFAULT FALSE
);

-- 2. RESTAURANT
CREATE TABLE IF NOT EXISTS Restaurant(
                            name VARCHAR(100) PRIMARY KEY
);

-- 3. ORDERS
CREATE TABLE IF NOT EXISTS Orders (
                        id_order INT AUTO_INCREMENT PRIMARY KEY,
                        address VARCHAR(255) NOT NULL,
                        costumer VARCHAR(100) NOT NULL,
                        tel_number VARCHAR(20),
                        order_time DATETIME,
                        status ENUM('PENDING', 'ASSIGNED', 'IN_DELIVERY', 'COMPLETED') DEFAULT 'PENDING',
                        id_rider VARCHAR(50),
                        id_restaurant VARCHAR(100),

                        FOREIGN KEY (id_rider) REFERENCES Rider(id_rider)
                            ON DELETE SET NULL
                            ON UPDATE CASCADE,

                        FOREIGN KEY (id_restaurant) REFERENCES Restaurant(name)
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

INSERT INTO Restaurant (name) VALUES
    ( 'Pizzeria Da Alessandro');



INSERT INTO Orders ( address, costumer, tel_number, status, id_rider, id_restaurant)
VALUES ('Via Roma 10', 'Giulia', '3331234567', 'PENDING', NULL, 'Pizzeria Da Alessandro');


INSERT INTO Orders (address, costumer, tel_number, status, id_rider, id_restaurant)
VALUES ( 'Piazza Dante 5', 'Marco', '3339876543', 'IN_DELIVERY', 'R1', 'Pizzeria Da Alessandro');


INSERT INTO Orders ( address, costumer, tel_number, status, id_rider, id_restaurant)
VALUES ( 'Via Garibaldi 20', 'Luca Bianchi', '3335550001', 'PENDING', NULL, 'Pizzeria Da Alessandro');


INSERT INTO Orders (address, costumer, tel_number, status, id_rider, id_restaurant)
VALUES ( 'Corso Italia 100', 'Anna Verdi', '3401239876', 'PENDING', NULL, 'Pizzeria Da Alessandro');


INSERT INTO Orders (address, costumer, tel_number, status, id_rider, id_restaurant)
VALUES ('Viale Kennedy 12', 'Paolo Neri', '3387776666', 'ASSIGNED', 'R1', 'Pizzeria Da Alessandro');


INSERT INTO Orders (address, costumer, tel_number, status, id_rider, id_restaurant)
VALUES ( 'Piazza Venezia 1', 'Sofia Gialli', '3394445555', 'ASSIGNED', 'R2', 'Pizzeria Da Alessandro');


INSERT INTO Orders (address, costumer, tel_number, status, id_rider, id_restaurant)
VALUES ('Via Napoli 88', 'Davide Blu', '3319998888', 'COMPLETED', 'R1', 'Pizzeria Da Alessandro');

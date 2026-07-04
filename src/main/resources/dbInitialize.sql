DROP DATABASE IF EXISTS CatchItDB;
CREATE DATABASE CatchItDB;
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
                                      delivery_time TIME,
                                      status ENUM('PENDING', 'ASSIGNED', 'IN_DELIVERY', 'COMPLETED') DEFAULT 'PENDING',
                                      id_rider VARCHAR(50),
                                      id_restaurant VARCHAR(100),

                                      CONSTRAINT chk_15_min_interval
                                          CHECK (MINUTE(delivery_time) IN (0, 15, 30, 45) AND SECOND(delivery_time) = 0),

                                      FOREIGN KEY (id_rider) REFERENCES Rider(id_rider)
                                              ON DELETE SET NULL
                                              ON UPDATE CASCADE,

                                      FOREIGN KEY (id_restaurant) REFERENCES Restaurant(name)
                                          ON DELETE CASCADE
                                          ON UPDATE CASCADE
);

-- 4. USERS
CREATE TABLE IF NOT EXISTS User (
                                      username VARCHAR(100) PRIMARY KEY,
                                      password VARCHAR(255) NOT NULL,
                                      role ENUM('RESTAURANT', 'RIDER') NOT NULL
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

INSERT INTO User (username, password, role) VALUES
                                                   ('DaAlessandro', 'admin', 'RESTAURANT'),
                                                   ('R1', 'password', 'RIDER'),
                                                   ('R2', 'password', 'RIDER');
                                                   ('R3', 'password', 'RIDER')
);

INSERT INTO Orders (address, costumer, tel_number, status, id_rider, id_restaurant, delivery_time)
VALUES ('Via Roma 10', 'Giulia', '3331234567', 'ASSIGNED', 'R2', 'Pizzeria Da Alessandro', '20:00:00');

INSERT INTO Orders (address, costumer, tel_number, status, id_rider, id_restaurant, delivery_time)
VALUES ('Piazza Dante 5', 'Marco', '3339876543', 'IN_DELIVERY', 'R1', 'Pizzeria Da Alessandro', '19:15:00');

INSERT INTO Orders (address, costumer, tel_number, status, id_rider, id_restaurant, delivery_time)
VALUES ('Via Garibaldi 20', 'Luca Bianchi', '3335550001', 'PENDING', NULL, 'Pizzeria Da Alessandro', '19:30:00');

INSERT INTO Orders (address, costumer, tel_number, status, id_rider, id_restaurant, delivery_time)
VALUES ('Corso Italia 100', 'Anna Verdi', '3401239876', 'PENDING', NULL, 'Pizzeria Da Alessandro', '20:15:00');

INSERT INTO Orders (address, costumer, tel_number, status, id_rider, id_restaurant, delivery_time)
VALUES ('Viale Kennedy 12', 'Paolo Neri', '3387776666', 'ASSIGNED', 'R1', 'Pizzeria Da Alessandro', '20:00:00');

INSERT INTO Orders (address, costumer, tel_number, status, id_rider, id_restaurant, delivery_time)
VALUES ('Piazza Venezia 1', 'Sofia Gialli', '3394445555', 'ASSIGNED', 'R2', 'Pizzeria Da Alessandro', '20:15:00');

INSERT INTO Orders (address, costumer, tel_number, status, id_rider, id_restaurant, delivery_time)
VALUES ('Via Napoli 88', 'Davide Blu', '3319998888', 'COMPLETED', 'R1', 'Pizzeria Da Alessandro', '20:30:00');

INSERT INTO Orders (address, costumer, tel_number, status, id_rider, id_restaurant, delivery_time)
VALUES ('Via Roma 25', 'Michael Jackson', '3319998888', 'PENDING', NULL, 'Pizzeria Da Alessandro', '20:00:00');
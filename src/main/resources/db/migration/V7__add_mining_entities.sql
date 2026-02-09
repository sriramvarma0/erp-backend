CREATE TABLE mining_vehicles (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(50) NOT NULL,
    status VARCHAR(50)
);

INSERT INTO mining_vehicles (name, type, status) VALUES ('Excavator X1', 'Excavator', 'Operational');
INSERT INTO mining_vehicles (name, type, status) VALUES ('Dump Truck D5', 'Dump Truck', 'Maintenance');

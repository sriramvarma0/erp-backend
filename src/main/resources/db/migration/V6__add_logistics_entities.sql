CREATE TABLE fleet (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(50) NOT NULL,
    status VARCHAR(50),
    capacity DOUBLE PRECISION
);

CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL,
    pickup_location VARCHAR(255),
    delivery_location VARCHAR(255)
);

INSERT INTO fleet (name, type, status, capacity) VALUES ('Truck A1', 'Truck', 'Available', 10.5);
INSERT INTO fleet (name, type, status, capacity) VALUES ('Van B2', 'Van', 'In Transit', 2.0);

INSERT INTO orders (description, status, pickup_location, delivery_location) VALUES ('Electronics Shipment', 'Pending', 'Warehouse A', 'Store B');

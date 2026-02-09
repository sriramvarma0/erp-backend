CREATE TABLE vehicles (
    id BIGSERIAL PRIMARY KEY,
    
    -- Identification
    vehicle_id VARCHAR(50) NOT NULL UNIQUE,
    registration_number VARCHAR(50) NOT NULL UNIQUE,
    fleet_number VARCHAR(50),

    -- Manufacturer
    make VARCHAR(100),
    model VARCHAR(100),
    manufacturing_year INTEGER,

    -- Power
    powertrain_type VARCHAR(50),
    engine_power_hp DOUBLE PRECISION,
    engine_capacity_cc INTEGER,
    transmission_type VARCHAR(50),
    emission_norm VARCHAR(50),

    -- Physical
    number_of_wheels INTEGER,
    axle_configuration VARCHAR(50),
    body_type VARCHAR(50),
    trailer_attached BOOLEAN DEFAULT FALSE,

    -- Load
    payload_capacity_tons DOUBLE PRECISION,
    gross_vehicle_weight DOUBLE PRECISION,
    load_volume_cbm DOUBLE PRECISION,

    -- Compliance
    insurance_expiry DATE,
    fitness_expiry DATE,
    permit_type VARCHAR(50),
    puc_expiry DATE,

    -- Tracking
    gps_device_id VARCHAR(100),
    odometer_reading_km DOUBLE PRECISION,
    average_mileage DOUBLE PRECISION,

    -- Ownership & Status
    ownership_type VARCHAR(50),
    status VARCHAR(50),
    assigned_depot VARCHAR(100),

    -- Maintenance
    last_service_date DATE,
    service_interval_km INTEGER,
    annual_maintenance_cost NUMERIC(19, 2),
    fuel_cost_per_km NUMERIC(10, 2)
);

-- Seed Data
INSERT INTO vehicles (vehicle_id, registration_number, fleet_number, make, model, manufacturing_year, powertrain_type, engine_power_hp, engine_capacity_cc, transmission_type, emission_norm, number_of_wheels, axle_configuration, body_type, trailer_attached, payload_capacity_tons, gross_vehicle_weight, load_volume_cbm, insurance_expiry, fitness_expiry, permit_type, puc_expiry, gps_device_id, odometer_reading_km, average_mileage, ownership_type, status, assigned_depot, last_service_date, service_interval_km, annual_maintenance_cost, fuel_cost_per_km)
VALUES 
('V001', 'MH12AB1234', 'FLT-001', 'Tata', 'Prima 5530', 2023, 'Diesel', 300.0, 6700, 'Manual', 'BS-VI', 10, '6x4', 'Container', TRUE, 40.0, 55.0, 30.0, '2026-12-31', '2026-12-31', 'National', '2026-06-30', 'GPS-12345', 15000.0, 3.5, 'Owned', 'Active', 'Pune Depot', '2025-12-01', 20000, 50000.00, 4.50),
('V002', 'KA01XY9876', 'FLT-002', 'Ashok Leyland', 'Ecomet 1615', 2022, 'Diesel', 150.0, 3800, 'Manual', 'BS-VI', 6, '4x2', 'Open', FALSE, 10.0, 16.0, 15.0, '2026-10-15', '2026-10-15', 'State', '2026-04-15', 'GPS-67890', 45000.0, 5.0, 'Owned', 'Maintenance', 'Bangalore Depot', '2025-12-20', 15000, 30000.00, 3.80);

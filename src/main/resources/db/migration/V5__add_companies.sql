CREATE TABLE companies (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL,
    description VARCHAR(255)
);

INSERT INTO companies (name, description) VALUES ('Construction', 'SR Group Construction Division');
INSERT INTO companies (name, description) VALUES ('Logistics', 'SR Group Logistics Division');
INSERT INTO companies (name, description) VALUES ('Mining', 'SR Group Mining Division');

-- Create Customer Table
CREATE TABLE customer (
  id          VARCHAR(36) NOT NULL PRIMARY KEY,
  first_name  VARCHAR(255),
  last_name   VARCHAR(255),
  birth_date  DATE
);

-- Create Vehicle Table
CREATE TABLE vehicle (
  id          VARCHAR(36) NOT NULL PRIMARY KEY,
  brand       VARCHAR(255),
  model       VARCHAR(255),
  production_year INT,
  vin         VARCHAR(17),
  price       DECIMAL(10, 2)
);

-- Create Contract Table
CREATE TABLE contract (
  id               VARCHAR(36) NOT NULL PRIMARY KEY,
  contract_number  INT,
  monthly_rate     DECIMAL(10, 2),
  vehicle_id       VARCHAR(36),
  customer_id      VARCHAR(36),
  FOREIGN KEY (vehicle_id) REFERENCES vehicle(id),
  FOREIGN KEY (customer_id) REFERENCES customer(id)
);
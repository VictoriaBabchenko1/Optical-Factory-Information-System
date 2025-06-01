CREATE TABLE product (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    category_id INTEGER,
    material_id INTEGER,
    price NUMERIC(10,2),
    quantity INTEGER,
    warehouse_id INTEGER
);

CREATE TABLE product_category (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT
);

CREATE TABLE material (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    supplier_id INTEGER,
    price NUMERIC(10,2),
    quantity INTEGER
);

CREATE TABLE supplier (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    contact VARCHAR(100),
    address VARCHAR(200)
);

CREATE TABLE warehouse (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(200)
);

CREATE TABLE client (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    contact VARCHAR(100),
    address VARCHAR(200)
);

CREATE TABLE employee (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    position VARCHAR(100),
    contact VARCHAR(100)
);

CREATE TABLE "order" (
    id SERIAL PRIMARY KEY,
    client_id INTEGER,
    date DATE,
    status VARCHAR(50)
);

CREATE TABLE order_item (
    id SERIAL PRIMARY KEY,
    order_id INTEGER,
    product_id INTEGER,
    quantity INTEGER,
    price NUMERIC(10,2)
);

CREATE TABLE production (
    id SERIAL PRIMARY KEY,
    product_id INTEGER,
    date DATE,
    employee_id INTEGER,
    quantity INTEGER
);

CREATE TABLE shipment (
    id SERIAL PRIMARY KEY,
    order_id INTEGER,
    date DATE,
    employee_id INTEGER,
    status VARCHAR(50)
); 
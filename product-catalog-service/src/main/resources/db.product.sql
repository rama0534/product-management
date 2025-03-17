CREATE TABLE product (
                         product_id SERIAL PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         brand VARCHAR(255) NOT NULL,
                         category VARCHAR(255) NOT NULL,
                         price DECIMAL(10,2) NOT NULL
);

CREATE TABLE inventory (
                           inventory_id SERIAL PRIMARY KEY,
                           product_id INT NOT NULL,
                           available_quantity INT NOT NULL CHECK (available_quantity >= 0),
                           FOREIGN KEY (product_id) REFERENCES product(product_id) ON DELETE CASCADE
);

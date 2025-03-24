
CREATE TABLE inventory (
                           inventory_id SERIAL PRIMARY KEY,
                           product_id INT NOT NULL,
                           available_quantity INT NOT NULL CHECK (available_quantity >= 0),
                           FOREIGN KEY (product_id) REFERENCES product(product_id) ON DELETE CASCADE
);
CREATE TABLE inventory (
                           inventory_id SERIAL PRIMARY KEY,
                           product_id INT NOT NULL UNIQUE,  -- Ensures product_id is unique
                           available_quantity INT NOT NULL CHECK (available_quantity >= 0),
                           FOREIGN KEY (product_id) REFERENCES product(product_id) ON DELETE CASCADE
);
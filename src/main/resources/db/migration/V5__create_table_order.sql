CREATE TABLE orders
(
    id          BINARY(16) NOT NULL PRIMARY KEY,

    user_id     BINARY(16) NOT NULL,
    customer_id BINARY(16) NOT NULL,
    address_id  BINARY(16) NOT NULL,

    status      VARCHAR(20)    NOT NULL,
    observation TEXT NULL,
    total_value DECIMAL(10, 2) NOT NULL,

    created_at  TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_orders_user
        FOREIGN KEY (user_id) REFERENCES users (id),

    CONSTRAINT fk_orders_customer
        FOREIGN KEY (customer_id) REFERENCES customers (id),

    CONSTRAINT fk_orders_address
        FOREIGN KEY (address_id) REFERENCES addresses (id)
);
CREATE TABLE customers
(
    id           BINARY(16) NOT NULL PRIMARY KEY,
    user_id      BINARY(16) NOT NULL,
    name         VARCHAR(100) NOT NULL,
    phone_number varchar(20)  NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,


    CONSTRAINT fk_customers_user FOREIGN KEY (user_id)
        REFERENCES users (id) ON DELETE CASCADE
);
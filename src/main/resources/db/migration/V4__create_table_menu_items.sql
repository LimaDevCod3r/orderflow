CREATE TABLE menu_items
(
    id          BINARY(16) NOT NULL PRIMARY KEY,
    name        VARCHAR(100)  NOT NULL,
    description TEXT NULL,
    size        VARCHAR(10)   NOT NULL,
    price       DECIMAL(8, 2) NOT NULL,
    available   BOOLEAN       NOT NULL DEFAULT TRUE
);
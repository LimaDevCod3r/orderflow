CREATE TABLE addresses
(
    id             BINARY(16) NOT NULL PRIMARY KEY, -- Unique identifier (UUID)
    customer_id    BINARY(16) NOT NULL,             -- Reference to customer (cliente dono do endereço)
    street         VARCHAR(150) NOT NULL,           -- Street name (nome da rua)
    street_number  VARCHAR(10)  NOT NULL,           -- Street number (número do imóvel)
    neighborhood   VARCHAR(100) NOT NULL,           -- Neighborhood / District (bairro)
    postal_code    VARCHAR(20)  NOT NULL,           -- Postal code / ZIP code (CEP)
    address_line_2 VARCHAR(100) NULL,               -- Additional address info (complemento: apto, bloco, etc.)

    CONSTRAINT fk_addresses_customers
        FOREIGN KEY (customer_id)
            REFERENCES customers (id)
            ON DELETE CASCADE
);
CREATE TABLE order_items
(
    id            BINARY(16) NOT NULL PRIMARY KEY,

    order_id      BINARY(16) NOT NULL,
    menu_item_id  BINARY(16) NOT NULL,

    quantity      INT NOT NULL,
    unit_price    DECIMAL(10,2) NOT NULL,
    subtotal      DECIMAL(10,2) NOT NULL,

    CONSTRAINT fk_order_items_order
        FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,

    CONSTRAINT fk_order_items_menu
        FOREIGN KEY (menu_item_id) REFERENCES menu_items(id)
);
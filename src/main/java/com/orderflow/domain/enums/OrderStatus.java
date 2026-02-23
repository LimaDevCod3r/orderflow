package com.orderflow.domain.enums;

public enum OrderStatus {

    CREATED,          // Pedido criado
    CONFIRMED,        // Confirmado pela loja
    IN_PREPARATION,   // Em preparo
    OUT_FOR_DELIVERY, // Saiu para entrega
    DELIVERED,        // Entregue
    CANCELED          // Cancelado

}

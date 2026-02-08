package com.alessandro.caracciolo.catchit.model;

import java.util.Arrays;

public enum OrderStatus {
    PENDING(1),
    ASSIGNED(2),
    IN_DELIVERY(3),
    COMPLETED(4);

    private final int id;

    OrderStatus(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static OrderStatus valueOf(int id) {
        return Arrays.stream(OrderStatus.values())
                .filter(Orderstatus -> Orderstatus.getId() == id)
                .findAny()
                .orElse(null);
    }
}

package com.app.enums;

public enum Category {

    FOOD("FOOD"),
    MEAL("MEAL"),
    PICPAY("PICPAY"),
    UBER("UBER"),
    CASH("CASH");

    private final String name;

    Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

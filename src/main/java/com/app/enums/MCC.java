package com.app.enums;

public enum MCC {

    FOOD_RATE_1("5411"),
    FOOD_RATE_2("5412"),
    MEAL_RATE_1("5811"),
    MEAL_RATE_2("5812"),
    ALTERNATIVE_UBER_MCC("5877"),
    ALTERNATIVE_PICPAY_MCC("6104"),

    NOT_FOUND_MCC("");

    private final String code;

    MCC(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
    }

    public static MCC fromCode(String code) {
        for (MCC mcc : values()) {
            if (mcc.getCode().equals(code)) {
                return mcc;
            }
        }
        NOT_FOUND_MCC.setCode(code);

        return NOT_FOUND_MCC;
    }
}


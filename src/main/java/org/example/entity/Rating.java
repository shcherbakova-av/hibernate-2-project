package org.example.entity;

public enum Rating {
    G("G"),
    PG("PG"),
    PG13("PG-13"),
    R("R"),
    NC17("NC-17");

    private final String value;

    Rating(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Rating fromValue(String value) {
        if (value == null) {
            return null;
        }

        for (Rating rating : Rating.values()) {
            if (rating.getValue().equals(value)) {
                return rating;
            }
        }

        throw new IllegalArgumentException(
                "Unknow rating: " + value
        );
    }
}

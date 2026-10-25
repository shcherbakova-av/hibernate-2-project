package org.example.converter;

import jakarta.persistence.AttributeConverter;

import java.time.Year;

public class YearConverter implements AttributeConverter<Year, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Year year) {
        return year == null ? null : year.getValue();
    }

    @Override
    public Year convertToEntityAttribute(Integer value) {
        return value == null ? null : Year.of(value);
    }
}

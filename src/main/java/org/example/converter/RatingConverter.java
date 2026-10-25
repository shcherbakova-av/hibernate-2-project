package org.example.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Column;
import org.example.entity.Rating;

public class RatingConverter implements AttributeConverter<Rating, String> {

    @Override
    public String convertToDatabaseColumn(Rating rating) {
        return rating == null ? null : rating.getValue();
    }

    @Override
    public Rating convertToEntityAttribute(String value) {
        return Rating.fromValue(value);
    }
}

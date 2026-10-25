package org.example.dto;

import org.example.entity.Rating;

import java.math.BigDecimal;
import java.time.Year;
import java.util.Set;

public record CreateFilmRequest(
        String title,
        String description,
        Year year,
        Byte languageId,
        Byte originalLanguageId,
        Byte rentalDuration,
        BigDecimal rentalRate,
        Short length,
        BigDecimal replacementCost,
        Rating rating,
        String specialFeatures,
        Set<Short> actorIds,
        Set<Byte> categoryIds,
        Byte storeId
) {
}

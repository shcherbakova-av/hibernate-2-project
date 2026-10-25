package org.example.dto;

import java.math.BigDecimal;

public record CreateRentalRequest(
        Short customerId,
        Integer inventoryId,
        Byte staffId,
        BigDecimal amount
) {
}

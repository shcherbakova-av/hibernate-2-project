package org.example.dto;

public record CreateCustomerRequest(
        String firstName,
        String lastName,
        String email,
        String address,
        String address2,
        String district,
        String postalCode,
        String phone,
        Short cityId,
        Byte storeId,
        boolean active

) {

}

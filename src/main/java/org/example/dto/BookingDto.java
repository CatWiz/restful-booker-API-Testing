package org.example.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.helpers.JsonKeys;

import java.io.IOException;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookingDto(
        @JsonProperty(JsonKeys.FIRSTNAME)
        String firstName,
        @JsonProperty(JsonKeys.LASTNAME)
        String lastName,
        @JsonProperty(JsonKeys.TOTAL_PRICE)
        Integer totalPrice,
        @JsonProperty(JsonKeys.DEPOSIT_PAID)
        Boolean depositPaid,
        @JsonProperty(JsonKeys.BOOKING_DATES)
        BookingDatesDto bookingdates,
        @JsonProperty(JsonKeys.ADDITIONAL_NEEDS)
        String additionalNeeds
) {
    public static BookingDto fromJson(String jsonStr) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(jsonStr, BookingDto.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to parse JSON", e);
        }
    }
}
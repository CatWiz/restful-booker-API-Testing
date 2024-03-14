package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.helpers.JsonKeys;
import org.json.*;

import java.io.IOException;

public record BookingDatesDto(
        @JsonProperty(JsonKeys.CHECKIN)
        String checkIn,
        @JsonProperty(JsonKeys.CHECKOUT)
        String checkOut
) {
    public static BookingDatesDto fromJson(String jsonStr) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonStr, BookingDatesDto.class);
    }
}

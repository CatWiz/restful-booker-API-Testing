package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Nullable;

public record CreateBookingResponseDto(
        @JsonProperty("bookingid")
        int bookingid,
        @JsonProperty("booking")
        BookingDto booking
) {
}

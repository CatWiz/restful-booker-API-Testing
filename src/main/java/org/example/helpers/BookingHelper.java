package org.example.helpers;

import io.restassured.response.Response;
import org.example.dto.BookingDto;
import org.example.requests.bookings.GetBookingIdsRequest;

import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class BookingHelper {
    public static List<String> getBookingIds() {
        Response response = GetBookingIdsRequest.getAllBookingIdsRequest();
        return response.jsonPath().getList(JsonKeys.ID, String.class);
    }
    public static String getExistingBookingId() {
        return getBookingIds().get(new Random().nextInt(getBookingIds().size()));
    }
    public static String getNonExistingBookingId() {
        return getBookingIds().getLast() + 35;
    }

    public static void assertDtoEquality(BookingDto bookingDto, BookingDto responseDto) {
        assertThat(bookingDto.firstName()).isEqualTo(responseDto.firstName());
        assertThat(bookingDto.lastName()).isEqualTo(responseDto.lastName());
        assertThat(bookingDto.totalPrice()).isEqualTo(responseDto.totalPrice());
        assertThat(bookingDto.depositPaid()).isEqualTo(responseDto.depositPaid());
        assertThat(bookingDto.bookingdates().checkIn()).isEqualTo(responseDto.bookingdates().checkIn());
        assertThat(bookingDto.bookingdates().checkOut()).isEqualTo(responseDto.bookingdates().checkOut());
        assertThat(bookingDto.additionalNeeds()).isEqualTo(responseDto.additionalNeeds());
    }
}

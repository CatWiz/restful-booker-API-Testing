package bookings;

import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.example.helpers.BookingHelper;
import org.example.requests.bookings.GetBookingByIdRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GetBookingTest {
    @Test
    @DisplayName("Get existing booking")
    void getExistingBooking() {
        var bookingId = BookingHelper.getExistingBookingId();
        Response response = GetBookingByIdRequest.getBookingIdRequest(bookingId);

        assertThat(response.getStatusCode())
                .as("Status code must be 200 (OK)")
                .isEqualTo(HttpStatus.SC_OK);

        // todo validate response body
    }

    @Test
    @DisplayName("Get non-existing booking")
    void getNonExistingBooking() {
        var bookingId = BookingHelper.getNonExistingBookingId();
        Response response = GetBookingByIdRequest.getBookingIdRequest(bookingId);

        assertThat(response.getStatusCode())
                .as("Status code must be 404 (Not Found)")
                .isEqualTo(HttpStatus.SC_NOT_FOUND);
    }
}

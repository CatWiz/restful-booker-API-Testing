package bookings;

import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.example.helpers.JsonKeys;
import org.example.requests.bookings.GetBookingIdsRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class GetBookingIdsTest {

    HashMap<String, String> queryParams;

    @BeforeEach
    void setUp() {
        queryParams = new HashMap<>();
    }

    @Test
    @DisplayName("Get all booking ids")
    void getAllBookingIds() {
        Response response = GetBookingIdsRequest.getAllBookingIdsRequest();

        var jsonPath = response.jsonPath();
        var bookingsList = jsonPath.getList(JsonKeys.ID);

        assertThat(response.getStatusCode())
                .as("Status code must be 200 (OK)")
                .isEqualTo(HttpStatus.SC_OK);
        assertThat(bookingsList).isNotEmpty();

        var uniqueBookingsCount = bookingsList.stream().distinct().count();
        assertThat(uniqueBookingsCount)
                .as("All booking ids must be unique")
                .isEqualTo(bookingsList.size());
    }

    @ParameterizedTest
    @ValueSource(strings = {"John, Doe", "Sally, Smith"})
    @DisplayName("Get all booking ids with specified first and last name")
    void getAllBookingIdsByFirstLastName(String firstLastName) {
        var firstName = firstLastName.split(",")[0];
        var lastName = firstLastName.split(",")[1];
        queryParams.put(JsonKeys.FIRSTNAME, firstName);
        queryParams.put(JsonKeys.LASTNAME, lastName);

        var response = GetBookingIdsRequest.getBookingIdsRequestWithQueryParams(queryParams);

        assertThat(response.getStatusCode())
                .as("Status code must be 200 (OK)")
                .isEqualTo(HttpStatus.SC_OK);

        var jsonPath = response.jsonPath();
        var bookingsList = jsonPath.getList(JsonKeys.ID);

        assertThat(bookingsList)
                .as("Response must contain a list of booking ids")
                .isNotNull();

        var uniqueBookingsCount = bookingsList.stream().distinct().count();
        assertThat(uniqueBookingsCount)
                .as("All booking ids must be unique")
                .isEqualTo(bookingsList.size());
    }

    @ParameterizedTest
    @ValueSource(strings = {"2021-07-01, 2021-07-31", "2021-08-01, 2021-08-31"})
    @DisplayName("Get all booking ids with specified check-in and check-out dates")
    void getAllBookingIdsByCheckInCheckOutDates(String checkInCheckOutDates) {
        var checkIn = checkInCheckOutDates.split(",")[0];
        var checkOut = checkInCheckOutDates.split(",")[1];
        queryParams.put(JsonKeys.CHECKIN, checkIn);
        queryParams.put(JsonKeys.CHECKOUT, checkOut);

        var response = GetBookingIdsRequest.getBookingIdsRequestWithQueryParams(queryParams);

        assertThat(response.getStatusCode())
                .as("Status code must be 200 (OK)")
                .isEqualTo(HttpStatus.SC_OK);

        var jsonPath = response.jsonPath();
        var bookingsList = jsonPath.getList(JsonKeys.ID);

        assertThat(bookingsList)
                .as("Response must contain a list of booking ids")
                .isNotNull();

        var uniqueBookingsCount = bookingsList.stream().distinct().count();
        assertThat(uniqueBookingsCount)
                .as("All booking ids must be unique")
                .isEqualTo(bookingsList.size());
    }
}

package bookings;

import org.apache.http.HttpStatus;
import org.example.helpers.BookingHelper;
import org.example.helpers.JsonKeys;
import org.example.properties.RestfulBookerProperties;
import org.example.requests.auth.CreateTokenRequest;
import org.example.requests.bookings.DeleteBookingRequest;
import org.example.requests.bookings.GetBookingByIdRequest;
import org.example.requests.bookings.GetBookingIdsRequest;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class DeleteBookingTest {
    static String token;

    @BeforeAll
    static void setup() {
        var auth = new JSONObject();
        auth.put(JsonKeys.USERNAME, RestfulBookerProperties.getUsername());
        auth.put(JsonKeys.PASSWORD, RestfulBookerProperties.getPassword());

        var response = CreateTokenRequest.createTokenRequest(auth);
        token = response.getBody().jsonPath().getString(JsonKeys.TOKEN);
    }

    static List<String> provideExistingBookingIds() {
        var response = GetBookingIdsRequest.getAllBookingIdsRequest();
        var ids = response.jsonPath().getList(JsonKeys.ID, String.class);
        return List.of(ids.getFirst());
    }

    @ParameterizedTest
    @MethodSource("provideExistingBookingIds")
    void testDeleteBooking(String id) {
        var response = DeleteBookingRequest.deleteBookingRequestCookie(id, token);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_CREATED);

        var response2 = GetBookingByIdRequest.getBookingIdRequest(id);
        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.SC_NOT_FOUND);
    }

    static List<String> provideNonExistingBookingIds() {
        return List.of(BookingHelper.getNonExistingBookingId());
    }

    @ParameterizedTest
    @MethodSource("provideNonExistingBookingIds")
    void testDeleteNonExistingBooking(String id) {
        var response = DeleteBookingRequest.deleteBookingRequestCookie(id, token);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_NOT_FOUND);
    }
}

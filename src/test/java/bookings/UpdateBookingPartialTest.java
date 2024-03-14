package bookings;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpStatus;
import org.example.dto.BookingDto;
import org.example.helpers.JsonKeys;
import org.example.properties.RestfulBookerProperties;
import org.example.requests.auth.CreateTokenRequest;
import org.example.requests.bookings.GetBookingByIdRequest;
import org.example.requests.bookings.GetBookingIdsRequest;
import org.example.requests.bookings.UpdateBookingRequest;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.helpers.BookingHelper.assertDtoEquality;

@Disabled
public class UpdateBookingPartialTest {
    static String token;
    static List<String> existingBookingIds;
    static List<BookingDto> validBookings;
    static List<BookingDto> invalidBookings;

    @BeforeAll
    static void setup() {
        var auth = new JSONObject();
        auth.put(JsonKeys.USERNAME, RestfulBookerProperties.getUsername());
        auth.put(JsonKeys.PASSWORD, RestfulBookerProperties.getPassword());

        var response = CreateTokenRequest.createTokenRequest(auth);
        token = response.getBody().jsonPath().getString(JsonKeys.TOKEN);

        var response2 = GetBookingIdsRequest.getAllBookingIdsRequest();
        existingBookingIds = response2.jsonPath().getList(JsonKeys.ID, String.class);

        try (var inputStream = CreateBookingTest.class.getClassLoader().getResourceAsStream("partial_update_booking.json")) {
            var objectMapper = new ObjectMapper();
            var dict = objectMapper.readValue(inputStream, new TypeReference<Map<String, Object>>() {});
            validBookings = objectMapper.convertValue(dict.get("valid"), new TypeReference<List<BookingDto>>() {});
            invalidBookings = objectMapper.convertValue(dict.get("invalid"), new TypeReference<List<BookingDto>>() {});
        }
        catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Failed to parse JSON", e);
        }
    }

    @Nested
    class TestWithInvalidToken {
        @Test
        void testUpdateInvalidTokenCookie() {
            BookingDto booking = validBookings.getFirst(); // take whatever valid booking
            String id = existingBookingIds.getFirst(); // take whatever existing booking id

            var response = UpdateBookingRequest.updateBookingRequestCookie(id, "invalidToken", booking);

            assertThat(response.getStatusCode())
                    .as("Status code must be 403 (Forbidden)")
                    .isEqualTo(HttpStatus.SC_FORBIDDEN);
        }

        @Test
        void testUpdateInvalidTokenHeader() {
            BookingDto booking = validBookings.getFirst(); // take whatever valid booking
            String id = existingBookingIds.getFirst(); // take whatever existing booking id

            var response = UpdateBookingRequest.updateBookingRequestAuth(id, "invalidToken", booking);

            assertThat(response.getStatusCode())
                    .as("Status code must be 403 (Forbidden)")
                    .isEqualTo(HttpStatus.SC_FORBIDDEN);
        }
    }

    @Nested
    class TestWithValidInput {
        public static List<Arguments> provideBookingDtos(){
            return IntStream.range(0, validBookings.size())
                    .mapToObj(i -> Arguments.of(existingBookingIds.get(i), validBookings.get(i)))
                    .toList();
        }

        @ParameterizedTest
        @MethodSource("provideBookingDtos")
        void testUpdateBookingPartialCookie(String id, BookingDto booking) {
            var response = UpdateBookingRequest.updateBookingRequestCookie(id, token, booking);

            assertThat(response.getStatusCode())
                    .as("Status code must be 200 (OK)")
                    .isEqualTo(HttpStatus.SC_OK);

            var bookingResult = response.getBody().as(BookingDto.class);
            assertDtoEquality(booking, bookingResult);
        }

        @ParameterizedTest
        @MethodSource("provideBookingDtos")
        void testUpdateBookingPartialAuth(String id, BookingDto booking) {
            var response = UpdateBookingRequest.updateBookingRequestAuth(id, token, booking);

            assertThat(response.getStatusCode())
                    .as("Status code must be 200 (OK)")
                    .isEqualTo(HttpStatus.SC_OK);

            var bookingResult = response.getBody().as(BookingDto.class);
            assertDtoEquality(booking, bookingResult);
        }
    }
}

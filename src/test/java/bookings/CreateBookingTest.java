package bookings;

import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.lang.ObjectUtils;
import org.apache.http.HttpStatus;
import org.example.dto.BookingDatesDto;
import org.example.dto.BookingDto;
import org.example.dto.CreateBookingResponseDto;
import org.example.requests.bookings.CreateBookingRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.*;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.*;
public class CreateBookingTest {
    private static List<BookingDto> validBookings;
    private static List<BookingDto> invalidBookings;

    @BeforeAll
    static void setup() {
        try (var inputStream = CreateBookingTest.class.getClassLoader().getResourceAsStream("create_bookings.json")) {
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
    class TestWithValidInput {
        public static List<BookingDto> providedBookingDtos(){
            return validBookings;
        }

        @ParameterizedTest
        @MethodSource("providedBookingDtos")
        void testCreateBooking(BookingDto bookingDto) {
            var response = CreateBookingRequest.createBookingRequest(bookingDto);

            assertThat(response.getStatusCode())
                    .as("Status code must be 200 (OK)")
                    .isEqualTo(HttpStatus.SC_OK);

            var responseBody = response.getBody();
            var responseDto = responseBody.as(CreateBookingResponseDto.class);
            var resultBookingDto = responseDto.booking();

            assertThat(bookingDto.firstName()).isEqualTo(resultBookingDto.firstName());
            assertThat(bookingDto.lastName()).isEqualTo(resultBookingDto.lastName());
            assertThat(bookingDto.totalPrice()).isEqualTo(resultBookingDto.totalPrice());
            assertThat(bookingDto.depositPaid()).isEqualTo(resultBookingDto.depositPaid());
            assertThat(bookingDto.bookingdates().checkIn()).isEqualTo(resultBookingDto.bookingdates().checkIn());
            assertThat(bookingDto.bookingdates().checkOut()).isEqualTo(resultBookingDto.bookingdates().checkOut());
            assertThat(bookingDto.additionalNeeds()).isEqualTo(resultBookingDto.additionalNeeds());
        }
    }

    @Nested
    class TestWithInvalidInput {
        public static List<BookingDto> providedBookingDtos(){
            return invalidBookings;
        }

        @ParameterizedTest
        @MethodSource("providedBookingDtos")
        void testCreateBooking(BookingDto bookingDto) {
            var response = CreateBookingRequest.createBookingRequest(bookingDto);

            assertThat(response.getStatusCode())
                    .as("Status code must be 400 (Bad Request)")
                    .isEqualTo(HttpStatus.SC_BAD_REQUEST);
        }
    }
}

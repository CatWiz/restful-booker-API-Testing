package org.example.requests.bookings;

import io.restassured.response.Response;
import org.example.dto.BookingDto;
import org.example.requests.BaseRequest;

import static io.restassured.RestAssured.given;
import static org.example.properties.RestfulBookerProperties.getUpdateBookingUrl;

public class UpdateBookingRequest {
    public static Response updateBookingRequestCookie(String bookingId, String token, BookingDto booking) {
        return given()
                .spec(BaseRequest.setUp())
                .header("Cookie", "token=" + token)
                .body(booking)
                .put(getUpdateBookingUrl(bookingId))
                .then()
                .extract().response();
    }

    public static Response updateBookingRequestAuth(String bookingId, String token, BookingDto booking) {
        return given()
                .spec(BaseRequest.setUp())
                .header("Authorization", "Basic " + token)
                .body(booking)
                .put("/booking/" + bookingId)
                .then()
                .extract().response();
    }
}

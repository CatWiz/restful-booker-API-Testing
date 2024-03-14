package org.example.requests.bookings;

import io.restassured.response.Response;
import org.example.dto.BookingDto;
import org.example.properties.RestfulBookerProperties;
import org.example.requests.BaseRequest;

import static io.restassured.RestAssured.given;

public class CreateBookingRequest {
    public static Response createBookingRequest(BookingDto bookingDto) {
        return given()
                .spec(BaseRequest.setUp())
                .body(bookingDto)
                .post(RestfulBookerProperties.getCreateBookingUrl())
                .then()
                .extract().response();
    }
}

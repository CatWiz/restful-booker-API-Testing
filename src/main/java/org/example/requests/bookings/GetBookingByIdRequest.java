package org.example.requests.bookings;

import io.restassured.response.Response;
import org.example.properties.RestfulBookerProperties;
import org.example.requests.BaseRequest;

import static io.restassured.RestAssured.given;

public class GetBookingByIdRequest {
    public static Response getBookingIdRequest(String bookingId) {
        return given()
                .spec(BaseRequest.setUp())
                .get(RestfulBookerProperties.getBookingByIdUrl(bookingId))
                .then()
                .extract().response();
    }
}

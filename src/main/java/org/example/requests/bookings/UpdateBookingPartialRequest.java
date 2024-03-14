package org.example.requests.bookings;

import io.restassured.response.Response;
import org.example.properties.RestfulBookerProperties;
import org.example.requests.BaseRequest;

import static io.restassured.RestAssured.given;

public class UpdateBookingPartialRequest {
    public static Response updateBookingPartialRequestCookie(String bookingId, String token, String body) {
        return given()
                .spec(BaseRequest.setUp())
                .header("Cookie", "token=" + token)
                .body(body)
                .patch(RestfulBookerProperties.getPartialUpdateBookingUrl(bookingId))
                .then()
                .extract().response();
    }
    public static Response updateBookingPartialRequestAuth(String bookingId, String token, String body) {
        return given()
                .spec(BaseRequest.setUp())
                .header("Authorization", "Basic " + token)
                .body(body)
                .patch(RestfulBookerProperties.getPartialUpdateBookingUrl(bookingId))
                .then()
                .extract().response();
    }
}

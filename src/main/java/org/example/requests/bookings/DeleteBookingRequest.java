package org.example.requests.bookings;

import io.restassured.response.Response;
import org.example.properties.RestfulBookerProperties;
import org.example.requests.BaseRequest;

import static io.restassured.RestAssured.given;
public class DeleteBookingRequest {
    public static Response deleteBookingRequestCookie(String bookingId, String token) {
        return given()
                .spec(BaseRequest.setUp())
                .header("Cookie", "token=" + token)
                .delete(RestfulBookerProperties.getDeleteBookingUrl(bookingId))
                .then()
                .extract().response();
    }
}

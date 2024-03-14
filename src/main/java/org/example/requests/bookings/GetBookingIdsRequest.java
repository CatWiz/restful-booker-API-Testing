package org.example.requests.bookings;

import io.restassured.response.Response;
import org.example.properties.RestfulBookerProperties;
import org.example.requests.BaseRequest;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class GetBookingIdsRequest {
    public static Response getAllBookingIdsRequest() {
        return given()
                .spec(BaseRequest.setUp())
                .body("")
                .get(RestfulBookerProperties.getBookingUrl())
                .then()
                .extract().response();
    }

    public static Response getBookingIdsRequestWithQueryParams(HashMap<String, String> queryParams) {
        return given()
                .spec(BaseRequest.setUp())
                .queryParams(queryParams)
                .get(RestfulBookerProperties.getBookingUrl())
                .then()
                .extract().response();
    }
}

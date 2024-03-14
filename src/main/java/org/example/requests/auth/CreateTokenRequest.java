package org.example.requests.auth;

import io.restassured.response.Response;
import org.example.properties.RestfulBookerProperties;
import org.example.requests.BaseRequest;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;

public class CreateTokenRequest {
    public static Response createTokenRequest(JSONObject authData) {
        return given()
                .spec(BaseRequest.setUp())
                .body(authData.toString())
                .post(RestfulBookerProperties.getAuthUrl())
                .then()
                .extract().response();
    }
}

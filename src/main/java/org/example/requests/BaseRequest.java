package org.example.requests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.example.properties.RestfulBookerProperties;

public class BaseRequest {
    public static RequestSpecification setUp(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri(RestfulBookerProperties.getBaseUrl());
        requestSpecBuilder.setContentType(ContentType.JSON);

        return requestSpecBuilder.build();
    }
}

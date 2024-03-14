package auth;

import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.example.properties.RestfulBookerProperties;
import org.example.requests.auth.CreateTokenRequest;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.example.helpers.JsonKeys;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateTokenTest {
    public static JSONObject auth;

    @BeforeAll
    static void setup() {
        auth = new JSONObject();
    }

    @Test
    @DisplayName("Create token with valid credentials")
    void testCreateToken() {
        auth.put(JsonKeys.USERNAME, RestfulBookerProperties.getUsername());
        auth.put(JsonKeys.PASSWORD, RestfulBookerProperties.getPassword());
        Response response = CreateTokenRequest.createTokenRequest(auth);

        assertThat(response.getStatusCode())
                .as("Status code must be 200 (OK)")
                .isEqualTo(HttpStatus.SC_OK);
        assertThat(response.getBody().jsonPath().getString(JsonKeys.TOKEN))
                .as("Response must contain a token")
                .isNotNull()
                .as("Token must not be empty")
                .isNotEmpty();
    }

    @Test
    @DisplayName("Create token with invalid credentials")
    void testCreateTokenInvalidCredentials() {
        auth.put(JsonKeys.USERNAME, "admin");
        auth.put(JsonKeys.PASSWORD, "randomPassword");
        Response response = CreateTokenRequest.createTokenRequest(auth);

        assertThat(response.getStatusCode())
                .as("Status code must be 401 (Unauthorized)")
                .isEqualTo(HttpStatus.SC_UNAUTHORIZED);
        assertThat(response.getBody().jsonPath().getString(JsonKeys.TOKEN))
                .as("Response must not contain a token")
                .isNull();
        assertThat(response.getBody().jsonPath().getString(JsonKeys.REASON))
                .as("Response must contain a reason message")
                .isEqualTo("Bad credentials");
    }
}

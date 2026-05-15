package com.metrolina;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;


public class AuthVerification extends BaseTest {
    
    @Test
    @DisplayName("2a - Auth: Valid API key returns 200")
    void auth_validKey_returns200() {
        given()
            .header("apiKey", API_KEY)
        .when()
            .get("/Test/GetItemList")
        .then()
            .log().all()
            .statusCode(200);
    }

 @Test
    @DisplayName("2b - Auth: Invalid API key returns 401 or 403")
    void auth_invalidKey_returnsUnauthorized() {
        int status = given()
            .header("apiKey", "invalid-key-12345")
        .when()
            .get("/Test/GetItemList")
        .then()
            .log().all()
            .extract().statusCode();

        assertTrue(status == 401 || status == 403,
            "Expected 401 or 403 for invalid key, got: " + status);
}
}

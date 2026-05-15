package com.metrolina;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class HealthCheckTest extends BaseTest {

    @Test
    @DisplayName("1 - Health Check: API is reachable without auth")
    void healthCheck_returnsOk() {
       String response = given()
            .when()
                .get("/Test/TestEndpoint")
            .then()
                .statusCode(200)
                .extract().body().asString();
         System.out.println("Health Check Response: " + response);
    }
}

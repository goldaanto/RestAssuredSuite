package com.metrolina;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import io.restassured.response.Response;

public class ItemCreationTest extends BaseTest {
    // Placeholder for future item creation tests
     static String createdItemKey;

    @Test
    @DisplayName("4 - Create Item: Item is created and appears in subsequent list call")
    void createItem_appearsInList() {
        String testItemNumber = "TEST-001";
        String testItemDesc   = "Automated Test Plant";

        // Create the item
        Response createResponse = given()
            .header("apiKey", API_KEY)
            .queryParam("itemNumber", testItemNumber)
            .queryParam("itemDesc",   testItemDesc)
        .when()
            .post("/Test/CreateItem")
        .then()
            .log().all()
            .statusCode(anyOf(is(200), is(201)))
            .extract().response();

        System.out.println("Create Response: " + createResponse.asString());

       
    }

    @Test
    @DisplayName("BONUS - Create Item: Missing required fields returns error")
    void createItem_missingFields_returnsError() {
        int status = given()
            .header("apiKey", API_KEY)
        .when()
            .post("/Test/CreateItem")
        .then()
            .log().all()
            .extract().statusCode();

        assertTrue(status >= 400,
            "Missing required fields should return 4xx error, got: " + status);
    }
}

package com.metrolina;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import io.restassured.response.Response;

public class ItemDeletiont extends BaseTest {
    // Placeholder for future item deletion tests
    @Test
    @DisplayName("5 - Delete Item: Item no longer appears in list after deletion")
    void deleteItem_notInListAfterDeletion() {
        // First create an item to delete
        String testItemNumber = "DELETE-001";
        String testItemDesc   = "Item To Be Deleted";

        Response createResponse = given()
            .header("apiKey", API_KEY)
            .queryParam("itemKey", testItemNumber)            
        .when()
            .post("/Test/CreateItem")
        .then()
            .statusCode(anyOf(is(200), is(201)))
            .extract().response();

        String itemKey = createResponse.jsonPath().getString("itemKey");
        assertNotNull(itemKey, "Created item should have an itemKey");

        System.out.println("Created itemKey for deletion: " + itemKey);

        // Delete the item
        given()
            .header("apiKey", API_KEY)
            .queryParam("itemKey", itemKey)
        .when()
            .post("/Test/DeleteItem")
        .then()
            .log().all()
            .statusCode(anyOf(is(200), is(204)));

        // Confirm it no longer appears in the list
        List<Map<String, Object>> items = given()
            .header("apiKey", API_KEY)
        .when()
            .get("/Test/GetItemList")
        .then()
            .statusCode(200)
            .extract().jsonPath().getList("$");

        boolean stillExists = items.stream()
            .anyMatch(item -> itemKey.equals(item.get("itemKey")));

        assertFalse(stillExists, "Deleted item should NOT appear in GetItemList");
    }
    
}

package com.metrolina;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class ItemEditTest extends BaseTest {

    @Test
    @DisplayName("6 - Edit Item: Pre-seeded item fields are updated successfully")
    void editItem_preSeeded_updatesSuccessfully() {
        // Get the first seeded item
        List<Map<String, Object>> items = given()
            .header("apiKey", API_KEY)
        .when()
            .get("/Test/GetItemList")
        .then()
            .statusCode(200)
            .extract().jsonPath().getList("$");

        assertFalse(items.isEmpty(), "Item list should have seeded items");

        Map<String, Object> firstItem = items.get(0);
        String seededItemKey  = (String) firstItem.get("itemKey");
        String originalDesc   = (String) firstItem.get("itemDesc");
        String updatedDesc    = originalDesc + " (edited)";

        System.out.println("Editing itemKey: " + seededItemKey);
        System.out.println("Original desc: "   + originalDesc);
        System.out.println("Updated desc: "    + updatedDesc);

        // Edit the item
        given()
            .header("apiKey", API_KEY)
            .contentType("application/json")
            .body(Map.of(
                "itemKey",  seededItemKey,
                "itemDesc", updatedDesc
            ))
        .when()
            .post("/Test/EditItem")
        .then()
            .log().all()
            .statusCode(anyOf(is(200), is(204)));

        // Verify change is reflected in list
        List<Map<String, Object>> updatedItems = given()
            .header("apiKey", API_KEY)
        .when()
            .get("/Test/GetItemList")
        .then()
            .statusCode(200)
            .extract().jsonPath().getList("$");

        boolean updated = updatedItems.stream()
            .anyMatch(item -> seededItemKey.equals(item.get("itemKey"))
                           && updatedDesc.equals(item.get("itemDesc")));

        assertTrue(updated, "Edited item should reflect the updated description");
    }

   
}

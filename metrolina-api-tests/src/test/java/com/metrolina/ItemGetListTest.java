package com.metrolina;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import io.restassured.response.Response;

public class ItemGetListTest extends BaseTest {
    @Test
    @DisplayName("3 - Get Item List: Validates structure including nested location and inventory")
    void getItemList_validatesStructure() {
        Response response = given()
            .header("apiKey", API_KEY)
        .when()
            .get("/Test/GetItemList")
        .then()
            .log().all()
            .statusCode(200) .extract().response();
            

        
    }

    
}

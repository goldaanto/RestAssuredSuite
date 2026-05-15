package com.metrolina;

import org.junit.jupiter.api.BeforeAll;

import io.github.cdimascio.dotenv.Dotenv;
import io.restassured.RestAssured;

public class BaseTest {

    protected static String API_KEY;
    protected static final String BASE_URL = "https://api.metrolinagreenhouses.com";

    @BeforeAll
    static void setup() {
        Dotenv dotenv = Dotenv.load();
        API_KEY = dotenv.get("API_KEY");
        RestAssured.baseURI = BASE_URL;
    }
}
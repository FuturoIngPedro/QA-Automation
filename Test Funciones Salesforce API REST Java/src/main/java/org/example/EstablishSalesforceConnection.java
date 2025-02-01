package org.example;

import static io.restassured.RestAssured.given;

public class EstablishSalesforceConnection {
    public static String establishConnection() {

        TestEnvironment testEnvironment = new TestEnvironment();

        return
                given().urlEncodingEnabled(true)
                        .param("username", testEnvironment.getUsername())
                        .param("password", testEnvironment.getPassword())
                        .param("client_id", testEnvironment.getClientId())
                        .param("client_secret", testEnvironment.getClientSecret())
                        .param("grant_type", "password")
                        .header("Accept", "application/json")
                        .header("Content-Type", "application/x-www-form-urlencoded").
                        when().
                        post("https://login.salesforce.com/services/oauth2/token").
                        then().
                        assertThat().statusCode(200).extract().path("access_token");
    }

}

package org.example;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;

public class RequestHandler {
    TestEnvironment testEnvironment = new TestEnvironment();

    public Response postRequest(JSONObject record, String object) {
        return
                given().
                        contentType(ContentType.JSON).
                        accept(ContentType.JSON).
                        header(Constants.AUTHORIZATION, Constants.BEARER + Constants.ACCESS_TOKEN).
                        header(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON).
                        body(record.toString()).
                        when().post(testEnvironment.getEndpoint() + "/services/data/v51.0/sobjects/" + object);
    }

    public Response pathRequest(JSONObject record, String id, String object) {

        return
                given().
                        contentType(ContentType.JSON).
                        accept(ContentType.JSON).
                        header(Constants.AUTHORIZATION, Constants.BEARER + Constants.ACCESS_TOKEN).
                        header(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON).
                        body(record.toString()).
                        when().patch(testEnvironment.getEndpoint() + "/services/data/v51.0/sobjects/" + object + "/" + id);

    }

    public String deleteRecordById(String id, String object) {
        return
                given().
                        contentType(ContentType.JSON).
                        accept(ContentType.JSON).
                        header(Constants.AUTHORIZATION, Constants.BEARER + Constants.ACCESS_TOKEN).
                        header(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON).
                        when().delete(testEnvironment.getEndpoint() + "/services/data/v51.0/sobjects/" + object + "/" + id).
                        then().statusCode(204).toString();
    }

}

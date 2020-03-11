package controllers;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class ControllerFather {
    protected RequestSpecification requestSpecification;

    public ControllerFather() {
        this.requestSpecification = RestAssured.given().contentType(ContentType.JSON);
    }
}

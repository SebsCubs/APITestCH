package controllers;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.net.URL;

public class ControllerFather {
    protected RequestSpecification requestSpecification;

    public ControllerFather() {
        this.requestSpecification = RestAssured.given().contentType(ContentType.JSON);
    }
}

package controllers;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.net.URL;

public class ControllerFather {
    protected URL url;
    protected RequestSpecification requestSpecification;

    public ControllerFather(URL url) {
        this.url = url;
        this.requestSpecification = RestAssured.given().contentType(ContentType.JSON);
    }
}

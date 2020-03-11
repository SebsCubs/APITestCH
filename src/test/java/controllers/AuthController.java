package controllers;

import entities.Authorization;
import entities.AuthorizationBuilder;
import helpers.UrlBuilder;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.serenitybdd.core.Serenity;
import utils.JacksonHelper;
import utils.PropertiesHelper;

import java.net.URL;

public class AuthController extends ControllerFather {
    private PropertiesHelper propertiesHelper = new PropertiesHelper();
    private JacksonHelper jsonHelper = new JacksonHelper();
    private Response response;
    private Authorization authorization;
    private URL url;
    private String jsonBody, request_token;


    public AuthController() {
    }

    public Response authentication() {
        createRequestToken();
        createSessionWithLogin();
        return createSession();
    }

    public Response createRequestToken() {
        url = new UrlBuilder()
                .addPathStep(propertiesHelper.getValueByKey("createRequestToken"))
                .build();
        response = requestSpecification.get(url);
        request_token = jsonHelper.getJsonField(response, "request_token");
        return response;
    }

    public Response createSessionWithLogin() {
        url = new UrlBuilder()
                .addPathStep(propertiesHelper.getValueByKey("createSessionWithLogin"))
                .build();
        authorization = new AuthorizationBuilder()
                .withUsername(System.getenv("TheMovieDBUser"))
                .withPassword(System.getenv("TheMovieDBPass"))
                .withRequestToken(request_token)
                .build();
        jsonBody = jsonHelper.objectToJson(authorization);
        response = requestSpecification.given().body(jsonBody).and().post(url);
        request_token = jsonHelper.getJsonField(response, "request_token");
        return response;
    }

    public Response createSession() {
        url = new UrlBuilder()
                .addPathStep(propertiesHelper.getValueByKey("createSession"))
                .build();
        authorization = new AuthorizationBuilder()
                .withRequestToken(request_token)
                .build();
        jsonBody = jsonHelper.objectToJson(authorization);
        response = requestSpecification.given().body(jsonBody).and().post(url);
        Serenity.setSessionVariable("session_id")
                .to(jsonHelper.getJsonField(response, "session_id"));
        return response;
    }

    public Response deleteSession(String session_id) {
        url = new UrlBuilder()
                .addPathStep(propertiesHelper.getValueByKey("deleteSession"))
                .build();
        authorization = new AuthorizationBuilder()
                .withSession_id(session_id)
                .build();
        jsonBody = jsonHelper.objectToJson(authorization);
        response = requestSpecification.given().body(jsonBody).delete(url);

        requestSpecification = RestAssured.given().contentType(ContentType.JSON);
        return response;
    }


}

package controllers;

import entities.Authorization;
import entities.AuthorizationBuilder;
import helpers.UrlBuilder;
import io.restassured.response.Response;
import utils.JacksonHelper;
import utils.PropertiesHelper;

import java.net.URL;

public class AuthController extends ControllerFather{
    private PropertiesHelper propertiesHelper = new PropertiesHelper();
    private JacksonHelper jsonHelper = new JacksonHelper();
    private Response response;
    private Authorization authorization;
    private String jsonBody,request_token;
    private URL url;

    public AuthController(){}

    public Response createRequestToken(){
        url = new UrlBuilder()
                .addPathStep(propertiesHelper.getValueByKey("createRequestToken"))
                .build();
        response = requestSpecification.get(url);
        request_token = jsonHelper.getJsonField(response,"request_token");
        return response;
    }

    public Response createSessionWithLogin(){
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
        request_token = jsonHelper.getJsonField(response,"request_token");
        return response;
    }
    public Response createSession(){
        url = new UrlBuilder()
                .addPathStep(propertiesHelper.getValueByKey("createSession"))
                .build();
        authorization = new AuthorizationBuilder()
                .withRequestToken(request_token)
                .build();
        jsonBody = jsonHelper.objectToJson(authorization);
        response = requestSpecification.given().body(jsonBody).and().post(url);
        return response;
    }



}

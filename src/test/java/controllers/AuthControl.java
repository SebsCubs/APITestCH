package controllers;

import helpers.builders.AuthorizationBuilder;
import helpers.builders.UrlBuilder;
import helpers.entities.Authorization;
import utils.JsonManager;
import utils.PropertiesManager;
import io.restassured.response.Response;

import java.net.URL;

public class AuthControl extends ControllerFather {
    private Response response;
    private Authorization authorization;
    private String JsonBody;
    private String username = PropertiesManager.getValueByKey("username");
    private String password = PropertiesManager.getValueByKey("password");
    private URL url;


    public AuthControl(){
        super(new UrlBuilder().build());
    }

    public Authorization authentication(){
        createRequestToken();
        createSessionWithLogin();
        authorization = createSession();
        return authorization;
    }
    private void createRequestToken() {
        url = new UrlBuilder()
                .addPathStep("authentication/token/new")
                .build();
        response = requestSpecification.get(url);
        authorization = JsonManager.responseToAuthorization(response);
    }
    private void createSessionWithLogin() {
        url = new UrlBuilder()
                .addPathStep("authentication/token/validate_with_login")
                .build();
        String request_token = authorization.getRequest_token();
        authorization = new AuthorizationBuilder()
                .withUsername(username)
                .withPassword(password)
                .withRequestToken(request_token)
                .build();
        JsonBody = JsonManager.objectToJson(authorization);
        response = requestSpecification.given().body(JsonBody).and().post(url);
        authorization = JsonManager.responseToAuthorization(response);
    }
    private Authorization createSession(){
        url = new UrlBuilder()
                .addPathStep("authentication/session/new")
                .build();
        authorization = new AuthorizationBuilder()
                .withRequestToken(authorization.getRequest_token())
                .build();
        JsonBody = JsonManager.objectToJson(authorization);
        response = requestSpecification.when().body(JsonBody).and().post(url);
        authorization = JsonManager.responseToAuthorization(response);
        return authorization;
    }

    public Response deleteSession(String session_id){
        url = new UrlBuilder()
                .addPathStep("authentication/session")
                .build();
        authorization = new AuthorizationBuilder()
                .withSession_id(session_id)
                .build();
        JsonBody = JsonManager.objectToJson(authorization);
        response = requestSpecification.given().body(JsonBody).and().delete(url);
        return response;
    }
}

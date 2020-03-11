package controllers;

import entities.Lists;
import entities.ListsBuilder;
import helpers.UrlBuilder;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.serenitybdd.core.Serenity;
import utils.JacksonHelper;
import utils.PropertiesHelper;

import java.net.URL;

public class ListsController extends ControllerFather {
    private PropertiesHelper propertiesHelper = new PropertiesHelper();
    private JacksonHelper jsonHelper = new JacksonHelper();
    private String jsonBody;
    private Response response;
    private URL url;
    private Lists list;


    public Response createList(String name, String description, String language) {
        url = new UrlBuilder()
                .addPathStep(propertiesHelper.getValueByKey("createList"))
                .build();
        list = new ListsBuilder()
                .withName(name)
                .withDescription(description)
                .withLanguage(language)
                .build();
        jsonBody = jsonHelper.objectToJson(list);
        response = requestSpecification.given().body(jsonBody)
                .queryParam("session_id", (Object) Serenity.sessionVariableCalled("session_id"))
                .and().post(url);

        Serenity.setSessionVariable("list_id").to(jsonHelper.getJsonField(response, "list_id"));


        requestSpecification = RestAssured.given().contentType(ContentType.JSON);
        return response;
    }

    public Response deleteList(String list_id) {
        url = new UrlBuilder()
                .addPathStep(propertiesHelper.getValueByKey("createList"))
                .addPathStep(list_id)
                .build();
        response = requestSpecification
                .queryParam("session_id", (Object) Serenity.sessionVariableCalled("session_id"))
                .delete(url);
        requestSpecification = RestAssured.given().contentType(ContentType.JSON);
        return response;
    }

    public Response getDetails(String list_id) {
        url = new UrlBuilder()
                .addPathStep("list")
                .addPathStep(list_id)
                .build();
        response = requestSpecification.get(url);

        requestSpecification = RestAssured.given().contentType(ContentType.JSON);
        return response;
    }

    public Response addMovie(String list_id, String media_id) {
        url = new UrlBuilder()
                .addPathStep(propertiesHelper.getValueByKey("createList"))
                .addPathStep(list_id)
                .addPathStep(propertiesHelper.getValueByKey("addMovie"))
                .build();
        list = new ListsBuilder()
                .withMedia_id(media_id)
                .build();
        jsonBody = jsonHelper.objectToJson(list);
        response = requestSpecification
                .queryParam("session_id", (Object) Serenity.sessionVariableCalled("session_id"))
                .given().body(jsonBody)
                .and().post(url);


        requestSpecification = RestAssured.given().contentType(ContentType.JSON);
        return response;
    }

    public Response checkItemStatus(String list_id, String movie_id) {
        requestSpecification = RestAssured.given().contentType(ContentType.JSON);
        url = new UrlBuilder()
                .addPathStep(propertiesHelper.getValueByKey("createList"))
                .addPathStep(list_id)
                .addPathStep(propertiesHelper.getValueByKey("checkItemStatus"))
                .build();
        response = requestSpecification.queryParam("movie_id", movie_id).get(url);


        requestSpecification = RestAssured.given().contentType(ContentType.JSON);
        return response;
    }

    public Response removeMovie(String list_id, String media_id) {
        url = new UrlBuilder()
                .addPathStep(propertiesHelper.getValueByKey("createList"))
                .addPathStep(list_id)
                .addPathStep(propertiesHelper.getValueByKey("removeMovie"))
                .build();
        list = new ListsBuilder()
                .withMedia_id(media_id)
                .build();
        jsonBody = jsonHelper.objectToJson(list);
        response = requestSpecification
                .queryParam("session_id", (Object) Serenity.sessionVariableCalled("session_id"))
                .given().body(jsonBody).and().post(url);


        requestSpecification = RestAssured.given().contentType(ContentType.JSON);
        return response;
    }

    public Response clearList(String list_id) {
        url = new UrlBuilder()
                .addPathStep(propertiesHelper.getValueByKey("createList"))
                .addPathStep(list_id)
                .addPathStep(propertiesHelper.getValueByKey("clearList"))
                .build();
        response = requestSpecification
                .queryParam("session_id", (Object) Serenity.sessionVariableCalled("session_id"))
                .queryParam("confirm", "true")
                .post(url);

        requestSpecification = RestAssured.given().contentType(ContentType.JSON);
        return response;
    }

}

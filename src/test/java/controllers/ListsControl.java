package controllers;

import helpers.builders.ListsBuilder;
import helpers.builders.UrlBuilder;
import helpers.entities.Authorization;
import helpers.entities.Lists;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.JsonManager;

import java.net.URL;

public class ListsControl extends ControllerFather{
    private Response response;
    private Lists list;
    private Authorization authorization;
    private String JsonBody;
    private URL url;

    public ListsControl(){
        super(new UrlBuilder().build());
    }

    public Response getDetails(String id){

        url = new UrlBuilder()
                .addPathStep("list")
                .addPathStep(id)
                .build();
        response = requestSpecification.get(url);
        return response;
    }
    public Response checkItemStatus(String list_id,String movie_id){
        requestSpecification = RestAssured.given().contentType(ContentType.JSON);
        url = new UrlBuilder()
                .addPathStep("list")
                .addPathStep(list_id)
                .addPathStep("item_status")
                .addQueryParam("movie_id="+movie_id)
                .build();
        response = requestSpecification.get(url);
        return response;
    }
    public Response createList(String session_id,String name,String description,String language){
        url = new UrlBuilder()
                .addPathStep("list")
                .addQueryParam("session_id="+session_id)
                .build();
        list = new ListsBuilder()
                .withName(name)
                .withDescription(description)
                .withLanguage(language)
                .build();
        JsonBody = JsonManager.objectToJson(list);
        response = requestSpecification.given().body(JsonBody).and().post(url);
        return response;
    }
    public Response addMovie(String list_id, String media_id, String session_id){
        url = new UrlBuilder()
                .addPathStep("list")
                .addPathStep(list_id)
                .addPathStep("add_item")
                .addQueryParam("session_id="+session_id)
                .build();
        list = new ListsBuilder()
                .withMedia_id(media_id)
                .build();
        JsonBody = JsonManager.objectToJson(list);
        response = requestSpecification.given().body(JsonBody).and().post(url);
        return response;
    }
    public Response removeMovie(String list_id, String media_id, String session_id){
        url = new UrlBuilder()
                .addPathStep("list")
                .addPathStep(list_id)
                .addPathStep("remove_item")
                .addQueryParam("session_id="+session_id)
                .build();
        list = new ListsBuilder()
                .withMedia_id(media_id)
                .build();
        JsonBody = JsonManager.objectToJson(list);
        response = requestSpecification.given().body(JsonBody).and().post(url);
        return response;
    }
    public Response clearList(String list_id,String session_id){
        url = new UrlBuilder()
                .addPathStep("list")
                .addPathStep(list_id)
                .addPathStep("clear")
                .addQueryParam("session_id="+session_id)
                .addQueryParam("confirm=true")
                .build();
        response = requestSpecification.post(url);
        return response;
    }
    public Response deleteList(String list_id, String session_id){
        url = new UrlBuilder()
                .addPathStep("list")
                .addPathStep(list_id)
                .addQueryParam("session_id="+session_id)
                .build();
        response = requestSpecification.delete(url);
        requestSpecification = RestAssured.given().contentType(ContentType.JSON);
        return response;
    }

}

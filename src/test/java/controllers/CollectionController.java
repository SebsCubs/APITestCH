package controllers;

import helpers.UrlBuilder;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.PropertiesHelper;

import java.net.URL;

public class CollectionController extends ControllerFather {
    private PropertiesHelper propertiesHelper = new PropertiesHelper();
    private Response response;
    private URL url;

    public Response getDetails(String coll_id) {
        url = new UrlBuilder()
                .addPathStep(propertiesHelper.getValueByKey("collections.mainUrl"))
                .addPathStep(coll_id)
                .build();
        response = requestSpecification.given().get(url);

        requestSpecification = RestAssured.given().contentType(ContentType.JSON);
        return response;
    }

    public Response getImages(String coll_id) {
        url = new UrlBuilder()
                .addPathStep(propertiesHelper.getValueByKey("collections.mainUrl"))
                .addPathStep(coll_id)
                .addPathStep("images")
                .build();
        response = requestSpecification.given().get(url);

        requestSpecification = RestAssured.given().contentType(ContentType.JSON);
        return response;
    }

    public Response getTranslations(String coll_id) {
        url = new UrlBuilder()
                .addPathStep(propertiesHelper.getValueByKey("collections.mainUrl"))
                .addPathStep(coll_id)
                .addPathStep("images")
                .build();
        response = requestSpecification.given().get(url);

        requestSpecification = RestAssured.given().contentType(ContentType.JSON);
        return response;
    }
}

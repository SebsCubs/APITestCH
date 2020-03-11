package controllers;

import entities.Rater;
import entities.RaterBuilder;
import helpers.UrlBuilder;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.serenitybdd.core.Serenity;
import utils.JacksonHelper;
import utils.PropertiesHelper;

import java.net.URL;


public class MovieRateController extends ControllerFather {
    private PropertiesHelper propertiesHelper = new PropertiesHelper();
    private JacksonHelper jsonHelper = new JacksonHelper();
    private Response response;
    private URL url;

    public Response rateMovie(String movie_id, double value) {
        url = getUrl(movie_id);
        Rater rater = new RaterBuilder()
                .withValue(value)
                .build();
        String jsonBody = jsonHelper.objectToJson(rater);
        response = requestSpecification
                .queryParam("session_id", (Object) Serenity.sessionVariableCalled("session_id"))
                .given().body(jsonBody).and().post(url);

        requestSpecification = RestAssured.given().contentType(ContentType.JSON);
        return response;
    }

    public Response deleteRate(String movie_id) {
        url = getUrl(movie_id);
        response = requestSpecification
                .queryParam("session_id", (Object) Serenity.sessionVariableCalled("session_id"))
                .delete(url);

        requestSpecification = RestAssured.given().contentType(ContentType.JSON);
        return response;
    }

    private URL getUrl(String movie_id) {
        url = new UrlBuilder()
                .addPathStep(propertiesHelper.getValueByKey("rateMovie.mainUrl"))
                .addPathStep(movie_id)
                .addPathStep(propertiesHelper.getValueByKey("rateModule"))
                .build();
        return url;
    }
}

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

public class TVEpisodeController extends ControllerFather {
    private PropertiesHelper propertiesHelper = new PropertiesHelper();
    private JacksonHelper jsonHelper = new JacksonHelper();
    private String jsonBody;
    private Response response;
    private URL url;
    private Rater rater;

    public Response rateTVEpisode(String tv_id,double rate,Integer season,Integer episode){
        url = getUrl(tv_id,season,episode);
        rater = new RaterBuilder()
                .withValue(rate)
                .build();
        jsonBody = jsonHelper.objectToJson(rater);

        response = requestSpecification
                .queryParam("session_id", (Object) Serenity.sessionVariableCalled("session_id"))
                .given().body(jsonBody).and().post(url);

        requestSpecification = RestAssured.given().contentType(ContentType.JSON);
        return response;
    }

    public Response deleteTVEpRate(String tv_id,Integer season,Integer episode){
        url = getUrl(tv_id,season,episode);
        response = requestSpecification
                .queryParam("session_id", (Object) Serenity.sessionVariableCalled("session_id"))
                .given().delete(url);

        requestSpecification = RestAssured.given().contentType(ContentType.JSON);
        return response;
    }
    private URL getUrl(String tv_id,Integer season, Integer episode) {
        url = new UrlBuilder()
                .addPathStep(propertiesHelper.getValueByKey("rateTVShow.mainUrl"))
                .addPathStep(tv_id)
                .addPathStep("season")
                .addPathStep(String.valueOf(season))
                .addPathStep("episode")
                .addPathStep(String.valueOf(episode))
                .addPathStep(propertiesHelper.getValueByKey("rateModule"))
                .build();
        return url;
    }
}

package controllers;

import helpers.builders.RaterBuilder;
import helpers.builders.UrlBuilder;
import helpers.entities.Rater;
import io.restassured.response.Response;
import utils.JsonManager;

public class MovieRankControl extends ControllerFather{
    private Response response;
    private Rater rater;
    private String JsonBody;
    public MovieRankControl() { super(new UrlBuilder().build()); }

    public Response rateMovie(Integer movie_id, double value) {
        url = new UrlBuilder()
                .addPathStep("movie")
                .addPathStep(String.valueOf(movie_id))
                .addPathStep("rating")
                .build();
        rater = new RaterBuilder()
                .withValue(value)
                .build();
        JsonBody = JsonManager.objectToJson(rater);
        response = requestSpecification.given().body(JsonBody).and().post(url);
        return response;
    }

}

package testSteps;

import controllers.MovieRankControl;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;

public class RateMovieSteps {
    private Response response;
    private MovieRankControl movieRankControlInstance;

    @When("^The movie with id \"([^\"]*)\" is rated with \"([^\"]*)\"$")
    public void theMovieWithIdIsRatedWith(String movie_id, String value) {
        movieRankControlInstance = new MovieRankControl();
        Integer movie_idInt = Integer.parseInt(movie_id);
        double valueNum = Double.parseDouble(value);


        response = movieRankControlInstance.rateMovie(movie_idInt,valueNum);
    }

    @Then("^The response has a valid status code$")
    public void theResponseHasAValidStatusCode() {
    }
}

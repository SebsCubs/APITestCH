package steps;

import controllers.MovieRateController;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import utils.JacksonHelper;
import utils.PropertiesHelper;

public class MovieRateSteps {
    private Response response;
    private MovieRateController rateControllerInstance = new MovieRateController();
    private PropertiesHelper propertiesHelper = new PropertiesHelper();
    private JacksonHelper jsonHelper = new JacksonHelper();

    @Given("^the movie Jumanji: The Next Level doesn't have a rate from the test account$")
    public void theMovieJumanjiTheNextLevelDoesnTHaveARateFromTheTestAccount() {
        response = rateControllerInstance.deleteRate(propertiesHelper.getValueByKey("jumanjiID"));
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        Assertions.assertThat(jsonHelper.getJsonField(response, "status_code")).isEqualTo("13");
    }

    @When("^the movie Jumanji:The Next Level is rated with \"([^\"]*)\"$")
    public void theMovieJumanjiTheNextLevelIsRatedWith(String rate) {
        double rateNum = Double.parseDouble(rate);
        response = rateControllerInstance.rateMovie(propertiesHelper.getValueByKey("jumanjiID"), rateNum);
    }

    @Then("^the movie has now a new rate$")
    public void theMovieHasNowANewRate() {
        Assertions.assertThat(response.statusCode()).isEqualTo(201);
        Assertions.assertThat(jsonHelper.getJsonField(response, "status_code")).contains("1");
    }

    @Then("^the rate is updated$")
    public void theRateIsUpdated() {
        Assertions.assertThat(response.statusCode()).isEqualTo(201);
        Assertions.assertThat(jsonHelper.getJsonField(response, "status_code")).contains("1");
    }

    @Given("^the movie Jumanji:The Next Level has a rate from the test account$")
    public void theMovieJumanjiTheNextLevelHasARateFromTheTestAccount() {
        response = rateControllerInstance.rateMovie(propertiesHelper.getValueByKey("jumanjiID"), 7.0);
        Assertions.assertThat(response.statusCode()).isEqualTo(201);
        Assertions.assertThat(jsonHelper.getJsonField(response, "status_code")).contains("1");
    }

    @When("^the rate is eliminated$")
    public void theRateIsEliminated() {
        response = rateControllerInstance.deleteRate(propertiesHelper.getValueByKey("jumanjiID"));
    }

    @Then("^the movie has a rate less$")
    public void theMovieHasARateLess() {
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        Assertions.assertThat(jsonHelper.getJsonField(response, "status_code")).isEqualTo("13");
    }

    @Then("^an error of no permissions is prompted$")
    public void anErrorOfNoPermissionsIsPrompted() {
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        Assertions.assertThat(jsonHelper.getJsonField(response, "status_code")).isEqualTo("13");
    }
}

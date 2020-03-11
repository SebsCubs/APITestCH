package steps;

import controllers.MovieRateController;
import controllers.TVEpisodeController;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import utils.JacksonHelper;
import utils.PropertiesHelper;

public class TVEpisodeSteps {
    private Response response;
    private TVEpisodeController rateControllerInstance = new TVEpisodeController();
    private PropertiesHelper propertiesHelper = new PropertiesHelper();
    private JacksonHelper jsonHelper = new JacksonHelper();

    @Given("^the first tv episode of The Simpsons doesn't have a rate from the test account$")
    public void theFirstTvEpisodeOfTheSimpsonsDoesnTHaveARateFromTheTestAccount() {
        response = rateControllerInstance
                .deleteTVEpRate(propertiesHelper.getValueByKey("theSimpsonsID"),1,1);
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        Assertions.assertThat(jsonHelper.getJsonField(response,"status_code")).isEqualTo("13");
    }

    @When("^the episode of The Simpsons is rated with \"([^\"]*)\"$")
    public void theEpisodeOfTheSimpsonsIsRatedWith(String rate)  {
        double rateNum = Double.parseDouble(rate);
        response = rateControllerInstance
                .rateTVEpisode(propertiesHelper.getValueByKey("theSimpsonsID"),rateNum,1,1);
    }

    @Then("^the episode has now a new rate$")
    public void theEpisodeHasNowANewRate() {
        Assertions.assertThat(response.statusCode()).isEqualTo(201);
        Assertions.assertThat(jsonHelper.getJsonField(response,"status_code")).contains("1");
    }

    @When("^the tv episode rate is deleted$")
    public void theTvEpisodeRateIsDeleted() {
        response = rateControllerInstance
                .deleteTVEpRate(propertiesHelper.getValueByKey("theSimpsonsID"),1,1);
    }

    @Then("^the episode has no rate from the test account now$")
    public void theEpisodeHasNoRateFromTheTestAccountNow() {
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        Assertions.assertThat(jsonHelper.getJsonField(response,"status_code")).isEqualTo("13");
    }

    @Given("^the first episode of The Simpsons has a rate from the test account$")
    public void theFirstEpisodeOfTheSimpsonsHasARateFromTheTestAccount() {
        response = rateControllerInstance
                .rateTVEpisode(propertiesHelper.getValueByKey("theSimpsonsID"),7.0,1,1);
        Assertions.assertThat(response.statusCode()).isEqualTo(201);
        Assertions.assertThat(jsonHelper.getJsonField(response,"status_code")).contains("1");
    }

    @Then("^the tv episode rate is updated$")
    public void theTvEpisodeRateIsUpdated() {
        Assertions.assertThat(response.statusCode()).isEqualTo(201);
        Assertions.assertThat(jsonHelper.getJsonField(response,"status_code")).contains("1");
    }

    @Then("^an error is received stating the rate wasn't found$")
    public void anErrorIsReceivedStatingTheRateWasnTFound() {
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        Assertions.assertThat(jsonHelper.getJsonField(response,"status_code")).isEqualTo("13");
    }
}

package steps;

import controllers.TVShowRateController;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import utils.JacksonHelper;
import utils.PropertiesHelper;

public class TVShowSteps {
    private Response response;
    private TVShowRateController rateControllerInstance = new TVShowRateController();
    private PropertiesHelper propertiesHelper = new PropertiesHelper();
    private JacksonHelper jsonHelper = new JacksonHelper();

    @Given("^the tv show doesn't have a rate from the test account$")
    public void theTvShowDoesnTHaveARateFromTheTestAccount() {
        response = rateControllerInstance.deleteRate(propertiesHelper.getValueByKey("theSimpsonsID"));
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        Assertions.assertThat(jsonHelper.getJsonField(response,"status_code")).isEqualTo("13");
    }

    @When("^the show The Simpsons is rated with \"([^\"]*)\"$")
    public void theShowTheSimpsonsIsRatedWith(String rate)  {
        double rateNum = Double.parseDouble(rate);
        response = rateControllerInstance.rateTVShow(propertiesHelper.getValueByKey("theSimpsonsID"),rateNum);
    }

    @Then("^the show has now a new rate$")
    public void theShowHasNowANewRate() {
        Assertions.assertThat(response.statusCode()).isEqualTo(201);
        Assertions.assertThat(jsonHelper.getJsonField(response,"status_code")).contains("1");
    }

    @Given("^the show The Simpsons has a rate from the test account$")
    public void theShowTheSimpsonsHasARateFromTheTestAccount() {
        response = rateControllerInstance.rateTVShow(propertiesHelper.getValueByKey("theSimpsonsID"),7.0);
        Assertions.assertThat(response.statusCode()).isEqualTo(201);
        Assertions.assertThat(jsonHelper.getJsonField(response,"status_code")).contains("1");
    }

    @When("^the tv show rate is deleted$")
    public void theTvShowRateIsDeleted() {
        response = rateControllerInstance.deleteRate(propertiesHelper.getValueByKey("theSimpsonsID"));
    }

    @Then("^the show has no rate from the test account now$")
    public void theShowHasNoRateFromTheTestAccountNow() {
        response = rateControllerInstance.deleteRate(propertiesHelper.getValueByKey("theSimpsonsID"));
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        Assertions.assertThat(jsonHelper.getJsonField(response,"status_code")).isEqualTo("13");
    }

    @Then("^the tv show rate is updated$")
    public void theTvShowRateIsUpdated() {
        Assertions.assertThat(response.statusCode()).isEqualTo(201);
        Assertions.assertThat(jsonHelper.getJsonField(response,"status_code")).contains("1");
    }

    @Then("^an error is received$")
    public void anErrorIsReceived() {
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        Assertions.assertThat(jsonHelper.getJsonField(response,"status_code")).isEqualTo("13");
    }
}

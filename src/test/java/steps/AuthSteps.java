package steps;

import controllers.AuthController;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;


public class AuthSteps {
    AuthController authControllerInstance = new AuthController();
    Response response;

    @Given("^a request token is generated$")
    public void aRequestTokenIsGenerated() {
        response = authControllerInstance.createRequestToken();
    }

    @When("^the request token is validated$")
    public void theRequestTokenIsValidated() {
        response = authControllerInstance.createSessionWithLogin();
    }

    @And("^a request to create the session ID is sent$")
    public void aRequestToCreateTheSessionIDIsSent() {
        response = authControllerInstance.createSession();
    }

    @Then("^a session is opened with a new ID$")
    public void aSessionIsOpenedWithANewID() {

    }
}

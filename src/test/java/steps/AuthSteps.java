package steps;

import controllers.AuthController;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import utils.JacksonHelper;


public class AuthSteps {
    private AuthController authControllerInstance = new AuthController();
    private JacksonHelper jsonHelper = new JacksonHelper();
    private String session_id;
    private Response response;


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
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        Assertions.assertThat(jsonHelper.getJsonField(response, "success")).isEqualTo("true");
    }

    @Given("^a session ID exists$")
    public void aSessionIDExists() {
        response = authControllerInstance.authentication();
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        session_id = jsonHelper.getJsonField(response, "session_id");
    }

    @When("^a request to delete the ID is made$")
    public void aRequestToDeleteTheIDIsMade() {
        response = authControllerInstance.deleteSession(session_id);
    }

    @Then("^the session ID os not usable anymore$")
    public void theSessionIDOsNotUsableAnymore() {
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        Assertions.assertThat(jsonHelper.getJsonField(response, "success")).isEqualTo("true");
    }

    @Given("^user authenticates and generates a session$")
    public void userAuthenticatesAndGeneratesASession() {
        response = authControllerInstance.authentication();
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        session_id = jsonHelper.getJsonField(response, "session_id");
    }

    @And("^the session closes$")
    public void theSessionCloses() {
        response = authControllerInstance.deleteSession(session_id);
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        Assertions.assertThat(jsonHelper.getJsonField(response, "success")).isEqualTo("true");
    }

    @Then("^an answer with an error for resource not found is received$")
    public void anAnswerWithAnErrorForResourceNotFoundIsReceived() {
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        Assertions.assertThat(jsonHelper.getJsonField(response, "status_code")).isEqualTo("6");
        Assertions.assertThat(jsonHelper.getJsonField(response, "status_message")).contains("Invalid id:");
    }
}

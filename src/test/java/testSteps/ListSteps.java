package testSteps;

import controllers.AuthControl;
import controllers.ListsControl;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import helpers.entities.Authorization;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Assert;

import java.util.List;
import java.util.Map;


public class ListSteps {
    private Response response;
    private AuthControl authControlInstance;
    private ListsControl listsControlInstance;
    private Authorization authSession;
    private String list_id;
    private String movie_id;

    @Given("^User authenticates and generates a session$")
    public void userAuthenticatesAndGeneratesASession() {
        authControlInstance = new AuthControl();
        authSession = authControlInstance.authentication();
        Assert.assertThat("Error: The sessionID success status is not true",
                authSession.getSuccess(), Matchers.equalTo("true"));
    }

    @When("^A new list is created with the data$")
    public void aNewListIsCreatedWithTheData(DataTable dataTable) {

        listsControlInstance = new ListsControl();
        List<Map<String,String>> data = dataTable.asMaps(String.class,String.class);
        String name = data.get(0).get("name");
        String description = data.get(0).get("description");
        String language = data.get(0).get("language");

        response = listsControlInstance.createList(authSession.getSession_id(),name,description,language);
        list_id = response.jsonPath().get("list_id").toString();
    }

    @Then("^A list is created and a list_id is generated$")
    public void aListIsCreatedAndAList_idIsGenerated() {
        Assert.assertThat("Error: The create_list success status is not true",
                response.jsonPath().get("success"), Matchers.equalTo(true));
    }

    @And("^The session closes$")
    public void theSessionCloses() {
        response = authControlInstance.deleteSession(authSession.getSession_id());
        Assert.assertThat("Error: The create_list success status is not true",
                response.jsonPath().get("success"), Matchers.equalTo(true));
    }

    @When("^The list is deleted$")
    public void theListIsDeleted() {
        response = listsControlInstance.deleteList(list_id,authSession.getSession_id());
        Assert.assertThat("Error: The delete list status_code is not 11",
                response.jsonPath().get("status_code"), Matchers.equalTo(11));

    }

    @Then("^The list and list_id are not available anymore$")
    public void theListAndList_idAreNotAvailableAnymore() {

        response = listsControlInstance.getDetails(list_id);
        Assert.assertThat("Error: The get details didn't return status 34: Resource not found",
                response.jsonPath().get("status_code"), Matchers.equalTo(34));
    }


    @Given("^A list exists with id \"([^\"]*)\"$")
    public void aListExistsWithId(String list_id) {
        listsControlInstance = new ListsControl();
        response = listsControlInstance.getDetails(list_id);
        this.list_id = list_id;
        Assert.assertThat("Error: The get details didn't return a valid responses: The list doesn't exists",
                response.jsonPath().get("description").toString().isEmpty(), Matchers.equalTo(false));
    }

    @When("^The movie with id \"([^\"]*)\" is added$")
    public void theMovieWithIdIsAdded(String movie_id) {
        response = listsControlInstance.addMovie(list_id,movie_id,authSession.getSession_id());
    }

    @Then("^The list has the movie added and doesn't appear in details$")
    public void theListHasTheMovieAddedAndDoesnTAppearInDetails() {
        Assert.assertThat("Error: the movie didn't get added: something went wrong with the API",
                response.jsonPath().get("status_code"),Matchers.equalTo(12));
    }

    @And("^The list contains the movie with id \"([^\"]*)\"$")
    public void theListContainsTheMovieWithId(String movie_id) {
        response = listsControlInstance.checkItemStatus(list_id,movie_id);
        this.movie_id = movie_id;
        Assert.assertThat("Error: The movie is not in the list",
                response.jsonPath().get("item_present"), Matchers.equalTo(true));
    }

    @When("^The movie is removed from the list$")
    public void theMovieIsRemovedFromTheList() {
        response = listsControlInstance.removeMovie(list_id,movie_id,authSession.getSession_id());
    }

    @Then("^The list doesn't have the movie in details$")
    public void theListDoesnTHaveTheMovieInDetails() {
        response = listsControlInstance.checkItemStatus(list_id,movie_id);
        Assert.assertThat("Error: The movie is not in the list",
                response.jsonPath().get("item_present"), Matchers.equalTo(false));
    }

    @When("^The list is cleared$")
    public void theListIsCleared() {
        response = listsControlInstance.clearList(list_id,authSession.getSession_id());
    }

    @Then("^The list has no items$")
    public void theListHasNoItems() {
        Assert.assertThat("Error: The lists could not be cleared",
                response.jsonPath().get("status_code"),Matchers.equalTo(12));
    }
}

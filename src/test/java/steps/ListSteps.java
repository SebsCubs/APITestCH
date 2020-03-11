package steps;

import controllers.ListsController;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import net.serenitybdd.core.Serenity;
import org.assertj.core.api.Assertions;
import utils.JacksonHelper;
import utils.PropertiesHelper;

import java.util.List;
import java.util.Map;

public class ListSteps {
    private Response response;
    private JacksonHelper jsonHelper = new JacksonHelper();
    private PropertiesHelper propertiesHelper = new PropertiesHelper();
    private ListsController listsControllerInstance = new ListsController();

    @When("^a new list is created with the data$")
    public void aNewListIsCreatedWithTheData(DataTable dataTable) {
        List<Map<String,String>> data = dataTable.asMaps(String.class,String.class);
        String name = data.get(0).get("name");
        String description = data.get(0).get("description");
        String language = data.get(0).get("language");

        response = listsControllerInstance.createList(name,description,language);

    }

    @Then("^a list is created and a list_id is generated$")
    public void aListIsCreatedAndAList_idIsGenerated() {
        Assertions.assertThat(response.statusCode()).isEqualTo(201);
        Assertions.assertThat(jsonHelper.getJsonField(response,"success")).isEqualTo("true");
        response = listsControllerInstance.deleteList(Serenity.sessionVariableCalled("list_id"));
        Assertions.assertThat(response.statusCode()).isEqualTo(500);
    }

    @When("^the list is deleted$")
    public void theListIsDeleted() {
       response = listsControllerInstance.deleteList(Serenity.sessionVariableCalled("list_id"));
    }

    @Then("^the list and list_id are not available anymore$")
    public void theListAndList_idAreNotAvailableAnymore() {
        Assertions.assertThat(response.statusCode()).isEqualTo(500);
        Assertions.assertThat(jsonHelper.getJsonField(response,"status_code")).isEqualTo("11");
        response = listsControllerInstance.getDetails(Serenity.sessionVariableCalled("list_id"));
        Assertions.assertThat(response.statusCode()).isEqualTo(404);
        Assertions.assertThat(jsonHelper.getJsonField(response,"status_message"))
                .isEqualTo("The resource you requested could not be found.");
    }

    @Given("^the list with id \"([^\"]*)\" doesn't exist$")
    public void theListWithIdDoesnTExist(String list_id) {
        response = listsControllerInstance.getDetails(list_id);
        Serenity.setSessionVariable("list_id").to(list_id);
        Assertions.assertThat(response.statusCode()).isEqualTo(404);
    }

    @When("^an attempt to delete the list is made$")
    public void anAttemptToDeleteTheListIsMade() {
        response = listsControllerInstance.deleteList(Serenity.sessionVariableCalled("list_id"));
    }

    @Then("^an answer with a resource not found is received$")
    public void anAnswerWithAResourceNotFoundIsReceived() {
        Assertions.assertThat(response.statusCode()).isEqualTo(404);
    }

    @When("^the movie with id \"([^\"]*)\" is added$")
    public void theMovieWithIdIsAdded(String movie_id){
        response = listsControllerInstance.addMovie(Serenity.sessionVariableCalled("list_id"),movie_id);
        Serenity.setSessionVariable("movie_id").to(movie_id);
    }

    @Then("^the list has the movie added$")
    public void theListHasTheMovieAdded() {
        Assertions.assertThat(response.statusCode()).isEqualTo(500);
        Assertions.assertThat(jsonHelper.getJsonField(response,"status_code")).isEqualTo("11");
        response = listsControllerInstance.checkItemStatus(
                Serenity.sessionVariableCalled("list_id")
                ,Serenity.sessionVariableCalled("movie_id"));
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        Assertions.assertThat(jsonHelper.getJsonField(response,"item_present")).isEqualTo("false");

    }

    @And("^the list contains at least one movie$")
    public void theListContainsAtLeastOneMovie() {
        response = listsControllerInstance.checkItemStatus(
                Serenity.sessionVariableCalled("list_id")
                ,propertiesHelper.getValueByKey("mockMovie"));
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        Assertions.assertThat(jsonHelper.getJsonField(response,"item_present")).isEqualTo("false");
    }

    @When("^the first movie is removed from the list$")
    public void theFirstMovieIsRemovedFromTheList() {
        response = listsControllerInstance.removeMovie(
                Serenity.sessionVariableCalled("list_id")
                ,propertiesHelper.getValueByKey("mockMovie"));
    }

    @Then("^the list doesn't have the movie in details$")
    public void theListDoesnTHaveTheMovieInDetails() {
        Assertions.assertThat(response.statusCode()).isEqualTo(500);
        Assertions.assertThat(jsonHelper.getJsonField(response,"status_code")).isEqualTo("11");
        response = listsControllerInstance.checkItemStatus(Serenity.sessionVariableCalled("list_id")
                ,propertiesHelper.getValueByKey("mockMovie"));
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        Assertions.assertThat(jsonHelper.getJsonField(response,"item_present")).isEqualTo("false");
    }

    @When("^an attempt to add a list already present in the list is made$")
    public void anAttemptToAddAListAlreadyPresentInTheListIsMade() {
        response = listsControllerInstance.addMovie(Serenity.sessionVariableCalled("list_id")
        ,propertiesHelper.getValueByKey("mockMovie"));
    }

    @Then("^an invalid movie message is received$")
    public void anInvalidMovieMessageIsReceived() {
        Assertions.assertThat(response.statusCode()).isEqualTo(500);
        Assertions.assertThat(jsonHelper.getJsonField(response,"status_code")).isEqualTo("11");
    }

    @When("^the list is cleared$")
    public void theListIsCleared() {
        response = listsControllerInstance.clearList(Serenity.sessionVariableCalled("list_id"));

    }

    @Then("^the list has no items$")
    public void theListHasNoItems() {
        response = listsControllerInstance.getDetails(Serenity.sessionVariableCalled("list_id"));
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        Assertions.assertThat(jsonHelper.getJsonField(response,"item_count")).isEqualTo("0");
    }
}

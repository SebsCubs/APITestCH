package steps;

import controllers.CollectionController;
import controllers.TVEpisodeController;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import utils.JacksonHelper;
import utils.PropertiesHelper;

public class CollectionsSteps {

    private Response response;
    private CollectionController collectionController = new CollectionController();
    private PropertiesHelper propertiesHelper = new PropertiesHelper();
    private JacksonHelper jsonHelper = new JacksonHelper();

    @When("^a details request for John Wick's collection is made$")
    public void aDetailsRequestForJohnWickSCollectionIsMade() {
        response = collectionController.getDetails(propertiesHelper.getValueByKey("jhonWickCollection"));
    }

    @Then("^the collection's info is obtained$")
    public void theCollectionSInfoIsObtained() {
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        Assertions.assertThat(jsonHelper.getJsonField(response,"id"))
                .isEqualTo(propertiesHelper.getValueByKey("jhonWickCollection"));
        Assertions.assertThat(jsonHelper.getJsonField(response,"name")).isNotBlank();
    }

    @When("^an images request for John Wick's collection is made$")
    public void anImagesRequestForJohnWickSCollectionIsMade() {
        response = collectionController.getImages(propertiesHelper.getValueByKey("jhonWickCollection"));
    }

    @Then("^the collection's images info is obtained$")
    public void theCollectionSImagesInfoIsObtained() {
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        Assertions.assertThat(jsonHelper.getJsonField(response,"id"))
                .isEqualTo(propertiesHelper.getValueByKey("jhonWickCollection"));
    }

    @When("^a translations request for John Wick's collection is made$")
    public void aTranslationsRequestForJohnWickSCollectionIsMade() {
        response = collectionController.getTranslations(propertiesHelper.getValueByKey("jhonWickCollection"));
    }

    @Then("^the collection's translations are obtained$")
    public void theCollectionSTranslationsAreObtained() {
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        Assertions.assertThat(jsonHelper.getJsonField(response,"id"))
                .isEqualTo(propertiesHelper.getValueByKey("jhonWickCollection"));
        Assertions.assertThat(jsonHelper.getJsonField(response,"translations")).isNotBlank();
    }
}

package helpers;

import utils.PropertiesHelper;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class UrlBuilder {

    private PropertiesHelper propertiesHelper = new PropertiesHelper();
    private List<String> path;
    private List<String> params;


    public UrlBuilder() {
        path = new ArrayList<>();
        params = new ArrayList<>();
    }

    public UrlBuilder addPathStep(String step) {
        path.add(step);
        return this;
    }


    public URL build() {
        String baseUrl = propertiesHelper.getValueByKey("RestAssured.baseURI");
        params.add("api_key=" + System.getenv("TheMovieDBApiKey"));
        try {
            return new URL(baseUrl + "/" + String.join("/", path) + "?" + String.join("&", params));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
package helpers.builders;

import utils.PropertiesManager;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class UrlBuilder {
    private String baseUrl;
    private List<String> path;
    private List<String> params;



    public UrlBuilder(){
        path = new ArrayList<>();
        params = new ArrayList<>();
    }

    public UrlBuilder addPathStep(String step){
        path.add(step);
        return this;
    }

    public UrlBuilder addQueryParam(String param){
        params.add(param);
        return this;
    }

    public URL build(){
        baseUrl = PropertiesManager.getValueByKey("domain");
        params.add("api_key=" + PropertiesManager.getValueByKey("apiKey"));
        try {
            return new URL(baseUrl +"/"+String.join("/", path)+"?"+String.join("&",params));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
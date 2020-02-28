package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import helpers.entities.Authorization;
import io.restassured.response.Response;

public class JsonManager {
    protected static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

    public static String objectToJson(Object object){
        return gson.toJson(object, object.getClass());
    }

    public static Authorization responseToAuthorization(Response response){
        return gson.fromJson(response.body().asString(),Authorization.class);
    }
}

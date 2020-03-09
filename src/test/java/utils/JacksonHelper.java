package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.response.Response;
import org.yecht.Data;

public class JacksonHelper {
    private ObjectNode node;
    private ObjectMapper mapper = new ObjectMapper();

    public JacksonHelper() {}

    public String objectToJson(Object object){
        String jsonString = null;
        try {
            jsonString = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
    public Object jsonToObject(String jsonInput,Object obj){
        try {
            obj = mapper.readValue(jsonInput,Object.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return obj;
    }
    public String getJsonField(Response response, String field){
        try {
            node = new ObjectMapper().readValue(response.getBody().asString(),
                    com.fasterxml.jackson.databind.node.ObjectNode.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        if (node.has(field)){
            return node.get(field).asText();
        }else {
            return "No field with name"+field+"was found";
        }

    }


}

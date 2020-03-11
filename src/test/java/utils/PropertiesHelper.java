package utils;

import java.io.IOException;
import java.util.Properties;

public class PropertiesHelper {
    private String PROPERTIES_FILENAME = "/params.properties";
    private Properties properties;

    public PropertiesHelper() {
    }

    public String getValueByKey(String key) {
        if (properties == null) {
            loadProperties();
        }
        return properties.get(key).toString();
    }


    private void loadProperties() {
        try {
            properties = new Properties();
            properties.load(PropertiesHelper.class.getResourceAsStream(PROPERTIES_FILENAME));
        } catch (IOException e) {
        }
    }
}

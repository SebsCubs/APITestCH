package utils;

import java.io.IOException;
import java.util.Properties;

public class PropertiesManager {
    private static final String PROPERTIES_FILENAME = "/params.properties";
    private static Properties properties;

    private PropertiesManager() {
    }

    public static String getValueByKey(String key) {
        if (properties == null) {
            loadProperties();
        }
        return properties.get(key).toString();
    }



    private static Properties loadProperties() {
        try {
            properties = new Properties();
            properties.load(PropertiesManager.class.getResourceAsStream(PROPERTIES_FILENAME));
        } catch (IOException e) {
        }
        return properties;
    }
}

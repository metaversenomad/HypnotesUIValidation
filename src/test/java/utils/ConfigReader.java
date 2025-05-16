// src/test/java/utils/ConfigReader.java
package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static final Properties properties = new Properties();

    static {
        try (FileInputStream file = new FileInputStream("src/test/resources/configuration.properties")) {
            properties.load(file);
        } catch (IOException e) {
            System.err.println("Configuration file not found or could not be loaded!");
            e.printStackTrace();
        }
    }

    /**
     * Returns the property value for the given key.
     *
     * @param key property name in configuration.properties
     * @return corresponding property value, or null if missing
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}

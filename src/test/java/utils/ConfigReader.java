package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties;

    static {
        try {
            // Configuration dosyasını oku
            FileInputStream file = new FileInputStream("src/test/resources/configuration.properties");
            properties = new Properties();
            properties.load(file);
            file.close();
        } catch (IOException e) {
            System.out.println("Configuration file not found!");
            e.printStackTrace();
        }
    }

    // Property değerini döndürür
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    // browser anahtarını alır ve uygun değeri döndürür
    public static String getConfigProperty(String key) {
        return properties.getProperty(key);
    }
}

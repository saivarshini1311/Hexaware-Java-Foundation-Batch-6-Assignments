package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBPropertyUtil {
    public static String getPropertyString(String fileName) {
        Properties properties = new Properties();
        String connectionString;

        try (FileInputStream input = new FileInputStream(fileName)) {
            properties.load(input);
            
            String url = properties.getProperty("jdbc.url");
            String username = properties.getProperty("jdbc.username");
            String password = properties.getProperty("jdbc.password");

            
            connectionString = url + "?user=" + username + "&password=" + password;
        } catch (IOException e) {
            e.printStackTrace();
            return null;  
        }

        return connectionString; 
    }
}

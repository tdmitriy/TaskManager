package database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class PropertiesReader {
    private static final String PROP_PATH = "/database.properties";
    private static final Logger log = LoggerFactory.getLogger(PropertiesReader.class);

    private static Properties prop;

    static {
        prop = new Properties();
        try {
            prop.load(PropertiesReader.class.getResourceAsStream(PROP_PATH));
            log.debug("db properties loaded.");
        } catch (Exception e) {
            log.error("Failed to load db properties.\n" + e.getMessage());
        }
    }

    private PropertiesReader() {

    }

    public static String getUser() {
        return prop.getProperty("jdbc.user");
    }

    public static String getPassword() {
        return prop.getProperty("jdbc.password");
    }

    public static String getConnectionString() {
        return prop.getProperty("jdbc.url");
    }
}

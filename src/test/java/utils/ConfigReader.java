package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static Properties props = new Properties();

    // Load properties when the class is first referenced
    static {
        loadConfig();
    }

    public static void loadConfig()
    {
        InputStream is = null;
        try {
            // Try loading from classpath (works when running tests via Maven/IDE)
            is = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties");
            if (is == null) {
                // Fallback to the source resources path (useful during IDE runs)
                is = new FileInputStream("src/test/resources/config.properties");
            }

            props.load(is);
        }
        catch(IOException e)
        {
            throw new RuntimeException("Could not load config file",e);
        }
        finally {
            if (is != null) {
                try { is.close(); } catch (IOException ignored) {}
            }
        }

    }
    public static String getProperty(String key) 
    {
        return props.getProperty(key);
    }


}
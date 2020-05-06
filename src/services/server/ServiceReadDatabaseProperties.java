/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.server;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 *
 * @author Veljko
 */
public class ServiceReadDatabaseProperties {

    private final Properties properties;
    private final String PROPERTIES_FILE_PATH="database.properties";
    private final String FILE_OUTPUT_STREAM="src/services/server/database.properties";
    private final Logger logger = Logger.getLogger(ServiceReadDatabaseProperties.class);

    public ServiceReadDatabaseProperties() {
        this.properties = new Properties();
        try {
            properties.load(getClass().getResourceAsStream(PROPERTIES_FILE_PATH));
        } catch (IOException ex) {
           logger.error(ex.getMessage());
        }
    }

    public String readUrl() {
        return properties.getProperty("url");
    }

    public String readUsername() {
        return properties.getProperty("username");
    }

    public String readPassword() {
        return properties.getProperty("password");
    }

    public void writeUrl(String url) {
         try (OutputStream outputStream = new FileOutputStream(FILE_OUTPUT_STREAM)) {
            properties.setProperty("url", url);
            properties.store(outputStream, null);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void writePassword(String password) {
        try (OutputStream outputStream = new FileOutputStream(FILE_OUTPUT_STREAM)) {
            properties.setProperty("password", password);
            properties.store(outputStream, null);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void writeUsername(String username) {
       try (OutputStream outputStream = new FileOutputStream(FILE_OUTPUT_STREAM)) {
            properties.setProperty("username", username);
            properties.store(outputStream, null);
        } catch (IOException e) {
             logger.error(e.getMessage());
        }
    }
}

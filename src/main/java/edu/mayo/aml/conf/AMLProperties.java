package edu.mayo.aml.conf;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by dks02 on 12/7/15.
 */
public class AMLProperties
{
    final static Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(AMLProperties.class);

    // Main Property File
    public static String AML_PROPERTIES_FILE_NAME = "AMLWorks.properties";

    private Properties properties = new Properties();
    private InputStream inputStream = null;

    public AMLProperties()
    {
        try
        {
            String propFileName = AMLProperties.AML_PROPERTIES_FILE_NAME;

            inputStream = new FileInputStream(propFileName);

            if (inputStream != null)
            {
                properties.load(inputStream);
            }
            else
            {
                throw new FileNotFoundException("Input Stream is null!");
            }

        } catch (Exception e)
        {
            logger.error("Failed to initialize AML properties!",  e);
        }
    }

    public void reinitialize(InputStream newInputStream)
    {
        try
        {
            if (newInputStream != null)
            {
                properties.clear();
                properties.load(newInputStream);
            }
            else
            {
                throw new FileNotFoundException("Input Stream is null!");
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            logger.error("Failed to re-initialize properties!",  e);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            logger.error("Failed to re-initialize properties!",  e);
        }
    }

    public String getPropertyValue(String propertyName)
    {
        if ((properties == null)||(properties.size() == 0))
        {
            logger.warn("No Property registered!");
            return null;
        }

        if (StringUtils.isEmpty(propertyName))
        {
            logger.warn("Property key is empty!");
            return null;
        }

        return properties.getProperty(propertyName);
    }
}

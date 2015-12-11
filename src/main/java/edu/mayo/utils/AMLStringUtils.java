package edu.mayo.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class has some utility methods for Java Strings,
 * OS related and others misc. purposes
 * @author Deepak Sharma
 */

public class AMLStringUtils
{
    /**
     * Adds a new line character at the end
     * @param str input string
     * @return input string concatenated with a new line character
     * if input string is not empty. Otherwise returns the string as it is.
     */
    public static final String addNL(String str)
    {
        if (!StringUtils.isEmpty(str))
            str += "\n";
        return str;
    }

    /**
     * Returns an empty string "" if input string is empty or null.
     * @param str any string
     * @return return "" if input string is null or empty
     */
    public static String replaceNullWithEmpty(String str)
    {
        if (str == null)
            return "";
        return str;
    }

    /**
     * Check condition checks for regular expression match for input string
     * @param input input string
     * @param regex regular expression
     * @return Matcher Object after applying regex.
     * @throws Exception related to regex matching (java.util.regex)
     */
    public static Matcher match(String input, String regex) throws Exception
    {
        if (StringUtils.isEmpty(regex))
            return null;

        Pattern r = Pattern.compile(regex);
        Matcher m = r.matcher(input);

        return m;
    }

    /**
     * Enumeration for Operating Systems types
     */
    public static enum OS { WINDOWS, LINUX, MAC, OTHER};

    /**
     * Returns enumeration literal for Operating System Type
     * @return Operating System's type - WINDOWS, LINUX, MAC or OTHER
     */
    public static OS getOsName()
    {
        if (System.getProperty("os.name").toLowerCase().indexOf("windows") > -1)
            return OS.WINDOWS;
        if (System.getProperty("os.name").toLowerCase().indexOf("linux") > -1)
            return OS.LINUX;
        if (System.getProperty("os.name").toLowerCase().indexOf("mac") > -1)
            return OS.MAC;

        return OS.OTHER;
    }
}

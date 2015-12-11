package edu.mayo.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Matcher;

/**
 * This class is used as a default File Filter for AMLClassPathManager
 * @author Deepak Sharma
 */

public class AMLFileFilter implements FileFilter
{
    public String regex_ = null;

    public AMLFileFilter(String regex)
    {
        super();
        this.regex_ = regex;
    }
    /**
     * Returns acceptance of the file, if it is a directory
     * @param f File
     * @return true if f is a directory.
     */
	public boolean accept(File f)
	{
		return accept(f, regex_);
	}

    /**
     * Returns acceptance of a file - either it is a directory or matches file name with regular expression
     * @param f  File
     * @param regex regular expression
     * @return true, if f is a directory of its name (getName()) matches regex
     */
    public boolean accept(File f, String regex)
    {
        if (f == null)
            return false;

        if (StringUtils.isEmpty(regex))
            return f.isDirectory();
        else
        {
            try
            {
                boolean matched = regex.equals(f.getName());
                if (matched)
                    return true;

                Matcher m = AMLStringUtils.match(f.getName(), regex);
                matched = ((m == null)? false : (m.find()));
                return (f.isDirectory() || matched);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return false;
            }
        }
    }


	public String getDescription()
	{
		return "This filter compares the file with a given file name regex.";
	}
}

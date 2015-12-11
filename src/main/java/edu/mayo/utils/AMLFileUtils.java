package edu.mayo.utils;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dks02 on 6/15/15.
 */
public class AMLFileUtils
{
    public static void setContents(File aFile, String aContents, boolean append)
            throws FileNotFoundException, IOException
    {
        if (aFile == null)
        {
            throw new IllegalArgumentException("File should not be null.");
        }
        if (!aFile.exists())
        {
            throw new FileNotFoundException ("File does not exist: " + aFile);
        }
        if (!aFile.isFile())
        {
            throw new IllegalArgumentException("Should not be a directory: " + aFile);
        }

        if (!aFile.canWrite())
        {
            throw new IllegalArgumentException("File cannot be written: " + aFile);
        }

        //use buffering
        Writer output = new BufferedWriter(new FileWriter(aFile));
        try
        {
            //FileWriter always assumes default encoding is OK!
            if (append)
                output.append(aContents);
            else
                output.write( aContents );

            output.flush();
        }
        finally
        {
            output.close();
        }
    }

    /**
     * Returns Files (list of file objects) for the matching files in a given directory
     * @param dir
     * @param fileNameRegex
     * @return
     */
    public static List<File> getAllFiles(String dir, String fileNameRegex)
    {
        String defaultRegex = ".\\.*";

        List<File> inputFiles = new ArrayList<File>();
        AMLFileFilter lmff = new AMLFileFilter((fileNameRegex == null) ? defaultRegex : fileNameRegex);

        try
        {
            AMLClassPathManager.scan(new File(dir), inputFiles, lmff);
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
        }

        return inputFiles;
    }

    /**
     * Returns all Files in current directory
     * @param fileNameRegex
     * @return
     */
    public static List<File> getAllFiles(String fileNameRegex)
    {
        return AMLFileUtils.getAllFiles(".", fileNameRegex);
    }

    /**
     * Returns File from its URL
     * @param fileUrl
     * @return
     */
    public static String getURLContents(URL fileUrl) throws IOException
    {
        InputStream input = fileUrl.openStream();
        try
        {
            return IOUtils.toString( input ) ;
        } finally
        {
            IOUtils.closeQuietly(input);
        }
    }
}

package edu.mayo.utils;


import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * This class has some utility methods to find and add files to classpath
 * and getting and setting content of the files.
 * @author Deepak Sharma
 */


public class AMLClassPathManager
{
    /**
     * Searches Files is a given directory and adds them to class path
     * @param startDirectory  directory to start searching for the files for a given name/pattern, Default: current directory "."
     * @param fileNameToMatch Match pattern
     * @param fileFilter  File Filter
     * @return
     */
    public static void findItAndAddToClassPath(File startDirectory,
                                          String fileNameToMatch,
                                          FileFilter fileFilter)
            throws NullPointerException, IOException {
        if (StringUtils.isEmpty(fileNameToMatch))
             throw new NullPointerException("File Name or Pattern cannot be empty!");

        FileFilter filter_ = fileFilter;
        if (filter_ == null)
            filter_ = new AMLFileFilter(fileNameToMatch);

        File[] paths = File.listRoots();
        if (startDirectory == null)
        {
            startDirectory = new File(".");
            paths = new File[]{startDirectory};
        }
        else
            paths = startDirectory.listFiles();
			     
        List<File> list = new ArrayList<File>();
        for (File path : paths)
        {
            scan(path, list, filter_);
            for (File file : list)
                addURL(file.toURI());
        }
    }

    /**
     * Recursively scan for files and add filtered ones to the list.
     * @param path Path to look into
     * @param list list of filtered files
     * @param filter  filter to apply
     * @throws IOException
     */
    public static void scan(File path, List<File> list, FileFilter filter) throws IOException
    {
        // Get filtered files in the current path
        File[] files = path.listFiles(filter);

        if (files == null)
            return;

        // Process each filtered entry
        for (File file : files)
        {
            //System.out.println("name=" + file.getName());
            // recursive - if the entry is a directory
            if (file.isDirectory())
            {
                scan(file,list,filter);
            }
            else
            {
                // add the filtered file to the list
                list.add(file);
            }
        } // for
    } // scan

    /**
     * Adds a path to the classpath using the class-loader
     * @param uri
     * @throws IOException
     */
    public static void addURL(URI uri) throws IOException
    {

        URLClassLoader systemClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        Class<URLClassLoader> urlClassLoaderClass = URLClassLoader.class;

        try
        {
            Method method = urlClassLoaderClass.getDeclaredMethod("addURL", new Class[] {URL.class});
            method.setAccessible(true);
            method.invoke(systemClassLoader, new Object[] {uri.toURL()});
        }
        catch (Throwable throwable)
        {
            throwable.printStackTrace();
            throw new IOException("Error, could not add URL to system class loader");
        }
    }
}

package edu.mayo.aml.common;

import org.eclipse.emf.common.util.URI;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.*;
import org.eclipse.uml2.uml.Package;
import org.openhealthtools.mdht.uml.common.search.ModelSearch;

import java.io.File;
import java.util.List;

/**
 * Created by dks02 on 12/15/15.
 */
public class ReferenceModel extends UMLModel
{
    public ReferenceModel(File file)
    {
        super(file);
    }

    public ReferenceModel(URI uri)
    {
        super(uri);
    }

    public ReferenceModel(String uriPath)
    {
        super(uriPath);
    }

    public Model getModel()
    {
        if (this.getResource() == null)
            return null;

        return (Model) this.getRootPackage();
    }

    public void printResource()
    {
        if (this.getModel() == null)
        {
            logger_.info("WARNING: Model is null or empty!");
            return;
        }

        logger_.info("Printing Resource ...");
        logger_.info("*****         BEGIN        *********");
        logger_.info("" + this.getModel().getName());
        logger_.info("**************");

        List<Element> allPackages = ModelSearch.findAllOf(this.getModel(), org.eclipse.uml2.uml.Package.class);
        for (Element element : allPackages)
        {
            Package pkg = (Package) element;
            logger_.info("Package: " + pkg.getName());
            List<Element> allClasses = ModelSearch.findAllOf(pkg, org.eclipse.uml2.uml.Class.class);
            for (Element clazz : allClasses)
            {
                if (clazz instanceof Class)
                {
                    Class cls = (Class) clazz;
                    logger_.info("\tClass: " + cls.getName());

                    for (Property attrib : cls.getOwnedAttributes())
                    {
                        logger_.info("\t\tProperty: "+ attrib.getVisibility().getName() + " " +
                                attrib.getType().getName() + ":" +
                                attrib.getName() + "[" + attrib.getLower() + ".." + attrib.getUpper() + "]");
                    }
                }
            }
        }
        logger_.info("*****         END        *********\n");
    }
}

package edu.mayo.aml.common;

import org.eclipse.emf.common.util.URI;
import org.eclipse.uml2.uml.*;
import org.openhealthtools.mdht.uml.common.search.ModelSearch;

import java.io.File;
import java.util.List;

/**
 * Created by dks02 on 12/15/15.
 */
public class AMLProfile extends UMLModel
{
    public AMLProfile(File file)
    {
        super(file);
    }

    public AMLProfile(URI uri)
    {
        super(uri);
    }

    public AMLProfile(String uriPath)
    {
        super(uriPath);
    }

    public Profile getProfile()
    {
        if (this.getResource() == null)
            return null;

        return (Profile) this.getRootPackage();
    }

    public void printResource()
    {
        if (this.getProfile() == null)
        {
            logger_.info("WARNING: Model is null or empty!");
            return;
        }

        logger_.info("Printing Resource ...");
        logger_.info("*****         BEGIN        *********");
        logger_.info("" + this.getProfile().getName());
        logger_.info("**************");

        List<Element> allPackages = ModelSearch.findAllOf(this.getResource(), org.eclipse.uml2.uml.Profile.class);
        for (Element element : allPackages)
        {
            org.eclipse.uml2.uml.Profile prof = (Profile) element;
            logger_.info("Profile: " + prof.getName());
            List<Element> allStereotypes = ModelSearch.findAllOf(prof, org.eclipse.uml2.uml.Stereotype.class);

            for (Element stereo : allStereotypes)
            {
                if (stereo instanceof Stereotype)
                {
                    Stereotype stereotype = (Stereotype) stereo;
                    logger_.info("\tStereotype: " + stereotype.getName());
                    List<org.eclipse.uml2.uml.Class> stypes = stereotype.getExtendedMetaclasses();

                    for (org.eclipse.uml2.uml.Class scl : stypes )
                        logger_.info("\t\tExtended MetaClass:" + scl.getName());
                    for (Property attrib : stereotype.getOwnedAttributes())
                    {
                        logger_.info("\t\tProperty: "+ attrib.getVisibility().getName() + " " +
                                attrib.getType().getName() + ":" +
                                attrib.getName() + "[" + attrib.getLower() + ".." + attrib.getUpper() + "]");
                    }

                    List<Constraint> constraints = stereotype.getOwnedRules();
                    for (Constraint con : constraints)
                    {
                        logger_.info("\t\tConstraint: "+ con.getVisibility().getName() + ": " + con.getName());
                    }
                }
            }
        }
        logger_.info("*****         END        *********\n");
    }

}

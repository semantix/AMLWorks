package edu.mayo.utils;

import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.*;
import org.eclipse.uml2.uml.Package;
import org.eclipse.mdht.uml.common.search.ModelSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by dks02 on 12/22/15.
 */
public class AMLPrintUtils
{
    final static Logger logger_ = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(AMLPrintUtils.class);

    public static void printUMLModel(Model model)
    {
        if (model == null)
        {
            logger_.info("WARNING: Model is null or empty!");
            return;
        }

        logger_.info("Printing Resource ...");
        logger_.info("*****         BEGIN        *********");
        logger_.info("" + model.getName());
        logger_.info("**************");

        List<Element> allPackages = ModelSearch.findAllOf(model, org.eclipse.uml2.uml.Package.class);
        for (Element element : allPackages)
        {
            Package pkg = (Package) element;
            logger_.info("Package: " + pkg.getName());
            List<Element> allClasses = ModelSearch.findAllOf(pkg, Class.class);
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

    public static void printUMLProfile(Profile profile)
    {
        if (profile == null)
        {
            logger_.info("WARNING: Profile is null or empty!");
            return;
        }

        logger_.info("Printing Resource ...");
        logger_.info("*****         BEGIN        *********");
        logger_.info("" + profile.getName());
        logger_.info("**************");

        List<Element> allPackages = ModelSearch.findAllOf(profile.eResource(), org.eclipse.uml2.uml.Profile.class);
        for (Element element : allPackages)
        {
            boolean imported = element instanceof PackageImport;

            String importedPkgTag = (imported) ? "[Package Import] " : "";

            org.eclipse.uml2.uml.Profile prof = (Profile) element;
            logger_.info(importedPkgTag + "Profile: " + prof.getName());

            if (imported)
                continue;

            List<Element> allStereotypes = ModelSearch.findAllOf(prof.eResource(), Stereotype.class);

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

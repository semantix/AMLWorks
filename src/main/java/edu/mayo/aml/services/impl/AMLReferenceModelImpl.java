package edu.mayo.aml.services.impl;

import edu.mayo.aml.common.UMLModel;
import edu.mayo.aml.conf.AMLEnvironment;
import edu.mayo.aml.services.AMLReferenceModel;
import edu.mayo.aml.services.ReferenceModelStatus;
import edu.mayo.utils.AMLPrintUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.mdht.uml.common.search.ModelSearch;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.*;
import org.eclipse.uml2.uml.Package;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * Created by dks02 on 12/15/15.
 */
public class AMLReferenceModelImpl extends UMLModel implements AMLReferenceModel
{
    final static Logger logger_ = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(AMLReferenceModelImpl.class);

    private String modelName_ = null;
    private ReferenceModelStatus status_ = ReferenceModelStatus.UNKNOWN;

    private String ns_ = null;
    private String publisher_ = null;
    private String package_ = null;
    private String version_ = null;
    private String desc_ = null;

    private Profile referenceModelProfile_ = null;
    private Package referenceModelPackage_ = null;

    private HashMap<String, Package> allPackages_ = new HashMap<String, Package>();
    private HashMap<String, Class> allClasses_ = new HashMap<String, Class>();
    private HashMap<String, Property> allProperties_ = new HashMap<String, Property>();

    public AMLReferenceModelImpl(String name, File file, Profile referenceModelProfile)
    {
        super(file);
        populate(name, referenceModelProfile);
    }

    public AMLReferenceModelImpl(String name, URI uri, Profile referenceModelProfile)
    {
        super(uri);
        populate(name, referenceModelProfile);
    }

    public AMLReferenceModelImpl(String name, String uriPath, Profile referenceModelProfile)
    {
        super(uriPath);
        populate(name, referenceModelProfile);
    }

    public Model getUMLModel()
    {
        if (this.getResource() == null)
            return null;

        return (Model) this.getRootPackage();
    }

    // Register Addtional maps to resolve references to external
    // resources like Profiles.
    private void registerMaps()
    {
        // Location of the Reference Model Profile (that is applied on a Reference Model)
        // It is important to register it with the model so that we can find out the
        // Profile and its application on the Reference Model.
        URI rmpPathMapURI = URI.createURI(AMLEnvironment.getProfileUriPathMap(AMLEnvironment.AML_RMP_KEY));
        URI rmpPathURI = URI.createURI(AMLEnvironment.getProfileUriPath(AMLEnvironment.AML_RMP_KEY));

        if (rmpPathMapURI == null)
            return;

        if (rmpPathURI == null)
            return;

        this.getResourceSet().getURIConverter().getURIMap().put(rmpPathMapURI, rmpPathURI);
    }

    public void setStatus(ReferenceModelStatus status)
    {
        this.status_ = status;
    }

    public ReferenceModelStatus getStatus()
    {
        return this.status_;
    }

    public boolean isDefaultReferenceModel()
    {
        return AMLEnvironment.isModelDefault(this.modelName_);
    }

    public String getName()
    {
        return this.modelName_;
    }

    public void setName(String name)
    {
        try
        {
            this.modelName_ = (StringUtils.isEmpty(name) ? this.uri_.lastSegment() : name);
        }
        catch(Exception e) {}
    }

    public String getModelDescription()
    {
        if (desc_ == null)
            desc_ = AMLEnvironment.getModelDescription(this.modelName_);

        if (desc_ == null)
            setModelDescription(this.modelName_);

        return desc_;
    }

    public void setModelDescription(String desc)
    {
        desc_ = desc;
    }

    public String getModelVersion()
    {
        if (version_ == null)
            version_ = AMLEnvironment.getModelVersion(this.modelName_);

        if (version_ == null)
            version_ = "Unknown";

        return version_;
    }

    public void setModelVersion(String version)
    {
        version_ = version;
    }

    public String getModelNamespace()
    {
        if (ns_ == null)
            ns_ = AMLEnvironment.getModelNameSpace(this.modelName_);

        if (ns_ == null)
            ns_ = "";

        return ns_;
    }

    public void setModelNamespace(String ns)
    {
        ns_ = ns;
    }

    public String getModelPublisher()
    {
        if (publisher_ == null)
            publisher_ = AMLEnvironment.getModelPublisher(this.modelName_);

        if (publisher_ == null)
            publisher_ = "";

        return publisher_;
    }

    public void setModelPublisher(String publisher)
    {
        publisher_ = publisher;
    }

    public String getModelPackage()
    {
        if (package_ == null)
            package_ = AMLEnvironment.getModelPackage(this.modelName_);

        if (package_ == null)
            package_ = "";

        return package_;
    }

    public void setModelPackage(String modelPackage)
    {
        package_ = modelPackage;
    }

    public void populate(String name, Profile profile)
    {
        setName(name);

        // this registration is crucial resolving profile or other
        // external resources
        registerMaps();

        this.referenceModelProfile_ = profile;

        if (this.getUMLModel() == null)
        {
            logger_.warn("WARNING: Root Model Element is null! RM is not available for use.");
            return;
        }

        logger_.info("Reading UML Model:" + this.getUMLModel().getName());

        List<Element> allPackages = ModelSearch.findAllOf(this.getUMLModel(), Package.class);
        for (Element element : allPackages)
        {
            Package pkg = (Package) element;
            //logger_.info("Package: " + pkg.getName());
            this.allPackages_.put(pkg.getQualifiedName(), pkg);
            List<Element> allClasses = ModelSearch.findAllOf(pkg, Class.class);
            for (Element clazz : allClasses)
            {
                if (clazz instanceof Class)
                {
                    Class cls = (Class) clazz;
                    //logger_.info("\tClass: " + cls.getName());
                    this.allClasses_.put(cls.getQualifiedName(), cls);
                    for (Property attrib : cls.getOwnedAttributes())
                    {
                        //logger_.info("\t\tProperty: "+ attrib.getVisibility().getName() + " " +
                        //        attrib.getType().getName() + ":" +
                        //        attrib.getName() + "[" + attrib.getLower() + ".." + attrib.getUpper() + "]");
                        this.allProperties_.put(attrib.getQualifiedName(), attrib);
                    }
                }
            }
        }
        int packagesCount = allPackages_.keySet().size();
        int classesCount = allClasses_.keySet().size();
        int propertiesCount = allProperties_.keySet().size();

        logger_.info("Reference Model read. TOTAL (Packages:" + packagesCount +
                    "), (Classes:" + classesCount +
                    "), (Properties:" + propertiesCount + ").");

        this.referenceModelPackage_ = getReferenceModelPackage();

        logger_.info("Reference Model Name:" + this.modelName_);
        logger_.info("Reference Model Version:" + getModelVersion());
        logger_.info("Reference Model Namespace:" + getModelNamespace());
        logger_.info("Reference Model Package:" + getModelPackage());
        logger_.info("Reference Model Publisher:" + getModelPublisher());
    }

    private Package getReferenceModelPackage()
    {
        String referenceModelURI = AMLEnvironment.getProfileRegisteredUri(AMLEnvironment.AML_RMP_KEY);

        if (StringUtils.isEmpty(referenceModelURI))
            return null;

        Model model = (Model) this.getRootPackage();

        boolean applied = false;

        List<Profile> profiles = model.getAllAppliedProfiles();
        for (Profile profile : profiles)
            if (referenceModelURI.equals(profile.getURI()))
                applied = true;

        if (!applied)
            return null;

        EList<Stereotype> modelStereotypes = model.getAppliedStereotypes();

        Stereotype refrenceModelStereotype = referenceModelProfile_.getOwnedStereotype("ReferenceModel");

        for (Package pkg : allPackages_.values())
        {
            if (pkg.getName().equals("CIMI Reference Model"))
            {
                List<Stereotype> stereotypes = pkg.getAppliedStereotypes();

                List<EObject> stereoApps = ModelSearch.findStereotypeApplications(pkg.eResource(), refrenceModelStereotype);



                if (stereotypes.isEmpty())
                {

                }
            }

            if (!applied)
                logger_.info("No Stereotype");
            else
                logger_.info("Stereotypes:" + pkg.getAppliedStereotypes());
        }

        return null;
    }

    public void print()
    {
        AMLPrintUtils.printUMLModel(getUMLModel());
    }

    public String toString()
    {
        return "(name:\"" + this.modelName_ + "\"" +
                " <Description:" + getModelDescription() +
                ">, <RMStatus:" + this.status_ +
                ">, <Default:" + isDefaultReferenceModel() +
                ">)[" + this.uri_ + "]";
    }


}

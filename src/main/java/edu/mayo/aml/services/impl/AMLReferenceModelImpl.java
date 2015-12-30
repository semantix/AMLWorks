package edu.mayo.aml.services.impl;

import edu.mayo.aml.common.UMLModel;
import edu.mayo.aml.conf.AMLEnvironment;
import edu.mayo.aml.services.AMLReferenceModel;
import edu.mayo.utils.AMLPrintUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.mdht.uml.aml.refmodel.ReferenceModel;
import org.eclipse.mdht.uml.aml.refmodel.util.RefModelSwitch;
import org.eclipse.mdht.uml.common.search.ModelSearch;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.*;
import org.eclipse.uml2.uml.Package;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;

/**
 * Created by dks02 on 12/15/15.
 */
public class AMLReferenceModelImpl extends UMLModel implements AMLReferenceModel
{
    final static Logger logger_ = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(AMLReferenceModelImpl.class);

    private HashMap<String, Package> allPackages_ = new HashMap<String, Package>();
    private HashMap<String, Class> allClasses_ = new HashMap<String, Class>();
    private HashMap<String, Property> allProperties_ = new HashMap<String, Property>();

    private String rmNameKey_ = null;
    private boolean isReady_ = false;

    private ReferenceModel referenceModelPackage_ = null;

    public AMLReferenceModelImpl(URI uri)
    {
        super(uri);
        populate();
    }

    public boolean isRMAvailable()
    {
        return this.isReady_;
    }

    public Model getUMLModel()
    {
        if (this.getResource() == null)
            return null;

        Model mdl = (Model) this.getRootPackage();

        return mdl;
    }

    public String getDescription()
    {
        if (!isRMAvailable())
            return null;
        return null;
    }

    public String getName()
    {
        if (!isRMAvailable())
            return null;

        return getUMLModel().getName();
    }

    public String getRMPackageName()
    {
        if (!isRMAvailable())
            return null;

        return this.referenceModelPackage_.getBase_Package().getName();
    }

    public String getRMNamespace()
    {
        if (!isRMAvailable())
            return null;

        return this.referenceModelPackage_.getRmNamespace();
    }

    public String getRMPublisher()
    {
        if (!isRMAvailable())
            return null;

        return this.referenceModelPackage_.getRmPublisher();
    }

    public String getRMVersion()
    {
        if (!isRMAvailable())
            return null;

        return this.referenceModelPackage_.getRmVersion();
    }

    public String getModelDescription()
    {
        return getName();
    }

    private void populate()
    {
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

        this.referenceModelPackage_ = getReferenceModelPackage();

        if (this.referenceModelPackage_ != null)
            isReady_ = true;

        logger_.info("Reference Model Name:" + getName());
        logger_.info("Reference Model Version:" + getRMVersion());
        logger_.info("Reference Model Namespace:" + getRMNamespace());
        logger_.info("Reference Model Package:" + getRMPackageName());
        logger_.info("Reference Model Publisher:" + getRMPublisher());

        logger_.info("Reference Model read. TOTAL (Packages:" + packagesCount +
                "), (Classes:" + classesCount +
                "), (Properties:" + propertiesCount + ").");
    }

    private ReferenceModel getReferenceModelPackage()
    {
        String referenceModelURI = AMLEnvironment.getProfileRegisteredUri(AMLEnvironment.AML_RMP_KEY);

        if (StringUtils.isEmpty(referenceModelURI))
            return null;

        Model model = (Model) this.getRootPackage();

        //List<Profile> profiles = model.getAllAppliedProfiles();
        //this.referenceModelProfile_ = profiles.get(0);

        //if (this.referenceModelProfile_ == null)
        //    return null;

        //final Stereotype refrenceModelStereotype = referenceModelProfile_.getOwnedStereotype("ReferenceModel");

        RefModelSwitch refSwitch = new RefModelSwitch()
        {
            @Override
            public Object caseReferenceModel(ReferenceModel refModel)
            {
                logger_.info("\tReferenceModel Package:" + refModel.getBase_Package().getName());
                logger_.info("\tReferenceModel NS:" + refModel.getRmNamespace());
                logger_.info("\tReferenceModel Publisher:" + refModel.getRmPublisher());
                logger_.info("\tReferenceModel Version:" + refModel.getRmVersion());

                return refModel;
            }

            /*
            @Override
            public Object caseMappedDataType(MappedDataType object)
            {
                EList<NamedElement> clients = object.getBase_Abstraction().getClients();
                logger_.info("\tMapped DataType Abstraction Client:" + clients.size());
                for (NamedElement ne : clients)
                    logger_.info("\t\tClient:" + ne.getName());
                return super.caseMappedDataType(object);
            }

            @Override
            public Object caseInfrastructure(Infrastructure object)
            {
                logger_.info("\tInfrastructure Property:" + object.getBase_Property().getName());
                return super.caseInfrastructure(object);
            }

            @Override
            public Object caseRuntime(org.eclipse.mdht.uml.aml.refmodel.Runtime object)
            {
                logger_.info("\tRuntime Property:" + object.getBase_Property().getName());
                return super.caseRuntime(object);
            }
            */
        };

        ReferenceModel referenceModel = null;
        TreeIterator trit = EcoreUtil.getAllProperContents(this.getResource(), true);
        while ((referenceModel == null) && (trit.hasNext()))
            referenceModel = (ReferenceModel) refSwitch.doSwitch((EObject) trit.next());

        return referenceModel;
    }

    public void print()
    {
        AMLPrintUtils.printUMLModel(getUMLModel());
    }

    public String toString()
    {
        return "(name:\"" + getName() + "\"" +
                " <Description:" + getModelDescription() +
                ">)[" + this.uri_ + "]";
    }


}

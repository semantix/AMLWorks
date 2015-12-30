package edu.mayo.aml.common;

import edu.mayo.aml.conf.AMLEnvironment;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xml.type.impl.AnyTypeImpl;
import org.eclipse.mdht.uml.aml.constraint.ConstraintPackage;
import org.eclipse.mdht.uml.aml.refmodel.RefModelPackage;
import org.eclipse.mdht.uml.aml.terminology.TerminologyPackage;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.eclipse.uml2.uml.resource.XMI212UMLResource;
import org.eclipse.uml2.uml.resources.util.UMLResourcesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by dks02 on 12/11/15.
 */
public class UMLModel
{
    final static Logger logger_ = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(UMLModel.class);

    protected URI uri_ = null;

    private ResourceSet resourceSet_ = new ResourceSetImpl();
    private Resource resource_ = null;

    public UMLModel(File file)
    {
        if (file == null)
            return;

        this.uri_ = URI.createURI(file.getAbsolutePath());
        init();
    }

    public UMLModel(URI uri)
    {
        this.uri_ = uri;
        init();
    }

    public UMLModel(String uriPath)
    {
        if (uriPath == null)
            return;
        this.uri_ = URI.createFileURI(uriPath);
        init();
    }

    private void init()
    {
        try
        {
            UMLResourcesUtil.init(this.getResourceSet());
            registerPathmaps(this.resourceSet_);
            this.resource_ = this.resourceSet_.getResource(this.uri_, true);
            this.resource_.load(null);
            //EcoreUtil.resolveAll(this.resourceSet_);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public Resource getResource()
    {
        return this.resource_;
    }

    public void setResource(Resource resource)
    {
        this.resource_ = resource;
    }

    public ResourceSet getResourceSet()
    {
        return this.resourceSet_;
    }

    public EObject getRootPackage()
    {
        if (this.resource_ == null)
            return null;

        if (this.resource_.getContents().isEmpty())
            return null;


        EList<EObject> contents = this.resource_.getContents();

        EObject root = contents.get(0);

        if (contents.size() > 1)
            for (EObject obj : contents)
                if (obj instanceof AnyTypeImpl)
                    continue;
                else
                {
                    root = obj;
                    break;
                }

        return root;
    }

    private void registerPathmaps(ResourceSet set)
    {
        String umlResourcePath = UMLResourcesUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        try
        {
            umlResourcePath = URLDecoder.decode(umlResourcePath, "UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        set.getPackageRegistry().put(UMLPackage.eNS_URI, UMLPackage.eINSTANCE);
        set.getResourceFactoryRegistry().getExtensionToFactoryMap().put(UMLResource.FILE_EXTENSION,
                UMLResource.Factory.INSTANCE);

        EPackage.Registry.INSTANCE.put(RefModelPackage.eNS_URI, RefModelPackage.eINSTANCE);
        EPackage.Registry.INSTANCE.put(ConstraintPackage.eNS_URI, ConstraintPackage.eINSTANCE);
        EPackage.Registry.INSTANCE.put(TerminologyPackage.eNS_URI, TerminologyPackage.eINSTANCE);

        URI umlResourcePluginURI = URI.createURI("jar:file:" + umlResourcePath + "!/");

        set.getURIConverter().getURIMap().put(URI.createURI(UMLResource.LIBRARIES_PATHMAP),
                umlResourcePluginURI.appendSegment("libraries").appendSegment(""));

        set.getURIConverter().getURIMap().put(URI.createURI(UMLResource.PROFILES_PATHMAP),
                umlResourcePluginURI.appendSegment("profiles").appendSegment(""));

        set.getURIConverter().getURIMap().put(URI.createURI(UMLResource.METAMODELS_PATHMAP),
                umlResourcePluginURI.appendSegment("metamodels").appendSegment(""));

        set.getURIConverter().getURIMap().put(URI.createURI(UMLResource.UML_PRIMITIVE_TYPES_LIBRARY_URI),
                umlResourcePluginURI.appendSegment("libraries").appendSegment("UMLPrimitiveTypes.library.uml"));

        set.getURIConverter().getURIMap().put(URI.createURI(UMLResource.UML_METAMODEL_URI),
                umlResourcePluginURI.appendSegment("metamodels").appendSegment("UML.metamodel.uml"));

        set.getURIConverter().getURIMap().put(URI.createURI(UMLResource.UML2_PROFILE_URI),
                umlResourcePluginURI.appendSegment("profiles").appendSegment("UML2.profile.uml"));

        //--------------------------------------------------------------------------------
        // Location of the Reference Model Profile (that is applied on a Reference Model)
        // It is important to register it with the model so that we can find out the
        // Profile and its application on the Reference Model.
        URI rmpPathMapURI = URI.createURI(AMLEnvironment.getProfileUriPathMap(AMLEnvironment.AML_RMP_KEY));
        URI rmpPathURI = URI.createURI(AMLEnvironment.getProfileUriPath(AMLEnvironment.AML_RMP_KEY));
        set.getURIConverter().getURIMap().put(rmpPathMapURI, rmpPathURI);

        URI termPathMapURI = URI.createURI(AMLEnvironment.getProfileUriPathMap(AMLEnvironment.AML_TP_KEY));
        URI termPathURI = URI.createURI(AMLEnvironment.getProfileUriPath(AMLEnvironment.AML_TP_KEY));
        set.getURIConverter().getURIMap().put(termPathMapURI, termPathURI);

        URI constPathMapURI = URI.createURI(AMLEnvironment.getProfileUriPathMap(AMLEnvironment.AML_CP_KEY));
        URI constPathURI = URI.createURI(AMLEnvironment.getProfileUriPath(AMLEnvironment.AML_CP_KEY));
        set.getURIConverter().getURIMap().put(constPathMapURI, constPathURI);
        //--------------------------------------------------------------------------------

        // Registering for "uml"
        set.getResourceFactoryRegistry().getExtensionToFactoryMap().put(UMLResource.FILE_EXTENSION,
                UMLResource.Factory.INSTANCE);
        // Registering for "xmi"
        set.getResourceFactoryRegistry().getExtensionToFactoryMap().put(XMI212UMLResource.FILE_EXTENSION,
                UMLResource.Factory.INSTANCE);
        // Registering for "xml"
        set.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xml",
                UMLResource.Factory.INSTANCE);
        // Registering for "ecore"
        set.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore",
                new EcoreResourceFactoryImpl());

        set.getLoadOptions().put(UMLResource.OPTION_RECORD_UNKNOWN_FEATURE, Boolean.TRUE);
    }

    public void save()
    {
        try
        {
            this.getResource().save(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

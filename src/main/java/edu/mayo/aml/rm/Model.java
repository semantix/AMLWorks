package edu.mayo.aml.rm;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.eclipse.uml2.uml.resource.XMI212UMLResource;

import java.io.File;
import java.util.Map;

/**
 * Created by dks02 on 12/11/15.
 */
public class Model
{
    private URI uri_ = null;

    private ResourceSet resourceSet_ = new ResourceSetImpl();
    private Resource resource_ = null;

    public Model(File file)
    {
        if (file == null)
            return;

        this.uri_ = URI.createURI(file.getAbsolutePath());
        init();
    }

    public Model(URI uri)
    {
        this.uri_ = uri;
        init();
    }

    private void init()
    {
        // Registering for "uml"
        resourceSet_.getResourceFactoryRegistry().getExtensionToFactoryMap().put(UMLResource.FILE_EXTENSION,
            UMLResource.Factory.INSTANCE);
        // Registering for "xmi"
        resourceSet_.getResourceFactoryRegistry().getExtensionToFactoryMap().put(XMI212UMLResource.FILE_EXTENSION,
                UMLResource.Factory.INSTANCE);
        // Registering for "ecore"
        resourceSet_.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore",
                new EcoreResourceFactoryImpl());

        resourceSet_.getLoadOptions().put(XMLResource.OPTION_RECORD_UNKNOWN_FEATURE, Boolean.TRUE);

        Map uriMap = resourceSet_.getURIConverter().getURIMap();
        uriMap.put(URI.createURI(UMLResource.LIBRARIES_PATHMAP), this.uri_.appendSegment("libraries").appendSegment(""));
        uriMap.put(URI.createURI(UMLResource.METAMODELS_PATHMAP), this.uri_.appendSegment("metamodels").appendSegment(""));
        uriMap.put(URI.createURI(UMLResource.PROFILES_PATHMAP), this.uri_.appendSegment("profiles").appendSegment(""));

        resourceSet_.getPackageRegistry().put(UMLPackage.eNS_URI, UMLPackage.eINSTANCE);
        resourceSet_.getPackageRegistry().put("*", UMLPackage.eINSTANCE);

        try
        {
            this.resource_ = resourceSet_.createResource(this.uri_);
            this.resource_.load(null);
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

        return this.resource_.getContents().get(0);
    }
}

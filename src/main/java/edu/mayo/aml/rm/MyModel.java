package edu.mayo.aml.rm;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.eclipse.uml2.uml.resource.XMI212UMLResource;

import java.io.File;
import java.io.IOException;

/**
 * Created by dks02 on 12/11/15.
 */
public class MyModel
{
    private URI uri_ = null;

    private ResourceSet resourceSet_ = new ResourceSetImpl();
    private Resource resource_ = null;

    public MyModel(File file)
    {
        if (file == null)
            return;

        this.uri_ = URI.createURI(file.getAbsolutePath());
        init();
    }

    public MyModel(URI uri)
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
        // Registering for "xml"
        resourceSet_.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xml",
                UMLResource.Factory.INSTANCE);
        // Registering for "ecore"
        resourceSet_.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore",
                new EcoreResourceFactoryImpl());

        resourceSet_.getLoadOptions().put(UMLResource.OPTION_RECORD_UNKNOWN_FEATURE, Boolean.TRUE);

        resourceSet_.getPackageRegistry().put(UMLPackage.eNS_URI, UMLPackage.eINSTANCE);

        /*
        Map uriMap = resourceSet_.getURIConverter().getURIMap();

        URI uri = URI.createURI("jar:file:/Users/dks02/A123/downloads/eclipse/plugins/org.eclipse.uml2.uml.resources_5.1.0.v20150906-1225.jar!/");
        uriMap.put(URI.createURI(UMLResource.LIBRARIES_PATHMAP), uri.appendSegment("libraries").appendSegment(""));
        uriMap.put(URI.createURI(UMLResource.METAMODELS_PATHMAP), uri.appendSegment("metamodels").appendSegment(""));
        uriMap.put(URI.createURI(UMLResource.PROFILES_PATHMAP), uri.appendSegment("profiles").appendSegment(""));
        */

        try
        {
            this.resource_ = resourceSet_.getResource(this.uri_, true);
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

    public void save()
    {
        try
        {
            this.resource_.save(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

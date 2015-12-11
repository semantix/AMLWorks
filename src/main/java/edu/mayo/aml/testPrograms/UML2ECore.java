package edu.mayo.aml.testPrograms;

import edu.mayo.aml.mdht.ModelSearch;
import edu.mayo.aml.mdht.PropertyTypeFilter;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.eclipse.uml2.uml.resource.XMI2UMLResource;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by dks02 on 12/10/15.
 */
public class UML2ECore
{
    // This is the way to convert a UML to ECore Programmatically,
    // But this is failing as uml files here are xmi formatted and I do not know how
    // to process a collection of AnyType. But a true UML model with packages should work
    public static void main(String[] args)
    {
        String fileName1 = "aml/ReferenceModelProfile.uml";
        String fileName2 = "aml/TerminologyProfile.uml";
        String fileName3 = "aml/ConstraintProfile.uml";
        String fileName4 = "out/testModel.uml";


        File testModel = new File("src/main/resources/" + fileName4);

        ResourceSet resourceSet = new ResourceSetImpl();

        //Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap( ).put(UMLResource.FILE_EXTENSION,
        //        UMLResource.Factory.INSTANCE);

        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap( ).put(UMLResource.FILE_EXTENSION,
                XMI2UMLResource.Factory.INSTANCE);

        resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore",
                new EcoreResourceFactoryImpl());

        URI uri = URI.createFileURI(testModel.getAbsolutePath());
        Resource resource = resourceSet.getResource(uri, true);

        Map defaultLoadOptions = resourceSet.getLoadOptions();
        defaultLoadOptions.put(XMLResource.OPTION_RECORD_UNKNOWN_FEATURE,
                Boolean.TRUE);

        resourceSet.getLoadOptions().putAll(defaultLoadOptions);

        try
        {
            resource.load(null);

            List<Element> classifiers = ModelSearch.findAllOf(resourceSet, new PropertyTypeFilter());

            System.out.println("TopPkg=" + classifiers.size());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        System.out.println("UML2ECORE DONE!!");
    }
}

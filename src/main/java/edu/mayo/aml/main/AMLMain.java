package edu.mayo.aml.main;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.*;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreSwitch;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.mapping.ecore2xml.Ecore2XMLRegistry;
import org.eclipse.emf.mapping.ecore2xml.impl.Ecore2XMLRegistryImpl;
import org.eclipse.emf.mapping.ecore2xml.util.Ecore2XMLExtendedMetaData;
import org.eclipse.mdht.uml.aml.constraint.ComplexObjectConstraint;
import org.eclipse.mdht.uml.aml.constraint.ConstraintFactory;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.eclipse.uml2.uml.resource.XMI212UMLResource;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by dks02 on 12/4/15.
 */
public class AMLMain
{

    public URI getResourceURI(String prefix, String name)
    {
        if (StringUtils.isEmpty(name))
            return null;

        String resName = name;
        try
        {
            if (!StringUtils.isEmpty(prefix))
                resName = prefix + name;

            File testModel = new File(resName);
            return URI.createFileURI(testModel.getAbsolutePath());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args)
    {
        AMLMain main = new AMLMain();

        String prefix = "src/main/resources/";

//        String rmName = "rm/CIMI_RM_v3.0.5.ecore";
//        String rmpName = "aml/refModel.ecore";
//        String tbpName = "aml/terminology.ecore";
//        String cpName = "aml/constraint.ecore";

        String rmName = "rm/CIMI_RM_v3.0.5.xmi";
        String rmpName = "aml/ReferenceModelProfile.uml";
        String tbpName = "aml/TerminologyProfile.uml";
        String cpName = "aml/ConstraintProfile.uml";


        Resource rmpRes = main.getECoreResource(main.getResourceURI(prefix, rmpName));
        Resource tbpRes = main.getECoreResource(main.getResourceURI(prefix, tbpName));
        Resource cpRes = main.getECoreResource(main.getResourceURI(prefix, cpName));
        Resource rmRes = main.getECoreResource(main.getResourceURI(prefix, rmName));

        try
        {

                EPackage rmtopObj = (EPackage) rmRes.getContents().get(0);
                EPackage rmptopObj = (EPackage) rmpRes.getContents().get(0);
                EPackage tbptopObj = (EPackage) tbpRes.getContents().get(0);
                EPackage cptopObj = (EPackage) cpRes.getContents().get(0);

                Collection allpackages = EcoreUtil.getObjectsByType(tbpRes.getContents(), EcorePackage.Literals.EPACKAGE);

                EPackage foundPkg = null;
                EClass foundCls = null;

                EcoreSwitch<EList<EClassifier>> ecoreSwitchPkg = new EcoreSwitch<EList<EClassifier>>()
                {
                    @Override
                    public EList<EClassifier> caseEPackage(EPackage pkg)
                    {
                        return pkg.getEClassifiers();
                    }
                };

                EcoreSwitch<EList<EPackage>> ecoreSwitchSubPkg = new EcoreSwitch<EList<EPackage>>()
                {
                    @Override
                    public EList<EPackage> caseEPackage(EPackage pkg)
                    {
                        return pkg.getESubpackages();
                    }
                };

                EcoreSwitch<EClass> ecoreSwitchCls = new EcoreSwitch<EClass>()
                {
                    public EClass caseEClass(EClass cls)
                    {
                        return cls;
                    }
                };

                EList<EClassifier> eClassifiers = ecoreSwitchPkg.doSwitch(rmtopObj);

                EPackage startwith = rmtopObj;
                while((eClassifiers == null)||(eClassifiers.size() == 0))
                {
                    for (EPackage subp : ecoreSwitchSubPkg.doSwitch(startwith))
                    {
                        eClassifiers = ecoreSwitchPkg.doSwitch(subp);

                        if ((eClassifiers == null) || (eClassifiers.size() == 0))
                        {
                            for (EPackage subp2 : ecoreSwitchSubPkg.doSwitch(subp))
                            {
                                eClassifiers = ecoreSwitchPkg.doSwitch(subp2);

                                if ((eClassifiers == null) || (eClassifiers.size() == 0))
                                    continue;
                            }

                            if ((eClassifiers == null) || (eClassifiers.size() == 0))
                                continue;
                        }
                    }
                }

                for (EClassifier ec : eClassifiers)
                {
                    EClass ecls = ecoreSwitchCls.doSwitch(ec);
                    if (ecls != null)
                        System.out.println("EClass Name:" + ecls.getName());
                }


            File outputModel = new File("src/main/resources/out/testModel.ecore");
            ResourceSet resourceSet2 = new ResourceSetImpl();
            resourceSet2.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore",
                    new EcoreResourceFactoryImpl());
            URI uri = URI.createFileURI(outputModel.getAbsolutePath());
            Resource resource2 = resourceSet2.createResource(uri);
            resource2.getContents().add(rmtopObj);

            ComplexObjectConstraint cc = ConstraintFactory.eINSTANCE.createComplexObjectConstraint();

            try
            {
                resource2.save(null);
                System.out.println("saved to " + uri);
            } catch (IOException e) {
                System.out.println("failed to write " + uri);
            }

            System.out.println("AllPackages got");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("failed to read " + rmName);
        }

        System.out.println("DONE");
    }

    public void load1()
    {
        ResourceSet rs = new ResourceSetImpl();

        Map<String, Object> options = new HashMap<String, Object>();
        //options.put(XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE);
        options.put(XMLResource.OPTION_RECORD_UNKNOWN_FEATURE, Boolean.TRUE);
        //options.put(XMLResource.OPTION_ENCODING, "UTF-8");

        rs.getLoadOptions().putAll(options);


        rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());


        URI fileURI = URI.createFileURI(new File("src/main/resources/CIMI_RM_v3.0.5.xmi").getAbsolutePath());

        Resource cimiRM = rs.getResource(fileURI, true);

        TreeIterator<EObject> allItems = cimiRM.getAllContents();

        while (allItems.hasNext())
        {
            EObject item = allItems.next();


            for (EObject eo : item.eContents())
                System.out.println("###### " + eo.toString());
            System.out.println("--------");
        }

        System.out.println("DONE");
    }

    public Resource getECoreResource(URI ecoreResourceURI)
    {
        if (ecoreResourceURI == null)
            return null;

        ResourceSet resourceSet = new ResourceSetImpl();

        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap( ).put(UMLResource.FILE_EXTENSION,
                UMLResource.Factory.INSTANCE);
        //Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap( ).put("xmi",
         //       new XMIResourceFactoryImpl());
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap( ).put(XMI212UMLResource.FILE_EXTENSION,
                new XMIResourceFactoryImpl());
        resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore",
                new EcoreResourceFactoryImpl());

//        EPackage.Registry packageRegistry = resourceSet.getPackageRegistry();
//        packageRegistry.put("http://schema.omg.org/spec/XMI/2.1", UMLPackage.eINSTANCE);
//        packageRegistry.put("http://schema.omg.org/spec/UML/2.1", UMLPackage.eINSTANCE);



        Resource resource = resourceSet.getResource(ecoreResourceURI, true);

        Map defaultLoadOptions = resourceSet.getLoadOptions();
        defaultLoadOptions.put(XMLResource.OPTION_EXTENDED_META_DATA,
                getExtendedMetaData());
        defaultLoadOptions.put(XMLResource.OPTION_RECORD_UNKNOWN_FEATURE,
                Boolean.TRUE);
        defaultLoadOptions.put(XMLResource.OPTION_RESOURCE_HANDLER,
                new RulesResourceHandler());
        resourceSet.getLoadOptions().putAll(defaultLoadOptions);

        try
        {
            //Package root = UML2Util.load(resourceSet, ecoreResourceURI, EcorePackage.Literals.EPACKAGE);

            resource.load(null);
            return resource;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public ExtendedMetaData getExtendedMetaData()
    {
        ExtendedMetaData extendedMetaData = null;
        if(extendedMetaData == null)
        {
            ResourceSet resourceSet = new ResourceSetImpl();
            EPackage.Registry ePackageRegistry = resourceSet.getPackageRegistry();
            Ecore2XMLRegistry ecore2xmlRegistry = new Ecore2XMLRegistryImpl(Ecore2XMLRegistry.INSTANCE);
            extendedMetaData = new Ecore2XMLExtendedMetaData(ePackageRegistry, ecore2xmlRegistry);
        }

        return extendedMetaData;
    }
}

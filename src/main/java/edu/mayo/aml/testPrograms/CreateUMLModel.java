package edu.mayo.aml.testPrograms;

import edu.mayo.aml.main.UMLModelHelper;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.mdht.uml.aml.constraint.ArchetypeLibrary;
import org.eclipse.mdht.uml.aml.constraint.ConstraintFactory;
import org.eclipse.mdht.uml.aml.refmodel.RefModelFactory;
import org.eclipse.mdht.uml.aml.terminology.TerminologyFactory;
import org.eclipse.uml2.uml.*;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.resource.UMLResource;

import java.io.File;

/**
 * Created by dks02 on 12/8/15.
 */
public class CreateUMLModel
{
    // This is the way to create a UML model
    public static void main(String[] args)
    {
        ConstraintFactory constraintFactory = ConstraintFactory.eINSTANCE;
        TerminologyFactory terminologyFactory = TerminologyFactory.eINSTANCE;
        RefModelFactory referenceModelFactory = RefModelFactory.eINSTANCE;

        File outputModel = new File("src/main/resources/out/testArchetype.uml");

        ArchetypeLibrary archetypeLibrary = ConstraintFactory.eINSTANCE.createArchetypeLibrary();


        Model epo2Model = UMLModelHelper.createModel("epo2");
        Package barPackage = UMLModelHelper.createPackage(epo2Model, "bar");

        org.eclipse.uml2.uml.Class testClass = UMLModelHelper.createClass(barPackage, "TESTDEEPAK", false);
        PrimitiveType inttype = UMLModelHelper.createPrimitiveType(barPackage, "int");
        Property testProp = UMLModelHelper.createAttribute(testClass, "nameOfme", inttype, 0, 1);

        ResourceSet resourceSet = new ResourceSetImpl();

        resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(UMLResource.FILE_EXTENSION,
                UMLResource.Factory.INSTANCE);

        URI uri = URI.createFileURI(outputModel.getAbsolutePath());
        Resource resource = resourceSet.createResource(uri);

        UMLModelHelper.save(barPackage, uri);
    }

    public void createTestModel()
    {
        File outputModel = new File("src/main/resources/out/testUMLModel.uml");

        Model epo2Model = UMLModelHelper.createModel("epo2");
        Package barPackage = UMLModelHelper.createPackage(epo2Model, "bar");

        org.eclipse.uml2.uml.Class testClass = UMLModelHelper.createClass(barPackage, "TESTDEEPAK", false);
        PrimitiveType inttype = UMLModelHelper.createPrimitiveType(barPackage, "int");
        Property testProp = UMLModelHelper.createAttribute(testClass, "nameOfme", inttype, 0, 1);

        ResourceSet resourceSet = new ResourceSetImpl();

        resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(UMLResource.FILE_EXTENSION,
                UMLResource.Factory.INSTANCE);

        URI uri = URI.createFileURI(outputModel.getAbsolutePath());
        Resource resource = resourceSet.createResource(uri);

        UMLModelHelper.save(barPackage, uri);
    }
}

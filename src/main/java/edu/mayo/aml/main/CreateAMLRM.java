package edu.mayo.aml.main;

import edu.mayo.aml.common.AMLProfile;
import edu.mayo.aml.common.UMLModel;
import edu.mayo.aml.conf.AMLEnvironment;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.*;
import org.eclipse.uml2.uml.Package;

import java.io.File;

/**
 * Created by dks02 on 12/8/15.
 */
public class CreateAMLRM extends UMLModel
{
    public CreateAMLRM(URI uri)
    {
        super(uri);
    }

    // This is the way to create a UML Profile
    public static void main(String[] args)
    {
        String uriPath = "src/main/resources/computable/rm/AM_RM_Programmed.uml";
        File outputModel = new File(uriPath);

        if (outputModel.exists())
        {
            //outputModel.delete();
        }

        CreateAMLRM amlRMModel = new CreateAMLRM(URI.createFileURI(uriPath));
        Resource resource = amlRMModel.getResource();

        if (resource == null)
        {
            resource = amlRMModel.getResourceSet().createResource(URI.createURI(uriPath));
            amlRMModel.setResource(resource);
        }

        EList<EObject> contents = resource.getContents();
        contents.clear();

        Model amlRM = UMLModelHelper.createModel("AML_RM");


        String rmpuriPath = AMLEnvironment.getProfileUriPath(AMLEnvironment.AML_RMP_KEY);
        AMLProfile rmpProfile = new AMLProfile(URI.createFileURI(rmpuriPath));

        Profile rmp = rmpProfile.getProfile();

        amlRM.applyProfile(rmp);


        Stereotype refrenceModelStereotype = rmp.getOwnedStereotype("ReferenceModel");

        Package cimiRMPkg = UMLModelHelper.createPackage(amlRM, "CIMI Reference Model");
        System.out.println("Is Applicable:" + cimiRMPkg.isStereotypeApplicable(refrenceModelStereotype));
        //cimiRMPkg.applyProfile(rmp);
        cimiRMPkg.applyStereotype(refrenceModelStereotype);


        Package primitiveTypesPkg = UMLModelHelper.createPackage(cimiRMPkg, "Primitive Types");

        PrimitiveType stringPrimitiveDataType = UMLModelHelper.createPrimitiveType(primitiveTypesPkg, "String");
        PrimitiveType booleanPrimitiveDataType = UMLModelHelper.createPrimitiveType(primitiveTypesPkg, "Boolean");
        PrimitiveType bytePrimitiveDataType = UMLModelHelper.createPrimitiveType(primitiveTypesPkg, "Byte");
        PrimitiveType characterPrimitiveDataType = UMLModelHelper.createPrimitiveType(primitiveTypesPkg, "Character");
        PrimitiveType integerPrimitiveDataType = UMLModelHelper.createPrimitiveType(primitiveTypesPkg, "Integer");
        PrimitiveType realPrimitiveDataType = UMLModelHelper.createPrimitiveType(primitiveTypesPkg, "Real");
        PrimitiveType uriPrimitiveDataType = UMLModelHelper.createPrimitiveType(primitiveTypesPkg, "URI");

        Class anyClass = UMLModelHelper.createClass(primitiveTypesPkg, "Any", false);
        Class arrayClass = UMLModelHelper.createClass(primitiveTypesPkg, "Array", false);
        Class listClass = UMLModelHelper.createClass(primitiveTypesPkg, "List", false);

        Package corePkg = UMLModelHelper.createPackage(cimiRMPkg, "Core");
        Package dataValueTypesPkg = UMLModelHelper.createPackage(cimiRMPkg, "Data Value Types");
        Package partyPkg = UMLModelHelper.createPackage(cimiRMPkg, "Party");

        Class archetypedClass = UMLModelHelper.createClass(corePkg, "ARCHETYPED", false);
        Property archetypeId = UMLModelHelper.createAttribute(archetypedClass, "archetype_id", stringPrimitiveDataType, 1, 1);
        Property rmVersion = UMLModelHelper.createAttribute(archetypedClass, "rm_version", stringPrimitiveDataType, 1, 1);
        rmVersion.setDefault("3.0.5");

        //Resource modelResource = amlRM.eResource();
        //modelResource.setURI(URI.createFileURI(outputModel.getAbsolutePath()));

        contents.add(amlRM);
        contents.addAll(amlRM.getStereotypeApplications());
        contents.addAll(cimiRMPkg.getStereotypeApplications());

        amlRMModel.save();
    }
}

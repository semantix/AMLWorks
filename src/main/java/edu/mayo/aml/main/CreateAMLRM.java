package edu.mayo.aml.main;

import edu.mayo.aml.common.UMLModel;
import org.eclipse.emf.common.util.URI;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;

import java.io.File;

/**
 * Created by dks02 on 12/8/15.
 */
public class CreateAMLRM extends UMLModel
{

    public CreateAMLRM(File file)
    {
        super(file);
    }

    public CreateAMLRM(URI uri)
    {
        super(uri);
    }

    // This is the way to create a UML Profile
    public static void main(String[] args)
    {
        String uriPath = "/Users/dks02/A123/EclipseWS/MDA/UMLModels/CIMIRMFromProgram/AM_RM_Programmed.uml";
        File outputModel = new File(uriPath);

        if (outputModel.exists())
        {
            //outputModel.delete();

        }

        CreateAMLRM amlRMModel = new CreateAMLRM(outputModel);

        org.eclipse.uml2.uml.Model amlRM = UMLModelHelper.createModel("AML_RM");

        //UMLUtil.importLibrary(amlRM, UMLResource.UML_PRIMITIVE_TYPES_LIBRARY_URI);
        //UMLModelHelper.importLibraries(amlRM, URI.createURI(UMLResource.UML_PRIMITIVE_TYPES_LIBRARY_URI));
        //UMLModelHelper.importLibraries(amlRM, URI.createURI(UMLResource.XML_PRIMITIVE_TYPES_LIBRARY_URI));
        Package cimiRMPkg = UMLModelHelper.createPackage(amlRM, "CIMI Reference UMLModel");

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

        Package modelPkg = amlRM.getModel();

        amlRMModel.getResource().getContents().add(amlRM);
        amlRMModel.save();
    }
}

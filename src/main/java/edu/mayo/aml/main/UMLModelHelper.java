package edu.mayo.aml.main;


import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.uml2.uml.*;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.resource.UMLResource;

import java.io.IOException;

/**
 * Created by dks02 on 12/8/15.
 */
public class UMLModelHelper
{
    public static Model createModel(String name)
    {
        Model model = UMLFactory.eINSTANCE.createModel();

        model.setName(name);

        System.out.println("Model '" + model.getQualifiedName() + "' created.");

        return model;
    }

    public static Package createPackage(Package nestingPackage, String name)
    {
        if (nestingPackage == null)
        {
            Package pkg = UMLFactory.eINSTANCE.createPackage();
            pkg.setName(name);
            return pkg;
        }

         Package pkg = nestingPackage.createNestedPackage(name);

         System.out.println("Package '" + pkg.getQualifiedName() + "' created.");

         return pkg;
    }

     public static PrimitiveType createPrimitiveType(Package pkg, String name)
     {
         PrimitiveType primitiveType = (PrimitiveType) pkg.createOwnedPrimitiveType(name);
         System.out.println("Primitive type '" + primitiveType.getQualifiedName()
             + "' created.");

        return primitiveType;
     }

    public static Enumeration createEnumeration(Package pkg, String name)
    {
        Enumeration enumeration = (Enumeration) pkg.createOwnedEnumeration(name);

        System.out.println("Enumeration '" + enumeration.getQualifiedName() + "' created.");

        return enumeration;
    }

    public static EnumerationLiteral createEnumerationLiteral(
                                    Enumeration enumeration, String name)
    {
        EnumerationLiteral enumerationLiteral = enumeration.createOwnedLiteral(name);

        System.out.println("Enumeration literal '" + enumerationLiteral.getQualifiedName()
                 + "' created.");

        return enumerationLiteral;
    }

    public static org.eclipse.uml2.uml.Class createClass(Package pkg, String name, boolean isAbstract)
    {
        Class cls = pkg.createOwnedClass(name, isAbstract);

        System.out.println("Class '" + cls.getQualifiedName() + "' created.");

        return cls;
    }

    public static Generalization createGeneralization(
             Classifier specificClassifier, Classifier generalClassifier)
    {
        Generalization generalization = specificClassifier
         .createGeneralization(generalClassifier);

        System.out.println("Generalization " + specificClassifier.getQualifiedName() + " ->> "
                 + generalClassifier.getQualifiedName() + " created.");

        return generalization;
    }

    public static Property createAttribute(Class cls, String name, Type type,
                                           int lowerBound, int upperBound)
    {
        Property attribute = cls.createOwnedAttribute(name, type,
                                            lowerBound, upperBound);

         StringBuffer sb = new StringBuffer();

         sb.append("Attribute '");

         sb.append(attribute.getQualifiedName());

         sb.append("' : ");

         sb.append(type.getQualifiedName());

         sb.append(" [");
         sb.append(lowerBound);
         sb.append("..");
         sb.append(LiteralUnlimitedNatural.UNLIMITED == upperBound
                 ? "*"
         : String.valueOf(upperBound));
         sb.append("]");

         sb.append(" created.");

        System.out.println(sb.toString());

         return attribute;
    }

    protected static Association createAssociation(Type type1,
                                                    boolean end1IsNavigable, AggregationKind end1Aggregation,
                                                    String end1Name, int end1LowerBound, int end1UpperBound,
                                                    Type type2, boolean end2IsNavigable,
                                                    AggregationKind end2Aggregation, String end2Name,
                                                    int end2LowerBound, int end2UpperBound)
    {
         Association association = type1.createAssociation(end1IsNavigable,
                 end1Aggregation, end1Name, end1LowerBound, end1UpperBound, type2,
                 end2IsNavigable, end2Aggregation, end2Name, end2LowerBound,
                 end2UpperBound);

         StringBuffer sb = new StringBuffer();

         sb.append("Association ");

         if (null == end1Name || 0 == end1Name.length()) {
             sb.append('{');
             sb.append(type1.getQualifiedName());
             sb.append('}');
             } else {
             sb.append("'");
             sb.append(type1.getQualifiedName());
             sb.append(NamedElement.SEPARATOR);
             sb.append(end1Name);
             sb.append("'");
             }

         sb.append(" [");
         sb.append(end1LowerBound);
         sb.append("..");
         sb.append(LiteralUnlimitedNatural.UNLIMITED == end1UpperBound
                 ? "*"
         : String.valueOf(end1UpperBound));
         sb.append("] ");

         sb.append(end2IsNavigable
                 ? '<'
         : '-');
         sb.append('-');
         sb.append(end1IsNavigable
                 ? '>'
         : '-');
         sb.append(' ');

         if (null == end2Name || 0 == end2Name.length()) {
             sb.append('{');
             sb.append(type2.getQualifiedName());
             sb.append('}');
             } else {
             sb.append("'");
             sb.append(type2.getQualifiedName());
             sb.append(NamedElement.SEPARATOR);
             sb.append(end2Name);
             sb.append("'");
             }

         sb.append(" [");
         sb.append(end2LowerBound);
         sb.append("..");
         sb.append(LiteralUnlimitedNatural.UNLIMITED == end2UpperBound
                 ? "*"
         : String.valueOf(end2UpperBound));
         sb.append("]");

         sb.append(" created.");

        System.out.println(sb.toString());

         return association;
    }

    public static void save(Package pkg, URI uri)
    {
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap( ).put(UMLResource.FILE_EXTENSION,
                UMLResource.Factory.INSTANCE);

        ResourceSet resourceSet = new ResourceSetImpl();

        Resource resource = resourceSet.createResource(uri);
        resource.getContents().add(pkg.getModel());

        try {
            resource.save(null);
            System.out.println("saved to " + uri);
        } catch (IOException e) {
            System.out.println("failed to write " + uri);
        }
    }

    public static void applyProfile(URI uri)
    {

    }
}

package edu.mayo.emf;


import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.uml2.uml.*;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.eclipse.uml2.uml.resource.XMI2UMLResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * Created by dks02 on 12/8/15.
 */
public class UMLModelHelper
{
    final static Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(UMLModelHelper.class);

    public static Model createModel(String name)
    {
        Model model = UMLFactory.eINSTANCE.createModel();

        model.setName(name);

        logger.info("MyModel '" + model.getQualifiedName() + "' created.");

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

         logger.info("Package '" + pkg.getQualifiedName() + "' created.");

         return pkg;
    }

    public static Profile createProfile(Package nestingPackage, String name)
    {
        Profile profile = null;

        if (nestingPackage == null)
        {
            profile = UMLFactory.eINSTANCE.createProfile();
            profile.setName(name);
            return profile;
        }

        profile.setNestingPackage(nestingPackage);

        logger.info("Profile '" + profile.getQualifiedName() + "' created.");

        return profile;
    }

    public static Stereotype createStereotype(Profile nestingProfile, String name)
    {
        Stereotype stereotype = null;

        if (nestingProfile == null)
        {
            stereotype = UMLFactory.eINSTANCE.createStereotype();
            stereotype.setName(name);
            return stereotype;
        }

        //stereotype.getsetNestingPackage(nestingPackage);

        logger.info("Stereotype '" + stereotype.getQualifiedName() + "' created.");

        return stereotype;
    }

     public static PrimitiveType createPrimitiveType(Package pkg, String name)
     {
         PrimitiveType primitiveType = (PrimitiveType) pkg.createOwnedPrimitiveType(name);
         logger.info("Primitive type '" + primitiveType.getQualifiedName()
                 + "' created.");

        return primitiveType;
     }

    public static Enumeration createEnumeration(Package pkg, String name)
    {
        Enumeration enumeration = (Enumeration) pkg.createOwnedEnumeration(name);

        logger.info("Enumeration '" + enumeration.getQualifiedName() + "' created.");

        return enumeration;
    }

    public static EnumerationLiteral createEnumerationLiteral(
                                    Enumeration enumeration, String name)
    {
        EnumerationLiteral enumerationLiteral = enumeration.createOwnedLiteral(name);

        logger.info("Enumeration literal '" + enumerationLiteral.getQualifiedName()
                + "' created.");

        return enumerationLiteral;
    }

    public static org.eclipse.uml2.uml.Class createClass(Package pkg, String name, boolean isAbstract)
    {
        Class cls = pkg.createOwnedClass(name, isAbstract);

        logger.info("Class '" + cls.getQualifiedName() + "' created.");

        return cls;
    }

    public static Generalization createGeneralization(
             Classifier specificClassifier, Classifier generalClassifier)
    {
        Generalization generalization = specificClassifier
         .createGeneralization(generalClassifier);

        logger.info("Generalization " + specificClassifier.getQualifiedName() + " ->> "
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

         logger.info(sb.toString());

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

        logger.info(sb.toString());

         return association;
    }

    public static void save(Package pkg, URI uri)
    {
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap( ).put(UMLResource.FILE_EXTENSION,
                UMLResource.Factory.INSTANCE);

        ResourceSet resourceSet = new ResourceSetImpl();

        Resource resource = resourceSet.createResource(uri);
        resource.getContents().add(pkg);

        try {
            resource.save(null);
            logger.info("saved to " + uri);
        } catch (IOException e) {
            logger.error("failed to write " + uri);
        }
    }

    public static Package load(URI urip)
    {
         Package package_ = null;

         try
         {
             ResourceSet resourceSet = new ResourceSetImpl();

             // Registering for "uml"
             resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(UMLResource.FILE_EXTENSION,
                     UMLResource.Factory.INSTANCE);

             resourceSet.getPackageRegistry().put(EcorePackage.eNS_URI, EcorePackage.eINSTANCE);
             resourceSet.getPackageRegistry().put(UMLPackage.eNS_URI, UMLPackage.eINSTANCE);

             // Registering for "xmi"
             resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xml",
                     UMLResource.Factory.INSTANCE);

             // Registering for "ecore"
             resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore",
                     new EcoreResourceFactoryImpl());

             Map uriMap = URIConverter.URI_MAP;
             URI uri = URI.createURI("jar:file:/Users/dks02/A123/downloads/eclipse/plugins/org.eclipse.uml2.uml.resources_5.1.0.v20150906-1225.jar!/");
             uriMap.put(URI.createURI(UMLResource.LIBRARIES_PATHMAP), uri.appendSegment("libraries").appendSegment(""));
             uriMap.put(URI.createURI(UMLResource.METAMODELS_PATHMAP), uri.appendSegment("metamodels").appendSegment(""));
             uriMap.put(URI.createURI(UMLResource.PROFILES_PATHMAP), uri.appendSegment("profiles").appendSegment(""));
             uriMap.put(URI.createURI("platform:/plugin/org.eclipse.uml2.uml/"), urip);

             Map extensionToFactoryMap =
                     Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap();
             extensionToFactoryMap.put(UMLResource.FILE_EXTENSION,
                     UMLResource.Factory.INSTANCE);
             extensionToFactoryMap.put(XMI2UMLResource.FILE_EXTENSION,
                     XMI2UMLResource.Factory.INSTANCE);

             Resource resource = resourceSet.getResource(urip, true);
             EcoreUtil.resolveAll(resource);

             package_ = (org.eclipse.uml2.uml.Package) EcoreUtil.getObjectByType(
                     resource.getContents(), EcorePackage.Literals.EPACKAGE);
         }
         catch (Exception e)
         {
             logger.error(e.getMessage());
         }

         return package_;
    }

    public static void importLibraries(Package importee, URI imported)
    {
        Package library = (Package) load(imported);

        // We check if a package import already exists
        if (!importee.getImportedPackages().contains(library))
        {
            PackageImport impPkg = importee.createPackageImport(library);
            impPkg.setImportedPackage(library);
            //importee.getImportedPackages().add(library);

            importee.getPackageImports().add(impPkg);
            logger.info("Pacakge '" + imported + "' imported.");
        }
    }

    public static PrimitiveType importPrimitiveType(Package package_, String name)
    {
         Model umlLibrary = (Model) load(URI.createURI(UMLResource.UML_PRIMITIVE_TYPES_LIBRARY_URI));

         PrimitiveType primitiveType = (PrimitiveType) umlLibrary.getOwnedType(name);

         package_.createElementImport(primitiveType);

         logger.info("Primitive type '" + primitiveType.getQualifiedName() + "' imported.");

         return primitiveType;
    }
}

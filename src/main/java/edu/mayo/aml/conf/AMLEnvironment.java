package edu.mayo.aml.conf;

/**
 * Created by dks02 on 12/7/15.
 */
public class AMLEnvironment
{
    public static AMLProperties properties_ = new AMLProperties();

    // AML Reference UMLModel
    public static String AML_RM_KEY = "aml.rm";

    // Profiles
    public static String AML_RMP_KEY = "aml.referenceModelProfile";
    public static String AML_TP_KEY = "aml.terminologyProfile";
    public static String AML_CP_KEY = "aml.constraintProfile";

    // Archetypes
    // URI of where the archetypes are published by this program
    public static String AML_ARCHETYPES = "aml.archetype.publish";
    // Subdirectory under the archetype publish URI location
    public static String AML_ARCHETYPES_DIR = "aml.archetypes.dir";

    public static String getModelName(String key)
    {
        return properties_.getPropertyValue(key + ".name");
    }

    public static String getModelVersion(String key)
    {
        return properties_.getPropertyValue(key + ".version");
    }

    public static String getModelUriPath(String key)
    {
        return properties_.getPropertyValue(key + ".path");
    }

    public static String getAMLArchetypesCollectionName()
    {
        return properties_.getPropertyValue(AML_ARCHETYPES_DIR);
    }
}

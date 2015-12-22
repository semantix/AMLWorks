package edu.mayo.aml.conf;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.net.URI;

/**
 * Created by dks02 on 12/7/15.
 */
public class AMLEnvironment
{
    public static AMLProperties properties_ = new AMLProperties();

    // AML Reference UML Models Key
    public static String AML_RMS_KEY = "aml.rms";

    // Resource Location
    public static String AML_RESOURCES_PREFIX = "aml.resources.path";

    // AML Reference Model Prefix
    public static String AML_RM_PREFIX = "aml.rm";

    // AML Profile Prefix
    public static String AML_PROFILE_PREFIX = "aml.profile";

    // Profiles
    public static String AML_RMP_KEY = "referenceModel";
    public static String AML_TP_KEY = "terminology";
    public static String AML_CP_KEY = "constraint";

    // Archetypes
    // URI of where the archetypes are published by this program
    public static String AML_ARCHETYPES_COLLECTION_PATH = "aml.archetypes.collection.path";

    // Subdirectory under the archetype publish URI location
    public static String AML_ARCHETYPES_COLLECTION_NAME = "aml.archetypes.collection.name";

    public static String getModelNameSpace(String rmName)
    {
        return properties_.getPropertyValue(AMLEnvironment.getRMKey(rmName) + ".ns");
    }

    public static String getModelPublisher(String rmName)
    {
        return properties_.getPropertyValue(AMLEnvironment.getRMKey(rmName) + ".publisher");
    }

    public static String getModelPackage(String rmName)
    {
        return properties_.getPropertyValue(AMLEnvironment.getRMKey(rmName) + ".package");
    }

    public static String getModelVersion(String rmName)
    {
        return properties_.getPropertyValue(AMLEnvironment.getRMKey(rmName) + ".version");
    }

    public static String getModelDescription(String rmName)
    {
        return properties_.getPropertyValue(AMLEnvironment.getRMKey(rmName) + ".desc");
    }

    public static boolean isModelDefault(String rmName)
    {
        String value = properties_.getPropertyValue(AMLEnvironment.getRMKey(rmName) + ".isDefault");

        return "true".equalsIgnoreCase(value);
    }

    public static String getReferenceModelUriPath(String rmName)
    {
        String pref = StringUtils.isEmpty(getResourcesPathPrefix())? "" : (getResourcesPathPrefix() + File.separator);
        return pref + properties_.getPropertyValue(AMLEnvironment.getRMKey(rmName) + ".path");
    }

    public static String getProfileUriPath(String rmName)
    {
        String pref = StringUtils.isEmpty(getResourcesPathPrefix())? "" : (getResourcesPathPrefix() + File.separator);
        return pref + properties_.getPropertyValue(AMLEnvironment.getProfileKey(rmName) + ".path");
    }

    public static String getAMLArchetypesCollectionPath()
    {
        return properties_.getPropertyValue(AMLEnvironment.AML_ARCHETYPES_COLLECTION_PATH.trim());
    }

    public static String getAMLArchetypesCollectionName()
    {
        return properties_.getPropertyValue(AMLEnvironment.AML_ARCHETYPES_COLLECTION_NAME.trim());
    }

    public static String getResourcesPathPrefix()
    {
        return properties_.getPropertyValue(AMLEnvironment.AML_RESOURCES_PREFIX.trim());
    }

    public static String getRMKey(String rmName)
    {
        if (StringUtils.isEmpty(rmName))
            return null;

        return AMLEnvironment.AML_RM_PREFIX.trim() + "." + rmName;
    }

    public static String getProfileKey(String profileName)
    {
        if (StringUtils.isEmpty(profileName))
            return null;

        return AMLEnvironment.AML_PROFILE_PREFIX.trim() + "." + profileName;
    }

    public static String[] getRMs()
    {
        String rmsStr = properties_.getPropertyValue(AMLEnvironment.AML_RMS_KEY.trim());

        if (!StringUtils.isEmpty(rmsStr))
            return rmsStr.split(",");

        return new String[0];
    }

    public static URI getArchetypePublishingUri(String fileName)
    {
        if (StringUtils.isEmpty(fileName))
            return null;

        String parentFolder = StringUtils.isEmpty(getAMLArchetypesCollectionPath())?"": (getAMLArchetypesCollectionPath() + File.separator);
        return  URI.create(parentFolder + getAMLArchetypesCollectionName() + File.separator + fileName);
    }
}

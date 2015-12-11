package edu.mayo.aml.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by dks02 on 12/7/15.
 */
public class AMLEnvironment extends AMLProperties
{
    final static Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(AMLEnvironment.class);

    // AML Reference Model
    public static String AML_RM_NAME_PROPERTY = "aml.rm.name";
    public static String AML_RM_VERSION_PROPERTY = "aml.rm.version";
    public static String AML_RM_URIPATH_PROPERTY = "aml.rm.uri.path";

    // Profiles
    public static String AML_RMP_PROFILE_URIPATH_PROPERTY = "aml.referenceModelProfile.uri.path";
    public static String AML_TP_PROFILE_URIPATH_PROPERTY = "aml.terminologyProfile.uri.path";
    public static String AML_CP_PROFILE_URIPATH_PROPERTY = "aml.constraintProfile.uri.path";

    // Archetypes
    // URI of where the archetypes are published by this program
    public static String AML_ARCHETYPE_PUBLISH_URIPATH_PROPERTY = "aml.archetype.publish.uri.path";
    // Subdirectory under the archetype publish URI location
    public static String AML_ARCHETYPES_DIR_PROPERTY = "aml.archetypes.dir";

    public String getReferenceModelName()
    {
        return getPropertyValue(AML_RM_NAME_PROPERTY);
    }

    public String getReferenceModelVersion()
    {
        return getPropertyValue(AML_RM_VERSION_PROPERTY);
    }

    public String getReferenceModelUriPath()
    {
        return getPropertyValue(AML_RM_URIPATH_PROPERTY);
    }

    public String getAMLReferenceModelProfileUriPath()
    {
        return getPropertyValue(AML_RMP_PROFILE_URIPATH_PROPERTY);
    }

    public String getAMLTerminologyProfileUriPath()
    {
        return getPropertyValue(AML_TP_PROFILE_URIPATH_PROPERTY);
    }

    public String getAMLConstraintProfileUriPath()
    {
        return getPropertyValue(AML_CP_PROFILE_URIPATH_PROPERTY);
    }

    public String getAMLArchetypePublishUriPath()
    {
        return getPropertyValue(AML_ARCHETYPE_PUBLISH_URIPATH_PROPERTY);
    }

    public String getAMLArchetypesCollectionName()
    {
        return getPropertyValue(AML_ARCHETYPES_DIR_PROPERTY);
    }
}

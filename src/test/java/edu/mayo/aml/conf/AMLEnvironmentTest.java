package edu.mayo.aml.conf;

import junit.framework.TestCase;

/**
 * Created by dks02 on 12/11/15.
 */
public class AMLEnvironmentTest extends TestCase
{
    public void testGetReferenceModelName() throws Exception
    {
        assertNotNull(new AMLEnvironment().getReferenceModelName());
    }

    public void testGetReferenceModelVersion() throws Exception
    {
        assertNotNull(new AMLEnvironment().getReferenceModelVersion());
    }

    public void testGetReferenceModelUriPath() throws Exception
    {
        assertNotNull(new AMLEnvironment().getReferenceModelUriPath());
    }

    public void testGetAMLReferenceModelProfileUriPath() throws Exception
    {
        assertNotNull(new AMLEnvironment().getAMLReferenceModelProfileUriPath());
    }

    public void testGetAMLTerminologyProfileUriPath() throws Exception
    {
        assertNotNull(new AMLEnvironment().getAMLTerminologyProfileUriPath());
    }

    public void testGetAMLConstraintProfileUriPath() throws Exception
    {
        assertNotNull(new AMLEnvironment().getAMLConstraintProfileUriPath());
    }

    public void testGetAMLArchetypePublishUriPath() throws Exception
    {
        assertNotNull(new AMLEnvironment().getAMLArchetypePublishUriPath());
    }

    public void testGetAMLArchetypesCollectionName() throws Exception
    {
        assertNotNull(new AMLEnvironment().getAMLArchetypesCollectionName());
    }
}
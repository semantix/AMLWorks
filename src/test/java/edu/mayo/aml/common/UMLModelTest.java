package edu.mayo.aml.common;

import edu.mayo.aml.conf.AMLEnvironment;
import junit.framework.TestCase;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.uml2.uml.Profile;

/**
 * Created by dks02 on 12/11/15.
 */
public class UMLModelTest extends TestCase
{
    public void testGetRootPackage() throws Exception
    {
        String rmPath = AMLEnvironment.getModelUriPath(AMLEnvironment.AML_RM_KEY); // ReferenceModel
        String rmpPath = AMLEnvironment.getModelUriPath(AMLEnvironment.AML_RMP_KEY); // Reference UMLModel Profile
        String tpPath = AMLEnvironment.getModelUriPath(AMLEnvironment.AML_TP_KEY);  // Terminology Profile
        String cpPath = AMLEnvironment.getModelUriPath(AMLEnvironment.AML_CP_KEY);  // Constraint Profile

        EObject rootPackage = testGetRootPackage(rmPath);
        assertNotNull(rootPackage);
        // should be a UML UMLModel
        assertTrue(rootPackage instanceof org.eclipse.uml2.uml.Model);

        // Next 3 should be a UML Profiles
        rootPackage = testGetRootPackage(rmpPath);
        assertNotNull(rootPackage);
        assertTrue(rootPackage instanceof Profile);

        rootPackage = testGetRootPackage(tpPath);
        assertNotNull(rootPackage);
        assertTrue(rootPackage instanceof Profile);

        rootPackage = testGetRootPackage(cpPath);
        assertNotNull(rootPackage);
        assertTrue(rootPackage instanceof Profile);
    }

    private  EObject testGetRootPackage(String path) throws Exception
    {
        assertNotNull(path);

        URI uri = URI.createURI(path);
        assertNotNull(uri);

        UMLModel model = new UMLModel(uri);
        assertNotNull(model);
        assertNotNull(model.getResource());

        Resource res = model.getResource();
        assertNotNull(res);

        EObject eo = model.getRootPackage();
        assertNotNull(model.getRootPackage());

        return eo;
    }
}
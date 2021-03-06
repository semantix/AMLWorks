package edu.mayo.aml.common;

import edu.mayo.aml.conf.AMLEnvironment;
import junit.framework.TestCase;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Profile;

/**
 * Created by dks02 on 12/11/15.
 */
public class UMLModelTest extends TestCase
{
    public void testGetRootPackage() throws Exception
    {
        String rmPath = AMLEnvironment.getRMUriPath("cimi"); // AMLReferenceModelImpl
        String rmpPath = AMLEnvironment.getProfileUriPath(AMLEnvironment.AML_RMP_KEY, true); // Reference UMLModel Profile
        String tpPath = AMLEnvironment.getProfileUriPath(AMLEnvironment.AML_TP_KEY, true);  // Terminology Profile
        String cpPath = AMLEnvironment.getProfileUriPath(AMLEnvironment.AML_CP_KEY, true);  // Constraint Profile

        EObject rootPackage = testGetRootPackage(rmPath);
        assertNotNull(rootPackage);
        // should be a UML UMLModel
        assertTrue(rootPackage instanceof Model);

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
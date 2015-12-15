package edu.mayo.aml.rm;

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
public class MyModelTest extends TestCase
{
    public void testGetRootPackage() throws Exception
    {

        AMLEnvironment env = new AMLEnvironment();
        String rmPath = new AMLEnvironment().getReferenceModelUriPath(); // ReferenceModel
        String rmpPath = env.getAMLReferenceModelProfileUriPath(); // Reference Model Profile
        String tpPath = env.getAMLTerminologyProfileUriPath();  // Terminology Profile
        String cpPath = env.getAMLConstraintProfileUriPath();  // Constraint Profile

        EObject rootPackage = testGetRootPackage(rmPath);
        assertNotNull(rootPackage);
        // should be a UML Model
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

        MyModel model = new MyModel(uri);
        assertNotNull(model);
        assertNotNull(model.getResource());

        Resource res = model.getResource();
        assertNotNull(res);

        EObject eo = model.getRootPackage();
        assertNotNull(model.getRootPackage());

        return eo;
    }
}
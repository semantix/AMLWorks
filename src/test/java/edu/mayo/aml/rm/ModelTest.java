package edu.mayo.aml.rm;

import edu.mayo.aml.conf.AMLEnvironment;
import junit.framework.TestCase;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Created by dks02 on 12/11/15.
 */
public class ModelTest extends TestCase
{
    public void testGetRootPackageCP() throws Exception
    {
        String rmPath = new AMLEnvironment().getAMLConstraintProfileUriPath();

        assertNotNull(rmPath);

        URI uri = URI.createURI(rmPath);

        assertNotNull(uri);

        Model model = new Model(uri);

        assertNotNull(model);
        assertNotNull(model.getResource());

        Resource res = model.getResource();

        org.eclipse.uml2.uml.Package package_ = (org.eclipse.uml2.uml.Package) EcoreUtil.getObjectByType(
                res.getContents(), UMLPackage.Literals.PACKAGE);

        EObject eo = model.getRootPackage();

        assertNotNull(model.getRootPackage());
    }

    public void testGetRootPackageRM() throws Exception
    {
        String rmPath =
                "file:/Users/dks02/A123/git/Deepak/AMLWorks/src/main/resources/out/testUMLModel.uml";
                //new AMLEnvironment().getReferenceModelUriPath();

        assertNotNull(rmPath);

        URI uri = URI.createURI(rmPath);

        assertNotNull(uri);

        Model model = new Model(uri);

        assertNotNull(model);
        assertNotNull(model.getResource());

        Resource res = model.getResource();

        org.eclipse.uml2.uml.Package package_ = (org.eclipse.uml2.uml.Package) EcoreUtil.getObjectByType(
                res.getContents(), UMLPackage.Literals.PACKAGE);

//        XMLResourceImpl xmlres = new XMLResourceImpl(res.getURI());
//        xmlres.load(null);
//        Map<EObject, AnyType> emap = xmlres.getEObjectToExtensionMap();

        EObject eo = model.getRootPackage();

        assertNotNull(model.getRootPackage());
    }

    public void testGetRootPackageRMP() throws Exception
    {
        String rmPath = new AMLEnvironment().getAMLReferenceModelProfileUriPath();

        assertNotNull(rmPath);

        URI uri = URI.createURI(rmPath);

        assertNotNull(uri);

        Model model = new Model(uri);

        assertNotNull(model);
        assertNotNull(model.getResource());

        Resource res = model.getResource();

        org.eclipse.uml2.uml.Package package_ = (org.eclipse.uml2.uml.Package) EcoreUtil.getObjectByType(
                res.getContents(), UMLPackage.Literals.PACKAGE);

        EObject eo = model.getRootPackage();

        assertNotNull(model.getRootPackage());
    }

    public void testGetRootPackageTP() throws Exception
    {
        String rmPath = new AMLEnvironment().getAMLTerminologyProfileUriPath();

        assertNotNull(rmPath);

        URI uri = URI.createURI(rmPath);

        assertNotNull(uri);

        Model model = new Model(uri);

        assertNotNull(model);
        assertNotNull(model.getResource());

        Resource res = model.getResource();

        org.eclipse.uml2.uml.Package package_ = (org.eclipse.uml2.uml.Package) EcoreUtil.getObjectByType(
                res.getContents(), UMLPackage.Literals.PACKAGE);

        EObject eo = model.getRootPackage();

        assertNotNull(model.getRootPackage());
    }
}
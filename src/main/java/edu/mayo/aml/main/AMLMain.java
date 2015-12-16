package edu.mayo.aml.main;

import edu.mayo.aml.common.ReferenceModel;
import edu.mayo.aml.conf.AMLEnvironment;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Model;
import org.openhealthtools.mdht.uml.common.search.ModelSearch;

import java.io.File;
import java.util.List;


/**
 * Created by dks02 on 12/4/15.
 */
public class AMLMain
{
    public static void main(String[] args)
    {
        AMLMain main = new AMLMain();

        try
        {
            String rmPath = AMLEnvironment.getModelUriPath(AMLEnvironment.AML_RM_KEY); // ReferenceModel
            ReferenceModel cimiRM = new ReferenceModel(new File(rmPath));

            Model rm = cimiRM.getModel();
            List<Element> allPackages = ModelSearch.findAllOf(rm, Package.class);

            cimiRM.printResource(cimiRM.getModel());

            System.out.println("DONE");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("failed to read ");
        }
    }
}

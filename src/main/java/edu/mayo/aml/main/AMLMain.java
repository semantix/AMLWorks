package edu.mayo.aml.main;

import edu.mayo.aml.common.AMLProfile;
import edu.mayo.aml.common.ReferenceModel;
import edu.mayo.aml.conf.AMLEnvironment;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Profile;

import java.io.File;


/**
 * Created by dks02 on 12/4/15.
 */
public class AMLMain
{
    private ReferenceModel getAndPrintModel(String path, boolean print)
    {
        if (StringUtils.isEmpty(path))
            return null;

        String uriPath = AMLEnvironment.getModelUriPath(path); // ReferenceModel
        ReferenceModel model = new ReferenceModel(new File(uriPath));

        if (print)
            model.printResource();

        return model;
    }

    private AMLProfile getAndPrintProfile(String path, boolean print)
    {
        if (StringUtils.isEmpty(path))
            return null;

        String uriPath = AMLEnvironment.getModelUriPath(path);
        AMLProfile amlProfile = new AMLProfile(new File(uriPath));

        if (print)
            amlProfile.printResource();

        return amlProfile;
    }

    public static void main(String[] args)
    {
        AMLMain main = new AMLMain();
        boolean print = true;

        try
        {
            ReferenceModel cimiRM = main.getAndPrintModel(AMLEnvironment.AML_RM_KEY, print);
            Model rm = cimiRM.getModel();

            AMLProfile referenceModelProfile = main.getAndPrintProfile(AMLEnvironment.AML_RMP_KEY, print);
            Profile rmp = referenceModelProfile.getProfile();

            AMLProfile terminologyProfile = main.getAndPrintProfile(AMLEnvironment.AML_TP_KEY, print);
            Profile tp = referenceModelProfile.getProfile();

            AMLProfile constraintProfile = main.getAndPrintProfile(AMLEnvironment.AML_CP_KEY, print);
            Profile cp = referenceModelProfile.getProfile();

            System.out.println("DONE");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("failed to read ");
        }
    }
}

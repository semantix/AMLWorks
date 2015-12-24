package edu.mayo.aml.conf;

import edu.mayo.aml.common.AMLProfile;
import edu.mayo.aml.services.AMLReferenceModel;
import edu.mayo.aml.services.AMLReferenceModels;
import edu.mayo.aml.services.impl.AMLReferenceModelImpl;
import edu.mayo.aml.services.impl.AMLReferenceModelsImpl;
import edu.mayo.utils.AMLPrintUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.uml2.uml.Profile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;


/**
 * Created by dks02 on 12/4/15.
 */
public class AMLResources
{
    final static Logger logger_ = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(AMLResources.class);

    private AMLReferenceModels models_ = new AMLReferenceModelsImpl();

    private Profile rmp = null;
    private Profile tp = null;
    private Profile cp = null;

    private boolean print = false;
    private String currentRM_ = null;

    public AMLResources(String rmName, boolean verbose)
    {
        this.print = verbose;
        initProfiles();
        // Will try to load only the specified RM.
        initRMs(rmName);
    }

    public AMLResources(boolean verbose)
    {
        this.print = verbose;
        initProfiles();
        // will load all available RMs
        initRMs(null);
    }

    private AMLReferenceModel getAndPrintModel(String rmName, boolean print)
    {
        if (StringUtils.isEmpty(rmName))
            return null;

        // Location of Reference Model
        String rmUriPath = AMLEnvironment.getRMUriPath(rmName);
        File rmFile = new File(rmUriPath);



        AMLReferenceModel model = new AMLReferenceModelImpl(rmName, rmFile, this.rmp);

        currentRM_ = (model.isDefaultReferenceModel())? rmName : currentRM_;

        if (print)
            AMLPrintUtils.printUMLModel(model.getUMLModel());

        return model;
    }

    private AMLProfile getAndPrintProfile(String profileName, boolean print)
    {
        if (StringUtils.isEmpty(profileName))
            return null;

        String uriPath = AMLEnvironment.getProfileUriPath(profileName);
        AMLProfile amlProfile = new AMLProfile(new File(uriPath));

        if (print)
            AMLPrintUtils.printUMLProfile(amlProfile.getProfile());

        return amlProfile;
    }

    private void initProfiles()
    {
        AMLProfile referenceModelProfile = getAndPrintProfile(AMLEnvironment.AML_RMP_KEY, print);
        rmp = referenceModelProfile.getProfile();

        AMLProfile terminologyProfile = getAndPrintProfile(AMLEnvironment.AML_TP_KEY, print);
        tp = referenceModelProfile.getProfile();

        AMLProfile constraintProfile = getAndPrintProfile(AMLEnvironment.AML_CP_KEY, print);
        cp = referenceModelProfile.getProfile();
    }

    private void initRMs(String rmName)
    {
        boolean match = false;
        try
        {
            String[] rms = AMLEnvironment.getRMs();

            if ((rms == null)||(rms.length == 0))
                logger_.warn("---- No Reference Models Found!!");

            for (String rm : rms)
            {
                if ((!StringUtils.isEmpty(rmName))&&(!rm.equals(rmName)))
                    continue;

                match = true;
                logger_.info(">>>> Loading Reference Model:" + rm);
                AMLReferenceModel amlRM = getAndPrintModel(rm, print);

                if (amlRM == null)
                {
                    logger_.warn("---- Failed to get RM " + amlRM);
                    continue;
                }

                models_.addReferenceModel(amlRM);
                logger_.info(">>>> Added RM " + amlRM);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger_.error("failed to read ");
        }

        if (!match)
            logger_.warn("Requested Reference Model with name " + rmName + " could not be found!");
    }

    public AMLReferenceModel getCurrentReferenceModel()
    {
        return this.models_.getReferenceModel(this.currentRM_);
    }

    public boolean changeReferenceModel(String rmName)
    {
        if (this.models_.getAvailableReferenceModelNames().contains(rmName))
        {
            currentRM_ = rmName;
            return true;
        }

        return false;
    }
}

package edu.mayo.aml.conf;

import edu.mayo.aml.common.AMLProfile;
import edu.mayo.aml.services.AMLReferenceModel;
import edu.mayo.aml.services.AMLReferenceModels;
import edu.mayo.aml.services.impl.AMLReferenceModelImpl;
import edu.mayo.aml.services.impl.AMLReferenceModelsImpl;
import edu.mayo.utils.AMLPrintUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.emf.common.util.URI;
import org.eclipse.uml2.uml.Profile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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

    private String currentRM_ = null;

    // If this is true, the AML Profiles are loaded from MDHT jar file
    // from where static implementation of AML Profiles used
    // Otherwise the Profiles are loaded from the UML files from
    // a given location and its EMF model is created programmatically
    // using define() method.
    private boolean loadProfileDynamically = false;

    public AMLResources(String rmName, boolean dynamicProfiles)
    {
        this.loadProfileDynamically = dynamicProfiles;

        msg();

        //if (loadProfileDynamically)
        //    initProfiles();

        // Will try to load only the specified RM.
        initRMs(rmName);
    }

    public boolean isLoadingProfileDynamically()
    {
        return this.loadProfileDynamically;
    }

    public AMLResources(boolean dynamicProfiles)
    {
        this.loadProfileDynamically = dynamicProfiles;

        msg();

        //if (!loadProfileDynamically)
        //    initProfiles();

        // will load all available RMs
        initRMs(null);
    }

    private void msg()
    {
        String mode = (this.loadProfileDynamically)? "Dynamic" : "Static";
        logger_.warn("******** Loading AML Profiles from " + mode + " implementation ...  **********");
    }

    private AMLReferenceModel getAndPrintModel(String rmName)
    {
        if (StringUtils.isEmpty(rmName))
            return null;

        // Location of Reference Model
        String rmUriPath = AMLEnvironment.getRMUriPath(rmName);
        URI rmUri = URI.createFileURI(rmUriPath);

        AMLReferenceModel model = new AMLReferenceModelImpl(rmUri, this.isLoadingProfileDynamically());

        currentRM_ = rmName;

        return model;
    }

    private AMLProfile getAndPrintProfile(String profileName, boolean print)
    {
        if (StringUtils.isEmpty(profileName))
            return null;

        String uriPath = AMLEnvironment.getProfileUriPath(profileName, this.isLoadingProfileDynamically());
        AMLProfile amlProfile = new AMLProfile(URI.createFileURI(uriPath), this.isLoadingProfileDynamically());

        if (print)
            AMLPrintUtils.printUMLProfile(amlProfile.getProfile());

        return amlProfile;
    }

    private void initProfiles()
    {
        AMLProfile referenceModelProfile = getAndPrintProfile(AMLEnvironment.AML_RMP_KEY, false);
        rmp = referenceModelProfile.getProfile();

        AMLProfile terminologyProfile = getAndPrintProfile(AMLEnvironment.AML_TP_KEY, false);
        tp = terminologyProfile.getProfile();

        AMLProfile constraintProfile = getAndPrintProfile(AMLEnvironment.AML_CP_KEY, false);
        cp = constraintProfile.getProfile();
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
                AMLReferenceModel amlRM = getAndPrintModel(rm);

                if (amlRM == null)
                {
                    logger_.warn("---- Failed to get RM " + amlRM);
                    continue;
                }

                models_.addReferenceModel(rmName, amlRM);
                logger_.info(">>>> Added RM " + rmName);
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

package edu.mayo.aml.main;

import edu.mayo.aml.conf.AMLResources;
import edu.mayo.aml.services.AMLReferenceModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by dks02 on 12/4/15.
 */
public class AMLMain
{
    final static Logger logger_ = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(AMLMain.class);
    private AMLResources amlResources = new AMLResources("cimi");
    //private AMLResources amlResources = new AMLResources(false);

    public static void main(String[] args)
    {
        AMLMain main = new AMLMain();

        AMLReferenceModel rm = main.amlResources.getCurrentReferenceModel();
        String archetypeName = "CLUSTER";


       // URI outputFileUri = AMLEnvironment.getArchetypePublishingUri();
        //File outputModel = new File(outputFileUri);


    }
}

package edu.mayo.aml.common;

import org.eclipse.emf.common.util.URI;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.resources.util.UMLResourcesUtil;

/**
 * Created by dks02 on 12/15/15.
 */
public class AMLProfile extends UMLModel
{
    public AMLProfile(URI uri, boolean loadStatic)
    {
        super(uri);
    }

    public void init()
    {
        try
        {
            UMLResourcesUtil.init(this.getResourceSet());
            registerPathmaps(this.resourceSet_);
            this.resource_ = this.resourceSet_.getResource(this.uri_, true);
            this.resource_.load(null);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public Profile getProfile()
    {
        if (this.getResource() == null)
            return null;

        Profile profile = (Profile) this.getRootPackage();

        if (!profile.isDefined())
            profile.define();

        return profile;
    }
}

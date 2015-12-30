package edu.mayo.aml.common;

import org.eclipse.emf.common.util.URI;
import org.eclipse.uml2.uml.Profile;

/**
 * Created by dks02 on 12/15/15.
 */
public class AMLProfile extends UMLModel
{
    public AMLProfile(URI uri)
    {
        super(uri);
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

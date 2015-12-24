package edu.mayo.aml.common;

import org.eclipse.emf.common.util.URI;
import org.eclipse.uml2.uml.Profile;

import java.io.File;

/**
 * Created by dks02 on 12/15/15.
 */
public class AMLProfile extends UMLModel
{
    public AMLProfile(File file)
    {
        super(file);
    }

    public AMLProfile(URI uri)
    {
        super(uri);
    }

    public AMLProfile(String uriPath)
    {
        super(uriPath);
    }

    public Profile getProfile()
    {
        if (this.getResource() == null)
            return null;

        Profile profile = (Profile) this.getRootPackage();

        profile.define();

        return profile;
    }
}

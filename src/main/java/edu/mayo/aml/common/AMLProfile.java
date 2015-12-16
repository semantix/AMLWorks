package edu.mayo.aml.common;

import org.eclipse.emf.common.util.URI;
import org.eclipse.uml2.uml.Model;

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

    public Model getModel()
    {
        if (this.getResource() == null)
            return null;

        return (Model) this.getRootPackage();
    }
}

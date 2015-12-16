package edu.mayo.aml.common;

import org.eclipse.emf.common.util.URI;
import org.eclipse.uml2.uml.Model;

import java.io.File;

/**
 * Created by dks02 on 12/15/15.
 */
public class ReferenceModel extends UMLModel
{
    public ReferenceModel(File file)
    {
        super(file);
    }

    public ReferenceModel(URI uri)
    {
        super(uri);
    }

    public ReferenceModel(String uriPath)
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

package edu.mayo.aml.services;

import org.eclipse.uml2.uml.Model;

/**
 * Created by dks02 on 12/21/15.
 */
public interface AMLReferenceModel
{
    // Get the Name of the model
    public String getName();

    // Get Description of the Reference Model
    public String getModelDescription();

    // get the contained UML Model after successful load
    public Model getUMLModel();

    public void setStatus(ReferenceModelStatus status);

    public ReferenceModelStatus getStatus();

    public boolean isDefaultReferenceModel();
}

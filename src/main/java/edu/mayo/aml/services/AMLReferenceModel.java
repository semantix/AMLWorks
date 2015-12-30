package edu.mayo.aml.services;

/**
 * Created by dks02 on 12/21/15.
 */
public interface AMLReferenceModel
{
    // Get Description of the Reference Model
    public String getDescription();

    // Get the Name of the model's root package
    public String getName();

    // Get "ReferenceModel" stereotyped Package Name
    public String getRMPackageName();

    // Get Namespace for the Reference Model
    public String getRMNamespace();

    // Get the Name of the publisher
    public String getRMPublisher();

    // Get Version of the Reference Model
    public String getRMVersion();

    public boolean isRMAvailable();
}

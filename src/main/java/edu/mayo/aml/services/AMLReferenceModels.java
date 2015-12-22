package edu.mayo.aml.services;

import java.util.Collection;
import java.util.Set;

/**
 * Created by dks02 on 12/21/15.
 */
public interface AMLReferenceModels
{
    // Retrieve all available Reference Model Names
    public Set<String> getAvailableReferenceModelNames();

    // Retrieve all available Reference Models
    public Collection<AMLReferenceModel> getAllReferenceModels();

    // Add Another Reference Model
    public boolean addReferenceModel(AMLReferenceModel newModel);

    // Remove and existing Reference Model
    public boolean removeReferenceModel(String name);

    // Activate a model
    public boolean activateReferenceModel(String modelName);

    // Deactivate a model
    public boolean deactivateReferenceModel(String modelName);

    // Get the contained AML Reference Model implementation object
    public AMLReferenceModel getReferenceModel(String modelName);

    // Get number of active Reference Models
    public int size();
}

package edu.mayo.aml.services.impl;

import edu.mayo.aml.services.AMLReferenceModel;
import edu.mayo.aml.services.AMLReferenceModels;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by dks02 on 12/22/15.
 */
public class AMLReferenceModelsImpl implements AMLReferenceModels
{
    // List of all the reference models
    private HashMap<String, AMLReferenceModel> models_ = new HashMap<String, AMLReferenceModel>();

    public Set<String> getAvailableReferenceModelNames()
    {
        return models_.keySet();
    }

    public Collection<AMLReferenceModel> getAllReferenceModels()
    {
        return models_.values();
    }

    public boolean addReferenceModel(String key, AMLReferenceModel newModel)
    {
        if (newModel == null)
            return false;

        if (!newModel.isRMAvailable())
            return false;

        String name = (!StringUtils.isEmpty(key))? key : newModel.getName();

        models_.put(name, newModel);

        return true;
    }

    public boolean removeReferenceModel(String name)
    {
        if (StringUtils.isEmpty(name))
            return false;

        if (models_.containsKey(name))
        {
            models_.remove(name);
            return true;
        }

        return false;
    }

    public boolean activateReferenceModel(String modelName)
    {
        AMLReferenceModel model = getReferenceModel(modelName);

        if (model == null)
            return false;

        return true;
    }

    public boolean deactivateReferenceModel(String modelName)
    {
        AMLReferenceModel model = getReferenceModel(modelName);

        if (model == null)
            return false;

        return true;
    }

    public AMLReferenceModel getReferenceModel(String modelName)
    {
        if (StringUtils.isEmpty(modelName))
            return null;

        return models_.get(modelName);
    }

    public int size()
    {
        return models_.size();
    }
}

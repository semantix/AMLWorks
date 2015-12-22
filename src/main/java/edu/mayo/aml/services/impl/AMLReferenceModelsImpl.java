package edu.mayo.aml.services.impl;

import edu.mayo.aml.services.AMLReferenceModel;
import edu.mayo.aml.services.AMLReferenceModels;
import edu.mayo.aml.services.ReferenceModelStatus;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * Created by dks02 on 12/22/15.
 */
public class AMLReferenceModelsImpl implements AMLReferenceModels
{
    // List of all the reference models
    private HashMap<String, AMLReferenceModel> models_ = new HashMap<String, AMLReferenceModel>();
    private Vector<String> noNameModels_ = new Vector<String>();

    public Set<String> getAvailableReferenceModelNames()
    {
        return models_.keySet();
    }

    public Collection<AMLReferenceModel> getAllReferenceModels()
    {
        return models_.values();
    }

    public boolean addReferenceModel(AMLReferenceModel newModel)
    {
        if (newModel == null)
            return false;

        String name = StringUtils.isEmpty(newModel.getName())?
                    ("NO_NAME_GIVEN_" + (noNameModels_.size() + 1)):
                    newModel.getName();

        noNameModels_.add(name);

        newModel.setStatus(ReferenceModelStatus.ACTIVE);

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

        model.setStatus(ReferenceModelStatus.ACTIVE);

        return true;
    }

    public boolean deactivateReferenceModel(String modelName)
    {
        AMLReferenceModel model = getReferenceModel(modelName);

        if (model == null)
            return false;

        model.setStatus(ReferenceModelStatus.DEACTIVATED);

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

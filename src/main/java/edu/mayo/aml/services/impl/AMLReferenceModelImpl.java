package edu.mayo.aml.services.impl;

import edu.mayo.aml.common.UMLModel;
import edu.mayo.aml.conf.AMLEnvironment;
import edu.mayo.aml.services.AMLReferenceModel;
import edu.mayo.aml.services.ReferenceModelStatus;
import edu.mayo.utils.AMLPrintUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.emf.common.util.URI;
import org.eclipse.uml2.uml.*;
import org.eclipse.uml2.uml.Class;
import org.openhealthtools.mdht.uml.common.search.ModelSearch;
import org.openhealthtools.mdht.uml.common.util.UMLUtil;

import java.io.File;

/**
 * Created by dks02 on 12/15/15.
 */
public class AMLReferenceModelImpl extends UMLModel implements AMLReferenceModel
{
    private String modelName_ = null;
    private ReferenceModelStatus status_ = ReferenceModelStatus.UNKNOWN;

    private String ns_ = null;
    private String publisher_ = null;
    private String package_ = null;
    private String version_ = null;
    private String desc_ = null;

    public AMLReferenceModelImpl(String name, File file)
    {
        super(file);
        this.modelName_ = (StringUtils.isEmpty(name)? file.getName() : name );
    }

    public AMLReferenceModelImpl(String name, URI uri)
    {
        super(uri);
        this.modelName_ = name;
    }

    public AMLReferenceModelImpl(String name, String uriPath)
    {
        super(uriPath);
        this.modelName_ = (StringUtils.isEmpty(name)? this.uri_.lastSegment() : name );;
    }

    public Model getUMLModel()
    {
        if (this.getResource() == null)
            return null;

        return (Model) this.getRootPackage();
    }

    public void setStatus(ReferenceModelStatus status)
    {
        this.status_ = status;
    }

    public ReferenceModelStatus getStatus()
    {
        return this.status_;
    }

    public boolean isDefaultReferenceModel()
    {
        return AMLEnvironment.isModelDefault(this.modelName_);
    }

    public String getName()
    {
        return this.modelName_;
    }

    public String getModelDescription()
    {
        if (desc_ == null)
            desc_ = AMLEnvironment.getModelDescription(this.modelName_);

        if (desc_ == null)
            desc_ = modelName_;

        return desc_;
    }

    public void setModelDescription(String desc)
    {
        desc_ = desc;
    }

    public String getModelVersion()
    {
        if (version_ == null)
            version_ = AMLEnvironment.getModelVersion(this.modelName_);

        if (version_ == null)
            version_ = "Unknown";

        return version_;
    }

    public void setModelVersion(String version)
    {
        version_ = version;
    }

    public String getModelNamespace()
    {
        if (ns_ == null)
            ns_ = AMLEnvironment.getModelNameSpace(this.modelName_);

        if (ns_ == null)
            ns_ = "";

        return ns_;
    }

    public void setModelNamespace(String ns)
    {
        ns_ = ns;
    }

    public String getModelPublisher()
    {
        if (publisher_ == null)
            publisher_ = AMLEnvironment.getModelPublisher(this.modelName_);

        if (publisher_ == null)
            publisher_ = "";

        return publisher_;
    }

    public void setModelPublisher(String publisher)
    {
        publisher_ = publisher;
    }

    public String getModelPackage()
    {
        if (package_ == null)
            package_ = AMLEnvironment.getModelPackage(this.modelName_);

        if (package_ == null)
            package_ = "";

        return package_;
    }

    public void setModelPackage(String modelPackage)
    {
        package_ = modelPackage;
    }

    public Class getRMClass(String className)
    {
        UMLUtil.getClassByName(getRootPackage(), className);

    }

    public void print()
    {
        AMLPrintUtils.printUMLModel(getUMLModel());
    }

    public String toString()
    {
        return "(name:\"" + this.modelName_ + "\"" +
                " <Description:" + getModelDescription() +
                ">, <RMStatus:" + this.status_ +
                ">, <Default:" + isDefaultReferenceModel() +
                ">)[" + this.uri_ + "]";
    }
}

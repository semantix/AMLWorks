package edu.mayo.aml.common;

import org.eclipse.emf.common.util.EList;
import org.eclipse.mdht.uml.common.search.IElementFilter;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLPackage;

public class StereoTypeFilter implements IElementFilter
{
    public StereoTypeFilter()
    {
    }

    public boolean accept(Element element)
    {
        EList<Stereotype> as = element.getAppliedStereotypes();
        System.out.println("Element eClass=" + as.size());
        if (as.size() > 0)
            System.out.println("here");
        return element.eClass() == UMLPackage.Literals.STEREOTYPE;
    }
}

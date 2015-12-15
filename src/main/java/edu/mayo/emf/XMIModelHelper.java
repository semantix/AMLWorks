package edu.mayo.emf;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by dks02 on 12/12/15.
 */
public class XMIModelHelper
{
    private static String ANYTYPE = "AnyType";
    final static Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(XMIModelHelper.class);

    public static void printEObject(EObject eo, String indent)
    {
        if (eo == null)
            logger.warn("EObject is null!");

//        EcoreSwitch<EList<EClassifier>> ecoreSwitchPkg = new EcoreSwitch<EList<EClassifier>>()
//        {
//            @Override
//            public EList<EClassifier> caseEPackage(EPackage pkg)
//            {
//                return pkg.getEClassifiers();
//            }
//        };
//
//        EcoreSwitch<EList<EPackage>> ecoreSwitchSubPkg = new EcoreSwitch<EList<EPackage>>()
//        {
//            @Override
//            public EList<EPackage> caseEPackage(EPackage pkg)
//            {
//                return pkg.getESubpackages();
//            }
//        };
//
//        EcoreSwitch<EClass> ecoreSwitchCls = new EcoreSwitch<EClass>()
//        {
//            public EClass caseEClass(EClass cls)
//            {
//                return cls;
//            }
//        };
//
//        EList<EClassifier> packages = ecoreSwitchPkg.doSwitch(eo);
//
//        EList<EPackage> subpackages = ecoreSwitchSubPkg.doSwitch(eo);
//          EClass ec = ecoreSwitchCls.doSwitch(eo);

        String type = eo.eClass().getName();

        if (XMIModelHelper.ANYTYPE.equals(type))
            return;

        EClass eClass = (EClass) eo.eClass();
        logger.info(indent + "EClass=" + eClass.getName());

        String instnaceClass = eClass.getInstanceClassName();

        for (EObject econt : eo.eContents())
        {
            logger.info(indent + "\tEContent=" + econt.eClass().getName() +
                       // "{DataType=" + econt.getEAttributeType().getName() +
                       // " [" + econt.getLowerBound() + " .. " + econt.getUpperBound() + "]" +
                        "}");
            //EStructuralFeature feature = eAttribute.eContainingFeature();
            //logger.info(indent + "\tFeature=" + feature.getEType().getName());

            logger.info("here");
        }

        EList<EObject> eoclsfrs = eo.eContents();

        for (EObject eoc : eoclsfrs)
        {
            //EObject eoc = eoclsfrs.next();
            //System.out.println(indent + "Content=" + eoc.eClass().getName());
            XMIModelHelper.printEObject(eoc, indent + "\t");
        }

        //logger.info("-------");
    }
}

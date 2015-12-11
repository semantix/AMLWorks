package edu.mayo.aml.main;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.BasicResourceHandler;
import org.eclipse.emf.ecore.xml.type.AnyType;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by dks02 on 12/9/15.
 */
public class RulesResourceHandler extends BasicResourceHandler
{
        public void postLoad(XMLResource resource, InputStream inputStream, Map options) {
            final Map extMap = resource.getEObjectToExtensionMap();
            for(Iterator itr = extMap.entrySet().iterator(); itr.hasNext();) {
                Map.Entry entry = (Map.Entry) itr.next();
                EObject key = (EObject) entry.getKey();
                AnyType value = (AnyType) entry.getValue();
                handleUnknownData(key, value);
            }
        }

        private void handleUnknownData(EObject eObj, AnyType unknownData) {
            handleUnknownFeatures(eObj, unknownData.getMixed());
            handleUnknownFeatures(eObj, unknownData.getAnyAttribute());
        }

        private void handleUnknownFeatures(EObject owner, FeatureMap featureMap) {
            for (Iterator iter = featureMap.iterator(); iter.hasNext();) {
                FeatureMap.Entry entry = (FeatureMap.Entry) iter.next();
                EStructuralFeature f = entry.getEStructuralFeature();
                //if(handleUnknownFeature(owner, f, entry.getValue())) {
                  //  iter.remove();
                //}
            }
        }
}

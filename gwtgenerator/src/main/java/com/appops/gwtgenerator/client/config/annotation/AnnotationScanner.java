package com.appops.gwtgenerator.client.config.annotation;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.appops.gwtgenerator.client.generator.Driver;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JPackage;
import com.google.gwt.core.ext.typeinfo.TypeOracle;

public class AnnotationScanner {

	public static Map<Class<? extends Annotation>, List<JClassType>> scan(TreeLogger logger, TypeOracle typeOracle,
			Class<? extends Annotation>[] annotationClasses) {

		Map<Class<? extends Annotation>, List<JClassType>> annotationMap = new HashMap<Class<? extends Annotation>, List<JClassType>>();
		for (Class<? extends Annotation> c : annotationClasses) {
			annotationMap.put(c, new ArrayList<JClassType>());
		}

		int nbClasses = 0;

		Date start = new Date();

		for (JPackage pack : typeOracle.getPackages()) {
			//System.out.println(pack.getName());
			//if (pack.getName().equalsIgnoreCase("in.appops.platform.core.shared")) {
			//	System.out.println(pack.getName());
			for (JClassType type : pack.getTypes()) {
				//	System.out.println(type.getName());
				for (Class<? extends Annotation> c : annotationClasses) {
					if (type.getAnnotation(c) != null) {
						annotationMap.get(c).add(type);
					}
				}
			}
			//}
			nbClasses += pack.getTypes().length;
		}

		Date end = new Date();

		logger.log(TreeLogger.INFO, nbClasses + " classes scanned in " + Long.toString(end.getTime() - start.getTime())
				+ " ms.");

		return annotationMap;
	}
}

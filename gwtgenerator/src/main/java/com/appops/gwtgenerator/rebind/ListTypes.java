package com.appops.gwtgenerator.rebind;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JPackage;

public class ListTypes extends Generator {
	
	@Override
	public String generate(TreeLogger logger, GeneratorContext context, String typeName) throws UnableToCompleteException {
		
		for (JPackage pack : context.getTypeOracle().getPackages()) {
			//System.out.println(pack.getName());
			//	if (pack.getName().equalsIgnoreCase("in.appops.platform.core.operation")) {
			System.out.println("PACKAGE::==>" + pack.getName());
			for (JClassType type : pack.getTypes()) {
				System.out.println("type-" + type.getName());
			}
			//}
			
		}
		return null;
	}
	
}

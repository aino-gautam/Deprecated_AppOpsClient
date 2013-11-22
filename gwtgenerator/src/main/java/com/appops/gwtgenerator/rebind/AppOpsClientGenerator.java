package com.appops.gwtgenerator.rebind;

import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

import com.appops.gwtgenerator.client.config.annotation.AnnotationScanner;
import com.appops.gwtgenerator.client.config.annotation.Attributes;
import com.appops.gwtgenerator.client.config.annotation.EventSet;
import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

public class AppOpsClientGenerator extends Generator {
	
	/** Simple name of class to be generated */
	private String				className	= null;
	
	/** Package name of class to be generated */
	private String				packageName	= null;
	
	/** Fully qualified class name passed into GWT.create() */
	private String				typeName	= null;
	
	private AnnotationScanner	annotationScanner;
	
	// inherited generator method
	public String generate(TreeLogger logger, GeneratorContext context, String typeName) throws UnableToCompleteException {
		TypeOracle typeOracle = context.getTypeOracle();
		
		annotationScanner = new AnnotationScanner();
		Class[] annotationClazzArray = new Class[] { Attributes.class };
		Map<Class<? extends Annotation>, List<JClassType>> scanResult = annotationScanner.scan(logger, typeOracle, annotationClazzArray);
		
		for (List<JClassType> list : scanResult.values()) {
			for (JClassType jClassType : list) {
				Annotation[] annotations = jClassType.getAnnotations();
				for (Annotation annotation : annotations) {
					if (annotation instanceof EventSet)
						System.out.println(annotation.getClass().getSimpleName());
				}
			}
		}
		
		/*this.typeName = typeName;
		
		try {
			for (JClassType types : typeOracle.getTypes()) {
				// get classType and save instance variables
				JClassType classType = typeOracle.getType(typeName);
				packageName = classType.getPackage().getName();
				className = classType.getSimpleSourceName() + "Stub";
				// Generate class source code
				generateStubClass(logger, context);
			}
			
		}
		catch (Exception e) {
			logger.log(TreeLogger.ERROR, "ERROR!!!", e);
		}
		
		// return the fully qualifed name of the class generated
		return packageName + "." + className;*/
		return null;
		
	}
	
	/**
	 * Generate source code for new class. Class extends <code>HashMap</code>.
	 * 
	 * @param logger
	 *            Logger object
	 * @param context
	 *            Generator context
	 */
	private void generateStubClass(TreeLogger logger, GeneratorContext context) {
		
		// get print writer that receives the source code
		PrintWriter printWriter = null;
		printWriter = context.tryCreate(logger, packageName, className);
		// print writer if null, source code has ALREADY been generated, return
		if (printWriter == null)
			return;
		
		// init composer, set class properties, create source writer
		ClassSourceFileComposerFactory composer = null;
		composer = new ClassSourceFileComposerFactory(packageName, className);
		//composer.setSuperclass("java.util.HashMap");
		SourceWriter sourceWriter = null;
		sourceWriter = composer.createSourceWriter(context, printWriter);
		
		// generator constructor source code
		//generateConstructor(sourceWriter);
		// close generated class
		sourceWriter.outdent();
		sourceWriter.println("}");
		
		// commit generated class
		context.commit(logger, printWriter);
		
	}
	
}

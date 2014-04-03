package com.appops.gwtgenerator.rebind;

import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.appops.gwtgenerator.client.config.annotation.AnnotationScanner;
import com.appops.gwtgenerator.client.config.annotation.Tag;
import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

public class LibraryTagProviderGenerator extends Generator {
	/** Simple name of class to be generated */
	private String				className			= null;
	private final String		APPOPS				= "AppOps";
	/** Fully qualified class name passed into GWT.create() */
	private String				typeName			= null;
	/** Package name of class to be generated */
	private String				packageName			= "com.appops.gwtgenerator.client.component.generated";
	private JClassType			classType;
	// need to find a way to pass it dynamically or it should be figured
	// dynamically
	private String				generatedClassName;
	private TypeOracle			typeOracle;
	
	private GeneratorContext	context;
	private List<String>		generatedClasses	= new ArrayList<String>();
	
	@Override
	public String generate(TreeLogger logger, GeneratorContext context, String typeName) throws UnableToCompleteException {
		this.typeName = typeName;
		this.context = context;
		typeOracle = context.getTypeOracle();
		try {
			// get classType and save instance variables
			classType = typeOracle.getType(typeName);
			className = APPOPS + classType.getSimpleSourceName();
			generatedClassName = packageName + "." + className;
			generateStubClass(logger, context);
			//generateDynamicInstanctiator(logger);
			
			// return the fully qualifed name of the class generated return
			return generatedClassName;
		}
		catch (Exception e) {
			logger.log(TreeLogger.ERROR, "ERROR!!!", e);
			throw new UnableToCompleteException();
		}
	}
	
	/*private void generateDynamicInstanctiator(TreeLogger logger) throws UnableToCompleteException, NotFoundException {
		classType = typeOracle.getType("com.appops.gwtgenerator.client.generator.DynamicInstantiator");
		typeName = classType.getSimpleSourceName();
		DynamicInstantiatorGenerator instantiatorGenerator = new DynamicInstantiatorGenerator();
		instantiatorGenerator.generate(logger, context, classType, typeName, generatedClasses);
	}*/
	
	/**
	 * Generate source code for new class. Class extends <code>TextBox</code>.
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
		composer.setSuperclass(typeName);
		
		SourceWriter sourceWriter = composer.createSourceWriter(context, printWriter);
		
		// generate constructor source code
		generateConstructor(logger, composer, sourceWriter);
		
		// close generated class
		sourceWriter.outdent();
		sourceWriter.println("}");
		
		// commit generated class
		context.commit(logger, printWriter);
	}
	
	private void generateConstructor(TreeLogger logger, ClassSourceFileComposerFactory composer, SourceWriter sourceWriter) {
		sourceWriter.println("public " + className + "(){ ");
		sourceWriter.indent();
		sourceWriter.println("super();");
		Class[] clazzarray = { Tag.class };
		
		Map<Class<? extends Annotation>, List<JClassType>> map = AnnotationScanner.scan(logger, typeOracle, clazzarray);
		List<JClassType> list = map.get(Tag.class);
		//StubGenerator stubGenerator = new StubGenerator();
		for (JClassType jClassType : list) {
			try {
				//String generatedClass = stubGenerator.generate(logger, context, jClassType.getName(), jClassType);
				//generatedClasses.add(generatedClass);
				//composer.addImport(generatedClass);
				Tag tag = jClassType.getAnnotation(Tag.class);
				//sourceWriter.println("this.add(\"" + tag.library() + "\",\"" + tag.tagname() + "\",\"" + ((generatedClass != null) ? generatedClass : tag.classname()) + "\");");
				sourceWriter.println("this.add(\"" + tag.library() + "\",\"" + tag.tagname() + "\",\"" + jClassType.getQualifiedSourceName() + "\");");
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		sourceWriter.outdent();
		sourceWriter.println("}");
	}
}

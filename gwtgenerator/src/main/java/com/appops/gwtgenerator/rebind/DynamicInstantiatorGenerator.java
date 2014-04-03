package com.appops.gwtgenerator.rebind;

import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

import com.appops.gwtgenerator.client.config.annotation.AnnotationScanner;
import com.appops.gwtgenerator.client.config.annotation.Tag;
import com.appops.gwtgenerator.client.config.util.SuperClassTypeScanner;
import com.appops.gwtgenerator.client.generator.Driver;
import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

public class DynamicInstantiatorGenerator extends Generator {
	/** Simple name of class to be generated */
	private String			className	= null;
	private final String	APPOPS		= "AppOps";
	private String			typeName	= null;
	private String			packageName	= "com.appops.gwtgenerator.client.components.generated";
	private JClassType		classType;
	private String			generatedClassName;
	
	@Override
	public String generate(TreeLogger logger, GeneratorContext context, String typeName) throws UnableToCompleteException {
		try {
			this.typeName = typeName;
			TypeOracle typeOracle = context.getTypeOracle();
			classType = typeOracle.getType(typeName);
			packageName = classType.getPackage().getName();
			className = APPOPS + classType.getSimpleSourceName();
			generatedClassName = packageName + "." + className;
			generateStubClass(logger, context);
			
			return generatedClassName;
		}
		catch (Exception e) {
			logger.log(TreeLogger.ERROR, "ERROR!!!", e);
			throw new UnableToCompleteException();
		}
	}
	
	/*
	 * public String generate(TreeLogger logger, GeneratorContext context,
	 * JClassType classType, String typeName, List generatedClasses) throws
	 * UnableToCompleteException { try { this.typeName = typeName; // get
	 * classType and save instance variables this.classType = classType;
	 * packageName = classType.getPackage().getName(); className = APPOPS +
	 * classType.getSimpleSourceName(); generatedClassName = packageName + "." +
	 * className; generateStubClass(logger, context, generatedClasses); //
	 * return the fully qualifed name of the class generated return return
	 * generatedClassName; } catch (Exception e) { logger.log(TreeLogger.ERROR,
	 * "ERROR!!!", e); throw new UnableToCompleteException(); } }
	 */
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
		composer.addImport("com.appops.gwtgenerator.client.generator.Dynamic");
		composer.addImport("com.appops.gwtgenerator.client.generator.Driver");
		
		composer.addImport("com.google.gwt.core.client.GWT");
		Class[] clazzarray = { Tag.class };
		
		Map<Class<? extends Annotation>, List<JClassType>> map = AnnotationScanner.scan(logger, context.getTypeOracle(), clazzarray);
		List<JClassType> list = map.get(Tag.class);
		SourceWriter sourceWriter = composer.createSourceWriter(context, printWriter);
		
		sourceWriter.println("@Override ");
		sourceWriter.println("public Dynamic getInstance(String correspondingClass) throws Exception {");
		sourceWriter.indent();
		sourceWriter.println("try {");
		
		for (JClassType jClassType : list) {
			try {
				sourceWriter.println("if(correspondingClass.equalsIgnoreCase(\"" + jClassType.getQualifiedSourceName() + "\"))");
				sourceWriter.println("return GWT.create(" + jClassType.getQualifiedSourceName() + ".class);");
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		sourceWriter.println("}");
		sourceWriter.println("catch (Exception e) {");
		sourceWriter.println("e.printStackTrace();");
		sourceWriter.println("	throw e;");
		sourceWriter.println("}");
		sourceWriter.println("return null;");
		sourceWriter.outdent();
		sourceWriter.println("}");
		
		sourceWriter.println("@Override ");
		sourceWriter.println("public Driver getDriverInstance(String correspondingClass) throws Exception {");
		sourceWriter.indent();
		sourceWriter.println("try {");
		sourceWriter.println("System.out.println(\"in getDriverInstanceMethod-=>\"+correspondingClass);");
		
		List<JClassType> listOfSubtypes = SuperClassTypeScanner.scan(logger, context.getTypeOracle(), Driver.class);
		
		for (JClassType jClassType : listOfSubtypes) {
			try {
				sourceWriter.println("if(correspondingClass.equalsIgnoreCase(\"" + jClassType.getQualifiedSourceName() + "\"))");
				sourceWriter.println("return GWT.create(" + jClassType.getQualifiedSourceName() + ".class);");
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		sourceWriter.println("}");
		sourceWriter.println("catch (Exception e) {");
		sourceWriter.println("e.printStackTrace();");
		sourceWriter.println("	throw e;");
		sourceWriter.println("}");
		sourceWriter.println("return null;");
		sourceWriter.outdent();
		sourceWriter.println("}");
		
		// close generated class
		sourceWriter.outdent();
		sourceWriter.println("}");
		
		// commit generated class
		context.commit(logger, printWriter);
	}
	
}

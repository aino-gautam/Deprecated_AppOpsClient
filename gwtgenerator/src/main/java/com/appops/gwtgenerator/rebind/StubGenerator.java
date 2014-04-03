package com.appops.gwtgenerator.rebind;

import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Property;
import in.appops.platform.core.shared.Configuration;

import java.io.PrintWriter;
import java.util.Map.Entry;

import com.appops.gwtgenerator.client.config.util.Snippet;
import com.appops.gwtgenerator.client.event.AppOpsEventHandler;
import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

// create a class that is the child of the class with event handler rule map configuration
// register this class for handling the events

// creates the wrapper class that is derived from the given class and implements the stub interface.

// implements the overriden methods for im and up and gp methods
// implements the presenter getter and setter methods
// adds tags to the generated classes 
// also Constructs a provider after all the stubs are generated.

public class StubGenerator extends Generator {

	/** Simple name of class to be generated */
	private String className = null;
	private final String APPOPS = "AppOps";
	/** Fully qualified class name passed into GWT.create() */
	private String typeName = null;
	/** Package name of class to be generated */
	private String packageName = "com.appops.gwtgenerator.client.components.generated";
	private JClassType classType;
	// need to find a way to pass it dynamically or it should be figured dynamically
	private String libraryName = "core";
	private String generatedClassName;

	public StubGenerator() {
		// TODO Auto-generated constructor stub
	}

	// inherited generator method
	public String generate(TreeLogger logger, GeneratorContext context, String typeName)
			throws UnableToCompleteException {
		this.typeName = typeName;
		TypeOracle typeOracle = context.getTypeOracle();
		try {
			// get classType and save instance variables
			classType = typeOracle.getType(typeName);
			packageName = classType.getPackage().getName();
			className = APPOPS + classType.getSimpleSourceName();
			generatedClassName = packageName + "." + className;
			generateStubClass(logger, context);

			// return the fully qualifed name of the class generated return
			return generatedClassName;
		}
		catch (Exception e) {
			logger.log(TreeLogger.ERROR, "ERROR!!!", e);
			throw new UnableToCompleteException();
		}
	}

	// inherited generator method
	public String generate(TreeLogger logger, GeneratorContext context, String typeName, JClassType classType)
			throws UnableToCompleteException {
		this.typeName = typeName;
		try {
			// get classType and save instance variables
			this.classType = classType;
			packageName = classType.getPackage().getName();
			className = APPOPS + classType.getSimpleSourceName();
			generatedClassName = packageName + "." + className;
			generateStubClass(logger, context);
			// return the fully qualifed name of the class generated return
			return generatedClassName;
		}
		catch (Exception e) {
			logger.log(TreeLogger.ERROR, "ERROR!!!", e);
			throw new UnableToCompleteException();
		}
	}

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
		System.out.println(typeName);
		composer.setSuperclass(typeName);

		composer.addImplementedInterface("com.appops.gwtgenerator.client.generator.Dynamic");
		//adding Tag annotation 
		//composer.addImport(com.appops.gwtgenerator.client.config.annotation.Tag.class.getCanonicalName());

		/*composer.addAnnotationDeclaration("@" + Tag.class.getSimpleName() + "(tagname = \"" + classType.getSimpleSourceName() + "\", library = \"" + libraryName + "\", classname = \""
				+ generatedClassName + "\")");*/

		//add import statement for Presenter
		composer.addImport(RootPanel.class.getCanonicalName());
		composer.addImport(java.io.Serializable.class.getCanonicalName());
		composer.addImport(java.util.ArrayList.class.getCanonicalName());
		composer.addImport(Snippet.class.getCanonicalName());
		composer.addImport(Configuration.class.getCanonicalName());
		composer.addImport(Entity.class.getCanonicalName());
		composer.addImport(Property.class.getCanonicalName());
		composer.addImport(Entry.class.getCanonicalName());
		composer.addImport(AppOpsEventHandler.class.getCanonicalName());
		SourceWriter sourceWriter = composer.createSourceWriter(context, printWriter);

		//TODO clean up the dynamic and configuration helper methods here.. 
		// generate constructor source code
		generateConstructor(composer, sourceWriter, context);

		// in general all the methods that are to be implemented from the Dynamic interface.

		// generate "im" method implementation in newly generated class
		generateMethodIM(composer, sourceWriter);

		// this method is required as the elements are replaced on the DOM in order to attach them to dom following method has to be implemented.
		generateMethodAttach(composer, sourceWriter);

		generateParentSnippetAccessMethods(composer, sourceWriter);

		generateInitializeAccessMethod(composer, sourceWriter);

		generateConfigurationAccessMethods(composer, sourceWriter);

		generateIDAccessMethods(composer, sourceWriter);
		// close generated class
		sourceWriter.outdent();
		sourceWriter.println("}");

		// commit generated class
		context.commit(logger, printWriter);
	}

	private void generateIDAccessMethods(ClassSourceFileComposerFactory composer, SourceWriter sourceWriter) {
		CustomGeneratorHelper helper = new CustomGeneratorHelper();
		helper.generateIDAccessMethodsImpl(composer, sourceWriter, classType);
	}

	private void generateConfigurationAccessMethods(ClassSourceFileComposerFactory composer, SourceWriter sourceWriter) {
		CustomGeneratorHelper helper = new CustomGeneratorHelper();
		helper.generateConfigurationAccessMethodsImpl(composer, sourceWriter, classType);
	}

	private void generateInitializeAccessMethod(ClassSourceFileComposerFactory composer, SourceWriter sourceWriter) {
		CustomGeneratorHelper helper = new CustomGeneratorHelper();
		helper.generateInitializeAccessMethodImpl(composer, sourceWriter, classType);
	}

	private void generateParentSnippetAccessMethods(ClassSourceFileComposerFactory composer, SourceWriter sourceWriter) {
		CustomGeneratorHelper helper = new CustomGeneratorHelper();
		helper.generateParentSnippetAccessMethodsImpl(composer, sourceWriter, classType);
	}

	/**
	 * Generate source code for the default constructor. Create default constructor, create the parameter list to invoke
	 * setText method on text box
	 * 
	 * @param sourceWriter
	 *            Source writer to output source code
	 */
	private void generateMethodIM(ClassSourceFileComposerFactory composer, SourceWriter sourceWriter) {
		CustomGeneratorHelper helper = new CustomGeneratorHelper();
		helper.generateImMethodImpl(composer, sourceWriter, classType);
	}

	private void generateMethodAttach(ClassSourceFileComposerFactory composer, SourceWriter sourceWriter) {
		CustomGeneratorHelper helper = new CustomGeneratorHelper();
		helper.generateAttachMethodImpl(composer, sourceWriter, classType);

	}

	/**
	 * Generate source code for the default constructor. Create default constructor, create the parameter list to invoke
	 * setText method on text box
	 * 
	 * @param sourceWriter
	 *            Source writer to output source code
	 * @param context
	 */
	private void generateConstructor(ClassSourceFileComposerFactory composer, SourceWriter sourceWriter,
			GeneratorContext context) {
		CustomGeneratorHelper helper = new CustomGeneratorHelper();
		helper.generateConstructor(composer, sourceWriter, classType, context);
	}

}

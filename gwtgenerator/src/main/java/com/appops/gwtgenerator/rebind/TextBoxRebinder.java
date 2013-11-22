package com.appops.gwtgenerator.rebind;

import java.io.PrintWriter;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

public class TextBoxRebinder extends Generator {
	
	/** Simple name of class to be generated */
	private String	className	= "AppOpsTextBox";
	
	/** Package name of class to be generated */
	private String	packageName	= "com.appops.gwtgenerator.client.components.generated";
	
	// inherited generator method
	public String generate(TreeLogger logger, GeneratorContext context, String typeName) throws UnableToCompleteException {
		
		try {
			generateStubClass(logger, context);
		}
		catch (Exception e) {
			logger.log(TreeLogger.ERROR, "ERROR!!!", e);
		}
		// return the fully qualifed name of the class generated return
		String generatedClassName = packageName + "." + className;
		
		return generatedClassName;
		
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
		composer.setSuperclass("com.google.gwt.user.client.ui.TextBox");
		
		composer.addAnnotationDeclaration("@Export");
		composer.addImplementedInterface("org.timepedia.exporter.client.Exportable");
		
		generateImports(composer);
		SourceWriter sourceWriter = null;
		sourceWriter = composer.createSourceWriter(context, printWriter);
		
		// generate import statements for the new class being generated
		
		// generate constructor source code
		generateConstructor(sourceWriter);
		
		// generate "im" method implementation in newly generated class
		generateMethodIM(sourceWriter);
		
		// overriding setTextMethod
		generateCodeForSetTextMethod(sourceWriter);
		
		// close generated class
		sourceWriter.outdent();
		sourceWriter.println("}");
		
		// commit generated class
		context.commit(logger, printWriter);
	}
	
	private void generateCodeForSetTextMethod(SourceWriter sourceWriter) {
	
		sourceWriter.println("@Override");
		sourceWriter.println("public void setText(String text) {");
		sourceWriter.println("super.setText(text);");
		sourceWriter.println("}");
	}
	
	/**
	 * Generate the import statements required by the class implementation
	 * @param composer
	 */
	private void generateImports(ClassSourceFileComposerFactory composer) {
		composer.addImport("java.util.ArrayList");
		composer.addImport("com.google.gwt.core.client.GWT");
		composer.addImport("org.timepedia.exporter.client.Export");
		composer.addImport("org.timepedia.exporter.client.Exportable");
	}
	
	/**
	 * Generate source code for the default constructor. Create default
	 * constructor, create the parameter list to invoke setText method on text box
	 * 
	 * @param sourceWriter
	 *            Source writer to output source code
	 */
	private void generateMethodIM(SourceWriter sourceWriter) {
		sourceWriter.println("public static native Object invokeMethod(AppOpsTextBox appopsTextBox, String obfuscatedMethodName, ArrayList parameters) /*-{");
		sourceWriter.indent();
		sourceWriter.println("$wnd.alert(obfuscatedMethodName);");
		sourceWriter.println("var theInstance = this;");
		sourceWriter.println("var methodName = obfuscatedMethodName;");

		sourceWriter.println("theInstance.methodName(\"this is set by me by performing magic!!\");");
		sourceWriter.println("return null;");
		sourceWriter.outdent();
		sourceWriter.println("}-*/;");
	}
	
	/**
	 * Generate source code for the default constructor. Create default
	 * constructor, create the parameter list to invoke setText method on text box
	 * 
	 * @param sourceWriter
	 *            Source writer to output source code
	 */
	private void generateConstructor(SourceWriter sourceWriter) {
		// start constructor source generation
		sourceWriter.println("public " + className + "() { ");
		sourceWriter.indent();
		//	sourceWriter.println("super();");
		sourceWriter.println("try {");
		sourceWriter.println("ArrayList<String> parameters = new ArrayList<String>();");
		sourceWriter.println("parameters.add(\"I was invoked by calling the obfuscated version of the method setText\");");
		//sourceWriter.println("this.setText(\"Initial set text\");");
		
		sourceWriter.println("Object myValue = invokeMethod(this,\"setText\", parameters);");
		sourceWriter.println("GWT.log(\"Got value \" + myValue, null);");
		sourceWriter.println("}");
		sourceWriter.println("catch (Exception e) {");
		sourceWriter.println("GWT.log(\"JSNI method invokeMethod() threw an exception:\", e);");
		sourceWriter.println("}");
		
		// end constructor source generation
		sourceWriter.outdent();
		sourceWriter.println("}");
	}
}

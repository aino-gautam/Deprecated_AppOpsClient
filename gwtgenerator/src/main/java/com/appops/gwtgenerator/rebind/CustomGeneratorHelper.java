package com.appops.gwtgenerator.rebind;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JConstructor;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.core.ext.typeinfo.JPrimitiveType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

// needs to scan the class for public methods 
// creates a switch or if else blocks to invoke these methods.

public class CustomGeneratorHelper {
	
	public CustomGeneratorHelper() {
	}
	
	// scans the class for public methods, creates the methods invoking
	// conditional block code
	// find a way to add methods only for getters setters starting with is / get
	// / set
	
	public void generateImMethodImpl(ClassSourceFileComposerFactory composer, SourceWriter sourceWriter, JClassType classType) {
		sourceWriter.println("@Override ");
		sourceWriter.println("public Object im(String methodName, ArrayList<Serializable> parameters) throws Exception {");
		sourceWriter.indent();
		JMethod[] jMethods = classType.getInheritableMethods();
		
		for (int j = 0; j < jMethods.length; j++) {
			JMethod jMethod = jMethods[j];
			String methodName = jMethod.getName();
			try {
				if (jMethod.isPublic()) {
					if ((methodName.startsWith("add"))/*"get")) || methodName.startsWith("set") || methodName.startsWith("is")*/) {
						System.out.println(methodName);
						JParameter[] methodParameters = jMethod.getParameters();
						sourceWriter.println("if(methodName.equalsIgnoreCase(\"" + methodName + "\")){");
						sourceWriter.indent();
						if (!jMethod.getReturnType().getSimpleSourceName().equalsIgnoreCase(Void.TYPE.toString()))
							sourceWriter.println("return ");
						sourceWriter.print(methodName + "(");
						if (methodParameters.length > 0) {
							for (int i = 0; i < methodParameters.length; i++) {
								JType parameterType = methodParameters[i].getType();
								String sourceStr = "";
								if (parameterType.isPrimitive() != null) {
									JPrimitiveType jPrimitiveType = parameterType.isPrimitive();
									if (jPrimitiveType.equals(JPrimitiveType.INT)) {
										sourceStr = "Integer.parseInt(parameters.get(" + i + ").toString())";
									}
									if (jPrimitiveType.equals(JPrimitiveType.BOOLEAN)) {
										sourceStr = "Boolean.parseBoolean(parameters.get(" + i + ").toString())";
									}
									if (jPrimitiveType.equals(JPrimitiveType.CHAR)) {
										sourceStr = "(parameters.get(" + i + ").toString()).charAt(0)";
									}
								}
								else {
									sourceStr = "(" + parameterType.getQualifiedSourceName() + ")parameters.get(" + i + ")";
									composer.addImport(parameterType.getQualifiedSourceName());
								}
								int val = methodParameters.length - 1;
								if (i == val)
									sourceStr = sourceStr + ");";
								else
									sourceStr = sourceStr + ",";
								sourceWriter.print(sourceStr);
							}
							
						}
						else
							sourceWriter.print(");");
						sourceWriter.outdent();
						sourceWriter.println("}");
					}
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		sourceWriter.outdent();
		sourceWriter.println("return null;");
		sourceWriter.println("}");
	}
	
	/**
	 * Generate source code for the default constructor. Create default
	 * constructor, create the parameter list to invoke setText method on text box
	 * 
	 * @param sourceWriter
	 *            Source writer to output source code
	 */
	void generateConstructor(ClassSourceFileComposerFactory composer, SourceWriter sourceWriter, JClassType classType) {
		
		JConstructor[] jMethods = classType.getConstructors();
		
		for (int j = 0; j < jMethods.length; j++) {
			String sourceStr = "";
			String paramStr = "";
			String finalStr = "";
			JConstructor jMethod = jMethods[j];
			String methodName = jMethod.getName();
			try {
				if (jMethod.isPublic()) {
					sourceWriter.println("public AppOps" + methodName + "( ");
					
					JParameter[] methodParameters = jMethod.getParameters();
					if (methodParameters.length > 0) {
						for (int i = 0; i < methodParameters.length; i++) {
							JType parameterType = methodParameters[i].getType();
							String paramName = methodParameters[i].getName();
							
							sourceStr = parameterType.getQualifiedSourceName() + " " + paramName;
							composer.addImport(parameterType.getQualifiedSourceName());
							
							int val = methodParameters.length - 1;
							
							if (i == val) {
								paramStr = paramStr + paramName;
								finalStr = sourceStr + "){";
							}
							else {
								paramStr = paramStr + paramName + ",";
								finalStr = sourceStr + ",";
							}
							sourceWriter.print(finalStr);
						}
					}
					else
						sourceWriter.print("){");
					sourceWriter.indent();
					sourceWriter.println("super(" + paramStr + ");");
					sourceWriter.outdent();
					sourceWriter.println("}");
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/*// start constructor source generation
		sourceWriter.indent();
		//	sourceWriter.println("super();");
		sourceWriter.println("try {");
		sourceWriter.println("String[] parameters = {\"I was invoked by calling the obfuscated version of the method setText\"};");
		sourceWriter.println("Object myValue = im(\"setText\", parameters);");
		//sourceWriter.println("GWT.log(\"Got value \" + myValue, null);");
		sourceWriter.println("}");
		sourceWriter.println("catch (Exception e) {");
		//sourceWriter.println("GWT.log(\"JSNI method invokeMethod() threw an exception:\", e);");
		sourceWriter.println("}");
		
		// end constructor source generation
		sourceWriter.outdent();
		sourceWriter.println("}");*/
	}
	
	public void generatePresenterAccessMethods(ClassSourceFileComposerFactory composer, SourceWriter sourceWriter, JClassType classType) {
		// create member variable
		sourceWriter.println("public Presenter	presenter = null;");
		// create getter method
		sourceWriter.println("@Override");
		sourceWriter.println("public Presenter getPresenter() {");
		sourceWriter.indent();
		sourceWriter.println("return presenter;");
		sourceWriter.outdent();
		sourceWriter.println("}");
		
		// create setter method
		sourceWriter.println("@Override");
		sourceWriter.println("public void setPresenter(Presenter presenter) {");
		sourceWriter.indent();
		sourceWriter.println("this.presenter = presenter;");
		sourceWriter.outdent();
		sourceWriter.println("}");
	}
}

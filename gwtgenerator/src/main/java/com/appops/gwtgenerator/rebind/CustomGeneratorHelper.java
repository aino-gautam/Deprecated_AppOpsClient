package com.appops.gwtgenerator.rebind;

import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JConstructor;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.core.ext.typeinfo.JPrimitiveType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

// needs to scan the class for public methods 
// creates a switch or if else blocks to invoke these methods.

public class CustomGeneratorHelper {

	public CustomGeneratorHelper() {}

	// scans the class for public methods, creates the methods invoking
	// conditional block code
	// find a way to add methods only for getters setters starting with is / get
	// / set

	public void generateImMethodImpl(ClassSourceFileComposerFactory composer, SourceWriter sourceWriter,
			JClassType classType) {

		sourceWriter.println("@Override");
		sourceWriter
				.println("public Object im(String methodName, ArrayList<Serializable> parameters) throws Exception {");
		sourceWriter.indent();
		JMethod[] jMethods = classType.getInheritableMethods();

		for (int j = 0; j < jMethods.length; j++) {
			JMethod jMethod = jMethods[j];

			String methodName = jMethod.getName();
			try {
				if (jMethod.isPublic()) {
					if ((methodName.startsWith("get")) || methodName.startsWith("set") || methodName.startsWith("is")) {
						//System.out.println(methodName);
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
									sourceStr = "(" + parameterType.getQualifiedSourceName() + ")parameters.get(" + i
											+ ")";
									composer.addImport(parameterType.getQualifiedSourceName());
								}
								int val = methodParameters.length - 1;
								if (i == val)
									sourceStr = sourceStr + ");";
								else sourceStr = sourceStr + ",";
								sourceWriter.print(sourceStr);
							}
						}
						else sourceWriter.print(");");
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
	 * Generate source code for the default constructor. and paramterized constructor also adds event handler.
	 * 
	 * @param sourceWriter
	 *            Source writer to output source code
	 * @param context
	 */
	void generateConstructor(ClassSourceFileComposerFactory composer, SourceWriter sourceWriter, JClassType classType,
			GeneratorContext context) {

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
					else sourceWriter.print("){");

					sourceWriter.indent();
					sourceWriter.println("super(" + paramStr + ");");

					// adding click handler at the moment
					//sourceWriter.println("try{");
					//sourceWriter.indent();
					try {
						TypeOracle typeOracle = context.getTypeOracle();
						JClassType clickHandlerType = typeOracle.getType(HasClickHandlers.class.getCanonicalName());
						if (classType.isAssignableTo(clickHandlerType)) {
							sourceWriter.println("this.addClickHandler(new AppOpsEventHandler());");
							sourceWriter.println("System.out.println(\"stub click handler registered\");");
						}
					}
					catch (Exception e) {
						//e.printStackTrace();
					}
					//sourceWriter.outdent();
					//sourceWriter.println("}catch(Exception ex)");
					//sourceWriter.println("{}");

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

	//TODO add click handler to all the components

	public void generateAttachMethodImpl(ClassSourceFileComposerFactory composer, SourceWriter sourceWriter,
			JClassType classType) {
		sourceWriter.println("public void attach(){");
		sourceWriter.println("  onAttach();");
		//sourceWriter.println("  RootPanel.detachOnWindowClose(this);");
		sourceWriter.println("  }		");
	}

	public void generateConfigurationAccessMethodsImpl(ClassSourceFileComposerFactory composer,
			SourceWriter sourceWriter, JClassType classType) {
		// create member variable
		sourceWriter.println("private Configuration configuration;");
		// create getter method
		sourceWriter.println("@Override");
		sourceWriter.println("public Configuration getConfiguration() {");
		sourceWriter.indent();
		sourceWriter.println("return configuration;");
		sourceWriter.outdent();
		sourceWriter.println("}");

		// create setter method
		sourceWriter.println("@Override");
		sourceWriter.println("public void setConfiguration(Configuration conf) {");
		sourceWriter.indent();
		sourceWriter.println("configuration = conf;");
		sourceWriter.outdent();
		sourceWriter.println("}");

	}

	public void generateInitializeAccessMethodImpl(ClassSourceFileComposerFactory composer, SourceWriter sourceWriter,
			JClassType classType) {

		sourceWriter.println("@Override");
		sourceWriter.println("public void initialize() {");
		sourceWriter.println("	if (configuration != null) {");
		sourceWriter.println("		Configuration viewConfig = configuration.getPropertyByName(\"view\");");
		sourceWriter
				.println("		for (Entry<String, Property<? extends Serializable>> entry : viewConfig.getValue().entrySet()) {");
		sourceWriter.println("			String propName = (String) entry.getKey();");
		sourceWriter.println("			Property<? extends Serializable> prop = entry.getValue();");
		sourceWriter.println("			if (prop instanceof Entity) {");

		sourceWriter.println("			}");
		sourceWriter.println("		else {");
		sourceWriter.println("			try {");
		sourceWriter.println("				ArrayList<Serializable> parameters = new ArrayList<Serializable>();");
		sourceWriter.println("				parameters.add(prop.getValue());");
		sourceWriter.println("			im(\"set\" + propName, parameters);");
		sourceWriter.println("		}");
		sourceWriter.println("			catch (Exception e) {");
		sourceWriter.println("			e.printStackTrace();");
		sourceWriter.println("			}");
		sourceWriter.println("		}");
		sourceWriter.println("	}");
		sourceWriter.println("	}");
		sourceWriter.println("}");
	}

	public void generateParentSnippetAccessMethodsImpl(ClassSourceFileComposerFactory composer,
			SourceWriter sourceWriter, JClassType classType) {
		// create member variable
		sourceWriter.println("private Snippet parentSnippet;");
		// create getter method
		sourceWriter.println("@Override");
		sourceWriter.println("public void setParentSnippet(Snippet snippet) {");
		sourceWriter.indent();
		sourceWriter.println("this.parentSnippet = snippet;");
		sourceWriter.outdent();
		sourceWriter.println("}");

		// create setter method
		sourceWriter.println("@Override");
		sourceWriter.println("public Snippet getParentSnippet() {");
		sourceWriter.indent();
		sourceWriter.println("return this.parentSnippet;");
		sourceWriter.outdent();
		sourceWriter.println("}");
	}
	
	
	public void generateIDAccessMethodsImpl(ClassSourceFileComposerFactory composer,
			SourceWriter sourceWriter, JClassType classType) {
		// create member variable
		sourceWriter.println("private String ID;");
		// create getter method
		sourceWriter.println("@Override");
		sourceWriter.println("public void setID(String iD) {");
		sourceWriter.indent();
		sourceWriter.println("this.ID = iD;");
		sourceWriter.outdent();
		sourceWriter.println("}");

		// create setter method
		sourceWriter.println("@Override");
		sourceWriter.println("public String getID() {");
		sourceWriter.indent();
		sourceWriter.println("return this.ID;");
		sourceWriter.outdent();
		sourceWriter.println("}");
	}

}

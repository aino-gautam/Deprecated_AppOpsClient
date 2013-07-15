package in.appops.client.common.config.field;

import in.appops.platform.core.shared.Configuration;

import com.google.gwt.user.client.ui.Composite;

public class ConfigurationEditor extends Composite{
	
	private Configuration configuration;
	
	public ConfigurationEditor() {
		
	}

	/**
	 * Creates the property editor.
	 * @param conf
	 */
	public void create(){
		//TODO 
		//create ui with editor on top and preview button at the bottom.
		
		/*1.get the configuration object. iterate it. 
		2. create name vs TextBox.
		3.add it to the hashmap of propertyname vs txtbox.
		4.set the property value as textbox value.*/
		
		// on click of preview button collect all the property values and put it in the configuration instance. 
	}
	
	/**
	 * Method converts string to property value according to the property type and return.
	 * @param propValue
	 * @return
	 */
	public Object convertStringToPropValue(String propValue){

		if(propValue.equalsIgnoreCase("true") || propValue.equalsIgnoreCase("false"))
			return Boolean.parseBoolean(propValue);
		else if(propValue.matches("[0-9]+"))
			return Integer.parseInt(propValue);
		else if(propValue.matches("-?\\d+(\\.\\d+)?")){
			return Double.parseDouble(propValue);
		}else
			return propValue;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

}

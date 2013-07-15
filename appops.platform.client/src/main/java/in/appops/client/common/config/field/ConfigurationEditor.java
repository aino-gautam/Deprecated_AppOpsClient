package in.appops.client.common.config.field;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ConfigurationEditor extends Composite{
	
	private Field field;
	private VerticalPanel basePanel ;
	
	public ConfigurationEditor() {
		
	}

	public ConfigurationEditor(Field selectedField) {
		this.field = selectedField;
	}

	/**
	 * Creates the property editor.
	 * @param conf
	 */
	public void create(){
		
		basePanel = new VerticalPanel();
		initWidget(basePanel);
		
		//TODO 
		//create ui with editor on top and preview button at the bottom.
		
		/*1.get the configuration object. iterate it. 
		2. create name vs TextBox.
		3.add it to the hashmap of propertyname vs txtbox.
		4.set the property value as textbox value.*/
		
		// write a method which will collect all the property values and put it in the configuration instance. 
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

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

}

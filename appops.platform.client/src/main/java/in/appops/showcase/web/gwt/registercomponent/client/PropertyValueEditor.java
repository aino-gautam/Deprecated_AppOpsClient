package in.appops.showcase.web.gwt.registercomponent.client;

import in.appops.client.common.config.field.RadioButtonField;
import in.appops.client.common.config.field.RadioButtonField.RadionButtonFieldConstant;
import in.appops.client.common.fields.TextField;
import in.appops.client.common.fields.TextField.TextFieldConstant;
import in.appops.platform.core.shared.Configuration;

import com.google.gwt.user.client.ui.FlexTable;

public class PropertyValueEditor {
	
	private FlexTable propValuePanel;
	private int valuePanelRow;
	public String ISDEF_RADIOBTN_GROUP_ID = "isDefaultRadioBtnGroup";
	//it has value pojo.
	
	public PropertyValueEditor() {
	}

	public PropertyValueEditor(FlexTable propValuePanel, int valuePanelRow ,String  id) {
		this.propValuePanel = propValuePanel;
		this.valuePanelRow = valuePanelRow;
		ISDEF_RADIOBTN_GROUP_ID = id;
	}

	public void createUi(String availValue, boolean isDefault){
				
		TextField propValueField = new TextField();
		propValueField.setConfiguration(getDefaultValueFieldConf(availValue));
		propValueField.configure();
		propValueField.create();
		
		RadioButtonField isDefaultValueField = new RadioButtonField();
		isDefaultValueField.setConfiguration(getIsDefRadioBtnFieldConf(isDefault));
		isDefaultValueField.configure();
		isDefaultValueField.create();
		
		propValuePanel.setWidget(valuePanelRow, 0, propValueField);
		propValuePanel.setWidget(valuePanelRow, 3, isDefaultValueField);
		
	}
	
	/**
	 * Method creates the default value field configuration object and return.
	 * @return Configuration instance
	 */
	private Configuration getDefaultValueFieldConf(String availValue){
		Configuration configuration = new Configuration();
		
		try {
			configuration.setPropertyByName(TextFieldConstant.TF_TYPE, TextFieldConstant.TFTYPE_TXTBOX);
			
			configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_POS, TextFieldConstant.BF_SUGGESTION_INLINE);
			configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_TEXT, "Add new property value");
			
			if(availValue!=null)
				configuration.setPropertyByName(TextFieldConstant.BF_DEFVAL, availValue);
			
			configuration.setPropertyByName(TextFieldConstant.BF_ID, availValue);
			
			configuration.setPropertyByName(TextFieldConstant.BF_BLANK_TEXT,"Attribute can't be empty");
			configuration.setPropertyByName(TextFieldConstant.BF_ALLOWBLNK,false);
			configuration.setPropertyByName(TextFieldConstant.BF_ERRPOS,TextFieldConstant.BF_ERRINLINE);
			
		} catch (Exception e) {
		}
		return configuration;
	}
	
	/**
	 * Method creates the is default radio button field configuration object and return.
	 * @return Configuration instance
	 */
	private Configuration getIsDefRadioBtnFieldConf(boolean isDefault){
		Configuration configuration = new Configuration();
		
		try {
			configuration.setPropertyByName(RadionButtonFieldConstant.RF_CHECKED, isDefault);
			configuration.setPropertyByName(RadionButtonFieldConstant.RF_GROUPID, ISDEF_RADIOBTN_GROUP_ID);
			
		} catch (Exception e) {
			
		}
		return configuration;
	}
}

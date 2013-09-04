package in.appops.showcase.web.gwt.componentconfiguration.client.library;

import java.util.ArrayList;

import in.appops.client.common.config.field.ListBoxField.ListBoxFieldConstant;
import in.appops.client.common.config.field.ListBoxField;
import in.appops.client.common.config.field.RadioButtonField;
import in.appops.client.common.config.field.RadioButtonField.RadionButtonFieldConstant;
import in.appops.client.common.fields.TextField;
import in.appops.client.common.fields.TextField.TextFieldConstant;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.type.MetaType;
import in.appops.platform.core.shared.Configuration;

import com.google.gwt.user.client.ui.FlexTable;

public class PropertyValueEditor {
	
	private FlexTable propValuePanel;
	private int valuePanelRow;
	private Entity confDefEntity;
	private TextField valueField;
	private ListBoxField typeField;
	private RadioButtonField isDefaultValueField;
	
	/*******************  Fields ID *****************************/
	public final String PROPNAME_FIELD_ID = "attributeFieldId";
	public final String INTVAL_FIELD_ID = "intValFieldId";
	public final String STRINGVAL_FIELD_ID = "stringValFieldId";
	public final String ISDEF_RADIOBTN_GROUP_ID = "isDefaultRadioBtnGroup";
	
	public PropertyValueEditor() {
		
	}

	public PropertyValueEditor(FlexTable propValuePanel, int valuePanelRow ,Entity confDefEntity) {
		this.confDefEntity = confDefEntity;
		this.propValuePanel = propValuePanel;
		this.valuePanelRow = valuePanelRow;
	}

	public void createUi(){
				
		valueField = new TextField();
		valueField.setConfiguration(getStringValueFieldConf());
		valueField.configure();
		valueField.create();
		
		typeField = new ListBoxField();
		typeField.setConfiguration(getTypeBoxConfig());
		typeField.configure();
		typeField.create();
				
		isDefaultValueField = new RadioButtonField();
		
		isDefaultValueField.setConfiguration(getIsDefRadioBtnFieldConf());
		isDefaultValueField.configure();
		isDefaultValueField.create();
		
		propValuePanel.insertRow(valuePanelRow);
		
		propValuePanel.setWidget(valuePanelRow, 3, valueField);
		propValuePanel.setWidget(valuePanelRow, 5, typeField);
		propValuePanel.setWidget(valuePanelRow, 7, isDefaultValueField);
		
	}
	
	private Configuration getTypeBoxConfig() {
		Configuration configuration = new Configuration();
		try {
			ArrayList<String> items = new ArrayList<String>();
			items.add("Integer");
			items.add("String");
			items.add("configuration");
			items.add("Boolean");
			
			configuration.setPropertyByName(ListBoxFieldConstant.BF_DEFVAL,"---Select the type ---");
			configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_ITEMS,items);
			
		} catch (Exception e) {
			
		}
		
		return configuration;
	}
	
	/**
	 * Method creates the property name field configuration object and return.
	 * @return Configuration instance
	 */
	private Configuration getPropNameFieldConf(){
		Configuration configuration = new Configuration();
		
		try {
			configuration.setPropertyByName(TextFieldConstant.TF_TYPE, TextFieldConstant.TFTYPE_TXTBOX);
			
			configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_POS, TextFieldConstant.BF_SUGGESTION_INLINE);
			configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_TEXT, "Enter property name");
			
			if(confDefEntity!=null){
				configuration.setPropertyByName(TextFieldConstant.BF_DEFVAL, confDefEntity.getPropertyByName("key").toString());
			}
			
			configuration.setPropertyByName(TextFieldConstant.BF_ID, PROPNAME_FIELD_ID);
			
			configuration.setPropertyByName(TextFieldConstant.BF_BLANK_TEXT,"Property can't be empty");
			configuration.setPropertyByName(TextFieldConstant.BF_ALLOWBLNK,false);
			configuration.setPropertyByName(TextFieldConstant.BF_ERRPOS,TextFieldConstant.BF_ERRINLINE);
			
		} catch (Exception e) {
		}
		return configuration;
	}

	
	/**
	 * Method creates the string value field configuration object and return.
	 * @return Configuration instance
	 */
	private Configuration getStringValueFieldConf(){
		Configuration configuration = new Configuration();
		
		try {
			configuration.setPropertyByName(TextFieldConstant.TF_TYPE, TextFieldConstant.TFTYPE_TXTBOX);
			
			configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_POS, TextFieldConstant.BF_SUGGESTION_INLINE);
			configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_TEXT, "Add string value");
			
			if(confDefEntity!=null)
				configuration.setPropertyByName(TextFieldConstant.BF_DEFVAL, confDefEntity.getPropertyByName("stringvalue").toString());
			
			configuration.setPropertyByName(TextFieldConstant.BF_ID, STRINGVAL_FIELD_ID);
			
			//configuration.setPropertyByName(TextFieldConstant.BF_BLANK_TEXT,"Attribute can't be empty");
			//configuration.setPropertyByName(TextFieldConstant.BF_ALLOWBLNK,false);
			//configuration.setPropertyByName(TextFieldConstant.BF_ERRPOS,TextFieldConstant.BF_ERRINLINE);
			
		} catch (Exception e) {
		}
		return configuration;
	}
	
	/**
	 * Method creates the int value field configuration object and return.
	 * @return Configuration instance
	 */
	private Configuration getIntValueFieldConf(){
				
		Configuration configuration = new Configuration();
		try {
			configuration.setPropertyByName(ListBoxFieldConstant.BF_ID,INTVAL_FIELD_ID);
			configuration.setPropertyByName(TextFieldConstant.TF_TYPE, TextFieldConstant.TFTYPE_NUMERIC);
			configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_POS, TextFieldConstant.BF_SUGGESTION_INLINE);
			configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_TEXT, "Add int value");
			configuration.setPropertyByName(TextFieldConstant.MINVALUE,0);
			configuration.setPropertyByName(TextFieldConstant.BF_DEFVAL,0);
			configuration.setPropertyByName(TextFieldConstant.ALLOWDEC,false);
			
			//configuration.setPropertyByName(TextFieldConstant.BF_BLANK_TEXT,"Size can't be empty");
			//configuration.setPropertyByName(TextFieldConstant.BF_ALLOWBLNK,false);
			//configuration.setPropertyByName(TextFieldConstant.BF_ERRPOS,TextFieldConstant.BF_ERRINLINE);
			
			if(confDefEntity!=null)
				configuration.setPropertyByName(TextFieldConstant.BF_DEFVAL, confDefEntity.getPropertyByName("intvalue").toString());
						
		} catch (Exception e) {
			
		}
		return configuration;
	}
	
	/**
	 * Method creates the is default radio button field configuration object and return.
	 * @return Configuration instance
	 */
	private Configuration getIsDefRadioBtnFieldConf(){
		Configuration configuration = new Configuration();
		
		try {
			boolean isDefault = false;
			
			if(confDefEntity!=null){
				isDefault = Boolean.valueOf(confDefEntity.getPropertyByName("isdefault").toString());
			}
			configuration.setPropertyByName(RadionButtonFieldConstant.RF_CHECKED, isDefault);
			configuration.setPropertyByName(RadionButtonFieldConstant.RF_GROUPID, ISDEF_RADIOBTN_GROUP_ID);
			
		} catch (Exception e) {
			
		}
		return configuration;
	}
	
	public Entity getConfigDefEntity(){
		if (confDefEntity == null) {
			confDefEntity = new Entity();
			confDefEntity.setType(new MetaType("Configurationdef"));
		}
		
		confDefEntity.setPropertyByName("key", Integer.parseInt(valueField.getValue().toString()));
		confDefEntity.setPropertyByName("intvalue", Integer.parseInt(typeField.getValue().toString()));
		confDefEntity.setPropertyByName("stringvalue", Integer.parseInt(valueField.getValue().toString()));
		confDefEntity.setPropertyByName("isdefault", isDefaultValueField.getValue().toString());
		return confDefEntity;
		
		
	}
}

package in.appops.showcase.web.gwt.componentconfiguration.client.page;

import java.util.ArrayList;
import java.util.HashMap;

import in.appops.client.common.config.field.GroupField;
import in.appops.client.common.config.field.ButtonField.ButtonFieldConstant;
import in.appops.client.common.config.field.GroupField.GroupFieldConstant;
import in.appops.client.common.config.field.ImageField.ImageFieldConstant;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.client.common.config.field.ListBoxField.ListBoxFieldConstant;
import in.appops.client.common.config.field.RadioButtonField.RadionButtonFieldConstant;
import in.appops.client.common.config.field.textfield.TextField.TextFieldConstant;
import in.appops.platform.core.shared.Configuration;

public class ConfigurationInstanceProvider {

	/**
	 * Creates the select span Label configuration object and return.
	 * @return Configuration instance
	 */
	public Configuration getSelectSpanLabelConfig() {
		try {
			Configuration configuration = null;	
			try{
				configuration = new Configuration();
				configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, "Select a span element");
				configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, PageConfigurationContant.SPAN_SELECTION_LABEL_CSS);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return configuration;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Creates the Config Title Label configuration object and return.
	 * @return Configuration instance
	 */
	public Configuration getConfigTitleLabelConfig() {
		try {
			Configuration configuration = null;	
			try{
				configuration = new Configuration();
				configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, "Page Configurations");
				configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, PageConfigurationContant.CONFIG_TITLE_LABEL_CSS);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return configuration;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Creates the Span Listbox configuration object and return.
	 * @return Configuration instance
	 */
	public Configuration getSpanListBoxConfiguration(ArrayList<String> configList) {
		try {
			Configuration configuration = new Configuration();
			configuration.setPropertyByName(ListBoxFieldConstant.BF_ID,PageConfigurationContant.SPAN_LISTBOX_ID);
			configuration.setPropertyByName(ListBoxFieldConstant.BF_DEFVAL,"--Select span--");
			if(configList != null) {
				configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_ITEMS,configList);
			}
			return configuration;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Creates the plus Image Field configuration object and return.
	 * @return Configuration instance
	 */
	public Configuration getPlusIconFieldConfiguration(){
		Configuration configuration = new Configuration();
		try {
			configuration.setPropertyByName(ImageFieldConstant.BF_ID, PageConfigurationContant.PLUS_ICONFIELD_ID);
			configuration.setPropertyByName(ImageFieldConstant.IMGFD_BLOBID, "images/plus-icon.png");
			configuration.setPropertyByName(ImageFieldConstant.BF_PCLS, PageConfigurationContant.PLUS_ICONFIELD_CSS);
			configuration.setPropertyByName(ImageFieldConstant.IMGFD_TITLE, "Add new");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return configuration;
	}
	
	/**
	 * Creates the Transform To Listbox configuration object and return.
	 * @return Configuration instance
	 */
	public Configuration getTransformToListBoxConfiguration(HashMap<String, Object> paramMap, boolean isHtmlSnippet) {
		try {
			Configuration configuration = new Configuration();
			configuration.setPropertyByName(ListBoxFieldConstant.BF_ID,PageConfigurationContant.TRANSFORM_TO_LISTBOX_ID);
			configuration.setPropertyByName(ListBoxFieldConstant.BF_DEFVAL,"--Select transform to--");
			
			if(paramMap != null) {
				configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_OPRTION,"appdefinition.AppDefinitionService.getEntityList");
				configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_ENTPROP,"name");
				configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_QUERY_RESTRICTION,paramMap);
				if(isHtmlSnippet) {
					configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_QUERYNAME,"getComponentDefinationForHtmlSnipp");
				} else {
					configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_QUERYNAME,"getComponentDefinationForComponent");
				}
			} else {
				ArrayList<String> items = new ArrayList<String>();
				configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_ITEMS,items);
			}
			
			return configuration;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Creates the transform to Label configuration object and return.
	 * @return Configuration instance
	 */
	public Configuration getTransformToLabelConfiguration() {
		try {
			Configuration configuration = null;	
			try{
				configuration = new Configuration();
				configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, "Transform To");
				configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, PageConfigurationContant.TRANSFORM_TO_LABEL_CSS);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return configuration;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Creates the Transform Type Listbox configuration object and return.
	 * @return Configuration instance
	 */
	public Configuration getTransformTypeListBoxConfiguration() {
		try {
			Configuration configuration = new Configuration();
			configuration.setPropertyByName(ListBoxFieldConstant.BF_ID,PageConfigurationContant.TRANSFORM_TYPE_LISTBOX_ID);
			configuration.setPropertyByName(ListBoxFieldConstant.BF_DEFVAL,"--Select type--");
			ArrayList<String> configList = new ArrayList<String>();
			configList.add(PageConfigurationContant.COMPONENT);
			configList.add(PageConfigurationContant.SNIPPET);
			configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_ITEMS,configList);
			return configuration;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Creates the transform type Label configuration object and return.
	 * @return Configuration instance
	 */
	public Configuration getTransformTypeLabelConfiguration() {
		try {
			Configuration configuration = null;	
			try{
				configuration = new Configuration();
				configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, "Transform Type");
				configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, PageConfigurationContant.TRANSFORM_TYPE_LABEL_CSS);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return configuration;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Creates the transform instance Label configuration object and return.
	 * @return Configuration instance
	 */
	public Configuration getTransformInstanceLabelConfiguration() {
		try {
			Configuration configuration = null;	
			try{
				configuration = new Configuration();
				configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, "Transform Instance");
				configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, PageConfigurationContant.TRANSFORM_INSTANCE_LABEL_CSS);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return configuration;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Creates the is Transform Widget Label configuration object and return.
	 * @return Configuration instance
	 */
	public Configuration getIsTransformWidgetLabelConfiguration() {
		try {
			Configuration configuration = null;	
			try{
				configuration = new Configuration();
				configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, "Is Transform Widget");
				configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, PageConfigurationContant.IS_UPDATE_CONFIG_LABEL_CSS);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return configuration;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Creates the Configure button configuration object and return.
	 * @return Configuration instance
	 */
	public Configuration getConfigureButtonConfiguration() {
		try {
			Configuration configuration = new Configuration();
			try {
				configuration.setPropertyByName(ButtonFieldConstant.BTNFD_DISPLAYTEXT, "Configure transform widget");
				configuration.setPropertyByName(ButtonFieldConstant.BF_PCLS,PageConfigurationContant.CONFIGURE_BUTTON_CSS);
				configuration.setPropertyByName(ButtonFieldConstant.BF_ENABLED, true);
				configuration.setPropertyByName(ButtonFieldConstant.BF_ID, PageConfigurationContant.CONFIGURE_BUTTON_ID);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return configuration;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Creates the Update Config Label configuration object and return.
	 * @return Configuration instance
	 */
	public Configuration getUpdateConfigLabelConfiguration() {
		try {
			Configuration configuration = null;	
			try{
				configuration = new Configuration();
				configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, "Update Configuration");
				configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, PageConfigurationContant.IS_UPDATE_CONFIG_LABEL_CSS);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return configuration;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public GroupField createGroupField(String groupId) {
		try {
			GroupField groupField = new GroupField();
			Configuration groupFieldConfig = new Configuration();
			groupFieldConfig.setPropertyByName(GroupFieldConstant.GF_ID,groupId);
			groupFieldConfig.setPropertyByName(GroupFieldConstant.GF_TYPE,GroupFieldConstant.GFTYPE_SINGLE_SELECT);
			groupFieldConfig.setPropertyByName(GroupFieldConstant.GF_ALIGNMENT,GroupFieldConstant.GF_ALIGN_HORIZONTAL);
			groupFieldConfig.setPropertyByName(GroupFieldConstant.GF_LIMIT,2);
			
			ArrayList<String> listOfItems = new ArrayList<String>();
			listOfItems.add("radio1");
			listOfItems.add("radio2");
			groupFieldConfig.setPropertyByName(GroupFieldConstant.GF_LIST_OF_ITEMS,listOfItems);
			
			Configuration childConfig1 = new Configuration();
			childConfig1.setPropertyByName(RadionButtonFieldConstant.BF_PCLS, "appops-CheckBoxField");
			childConfig1.setPropertyByName(RadionButtonFieldConstant.RF_DISPLAYTEXT, "true");
			//childConfig1.setPropertyByName(RadionButtonFieldConstant.RF_CHECKED, true);
			
			Configuration childConfig2 = new Configuration();
			childConfig2.setPropertyByName(RadionButtonFieldConstant.BF_PCLS, "appops-CheckBoxField");
			childConfig2.setPropertyByName(RadionButtonFieldConstant.RF_DISPLAYTEXT, "false");
			
			groupFieldConfig.setPropertyByName("radio1",childConfig1);
			groupFieldConfig.setPropertyByName("radio2",childConfig2);
			
			groupField.setConfiguration(groupFieldConfig);
			groupField.configure();
			groupField.create();
			return groupField;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Creates the is Update Config Label configuration object and return.
	 * @return Configuration instance
	 */
	public Configuration getIsUpdateConfigLabelConfiguration() {
		try {
			Configuration configuration = null;	
			try{
				configuration = new Configuration();
				configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, "Is Update Configuration");
				configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, PageConfigurationContant.IS_UPDATE_CONFIG_LABEL_CSS);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return configuration;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Creates the Event Name Label configuration object and return.
	 * @return Configuration instance
	 */
	public Configuration getEventNameLabelConfiguration() {
		try {
			Configuration configuration = null;	
			try{
				configuration = new Configuration();
				configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, "Event Name");
				configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, PageConfigurationContant.EVENT_NAME_LABEL_CSS);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return configuration;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Creates the Property Config Title Label configuration object and return.
	 * @return Configuration instance
	 */
	public Configuration getPropConfigTitleLabelConfiguration() {
		try {
			Configuration configuration = null;	
			try{
				configuration = new Configuration();
				configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, "Interested Event");
				configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, PageConfigurationContant.PROP_CONFIG_TITLE_LABEL_CSS);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return configuration;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Creates the TextField configuration object and return.
	 * @return Configuration instance
	 */
	public Configuration getTextFieldConfiguration(String textFieldId) {
		try {
			Configuration configuration = new Configuration();
			configuration.setPropertyByName(TextFieldConstant.BF_ID, textFieldId);
			configuration.setPropertyByName(TextFieldConstant.TF_VISLINES, 1);
			configuration.setPropertyByName(TextFieldConstant.BF_READONLY, false);
			configuration.setPropertyByName(TextFieldConstant.TF_TYPE, TextFieldConstant.TFTYPE_TXTBOX);
			configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_POS, TextFieldConstant.BF_SUGGESTION_INLINE);
			configuration.setPropertyByName(TextFieldConstant.BF_VALIDATEONCHANGE, true);
			configuration.setPropertyByName(TextFieldConstant.BF_ERRPOS, TextFieldConstant.BF_SIDE);
			configuration.setPropertyByName(TextFieldConstant.TF_MAXLENGTH, 100);
			return configuration;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Creates the Event name Listbox configuration object and return.
	 * @return Configuration instance
	 */
	public Configuration getEventNameListBoxConfiguration(ArrayList<String> eventNameList) {
		try {
			Configuration configuration = new Configuration();
			configuration.setPropertyByName(ListBoxFieldConstant.BF_ID,PageConfigurationContant.SPAN_LISTBOX_ID);
			configuration.setPropertyByName(ListBoxFieldConstant.BF_DEFVAL,"--Select span--");
			if(eventNameList != null) {
				configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_ITEMS,eventNameList);
			}
			return configuration;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

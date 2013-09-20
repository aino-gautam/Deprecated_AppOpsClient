package in.appops.showcase.web.gwt.componentconfiguration.client.library;

import in.appops.client.common.config.field.ImageField;
import in.appops.client.common.config.field.ImageField.ImageFieldConstant;
import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.client.common.config.field.ListBoxField;
import in.appops.client.common.config.field.ListBoxField.ListBoxFieldConstant;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.ConfigEvent;
import in.appops.client.common.event.ConfigInstanceEvent;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.client.common.fields.TextField;
import in.appops.client.common.fields.TextField.TextFieldConstant;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.entity.Property;
import in.appops.platform.core.entity.type.MetaType;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.server.core.services.configuration.constant.ConfigTypeConstant;

import java.util.ArrayList;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.PopupPanel;

/**
 * @author mahesh@ensarm.com
 *
 */
public class SnippetPropValueEditor extends Composite implements FieldEventHandler{

	private String mode;

	public SnippetPropValueEditor reference;
	
	private FlexTable propValuePanel;
//	private int valuePanelRow=0;
	private Entity parentConfTypeEntity;
	private Entity confTypeParamValEnt;
	
	private TextField paramNameField;
	private TextField paramValueField;
	private final String CROSSIMG_CSS = "removePropertyImage";
	
	
	private ImageField removePropImgFld;
	
	/*******************  Fields ID *****************************/
	private final String PARANAME_FIELD_ID = "paramNameFieldId";
	private final String QUERYPARAMVALUE_FLD_ID = "qryParamValFldId";
	private final String OPERATIONPARAMVALUE_FLD_ID = "opParamValFldId";
	private final String REMOVEPROP_IMGID = "removePropValueImgId";
	
	private boolean deletable;
	private boolean instanceMode;
	private Entity parentConfInstanceEntity;
	private Entity confInstanceParamValEnt;
	private Entity configTypeEntity;
	private ListBoxField configTypeListbox;
	private boolean isConfigTypeListboxVisible;
	
	/*******************  Contants *****************************/
	private final String STRING_CONFIGTYPE = "String";
	private final String DOUBLE_CONFIGTYPE = "Double";
	private final String LONG_CONFIGTYPE = "Long";
	private final String INTEGER_CONFIGTYPE = "Integer";
	private final String BOOLEAN_CONFIGTYPE = "Boolean";
	private final String ENTITY_CONFIGTYPE = "Entity";
	private final String CONFIGURATION_CONFIGTYPE = "Configuration";
	private final String POPUP_CSS = "popupCss";
	private final String POPUP_LBL_PCLS = "popupLbl";
	
	
	public SnippetPropValueEditor(String querymode/*, int valPanelRow*/) {
		propValuePanel = new FlexTable();
		mode = querymode;
		reference = this;
	//	setValuePanelRow(valPanelRow);
		AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE, this);
		initWidget(propValuePanel);
	}

	public void createUi() {
				
		setParamNameField(new TextField());
		getParamNameField().setConfiguration(getParamNameFldConfig());
		getParamNameField().configure();
		getParamNameField().create();
		
		paramValueField = new TextField();
		paramValueField.setConfiguration(getParamValueFldConfig());
		paramValueField.configure();
		paramValueField.create();
		
		propValuePanel.setWidget(0, 3, getParamNameField());
		propValuePanel.setWidget(0, 7, paramValueField);

		if(deletable) {
			removePropImgFld = new ImageField();
			removePropImgFld.setConfiguration(getCrossImageConfiguration());
			removePropImgFld.configure();
			removePropImgFld.create();
			propValuePanel.setWidget(0, 9, removePropImgFld);
		}
		
		if(isConfigTypeListboxVisible) {
			configTypeListbox = new ListBoxField();
			configTypeListbox.setConfiguration(getConfigTypeListBoxConfiguration());
			configTypeListbox.configure();
			configTypeListbox.create();
			propValuePanel.setWidget(0, 5, configTypeListbox);
		}
		
		propValuePanel.setStylePrimaryName("propNameValContainerFlex");
	}
	
	/**
	 * Creates the cross image field configuration object and return.
	 * @return Configuration instance
	 */
	private Configuration getCrossImageConfiguration(){
		Configuration configuration = new Configuration();
		try {
			configuration.setPropertyByName(ImageFieldConstant.IMGFD_BLOBID, "images/cross.png");
			configuration.setPropertyByName(ImageFieldConstant.BF_PCLS,CROSSIMG_CSS);
			configuration.setPropertyByName(ImageFieldConstant.IMGFD_TITLE, "Remove property");
			configuration.setPropertyByName(ImageFieldConstant.BF_ID, REMOVEPROP_IMGID);
		} catch (Exception e) {
			
		}
		return configuration;
	}
	
	/**
	 * Method creates the string value field configuration object and return.
	 * @return Configuration instance
	 */
	private Configuration getParamNameFldConfig(){
		Configuration configuration = new Configuration();
		
		try {
			configuration.setPropertyByName(TextFieldConstant.TF_TYPE, TextFieldConstant.TFTYPE_TXTBOX);
			
			configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_POS, TextFieldConstant.BF_SUGGESTION_INLINE);
			configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_TEXT, "Param Name");
			
			configuration.setPropertyByName(TextFieldConstant.BF_ID, PARANAME_FIELD_ID);
			
			//configuration.setPropertyByName(TextFieldConstant.BF_TABINDEX, 5);
			
			configuration.setPropertyByName(TextFieldConstant.BF_BLANK_TEXT,"name can't be empty");
			configuration.setPropertyByName(TextFieldConstant.BF_ALLOWBLNK,false);
			configuration.setPropertyByName(TextFieldConstant.BF_ERRPOS,TextFieldConstant.BF_ERRINLINE);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return configuration;
	}
	
	/**
	 * Method creates the string value field configuration object and return.
	 * @return Configuration instance
	 */
	private Configuration getParamValueFldConfig(){
		Configuration configuration = new Configuration();
		
		try {
			configuration.setPropertyByName(TextFieldConstant.TF_TYPE, TextFieldConstant.TFTYPE_TXTBOX);
			
			configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_POS, TextFieldConstant.BF_SUGGESTION_INLINE);
			configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_TEXT, "Param Value");
			
			if(mode.equals(ModelConfigurationEditor.QUERYMODE))
				configuration.setPropertyByName(TextFieldConstant.BF_ID, QUERYPARAMVALUE_FLD_ID);
			else
				configuration.setPropertyByName(TextFieldConstant.BF_ID, OPERATIONPARAMVALUE_FLD_ID);
			
			configuration.setPropertyByName(TextFieldConstant.BF_BLANK_TEXT,"Value can't be empty");
			configuration.setPropertyByName(TextFieldConstant.BF_ALLOWBLNK,false);
			configuration.setPropertyByName(TextFieldConstant.BF_ERRPOS,TextFieldConstant.BF_ERRINLINE);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return configuration;
	}
	
	public Entity getConfigTypeEntity(){
		try{
			if(getConfTypeParamValEnt() ==null){
				setConfTypeParamValEnt(new Entity());
				getConfTypeParamValEnt().setType(new MetaType("Configtype"));
			}
			getConfTypeParamValEnt().setPropertyByName(ConfigTypeConstant.EMSTYPEID,3);
			getConfTypeParamValEnt().setPropertyByName(ConfigTypeConstant.ISDEFAULT,true);
			getConfTypeParamValEnt().setPropertyByName(ConfigTypeConstant.KEYNAME,paramNameField.getValue().toString());
			getConfTypeParamValEnt().setPropertyByName(ConfigTypeConstant.KEYVALUE,paramValueField.getValue().toString());
			getConfTypeParamValEnt().setProperty("configtype", parentConfTypeEntity);
			getConfTypeParamValEnt().setPropertyByName(ConfigTypeConstant.SERVICEID, 10);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return confTypeParamValEnt;
	}

	/**
	 * @return the snipPropValEditorId
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * @param mode the snipPropValEditorId to set
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	@Override
	public void onFieldEvent(FieldEvent event) {
		try{
			if(event.getEventSource() instanceof TextField){
				if(event.getEventType() == FieldEvent.TAB_KEY_PRESSED){
					if(((TextField)event.getEventSource()).equals(paramValueField)){
						if(isConfigTypeListboxVisible) {
							if(configTypeEntity != null) {
								fireEvent();
							} else {
								showPopup("Please select config type");
							}
						} else {
							fireEvent();
						}
					}
				}
			}
			else if(event.getEventSource() instanceof ImageField){
				if(event.getEventType() == FieldEvent.CLICKED){
					if(((ImageField)event.getEventSource()).equals(removePropImgFld)){
						ConfigEvent configEvent = new ConfigEvent(ConfigEvent.REMOVEPARAMPROPERTYVALUE, getConfigTypeEntity(), reference);
						AppUtils.EVENT_BUS.fireEvent(configEvent);
					}
				}
			}
			else if(event.getEventSource() instanceof ListBoxField){
				if(event.getEventType() == FieldEvent.VALUECHANGED){
					ListBoxField source = (ListBoxField) event.getEventSource();
					if(source.equals(configTypeListbox)) {
						String value = (String) source.getValue();
						if(value.equals(source.getSuggestionValueForListBox())) {
							configTypeEntity = null;
						} else {
							configTypeEntity = getConfigTypeEnt(value);
						}
					}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the paramNameField
	 */
	public TextField getParamNameField() {
		return paramNameField;
	}

	/**
	 * @param paramNameField the paramNameField to set
	 */
	public void setParamNameField(TextField paramNameField) {
		this.paramNameField = paramNameField;
	}

	/**
	 * @return the valuePanelRow
	 */
/*	public int getValuePanelRow() {
		return valuePanelRow;
	}

	*//**
	 * @param valuePanelRow the valuePanelRow to set
	 *//*
	public void setValuePanelRow(int valuePanelRow) {
		this.valuePanelRow = valuePanelRow;
	}*/

	/**
	 * @return the confTypeEntity
	 */
	public Entity getParentConfTypeEntity() {
		return parentConfTypeEntity;
	}

	/**
	 * @param confTypeEntity the confTypeEntity to set
	 */
	public void setParentConfTypeEntity(Entity confTypeEntity) {
		this.parentConfTypeEntity = confTypeEntity;
	}

	/**
	 * @return the confTypeParamValEnt
	 */
	public Entity getConfTypeParamValEnt() {
		return confTypeParamValEnt;
	}

	/**
	 * @param confTypeParamValEnt the confTypeParamValEnt to set
	 */
	public void setConfTypeParamValEnt(Entity confTypeParamValEnt) {
		this.confTypeParamValEnt = confTypeParamValEnt;
	}

	public boolean isDeletable() {
		return deletable;
	}

	public void setDeletable(boolean deletable) {
		this.deletable = deletable;
	}

	public boolean isInstanceMode() {
		return instanceMode;
	}

	public void setInstanceMode(boolean instanceMode) {
		this.instanceMode = instanceMode;
	}

	public Entity getParentConfInstanceEntity() {
		return parentConfInstanceEntity;
	}

	public void setParentConfInstanceEntity(Entity parentConfInstanceEntity) {
		this.parentConfInstanceEntity = parentConfInstanceEntity;
	}

	public Entity getConfInstanceParamValEnt() {
		if(confInstanceParamValEnt == null) {
			confInstanceParamValEnt = new Entity();
			confInstanceParamValEnt.setType(new MetaType("Configinstance"));
		}

		confInstanceParamValEnt.setPropertyByName("instancename", paramNameField.getValue().toString());
		confInstanceParamValEnt.setPropertyByName("configkeyname", paramNameField.getValue().toString());
		
		if(isConfigTypeListboxVisible) {
			confInstanceParamValEnt.setProperty("configtype", configTypeEntity);
		} else {
			if(confTypeParamValEnt != null) {
				confInstanceParamValEnt.setProperty("configtype", confTypeParamValEnt);
			}
		}
		
		confInstanceParamValEnt.setPropertyByName("instancevalue", paramValueField.getValue().toString());
		if(parentConfInstanceEntity != null) {
			confInstanceParamValEnt.setProperty("configinstance", parentConfInstanceEntity);
		}
		
		return confInstanceParamValEnt;
	}

	public void setConfInstanceParamValEnt(Entity confInstanceParamValEnt) {
		this.confInstanceParamValEnt = confInstanceParamValEnt;
	}

	public void populate() {
		if(isInstanceMode()) {
			String paramKey = confTypeParamValEnt.getPropertyByName(ConfigTypeConstant.KEYNAME).toString();
			String paramValue = confTypeParamValEnt.getPropertyByName(ConfigTypeConstant.KEYVALUE).toString();
			
			paramNameField.setValue(paramKey);
			paramValueField.setValue(paramValue);
		}
	}
	
	/**
	 * Creates the config type Listbox configuration object and return.
	 * @return Configuration instance
	 */
	private Configuration getConfigTypeListBoxConfiguration() {
		try {
			Configuration configuration = new Configuration();
			configuration.setPropertyByName(ListBoxFieldConstant.BF_DEFVAL,"--Please select a type--");
			configuration.setPropertyByName(ListBoxFieldConstant.BF_ENABLED, true);
			
			ArrayList<String> items = new ArrayList<String>();
			items.add(STRING_CONFIGTYPE);
			items.add(DOUBLE_CONFIGTYPE);
			items.add(LONG_CONFIGTYPE);
			items.add(INTEGER_CONFIGTYPE);
			items.add(BOOLEAN_CONFIGTYPE);
			items.add(ENTITY_CONFIGTYPE);
			items.add(CONFIGURATION_CONFIGTYPE);
			configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_ITEMS,items);
			
			return configuration;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//TODO : Hardcoded entity has been set
	private Entity getConfigTypeEnt(String value) {
		Long configTypeId = null;
		
		if(value.equals(STRING_CONFIGTYPE)) {
			configTypeId = 809L;
		} else if(value.equals(DOUBLE_CONFIGTYPE)) {
			configTypeId = 810L;
		} else if(value.equals(LONG_CONFIGTYPE)) {
			configTypeId = 811L;
		} else if(value.equals(INTEGER_CONFIGTYPE)) {
			configTypeId = 812L;
		} else if(value.equals(BOOLEAN_CONFIGTYPE)) {
			configTypeId = 813L;
		} else if(value.equals(ENTITY_CONFIGTYPE)) {
			configTypeId = 814L;
		} else if(value.equals(CONFIGURATION_CONFIGTYPE)) {
			configTypeId = 815L;
		}
		
		Entity configTypeEntity = new Entity();
		configTypeEntity.setType(new MetaType("Configtype"));
		Key<Long> key = new Key<Long>(configTypeId);
		Property<Key<Long>> keyProp = new Property<Key<Long>>(key);
		configTypeEntity.setProperty(ConfigTypeConstant.ID, keyProp);
		return configTypeEntity;
	}
	
	/**
	 * Used to show popup at perticular position.
	 * @param popuplabel
	 */
	private void showPopup(String popuplabel){
		try {
			LabelField popupLbl = new LabelField();
			popupLbl.setConfiguration(getLabelFieldConf(popuplabel,POPUP_LBL_PCLS,null,null));
			popupLbl.configure();
			popupLbl.create();
					
			PopupPanel popup = new PopupPanel();
			popup.setAnimationEnabled(true);
			popup.setAutoHideEnabled(true);
			popup.setStylePrimaryName(POPUP_CSS);
			popup.add(popupLbl);
			popup.center();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates the table name label field configuration object and return.
	 * @param displayText
	 * @param primaryCss
	 * @param dependentCss
	 * @param propEditorLblPanelCss
	 * @return Configuration instance
	 */
	private Configuration getLabelFieldConf(String displayText , String primaryCss , String dependentCss ,String propEditorLblPanelCss){
		Configuration conf = new Configuration();
		try {
			conf.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, displayText);
			conf.setPropertyByName(LabelFieldConstant.BF_PCLS, primaryCss);
			conf.setPropertyByName(LabelFieldConstant.BF_DCLS, dependentCss);
			conf.setPropertyByName(LabelFieldConstant.BF_BASEPANEL_PCLS, propEditorLblPanelCss);
		} catch (Exception e) {
			
		}
		return conf;
	}

	public boolean isConfigTypeListboxVisible() {
		return isConfigTypeListboxVisible;
	}

	public void setConfigTypeListboxVisible(boolean isConfigTypeListboxVisible) {
		this.isConfigTypeListboxVisible = isConfigTypeListboxVisible;
	}
	
	private void fireEvent() {
		if(!(paramValueField.getValue().toString().trim().equals("")) && !(paramNameField.getValue().toString().trim().equals(""))){
			GwtEvent configEvent = null;
			if(isInstanceMode()) {
				configEvent = new ConfigInstanceEvent(ConfigEvent.SAVEPROPVALUEADDWIDGET, reference);
			} else {
				configEvent = new ConfigEvent(ConfigEvent.SAVEPROPVALUEADDWIDGET, getConfigTypeEntity(), reference);
			}
			AppUtils.EVENT_BUS.fireEvent(configEvent);
		}
	}
}
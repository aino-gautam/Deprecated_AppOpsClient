package in.appops.showcase.web.gwt.componentconfiguration.client.library;

import in.appops.client.common.config.field.ImageField;
import in.appops.client.common.config.field.ImageField.ImageFieldConstant;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.ConfigEvent;
import in.appops.client.common.event.ConfigInstanceEvent;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.client.common.fields.TextField;
import in.appops.client.common.fields.TextField.TextFieldConstant;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.type.MetaType;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.server.core.services.configuration.constant.ConfigTypeConstant;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;

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
		propValuePanel.setWidget(0, 5, paramValueField);

		if(deletable) {
			removePropImgFld = new ImageField();
			removePropImgFld.setConfiguration(getCrossImageConfiguration());
			removePropImgFld.configure();
			removePropImgFld.create();
			propValuePanel.setWidget(0, 7, removePropImgFld);
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
			}
			else if(event.getEventSource() instanceof ImageField){
				if(event.getEventType() == FieldEvent.CLICKED){
					if(((ImageField)event.getEventSource()).equals(removePropImgFld)){
						ConfigEvent configEvent = new ConfigEvent(ConfigEvent.REMOVEPARAMPROPERTYVALUE, getConfigTypeEntity(), reference);
						AppUtils.EVENT_BUS.fireEvent(configEvent);
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
		if(confTypeParamValEnt != null) {
			confInstanceParamValEnt.setProperty("configtype", confTypeParamValEnt);
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
}

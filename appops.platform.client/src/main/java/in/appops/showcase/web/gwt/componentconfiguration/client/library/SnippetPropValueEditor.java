package in.appops.showcase.web.gwt.componentconfiguration.client.library;

import in.appops.client.common.config.field.ImageField;
import in.appops.client.common.config.field.ImageField.ImageFieldConstant;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.ConfigEvent;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.client.common.fields.TextField;
import in.appops.client.common.fields.TextField.TextFieldConstant;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.type.MetaType;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.server.core.services.configuration.constant.ConfigTypeConstant;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;

/**
 * @author mahesh@ensarm.com
 *
 */
public class SnippetPropValueEditor extends Composite implements FieldEventHandler{

	private String snipPropValEditorId;

	public SnippetPropValueEditor reference;
	
	private FlexTable propValuePanel;
	private int valuePanelRow=0;
	private Entity confTypeEntity;
	private TextField paramNameField;
	private TextField paramValueField;
	private final String CROSSIMG_CSS = "removePropertyImage";
	
	
	private ImageField removePropImgFld;
	
	/*******************  Fields ID *****************************/
	private final String PARANAME_FIELD_ID = "paramNameFieldId";
	private final String QUERYPARAMVALUE_FLD_ID = "qryParamValFldId";
	private final String OPERATIONPARAMVALUE_FLD_ID = "opParamValFldId";
	private final String REMOVEPROP_IMGID = "removePropValueImgId";
	
	public SnippetPropValueEditor(String querymode, int valPanelRow) {
		propValuePanel = new FlexTable();
		snipPropValEditorId = querymode;
		reference = this;
		setValuePanelRow(valPanelRow);
		AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE, this);
		initWidget(propValuePanel);
		createUi();
	}

	private void createUi(){
				
		setParamNameField(new TextField());
		getParamNameField().setConfiguration(getParamNameFldConfig());
		getParamNameField().configure();
		getParamNameField().create();
		
		paramValueField = new TextField();
		paramValueField.setConfiguration(getParamValueFldConfig());
		paramValueField.configure();
		paramValueField.create();
		
		removePropImgFld = new ImageField();
		removePropImgFld.setConfiguration(getCrossImageConfiguration());
		removePropImgFld.configure();
		removePropImgFld.create();
		
		//propValuePanel.insertRow(valuePanelRow);
		
		propValuePanel.setWidget(0, 3, getParamNameField());
		propValuePanel.setWidget(0, 5, paramValueField);
		propValuePanel.setWidget(0, 7, removePropImgFld);
		
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
			
			configuration.setPropertyByName(TextFieldConstant.BF_TABINDEX, 5);
			
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
			
			if(snipPropValEditorId.equals(ModelConfigurationEditor.QUERYMODE))
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
			Entity configTypeEnt = new Entity();
			configTypeEnt.setType(new MetaType("Configtype"));
			configTypeEnt.setPropertyByName(ConfigTypeConstant.EMSTYPEID,3);
			configTypeEnt.setPropertyByName(ConfigTypeConstant.ISDEFAULT,true);
			configTypeEnt.setPropertyByName(ConfigTypeConstant.KEYNAME,paramNameField.getValue().toString());
			configTypeEnt.setPropertyByName(ConfigTypeConstant.KEYVALUE,paramValueField.getValue().toString());
			configTypeEnt.setPropertyByName("configtype", getConfTypeEntity());
			configTypeEnt.setPropertyByName(ConfigTypeConstant.SERVICEID, 10);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return getConfTypeEntity();
	}

	/**
	 * @return the snipPropValEditorId
	 */
	public String getSnipPropValEditorId() {
		return snipPropValEditorId;
	}

	/**
	 * @param snipPropValEditorId the snipPropValEditorId to set
	 */
	public void setSnipPropValEditorId(String snipPropValEditorId) {
		this.snipPropValEditorId = snipPropValEditorId;
	}

	@Override
	public void onFieldEvent(FieldEvent event) {
		try{
			if(event.getEventSource() instanceof TextField){
				if(event.getEventType() == FieldEvent.TAB_KEY_PRESSED){
					if(((TextField)event.getEventSource()).equals(paramValueField)){
						ConfigEvent configEvent = new ConfigEvent(ConfigEvent.SAVEPROPVALUEADDWIDGET, getConfigTypeEntity(), reference);
						AppUtils.EVENT_BUS.fireEvent(configEvent);
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
	public int getValuePanelRow() {
		return valuePanelRow;
	}

	/**
	 * @param valuePanelRow the valuePanelRow to set
	 */
	public void setValuePanelRow(int valuePanelRow) {
		this.valuePanelRow = valuePanelRow;
	}

	/**
	 * @return the confTypeEntity
	 */
	public Entity getConfTypeEntity() {
		return confTypeEntity;
	}

	/**
	 * @param confTypeEntity the confTypeEntity to set
	 */
	public void setConfTypeEntity(Entity confTypeEntity) {
		this.confTypeEntity = confTypeEntity;
	}
}

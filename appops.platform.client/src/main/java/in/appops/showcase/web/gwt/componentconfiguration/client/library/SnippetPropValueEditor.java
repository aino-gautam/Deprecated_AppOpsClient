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
	private Entity confInstanceEntity;
	private TextField paramNameField;
	private TextField paramValueField;
	private final String CROSSIMG_CSS = "removePropertyImage";
	
	
	private ImageField removePropImgFld;
	
	/*******************  Fields ID *****************************/
	private final String PARANAME_FIELD_ID = "paramNameFieldId";
	private final String QUERYPARAMVALUE_FLD_ID = "qryParamValFldId";
	private final String OPERATIONPARAMVALUE_FLD_ID = "opParamValFldId";
	private final String REMOVEPROP_IMGID = "removePropValueImgId";
	
	public SnippetPropValueEditor(String querymode) {
		propValuePanel = new FlexTable();
		snipPropValEditorId = querymode;
		reference = this;
		AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE, this);
		initWidget(propValuePanel);
		createUi();
	}

	private void createUi(){
				
		paramNameField = new TextField();
		paramNameField.setConfiguration(getParamNameFldConfig());
		paramNameField.configure();
		paramNameField.create();
		
		paramValueField = new TextField();
		paramValueField.setConfiguration(getParamValueFldConfig());
		paramValueField.configure();
		paramValueField.create();
		
		removePropImgFld = new ImageField();
		removePropImgFld.setConfiguration(getCrossImageConfiguration());
		removePropImgFld.configure();
		removePropImgFld.create();
		
		//propValuePanel.insertRow(valuePanelRow);
		
		propValuePanel.setWidget(valuePanelRow, 3, paramNameField);
		propValuePanel.setWidget(valuePanelRow, 5, paramValueField);
		propValuePanel.setWidget(valuePanelRow, 7, removePropImgFld);
		
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
	
	public Entity getConfigDefEntity(){
		try{
			Entity configDefEnt = new Entity();
			configDefEnt.setType(new MetaType("Configurationdef"));
			
			configDefEnt.setPropertyByName("name", paramNameField.getValue().toString());
			configDefEnt.setPropertyByName("instancevalue", paramValueField.getValue().toString());
			configDefEnt.setProperty("parentId", confInstanceEntity);
			configDefEnt.setPropertyByName("isdefault", true);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return confInstanceEntity;
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
				if(event.getEventType() == FieldEvent.EDITCOMPLETED){
					if(((TextField)event.getEventSource()).equals(paramValueField)){
						ConfigEvent configEvent = new ConfigEvent(ConfigEvent.SAVEPROPVALUEADDWIDGET, getConfigDefEntity(), reference);
						AppUtils.EVENT_BUS.fireEvent(configEvent);
					}
					/*else if(((TextField)event.getEventSource()).getBaseFieldId().equals(OPERATIONPARAMVALUE_FLD_ID)){
						ConfigEvent configEvent = new ConfigEvent(ConfigEvent.SAVEPROPVALUEADDWIDGET, getConfigDefEntity(), reference);
						AppUtils.EVENT_BUS.fireEvent(configEvent);
					}*/
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
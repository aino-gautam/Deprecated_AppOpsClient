package in.appops.showcase.web.gwt.componentconfiguration.client.library;

import in.appops.client.common.config.field.ButtonField;
import in.appops.client.common.config.field.ImageField;
import in.appops.client.common.config.field.ButtonField.ButtonFieldConstant;
import in.appops.client.common.config.field.ImageField.ImageFieldConstant;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.ConfigEvent;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.ConfigEventHandler;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.client.common.fields.TextField;
import in.appops.client.common.fields.TextField.TextFieldConstant;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.EntityList;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * 
 * @author pallavi@ensarm.com
 *
 */
public class ConfPropertyEditor extends VerticalPanel implements FieldEventHandler,ConfigEventHandler{
	
	private String ADD_NEW_PROPVAL_IMGID = "addNewPropValImgId";
	private String NEWVALUE_CSS = "addNewPropertyImage";
	private final String COMPFORM_PANEL_CSS = "componentFormPanel";
	private static String SAVE_BTN_PCLS = "saveConfigButton";
	private static String SAVECONFIGURATION_BTN_ID = "saveConfigBtnId";
	
	private FlexTable propValuePanel;
	private TextField propNameField;
	private int valuePanelRow = 0;
	private Entity componentDefEnt;
	private  EntityList componentDeflist;
	private ArrayList<PropertyValueEditor> propValueList;
		
	public ConfPropertyEditor(Entity componentDefEnt, EntityList componentDeflist) {
		this.componentDefEnt = componentDefEnt;
		this.componentDeflist = componentDeflist;
	}
	
	public void createUi(){
		try {
			propValuePanel = new FlexTable();
			
			propNameField = new TextField();
			propNameField.setConfiguration(getPropNameFieldConf(null));
			propNameField.configure();
			propNameField.create();
			
			ImageField addValueImgField = new ImageField();
			addValueImgField.setConfiguration(getPlusImageConfiguration());
			addValueImgField.configure();
			addValueImgField.create();
			
			ButtonField saveConfigBtn = new ButtonField();
			saveConfigBtn.setConfiguration(getSaveConfigurationBtnConf());
			saveConfigBtn.configure();
			saveConfigBtn.create();
			
			propValuePanel.setWidget(valuePanelRow, 0, propNameField);
			
			propValuePanel.setWidget(valuePanelRow, 7, addValueImgField);
			
			valuePanelRow+=2;
			
			propValuePanel.setWidget(valuePanelRow, 0, saveConfigBtn);
			
			/*for(Entity confEnt:componentDeflist){
				PropertyValueEditor propValueEditor = new PropertyValueEditor(propValuePanel, valuePanelRow, confEnt);
				propValueEditor.createUi();
				valuePanelRow++;
				if(propValueList == null){
					propValueList = new ArrayList<PropertyValueEditor>();
				}
				propValueList.add(propValueEditor);
			}*/
			
			addNewPropertyValue();
			
			add(propValuePanel);
			setStylePrimaryName(COMPFORM_PANEL_CSS);
			AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE,this);
			AppUtils.EVENT_BUS.addHandler(ConfigEvent.TYPE, this);
		} catch (Exception e) {
			
		}
	}
	
	/**
	 * Creates the save button configuration object and return.
	 * @return Configuration instance
	 */
	private Configuration getSaveConfigurationBtnConf(){
		Configuration configuration = new Configuration();
		try {
			configuration.setPropertyByName(ButtonFieldConstant.BTNFD_DISPLAYTEXT, "Save configuration");
			configuration.setPropertyByName(ButtonFieldConstant.BF_PCLS,SAVE_BTN_PCLS);
			configuration.setPropertyByName(ButtonFieldConstant.BF_ENABLED, true);
			configuration.setPropertyByName(ButtonFieldConstant.BF_ID, SAVECONFIGURATION_BTN_ID);
		} catch (Exception e) {
			
		}
		return configuration;
	}
	
	/**
	 * Method creates the property name field configuration object and return.
	 * @return Configuration instance
	 */
	private Configuration getPropNameFieldConf(String propName){
		Configuration configuration = new Configuration();
		
		try {
			configuration.setPropertyByName(TextFieldConstant.TF_TYPE, TextFieldConstant.TFTYPE_TXTBOX);
			
			configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_POS, TextFieldConstant.BF_SUGGESTION_INLINE);
			configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_TEXT, "Enter property name");
			
			if(propName!=null)
				configuration.setPropertyByName(TextFieldConstant.BF_DEFVAL, propName);
			
			configuration.setPropertyByName(TextFieldConstant.BF_ID, propName);
			
			configuration.setPropertyByName(TextFieldConstant.BF_BLANK_TEXT,"Property can't be empty");
			configuration.setPropertyByName(TextFieldConstant.BF_ALLOWBLNK,false);
			configuration.setPropertyByName(TextFieldConstant.BF_ERRPOS,TextFieldConstant.BF_ERRINLINE);
			
		} catch (Exception e) {
		}
		return configuration;
	}
	
	private void addNewPropertyValue(){
		PropertyValueEditor propValueEditor = new PropertyValueEditor(propValuePanel, valuePanelRow, null);
		propValueEditor.createUi();
	}
	
	/**
	 * Creates the plus image field configuration object and return.
	 * @return Configuration instance
	 */
	private Configuration getPlusImageConfiguration(){
		Configuration configuration = new Configuration();
		try {
			configuration.setPropertyByName(ImageFieldConstant.IMGFD_BLOBID, "images/plus-icon.png");
			configuration.setPropertyByName(ImageFieldConstant.BF_PCLS,NEWVALUE_CSS);
			configuration.setPropertyByName(ImageFieldConstant.IMGFD_TITLE, "Add new property value");
			configuration.setPropertyByName(ImageFieldConstant.BF_ID, ADD_NEW_PROPVAL_IMGID);
		} catch (Exception e) {
			
		}
		return configuration;
	}
	
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
	
	@Override
	public void onFieldEvent(FieldEvent event) {
		try {
			int eventType = event.getEventType();
			Object eventSource = event.getEventSource();
			switch (eventType) {
			case FieldEvent.CLICKED: {
				if (eventSource instanceof ImageField) {
					ImageField imageField = (ImageField) eventSource;
					if (imageField.getBaseFieldId().equals(ADD_NEW_PROPVAL_IMGID)) {
						addNewPropertyValue();
					}
				}
				break;
			}
			default:
				break;
			}
		} catch (Exception e) {
			
		}
	}
	
	public EntityList getConfDefList(){
		EntityList list = new EntityList();
		for(int i = 0; i< propValueList.size() ; i++){
			Entity confDef = propValueList.get(i).getConfigDefEntity();
			list.add(confDef);
		}
		return list;
	}

	@Override
	public void onConfigEvent(ConfigEvent event) {
		try {
			int eventType = event.getEventType();
			Object eventSource = event.getEventSource();
			switch (eventType) {
			case ConfigEvent.PROPERTYSELECTED: {
				if (eventSource instanceof ConfigurationListDisplayer) {
					Entity entity=  (Entity) event.getEventData(); 
					String name = entity.getPropertyByName("name");
					propNameField.setFieldValue(name);
				}
				break;
			}
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace()
			;
		}
		
	}
}

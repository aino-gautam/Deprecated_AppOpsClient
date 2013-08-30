package in.appops.showcase.web.gwt.registercomponent.client;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.VerticalPanel;
import in.appops.client.common.config.field.ImageField;
import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.ImageField.ImageFieldConstant;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.client.common.fields.TextField;
import in.appops.client.common.fields.TextField.TextFieldConstant;
import in.appops.platform.core.shared.Configuration;

/**
 * 
 * @author pallavi@ensarm.com
 *
 */
public class ConfPropertyEditor extends VerticalPanel implements FieldEventHandler{
	
	private String ADD_NEW_PROPVAL_IMGID = "addNewPropValImgId";
	private String NEWVALUE_CSS = "addNewPropertyImage";
	private String PROPPANEL_CSS = "properyPanelCss";
	private String PROP_LABEL_CSS = "propValueLabelCss";
	
	private FlexTable propValuePanel;
	private int valuePanelRow = 0;
	
	public ConfPropertyEditor(String propName) {
		ADD_NEW_PROPVAL_IMGID = propName;
	}
	
	public void createUi(){
		try {
			
			TextField propNameField = new TextField();
			propNameField.setConfiguration(getPropNameFieldConf(null));
			propNameField.configure();
			propNameField.create();
			
			propValuePanel = new FlexTable();
			
			LabelField valueLbl = new LabelField();
			valueLbl.setConfiguration(getLabelFieldConf("Value",PROP_LABEL_CSS,null,null));
			valueLbl.configure();
			valueLbl.create();
			
			LabelField isDefLbl = new LabelField();
			isDefLbl.setConfiguration(getLabelFieldConf("isDefault",PROP_LABEL_CSS,null,null));
			isDefLbl.configure();
			isDefLbl.create();
			
			ImageField addValueImgField = new ImageField();
			addValueImgField.setConfiguration(getPlusImageConfiguration());
			addValueImgField.configure();
			addValueImgField.create();
			
			propValuePanel.setWidget(valuePanelRow, 0, valueLbl);
			propValuePanel.setWidget(valuePanelRow, 3, isDefLbl);
			propValuePanel.setWidget(valuePanelRow, 5, addValueImgField);
			
			//TODO check if property is new i.e no value is set to property then add default prop.
			addNewPropertyValue();
			
			add(propNameField);
			add(propValuePanel);
			
			setStylePrimaryName(PROPPANEL_CSS);
			
			AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE,this);
			
		} catch (Exception e) {
			
		}
	}
	
	private void addNewPropertyValue(){
		valuePanelRow++;
		PropertyValueEditor propValueEditor = new PropertyValueEditor(propValuePanel, valuePanelRow, ADD_NEW_PROPVAL_IMGID);
		propValueEditor.createUi(null, true);
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
	
}

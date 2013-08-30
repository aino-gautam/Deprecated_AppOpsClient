package in.appops.showcase.web.gwt.registercomponent.client;

import in.appops.client.common.config.field.ButtonField;
import in.appops.client.common.config.field.ImageField;
import in.appops.client.common.config.field.ButtonField.ButtonFieldConstant;
import in.appops.client.common.config.field.ImageField.ImageFieldConstant;
import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.platform.core.shared.Configuration;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * take configuration instance ,iterate map and add confpropertyeditor instance for each property.
 * @author pallavi@ensarm.com
 *
 */
public class ConfigurationEditor extends Composite implements FieldEventHandler{
	
	private VerticalPanel basePanel;
	private FlexTable propertyPanel;
	private int propertyPanelRow = 2;
	
	private static String ADD_NEW_PROP_IMGID = "addNewPropImgId";
	private static String NEWPROP_CSS = "addNewPropertyImage";
	private static String PROPPANEL_CSS = "properyPanelCss";
	private static String SAVE_BTN_PCLS = "saveConfigButton";
	private static String SAVE_BTN_ID = "saveConfigBtnId";
	private static String PROP_LABEL_CSS = "propValueLabelCss";
	
	public ConfigurationEditor() {
		
	}
	
	public void createEditor(){
		
		basePanel = new VerticalPanel();
		propertyPanel = new FlexTable();
		
		LabelField propEditorLbl = new LabelField();
		propEditorLbl.setConfiguration(getLabelFieldConf("Property",PROP_LABEL_CSS,null,null));
		propEditorLbl.configure();
		propEditorLbl.create();
		
		ImageField addPropertyImgField = new ImageField();
		addPropertyImgField.setConfiguration(getPlusImageConfiguration());
		addPropertyImgField.configure();
		addPropertyImgField.create();
		
		propertyPanel.setWidget(0, 0, propEditorLbl);
		propertyPanel.setWidget(0,1,addPropertyImgField);
		
		propertyPanel.setStylePrimaryName(PROPPANEL_CSS);
		
		//TODO check if component is new i.e no configuration is set to component then add default prop.
		addNewConfigurationProperty();
		
		ButtonField previewBtn = new ButtonField();
		previewBtn.setConfiguration(getSaveConfigurationBtnConf());
		previewBtn.configure();
		previewBtn.create();
		
		basePanel.add(propertyPanel);
		basePanel.add(previewBtn);
		
		AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE,this);
		initWidget(basePanel);
	}
	
	/**
	 * Creates the save button configuration object and return.
	 * @return Configuration instance
	 */
	private Configuration getSaveConfigurationBtnConf(){
		Configuration configuration = new Configuration();
		try {
			configuration.setPropertyByName(ButtonFieldConstant.BTNFD_DISPLAYTEXT, "Save");
			configuration.setPropertyByName(ButtonFieldConstant.BF_PCLS,SAVE_BTN_PCLS);
			configuration.setPropertyByName(ButtonFieldConstant.BF_ENABLED, false);
			configuration.setPropertyByName(ButtonFieldConstant.BF_ID, SAVE_BTN_ID);
		} catch (Exception e) {
			
		}
		return configuration;
	}
	
	private void addNewConfigurationProperty(){
		String propId = Document.get().createUniqueId();
		ConfPropertyEditor confPropertyEditor = new ConfPropertyEditor(propId);
		confPropertyEditor.createUi();
		propertyPanelRow++;
		propertyPanel.setWidget(propertyPanelRow, 0, confPropertyEditor);
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
	 * Creates the plus image field configuration object and return.
	 * @return Configuration instance
	 */
	private Configuration getPlusImageConfiguration(){
		Configuration configuration = new Configuration();
		try {
			configuration.setPropertyByName(ImageFieldConstant.IMGFD_BLOBID, "images/plus-icon.png");
			configuration.setPropertyByName(ImageFieldConstant.BF_PCLS,NEWPROP_CSS);
			configuration.setPropertyByName(ImageFieldConstant.IMGFD_TITLE, "Add new Property");
			configuration.setPropertyByName(ImageFieldConstant.BF_ID, ADD_NEW_PROP_IMGID);
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
					if (imageField.getBaseFieldId().equals(ADD_NEW_PROP_IMGID)) {
						addNewConfigurationProperty();
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

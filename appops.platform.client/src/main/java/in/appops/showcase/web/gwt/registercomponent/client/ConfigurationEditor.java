package in.appops.showcase.web.gwt.registercomponent.client;

import in.appops.client.common.config.field.ButtonField;
import in.appops.client.common.config.field.ButtonField.ButtonFieldConstant;
import in.appops.client.common.config.field.ImageField;
import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.platform.core.shared.Configuration;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
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
	private static String SAVE_BTN_PCLS = "saveConfigButton";
	private static String SAVE_BTN_ID = "saveConfigBtnId";
	private static String BASEPANEL_CSS = "confEditorPanel";
	private final String HEADERLBL_CSS = "componentSectionHeaderLbl";
	
	public ConfigurationEditor() {
		
	}
	
	public void createEditor(){
		
		basePanel = new VerticalPanel();
		basePanel.setStylePrimaryName(BASEPANEL_CSS);
		
		LabelField headerLbl = new LabelField();
		Configuration headerLblConfig = getHeaderLblConfig();
	
		headerLbl.setConfiguration(headerLblConfig);
		headerLbl.configure();
		headerLbl.create();
						
		ButtonField saveConfigBtn = new ButtonField();
		saveConfigBtn.setConfiguration(getSaveConfigurationBtnConf());
		saveConfigBtn.configure();
		saveConfigBtn.create();
		
		String propId = Document.get().createUniqueId();
		ConfPropertyEditor confPropertyEditor = new ConfPropertyEditor(propId);
		confPropertyEditor.createUi();
		
		basePanel.add(headerLbl);
		basePanel.add(confPropertyEditor);
		basePanel.add(saveConfigBtn);
		
		basePanel.setCellHorizontalAlignment(headerLbl, HorizontalPanel.ALIGN_CENTER);
		
		AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE,this);
		initWidget(basePanel);
	}
	
	private Configuration getHeaderLblConfig() {
		Configuration configuration = null;	
		try{
			configuration = new Configuration();
			configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, "Configuration Editor ");
			configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, HEADERLBL_CSS);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return configuration;
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

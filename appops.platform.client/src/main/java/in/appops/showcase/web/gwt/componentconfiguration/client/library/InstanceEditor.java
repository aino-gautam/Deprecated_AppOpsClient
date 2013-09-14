package in.appops.showcase.web.gwt.componentconfiguration.client.library;

import in.appops.client.common.config.field.StateField;
import in.appops.client.common.config.field.StateField.StateFieldConstant;
import in.appops.client.common.config.field.suggestion.AppopsSuggestion;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.client.common.fields.TextField;
import in.appops.client.common.fields.TextField.TextFieldConstant;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.entity.type.MetaType;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;
import in.appops.platform.core.util.EntityList;

import com.google.gwt.user.client.ui.FlexTable;

public class InstanceEditor implements FieldEventHandler{
	
	private String keyName = null;
	private EntityList configList = null;
	private TextField keyNameField;
	private StateField keyValueField;
	private FlexTable propValuePanel;
	private int valuePanelRow;
	private Entity parentConfigInstanceEntity = null;
	private Entity configEnt = null;
	
	/** Field ID **/
	private String KEYNAME_FIELD_ID = "instanceNameFieldId";
	private String KEYVALUE_FIELD_ID = "instanceValueFieldId";
	private final String KEYNAME_FIELD_PCLS = "propertyValueField";
	
	/** CSS style **/
	private final String SUGGESTIONBOX_PCLS = "componentNameSuggestionBox";
	
	public InstanceEditor(FlexTable propValuePanel, int valuePanelRow ,String keyName, EntityList list, Entity parentConfigInstanceEntity) {
		this.keyName = keyName;
		this.configList = list;
		this.propValuePanel = propValuePanel;
		this.valuePanelRow = valuePanelRow;
		this.parentConfigInstanceEntity = parentConfigInstanceEntity;
	}
	
	public void createUi(){
		try {
			keyNameField = new TextField();
			keyNameField.setConfiguration(getKeyNameFieldConf());
			keyNameField.configure();
			keyNameField.create();
			
			Long configTypeId = ((Key<Long>)configList.get(0).getPropertyByName("id")).getKeyValue();
			
			KEYVALUE_FIELD_ID = KEYVALUE_FIELD_ID+ configTypeId;
			keyValueField = new StateField();
			keyValueField.setConfiguration(getKeyValueSuggestionFieldConf());
			keyValueField.configure();
			keyValueField.create();	
			
			propValuePanel.setWidget(valuePanelRow, 0,keyNameField);
			propValuePanel.setWidget(valuePanelRow, 1,keyValueField);
			
			AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE, this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private Configuration getKeyValueSuggestionFieldConf() {

		Configuration configuration = new Configuration();
		try {
			configuration.setPropertyByName(StateFieldConstant.IS_STATIC_BOX, true);
			configuration.setPropertyByName(StateFieldConstant.STFD_ENTPROP,"keyvalue");
			configuration.setPropertyByName(StateFieldConstant.BF_ID, KEYVALUE_FIELD_ID);
			configuration.setPropertyByName(StateFieldConstant.IS_AUTOSUGGESTION,true);
			configuration.setPropertyByName(StateFieldConstant.BF_SUGGESTION_TEXT,"Key value");
			configuration.setPropertyByName(StateFieldConstant.BF_PCLS,SUGGESTIONBOX_PCLS);
			configuration.setPropertyByName(StateFieldConstant.BF_SUGGESTION_POS,StateFieldConstant.BF_SUGGESTION_INLINE);
			configuration.setPropertyByName(StateFieldConstant.ITEMS_LIST, configList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return configuration;
	}
	
	/**
	 * Method creates the key name field configuration object and return.
	 * @return Configuration instance
	 */
	private Configuration getKeyNameFieldConf() {
		Configuration configuration = new Configuration();

		try {
			configuration.setPropertyByName(TextFieldConstant.TF_TYPE, TextFieldConstant.TFTYPE_TXTBOX);
			configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_POS, TextFieldConstant.BF_SUGGESTION_INLINE);
			configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_TEXT, "Enter a value for the property");
			configuration.setPropertyByName(TextFieldConstant.BF_DEFVAL, keyName);
			configuration.setPropertyByName(TextFieldConstant.BF_ID, KEYNAME_FIELD_ID);
			configuration.setPropertyByName(TextFieldConstant.BF_PCLS, KEYNAME_FIELD_PCLS);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return configuration;
	}

	@Override
	public void onFieldEvent(FieldEvent event) {
		int eventType = event.getEventType();
		if(eventType == FieldEvent.SUGGESTION_SELECTED) {
			if(event.getEventSource() instanceof StateField) {
				StateField stateField = (StateField) event.getEventSource();
				if(stateField.getBaseFieldId().equals(KEYVALUE_FIELD_ID)){
					AppopsSuggestion appopsSuggestion = (AppopsSuggestion) event.getEventData();
					configEnt = appopsSuggestion.getEntity();
				}
			}
		}
		
	}
	
	public Entity getPopulatedConfigInstanceEntity() throws AppOpsException {
		try {
			Entity configInstance = new Entity();
			configInstance.setType(new MetaType("Configinstance"));

			
			if (keyNameField.getValue().toString().equals("")) {
				throw new AppOpsException("Instance name cannot be empty");
			} else {
				configInstance.setPropertyByName("instancename", keyNameField.getValue().toString());
				configInstance.setPropertyByName("configkeyname", keyNameField.getValue().toString());
			}

			if (keyValueField.getValue().toString()!=null) {
				throw new AppOpsException("Instance value cannot be empty");
			} else {
				configInstance.setPropertyByName("instancevalue", keyValueField.getValue().toString());
			}
			
			configInstance.setProperty("configinstance", parentConfigInstanceEntity);
			configInstance.setProperty("configtype", configEnt);
			
			return configInstance;

		} catch (Exception e) {
			if(e instanceof AppOpsException){
				AppOpsException ex = (AppOpsException) e;
				throw ex;
			}
		}
		return null;
	}
}

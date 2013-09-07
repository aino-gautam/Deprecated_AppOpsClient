package in.appops.showcase.web.gwt.componentconfiguration.client.library;

import in.appops.client.common.config.field.ImageField;
import in.appops.client.common.config.field.ImageField.ImageFieldConstant;
import in.appops.client.common.config.field.ListBoxField;
import in.appops.client.common.config.field.ListBoxField.ListBoxFieldConstant;
import in.appops.client.common.config.field.RadioButtonField;
import in.appops.client.common.config.field.RadioButtonField.RadionButtonFieldConstant;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.ConfigEvent;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.client.common.fields.TextField;
import in.appops.client.common.fields.TextField.TextFieldConstant;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.entity.type.MetaType;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.EntityList;

import com.emitrom.lienzo.client.core.mediator.IMediator;
import com.google.gwt.user.client.ui.FlexTable;

public class PropertyValueEditor implements FieldEventHandler {
	
	private FlexTable propValuePanel;
	private int valuePanelRow;
	private Entity confTypeEntity;
	private TextField valueField;
	private ListBoxField typeField;
	private RadioButtonField isDefaultValueField;
	private ImageField crossImgField;
	private long entityId = 0;
	
	/*******************  Fields ID *****************************/
	public final String PROPNAME_FIELD_ID = "attributeFieldId";
	public final String INTVAL_FIELD_ID = "intValFieldId";
	public final String STRINGVAL_FIELD_ID = "stringValFieldId";
	public static final String ISDEF_RADIOBTN_GROUP_ID = "isDefaultRadioBtnGroup";
	public static final String ISDEF_RADIOBTN_ID = "isDefaultRadioBtnId";
	private String REMOVEPROP_IMGID = "removePropImgId";
	
	
	/** CSS styles **/
	private final String CROSSIMG_CSS = "removePropertyImage";
	private final String TYPEFIELD_PCLS = "emsTypeListBox";
	private final String ISDEFAULT_PCLS = "isDefaultField";
	private final String VALUEFIELD_PCLS = "propertyValueField";
	
	public PropertyValueEditor() {
		
	}

	public PropertyValueEditor(FlexTable propValuePanel, int valuePanelRow ,Entity confTypeEntity) {
		
		this.confTypeEntity = confTypeEntity;
		this.propValuePanel = propValuePanel;
		this.valuePanelRow = valuePanelRow;
	}

	public void createUi(){
		valueField = new TextField();
		valueField.setConfiguration(getValueFieldConf());
		valueField.configure();
		valueField.create();
		
		typeField = new ListBoxField();
		typeField.setConfiguration(getTypeBoxConfig());
		typeField.configure();
		typeField.create();
				
		isDefaultValueField = new RadioButtonField();
		
		isDefaultValueField.setConfiguration(getIsDefRadioBtnFieldConf());
		isDefaultValueField.configure();
		isDefaultValueField.create();
		
				
		crossImgField = new ImageField();
		crossImgField.setConfiguration(getCrossImageConfiguration());
		crossImgField.configure();
		crossImgField.create();
		
		propValuePanel.setWidget(valuePanelRow, 1, valueField);
		propValuePanel.setWidget(valuePanelRow, 2, typeField);
		propValuePanel.setWidget(valuePanelRow, 3, isDefaultValueField);
		propValuePanel.setWidget(valuePanelRow, 4, crossImgField);
		
		
		AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE,this);
		
	}
	
	/**
	 * Creates the cross image field configuration object and return.
	 * @return Configuration instance
	 */
	private Configuration getCrossImageConfiguration(){
		Configuration configuration = new Configuration();
		try {
			configuration.setPropertyByName(ImageFieldConstant.IMGFD_BLOBID, "images/minus-icon.jpg");
			configuration.setPropertyByName(ImageFieldConstant.BF_PCLS,CROSSIMG_CSS);
			configuration.setPropertyByName(ImageFieldConstant.IMGFD_TITLE, "Remove property");
			configuration.setPropertyByName(ImageFieldConstant.BF_ID, REMOVEPROP_IMGID);
		} catch (Exception e) {
			
		}
		return configuration;
	}
	
	
	public void setEntityIdToCrossImage(long id){
		
		try {
			entityId= id;
			Configuration configuration = crossImgField.getConfiguration();
			REMOVEPROP_IMGID = REMOVEPROP_IMGID+"_"+String.valueOf(id);
			
			configuration.setPropertyByName(ImageFieldConstant.BF_ID, REMOVEPROP_IMGID);
			crossImgField.removeRegisteredHandlers();
			crossImgField.setConfiguration(configuration);
			crossImgField.configure();
			crossImgField.create();
		} catch (Exception e) {
			
		}
		
	}
	
	private EntityList getDummyTypeList(){
		Entity inte = new Entity();
		Key<Long> key1 = new Key<Long>(2L);
		inte.setPropertyByName("id",key1);
		inte.setPropertyByName("name","Integer");
		
		Entity str = new Entity();
		Key<Long> key2 = new Key<Long>(3L);
		str.setPropertyByName("id",key2);
		str.setPropertyByName("name","String");
		
		Entity bool = new Entity();
		Key<Long> key3 = new Key<Long>(22L);
		bool.setPropertyByName("id",key3);
		bool.setPropertyByName("name","Boolean");
		
		Entity conf = new Entity();
		Key<Long> key4 = new Key<Long>(199L);
		conf.setPropertyByName("id",key4);
		conf.setPropertyByName("name","Configuration");
						
		EntityList list = new EntityList();
		list.add(inte);
		list.add(str);
		list.add(bool);
		list.add(conf);
		return list;
	}
	
	private Configuration getTypeBoxConfig() {
		Configuration configuration = new Configuration();
		try {
						
			configuration.setPropertyByName(ListBoxFieldConstant.BF_DEFVAL,"---Select the type ---");
			configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_ITEMS,getDummyTypeList());
			configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_ENTPROP,"name");
			configuration.setPropertyByName(ListBoxFieldConstant.BF_PCLS, TYPEFIELD_PCLS);
		} catch (Exception e) {
			
		}
		
		return configuration;
	}
	

	/**
	 * Method creates the value field configuration object and return.
	 * @return Configuration instance
	 */
	private Configuration getValueFieldConf(){
		Configuration configuration = new Configuration();
		
		try {
			configuration.setPropertyByName(TextFieldConstant.TF_TYPE, TextFieldConstant.TFTYPE_TXTBOX);
			
			configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_POS, TextFieldConstant.BF_SUGGESTION_INLINE);
			configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_TEXT, "Enter a value for the property");
			
			/*if(confTypeEntity!=null)
			configuration.setPropertyByName(TextFieldConstant.BF_DEFVAL, confTypeEntity.getPropertyByName("stringvalue").toString());*/
		
		if(confTypeEntity!=null)
			if(confTypeEntity.getPropertyByName("keyvalue")!=null)
			   configuration.setPropertyByName(TextFieldConstant.BF_DEFVAL, confTypeEntity.getPropertyByName("keyvalue").toString());
			else
				configuration.setPropertyByName(TextFieldConstant.BF_DEFVAL, "--");
			
			configuration.setPropertyByName(TextFieldConstant.BF_ID, STRINGVAL_FIELD_ID);
			configuration.setPropertyByName(TextFieldConstant.BF_PCLS, VALUEFIELD_PCLS);
			
			//configuration.setPropertyByName(TextFieldConstant.BF_BLANK_TEXT,"Attribute can't be empty");
			//configuration.setPropertyByName(TextFieldConstant.BF_ALLOWBLNK,false);
			//configuration.setPropertyByName(TextFieldConstant.BF_ERRPOS,TextFieldConstant.BF_ERRINLINE);
			
		} catch (Exception e) {
		}
		return configuration;
	}
	
	/**
	 * Method creates the is default radio button field configuration object and return.
	 * @return Configuration instance
	 */
	private Configuration getIsDefRadioBtnFieldConf(){
		Configuration configuration = new Configuration();
		
		try {
			boolean isDefault = false;
			
			if(confTypeEntity!=null){
				isDefault = Boolean.valueOf(confTypeEntity.getPropertyByName("isdefault").toString());
			}
			configuration.setPropertyByName(RadionButtonFieldConstant.RF_CHECKED, isDefault);
			configuration.setPropertyByName(RadionButtonFieldConstant.RF_DISPLAYTEXT, "Default");
			configuration.setPropertyByName(RadionButtonFieldConstant.RF_GROUPID, ISDEF_RADIOBTN_GROUP_ID);
			configuration.setPropertyByName(RadionButtonFieldConstant.BF_ID, ISDEF_RADIOBTN_ID);
			configuration.setPropertyByName(RadionButtonFieldConstant.BF_PCLS, ISDEFAULT_PCLS);
			//configuration.setPropertyByName(RadionButtonFieldConstant.BF_TABINDEX, 3);
		} catch (Exception e) {
			
		}
		return configuration;
	}
	
	public Entity getPopulatedConfigTypeEntity(String propName){
		if (confTypeEntity == null) {
			Entity confTypeEntity = new Entity();
			confTypeEntity.setType(new MetaType("Configtype"));
			confTypeEntity.setPropertyByName("keyname", propName);
			confTypeEntity.setPropertyByName("keyvalue", valueField.getValue().toString());
			confTypeEntity.setPropertyByName("emstypeId",  Long.parseLong(typeField.getSelectedValue().toString()));
			confTypeEntity.setPropertyByName("isdefault", Boolean.valueOf(isDefaultValueField.getValue().toString()));
			return confTypeEntity;
		}
		return confTypeEntity;
		
	}

	public Entity getConfTypeEntity() {
		return confTypeEntity;
	}

	public void setConfTypeEntity(Entity confTypeEntity) {
		this.confTypeEntity = confTypeEntity;
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
					if(imageField.getBaseFieldId().equals(REMOVEPROP_IMGID)){
						if(entityId!=0){
							ConfigEvent configEvent = new ConfigEvent(ConfigEvent.PROPERTYREMOVED, entityId, this);
							AppUtils.EVENT_BUS.fireEvent(configEvent);
						}
					}
				}
				break;
			}default:
				
				break;
			}
		} catch (Exception e) {
			
		}
	}
}

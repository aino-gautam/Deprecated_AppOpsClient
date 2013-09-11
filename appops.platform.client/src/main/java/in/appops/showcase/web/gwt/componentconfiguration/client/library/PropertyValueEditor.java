package in.appops.showcase.web.gwt.componentconfiguration.client.library;

import java.util.HashMap;

import in.appops.client.common.config.field.CheckboxField;
import in.appops.client.common.config.field.CheckboxField.CheckBoxFieldConstant;
import in.appops.client.common.config.field.ImageField;
import in.appops.client.common.config.field.ImageField.ImageFieldConstant;
import in.appops.client.common.config.field.ListBoxField;
import in.appops.client.common.config.field.ListBoxField.ListBoxFieldConstant;
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
import in.appops.platform.core.util.AppOpsException;
import in.appops.platform.core.util.EntityList;

import com.google.gwt.user.client.ui.FlexTable;

public class PropertyValueEditor implements FieldEventHandler {
	
	private FlexTable propValuePanel;
	private int valuePanelRow;
	private Entity confTypeEntity;
	private TextField valueField;
	private ListBoxField typeField;
	private CheckboxField isDefaultValueField;
	private ImageField crossImgField;
	private ImageField savedImgField;
	private long entityId = 0;
	
	/*******************  Fields ID *****************************/
	private String PROPNAME_FIELD_ID = "attributeFieldId";
	private String VALUE_FIELD_ID = "stringValFieldId";
	private  String TYPEFIELD_ID = "typeFieldId";
	
	public static final String ISDEF_CHKBOX_GROUP_ID = "isDefaultChkBoxGroup";
	private String ISDEF_CHKBOX_ID = "isDefaultChkBoxId";
	
	private String REMOVEPROP_IMGID = "removePropImgId";
	private String TYPEFIELD_DEFVAL ="---Select the type ---";
	
	
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
		
		ISDEF_CHKBOX_ID = ISDEF_CHKBOX_ID + valuePanelRow;
		PROPNAME_FIELD_ID = PROPNAME_FIELD_ID+valuePanelRow;
		VALUE_FIELD_ID = VALUE_FIELD_ID+valuePanelRow;
		TYPEFIELD_ID = TYPEFIELD_ID+valuePanelRow;
		
		valueField = new TextField();
		valueField.setConfiguration(getValueFieldConf());
		valueField.configure();
		valueField.create();
		
		typeField = new ListBoxField();
		typeField.setConfiguration(getTypeBoxConfig());
		typeField.configure();
		typeField.create();
				
		isDefaultValueField = new CheckboxField();
		
		isDefaultValueField.setConfiguration(getIsDefCheckBoxFieldConf());
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
	
	/**
	 * Creates the check image field configuration object and return.
	 * @return Configuration instance
	 */
	private Configuration getCheckImageConfiguration(){
		Configuration configuration = new Configuration();
		try {
			configuration.setPropertyByName(ImageFieldConstant.IMGFD_BLOBID, "images/check-icon.jpg");
			configuration.setPropertyByName(ImageFieldConstant.BF_PCLS,CROSSIMG_CSS);
			configuration.setPropertyByName(ImageFieldConstant.IMGFD_TITLE, "property saved.");
			configuration.setPropertyByName(ImageFieldConstant.BF_ID, REMOVEPROP_IMGID);
			configuration.setPropertyByName(ImageFieldConstant.BF_VISIBLE, true);
		} catch (Exception e) {
			
		}
		return configuration;
	}
	
	public void showCheckImage(){
		try {
			savedImgField = new ImageField();
			savedImgField.setConfiguration(getCheckImageConfiguration());
			savedImgField.configure();
			savedImgField.create();
			propValuePanel.setWidget(valuePanelRow, 5, savedImgField);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
						
			configuration.setPropertyByName(ListBoxFieldConstant.BF_DEFVAL,TYPEFIELD_DEFVAL);
			configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_ITEMS,getDummyTypeList());
			configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_ENTPROP,"name");
			configuration.setPropertyByName(ListBoxFieldConstant.BF_PCLS, TYPEFIELD_PCLS);
			configuration.setPropertyByName(ListBoxFieldConstant.BF_ID, TYPEFIELD_ID);
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
			
			configuration.setPropertyByName(TextFieldConstant.BF_ID, VALUE_FIELD_ID);
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
	private Configuration getIsDefCheckBoxFieldConf(){
		Configuration configuration = new Configuration();
		
		try {
			boolean isDefault = false;
			
			if(confTypeEntity!=null){
				isDefault = Boolean.valueOf(confTypeEntity.getPropertyByName("isdefault").toString());
			}
			configuration.setPropertyByName(CheckBoxFieldConstant.CF_CHECKED, isDefault);
			configuration.setPropertyByName(CheckBoxFieldConstant.CF_DISPLAYTEXT, "Default");
			configuration.setPropertyByName(CheckBoxFieldConstant.CF_GROUPID, ISDEF_CHKBOX_GROUP_ID);
			configuration.setPropertyByName(CheckBoxFieldConstant.BF_ID, ISDEF_CHKBOX_ID);
			configuration.setPropertyByName(CheckBoxFieldConstant.BF_PCLS, ISDEFAULT_PCLS);
			//configuration.setPropertyByName(RadionButtonFieldConstant.BF_TABINDEX, 3);
		} catch (Exception e) {
			
		}
		return configuration;
	}
	
	public Entity getPopulatedConfigTypeEntity(String propName) throws AppOpsException {
		try {
			if (confTypeEntity == null) {
				confTypeEntity = new Entity();
			}
			confTypeEntity.setType(new MetaType("Configtype"));
			confTypeEntity.setPropertyByName("keyname", propName);
			
			if(propName.equals("")){
				throw new AppOpsException("keyname can't be empty");
			}
			
			
			if(valueField.getValue().toString().equals("")){
				throw new AppOpsException("keyvalue value can't be empty");
			}else{
				confTypeEntity.setPropertyByName("keyvalue", valueField.getValue().toString());
			}
			
			
			if(typeField.getValue().toString().equals(TYPEFIELD_DEFVAL)){
				throw new AppOpsException("Please select type");
			}else{
				confTypeEntity.setPropertyByName("emstypeId",Long.parseLong(typeField.getSelectedValue().toString()));
			}
			
			confTypeEntity.setPropertyByName("isdefault",Boolean.valueOf(isDefaultValueField.getValue().toString()));
			return confTypeEntity;
		} catch (NumberFormatException e) {
			throw e;
		}

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
			}case FieldEvent.VALUECHANGED: {
				if (eventSource instanceof ListBoxField) {
					ListBoxField listboxField = (ListBoxField) eventSource;
					if(listboxField.getBaseFieldId().equals(TYPEFIELD_ID)){
						if(entityId!=0){
							ConfigEvent configEvent = new ConfigEvent(ConfigEvent.CONFIGTYPE_UPDATED, confTypeEntity , this);
							AppUtils.EVENT_BUS.fireEvent(configEvent);
						}
					}
				}
				break;
			}case FieldEvent.EDITINPROGRESS: {
				if (eventSource instanceof TextField) {
					TextField listboxField = (TextField) eventSource;
					if(listboxField.getBaseFieldId().equals(VALUE_FIELD_ID)){
						if(entityId!=0){
							ConfigEvent configEvent = new ConfigEvent(ConfigEvent.CONFIGTYPE_UPDATED, confTypeEntity , this);
							AppUtils.EVENT_BUS.fireEvent(configEvent);
						}
					}
				}
				break;
			}case FieldEvent.CHECKBOX_SELECT: {
				if (eventSource instanceof CheckboxField) {
					CheckboxField chkField = (CheckboxField) eventSource;
					if(chkField.getBaseFieldId().equals(ISDEF_CHKBOX_ID)){
						HashMap<String, Object> map = new HashMap<String, Object>();
						map.put("updated", false);
						map.put("checkboxField", chkField);
						
						if(entityId!=0){
							map.put("updated", true);
						}
						ConfigEvent configEvent = new ConfigEvent(ConfigEvent.DEFAULT_PROP_SELECTED, map , this);
						AppUtils.EVENT_BUS.fireEvent(configEvent);
					}
				}
				break;
			}case FieldEvent.CHECKBOX_DESELECT: {
				if (eventSource instanceof CheckboxField) {
					CheckboxField chkField = (CheckboxField) eventSource;
					HashMap<String, Object> map = new HashMap<String, Object>();
					if (chkField.getBaseFieldId().equals(ISDEF_CHKBOX_ID)) {
						
						map.put("updated", false);
						map.put("checkboxField", chkField);
						
						if(entityId!=0){
							map.put("updated", true);
							
						}
						ConfigEvent configEvent = new ConfigEvent(ConfigEvent.DEFAULT_PROP_DESELECTED, map , this);
						AppUtils.EVENT_BUS.fireEvent(configEvent);
						
					}
				}
				break;
			}default:
				
				break;
			}
		} catch (Exception e) {
			
		}
	}

	public CheckboxField getIsDefaultValueField() {
		return isDefaultValueField;
	}

	public void setIsDefaultValueField(CheckboxField isDefaultValueField) {
		this.isDefaultValueField = isDefaultValueField;
	}
}

package in.appops.showcase.web.gwt.componentconfiguration.client.library;

import in.appops.client.common.config.field.ImageField;
import in.appops.client.common.config.field.ImageField.ImageFieldConstant;
import in.appops.client.common.config.field.ListBoxField;
import in.appops.client.common.config.field.ListBoxField.ListBoxFieldConstant;
import in.appops.client.common.config.field.RadioButtonField;
import in.appops.client.common.config.field.RadioButtonField.RadionButtonFieldConstant;
import in.appops.client.common.fields.TextField;
import in.appops.client.common.fields.TextField.TextFieldConstant;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.entity.type.MetaType;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.EntityList;

import com.google.gwt.user.client.ui.FlexTable;

public class PropertyValueEditor {
	
	private FlexTable propValuePanel;
	private int valuePanelRow;
	private Entity confTypeEntity;
	private String propName;
	private TextField valueField;
	private ListBoxField typeField;
	private RadioButtonField isDefaultValueField;
	
	/*******************  Fields ID *****************************/
	public final String PROPNAME_FIELD_ID = "attributeFieldId";
	public final String INTVAL_FIELD_ID = "intValFieldId";
	public final String STRINGVAL_FIELD_ID = "stringValFieldId";
	public static final String ISDEF_RADIOBTN_GROUP_ID = "isDefaultRadioBtnGroup";
	public static final String ISDEF_RADIOBTN_ID = "isDefaultRadioBtnId";
	
	/** CSS styles **/
	public static final String REMOVEPROP_IMGID = "removePropImgId";
	private final String CROSSIMG_CSS = "removePropertyImage";
	
	public PropertyValueEditor() {
		
	}

	public PropertyValueEditor(String propName, FlexTable propValuePanel, int valuePanelRow ,Entity confTypeEntity) {
		this.propName = propName;
		this.confTypeEntity = confTypeEntity;
		this.propValuePanel = propValuePanel;
		this.valuePanelRow = valuePanelRow;
	}

	public void createUi(){
		valueField = new TextField();
		valueField.setConfiguration(getStringValueFieldConf());
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
		
		ImageField crossImgField = new ImageField();
		crossImgField.setConfiguration(getCrossImageConfiguration());
		crossImgField.configure();
		crossImgField.create();
		
		propValuePanel.setWidget(valuePanelRow, 1, valueField);
		propValuePanel.setWidget(valuePanelRow, 2, typeField);
		propValuePanel.setWidget(valuePanelRow, 3, isDefaultValueField);
		propValuePanel.setWidget(valuePanelRow, 4, crossImgField);
		
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
		} catch (Exception e) {
			
		}
		
		return configuration;
	}
	

	/**
	 * Method creates the string value field configuration object and return.
	 * @return Configuration instance
	 */
	private Configuration getStringValueFieldConf(){
		Configuration configuration = new Configuration();
		
		try {
			configuration.setPropertyByName(TextFieldConstant.TF_TYPE, TextFieldConstant.TFTYPE_TXTBOX);
			
			configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_POS, TextFieldConstant.BF_SUGGESTION_INLINE);
			configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_TEXT, "Add string value");
			
			if(confTypeEntity!=null)
				configuration.setPropertyByName(TextFieldConstant.BF_DEFVAL, confTypeEntity.getPropertyByName("stringvalue").toString());
			
			configuration.setPropertyByName(TextFieldConstant.BF_ID, STRINGVAL_FIELD_ID);
			
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
			boolean isDefault = true;
			
			if(confTypeEntity!=null){
				isDefault = Boolean.valueOf(confTypeEntity.getPropertyByName("isdefault").toString());
			}
			configuration.setPropertyByName(RadionButtonFieldConstant.RF_CHECKED, isDefault);
			configuration.setPropertyByName(RadionButtonFieldConstant.RF_GROUPID, ISDEF_RADIOBTN_GROUP_ID);
			configuration.setPropertyByName(RadionButtonFieldConstant.BF_ID, ISDEF_RADIOBTN_ID);
		} catch (Exception e) {
			
		}
		return configuration;
	}
	
	public Entity getPopulatedConfigTypeEntity(){
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
}

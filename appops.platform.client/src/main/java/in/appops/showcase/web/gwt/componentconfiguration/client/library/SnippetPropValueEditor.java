package in.appops.showcase.web.gwt.componentconfiguration.client.library;

import in.appops.client.common.config.field.ImageField;
import in.appops.client.common.config.field.ImageField.ImageFieldConstant;
import in.appops.client.common.fields.TextField;
import in.appops.client.common.fields.TextField.TextFieldConstant;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.type.MetaType;
import in.appops.platform.core.shared.Configuration;

import com.google.gwt.user.client.ui.FlexTable;

/**
 * @author mahesh@ensarm.com
 *
 */
public class SnippetPropValueEditor {

	private FlexTable propValuePanel;
	private int valuePanelRow;
	private Entity confInstanceEntity;
	private TextField paramNameField;
	private TextField paramValueField;
	private final String CROSSIMG_CSS = "removePropertyImage";
	
	
	private ImageField removePropImgFld;
	
	/*******************  Fields ID *****************************/
	private final String PARANAME_FIELD_ID = "paramNameFieldId";
	private final String PARAMVALUE_FLD_ID = "paramValFldId";
	private final String REMOVEPROP_IMGID = "removePropImgId";
	
	public SnippetPropValueEditor() {
		
	}

	public void createUi(){
				
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
		
		propValuePanel.insertRow(valuePanelRow);
		
		propValuePanel.setWidget(valuePanelRow, 3, paramNameField);
		propValuePanel.setWidget(valuePanelRow, 5, paramValueField);
		propValuePanel.setWidget(valuePanelRow, 7, removePropImgFld);
		
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
			
			configuration.setPropertyByName(TextFieldConstant.BF_ID, PARAMVALUE_FLD_ID);
			
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
/*			if (confDefEntity == null) {
				confDefEntity = new Entity();
				confDefEntity.setType(new MetaType("Configurationdef"));
			}

			confDefEntity.setPropertyByName("key", Integer.parseInt(paramNameField.getValue().toString()));
			confDefEntity.setPropertyByName("intvalue", Integer.parseInt(typeField.getValue().toString()));
			confDefEntity.setPropertyByName("stringvalue", Integer.parseInt(paramNameField.getValue().toString()));
			confDefEntity.setPropertyByName("isdefault", isDefaultValueField.getValue().toString());*/
			
			Entity configInstance = new Entity();
			configInstance.setType(new MetaType("Configinstance"));
			
			configInstance.setPropertyByName("name", paramNameField.getValue().toString());
			configInstance.setPropertyByName("instancevalue", paramValueField.getValue().toString());
			configInstance.setProperty("parentId", confInstanceEntity);

		}catch (Exception e) {
			e.printStackTrace();
		}
		return confInstanceEntity;
	}
}

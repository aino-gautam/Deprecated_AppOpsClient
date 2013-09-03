package in.appops.showcase.web.gwt.componentconfiguration.client.library;

import java.util.ArrayList;

import in.appops.client.common.config.field.ImageField;
import in.appops.client.common.config.field.ImageField.ImageFieldConstant;
import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.EntityList;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * 
 * @author pallavi@ensarm.com
 *
 */
public class ConfPropertyEditor extends VerticalPanel implements FieldEventHandler{
	
	private String ADD_NEW_PROPVAL_IMGID = "addNewPropValImgId";
	private String NEWVALUE_CSS = "addNewPropertyImage";
	private String PROPPANEL_CSS = "properyPanelCss";
	private String PROP_LABEL_CSS = "compRegisterLabelCss";
	
	private FlexTable propValuePanel;
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
			
			LabelField propNameLbl = new LabelField();
			propNameLbl.setConfiguration(getLabelFieldConf("Property Name",PROP_LABEL_CSS,null,null));
			propNameLbl.configure();
			propNameLbl.create();
			
			LabelField strValLbl = new LabelField();
			strValLbl.setConfiguration(getLabelFieldConf("StringValue",PROP_LABEL_CSS,null,null));
			strValLbl.configure();
			strValLbl.create();
			
			LabelField intValLbl = new LabelField();
			intValLbl.setConfiguration(getLabelFieldConf("IntValue",PROP_LABEL_CSS,null,null));
			intValLbl.configure();
			intValLbl.create();
			
			LabelField isDefLbl = new LabelField();
			isDefLbl.setConfiguration(getLabelFieldConf("isDefault",PROP_LABEL_CSS,null,null));
			isDefLbl.configure();
			isDefLbl.create();
			
			ImageField addValueImgField = new ImageField();
			addValueImgField.setConfiguration(getPlusImageConfiguration());
			addValueImgField.configure();
			addValueImgField.create();
			
			propValuePanel.setWidget(valuePanelRow, 0, propNameLbl);
			propValuePanel.setWidget(valuePanelRow, 3, intValLbl);
			propValuePanel.setWidget(valuePanelRow, 5, strValLbl);
			propValuePanel.setWidget(valuePanelRow, 7, isDefLbl);
			propValuePanel.setWidget(valuePanelRow, 9, addValueImgField);
			
			for(Entity confEnt:componentDeflist){
				valuePanelRow++;
				PropertyValueEditor propValueEditor = new PropertyValueEditor(propValuePanel, valuePanelRow, confEnt);
				propValueEditor.createUi();
				
				if(propValueList == null){
					propValueList = new ArrayList<PropertyValueEditor>();
				}
				propValueList.add(propValueEditor);
			}
			
			add(propValuePanel);
			setStylePrimaryName(PROPPANEL_CSS);
			AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE,this);
			
		} catch (Exception e) {
			
		}
	}
	
	private void addNewPropertyValue(){
		valuePanelRow++;
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
}

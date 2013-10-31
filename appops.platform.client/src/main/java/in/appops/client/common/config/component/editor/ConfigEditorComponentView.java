package in.appops.client.common.config.component.editor;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

import in.appops.client.common.config.component.base.BaseComponent;
import in.appops.client.common.config.component.editor.ConfigEditorComponentPresenter.ConfigEditorComponentConstant;
import in.appops.client.common.config.dsnip.DynamicMvpFactory;
import in.appops.client.common.config.field.FieldPresenter;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.client.common.config.field.StateField.StateFieldConstant;
import in.appops.client.common.config.model.ConfigurationModel;
import in.appops.client.common.config.model.PropertyModel;
import in.appops.client.common.core.EntityReceiver;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.util.EntityList;

public class ConfigEditorComponentView  extends BaseComponent implements EntityReceiver  {
	
	protected FlexTable configEditorFlex;
	private Entity currentConfigTypeEntity;
	
	private Image  loaderImage = null;
	int currentRow;
	    
	private final String CONFIG_TYPES = "configtypes";
	private final String VIEW_CONFIG_TYPES = "viewconfigtypes";
			
	@Override
	public void initialize() {
		super.initialize();
		configEditorFlex = new FlexTable();
		((ConfigurationModel)model).setReceiver(this);
	}
	
	@Override
	public void configure() {
		super.configure();
		if(viewConfiguration.getConfigurationValue(ConfigEditorComponentConstant.CE_EDITORCSS) != null) {
			String primaryCss = viewConfiguration.getConfigurationValue(ConfigEditorComponentConstant.CE_EDITORCSS).toString();
			configEditorFlex.setStylePrimaryName(primaryCss);
		}
		
	}
	
	@Override
	public void create() {
		super.create();
		
		if(currentConfigTypeEntity != null){
			Entity viewConfigTypeEntity = currentConfigTypeEntity.getPropertyByName(CONFIG_TYPES);
			HashMap<String, EntityList> viewConfigTypesMap = getGroupedConfigTypeMap(viewConfigTypeEntity);
			
			for(String propName : viewConfigTypesMap.keySet()){
				EntityList list= viewConfigTypesMap.get(propName);
				
				PropertyModel lblFldModel = ((ConfigurationModel)model).getPropertyModel();
				FieldPresenter lblFldPresenter = new FieldPresenter(DynamicMvpFactory.LABELFIELD, "cieLabelFieldConfig", lblFldModel);
				lblFldPresenter.configure();
				lblFldPresenter.create();
				lblFldModel.updateConfiguration(LabelFieldConstant.BF_DEFVAL, propName);
				lblFldPresenter.configure();
				
				PropertyModel listBoxFldModel = ((ConfigurationModel)model).getPropertyModel();
				FieldPresenter listBoxFldPresenter = new FieldPresenter(DynamicMvpFactory.LISTBOXFIELD, "cieListBoxFieldConfig", listBoxFldModel);
				listBoxFldPresenter.configure();
				listBoxFldPresenter.create();
				listBoxFldModel.updateConfiguration(StateFieldConstant.ITEMS_LIST, list);
				listBoxFldPresenter.configure();
				
				configEditorFlex.setWidget(currentRow, 0, lblFldPresenter.getView());
				configEditorFlex.setWidget(currentRow, 1, listBoxFldPresenter.getView());
				currentRow++;
				
			}
			
			PropertyModel actionFldModel = ((ConfigurationModel)model).getPropertyModel();
			FieldPresenter actionFldPresenter = new FieldPresenter(DynamicMvpFactory.ACTIONFIELD, "cieActionButtonFieldConfig", actionFldModel);
			actionFldPresenter.configure();
			actionFldPresenter.create();
			
			configEditorFlex.setWidget(currentRow, 0, actionFldPresenter.getView());
		}else{
			Label errorLbl = new Label();
			String errorMsg = viewConfiguration.getConfigurationValue(ConfigEditorComponentConstant.CE_ERRORMSG).toString();
			if(errorMsg != null)
				errorLbl.setText(errorMsg);
			else
				errorLbl.setText("No configurations avaialable");
			
			configEditorFlex.setWidget(currentRow, 0, errorLbl);
		}
		
		basePanel.add(configEditorFlex, DockPanel.CENTER);
		basePanel.setCellHorizontalAlignment(configEditorFlex, HasAlignment.ALIGN_CENTER);
		basePanel.remove(loaderImage);
	}
	
    /**
     * creates a map by grouping entities having similar key names in a map of keyname vs list of entities
     * @param configTypeEntity
     */
    private HashMap<String, EntityList> getGroupedConfigTypeMap(Entity viewConfigTypeEntity) {
            try {
                    String name = null;
                    if (viewConfigTypeEntity.getPropertyByName(VIEW_CONFIG_TYPES) != null) {
                            ArrayList<Entity> entityList = viewConfigTypeEntity.getPropertyByName(VIEW_CONFIG_TYPES);
                            HashMap<String, EntityList> configTypeHashMap = new HashMap<String, EntityList>();

                            for (Entity configTypeEnt : entityList) {
                                    name = configTypeEnt.getPropertyByName("keyname");
                                    if (configTypeHashMap.containsKey(name)) {

                                            EntityList existingConfigTypeList = configTypeHashMap.get(name);
                                            existingConfigTypeList.add(configTypeEnt);
                                            configTypeHashMap.put(name, existingConfigTypeList);
                                    } else {
                                            EntityList list = new EntityList();
                                            list.add(configTypeEnt);
                                            configTypeHashMap.put(name, list);
                                    }
                            }
                            return configTypeHashMap;
                            
                    }
            } catch (Exception e) {
                    e.printStackTrace();
            }
            return null;
    }

	@Override
	public void noMoreData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEntityReceived(Entity entity) {
		if(entity != null)
			currentConfigTypeEntity =  entity;
	
		loaderImage = new Image("images/opptinLoader.gif");
        loaderImage.setStylePrimaryName("appops-intelliThoughtActionImage");
        loaderImage.setVisible(true);
        basePanel.add(loaderImage);
        
		create();
	}

	@Override
	public void onEntityUpdated(Entity entity) {
		// TODO Auto-generated method stub
		
	}

	public void setCurrentConfigEntity(Entity currentConfigEntity) {
		this.currentConfigTypeEntity = currentConfigEntity;
	}

}

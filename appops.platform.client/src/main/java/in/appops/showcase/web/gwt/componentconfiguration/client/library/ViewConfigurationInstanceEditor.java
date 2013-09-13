package in.appops.showcase.web.gwt.componentconfiguration.client.library;

import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.EntityList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ViewConfigurationInstanceEditor extends Composite{

	private VerticalPanel basePanel;
	private ScrollPanel scrollPanel;
	private FlexTable configurationListTable;
	private ArrayList<InstanceEditor> editorList;
	private int currentRow = 0;
		
	public ViewConfigurationInstanceEditor() {
		
	}
	
	public void createUi(){
		try {
			basePanel = new VerticalPanel();
			
			scrollPanel = new ScrollPanel(basePanel);
			scrollPanel.setHeight("300px");
			
			initWidget(scrollPanel);
			//it's hardcoded.
			getConfigTypeEntity(1L);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param configTypeEntity
	 */
	private void initialize(Entity configTypeEntity) {
		try {
			basePanel.clear();
			String name = null;
			if (configTypeEntity.getPropertyByName("configtypes") != null) {
				ArrayList<Entity> entityList = configTypeEntity.getPropertyByName("configtypes");
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
				
				createEditor(configTypeHashMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private void createEditor(HashMap<String, EntityList> configTypeHashMap) {
		try {
			if(editorList==null)
				editorList = new ArrayList<InstanceEditor>();
			
			configurationListTable = new FlexTable();
			
			Iterator it = configTypeHashMap.entrySet().iterator();
			
			while (it.hasNext()) {
			    Map.Entry pairs = (Map.Entry)it.next();
			    EntityList list = (EntityList) pairs.getValue();
			    InstanceEditor instanceEditor = new InstanceEditor(configurationListTable, currentRow ,pairs.getKey().toString(), list);
			    instanceEditor.createUi();
			    editorList.add(instanceEditor);
			    currentRow++;
			}
			
			
			basePanel.add(configurationListTable);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void getConfigTypeEntity(Long configTypeId) {
		try {
			DefaultExceptionHandler exceptionHandler = new DefaultExceptionHandler();
			DispatchAsync dispatch = new StandardDispatchAsync(exceptionHandler);

			/*Map parameterMap = new HashMap();
			parameterMap.put("configurationId", configTypeId);

			StandardAction action = new StandardAction(Entity.class,"appdefinition.AppDefinitionService.getConfigurationtype",parameterMap);*/
			Map parameterMap = new HashMap();
			parameterMap.put("configTypeId", configTypeId);
			
			StandardAction action = new StandardAction(Entity.class, "configuration.ConfigurationService.getConfigTypeFromKey", parameterMap);
			
			dispatch.execute(action, new AsyncCallback<Result<Entity>>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.printStackTrace();
				}

				@Override
				public void onSuccess(Result<Entity> result) {
					if (result != null) {
						Entity configType = result.getOperationResult();
						if (configType != null) {
							initialize(configType);
						}
					}

				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void showNotificationForEmptyConfList() {
		try {
			basePanel.clear();
			LabelField notificationMessageField = createLabelField("No configurations available ","componentSectionHeaderLbl","");
			basePanel.add(notificationMessageField);
			basePanel.setCellHorizontalAlignment(notificationMessageField, HasHorizontalAlignment.ALIGN_CENTER);
			scrollPanel.setStylePrimaryName("propertyConfListScrollPanel");
			configurationListTable.setStylePrimaryName("configurationListTable");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private LabelField createLabelField(String name, String primaryCss,String secondaryCss) {
		try{
			LabelField labelField = new LabelField();
			labelField.setConfiguration(createLabelConfiguration(name,primaryCss));
			labelField.configure();
			labelField.create();
			return labelField;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	private Configuration createLabelConfiguration(String text, String css) {
		Configuration configuration = new Configuration();
		try{
			configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, text);
			configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, css);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return configuration;

	}
}

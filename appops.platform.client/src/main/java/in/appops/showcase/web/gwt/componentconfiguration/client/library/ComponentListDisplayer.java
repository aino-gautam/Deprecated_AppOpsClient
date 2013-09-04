/**
 * 
 */
package in.appops.showcase.web.gwt.componentconfiguration.client.library;

import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.client.common.config.field.ListBoxField;
import in.appops.client.common.config.field.SelectedItem;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.ConfigEvent;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.ConfigEventHandler;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.entity.query.Query;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.EntityList;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author pallavi@ensarm.com
 *
 */
public class ComponentListDisplayer extends Composite implements FieldEventHandler,ConfigEventHandler{

	private VerticalPanel basePanel;
	private FlexTable compListPanel;
	private int componentRow = 0;
	private Entity libraryEntity;
	private Logger logger = Logger.getLogger("ComponentListDisplayer");
	
	/** CSS styles **/
	private final String HEADERLBL_CSS = "componentSectionHeaderLbl";
	private final String LISTPANEL = "componentFormPanel";
	
	public ComponentListDisplayer(){
		
	}

	public void createUi() {
		try {
			basePanel = new VerticalPanel();
						
			compListPanel = new FlexTable();
			LabelField compListLbl = new LabelField();
			Configuration compListLblConfig = getCompListLblConfig();

			compListLbl.setConfiguration(compListLblConfig);
			compListLbl.configure();
			compListLbl.create();
			
			LabelField nameLbl = new LabelField();
			Configuration headerLblConfig = getHeaderLblConfig("Name");
		
			nameLbl.setConfiguration(headerLblConfig);
			nameLbl.configure();
			nameLbl.create();
			
			LabelField descLbl = new LabelField();
			Configuration descLblConfig = getHeaderLblConfig("Description");
		
			descLbl.setConfiguration(descLblConfig);
			descLbl.configure();
			descLbl.create();
			
			compListPanel.setWidget(componentRow, 0, nameLbl);
			compListPanel.setWidget(componentRow, 1, descLbl);
			componentRow++;
			
			basePanel.add(compListLbl);
			basePanel.add(compListPanel);
			
			basePanel.setCellHorizontalAlignment(compListLbl, HorizontalPanel.ALIGN_CENTER);
			compListPanel.setStylePrimaryName(LISTPANEL);
			
			AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE, this);
			AppUtils.EVENT_BUS.addHandler(ConfigEvent.TYPE, this);
			
			initWidget(basePanel);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "ComponentListDisplayer :: createUi :: Exception", e);
		}
	}
	
	private Configuration getHeaderLblConfig(String displayTxt) {
		Configuration configuration = null;	
		try{
			configuration = new Configuration();
			configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, displayTxt);
			configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, HEADERLBL_CSS);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return configuration;
	}
	
	private Configuration getCompListLblConfig() {
		Configuration configuration = null;	
		try{
			configuration = new Configuration();
			configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, "Components");
			configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, HEADERLBL_CSS);
		}
		catch(Exception e){
			logger.log(Level.SEVERE, "ComponentListDisplayer :: getCompListLblConfig :: Exception", e);
		}
		return configuration;
	}
	
	@SuppressWarnings("unchecked")
	private void populateComponents() {
		try {
			DefaultExceptionHandler exceptionHandler = new DefaultExceptionHandler();
			DispatchAsync	dispatch = new StandardDispatchAsync(exceptionHandler);
			
			Query queryObj = new Query();
			queryObj.setQueryName("getComponentDefOfLibrary");
			
			HashMap<String, Object> queryParam = new HashMap<String, Object>();
			Long libId = ((Key<Long>)libraryEntity.getPropertyByName("id")).getKeyValue();
			queryParam.put("libraryId", libId);
			queryObj.setQueryParameterMap(queryParam);
						
			Map parameterMap = new HashMap();
			parameterMap.put("query", queryObj);
			
			StandardAction action = new StandardAction(EntityList.class, "appdefinition.AppDefinitionService.getComponentDefinitions", parameterMap);
			dispatch.execute(action, new AsyncCallback<Result<EntityList>>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.printStackTrace();
				}

				@Override
				public void onSuccess(Result<EntityList> result) {
					if(result!=null){
						EntityList componentlist   = result.getOperationResult();
						if(!componentlist.isEmpty()){
							populate(componentlist);
						}
					}
				}
			});
		} catch (Exception e) {
			logger.log(Level.SEVERE, "ComponentListDisplayer :: populateComponents :: Exception", e);
		}
	}
	
	private void populate(EntityList componentlist) {
		try {
			for(Entity comp:componentlist){
				ComponentPanel componentPanel = new ComponentPanel(comp);
				componentPanel.createUi();
				compListPanel.setWidget(componentRow, 0, componentPanel);
				componentRow++;
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "ComponentListDisplayer :: populate :: Exception", e);
		}
	}
	
	public void addComponent(Entity component) {
		try {
			ComponentPanel componentPanel = new ComponentPanel(component);
			componentPanel.createUi();
			compListPanel.setWidget(componentRow, 0, componentPanel);
			componentRow++;
		} catch (Exception e) {
			logger.log(Level.SEVERE, "ComponentListDisplayer :: addComponent :: Exception", e);
		}
	}
	
	private EntityList getDummyList(){
		Entity labelField = new Entity();
		labelField.setPropertyByName("name","LabelField");
		labelField.setPropertyByName("desc","   Display text");
		
		Entity dateLabelField = new Entity();
		dateLabelField.setPropertyByName("name","DateLabelField");
		dateLabelField.setPropertyByName("desc","   Display date in different formats");
		
		EntityList list = new EntityList();
		list.add(labelField);
		list.add(dateLabelField);
		return list;
	}

	public Entity getLibraryEntity() {
		return libraryEntity;
	}

	public void setLibraryEntity(Entity libraryEntity) {
		this.libraryEntity = libraryEntity;
	}
	
	@Override
	public void onFieldEvent(FieldEvent event) {
		try{
			int eventType = event.getEventType();
			Object eventSource = event.getEventSource();
			
			if(eventType == FieldEvent.VALUECHANGED){
				if(eventSource instanceof ListBoxField){
					ListBoxField listBoxField = (ListBoxField) eventSource;
					if(listBoxField.getBaseFieldId().equalsIgnoreCase(LibraryComponentManager.LIBRARYLISTBOX_ID)){
						SelectedItem selectedItem = (SelectedItem) event.getEventData();
						Entity libEntity = selectedItem.getAssociatedEntity();
						libraryEntity = libEntity;
						populateComponents();
					}
				}
			}
		}
		catch (Exception e) {
			logger.log(Level.SEVERE, "ComponentListDisplayer :: onFieldEvent :: Exception", e);
		}
	}

	@Override
	public void onConfigEvent(ConfigEvent event) {
		try{
			int eventType = event.getEventType();
			Object eventSource = event.getEventSource();
			
			if(eventType == ConfigEvent.ADDCOMPONENTTOLIST){
				Entity componentEntity = (Entity) event.getEventData();
				addComponent(componentEntity);
			}
		}
		catch (Exception e) {
			logger.log(Level.SEVERE, "ComponentListDisplayer :: onConfigEvent :: Exception", e);
		}
		
	}
}

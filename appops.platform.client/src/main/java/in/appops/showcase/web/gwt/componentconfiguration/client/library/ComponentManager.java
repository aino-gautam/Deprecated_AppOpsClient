/**
 * 
 */
package in.appops.showcase.web.gwt.componentconfiguration.client.library;

import in.appops.client.common.config.field.ListBoxField;
import in.appops.client.common.config.field.SelectedItem;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.entity.query.Query;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.util.EntityList;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author pallavi@ensarm.com
 *
 */
public class ComponentManager extends Composite implements FieldEventHandler{
	
	private VerticalPanel basePanel;
	private RegisterComponentForm regCompForm;
	private ComponentListDisplayer regCompList;
		
	private final String FORMLIST_HOLDER_CSS = "formListHolder";
	private final String BASEPANEL_CSS = "componentManager";
	
	private final String LIBRARYBOX_ID = "libraryBoxFieldId";
	
	public ComponentManager(){
		initialize();
	}

	public void createUi() {
		try{
			basePanel.setStylePrimaryName(BASEPANEL_CSS);
			
			HorizontalPanel formListHolder = new HorizontalPanel();
			regCompForm.createUi();
			
			formListHolder.add(regCompForm);
			formListHolder.add(regCompList);
			
			formListHolder.setCellWidth(regCompForm, "55%");
			formListHolder.setCellWidth(regCompList, "40%");
			
			formListHolder.setCellHorizontalAlignment(regCompForm, HorizontalPanel.ALIGN_CENTER);
			
			basePanel.add(formListHolder);
			formListHolder.setStylePrimaryName(FORMLIST_HOLDER_CSS);
			
			
		}
		catch (Exception e) {	
			e.printStackTrace();
		}
	}
	

	private void initialize() {
		try{
			basePanel = new VerticalPanel();
			regCompForm = new RegisterComponentForm();
			regCompList = new ComponentListDisplayer();
			AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE,this);
			initWidget(basePanel);
		}
		catch (Exception e) {	
			e.printStackTrace();
		}
	}
	
	private void intializeFormListHolder(Entity libEntity){
		try {
			regCompList.createUi(libEntity);
			regCompForm.setLibraryEntity(libEntity);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onFieldEvent(FieldEvent event) {
		
		int eventType = event.getEventType();
		Object eventSource = event.getEventSource();
		
		try {
			if(eventType == FieldEvent.VALUECHANGED){
				if(eventSource instanceof ListBoxField){
					
					ListBoxField listBoxField = (ListBoxField) eventSource;
					if(listBoxField.getBaseFieldId().equalsIgnoreCase(LIBRARYBOX_ID)){
						SelectedItem selectedItem = (SelectedItem) event.getEventData();
						Entity libEntity = selectedItem.getAssociatedEntity();
						if(libEntity!=null){
							intializeFormListHolder(libEntity);
						}
					}
				}
			}else if(eventType == FieldEvent.CLICKED){
				if(eventSource instanceof ComponentPanel){
					Entity componentEtity = (Entity) event.getEventData();
						if(componentEtity!=null){
							populateComponentConfiguration(componentEtity);
						}
					
				}
			}else if(eventType == FieldEvent.ADDCOMPONENT){
				Entity componentEtity = (Entity) event.getEventData();
				if(componentEtity!=null){
					regCompList.addComponent(componentEtity);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@SuppressWarnings("unchecked")
	private void populateComponentConfiguration(final Entity componentDefEntity) {
		try {
			DefaultExceptionHandler exceptionHandler = new DefaultExceptionHandler();
			DispatchAsync	dispatch = new StandardDispatchAsync(exceptionHandler);
			
			Query queryObj = new Query();
			queryObj.setQueryName("getConfigurationdefOfComponent");
			
			HashMap<String, Object> queryParam = new HashMap<String, Object>();
			Long libId = ((Key<Long>)componentDefEntity.getPropertyByName("id")).getKeyValue();
			queryParam.put("confdefId", libId);
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
						EntityList confDeflist   = result.getOperationResult();
						if(!confDeflist.isEmpty()){
							
						}
					}
				}
			});
		} catch (Exception e) {
		}
	}
}

/**
 * 
 */
package in.appops.showcase.web.gwt.componentconfiguration.client.library;

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
import java.util.logging.Level;
import java.util.logging.Logger;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author pallavi@ensarm.com
 *
 */
public class ComponentManager extends Composite implements FieldEventHandler{
	
	private VerticalPanel basePanel;
	private ComponentRegistrationForm compRegForm;
	private ComponentListDisplayer compListDisplayer;
	private Logger logger = Logger.getLogger("ComponentManager");

	/**CSS styles **/
	private final String BASEPANEL_CSS = "componentManager";
	
	public ComponentManager(){
		
	}

	public void createUi() {
		try{
			basePanel = new VerticalPanel();
			basePanel.setStylePrimaryName(BASEPANEL_CSS);
			
			compRegForm = new ComponentRegistrationForm();
			compRegForm.createUi();
			
			compListDisplayer = new ComponentListDisplayer();
			compListDisplayer.createUi();
			
			basePanel.add(compRegForm);
			basePanel.add(compListDisplayer);
			
			AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE,this);
			initWidget(basePanel);
							
		}
		catch (Exception e) {	
			logger.log(Level.SEVERE, "ComponentManager :: createUi :: Exception", e);
		}
	}

	@Override
	public void onFieldEvent(FieldEvent event) {
		
		int eventType = event.getEventType();
		Object eventSource = event.getEventSource();
		
		try {
			if(eventType == FieldEvent.CLICKED){
				if(eventSource instanceof ComponentPanel){
					Entity componentEtity = (Entity) event.getEventData();
						if(componentEtity!=null){
							populateConfigurationDef(componentEtity);
						}
					
				}
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "ComponentManager :: onFieldEvent :: Exception", e);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	private void populateConfigurationDef(final Entity componentDefEntity) {
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
			logger.log(Level.SEVERE, "ComponentManager :: populateComponentConfiguration :: Exception", e);
		}
	}

}

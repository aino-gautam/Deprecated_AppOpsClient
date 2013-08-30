/**
 * 
 */
package in.appops.showcase.web.gwt.registercomponent.client;

import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.core.entity.query.Query;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.util.EntityList;
import java.util.HashMap;
import java.util.Map;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author root
 *
 */
public class RegisterComponentLister extends Composite {

	private VerticalPanel basePanel;
	
	public RegisterComponentLister(){
		initialize();
	}

	void createUi() {
		populateComponents();
	}

	private void initialize() {
		basePanel = new VerticalPanel();
		initWidget(basePanel);
	}
	
	@SuppressWarnings("unchecked")
	private void populateComponents() {
		try {
			DefaultExceptionHandler exceptionHandler = new DefaultExceptionHandler();
			DispatchAsync	dispatch = new StandardDispatchAsync(exceptionHandler);
			
			Query queryObj = new Query();
			queryObj.setQueryName("");
						
			Map parameterMap = new HashMap();
			parameterMap.put("query", queryObj);
			
			StandardAction action = new StandardAction(EntityList.class, "", parameterMap);
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
		}
	}
	
	private void populate(EntityList componentlist) {
		
	}
}

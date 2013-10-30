/**
 * 
 */
package in.appops.client.common.config.form;

import in.appops.client.common.config.dsnip.HTMLSnippetModel;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Property;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.shared.Configuration;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author mahesh@ensarm.com
 * All the server call needed for form to be funtional will be done
 * by this model.
 */
@Deprecated
public class FormModel extends HTMLSnippetModel{
	
	/**
	 * Method to make a save or update entity call depending on the isUpdate flag
	 */
	@SuppressWarnings("unchecked")
	public void saveUpdateEntity(){

		HashMap<String, Object> parameterMap = getParamMapFromConfig();
		
		StandardAction action = new StandardAction(Entity.class, getOperationName(), parameterMap);
		dispatch.execute(action, new AsyncCallback<Result>() {

			public void onFailure(Throwable caught) {
				caught.printStackTrace();
				getReceiver().onEntityReceived(null);
			}

			public void onSuccess(Result result) {
				try {
					Entity entity = (Entity) result.getOperationResult();
					//entity.setPropertyByName(FormSnippetConstant.ISSAVEUPDATECALL, true);
					getReceiver().onEntityReceived(entity);
				} catch (Exception e) {
					e.printStackTrace();
					getReceiver().onEntityReceived(null);
				}
			}
		});
	}

	/**
	 * Will fetch the operation parameter from the configuration and then add them into 
	 * a hashmap that will be sent on the server side for updation.
	 * @return
	 */
	private HashMap<String, Object> getParamMapFromConfig() {
		HashMap<String, Object> queryParamMap = null;
		try {
			
			Configuration operationParam = getOperationParameters();
			if(operationParam != null) {
				queryParamMap = new HashMap<String, Object>();
		
				Set<Entry<String, Property<? extends Serializable>>> confSet = operationParam.getValue().entrySet();
		
				for(Entry<String, Property<? extends Serializable>> entry : confSet) {
					String paramName = entry.getKey();
					Serializable value = entry.getValue().getValue();
					if(value != null) {
						queryParamMap.put(paramName, value);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return queryParamMap;
	}
}

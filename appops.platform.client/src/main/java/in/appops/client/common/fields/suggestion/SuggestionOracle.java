package in.appops.client.common.fields.suggestion;

import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.query.Query;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.util.EntityList;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SuggestOracle;

public class SuggestionOracle extends SuggestOracle {

	private List<AppopsSuggestion> store;
	private String query;
	private String operationName;
	private final DefaultExceptionHandler exceptionHandler = new DefaultExceptionHandler();
	private final DispatchAsync	dispatch = new StandardDispatchAsync(exceptionHandler);
	
	public void setQuery(String query) {
		this.query = query;
	}
	
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void requestSuggestions(final Request request, final Callback callback) {
		final String search = request.getQuery();
		final SuggestOracle.Response response = new Response();
		List<AppopsSuggestion> suggestionList = new LinkedList<AppopsSuggestion>();

		if(store != null && !store.isEmpty() && !search.equals(" ")){
			for(AppopsSuggestion suggestion : store){
				if(suggestion.getDisplayString().toLowerCase().contains(search.trim().toLowerCase())){
					suggestionList.add(suggestion);
				}
			}
		}
		
		if(!suggestionList.isEmpty()){
			response.setSuggestions(suggestionList);
			callback.onSuggestionsReady(request, response);
		} else{
			
			Query queryObj = new Query();
			
			HashMap<String,Object> map = new HashMap<String,Object>();
			/*map.put("qName", query);*/
			//queryObj.setQueryName(query);
			queryObj.setName(query);
			queryObj.setQueryParameterMap(map);
			Map parameterMap = new HashMap();
			parameterMap.put("query", queryObj);
			
			StandardAction action = new StandardAction(Entity.class, operationName, parameterMap);
			dispatch.execute(action, new AsyncCallback<Result>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.printStackTrace();
				}

				@Override
				public void onSuccess(Result result) {
					store = new LinkedList<AppopsSuggestion>();
					EntityList entityList = (EntityList) result.getOperationResult();
					if(entityList != null && !entityList.isEmpty()) {
						for (Entity entity : entityList) {
							AppopsSuggestion appopsSuggestion = new AppopsSuggestion(entity);
							appopsSuggestion.initialize();
							store.add(appopsSuggestion);
						}
					}
					
					if(search.equals(" ")){
						response.setSuggestions(store);
					} else{
						List<AppopsSuggestion> suggestionList = new LinkedList<AppopsSuggestion>();
						for(AppopsSuggestion suggestion : store){
							if(suggestion.getDisplayString().toLowerCase().contains(search.trim().toLowerCase())){
								suggestionList.add(suggestion);
							}
						}
						response.setSuggestions(suggestionList);
					}
					callback.onSuggestionsReady(request, response);
				}
			});
		}
	}

}

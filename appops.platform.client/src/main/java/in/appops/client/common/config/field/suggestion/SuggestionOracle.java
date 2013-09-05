package in.appops.client.common.config.field.suggestion;

import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.query.Query;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.util.EntityList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SuggestOracle;

public class SuggestionOracle extends SuggestOracle {

	private List<AppopsSuggestion> store ;
	private String queryName;
	private String operationName;
	private final DefaultExceptionHandler exceptionHandler = new DefaultExceptionHandler();
	private final DispatchAsync	dispatch = new StandardDispatchAsync(exceptionHandler);
	private int maxResult;
	private String entPropToDisplay;
	private HashMap<String, Object> restrictionMap;
	private Boolean isSearchQuery = false;
	private Boolean isStaticSuggestionBox = false;
	private ArrayList<String> itemsToDisplay;
	private Logger logger = Logger.getLogger(getClass().getName());

	public SuggestionOracle() {
		
	}
	
	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}
	
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void requestSuggestions(final Request request, final Callback callback) {
		try {
			logger.log(Level.INFO, "[SuggestionOracle] ::In requestSuggestions method ");
			final String search = request.getQuery();
			final SuggestOracle.Response response = new Response();
			List<AppopsSuggestion> suggestionList = new LinkedList<AppopsSuggestion>();

			if(store != null && !store.isEmpty() && !search.equals(" ")){
				for(AppopsSuggestion suggestion : store){
					if(suggestion.getDisplayString().toLowerCase().startsWith(search.trim().toLowerCase())){
						suggestionList.add(suggestion);
					}
				}
			}
			
			if(!suggestionList.isEmpty()){
				response.setSuggestions(suggestionList);
				callback.onSuggestionsReady(request, response);
			} else if(isStaticSuggestionBox){
				store = new LinkedList<AppopsSuggestion>();
				for (String name : getItemsToDisplay()) {
					AppopsSuggestion appopsSuggestion = new AppopsSuggestion();
					appopsSuggestion.setDisplay(name);
					store.add(appopsSuggestion);
				}
			}else{
				
				Query queryObj = new Query();
				queryObj.setQueryName(queryName);
				queryObj.setListSize(maxResult);
				HashMap map = new HashMap();

				if(isSearchQuery){
					if(restrictionMap != null){
						for (String key : restrictionMap.keySet())
							map.put(key, restrictionMap.get(key));
					}
					//map.put("searchChar", search+"*");
					map.put("searchChar", "%" + search + "%");
				}else if(restrictionMap != null){
					for (String key : restrictionMap.keySet())
						map.put(key, restrictionMap.get(key));
				}
				
				if(!map.isEmpty())
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
								appopsSuggestion.setPropertToDisplay(entPropToDisplay);
								store.add(appopsSuggestion);
							}
						}
						
						if(search.equals(" ")){
							response.setSuggestions(store);
						} else{
							List<AppopsSuggestion> suggestionList = new LinkedList<AppopsSuggestion>();
							for(AppopsSuggestion suggestion : store){
								if(suggestion.getDisplayString().toLowerCase().startsWith(search.trim().toLowerCase())){
									suggestionList.add(suggestion);
								}
							}
							response.setSuggestions(suggestionList);
						}
						callback.onSuggestionsReady(request, response);
					}
				});
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[SuggestionOracle] ::Exception in requestSuggestions method :"+e);
		}
	}
	
	
	public void setMaxResult(int max) {
		this.maxResult = max;
	}

	public void setEntityPropToDisplay(String prop) {
		this.entPropToDisplay = prop;
	}

	public void setRestriction(HashMap<String, Object> map) {
		this.restrictionMap = map;
	}

	public void IsSearchQuery(Boolean val) {
		this.isSearchQuery = val;
	}
	
	public void IsStaticSuggestionBox(Boolean val) {
		this.isStaticSuggestionBox = val;
	}

	public ArrayList<String> getItemsToDisplay() {
		return itemsToDisplay;
	}

	public void setItemsToDisplay(ArrayList<String> itemsToDisplay) {
		this.itemsToDisplay = itemsToDisplay;
	}
}

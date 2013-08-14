package in.appops.client.common.config.model;

import in.appops.client.common.config.component.tree.TreeComponentPresenter.TreeComponentConstant;
import in.appops.client.common.core.EntityListReceiver;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.core.entity.Property;
import in.appops.platform.core.entity.query.Query;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.EntityList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class TreeModel extends AppopsBaseModel {
	
	private HashMap<String,Configuration> depthConfigList;
	private EntityListReceiver receiver;
	
	@Override
	public void configure() {
		super.configure();
		setDepthList(getDepthConfigList());
	}
	
	@SuppressWarnings("unchecked")
	private HashMap<String,Configuration> getDepthConfigList() {
		HashMap<String,Configuration> depthConfigList = null;
		if(getConfigurationValue("depthConfigList") != null) {
			depthConfigList = (HashMap<String,Configuration>)getConfigurationValue("depthConfigList");
		}
		return depthConfigList;
	}

	public void setDepthList(HashMap<String,Configuration> depthList) {
		this.depthConfigList = depthList;
	}

	public HashMap<String, Configuration> getDepthList() {
		return depthConfigList;
	}

	/**
	 * Would Fetch the tree items for the requested depth
	 * @param depth
	 */
	public void getItems(int depth) {
		Configuration depthConfig = (Configuration) configuration.getProperty(Integer.toString(depth));
		
		String depthQuery = depthConfig.getPropertyByName(TreeComponentConstant.TM_DEPTH_QUERY);
		Configuration param = depthConfig.getPropertyByName("queryParam");
		
		HashMap<String, Object> queryParamMap = new HashMap<String, Object>();
		
		Set<Entry<String, Property<? extends Serializable>>> confSet = param.getValue().entrySet();
		
		for(Entry<String, Property<? extends Serializable>> entry : confSet) {
			String paramName = entry.getKey();
			Serializable value = entry.getValue().getValue();
				queryParamMap.put(paramName, value);
		}
		
		if(!queryParamMap.isEmpty()) {
			Query query = new Query();
			query.setQueryName(depthQuery);
			query.setQueryParameterMap(queryParamMap);
			
			executeQuery(query);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void executeQuery(Query query) {
		Map<String, Serializable> queryParam = new HashMap<String, Serializable>();
		queryParam.put("query", query);
		
		StandardAction action = new StandardAction(EntityList.class, getTreeOperationName(), queryParam);
		dispatch.execute(action, new AsyncCallback<Result<EntityList>>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(Result<EntityList> result) {
				EntityList entList = (EntityList) result.getOperationResult();
				//TODO
				if(entList != null) {
					receiver.onEntityListReceived(entList);
				}
			
			}
		});
	}
	
	public String getTreeOperationName() {
		String operation = null;
		if(getConfigurationValue(TreeComponentConstant.TM_OPERATION) != null) {
			operation = getConfigurationValue(TreeComponentConstant.TM_OPERATION).toString();
		}
		return operation;
	}

	public void setReceiver(EntityListReceiver receiver) {
		this.receiver = receiver;
	}
	
}

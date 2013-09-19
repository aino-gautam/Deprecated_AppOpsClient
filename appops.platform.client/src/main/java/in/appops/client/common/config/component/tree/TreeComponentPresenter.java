package in.appops.client.common.config.component.tree;

import in.appops.client.common.config.component.base.BaseComponentPresenter;
import in.appops.client.common.config.dsnip.ApplicationContext;
import in.appops.client.common.config.dsnip.Container.ContainerConstant;
import in.appops.client.common.config.dsnip.EventConstant;
import in.appops.client.common.config.dsnip.HTMLSnippet;
import in.appops.client.common.config.model.TreeModel;
import in.appops.client.common.core.EntityListReceiver;
import in.appops.client.common.util.JsonToEntityConverter;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.entity.Property;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.EntityList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.user.client.ui.TreeItem;

public class TreeComponentPresenter extends BaseComponentPresenter implements OpenHandler<TreeItem>, CloseHandler<TreeItem>, EntityListReceiver {
	TreeItem treeItem = null;

	public TreeComponentPresenter() {

	}
	
	@Override
	public void initialize() {
		model = new TreeModel();
		((TreeModel)model).setReceiver(this);
		view = new TreeComponentView();		
	}
	
	@Override
	public void configure() {
		super.configure();
		((TreeComponentView)view).getRoot().addOpenHandler(this);
	}
	
	@Override
	public void load() {
		if(getCurrentRequestedDepth() != -1) {
			((TreeModel)model).getItems(getCurrentRequestedDepth());
		}
	}

	private int getCurrentRequestedDepth() {
		int operation = -1;
		if(getConfigurationValue("currentRequestedDepth") != null) {
			operation = Integer.parseInt(getConfigurationValue("currentRequestedDepth").toString());
		}
		return operation;
	}

	@SuppressWarnings("unchecked")
	protected HashMap<String, Configuration> getInterestedEvents() {
		HashMap<String, Configuration> interestedEvents = new HashMap<String, Configuration>();
		if(getConfigurationValue(ContainerConstant.CT_INTRSDEVNTS) != null) {
			interestedEvents = (HashMap<String, Configuration>) getConfigurationValue(ContainerConstant.CT_INTRSDEVNTS);
		}
		return interestedEvents;
	}
	
	
	public boolean isTypeInteresting(String eventName) {
		HashMap<String, Configuration> interestedEvents = getInterestedEvents();
		Set<String> eventSet = interestedEvents.keySet();
		
		if(!eventSet.isEmpty()) {
			if(eventSet.contains(eventName)) {
				return true;
			}
		}
		return false;
	}
	
	protected Configuration getEventConfiguration(String event) {
		HashMap<String, Configuration> interestedEvents = getInterestedEvents();
		
		if(!interestedEvents.isEmpty() && interestedEvents.containsKey(event)) {
			return interestedEvents.get(event);
		}
		return null;
	}

	@Override
	public void onClose(CloseEvent<TreeItem> event) {
		
		
	}

	@Override
	public void onOpen(OpenEvent<TreeItem> event) {
		try {
			TreeItem treeItem = event.getTarget();
			TreeComponentView treeView = (TreeComponentView)view;
			int depth = Integer.parseInt(treeItem.getTitle());

			treeView.getRoot().setSelectedItem(treeItem);

			HTMLSnippet treeSnippet = (HTMLSnippet) treeItem.getWidget();
			
			configuration.setPropertyByName("currentRequestedDepth", depth + 1);
			
			String parentProp = "model."+ (depth + 1) +".queryParam.parentId";

			String blogProp = "model."+ (depth + 1) +".queryParam.blogId";
	
			Configuration updateConf = new Configuration();
			updateConf.setPropertyByName(parentProp, "evt.id");
			updateConf.setPropertyByName(blogProp, "ac.blog.id");
			
			Set<Entry<String, Property<? extends Serializable>>> confSet = updateConf.getValue().entrySet();
			
			for(Entry<String, Property<? extends Serializable>> entry : confSet) {
				String key = entry.getKey();
				
				Serializable propvalue = entry.getValue().getValue();
				
				if(propvalue != null) {
					String propStrVal = entry.getValue().getValue().toString();
					Serializable value = null;
					if(propStrVal.startsWith("evt")) {
						Entity entity = treeSnippet.getEntity();
						String entityProp = propStrVal.substring(propStrVal.indexOf(".") + 1);
						
						value = entity.getGraphPropertyValue(entityProp, entity);
					} else if(propStrVal.startsWith("ac")) {
						String appContextProp = propStrVal.substring(propStrVal.indexOf(".") + 1);
						value = ApplicationContext.getInstance().getGraphPropertyValue(appContextProp, null);
					}	
					if(value instanceof Key) {
						Key keyVal = (Key)value;
						value = keyVal.getKeyValue();
					}
					configuration.setGraphPropertyValue(key, value, null);
				}

				
			}
			
/*			for(Configuration paramConfig : paramList) {
				Serializable value = null;
				
				String paramType = paramConfig.getPropertyByName("paramType");
				
				if(paramType.equals("entityParam")) {
					Entity entity = treeSnippet.getEntity();
					String entityProp = paramConfig.getPropertyByName("entityProp");
					
					value = entity.getGraphPropertyValue(entityProp, entity);
				} else {
					String entityProp = paramConfig.getPropertyByName("entityProp");
					value = ApplicationContext.getInstance().getGraphPropertyValue(entityProp, null);
				}

				if(value instanceof Key) {
					Key key = (Key)value;
					value = key.getKeyValue();
				}
				paramConfig.setPropertyByName("value", value);
			}
			*/
			load();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void noMoreData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEntityListReceived(EntityList entityList) {
		if(entityList != null && !entityList.isEmpty()) {
			TreeComponentView listView = (TreeComponentView)view;
			listView.setEntityList(entityList);
			listView.populate();
		}
	}

	@Override
	public void onEntityListUpdated() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCurrentView(Entity entity) {
		// TODO Auto-generated method stub
		
	}
	
	public interface TreeComponentConstant extends BaseComponentConstant {
		String TR_SNIPPETTYPE = "treeSnippetType";
		
		String TR_INSTANCETYPE = "treeSnippetInstanceType";
		
		/**
		 * A list which consist of query information for each depth
		 */
		String TM_DEPTHCONFIGLIST = "depthConfigList";

		/**
		 * name of query for each depth
		 */
		String TM_DEPTH_QUERY = "treeDepthQuery";
		
		/**
		 * Parameters information for each depth
		 */
		String TM_DEPTH_QUERY_PARAM = "treeDepthParam";

		/**
		 * Name of operation name that would executed the query to fetch entity list 
		 */
		String TM_OPERATION = "treeOperation";
	}



}
package in.appops.client.common.config.component.list;

import in.appops.client.common.config.component.base.BaseComponentPresenter;
import in.appops.client.common.config.dsnip.Container.ContainerConstant;
import in.appops.client.common.config.dsnip.EventConstant;
import in.appops.client.common.config.model.EntityListModel;
import in.appops.client.common.core.EntityListReceiver;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.EntityList;

import java.util.HashMap;
import java.util.Set;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;

public class ListComponentPresenter extends BaseComponentPresenter {
	
	@Override
	public void init() {
		super.init();

		model = new EntityListModel();
		view = new ListComponentView();
		
		model.setConfiguration(getModelConfiguration());
		model.configure();
		view.setConfiguration(getViewConfiguration());
		
		view.setModel(model);
		view.configure();
		view.create();
	}
	
	@Override
	public void load() {
		super.load();
		if(isTypeInteresting("onLoad")) {
			Configuration eventConf = getEventConfiguration("onLoad");
			processEvent(eventConf, null);
		}
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
/*		 String evntToken = event.getValue();
		 String[] tokenSplitter = evntToken.split("&&");
		 String eventName = tokenSplitter[0].substring(tokenSplitter[0].indexOf("=") + 1);
//		 String eventSource = tokenSplitter[1].substring(tokenSplitter[1].indexOf("=") + 1);
		 
		 String snippetType = tokenSplitter[2].substring(tokenSplitter[2].indexOf("=") + 1);
		 String instanceType = tokenSplitter[3].substring(tokenSplitter[3].indexOf("=") + 1);
		 
		 ListComponentView listView = (ListComponentView)view;
		 listView.setSnippetType(snippetType);
		 listView.setInstanceType(instanceType);
		 
		 if(view.isTypeInteresting(eventName)) {
	        if (eventName.equals("viewArticle")) {
	        	String opName = tokenSplitter[1].substring(tokenSplitter[1].indexOf("=") + 1);
	        	String entityIdVal = tokenSplitter[4].substring(tokenSplitter[4].indexOf("=") + 1);
	        	Long entId = Long.parseLong(entityIdVal);
	        	//model.getEntity(opName, entId, this);	
	         } else if(eventName.equals("editArticle")) {
	        	 String opName = tokenSplitter[1].substring(tokenSplitter[1].indexOf("=") + 1);
		        	String entityIdVal = tokenSplitter[4].substring(tokenSplitter[4].indexOf("=") + 1);
		        	Long entId = Long.parseLong(entityIdVal);
		        	//model.getEntity(opName, entId, this);	
	         }
		 }*/
		
		String evntToken = event.getValue();
		String[] tokenSplitter = evntToken.split("&&");
		String eventName = tokenSplitter[0].substring(tokenSplitter[0].indexOf("=") + 1);
		// Should be some map of data.
		String entityIdVal = null;
		if(tokenSplitter.length > 1) { 
			entityIdVal = tokenSplitter[1].substring(tokenSplitter[1].indexOf("=") + 1);
		}
		
		if(isTypeInteresting(eventName)) {
			Configuration eventConf = getEventConfiguration(eventName);
			processEvent(eventConf, entityIdVal);
		}
 	}
	
	private void processEvent(Configuration conf, Object eventData) {
		Integer eventType = Integer.parseInt(conf.getPropertyByName(EventConstant.EVNT_TYPE).toString());
		if(eventType == EventConstant.EVNT_TRANSWGT) {

		} else if(eventType == EventConstant.EVNT_EXECOP) {
			String operation = conf.getPropertyByName(EventConstant.EVNT_OPERATION).toString();
			
			EntityListModel listModel = (EntityListModel)model;
			listModel.executeOperation(operation, null, new EntityListReceiver() {
				
				@Override
				public void updateCurrentView(Entity entity) {	}
				
				@Override
				public void onEntityListUpdated() {}
				
				@Override
				public void onEntityListReceived(EntityList entityList) {
					if(entityList != null && !entityList.isEmpty()) {
						ListComponentView listView = (ListComponentView)view;
						listView.initializeListPanel(entityList);
					}
				}
				
				@Override
				public void noMoreData() {	}
			} );
			
		}
	}
	
	private int getEventType() {
		int eventType = EventConstant.EVNT_TRANSWGT;
		if(getConfigurationValue(EventConstant.EVNT_TYPE) != null) {
			eventType = Integer.parseInt(getConfigurationValue(EventConstant.EVNT_TYPE).toString());
		}
		return eventType;
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
	
	private Configuration getEventConfiguration(String event) {
		HashMap<String, Configuration> interestedEvents = getInterestedEvents();
		
		if(!interestedEvents.isEmpty() && interestedEvents.containsKey(event)) {
			return interestedEvents.get(event);
		}
		return null;
	}
	


}

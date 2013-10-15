package in.appops.client.common.config.component.list;

import in.appops.client.common.config.component.base.BaseComponent.BaseComponentConstant;
import in.appops.client.common.config.component.base.BaseComponentPresenter;
import in.appops.client.common.config.model.ConfigurationListModel;
import in.appops.client.common.config.model.EntityListModel;
import in.appops.client.common.core.EntityListReceiver;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.util.EntityList;

public class ListComponentPresenter extends BaseComponentPresenter implements EntityListReceiver {
	private EntityListModel entityListModel;
	
	@Override
	public void initialize() {
		model = new ConfigurationListModel();
		entityListModel = (EntityListModel) model;
		entityListModel.setReceiver(this);
		view = new ListComponentView();
	}

	/*@Override
	public void load() {
		entityListModel.fetchEntityList();
	}*/
	
/*	
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
	}*/
	
	
	

	@Override
	public void noMoreData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEntityListReceived(EntityList entityList) {
		if(entityList != null && !entityList.isEmpty()) {
			ListComponentView listView = (ListComponentView)view;
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
	
	
	public interface ListComponentConstant extends BaseComponentConstant {
		String LC_LISTCLS = "listCss";
		String LC_SNIPPETTYPE = "snippetType";
		String LC_INSTANCETYPE = "snippetInstance";
	}

}

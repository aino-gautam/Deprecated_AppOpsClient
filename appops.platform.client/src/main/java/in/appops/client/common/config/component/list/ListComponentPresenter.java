package in.appops.client.common.config.component.list;

import in.appops.client.common.config.component.base.BaseComponentPresenter;
import in.appops.client.common.config.dsnip.Container.ContainerConstant;
import in.appops.client.common.config.dsnip.EventConstant;
import in.appops.client.common.config.model.EntityListModel;
import in.appops.client.common.core.EntityListReceiver;
import in.appops.client.common.util.JsonToEntityConverter;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.EntityList;

import java.util.HashMap;
import java.util.Set;

import com.google.gwt.event.logical.shared.ValueChangeEvent;

public class ListComponentPresenter extends BaseComponentPresenter implements EntityListReceiver {
	
	public ListComponentPresenter () {
		model = new EntityListModel();
		((EntityListModel)model).setReceiver(this);
		view = new ListComponentView();
	}
	
	@Override
	public void configure() {
		super.configure();
		
		model.setConfiguration(getModelConfiguration());
		model.configure();
		view.setConfiguration(getViewConfiguration());
		
		view.configure();
		view.create();
	}
	
	
	@Override
	public void init() {
		super.init();

		((EntityListModel)model).fetchEntityList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		String appEventJson = event.getValue();
		
		Entity appEvent = new JsonToEntityConverter().convertjsonStringToEntity(appEventJson);
		
		String eventName = appEvent.getPropertyByName(EventConstant.EVNT_NAME);
		Object eventData = appEvent.getPropertyByName(EventConstant.EVNT_DATA);

		if(isTypeInteresting(eventName)) {
			Configuration eventConf = getEventConfiguration(eventName);
			processEvent(eventConf, eventData);
		}
 	}
	
	@SuppressWarnings("unchecked")
	private void processEvent(Configuration conf, Object eventData) {
//		try {
//			Configuration updateConf =  conf.getPropertyByName("updateConfiguration");
//			Set<String> confSet = updateConf.getValue().keySet();
//			
//			for(String confToUpdate : confSet) {
//				Object confValue = updateConf.getPropertyByName(confToUpdate);
//				configuration.setPropertyByName(confToUpdate, (Serializable)confValue);
//			}
//	
//			ArrayList<Configuration> paramList = model.getQueryParamList();
//			
//			for(Configuration paramConfig : paramList) {
//				Serializable value = null; paramConfig.getPropertyByName("value");
//					String paramType = paramConfig.getPropertyByName("paramType");
//					if(paramType == null) {
//						value = paramConfig.getPropertyByName("value");
//					} else {
//						if(paramType.equals("entityParam")) {
//							Entity entity = (Entity)eventData;
//							String entityProp = paramConfig.getPropertyByName("entityProp");
//							
//							value = entity.getPropertyByName(entityProp);
//						} else {
//								String entityProp = paramConfig.getPropertyByName("entityProp");
//								
//								value = ApplicationContext.getInstance().getGraphPropertyValue(entityProp, null);
//								
//								if(value instanceof Key) {
//									Key key = (Key)value;
//									value = key.getKeyValue();
//								}
//						}
//					}
//					paramConfig.setPropertyByName("value", value);
//			}
//			
//			init();
//		} catch(Exception e) {
//			
//		}
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
	public void updateConfiguration(String confProp) {
		/*super.updateConfiguration(confProp);
		
		String[] splitConf = confProp.split("\\."); 
		if(splitConf != null && splitConf.length > 0) {
			
			if(splitConf[0].equals("model")) {
				
			} else if(splitConf[0].equals("view")) {
				
			} else {
				
			}
		}*/
	}

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
		String LC_SNIPPETTYPE = "listSnippetType";
		String LC_INSTANCETYPE = "listSnippetInstanceType";
	}
}

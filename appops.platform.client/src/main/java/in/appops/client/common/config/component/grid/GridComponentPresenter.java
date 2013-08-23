package in.appops.client.common.config.component.grid;

import in.appops.client.common.config.component.base.BaseComponentPresenter;
import in.appops.client.common.config.dsnip.Container.ContainerConstant;
import in.appops.client.common.config.dsnip.EventConstant;
import in.appops.client.common.config.model.EntityListModel;
import in.appops.client.common.core.EntityListReceiver;
import in.appops.client.common.util.BlobDownloader;
import in.appops.client.common.util.JsonToEntityConverter;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.entity.Property;
import in.appops.platform.core.entity.type.MetaType;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.EntityList;
import java.util.HashMap;
import java.util.Set;

import com.google.gwt.event.logical.shared.ValueChangeEvent;

public class GridComponentPresenter extends BaseComponentPresenter implements EntityListReceiver {
	
	public GridComponentPresenter () {
		model = new EntityListModel();
		((EntityListModel)model).setReceiver(this);
		view = new GridComponentView();
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
		//((EntityListModel)model).fetchEntityList();
		EntityList entityList = getProductEntityList();
		if(entityList != null && !entityList.isEmpty()) {
			GridComponentView listView = (GridComponentView)view;
			listView.setEntityList(entityList);
			listView.populate();
			
		}
		
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
	}

	@Override
	public void noMoreData() {
				
	}

	@Override
	public void onEntityListReceived(EntityList entityList) {
		if(entityList != null && !entityList.isEmpty()) {
			GridComponentView listView = (GridComponentView)view;
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
	
	
	public interface GridComponentConstant extends BaseComponentConstant {
		String GC_SNIPPETTYPE = "gridSnippetType";
		String GC_INSTANCETYPE = "gridSnippetInstanceType";
		String GC_NO_OF_COLS = "noOfColumns";
		String GC_ALIGN = "alignment";
		String GC_PANEL_CSS = "gridPanelCss";
		String SCROLL_PANEL_CSS = "scrollPanelCss";
		String SCROLL_PANEL_WIDTH = "scrollPanelWidth";
		String SCROLL_PANEL_HEIGHT = "scrollPanelHeight";
	}
	
	private EntityList getProductEntityList(){
		
		BlobDownloader blobDownloader = new BlobDownloader();		
		EntityList entityList = new EntityList();
		for (int i = 0; i < 20; i++) {
			Entity ent = new Entity();
			ent.setType(new MetaType("product"));
			
			Key<Long> entkey = new Key<Long>((long) i);
			Property<Key<Long>> entKeyProp = new Property<Key<Long>>(entkey);
			ent.setProperty("id", entKeyProp);
			
			ent.setPropertyByName("name", "Ring"+i);
			ent.setPropertyByName("blobId", blobDownloader.getThumbNailDownloadURL("i_irqSN52SzHwHksn9NQFKxA6zR2xKgDVgQLkP5WGJXH4%3D"));
			
			ent.setPropertyByName("price", "$25");
			ent.setPropertyByName("likes",25);
			entityList.add(ent);
		}
		
		return entityList;
	}
}


package in.appops.client.common.config.component.grid;

import in.appops.client.common.config.component.base.BaseComponent.BaseComponentConstant;
import in.appops.client.common.config.component.base.BaseComponentPresenter;
import in.appops.client.common.config.model.ConfigurationListModel;
import in.appops.client.common.config.model.EntityListModel;
import in.appops.client.common.core.EntityListReceiver;
import in.appops.client.common.util.BlobDownloader;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.entity.Property;
import in.appops.platform.core.entity.type.MetaType;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.EntityList;

import java.util.HashMap;
import java.util.Set;

public class GridComponentPresenter extends BaseComponentPresenter implements EntityListReceiver {
	
	@Override
	public void configure() {
		super.configure();
		
		model.setConfiguration(model.getModelConfiguration());
		model.configure();
		//view.setConfiguration(model.getViewConfiguration());
		
		view.configure();
		view.create();
	}
	
	@Override
	public void initialize() {
		model = new ConfigurationListModel();
		((EntityListModel)model).setReceiver(this);
		view = new GridComponentView();		
	}
	
	
	/*@Override
	public void load() {
		//((EntityListModel)model).fetchEntityList();
		EntityList entityList = getProductEntityList();
		if(entityList != null && !entityList.isEmpty()) {
			GridComponentView listView = (GridComponentView)view;
			listView.setEntityList(entityList);
			listView.populate();
		}
	}*/
	
	@SuppressWarnings("unchecked")
	protected HashMap<String, Configuration> getInterestedEvents() {
		HashMap<String, Configuration> interestedEvents = new HashMap<String, Configuration>();
		/*if(getConfigurationValue(ContainerConstant.CT_INTRSDEVNTS) != null) {
			interestedEvents = (HashMap<String, Configuration>) getConfigurationValue(ContainerConstant.CT_INTRSDEVNTS);
		}*/
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
	
	
	public interface GridComponentConstant extends BaseComponentConstant{
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
		for (int i = 0; i < 21; i++) {
			Entity ent = new Entity();
			ent.setType(new MetaType("product"));
			
			Key<Long> entkey = new Key<Long>((long) i);
			Property<Key<Long>> entKeyProp = new Property<Key<Long>>(entkey);
			ent.setProperty("id", entKeyProp);
			
			ent.setPropertyByName("name", "Ring"+i);
			ent.setPropertyByName("blobId", blobDownloader.getThumbNailDownloadURL("i_irqSN52SzHwHksn9NQFKxA6zR2xKgDVgQLkP5WGJXH4%3D"));
			if(i%2==0){
				ent.setPropertyByName("isliked", true);
				ent.setPropertyByName("likes",2);
			}else{
				ent.setPropertyByName("isliked", false);
				ent.setPropertyByName("likes",0);
			}
			ent.setPropertyByName("price", "$25");
			
			entityList.add(ent);
		}
		
		return entityList;
	}
}

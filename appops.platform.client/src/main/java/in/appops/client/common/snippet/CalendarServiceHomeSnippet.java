package in.appops.client.common.snippet;

import in.appops.client.common.core.EntityListModel;
import in.appops.client.common.core.EntityListReceiver;
import in.appops.client.common.snippet.ListSnippet;
import in.appops.client.common.snippet.Snippet;
import in.appops.platform.core.constants.propertyconstants.UserConstants;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.entity.Property;
import in.appops.platform.core.entity.query.Query;
import in.appops.platform.core.util.EntityList;

import java.util.HashMap;

import com.google.gwt.user.client.ui.VerticalPanel;

public class CalendarServiceHomeSnippet extends Snippet implements EntityListReceiver {
	
	private VerticalPanel basePanel = new VerticalPanel();
	private ListSnippet listSnippet ;
	
		
	public CalendarServiceHomeSnippet() {
		initWidget(basePanel);
	}
	
	@Override
	public void initialize(){
		//Property<Key<Long>> userIdProp = (Property<Key<Long>>) entity.getProperty(UserConstants.ID);
		//initializeListForUser(userIdProp.getValue().getKeyValue());
		
		initializeListForUser(1L);
	}
	
	
	public void initializeListForUser(long userId) {
		
		
		listSnippet= new ListSnippet();
		basePanel.add(listSnippet);
		
		Query query = new Query();
		query.setQueryName("getAllRemindersOfUser");
		query.setListSize(8);
			
		HashMap<String, Object> queryParam = new HashMap<String, Object>();
		queryParam.put("userId", userId);
		query.setQueryParameterMap(queryParam);
		
		//ReminderListModel reminderListModel = new ReminderListModel(query, "calendar.CalendarService.getEntityList", 0);
		
		EntityListModel reminderListModel = new EntityListModel();
		
		reminderListModel.setOperationNameToBind("calendar.CalendarService.getEntityList");
		reminderListModel.setQueryToBind(query);
		reminderListModel.setNoOfEntities(0);
		
		reminderListModel.getEntityList(0, this);
		
	}


	@Override
	public void noMoreData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEntityListReceived(EntityList entityList) {
		initializeListComponentWidget(entityList);
		
	}

	private void initializeListComponentWidget(EntityList entityList) {
		
			listSnippet.setEntityList(entityList);
			listSnippet.initialize();
		
		
	}

	@Override
	public void onEntityListUpdated() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCurrentView(Entity entity) {
		// TODO Auto-generated method stub
		
	}


	

}

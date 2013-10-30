package in.appops.client.common.snippet;

import in.appops.client.common.core.EntityListModel;
import in.appops.client.common.util.AppEnviornment;
import in.appops.platform.core.constants.propertyconstants.UserConstants;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.entity.Property;
import in.appops.platform.core.entity.query.Query;
import in.appops.platform.core.operation.ActionContext;
import in.appops.platform.core.shared.Configuration;

import java.util.Date;
import java.util.HashMap;

import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class EventListSnippet extends VerticalPanel implements Snippet{

	private ListSnippet listSnippet ;
	private Entity entity;
	private Configuration configuration;
	private Entity userEntity;
	
	
	public static String DEFAULT_LIST="defaultList";
	public static String DATEBASED_LIST="dateBasedList";
	public static String DATEBASED_LIST_VALUE="dateBasedListValue";
	
	@Override
	public Entity getEntity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setEntity(Entity entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setType(String type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initialize() {
		userEntity=AppEnviornment.getCurrentUser();
	    Property<Key<Long>> userIdProp = (Property<Key<Long>>) userEntity.getProperty(UserConstants.ID);
	    Key<Long> userIdKey = userIdProp.getValue();
	    Long userId=userIdKey.getKeyValue();
		
	    if(getConfiguration().getPropertyByName(DEFAULT_LIST)!=null){
	       fetchEventListSpecificToUser(userId);
	    }else if(getConfiguration().getPropertyByName(DATEBASED_LIST)!=null) {
	    	Object value = getConfiguration().getPropertyByName(DATEBASED_LIST_VALUE);
	    	fetchEventListFromDate(userId,value);
	    }
		
	}

	private void fetchEventListFromDate(Long userId, Object value) {
		
		Date date = (Date) value;
		/*DateTimeFormat fmt = DateTimeFormat.getFormat("yyyy-MM-dd");
		String formatedFromDate = fmt.getFormat("yyyy-MM-dd").format(date);
		Date date2 = new */
		clear();
		Query query = new Query();
		query.setQueryName("getAllEventsFromDate");//
		query.setListSize(10);
			
		HashMap<String, Object> queryParam = new HashMap<String, Object>();
		queryParam.put("ownerId", userId);
		queryParam.put("fromDate", date);
		query.setQueryParameterMap(queryParam);
		
		
		EntityListModel reminderListModel = new EntityListModel();
		
		reminderListModel.setOperationNameToBind("calendar.CalendarService.getEntityList");
		reminderListModel.setQueryToBind(query);
		reminderListModel.setNoOfEntities(0);
						
		listSnippet= new ListSnippet();
		listSnippet.setEntityListModel(reminderListModel);
		listSnippet.setConfiguration(getConfiguration());
		listSnippet.initialize();
		
		Label headingLbl = new Label(" ______________________________________________________");
		headingLbl.setStylePrimaryName("serviceHomeHeadingLabel");
		add(headingLbl);
		setCellHorizontalAlignment(headingLbl, HasHorizontalAlignment.ALIGN_CENTER);
		add(listSnippet);
		setCellHorizontalAlignment(listSnippet, HasHorizontalAlignment.ALIGN_CENTER);
		
	}

	public void fetchEventListSpecificToUser(Long userId){
		clear();
		Query query = new Query();
		query.setQueryName("getAllEventsOfUser");
		query.setListSize(10);
			
		HashMap<String, Object> queryParam = new HashMap<String, Object>();
		queryParam.put("ownerId", userId);
		query.setQueryParameterMap(queryParam);
		
		
		EntityListModel reminderListModel = new EntityListModel();
		
		reminderListModel.setOperationNameToBind("calendar.CalendarService.getEntityList");
		reminderListModel.setQueryToBind(query);
		reminderListModel.setNoOfEntities(0);
						
		listSnippet= new ListSnippet();
		listSnippet.setEntityListModel(reminderListModel);
		listSnippet.setConfiguration(getConfiguration());
		listSnippet.initialize();
		
		Label headingLbl = new Label(" ______________________________________________________");
		headingLbl.setStylePrimaryName("serviceHomeHeadingLabel");
		add(headingLbl);
		setCellHorizontalAlignment(headingLbl, HasHorizontalAlignment.ALIGN_CENTER);
		add(listSnippet);
		setCellHorizontalAlignment(listSnippet, HasHorizontalAlignment.ALIGN_CENTER);
	}
	
	@Override
	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
		
	}

	@Override
	public Configuration getConfiguration() {
		return configuration;
	}

	@Override
	public ActionContext getActionContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setActionContext(ActionContext actionContext) {
		// TODO Auto-generated method stub
		
	}

}

package in.appops.client.common.snippet;

import in.appops.client.common.core.EntityListModel;
import in.appops.client.common.util.AppEnviornment;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.entity.query.Query;
import in.appops.platform.core.operation.ActionContext;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.server.core.services.useraccount.constant.UserPojoConstant;

import java.util.HashMap;

import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CalendarServiceHomeSnippet extends VerticalPanel implements Snippet {
	
	private ListSnippet listSnippet ;
	private Entity entity;
	private String type;
	private Configuration configuration;
	
		
	public CalendarServiceHomeSnippet() {
	}
	
	@Override
	public void initialize(){
		//Property<Key<Long>> userIdProp = (Property<Key<Long>>) entity.getProperty(UserConstants.ID);
		//initializeListForUser(userIdProp.getValue().getKeyValue());
		
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(SnippetConstant.SELECTIONMODE, false);
		setConfiguration(configuration);
		
		Entity usrEnt =  AppEnviornment.getCurrentUser();
		Long userId = ((Key<Long>)usrEnt.getPropertyByName(UserPojoConstant.ID)).getKeyValue();
		initializeListForUser(userId);
	}
	
	
	public void initializeListForUser(long userId) {
		
		Query query = new Query();
		query.setQueryName("getAllRemindersOfUser");
		query.setListSize(10);
			
		HashMap<String, Object> queryParam = new HashMap<String, Object>();
		queryParam.put("userId", userId);
		query.setQueryParameterMap(queryParam);
		
		
		EntityListModel reminderListModel = new EntityListModel();
		
		reminderListModel.setOperationNameToBind("calendar.CalendarService.getEntityList");
		reminderListModel.setQueryToBind(query);
		reminderListModel.setNoOfEntities(0);
						
		listSnippet= new ListSnippet();
		listSnippet.setEntityListModel(reminderListModel);
		listSnippet.setConfiguration(getConfiguration());
		listSnippet.initialize();
		
		Label headingLbl = new Label(" Calendar reminders for you ");
		headingLbl.setStylePrimaryName("serviceHomeHeadingLabel");
		add(headingLbl);
		setCellHorizontalAlignment(headingLbl, HasHorizontalAlignment.ALIGN_CENTER);
		add(listSnippet);
		
	}

	@Override
	public Entity getEntity() {
		return entity;
	}

	@Override
	public void setEntity(Entity entity) {
		this.entity = entity;
		
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public void setType(String type) {
		this.type = type;
		
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

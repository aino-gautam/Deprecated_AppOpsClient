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

public class ReminderListSnippet extends VerticalPanel implements Snippet{

	private ListSnippet listSnippet ;
	private Entity entity;
	private Configuration configuration;
	private Entity userEntity;
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
		     Configuration configuration = new Configuration();
			 configuration.setPropertyByName(SnippetConstant.SELECTIONMODE, false);
			 configuration.setPropertyByName(ListSnippet.SCROLLPANELWIDTH, 650);
			 configuration.setPropertyByName(ListSnippet.SCROLLPANELHEIGHT, 500);
			 setConfiguration(configuration);
						
			Long userId = ((Key<Long>)userEntity.getPropertyByName(UserPojoConstant.ID)).getKeyValue();
			initializeListForUser(userId);
				
		
	}
     public void initializeListForUser(long userId) {
		clear();
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

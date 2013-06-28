package in.appops.client.common.components;

import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.client.common.fields.LabelField;
import in.appops.client.common.util.AppEnviornment;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.core.constants.propertyconstants.SpaceConstants;
import in.appops.platform.core.constants.propertyconstants.UserConstants;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.entity.query.Query;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;
import in.appops.platform.core.util.EntityList;
import in.appops.platform.server.core.services.contact.constant.ContactConstant;
import in.appops.platform.server.core.services.usermessage.constant.MessageConstant;

import java.io.Serializable;
import java.util.HashMap;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MessagesHomeWidget extends Composite implements FieldEventHandler{

	private HorizontalPanel mainPanel;
	private VerticalPanel leftSidePanel;
	private VerticalPanel rightSidePanel;
	private Entity userEntity;
	private Entity contactEntity;
	
	
	public MessagesHomeWidget() {
		initialize();
		//fecthUser();
		userEntity=AppEnviornment.getCurrentUser();
		fetchContactOfLoggedUser(userEntity);
		initWidget(mainPanel);
	}
	
	@SuppressWarnings("unchecked")
	private void fecthUser() {
		DefaultExceptionHandler	exceptionHandler	= new DefaultExceptionHandler();
		DispatchAsync				dispatch			= new StandardDispatchAsync(exceptionHandler);

		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId",Long.valueOf(2));
				
		StandardAction action = new StandardAction(EntityList.class, "useraccount.UserAccountService.getUserFromId", paramMap);
		dispatch.execute(action, new AsyncCallback<Result<Entity>>() {
			
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}
			
			public void onSuccess(Result<Entity> result) {
				if(result!=null){
				    userEntity=result.getOperationResult();
				    fetchContactOfLoggedUser(userEntity);
				    
				}
			}

			
		});
		
	}

	@SuppressWarnings("unchecked")
	private void fetchContactOfLoggedUser(final Entity userEntity) {
 		Key<Serializable> key = (Key<Serializable>) userEntity.getProperty(UserConstants.ID).getValue();
	    Long userId = (Long) key.getKeyValue();

	    Query query = new Query();
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		
		if(AppEnviornment.getCurrentSpace()!=null){
			query.setQueryName("getUserSpaceContact");
			Entity spaceEnt = AppEnviornment.getCurrentSpace();
			Long spaceId = ((Key<Long>)spaceEnt.getPropertyByName(SpaceConstants.ID)).getKeyValue();
			hashMap.put("spaceId", spaceId);
		}else
			query.setQueryName("getContactFromUserId");
		
		hashMap.put("userId", userId);
		
		query.setQueryParameterMap(hashMap);
		
		DefaultExceptionHandler	exceptionHandler	= new DefaultExceptionHandler();
		DispatchAsync				dispatch			= new StandardDispatchAsync(exceptionHandler);

		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("query",query);
				
		StandardAction action = new StandardAction(EntityList.class, "contact.ContactService.getEntityList", paramMap);
		dispatch.execute(action, new AsyncCallback<Result<EntityList>>() {
			
			public void onFailure(Throwable caught) {
				Window.alert("operation failed ");
				caught.printStackTrace();
			}
			
			public void onSuccess(Result<EntityList> result) {
				if(result!=null){
				  EntityList  list=result.getOperationResult();
				  try {
					  if(list.size()>0){
					   for(Entity conEntity : list){
						   contactEntity = conEntity;
						   createComponent(userEntity,conEntity);
					   }
					  }else{
						  LabelField label = new LabelField();
							label.setFieldValue("No contacts are available for current space..");
							label.setConfiguration(getLabelFieldConfiguration(true, "messageNotificationLabel", null, null));
							label.createField();
							
							mainPanel.add(label);
					  }
					} catch (AppOpsException e) {
						e.printStackTrace();
					}
				    
				}
			}
		});
		
		
	}
	
	

	private void initialize() {
		mainPanel = new HorizontalPanel();
		leftSidePanel = new VerticalPanel();
		rightSidePanel = new VerticalPanel();
	}
	
	public void createComponent(Entity userEntity2, Entity contactEntity) throws AppOpsException{
		mainPanel.clear();
		UserThreadWidget threadWidget = new UserThreadWidget();
	    threadWidget.setUserEntity(userEntity);
	    //threadWidget.setHandler(this);
	    threadWidget.addFieldEventHandler(this);
	    threadWidget.fetchAllMessageUserParticipantsAndSender();
	    leftSidePanel.add(threadWidget);
		mainPanel.add(leftSidePanel);
		mainPanel.setCellWidth(leftSidePanel, "20%");
		mainPanel.setCellHorizontalAlignment(leftSidePanel, HasHorizontalAlignment.ALIGN_CENTER);
		
		mainPanel.add(rightSidePanel);
		mainPanel.setCellWidth(rightSidePanel, "80%");
		mainPanel.setCellHorizontalAlignment(rightSidePanel, HasHorizontalAlignment.ALIGN_CENTER);
		mainPanel.setStylePrimaryName("messagesHomeWidget");
		leftSidePanel.setStylePrimaryName("leftSidePanel");
		
	}

	public Entity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(Entity userEntity) {
		this.userEntity = userEntity;
	}

	@Override
	public void onFieldEvent(FieldEvent event) {
		int eventType = event.getEventType();
		Object object=event.getEventData();
		if(object instanceof Entity){
			Entity snippetEntity = (Entity) event.getEventData();
			 if(eventType == FieldEvent.SUGGESTION_CLICKED ){
				fetchMessageConversationForContact(snippetEntity); 
			}
		}
		
	}

	@SuppressWarnings("unchecked")
	private void fetchMessageConversationForContact(final Entity snippetEntity) {
		rightSidePanel.clear();
		rightSidePanel.add(createLoaderWithTextWidget());
		Long parentId = null;
	     try{
		    parentId=snippetEntity.getPropertyByName(MessageConstant.PARENTID);
		  
		Query query = new Query();
		query.setQueryName("getMessageWithReply");
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("parentId", parentId);
		hashMap.put("id", parentId);
		query.setQueryParameterMap(hashMap);
		
		DefaultExceptionHandler	exceptionHandler	= new DefaultExceptionHandler();
		DispatchAsync				dispatch			= new StandardDispatchAsync(exceptionHandler);

		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("query",query);
				
		StandardAction action = new StandardAction(EntityList.class, "usermessage.UserMessageService.getMessageWithReply", paramMap);
		dispatch.execute(action, new AsyncCallback<Result<EntityList>>() {
			
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}
			
			public void onSuccess(Result<EntityList> result) {
				
				if(result!=null){
				  EntityList  list=result.getOperationResult();
				    try {
				    	rightSidePanel.clear();
						MessagingThreadWithReplyWidget messagingThreadWithReplyWidget = new MessagingThreadWithReplyWidget();
						messagingThreadWithReplyWidget.setContactEntity(contactEntity);
						messagingThreadWithReplyWidget.setClickSnippetEntity(snippetEntity);
						messagingThreadWithReplyWidget.createComponent(list);
						rightSidePanel.add(messagingThreadWithReplyWidget);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				    
				}
			}
		});
	 
	    }catch(Exception e){
	    	rightSidePanel.clear();
			  LabelField labelField = new LabelField();
				Configuration labelConfig = getLabelFieldConfiguration(true, "flowPanelContent", null, null);
				
				labelField.setFieldValue("No messages available");
				
				labelField.setConfiguration(labelConfig);
				try {
					labelField.createField();
				} catch (AppOpsException e1) {
					e1.printStackTrace();
				}
				
			  rightSidePanel.add(labelField);
		  } 
		  
	 
	}
	public Configuration getLabelFieldConfiguration(boolean allowWordWrap, String primaryCss, String secondaryCss, String debugId) {
		Configuration config = new Configuration();
		config.setPropertyByName(LabelField.LABELFIELD_WORDWRAP, allowWordWrap);
		config.setPropertyByName(LabelField.LABELFIELD_PRIMARYCSS, primaryCss);
		config.setPropertyByName(LabelField.LABELFIELD_DEPENDENTCSS, secondaryCss);
		config.setPropertyByName(LabelField.LABELFIELD_DEBUGID, debugId);
		return config;
	}
	
	private HorizontalPanel createLoaderWithTextWidget() {
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		
	        try {
				
				
				LabelField labelField = new LabelField();
				Configuration labelConfig = getLabelFieldConfiguration(true, "flowPanelContent", null, null);
								
					labelField.setFieldValue("Loading messages ...");
				
				labelField.setConfiguration(labelConfig);
				labelField.createField();
				
			
				horizontalPanel.add(labelField);
				
			} catch (AppOpsException e) {
				
				e.printStackTrace();
			}
		return horizontalPanel;
		
	}
}

package in.appops.client.common.components;

import in.appops.client.common.components.ActionWidget.ActionWidgetConfiguration;
import in.appops.client.common.components.ActionWidget.ActionWidgetType;
import in.appops.client.common.event.ActionEvent;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.AttachmentEvent;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.SearchEvent;
import in.appops.client.common.event.handlers.AttachmentEventHandler;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.client.common.fields.IntelliThoughtField;
import in.appops.client.common.util.AppEnviornment;
import in.appops.client.common.util.EntityToJsonClientConvertor;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.core.constants.propertyconstants.SpaceConstants;
import in.appops.platform.core.constants.typeconstants.TypeConstants;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.type.MetaType;
import in.appops.platform.core.operation.InitiateActionContext;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.shared.Configurable;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;
import in.appops.platform.core.util.EntityList;
import in.appops.platform.server.core.services.platform.coreplatformservice.constant.ActionsConstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class IntelliThoughtWidget extends Composite implements Configurable, ClickHandler, FieldEventHandler, AttachmentEventHandler{
	private FlexTable basePanel;
	private IntelliThoughtField intelliShareField;
	private MediaAttachWidget attachMediaField;
	private SuggestionAction suggestionAction;
	private HorizontalPanel mediaServicePanel;
	private boolean isAttachedMediaField;

	private Label searchButton;
	private Label messageButton;
	
	private HorizontalPanel searchPanel = new HorizontalPanel();
	private HorizontalPanel messagePanel = new HorizontalPanel();

	private FocusPanel searchBasePanel = new FocusPanel();
	private FocusPanel messageBasePanel = new FocusPanel();
	
	public static final String IS_INTELLISHAREFIELD = "isIntelliShareField";
	public static final String IS_ATTACHMEDIAFIELD = "isAttachMediaField";
	public static final String IS_SUGGESTIONACTION = "isSuggestionAction";
	public static final String IS_PREDIFINEDACTION = "isPredifinedAction";
	public static final String PRIMARYSCSS = "intelliToughtOptionPanel";
	public static final String DEPENDENTCSS = "intelliToughtOptionPanel";

	private ArrayList<String> uploadedMediaId = null;
	
	public IntelliThoughtWidget(){
		initialize();
		initWidget(basePanel);
	}

	private void initialize() {
		basePanel = new FlexTable();
		mediaServicePanel = new HorizontalPanel();
		intelliShareField = new IntelliThoughtField();
		suggestionAction = new SuggestionAction();
		uploadedMediaId = new ArrayList<String>();
	}
	
	/**
	 * Creates the IntelliSharedComponent
	 * @param configuration
	 * @throws AppOpsException
	 */
	public void createComponent(Configuration configuration) throws AppOpsException {
			
			String isIntelliShareField = 	configuration.getPropertyByName(IS_INTELLISHAREFIELD);
			String isAttachMediaField = 	configuration.getPropertyByName(IS_ATTACHMEDIAFIELD);
			String isSuggestionAction = 	configuration.getPropertyByName(IS_SUGGESTIONACTION);
			String isPredifinedAction = 	configuration.getPropertyByName(IS_PREDIFINEDACTION);

			if(Boolean.valueOf(isAttachMediaField)){
				createAttachMediaField();
			}
			if(Boolean.valueOf(isIntelliShareField)){
				createIntelliShareField();
			}
			if(Boolean.valueOf(isPredifinedAction)){
				addPredefinedOptions();
			}
			if(Boolean.valueOf(isSuggestionAction)){
				createSuggestionActionHolder();
			}
			
			intelliShareField.addFieldEventHandler(this);
			AppUtils.EVENT_BUS.addHandler(AttachmentEvent.TYPE, this);
	}
	
	private void createSuggestionActionHolder() {
		basePanel.setWidget(5, 0, suggestionAction);
	}

	private void addPredefinedOptions() {
		searchPanel = new HorizontalPanel();
		messagePanel = new HorizontalPanel();
		
		searchBasePanel.add(searchPanel);
		messageBasePanel.add(messagePanel);

		Image searchImage = new Image("images/binocular.png");
		searchImage.setStylePrimaryName("appops-intelliThoughtActionImage");
		searchButton = new Label("Search");
		searchPanel.add(searchImage);
		searchPanel.add(searchButton);
		searchPanel.setCellVerticalAlignment(searchButton, HasVerticalAlignment.ALIGN_MIDDLE);
		searchBasePanel.addClickHandler(this);
				
		Image msgImage = new Image("images/message_email.png");
		msgImage.setStylePrimaryName("appops-intelliThoughtActionImage");
		messageButton = new Label("Message");
		messagePanel.add(msgImage);
		messagePanel.add(messageButton);
		messagePanel.setCellVerticalAlignment(messageButton, HasVerticalAlignment.ALIGN_MIDDLE);
		messageBasePanel.addClickHandler(this);

		searchBasePanel.setStylePrimaryName("appops-intelliThoughtPredefinedOptionPanel");
		messageBasePanel.setStylePrimaryName("appops-intelliThoughtPredefinedOptionPanel");
		
		searchButton.setStylePrimaryName("appops-intelliThoughtActionLabel");
		messageButton.setStylePrimaryName("appops-intelliThoughtActionLabel");

		basePanel.setWidget(1, 1, searchBasePanel);
		basePanel.setWidget(2, 0, messageBasePanel);
		
		searchBasePanel.setVisible(false);
		messageBasePanel.setVisible(false);
	}

	private void createAttachMediaField() {
		basePanel.setWidget(0, 0, mediaServicePanel);
		mediaServicePanel.setStylePrimaryName("intelliShareField");
		
		attachMediaField = new WebMediaAttachWidget();
		attachMediaField.isFadeUpEffect(true);
		attachMediaField.createUi();
		
		mediaServicePanel.add(attachMediaField);
		attachMediaField.setVisible(false);
		mediaServicePanel.setWidth("100%");
		mediaServicePanel.setCellHorizontalAlignment(attachMediaField, HasHorizontalAlignment.ALIGN_RIGHT);
		attachMediaField.createAttachmentUi();
		attachMediaField.collapse();

		attachMediaField.getMedia().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(attachMediaField.isExpand()){
					attachMediaField.collapse();
				} else if(attachMediaField.isCollapse()){
					attachMediaField.expand();
				}
			}
		});
	}

	private void createIntelliShareField() throws AppOpsException {
		intelliShareField.createField();
		basePanel.getFlexCellFormatter().setRowSpan(1, 0, 3);

		basePanel.setWidget(1, 0, intelliShareField);
		basePanel.getFlexCellFormatter().setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_TOP);
		basePanel.getFlexCellFormatter().getElement(1, 0).setClassName("appops-intelliThoughtHolder");
	}

	public void setIntelliShareFieldConfiguration(Configuration conf){
		intelliShareField.setConfiguration(conf);
	}

	@Override
	public Configuration getConfiguration() {
		return null;
	}
	
	@Override
	public void setConfiguration(Configuration conf) {
		
	}

	@Override
	public void onClick(ClickEvent event) {
		String text = intelliShareField.getText();

		Widget source = (Widget) event.getSource();
		if(source.equals(searchBasePanel)){
/*			SearchEvent searchEvent = new SearchEvent(SearchEvent.SEARCHFIRED, text);
			AppUtils.EVENT_BUS.fireEvent(searchEvent);*/

			/**** For Appops Showcase *****/
			if(!text.trim().equals("")) {
				Window.open( GWT.getHostPageBaseURL() + "SearchWidgetDemo.html?gwt.codesvr=127.0.0.1:9997&text="+text.trim(), "right_frame", "");
			}
		} else if(source.equals(messageBasePanel)){
//			ActionEvent actionEvent = getMessageActionEvent();
//			AppUtils.EVENT_BUS.fireEvent(actionEvent);

			/**** For Appops Showcase *****/
			if(!text.trim().equals("")) {
				Window.open( GWT.getHostPageBaseURL() + "contactbox.html?gwt.codesvr=127.0.0.1:9997&text="+text.trim(), "right_frame", "");
			}
		}
	}

	private ActionEvent getMessageActionEvent() {
		InitiateActionContext context = new InitiateActionContext();
		context.setType(new MetaType("ActionContext"));
		context.setSpace(AppEnviornment.getCurrentSpace());
		context.setUploadedMedia(uploadedMediaId);
		context.setIntelliThought(intelliShareField.getIntelliThought());
		
		ActionEvent actionEvent = new ActionEvent();
		actionEvent.setEventType(ActionEvent.MESSAGE);			
		actionEvent.setEventData(context);
		
		return actionEvent;
	}

	private void handleWordEnteredEvent(String string) {
		
		if(!searchBasePanel.isVisible()){
			searchBasePanel.addStyleName("fadeInLeft");
			searchBasePanel.setVisible(true);
			messageBasePanel.addStyleName("fadeInLeft");
			messageBasePanel.setVisible(true);
		} 
		showActionSuggestion(string);
	}

	public boolean isAttachedMediaField() {
		return isAttachedMediaField;
	}

	public void setAttachedMediaField(boolean isAttachedMediaField) {
		this.isAttachedMediaField = isAttachedMediaField;
	}

	@Override
	public void onFieldEvent(FieldEvent event) {
		int eventType = event.getEventType();
		String eventData = (String) event.getEventData();
		if(this.isVisible()) {
			if(eventType == FieldEvent.EDITINITIATED) {
				if (!isAttachedMediaField) {
					attachMediaField.setVisible(true);
					setAttachedMediaField(true);
				} 
			} else if(eventType == FieldEvent.WORDENTERED) {
				handleWordEnteredEvent(eventData);
			}
		}
	}

	@Override
	public void onAttachmentEvent(AttachmentEvent event) {
		String blobId = (String) event.getEventData();
		if(event.getEventType()==AttachmentEvent.ATTACHMENTINITIATED){

		}else if(event.getEventType()==AttachmentEvent.ATTACHMENTCOMPLETED){
			if(blobId!=null){
				if(!uploadedMediaId.contains(blobId))
					uploadedMediaId.add(blobId);
			}
		}else if(event.getEventType()==AttachmentEvent.ATTACHMENTCANCELLED){
			if(blobId!=null){
				if(uploadedMediaId.contains(blobId))
					uploadedMediaId.remove(blobId);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public void showActionSuggestion(String word){
	
		DefaultExceptionHandler	exceptionHandler	= new DefaultExceptionHandler();
		DispatchAsync				dispatch			= new StandardDispatchAsync(exceptionHandler);

		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("word", word);
		
		StandardAction action = new StandardAction(EntityList.class, "coreplatform.CorePlatformService.getSuggestionAction", paramMap);
		dispatch.execute(action, new AsyncCallback<Result<EntityList>>() {
			
			public void onFailure(Throwable caught) {
				Window.alert("operation failed ");
				caught.printStackTrace();
			}
			
			public void onSuccess(Result<EntityList> result) {
				if(result != null) { 
					EntityList  entityList =  result.getOperationResult();
					suggestionAction.clearSuggestionPanel();
					if(entityList != null && !entityList.isEmpty()) {
						for(Entity entity : entityList ){
							final String widgetName = entity.getPropertyByName(ActionsConstant.NAME);
							final ActionWidget actionWidget = new ActionWidget(ActionWidgetType.LABEL);
							actionWidget.setWidgetText(widgetName);
							actionWidget.setActionEntity(entity);
							actionWidget.setConfiguration(getActionConfiguration());
							actionWidget.createUi();
							suggestionAction.addSuggestionAction(actionWidget);
							actionWidget.addClickHandler(new ClickHandler() {
								
								@Override
								public void onClick(ClickEvent event) {
	//								ActionEvent actionEvent = getActionEvent(actionWidget);
	//								actionWidget.fireEvent(actionEvent);
									
									/**** For Appops Showcase *****/
									appopsClientTransformWidget(widgetName);
								}
	
							});
							
						}
					}
				}
			}
		});
	}
	
	protected Configuration getActionConfiguration() {
		Configuration conf = new Configuration();
		conf.setPropertyByName(ActionWidgetConfiguration.PRIMARY_CSS.toString(), "appops-intelliThoughtActionLabel");
		return conf;
	}

	private ActionEvent getActionEvent(ActionWidget actionWidget) {
		
		InitiateActionContext context = new InitiateActionContext();
		context.setType(new MetaType("ActionContext"));
		
		Entity spaceEntity = new Entity();
		spaceEntity.setType(new MetaType(TypeConstants.SPACE));
		spaceEntity.setPropertyByName(SpaceConstants.ID, 3);
		spaceEntity.setPropertyByName(SpaceConstants.NAME, "Pune");
		
		context.setSpace(AppEnviornment.getCurrentSpace());
		context.setUploadedMedia(uploadedMediaId);
		context.setIntelliThought(intelliShareField.getIntelliThought());
		
		if(actionWidget.getActionEntity() != null){
			context.setActionEntity(actionWidget.getActionEntity());
		}
		
		JSONObject token = EntityToJsonClientConvertor.createJsonFromEntity(context);
		
		/** For testing purpose.. not to be removed **/
//		Entity ent = new JsonToEntityConverter().getConvertedEntity(token);
//		InitiateActionContext cont = (InitiateActionContext)ent;
//		String action = cont.getAction();
//		ArrayList<String> media = cont.getUploadedMedia();
//		Entity space = cont.getSpace();
		
		ActionEvent actionEvent = new ActionEvent();
		actionEvent.setEventType(ActionEvent.TRANSFORMWIDGET);			
		actionEvent.setEventData(token.toString());
		
		return actionEvent;
	}
	
	public void populateAttachment(List<String> mediaList){
		attachMediaField.setMediaAttachments(mediaList);
		attachMediaField.setVisible(true);
		setAttachedMediaField(true);
	}
	

	/**** For Appops Showcase *****/
	private void appopsClientTransformWidget(String widgetName) {
		Widget displayWidget = null;
		if(widgetName.equalsIgnoreCase("Photos")) {
			Window.open( GWT.getHostPageBaseURL() + "dragonwheel.html?gwt.codesvr=127.0.0.1:9997", "right_frame", "");
		} else if(widgetName.equalsIgnoreCase("Contacts")) {
			Window.open( GWT.getHostPageBaseURL() + "contactselector.html?gwt.codesvr=127.0.0.1:9997", "right_frame", "");
		}
	}
}
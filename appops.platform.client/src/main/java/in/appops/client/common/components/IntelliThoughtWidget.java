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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
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
	private Label postButton;
	private Label messageButton;
	
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

		searchButton = new Label("Search");
		searchButton.addClickHandler(this);
		
		postButton = new Label("Post");
		messageButton = new Label("Message");

		searchButton.setStylePrimaryName("appops-intelliThought-Label");
		postButton.setStylePrimaryName("appops-intelliThought-Label");
		messageButton.setStylePrimaryName("appops-intelliThought-Label");
		
//		prominentOptionlPanel.add(searchButton);
//		prominentOptionlPanel.add(postButton);
//		prominentOptionlPanel.add(messageButton);

		basePanel.setWidget(1, 1, searchButton);
		basePanel.getCellFormatter().setHeight(1, 1, "10px");

		basePanel.setWidget(2, 0, messageButton);
		basePanel.getCellFormatter().setHeight(2, 0, "10px");

		basePanel.setWidget(3, 0, postButton);
		basePanel.getCellFormatter().setHeight(3, 0, "10px");
		
		searchButton.setVisible(false);
		postButton.setVisible(false);
		messageButton.setVisible(false);
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
		basePanel.getFlexCellFormatter().setRowSpan(1, 0, 4);

		basePanel.setWidget(1, 0, intelliShareField);
		basePanel.getFlexCellFormatter().setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_TOP);
		//basePanel.getFlexCellFormatter().getElement(1, 0).setClassName("intelliThoughtFieldCol");
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
		Widget source = (Widget) event.getSource();
		if(source.equals(attachMediaField.getMedia())){
			//attachMediaField.showMediaOption();
		} else if(source.equals(searchButton)){
			String text = intelliShareField.getText();
			SearchEvent searchEvent = new SearchEvent(SearchEvent.SEARCHFIRED, text);
			AppUtils.EVENT_BUS.fireEvent(searchEvent);
		}
	}

	private void handleWordEnteredEvent(String string) {
		String intelliText  = intelliShareField.getText();
		String[] words = intelliText.split("\\s+");
		
		if(!searchButton.isVisible()){
			searchButton.addStyleName("fadeInLeft");
			searchButton.setVisible(true);
			messageButton.addStyleName("fadeInLeft");
			messageButton.setVisible(true);
		} 
		
		if(words.length == 2 && !postButton.isVisible()){
			postButton.addStyleName("fadeInLeft");
			postButton.setVisible(true);
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
		
		if(eventType == FieldEvent.EDITINITIATED) {
			if (!isAttachedMediaField) {
				attachMediaField.setVisible(true);
				setAttachedMediaField(true);
			} 
		} else if(eventType == FieldEvent.WORDENTERED) {
			handleWordEnteredEvent(eventData);
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
		paramMap.put("word", "%"+ word +"%");
		
		StandardAction action = new StandardAction(EntityList.class, "spacemanagement.SpaceManagementService.getSuggestionAction", paramMap);
		dispatch.execute(action, new AsyncCallback<Result<EntityList>>() {
			
			public void onFailure(Throwable caught) {
				Window.alert("operation failed ");
				caught.printStackTrace();
			}
			
			public void onSuccess(Result<EntityList> result) {
				EntityList  entityList =  result.getOperationResult();
				for(Entity entity : entityList ){
					String widgetName = entity.getPropertyByName("widgetname");
					final ActionWidget actionWidget = new ActionWidget(ActionWidgetType.LABEL);
					actionWidget.setWidgetText(widgetName);
					actionWidget.setActionEvent(getActionEvent(actionWidget));
					actionWidget.setConfiguration(getActionConfiguration());
					actionWidget.createUi();
					suggestionAction.addSuggestionAction(actionWidget);
					
				}
			}
		});
	}
	
	protected Configuration getActionConfiguration() {
		Configuration conf = new Configuration();
		conf.setPropertyByName(ActionWidgetConfiguration.PRIMARY_CSS.toString(), "appops-LabelField");
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
		
		if(actionWidget.getWidgetText() != null){
			context.setAction(actionWidget.getWidgetText());
		}
		
		JSONObject token = EntityToJsonClientConvertor.createJsonFromEntity(context);
//		Entity ent = new JsonToEntityConverter().getConvertedEntity(token);
//		InitiateActionContext cont = (InitiateActionContext)ent;
//		String action = cont.getAction();
//		ArrayList<String> media = cont.getUploadedMedia();
//		Entity space = cont.getSpace();
		
		ActionEvent actionEvent = new ActionEvent();
		actionEvent.setEventType(ActionEvent.TRANSFORMWIDGET);			
		actionEvent.setEventData(token.toString());
		
//		ActionEvent actionEvent = getActionEvent(ActionEvent.TRANSFORMWIDGET, token.toString()); 
//		fireActionEvent(actionEvent);
		return actionEvent;
	}
	
	public void populateAttachment(List<String> mediaList){
		attachMediaField.setMediaAttachments(mediaList);
		attachMediaField.setVisible(true);
		setAttachedMediaField(true);
	}
	
//	private ActionEvent getActionEvent(int type, String data){
//		ActionEvent actionEvent = new ActionEvent();
//		actionEvent.setEventType(type);			
//		actionEvent.setEventData(data);
//		return actionEvent;
//	}
//	
//	private void fireActionEvent(ActionEvent actionEvent) {
//		AppUtils.EVENT_BUS.fireEvent(actionEvent);
//	}
}
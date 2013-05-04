package in.appops.client.common.snippet;

import in.appops.client.common.components.ActionWidget;
import in.appops.client.common.components.ActionWidget.ActionWidgetConfiguration;
import in.appops.client.common.components.ActionWidget.ActionWidgetType;
import in.appops.client.common.core.EntityReceiver;
import in.appops.client.common.event.ActionEvent;
import in.appops.client.common.fields.ImageField;
import in.appops.client.common.fields.LabelField;
import in.appops.client.common.fields.PostInButton;
import in.appops.client.common.handler.HandlerFactory;
import in.appops.client.common.handler.HandlerFactoryImpl;
import in.appops.client.common.handler.ResponseActionHandler;
import in.appops.client.common.util.AppEnviornment;
import in.appops.client.common.util.BlobDownloader;
import in.appops.client.common.util.EntityToJsonClientConvertor;
import in.appops.client.common.util.JsonToEntityConverter;
import in.appops.client.common.util.PostContentParser;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.JsonProperty;
import in.appops.platform.core.entity.Property;
import in.appops.platform.core.entity.query.Query;
import in.appops.platform.core.entity.type.MetaType;
import in.appops.platform.core.operation.InitiateActionContext;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;
import in.appops.platform.core.util.EntityList;
import in.appops.platform.server.core.services.platform.coreplatformservice.constant.ActionResponseViewConstant;
import in.appops.platform.server.core.services.social.constant.PostConstant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PostViewSnippet extends RowSnippet {

	private DockPanel postSnippetPanel;
	private LabelField timeLbl;
	private VerticalPanel postContentPanel;
	private HorizontalPanel spaceIconPlusTimePanel ;
	
	public static final String POST_SNIPPET = "postSnippet";
	public static final String POST_TIME_LABEL = "postTimeLabel";
	public static final String POST_USER_IMAGE = "postUserImage";
	public static final String POST_CONTEXT_LABEL = "postContextLabel";
	public static final String HAND_CSS = "handCss";
	public static final String POST_RESPONSE_IMAGE = "postResponseImage";
	
	private final DefaultExceptionHandler exceptionHandler = new DefaultExceptionHandler();
	private final DispatchAsync	dispatch = new StandardDispatchAsync(exceptionHandler);
	private ArrayList<Entity> responseoptionList = null;
	private Entity userEntity;
	
	public PostViewSnippet() {
		
	}
	
	@Override
	public void initialize(){
		super.initialize();
		responseoptionList = new ArrayList<Entity>();
		postSnippetPanel = new DockPanel();
		createUi();
	}
	
	public void createUi(){
		userEntity = null;
		String blobUrl = null;
				
		HorizontalPanel imagePanel = new HorizontalPanel();
		 postContentPanel = new VerticalPanel();
		 spaceIconPlusTimePanel = new HorizontalPanel();
		final ImageField imageField = new ImageField();
				
		BlobDownloader blobDownloader = new BlobDownloader();
		//TODO currently all this values getting from dummy post entity need to modify it in future
		Property<Serializable> property=(Property<Serializable>) getEntity().getProperty(PostConstant.CREATEDBY);
		if(property==null)
			blobUrl=blobDownloader.getIconDownloadURL("irqSN52SzHwHksn9NQFKxEIDYl0RWF3RJz6m45WSDzsafhuCSihRDg%3D%3D");
		else if(property.getValue() instanceof Long){
			//blobUrl=blobDownloader.getIconDownloadURL(userEntity.getPropertyByName("imgBlobId").toString());
			blobUrl=blobDownloader.getIconDownloadURL("irqSN52SzHwHksn9NQFKxEIDYl0RWF3RJz6m45WSDzsafhuCSihRDg%3D%3D");
		} else{
			userEntity=(Entity) property.getValue();
			blobUrl=blobDownloader.getIconDownloadURL(userEntity.getPropertyByName("imgBlobId").toString());
		}
		
		
		
		imageField.setConfiguration(getImageFieldConfiguration(blobUrl));
		try {
			imageField.createField();
		} catch (AppOpsException e) {
			 e.printStackTrace();
		}
		
		imagePanel.add(imageField);
		imagePanel.setBorderWidth(1);
		postSnippetPanel.add(imagePanel, DockPanel.WEST);
		postSnippetPanel.setCellWidth(imagePanel, "10%");
		
		imagePanel.setCellVerticalAlignment(imageField, HasVerticalAlignment.ALIGN_MIDDLE);
		
	    imageField.addMouseOverHandler(new MouseOverHandler() {
			
			@Override
			public void onMouseOver(MouseOverEvent event) {
				if(userEntity == null){
					imageField.setAltText("Image not available");
				} else{
					imageField.setAltText(userEntity.getPropertyByName("username").toString());
				}
				
			}
		});
		
				
		String xmlContent = getEntity().getPropertyByName(PostConstant.CONTENT).toString();
		PostContentParser postContentParser = new PostContentParser();
		FlowPanel postContentFlowPanel = postContentParser.getGeneralMsgComponent(xmlContent);
		
		
		postContentPanel.add(spaceIconPlusTimePanel);
		postContentPanel.setCellWidth(spaceIconPlusTimePanel, "10%");
		
		postContentPanel.add(postContentFlowPanel);
		postContentPanel.setCellVerticalAlignment(postContentFlowPanel, HasVerticalAlignment.ALIGN_MIDDLE);
		postContentPanel.setCellHorizontalAlignment(postContentFlowPanel, HasHorizontalAlignment.ALIGN_JUSTIFY);
		postContentPanel.setHeight("100%");
		postContentPanel.setWidth("100%");
		//spaceIconPlusTimePanel.setWidth("100%");
		postSnippetPanel.add(postContentPanel, DockPanel.CENTER);
		postSnippetPanel.setCellWidth(postContentPanel, "100%");
		
		DOM.setStyleAttribute(postContentFlowPanel.getElement(), "margin", "5px");
		DOM.setStyleAttribute(imagePanel.getElement(), "margin", "7px");
		setTime(getEntity());
		
		final ImageField responsesImageField = new ImageField();
		responsesImageField.setConfiguration(getImageFieldConfiguration("images/dropDownIcon.png"));
		try {
			responsesImageField.createField();
		} catch (AppOpsException e) {
			 e.printStackTrace();
		}
		spaceIconPlusTimePanel.add(responsesImageField);
		//spaceIconPlusTimePanel.setCellWidth(responsesImageField, "40%");
		spaceIconPlusTimePanel.setCellHorizontalAlignment(responsesImageField, HasHorizontalAlignment.ALIGN_RIGHT);
		spaceIconPlusTimePanel.setCellVerticalAlignment(responsesImageField, HasVerticalAlignment.ALIGN_MIDDLE);
		//DOM.setStyleAttribute(responsesImageField.getElement(), "margin", "5px");
		
		responsesImageField.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				/*int eventX = event.getNativeEvent().getClientX();
				int eventY = event.getNativeEvent().getClientY();*/
				ImageField img = (ImageField) event.getSource();
				if(img.equals(responsesImageField)){
					int eventX = img.getAbsoluteLeft()+10;
					int eventY = img.getAbsoluteTop()+5;
					getResponsesForWidget(eventX, eventY);
				}
			}
		});
		
		PostInButton postButton = createPostButtonAndReturn();
		
		postButton.setStylePrimaryName("postInBtn");
		
		postSnippetPanel.add(postButton,DockPanel.EAST);
		postSnippetPanel.setCellVerticalAlignment(postButton, ALIGN_TOP);
		
		add(postSnippetPanel,DockPanel.CENTER);
		
		postSnippetPanel.setStylePrimaryName(POST_SNIPPET);
		imagePanel.setStylePrimaryName(POST_USER_IMAGE);
		postContentFlowPanel.setStylePrimaryName(POST_CONTEXT_LABEL);
		responsesImageField.setStylePrimaryName(POST_RESPONSE_IMAGE);
		//spaceImageField.setStylePrimaryName(HAND_CSS);
		showEmbededEntityDetailsInSnippet(getEntity());
		
	}
	
	private PostInButton createPostButtonAndReturn() {
		
		PostInButton postInButton = new PostInButton(getEntity());
		Entity postEntity = getEntity();
		
		Byte postAlreadyIn = postEntity.getPropertyByName(PostConstant.ALREADYIN);

		if(postAlreadyIn !=null && postAlreadyIn == 1)
			postInButton.createOutButton();
		else
			postInButton.createInButton();

		return postInButton;
	}
	
	@SuppressWarnings("unchecked")
	private void getResponsesForWidget(final int eventX, final int eventY) {
		Query query = new Query();
		query.setQueryName("getResponsesForWidgetId");
		//query.setListSize(4);
		
		String widgetId = getEntity().getPropertyByName(PostConstant.ACTION_ID).toString();
		
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("widgetid", widgetId);
		query.setQueryParameterMap(paramMap);
		
		Map parameters = new HashMap();
		parameters.put("query", query);
		
		StandardAction action = new StandardAction(EntityList.class, "coreplatform.CorePlatformService.getEntityList", parameters);
		dispatch.execute(action, new AsyncCallback<Result>() {
			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}
			@Override
			public void onSuccess(Result result) {
				responseoptionList.clear();
				EntityList responseEntList = (EntityList) result.getOperationResult();
				for (Entity entity : responseEntList) {
					/*** nitish@ensarm.com.. Adding entity to the list rather than the responsename**/
					responseoptionList.add(entity);
				}
				createResponsePopup(eventX, eventY);
			}
		});
	}
	
	private void createResponsePopup(int eventX, int eventY) {
		final PopupPanel popupPanel = new PopupPanel(true);
		popupPanel.setPopupPosition(eventX, eventY);
		popupPanel.setStylePrimaryName("responsePopupPanel");
		
		VerticalPanel mainPanel = new VerticalPanel();
		mainPanel.setSpacing(5);
		int size = responseoptionList.size();
		for(final Entity responseEntity : responseoptionList){
			final String responseText = responseEntity.getPropertyByName(ActionResponseViewConstant.RESPONSE_NAME);
			final ActionWidget actionWidget = new ActionWidget(ActionWidgetType.LINK);
			actionWidget.setWidgetText(responseText);
			actionWidget.setActionEntity(responseEntity);
			
			if(size-1 !=0){
				actionWidget.setConfiguration(getActionLinkConfiguration("responseLbl", null));
			} else {
				actionWidget.setConfiguration(getActionLinkConfiguration("responseLblLast", null));
			}
			actionWidget.createUi();

			mainPanel.add(actionWidget);
			//mainPanel.add(new HTML("<hr style=\"color: #848181; background-color: #848181; width: 98%; height: 1px;\"></hr>"));
			size--;
			
			actionWidget.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					boolean isWidget = (Boolean) responseEntity.getProperty(ActionResponseViewConstant.IS_WIDGET).getValue();
					if(isWidget) {
						ActionEvent actionEvent = getActionEvent(actionWidget);
						actionWidget.fireEvent(actionEvent);
						popupPanel.hide();
					} else {
						HandlerFactory handFactory = GWT.create(HandlerFactoryImpl.class);
						
						ResponseActionHandler handler = handFactory.getActionHandlerByName(responseText);
						handler.setEmbeddedEntity(getEmbeddedEntity());
						handler.setPostEntity(getEntity());
						handler.setResponseEntity(responseEntity);
						handler.executeResponse(new EntityReceiver() {
							
							@Override
							public void onEntityUpdated(Entity entity) {
								popupPanel.hide();
							}
							
							@Override
							public void onEntityReceived(Entity entity) {
								
							}
							
							@Override
							public void noMoreData() {
								
							}
						});
					}
				}
			});
		}
		popupPanel.add(mainPanel);
		popupPanel.show();
	}
	
	private Configuration getImageFieldConfiguration(String url) {
	    Configuration configuration = new Configuration();
	    configuration.setPropertyByName(ImageField.IMAGEFIELD_BLOBID,url);
		return configuration;
	}
	
	private void setTime(Entity ent) {
		timeLbl = new LabelField();
		timeLbl.setConfiguration(createConfiguration(true));
		try {
			timeLbl.createField();
		} catch (AppOpsException e) {
			e.printStackTrace();
		}
		final Date timeStamp = (Date) ent.getProperty(PostConstant.CREATEDON).getValue();
		
		Timer timer = new Timer() {
			
			
			public void run() {
				String timeAgo = calculateTimeAgo(timeStamp);
				timeLbl.setText(timeAgo);
				
			}
		};
		timer.run();
		timer.scheduleRepeating(5000);
		
		DateTimeFormat dateFormat = DateTimeFormat.getFormat("dd MMM yyyy h:mm");	
		String date = dateFormat.format(timeStamp);
		

		
		timeLbl.setTitle(date);
		spaceIconPlusTimePanel.add(timeLbl);
		postContentPanel.add(spaceIconPlusTimePanel);
		//spaceIconPlusTimePanel.setCellWidth(timeLbl, "30%");
		
		DOM.setStyleAttribute(timeLbl.getElement(), "margin", "5px");
		timeLbl.setStylePrimaryName(POST_TIME_LABEL);
	}
	
	private String calculateTimeAgo(Date activitydate){
		Date currDate = new Date();
		long diffInSec = (currDate.getTime() - activitydate.getTime()) / 1000;
		long second = diffInSec % 60;
	    diffInSec/= 60;
	    long minute = diffInSec % 60;
	    diffInSec /= 60;
	    long hour = diffInSec % 24;
	    diffInSec /= 24;
	    long day = diffInSec;
	    
	    String str = "";
	    if(day != 0){
	    	str = day > 1 ? " days " : " day ";
	    	return Long.toString(day) + str + "ago";
	    } else if(hour != 0){
	    	str = hour > 1 ? " hours " : " hour ";
	    	return Long.toString(hour) + str + "ago";
	    } else if(minute != 0){
	    	str = minute > 1 ? " minutes " : " minute ";
	    	return Long.toString(minute) + str + "ago";
	    } else{
	    	str = second > 1 ? " secs " : " sec ";
	    	return Long.toString(second) + str + "ago";
	    }
	}
	
	
	public Configuration createConfiguration(boolean wordWrap){
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(LabelField.LABELFIELD_WORDWRAP, wordWrap);
		return configuration;
		
	}
	
	private Configuration getActionLinkConfiguration(String primaryCss, String dependentCss) {
		Configuration conf = new Configuration();
		conf.setPropertyByName(ActionWidgetConfiguration.PRIMARY_CSS.toString(), primaryCss);
		return conf;
	}
	
	protected Entity getEmbeddedEntity() {
		JsonProperty embeddedEntityJson = (JsonProperty) getEntity().getProperty("embeddedEntity");
		String jsonString = embeddedEntityJson.getJsonString();

		JSONValue jsonVal = JSONParser.parseLenient(jsonString);
		JSONObject jsonObj = new JSONObject(jsonVal.isObject().getJavaScriptObject());

		Entity embeddedEntity = new JsonToEntityConverter().getConvertedEntity(jsonObj);
		return embeddedEntity;
	}
	
	private void showEmbededEntityDetailsInSnippet(Entity postEnt){
		
		JsonProperty jsonProp = (JsonProperty) postEnt.getProperty("embeddedEntity");
		
		String jsonEmbededString  = jsonProp.getJsonString();
		
		JsonToEntityConverter jsonToEntityConverter = new JsonToEntityConverter();
		
		Entity embeddedEntity = jsonToEntityConverter.convertjsonStringToEntity(jsonEmbededString);
		
		Label detailLbl = new Label();
		
		if(embeddedEntity.getPropertyByName("title") != null){
			detailLbl.setText(embeddedEntity.getPropertyByName("title").toString());
		}else if(embeddedEntity.getPropertyByName("name") != null){
			detailLbl.setText(embeddedEntity.getPropertyByName("name").toString());
			
		}
		
		detailLbl.setStylePrimaryName("blockquote");
		
		if(detailLbl.getText().equals("") ||detailLbl.getText().equals(" ")){
			
		}else{
			postContentPanel.add(detailLbl);
			
			postContentPanel.setCellHorizontalAlignment(detailLbl, HasHorizontalAlignment.ALIGN_LEFT);
		}
		
		
	}
	
	private ActionEvent getActionEvent(ActionWidget actionWidget) {
		
		InitiateActionContext context = new InitiateActionContext();
		context.setType(new MetaType("ActionContext"));
		
		context.setSpace(AppEnviornment.getCurrentSpace());
		
		if(actionWidget.getActionEntity() != null){
			context.setActionEntity(actionWidget.getActionEntity());
		}
		
		JSONObject token = EntityToJsonClientConvertor.createJsonFromEntity(context);
		
		ActionEvent actionEvent = new ActionEvent();
		actionEvent.setEventType(ActionEvent.TRANSFORMWIDGET);			
		actionEvent.setEventData(token.toString());
		
		return actionEvent;
	}
}

package in.appops.client.common.snippet;

import in.appops.client.common.fields.ImageField;
import in.appops.client.common.fields.LabelField;
import in.appops.client.common.util.BlobDownloader;
import in.appops.client.common.util.PostContentParser;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Property;
import in.appops.platform.core.entity.query.Query;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;
import in.appops.platform.core.util.EntityList;
import in.appops.platform.server.core.services.platform.coreplatformservice.constant.WidgetResponseConstant;
import in.appops.platform.server.core.services.social.constant.PostConstant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.dom.client.Style.VerticalAlign;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
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

public class PostViewSnippet extends Snippet {

	private VerticalPanel basePanel = new VerticalPanel();
	private DockPanel dockPanel;
	private LabelField timeLbl;
	private VerticalPanel postContentPanel;
	private HorizontalPanel spaceIconPlusTimePanel ;
	
	public static final String POST_SNIPPET = "postSnippet";
	public static final String POST_TIME_LABEL = "postTimeLabel";
	public static final String POST_USER_IMAGE = "postUserImage";
	public static final String POST_CONTEXT_LABEL = "postContextLabel";
	public static final String HAND_CSS = "handCss";
	
	private final DefaultExceptionHandler exceptionHandler = new DefaultExceptionHandler();
	private final DispatchAsync	dispatch = new StandardDispatchAsync(exceptionHandler);
	private ArrayList<String> responseoptionList = null;
	
	public PostViewSnippet() {
		initWidget(basePanel);
		basePanel.setHeight("100%");
		basePanel.setWidth("100%");
		
		
	}
	
	@Override
	public void initialize(){
		responseoptionList = new ArrayList<String>();
		dockPanel = new DockPanel();
		createUi();
	}
	
	public void createUi(){
		
		
		HorizontalPanel imagePanel = new HorizontalPanel();
		 postContentPanel = new VerticalPanel();
		 spaceIconPlusTimePanel = new HorizontalPanel();
		final ImageField imageField = new ImageField();
				
		BlobDownloader blobDownloader = new BlobDownloader();
		//TODO currently all this values getting from dummy post entity need to modify it in future
		Property<Serializable> property=(Property<Serializable>) entity.getProperty(PostConstant.CREATEDBY);
		final Entity userEntity=(Entity) property.getValue();
		String blobUrl=blobDownloader.getIconDownloadURL(userEntity.getPropertyByName("imgBlobId").toString());
		
		imageField.setConfiguration(getImageFieldConfiguration(blobUrl));
		try {
			imageField.createField();
		} catch (AppOpsException e) {
			 e.printStackTrace();
		}
		
		imagePanel.add(imageField);
		imagePanel.setBorderWidth(1);
		dockPanel.add(imagePanel, DockPanel.WEST);
		dockPanel.setCellWidth(imagePanel, "15%");
		
		imagePanel.setCellVerticalAlignment(imageField, HasVerticalAlignment.ALIGN_MIDDLE);
		
	    imageField.addMouseOverHandler(new MouseOverHandler() {
			
			@Override
			public void onMouseOver(MouseOverEvent event) {
				imageField.setAltText(userEntity.getPropertyByName("username").toString());
				
			}
		});
		
		
		
	/*	LabelField postContentLabel = new LabelField();
		postContentLabel.setConfiguration(createConfiguration(true));
		
		
		try {
			postContentLabel.createField();
		} catch (AppOpsException e) {
			 e.printStackTrace();
		}*/
		
		String xmlContent = entity.getPropertyByName(PostConstant.CONTENT).toString();
		PostContentParser postContentParser = new PostContentParser();
		FlowPanel postContentFlowPanel = postContentParser.getGeneralMsgComponent(xmlContent);
		
		//ImageField spaceImageField = new ImageField();
		
		
		//TODO currently all this values getting from dummy post entity need to modify it in future
		/*Property<Serializable> property1=(Property<Serializable>) entity.getProperty("User");
		Entity userEntity1=(Entity) property.getValue();
		String blobUrl1=blobDownloader.getIconDownloadURL(userEntity.getPropertyByName(MediaConstant.BLOBID).toString());*/
		
		/*spaceImageField.setConfiguration(getImageFieldConfiguration(blobUrl1));
		try {
			spaceImageField.createField();
		} catch (AppOpsException e) {
			 e.printStackTrace();
		}
		
		spaceImageField.setPixelSize(16,16);*/
		
		//spaceIconPlusTimePanel.add(spaceImageField);
		postContentPanel.add(spaceIconPlusTimePanel);
		postContentPanel.setCellWidth(spaceIconPlusTimePanel, "10%");
		//spaceIconPlusTimePanel.setCellWidth(spaceImageField, "5%");
		
		postContentPanel.add(postContentFlowPanel);
		postContentPanel.setCellVerticalAlignment(postContentFlowPanel, HasVerticalAlignment.ALIGN_MIDDLE);
		postContentPanel.setCellHorizontalAlignment(postContentFlowPanel, HasHorizontalAlignment.ALIGN_JUSTIFY);
		//postContentPanel.setBorderWidth(1);
		postContentPanel.setHeight("100%");
		postContentPanel.setWidth("90%");
		spaceIconPlusTimePanel.setWidth("100%");
		dockPanel.add(postContentPanel, DockPanel.CENTER);
		dockPanel.setCellWidth(postContentPanel, "90%");
		
		DOM.setStyleAttribute(postContentFlowPanel.getElement(), "margin", "5px");
		DOM.setStyleAttribute(imagePanel.getElement(), "margin", "7px");
		//DOM.setStyleAttribute(spaceImageField.getElement(), "margin", "5px");
		setTime(entity);
		
		final ImageField responsesImageField = new ImageField();
		responsesImageField.setConfiguration(getImageFieldConfiguration("images/dropDownIcon.png"));
		try {
			responsesImageField.createField();
		} catch (AppOpsException e) {
			 e.printStackTrace();
		}
		spaceIconPlusTimePanel.add(responsesImageField);
		spaceIconPlusTimePanel.setCellWidth(responsesImageField, "40%");
		spaceIconPlusTimePanel.setCellHorizontalAlignment(responsesImageField, HasHorizontalAlignment.ALIGN_RIGHT);
		spaceIconPlusTimePanel.setCellVerticalAlignment(responsesImageField, HasVerticalAlignment.ALIGN_MIDDLE);
		DOM.setStyleAttribute(responsesImageField.getElement(), "margin", "5px");
		
		responsesImageField.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				/*int eventX = event.getNativeEvent().getClientX();
				int eventY = event.getNativeEvent().getClientY();*/
				ImageField img = (ImageField) event.getSource();
				if(img.equals(responsesImageField)){
					int eventX = img.getAbsoluteLeft();
					int eventY = img.getAbsoluteTop();
					getResponsesForWidget(eventX, eventY);
				}
			}
		});
		
		dockPanel.setHeight("100%");
		dockPanel.setWidth("100%");
		
		basePanel.add(dockPanel);
		basePanel.setCellHorizontalAlignment(dockPanel, HasHorizontalAlignment.ALIGN_CENTER);
		basePanel.setCellVerticalAlignment(dockPanel, HasVerticalAlignment.ALIGN_MIDDLE);
		
		dockPanel.setStylePrimaryName(POST_SNIPPET);
		imagePanel.setStylePrimaryName(POST_USER_IMAGE);
		postContentFlowPanel.setStylePrimaryName(POST_CONTEXT_LABEL);
		responsesImageField.setStylePrimaryName(HAND_CSS);
		//spaceImageField.setStylePrimaryName(HAND_CSS);
		
		
	}
	
	@SuppressWarnings("unchecked")
	private void getResponsesForWidget(final int eventX, final int eventY) {
		Query query = new Query();
		query.setQueryName("getResponsesForWidgetId");
		//query.setListSize(4);
		
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("widgetid", 5L);	//TODO hardcoded widget id..
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
				EntityList responseEntList = (EntityList) result.getOperationResult();
				for (Entity entity : responseEntList) {
					String option = entity.getPropertyByName(WidgetResponseConstant.WIDGETRESPONSE);
					if(!responseoptionList.contains(option)){
						responseoptionList.add(option);
					}
				}
				createResponsePopup(eventX, eventY);
			}
		});
	}
	
	private void createResponsePopup(int eventX, int eventY) {
		PopupPanel popupPanel = new PopupPanel(true);
		popupPanel.setPopupPosition(eventX, eventY);
		popupPanel.setStylePrimaryName("responsePopupPanel");
		
		VerticalPanel mainPanel = new VerticalPanel();
		mainPanel.setSpacing(5);
		int size = responseoptionList.size();
		for(String responseText : responseoptionList){
			Label label = new Label(responseText);
			if(size-1!=0)
				label.setStylePrimaryName("responseLbl");
			else
				label.setStylePrimaryName("responseLblLast");
			mainPanel.add(label);
			//mainPanel.add(new HTML("<hr style=\"color: #848181; background-color: #848181; width: 98%; height: 1px;\"></hr>"));
			size--;
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
		spaceIconPlusTimePanel.setCellWidth(timeLbl, "30%");
		
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
	
	/*public void createDummyEntity(){
		entity = new Entity();
		entity.setPropertyByName(PostConstant.CONTENT, "Dinner tonight! at Malaka Spice :)");
		Entity userEntity = new Entity();
		userEntity.setPropertyByName(UserConstants.NAME, "Dhananjay Patil");
		userEntity.setPropertyByName(MediaConstant.BLOBID, "irqSN52SzHwHksn9NQFKxNFAd%2BaWFzr54FsUNUp8GW35LpQdoFtM%2BQ%3D%3D");
		Property<Serializable> property = new Property<Serializable>();
		property.setName("User");
		property.setValue(userEntity);
		entity.setProperty("User",property);
		Date date = new Date();
		entity.setPropertyByName(PostConstant.CREATEDON, date);
	
		
		
	}*/
}

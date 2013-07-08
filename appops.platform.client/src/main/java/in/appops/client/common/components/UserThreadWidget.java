package in.appops.client.common.components;

import in.appops.client.common.contactmodel.ContactSnippet;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.client.common.fields.ImageField;
import in.appops.client.common.fields.LabelField;
import in.appops.client.common.gin.AppOpsGinjector;
import in.appops.client.common.snippet.SnippetConstant;
import in.appops.client.common.snippet.SnippetFactory;
import in.appops.client.common.util.AppEnviornment;
import in.appops.client.common.util.BlobDownloader;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.core.constants.propertyconstants.SpaceConstants;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;
import in.appops.platform.core.util.EntityList;
import in.appops.platform.server.core.services.contact.constant.ContactConstant;

import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class UserThreadWidget extends Composite implements EventListener,ClickHandler{

	private VerticalPanel mainPanel;
	private ScrollPanel panel;
	private Entity userEntity;
	private final AppOpsGinjector injector = GWT.create(AppOpsGinjector.class);
	private FieldEventHandler handler;
	public UserThreadWidget() {
		initialize();
		initWidget(panel);
		mainPanel.setWidth("250px");
		mainPanel.setHeight("100%");
		//fetchAllMessageUserParticipantsAndSender();
		
	}

	private void initialize() {
		mainPanel = new VerticalPanel();
		panel = new ScrollPanel(mainPanel);
		panel.setStylePrimaryName("userThreadScrollPanel");
	}
	
		
	private void createUserSnippet(EntityList list) {
		SnippetFactory snippetFactory = injector.getSnippetFactory();
		for(final Entity entity:list){
		   ContactSnippet snippet = (ContactSnippet)snippetFactory.getSnippetByEntityType(null, SnippetConstant.CONTACTSNIPPET);
		    HorizontalPanel horizontalPanel = new HorizontalPanel();
			snippet.setSelectionAllowed(false);
			
			snippet.setWidth("100%");
			BlobDownloader downloader = new BlobDownloader();
			Configuration imageConfig =null;
			if(entity.getPropertyByName(ContactConstant.IMGBLOBID)!=null){
				String blobId = entity.getPropertyByName(ContactConstant.IMGBLOBID).toString();
				String url = downloader.getIconDownloadURL(blobId);
				imageConfig = getImageFieldConfiguration(url, "defaultIcon","defaultIcon_Medium");
			}else{
				imageConfig = getImageFieldConfiguration("images/default_Icon.png", "defaultIcon","defaultIcon_Medium");
			}
			
			Configuration labelConfig = getLabelFieldConfiguration(true, "flowPanelContent", null, null);
			snippet.setConfigurationForFields(labelConfig, imageConfig);
			snippet.initialize(entity);
			snippet.setStylePrimaryName("flowPanelContent");
			snippet.setStyleName("crossImageCss");
			snippet.addStyleName("outlineCss");
			
			ClickHandler handler = new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					FieldEvent fieldEvent = new FieldEvent();
					fieldEvent.setEventType(FieldEvent.SUGGESTION_CLICKED);			
					fieldEvent.setEventData(entity);	
					//AppUtils.EVENT_BUS.fireEventFromSource(fieldEvent, this);
					AppUtils.EVENT_BUS.fireEvent(fieldEvent);
					
				}
			};
			
			snippet.addDomHandler(handler, ClickEvent.getType());
			
			//mainPanel.setStylePrimaryName("userThreadWidget");
			horizontalPanel.add(snippet);
			mainPanel.add(horizontalPanel);
			//horizontalPanel.setStylePrimaryName("snippetPanel");
			horizontalPanel.setStylePrimaryName("snippetPanelForUser");
			
		}
	}
	
	@SuppressWarnings("unchecked")
	public void fetchAllMessageUserParticipantsAndSender() {
		mainPanel.clear();
		HorizontalPanel horizontalPanel=createLoaderWithTextWidget();
		mainPanel.add(horizontalPanel);
		mainPanel.setCellVerticalAlignment(horizontalPanel, HasVerticalAlignment.ALIGN_MIDDLE);
		mainPanel.setCellHorizontalAlignment(horizontalPanel, HasHorizontalAlignment.ALIGN_CENTER);
		DefaultExceptionHandler	exceptionHandler	= new DefaultExceptionHandler();
		DispatchAsync				dispatch			= new StandardDispatchAsync(exceptionHandler);

		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userEntity",userEntity);
		Long spaceId = null;
		if(AppEnviornment.getCurrentSpace()!=null){
			Entity spaceEnt = AppEnviornment.getCurrentSpace();
			spaceId = ((Key<Long>)spaceEnt.getPropertyByName(SpaceConstants.ID)).getKeyValue();
		}
		paramMap.put("spaceId", spaceId);
		
		StandardAction action = new StandardAction(EntityList.class, "usermessage.UserMessageService.getMessageParticipantsWithSender", paramMap);
		dispatch.execute(action, new AsyncCallback<Result<EntityList>>() {
			
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}
			
			public void onSuccess(Result<EntityList> result) {
				if(result!=null){
				   EntityList  list=result.getOperationResult();
				   if(list!=null){
					  if(list.size() > 0) {
						mainPanel.clear();
						createUserSnippet(list);  
					  } else {
						  try {
							  mainPanel.clear();
							  LabelField labelField = new LabelField();
							  Configuration labelConfig = getLabelFieldConfiguration(true, "flowPanelContent", null, null);
							  labelField.setFieldValue("No contacts");
							  labelField.setConfiguration(labelConfig);
							  labelField.createField();
							  mainPanel.add(labelField);
							  mainPanel.setCellVerticalAlignment(labelField, HasVerticalAlignment.ALIGN_MIDDLE);
							  mainPanel.setCellHorizontalAlignment(labelField, HasHorizontalAlignment.ALIGN_CENTER);
						} catch (AppOpsException e) {
							e.printStackTrace();
						}
					  }
				   }
				}
			}

			
		});
		
	}
	
	private HorizontalPanel createLoaderWithTextWidget() {
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		/*Configuration imageConfig = getImageFieldConfiguration("images/loader.gif", "defaultIcon_Medium");
		 ImageField imageField = new ImageField();*/
			
			////imageField.setConfiguration(imageConfig);
	        try {
				//imageField.createField();
				
				LabelField labelField = new LabelField();
				Configuration labelConfig = getLabelFieldConfiguration(true, "flowPanelContent", null, null);
								
					labelField.setFieldValue("Loading contacts ...");
				
				labelField.setConfiguration(labelConfig);
				labelField.createField();
				
			//	horizontalPanel.add(imageField);
				horizontalPanel.add(labelField);
				
			} catch (AppOpsException e) {
				
				e.printStackTrace();
			}
		return horizontalPanel;
		
	}

	public Configuration getImageFieldConfiguration(String url, String primaryCSS, String secondaryCss) {
		Configuration config = new Configuration();
		config.setPropertyByName(ImageField.IMAGEFIELD_BLOBID, url);
		config.setPropertyByName(ImageField.IMAGEFIELD_PRIMARYCSS, primaryCSS);
		config.setPropertyByName(ImageField.IMAGEFIELD_DEPENDENTCSS, secondaryCss);
		return config;
	}
	
	public Configuration getLabelFieldConfiguration(boolean allowWordWrap, String primaryCss, String secondaryCss, String debugId) {
		Configuration config = new Configuration();
		config.setPropertyByName(LabelField.LABELFIELD_WORDWRAP, allowWordWrap);
		config.setPropertyByName(LabelField.LABELFIELD_PRIMARYCSS, primaryCss);
		config.setPropertyByName(LabelField.LABELFIELD_DEPENDENTCSS, secondaryCss);
		config.setPropertyByName(LabelField.LABELFIELD_DEBUGID, debugId);
		return config;
	}

	public Entity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(Entity userEntity) {
		this.userEntity = userEntity;
	}

	public FieldEventHandler getHandler() {
		return handler;
	}

	public void setHandler(FieldEventHandler handler) {
		this.handler = handler;
	}
	public void addFieldEventHandler(FieldEventHandler handler){
		//AppUtils.EVENT_BUS.addHandlerToSource(FieldEvent.TYPE, this, handler);
		AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE,  handler);
	}
	@Override
	public void onClick(ClickEvent event) {
		// TODO Auto-generated method stub
		
	}
}

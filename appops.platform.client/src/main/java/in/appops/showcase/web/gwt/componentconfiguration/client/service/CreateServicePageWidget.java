package in.appops.showcase.web.gwt.componentconfiguration.client.service;

import in.appops.client.common.config.field.ButtonField;
import in.appops.client.common.config.field.ButtonField.ButtonFieldConstant;
import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.client.common.config.field.media.MediaField;
import in.appops.client.common.config.field.media.MediaField.MediaFieldConstant;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.AttachmentEvent;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.AttachmentEventHandler;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.client.common.fields.TextField;
import in.appops.client.common.fields.TextField.TextFieldConstant;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.core.constants.typeconstants.TypeConstants;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.type.MetaType;
import in.appops.platform.core.operation.ResponseActionContext;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.EntityList;
import in.appops.platform.server.core.services.platform.coreplatformservice.constant.ServiceConstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CreateServicePageWidget extends Composite implements FieldEventHandler,AttachmentEventHandler{

	private VerticalPanel basePanel;
	private FlexTable createServiceFlexTable;
	private HorizontalPanel errorlabelHorizontalPanel;
	private HorizontalPanel mediaPanel;
	private TextField nameTextField;
	private TextField versionTextField;
	private ButtonField createServiceBtnField;
	public static String SERVICENAME_STRINGFIELD_ID = "appNameStringFieldId";
	public static String CREATESERVICE_BTN_ID = "saveService";
	public static String CREATESERVICEBTNCSS = "createAppBtn";
	public static String CREATEAPPLABELSCSS = "createAppWidgetLabels";
	private final String POPUPGLASSPANELCSS = "popupGlassPanel";
	private final String POPUP_CSS = "popupCss";
	private final String POPUP_LBL_PCLS = "popupLbl";
	private final String SERVICENAME_TEXTFIELDCSS="serviceNameTextField";
	private final String SERVCEVERSION_TEXTFIELDCSS="serviceVersionTextField";
	private String serviceBlobId;
	 
	public CreateServicePageWidget() {
		initialise();
	}
	
	public void initialise(){
		basePanel = new VerticalPanel();
		createServiceFlexTable = new FlexTable();
		errorlabelHorizontalPanel = new HorizontalPanel();
		mediaPanel = new HorizontalPanel();
		initWidget(basePanel);
		AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE, this);
	}
	
	public void createUi(){
		try{
			
			 LabelField serviceNameLabelField = new LabelField();
			 serviceNameLabelField.setConfiguration(getLabelFieldConfiguration("Service name :"));
			 serviceNameLabelField.configure();
			 serviceNameLabelField.create();
			
			 nameTextField = new TextField();
			 nameTextField.setConfiguration(getServiceNameFieldConfiguration());
			 nameTextField.configure();
			 nameTextField.create();
			 
			 LabelField serviceVersionLabelField = new LabelField();
			 serviceVersionLabelField.setConfiguration(getLabelFieldConfiguration("Service version :"));
			 serviceVersionLabelField.configure();
			 serviceVersionLabelField.create();
			 
			 versionTextField = new TextField();
			 versionTextField.setConfiguration(getServiceVersionFieldConfiguration());
			 versionTextField.configure();
			 versionTextField.create();
			 
			 LabelField serviceBlobIdLabelField = new LabelField();
			 serviceBlobIdLabelField.setConfiguration(getLabelFieldConfiguration("Service blobId :"));
			 serviceBlobIdLabelField.configure();
			 serviceBlobIdLabelField.create();
			 
			 
			 createServiceBtnField = new ButtonField();
			 createServiceBtnField.setConfiguration(getCreateServiceBtnConfiguration());
			 createServiceBtnField.configure();
			 createServiceBtnField.create();
			 
			 createMediaField();
			 LabelField loadingLabelField = new LabelField();
			 loadingLabelField.setConfiguration(getLabelFieldConfiguration("Loading..."));
			 loadingLabelField.configure();
			 loadingLabelField.create();
			 mediaPanel.add(loadingLabelField);
			 
			 createServiceFlexTable.setWidget(1, 0, serviceNameLabelField);
			 createServiceFlexTable.setWidget(1, 2, nameTextField);
			 
			 
			 createServiceFlexTable.setWidget(3, 0, serviceVersionLabelField);
			 createServiceFlexTable.setWidget(3, 2, versionTextField);
			
			 
			 createServiceFlexTable.setWidget(4, 0, serviceBlobIdLabelField);
			 createServiceFlexTable.setWidget(4, 2, mediaPanel);
			
			 basePanel.add(createServiceFlexTable);
			 basePanel.setCellHorizontalAlignment(createServiceFlexTable, HasHorizontalAlignment.ALIGN_CENTER);
			 basePanel.setCellVerticalAlignment(createServiceFlexTable, HasVerticalAlignment.ALIGN_MIDDLE);
			 
			 basePanel.add(createServiceBtnField);
			 basePanel.setCellHorizontalAlignment(createServiceBtnField, HasHorizontalAlignment.ALIGN_RIGHT);
			 basePanel.setCellVerticalAlignment(createServiceBtnField, HasVerticalAlignment.ALIGN_BOTTOM);
			 basePanel.setWidth("100%");
			 AppUtils.EVENT_BUS.addHandler(AttachmentEvent.TYPE, this);
			 
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void createMediaField() {
				
		try{
		Map parameters = new HashMap();
		parameters.put("emailId", "pallavi@ensarm.com");
		parameters.put("password", "pallavi123");
		
		StandardAction action = new StandardAction(EntityList.class, "useraccount.LoginService.validateUser", parameters);
		
		DefaultExceptionHandler exceptionHandler = new DefaultExceptionHandler();
		DispatchAsync	dispatch = new StandardDispatchAsync(exceptionHandler);

		ResponseActionContext actionContext = new ResponseActionContext();
		actionContext.setEmbeddedAction(action);
		
		dispatch.executeContextAction(actionContext, new AsyncCallback<Result>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(Result result) {
				mediaPanel.clear();
				MediaField mediaField = new MediaField();
				mediaField.setConfiguration(getMediaFieldConfiguration());
				mediaField.configure();
				mediaField.create();
				mediaPanel.add(mediaField);
			}
		});
	} catch (Exception e) {
		e.printStackTrace();
	}
		
		
	}

	private Configuration getMediaFieldConfiguration() {
	
	Configuration configuration = new Configuration();
	try {
		
		configuration.setPropertyByName(MediaFieldConstant.MF_MEDIAIMG_BLOB, "images/Media.png");
		configuration.setPropertyByName(MediaFieldConstant.MF_MEDIAIMG_PCLS, "mediaImage");
		configuration.setPropertyByName(MediaFieldConstant.MF_MEDIAIMG_DCLS, "fadeInUp");
		configuration.setPropertyByName(MediaFieldConstant.MF_ISPROFILE_IMG, true);
		configuration.setPropertyByName(MediaFieldConstant.MF_FILEUPLOADER_CLS, "appops-webMediaAttachment");
		
		ArrayList<String> extensions = new ArrayList<String>();
		extensions.add("jpg");
		extensions.add("jpeg");
		extensions.add("gif");
		extensions.add("png");
		configuration.setPropertyByName(MediaFieldConstant.MF_VALIDEXTEXNSION_LIST, extensions);
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	return configuration;
}
	private Configuration getCreateServiceBtnConfiguration() {
		Configuration configuration = new Configuration();
		try {
			configuration.setPropertyByName(ButtonFieldConstant.BTNFD_DISPLAYTEXT, "Create a service");
			configuration.setPropertyByName(ButtonFieldConstant.BF_PCLS,CREATESERVICEBTNCSS);
			configuration.setPropertyByName(ButtonFieldConstant.BF_ID, CREATESERVICE_BTN_ID);
		} catch (Exception e) {
			
		}
		return configuration;
	}

	private Configuration getServiceVersionFieldConfiguration() {
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(TextFieldConstant.TF_TYPE, TextFieldConstant.TFTYPE_TXTBOX);
		
		configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_POS, TextFieldConstant.BF_SUGGESTION_INLINE);
		configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_TEXT, "Enter service version");
		
		configuration.setPropertyByName(TextFieldConstant.BF_ID, SERVICENAME_STRINGFIELD_ID);
		configuration.setPropertyByName(TextFieldConstant.TF_MAXLENGTH, 55);
		configuration.setPropertyByName(TextFieldConstant.BF_PCLS, SERVCEVERSION_TEXTFIELDCSS);
		//configuration.setPropertyByName(TextFieldConstant.BF_PCLS, VALUEFIELD_PCLS);
		
		return configuration;
	}

	private Configuration getLabelFieldConfiguration(String displayText) {
		Configuration configuration = null;	
		try{
			
			configuration = new Configuration();
			
			configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, displayText);
			configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, CREATEAPPLABELSCSS);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return configuration;
	}

	private Configuration getServiceNameFieldConfiguration() {
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(TextFieldConstant.TF_TYPE, TextFieldConstant.TFTYPE_TXTBOX);
		
		configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_POS, TextFieldConstant.BF_SUGGESTION_INLINE);
		configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_TEXT, "Enter service name");
		
		configuration.setPropertyByName(TextFieldConstant.BF_ID, SERVICENAME_STRINGFIELD_ID);
		configuration.setPropertyByName(TextFieldConstant.BF_PCLS, SERVICENAME_TEXTFIELDCSS);
		//configuration.setPropertyByName(TextFieldConstant.TF_MAXLENGTH, 55);
		
		//configuration.setPropertyByName(TextFieldConstant.BF_PCLS, VALUEFIELD_PCLS);
		
		return configuration;
	}

	@Override
	public void onFieldEvent(FieldEvent event) {
		try{
		Object srcObject = event.getEventSource();
		int eventType= event.getEventType();
		switch(eventType){
		case FieldEvent.CLICKED:{
			if(srcObject instanceof ButtonField){
				ButtonField buttonField = (ButtonField) srcObject;
				if(buttonField.getBaseFieldId().equals(CREATESERVICE_BTN_ID)){
					Entity serviceEntity = createServiceDataEntity();
					if(serviceEntity!=null){
					 saveServiceData(serviceEntity);
					}
				}
			}
				
		}
		
		
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void saveServiceData(Entity serviceEntity) {
		DefaultExceptionHandler defaultExceptionHandler = new DefaultExceptionHandler();
		DispatchAsync dispatchAsync = new StandardDispatchAsync(defaultExceptionHandler);
		
		Map parameters = new HashMap();
		parameters.put("serviceEnt", serviceEntity);
		
		StandardAction action = new StandardAction(Entity.class, "appdefinition.AppDefinitionService.createService", parameters);
		
		dispatchAsync.execute(action, new AsyncCallback<Result<Entity>>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
				
			}

			@Override
			public void onSuccess(Result<Entity> result) {
				if(result!=null){
					Entity entity = result.getOperationResult();
					showNotification("Service created successfully..");
				}else{
					showNotification("Service not created successfully..");
				}
				
			}
			
		});

			
	}

	private void showNotification(String messageStr) {
		try {
			LabelField popupLbl = new LabelField();
			popupLbl.setConfiguration(getNotificationLabelFieldConf(messageStr,POPUP_LBL_PCLS,null,null));
			popupLbl.configure();
			popupLbl.create();
					
			PopupPanel popup = new PopupPanel();
			popup.setAnimationEnabled(true);
			popup.setAutoHideEnabled(true);
			popup.setGlassEnabled(true);
			popup.setGlassStyleName(POPUPGLASSPANELCSS);
			popup.setStylePrimaryName(POPUP_CSS);
			popup.add(popupLbl);
			popup.setPopupPosition(542, 70);
			popup.show();
		} catch (Exception e) {
			
		}
		
	}
	
	private Configuration getNotificationLabelFieldConf(String displayText , String primaryCss , String dependentCss ,String propEditorLblPanelCss){
		Configuration conf = new Configuration();
		try {
			conf.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, displayText);
			conf.setPropertyByName(LabelFieldConstant.BF_PCLS, primaryCss);
			conf.setPropertyByName(LabelFieldConstant.BF_DCLS, dependentCss);
			conf.setPropertyByName(LabelFieldConstant.BF_BASEPANEL_PCLS, propEditorLblPanelCss);
		} catch (Exception e) {
			
		}
		return conf;
	}
	private Entity createServiceDataEntity() {
		errorlabelHorizontalPanel.clear();
		LabelField errorLabelField = new LabelField();
		try{
			Entity serviceEntity = new Entity();
			serviceEntity.setType(new MetaType(TypeConstants.SERVICE));
			
			if(!nameTextField.getFieldValue().equals("")){
				String serviceName = nameTextField.getFieldValue();
				serviceEntity.setPropertyByName(ServiceConstant.NAME, serviceName);
			}else {
				throw new NullPointerException("name");
			}
			
			if(!versionTextField.getFieldValue().equals("")){
				String serviceVersion = versionTextField.getFieldValue();
				serviceEntity.setPropertyByName(ServiceConstant.APPOPSCOREVERSION, serviceVersion);
			}else{
				throw new NullPointerException("version");
			}
			
			if(!serviceBlobId.equals("")){
				serviceEntity.setPropertyByName("blobId", serviceBlobId);
			}
		
			return serviceEntity;
			
		}catch(Exception e){
			if(e.getMessage().equals("name")){
				 errorLabelField.setConfiguration(getErrorLabelFieldConfiguration("Please enter service name"));
				 errorLabelField.configure();
				 errorLabelField.create();
			}else if(e.getMessage().equals("version")){
				 errorLabelField.setConfiguration(getErrorLabelFieldConfiguration("Please enter service version"));
				 errorLabelField.configure();
				 errorLabelField.create();
			}
			errorlabelHorizontalPanel.add(errorLabelField);
			basePanel.add(errorlabelHorizontalPanel);
		}
		
		
		return null;
	}
	private Configuration getErrorLabelFieldConfiguration(String displayText) {
		Configuration configuration = null;	
		try{
			
			configuration = new Configuration();
			
			configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, displayText);
			configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, "errorLabelCss");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return configuration;
	}
	@Override
	public void onAttachmentEvent(AttachmentEvent event) {
		String blobId = (String) event.getEventData();
		if(event.getEventType()==AttachmentEvent.ATTACHMENTCOMPLETED){
			if(blobId!=null){
				serviceBlobId = blobId;
			}
		}
		
	}
	

}

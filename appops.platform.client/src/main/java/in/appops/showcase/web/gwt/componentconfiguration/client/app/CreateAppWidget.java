package in.appops.showcase.web.gwt.componentconfiguration.client.app;

import in.appops.client.common.config.field.ButtonField;
import in.appops.client.common.config.field.ButtonField.ButtonFieldConstant;
import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.client.common.config.field.ListBoxField;
import in.appops.client.common.config.field.ListBoxField.ListBoxFieldConstant;
import in.appops.client.common.config.field.SelectedItem;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.client.common.fields.TextField;
import in.appops.client.common.fields.TextField.TextFieldConstant;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.entity.type.MetaType;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.server.core.services.platform.coreplatformservice.constant.ServiceConstant;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CreateAppWidget extends Composite implements FieldEventHandler{

	private VerticalPanel basePanel ;
	private FlexTable appCreationTable;
	private  HorizontalPanel errorLabelPanel;
	private TextField appNameTextField;
	private TextField appUrlTextField;
	private ListBoxField deviceGroupBoxField;
	private ListBoxField servicesBoxField;
	private ButtonField createAppBtnField;
	public static String APPNAME_STRINGFIELD_ID = "appNameStringFieldId";
	public static String CREATEAPP_BTN_ID = "saveApp";
	public static String CREATEAPPBTNCSS = "createAppBtn";
	public static String CREATEAPPLABELSCSS = "createAppWidgetLabels";
	public static String DEVICEGROUPLISTBOX_ID = "deviceGroupListBox";
	public static String SERVICELISTBOX_ID = "serviceListBox";
	private Entity deviceGroupEntity;
	private Entity serviceEntity;
	private final String POPUPGLASSPANELCSS = "popupGlassPanel";
	private final String POPUP_CSS = "popupCss";
	private final String POPUP_LBL_PCLS = "popupLbl";
	private HandlerRegistration fieldEventhandler = null;
	private final String CREATEAPPHEADERLBL="createAppPlusServiceHeaderLbl";
	public CreateAppWidget() {
		initialize();
		initWidget(basePanel);
		if(fieldEventhandler == null)
			fieldEventhandler = AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE, this);
	}
	
	public void deregisterHandler(){
		try {
			fieldEventhandler.removeHandler();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initialize() {
		basePanel = new VerticalPanel();
		appCreationTable = new FlexTable();
		errorLabelPanel = new HorizontalPanel();
	}
	
	public void createUi(){
		try{
			LabelField appCreationHeaderLabelField = new LabelField();
			 appCreationHeaderLabelField.setConfiguration(getAppCreationLabelFieldConfiguration("App Creation"));
			 appCreationHeaderLabelField.configure();
			 appCreationHeaderLabelField.create();
			
			 LabelField appNameLabelField = new LabelField();
			 appNameLabelField.setConfiguration(getLabelFieldConfiguration("App name :"));
			 appNameLabelField.configure();
			 appNameLabelField.create();
			
			 appNameTextField = new TextField();
			 appNameTextField.setConfiguration(getAppNameFieldConfiguration());
			 appNameTextField.configure();
			 appNameTextField.create();
			 
			 LabelField appUrlLabelField = new LabelField();
			 appUrlLabelField.setConfiguration(getLabelFieldConfiguration("App url :"));
			 appUrlLabelField.configure();
			 appUrlLabelField.create();
			 
			 appUrlTextField = new TextField();
			 appUrlTextField.setConfiguration(getAppUrlFieldConfiguration());
			 appUrlTextField.configure();
			 appUrlTextField.create();
			 
			 LabelField appDeviceGroupLabelField = new LabelField();
			 appDeviceGroupLabelField.setConfiguration(getLabelFieldConfiguration("App device group :"));
			 appDeviceGroupLabelField.configure();
			 appDeviceGroupLabelField.create();
			 
			 deviceGroupBoxField = new ListBoxField();
			 deviceGroupBoxField.setConfiguration(getDeviceGroupConfiguration());
			 deviceGroupBoxField.configure();
			 deviceGroupBoxField.create();
			 
			 LabelField appServicesLabelField = new LabelField();
			 appServicesLabelField.setConfiguration(getLabelFieldConfiguration("App service :"));
			 appServicesLabelField.configure();
			 appServicesLabelField.create();
			 
			 
			 servicesBoxField = new ListBoxField();
			 servicesBoxField.setConfiguration(getServicesBoxConfiguration());
			 servicesBoxField.configure();
			 servicesBoxField.create();
			 
			 createAppBtnField = new ButtonField();
			 createAppBtnField.setConfiguration(getCreateAppBtnConfiguration());
			 createAppBtnField.configure();
			 createAppBtnField.create();
			 
			 appCreationTable.setWidget(1, 0, appNameLabelField);
			 appCreationTable.setWidget(1, 2, appNameTextField);
			 
			 
			 appCreationTable.setWidget(3, 0, appUrlLabelField);
			 appCreationTable.setWidget(3, 2, appUrlTextField);
			
			 
			 appCreationTable.setWidget(5, 0, appDeviceGroupLabelField);
			 appCreationTable.setWidget(5, 2, deviceGroupBoxField);
			
			 
			 appCreationTable.setWidget(7, 0, appServicesLabelField);
			 appCreationTable.setWidget(7, 2, servicesBoxField);
			 appCreationTable.setWidget(11, 2, createAppBtnField);
			 
			 appCreationTable.getCellFormatter().setAlignment(11, 2, HasHorizontalAlignment.ALIGN_RIGHT, HasVerticalAlignment.ALIGN_MIDDLE);
			 
			 
			 basePanel.add(appCreationHeaderLabelField);
			 basePanel.setCellHorizontalAlignment(appCreationHeaderLabelField, HasHorizontalAlignment.ALIGN_CENTER);
			 basePanel.setCellVerticalAlignment(appCreationHeaderLabelField, HasVerticalAlignment.ALIGN_MIDDLE);
			 
			 basePanel.add(appCreationTable);
			 basePanel.setCellHorizontalAlignment(appCreationTable, HasHorizontalAlignment.ALIGN_CENTER);
			 basePanel.setCellVerticalAlignment(appCreationTable, HasVerticalAlignment.ALIGN_MIDDLE);
			 
			 basePanel.setWidth("100%");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private Configuration getCreateAppBtnConfiguration() {
		Configuration configuration = new Configuration();
		try {
			configuration.setPropertyByName(ButtonFieldConstant.BTNFD_DISPLAYTEXT, "Create app");
			configuration.setPropertyByName(ButtonFieldConstant.BF_PCLS,CREATEAPPBTNCSS);
			configuration.setPropertyByName(ButtonFieldConstant.BF_ID, CREATEAPP_BTN_ID);
		} catch (Exception e) {
			
		}
		return configuration;
	}

	private Configuration getAppCreationLabelFieldConfiguration(String displayText) {
		Configuration configuration = null;	
		try{
			
			configuration = new Configuration();
			
			configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, displayText);
			configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, CREATEAPPHEADERLBL);
		}
		catch(Exception e){
			e.printStackTrace();
		}
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

	private Configuration getServicesBoxConfiguration() {
		Configuration configuration = new Configuration();
		try {
						
			configuration.setPropertyByName(ListBoxFieldConstant.BF_DEFVAL,"---Select service ---");
			//configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_ITEMS,getServicesList());
			configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_OPRTION,"appdefinition.AppDefinitionService.getAllServiceList");
			configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_QUERYNAME,"getServices");
			configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_ENTPROP,"name");
			configuration.setPropertyByName(ListBoxFieldConstant.BF_ID,SERVICELISTBOX_ID);
			configuration.setPropertyByName(ListBoxFieldConstant.BF_PCLS, "createAppListBox");
		} catch (Exception e) {
			
		}
		
		return configuration;
	}

	
	private Configuration getDeviceGroupConfiguration() {
		Configuration configuration = new Configuration();
		try {
						
			configuration.setPropertyByName(ListBoxFieldConstant.BF_DEFVAL,"---Select device group ---");
			//configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_ITEMS,getDeviceGroupList());
			configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_OPRTION,"appdefinition.AppDefinitionService.getEntityList");
			configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_QUERYNAME,"getDeviceGroup");
			
			configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_ENTPROP,"name");
			configuration.setPropertyByName(ListBoxFieldConstant.BF_ID,DEVICEGROUPLISTBOX_ID);
			configuration.setPropertyByName(ListBoxFieldConstant.BF_PCLS, "createAppListBox");
		} catch (Exception e) {
			
		}
		
		return configuration;
	}

	
	private Configuration getAppUrlFieldConfiguration() {
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(TextFieldConstant.TF_TYPE, TextFieldConstant.TFTTYPE_TXTAREA);
		
		configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_POS, TextFieldConstant.BF_SUGGESTION_INLINE);
		configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_TEXT, "Enter app url");
		
		configuration.setPropertyByName(TextFieldConstant.BF_ID, APPNAME_STRINGFIELD_ID);
		configuration.setPropertyByName(TextFieldConstant.TF_VISLINES,5);
		configuration.setPropertyByName(TextFieldConstant.TF_CHARWIDTH,55);
		
		//configuration.setPropertyByName(TextFieldConstant.BF_PCLS, VALUEFIELD_PCLS);
		
		return configuration;
	}

	private Configuration getAppNameFieldConfiguration() {
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(TextFieldConstant.TF_TYPE, TextFieldConstant.TFTYPE_TXTBOX);
		
		configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_POS, TextFieldConstant.BF_SUGGESTION_INLINE);
		configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_TEXT, "Enter app name");
		
		configuration.setPropertyByName(TextFieldConstant.BF_ID, APPNAME_STRINGFIELD_ID);
		configuration.setPropertyByName(TextFieldConstant.TF_MAXLENGTH, 55);
		
		//configuration.setPropertyByName(TextFieldConstant.BF_PCLS, VALUEFIELD_PCLS);
		
		return configuration;
	}

	@Override
	public void onFieldEvent(FieldEvent event) {
		try {
			int eventType = event.getEventType();
			Object eventSource = event.getEventSource();
			switch (eventType) {
			case FieldEvent.CLICKED: {
				if (eventSource instanceof ButtonField) {
					ButtonField btnField = (ButtonField) eventSource;
					if (btnField.getBaseFieldId().equals(CREATEAPP_BTN_ID)) {
						Entity appEntity = createAppEntity();
						if(appEntity!=null)
						 saveAppEntityData(appEntity);
					}
				}
				break;
			}
			case FieldEvent.VALUECHANGED:{
				
					if(eventSource instanceof ListBoxField){
						ListBoxField listBoxField = (ListBoxField) eventSource;
						if(listBoxField.getBaseFieldId().equalsIgnoreCase(DEVICEGROUPLISTBOX_ID)){
							SelectedItem selectedItem = (SelectedItem) event.getEventData();
							 deviceGroupEntity = selectedItem.getAssociatedEntity();
							
						}else if(listBoxField.getBaseFieldId().equalsIgnoreCase(SERVICELISTBOX_ID)){
							SelectedItem selectedItem = (SelectedItem) event.getEventData();
							serviceEntity  = selectedItem.getAssociatedEntity();
							
						}
					}
				}
			
			}
		} catch (Exception e) {
			
		}
		
	}

	@SuppressWarnings("unchecked")
	private void saveAppEntityData(Entity appEnt) {
		DefaultExceptionHandler exceptionHandler = new DefaultExceptionHandler();
		DispatchAsync	dispatch = new StandardDispatchAsync(exceptionHandler);
		
		Map parameterMap = new HashMap();
		parameterMap.put("entity", appEnt);
		parameterMap.put("update", false);
		
		
		StandardAction action = new StandardAction(Entity.class, "appdefinition.AppDefinitionService.createApp", parameterMap);
		dispatch.execute(action, new AsyncCallback<Result<Entity>>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(Result<Entity> result) {
				if (result != null) {
					Entity appEntity = result.getOperationResult();
					if(appEntity!=null){
						clearFields();
						showNotification("App created successfully..");
					}
				}else{
					showNotification("Data not saved..");
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
	
	private void clearFields() {
		appNameTextField.clear();
		appUrlTextField.clear();
		
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
	
	private Entity createAppEntity() {
		errorLabelPanel.clear();
		 LabelField errorLabelField = new LabelField();
		Entity appEntity = new Entity();
		appEntity.setType(new MetaType("App"));
		try{
						
			
			if(!appNameTextField.getFieldValue().equals("")){
				String appName = appNameTextField.getFieldValue();
				appEntity.setPropertyByName("name", appName);
			}else{
				throw new NullPointerException("name");
			}
			
			
			
			if(!appUrlTextField.getFieldValue().equals("")){
				String appUrl = appUrlTextField.getFieldValue();
				appEntity.setPropertyByName("url", appUrl);
			}else{
				throw new NullPointerException("url");
			}
			
			if(!deviceGroupBoxField.getSelectedValue().equals("---Select device group ---")){
				
				appEntity.setProperty("devicegroup", deviceGroupEntity);
			}
			if(!servicesBoxField.getSelectedValue().equals("---Select service ---")){
				Key<Serializable> key = (Key<Serializable>) serviceEntity.getProperty(ServiceConstant.ID).getValue();
				Long serviceId = (Long) key.getKeyValue();
				appEntity.setPropertyByName("serviceId", serviceId);
			}
			
		}catch(Exception e){
			
			 
			if(e.getMessage().equals("url")){
			
			 errorLabelField.setConfiguration(getErrorLabelFieldConfiguration("Please enter app url"));
			 errorLabelField.configure();
			 errorLabelField.create();
			
			}else if(e.getMessage().equals("name")){
				 errorLabelField.setConfiguration(getErrorLabelFieldConfiguration("Please enter app name"));
				 errorLabelField.configure();
				 errorLabelField.create();
			}
			 errorLabelPanel.add(errorLabelField);
			 basePanel.add(errorLabelPanel);
			 basePanel.setCellHorizontalAlignment(errorLabelPanel, HasHorizontalAlignment.ALIGN_CENTER);
			 basePanel.setCellVerticalAlignment(errorLabelPanel, HasVerticalAlignment.ALIGN_MIDDLE);
			return null;			 
		}
		return appEntity;
		
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
	
}

package in.appops.showcase.web.gwt.componentconfiguration.client.library;

import java.util.HashMap;
import java.util.Map;

import in.appops.client.common.config.field.ButtonField;
import in.appops.client.common.config.field.ButtonField.ButtonFieldConstant;
import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.EntityList;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author pallavi@ensarm.com
 *
 */
public class ConfigurationEditor extends Composite implements FieldEventHandler{
	
	private VerticalPanel basePanel;
	private ConfPropertyEditor confPropertyEditor;
	
	private static String BASEPANEL_CSS = "confEditorPanel";
	private final String HEADERLBL_CSS = "componentSectionHeaderLbl";
	private static String SAVECONFIGURATION_BTN_ID = "saveConfigBtnId";
	
	public ConfigurationEditor() {
		basePanel = new VerticalPanel();
		initWidget(basePanel);
	}
	
	public void createUi(){
		
		
		basePanel.setStylePrimaryName(BASEPANEL_CSS);
		
		LabelField headerLbl = new LabelField();
		Configuration headerLblConfig = getHeaderLblConfig();
	
		headerLbl.setConfiguration(headerLblConfig);
		headerLbl.configure();
		headerLbl.create();
						
		
		confPropertyEditor = new ConfPropertyEditor(null, null);
		confPropertyEditor.createUi();
		
		basePanel.add(headerLbl);
		basePanel.add(confPropertyEditor);
				
		basePanel.setCellHorizontalAlignment(headerLbl, HorizontalPanel.ALIGN_CENTER);
		
		
	}
	
	private Configuration getHeaderLblConfig() {
		Configuration configuration = null;	
		try{
			configuration = new Configuration();
			configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, "Add configurations");
			configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, HEADERLBL_CSS);
		}
		catch(Exception e){
			e.printStackTrace();
		}
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
					ButtonField saveConfBtnField = (ButtonField) eventSource;
					if (saveConfBtnField.getBaseFieldId().equals(SAVECONFIGURATION_BTN_ID)) {
						saveConfDefinition();
					}
				}
				break;
			}
			default:
				break;
			}
		} catch (Exception e) {
			
		}
	}

	@SuppressWarnings("unchecked")
	private void saveConfDefinition() {
		try {
			EntityList confDefList = confPropertyEditor.getConfDefList();
			
			DefaultExceptionHandler exceptionHandler = new DefaultExceptionHandler();
			DispatchAsync	dispatch = new StandardDispatchAsync(exceptionHandler);
						
			Map parameterMap = new HashMap();
			parameterMap.put("confEnt", null);
			
			StandardAction action = new StandardAction(Entity.class, "appdefinition.AppDefinitionService.saveConfigurationDefinition", parameterMap);
			dispatch.execute(action, new AsyncCallback<Result<Entity>>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.printStackTrace();
				}

				@Override
				public void onSuccess(Result<Entity> result) {
					if(result!=null){
						Window.alert("Configurations saved successfully");
					}
				}
			});
		} catch (Exception e) {
		}
		
	}
}

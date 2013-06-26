package in.appops.showcase.web.gwt.calendarHome.client;

import in.appops.client.common.components.CreateCalendarEntryScreen;
import in.appops.client.common.fields.LabelField;
import in.appops.client.common.fields.LabelField.LabelFieldConstant;
import in.appops.client.common.snippet.CalendarServiceHomeSnippet;
import in.appops.client.common.util.AppEnviornment;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.EntityList;

import java.util.HashMap;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

public class CalenderHomeEntry implements EntryPoint{

	@Override
	public void onModuleLoad() {
		fecthUser();
		/*CalendarServiceHomeSnippet calendarServiceHomeSnippet = new CalendarServiceHomeSnippet();
		calendarServiceHomeSnippet.setConfiguration(getConfiguration());
		calendarServiceHomeSnippet.initialize();
		//calendarServiceHomeSnippet.createUi();
		RootPanel.get().add(calendarServiceHomeSnippet);
		RootPanel.get().setWidgetPosition(calendarServiceHomeSnippet,300,50);*/
	}

	private Configuration getConfiguration() {
		Configuration configuration = new Configuration();
		//configuration.setPropertyByName(CreateCalendarEntryScreen.REMINDER_MODE, CreateCalendarEntryScreen.REMINDER_NEW);
		configuration.setPropertyByName(CreateCalendarEntryScreen.SCREEN_TYPE, CreateCalendarEntryScreen.CREATE_EVENT);
		return configuration;
	}

	@SuppressWarnings("unchecked")
	private void fecthUser() {
		try{
			
			LabelField label = new LabelField();
			label.setFieldValue("Loading..");
			label.setConfiguration(getLabelFieldConfiguration(true, "appops-LabelField", null, null));
			label.create();
			RootPanel.get().add(label); 
			RootPanel.get().setWidgetPosition(label, 200, 50);
			DefaultExceptionHandler	exceptionHandler	= new DefaultExceptionHandler();
			DispatchAsync				dispatch			= new StandardDispatchAsync(exceptionHandler);
	
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("emailId", "kiran@ensarm.com");
			paramMap.put("password", "kiran123");
					
			StandardAction action = new StandardAction(EntityList.class, "useraccount.LoginService.validateUser", paramMap);
			dispatch.execute(action, new AsyncCallback<Result<EntityList>>() {
				
				public void onFailure(Throwable caught) {
					caught.printStackTrace();
				}
				
				public void onSuccess(Result<EntityList> result) {
					if(result!=null){
						EntityList entityList = result.getOperationResult();
						Entity userEnt = entityList.get(0);
						AppEnviornment.setCurrentUser(userEnt);
						CalendarServiceHomeSnippet calendarServiceHomeSnippet = new CalendarServiceHomeSnippet();
						calendarServiceHomeSnippet.setConfiguration(getConfiguration());
						calendarServiceHomeSnippet.initialize();
						//calendarServiceHomeSnippet.createUi();
						RootPanel.get().clear();
						RootPanel.get().add(calendarServiceHomeSnippet);
						RootPanel.get().setWidgetPosition(calendarServiceHomeSnippet,300,50);
					}
				}
	
				
			});
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	private Configuration getLabelFieldConfiguration(boolean allowWordWrap, String primaryCss, String secondaryCss, String debugId){
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(LabelFieldConstant.LBLFIELD_WORDWRAP, allowWordWrap);
		configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, primaryCss);
		configuration.setPropertyByName(LabelFieldConstant.BF_DCLS, secondaryCss);
		return configuration;
	}
}

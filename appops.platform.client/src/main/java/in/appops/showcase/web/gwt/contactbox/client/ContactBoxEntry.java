package in.appops.showcase.web.gwt.contactbox.client;

import in.appops.client.common.components.IntelliThoughtWidget;
import in.appops.client.common.components.SendMessageWidget;
import in.appops.client.common.fields.LabelField;
import in.appops.client.common.util.AppEnviornment;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.core.constants.propertyconstants.SpaceConstants;
import in.appops.platform.core.constants.typeconstants.TypeConstants;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.entity.Property;
import in.appops.platform.core.entity.type.MetaType;
import in.appops.platform.core.operation.ActionContext;
import in.appops.platform.core.operation.InitiateActionContext;
import in.appops.platform.core.operation.IntelliThought;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.EntityList;

import java.io.Serializable;
import java.util.HashMap;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

public class ContactBoxEntry implements EntryPoint{

	@Override
	public void onModuleLoad() {
		/*ContactBoxField contactBoxField = new ContactBoxField();
		contactBoxField.setConfiguration(getConfiguration(null, null));
		try {
			contactBoxField.createField();
		} catch (AppOpsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		fecthUser();
		
		
	}

	@SuppressWarnings("unchecked")
	private void fecthUser() {
		
		try{
			
			LabelField label = new LabelField();
			label.setFieldValue("Loading..");
			label.setConfiguration(getLabelFieldConfiguration(true, "appops-LabelField", null, null));
			label.createField();
			RootPanel.get().add(label); 
			RootPanel.get().setWidgetPosition(label, 200, 50);
			DefaultExceptionHandler	exceptionHandler	= new DefaultExceptionHandler();
			DispatchAsync				dispatch			= new StandardDispatchAsync(exceptionHandler);
	
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("emailId", "nitish@ensarm.com");
			paramMap.put("password", "nitish123");
					
			StandardAction action = new StandardAction(EntityList.class, "useraccount.LoginService.validateUser", paramMap);
			dispatch.execute(action, new AsyncCallback<Result<EntityList>>() {
				
				public void onFailure(Throwable caught) {
					caught.printStackTrace();
				}
				
				public void onSuccess(Result<EntityList> result) {
					if(result!=null){
						EntityList entityList = result.getOperationResult();
						Entity userEnt = entityList.get(0);
						Entity spaceEnt = entityList.get(1);
						AppEnviornment.setCurrentUser(userEnt);
						Entity entity = new Entity();
					    entity.setType(new MetaType(TypeConstants.SPACE));
				        Property<Serializable> property = new Property<Serializable>();
				        Key<Serializable> key = new Key<Serializable>(5008L);
				        property.setName(SpaceConstants.ID);
				        property.setValue(key);
				        entity.setProperty(property);
				        AppEnviornment.setCurrentSpace(entity);
						//AppEnviornment.setCurrentSpace(spaceEnt);
					   /* for(Entity userEntity:entityList){
						
						    AppEnviornment.setCurrentUser(userEntity);
						    Entity entity = new Entity();
						    entity.setType(new MetaType(TypeConstants.SPACE));
					        Property<Serializable> property = new Property<Serializable>();
					        Key<Serializable> key = new Key<Serializable>(1L);
					        property.setName(SpaceConstants.ID);
					        property.setValue(key);
					        entity.setProperty(property);
					        AppEnviornment.setCurrentSpace(entity);
					    }*/
				        Configuration intelliFieldConf = getIntelliFieldConfiguration("intelliShareField", null);
				        
						/**** For Appops Showcase *****/
				        String queryString = Window.Location.getQueryString();
				        
				        SendMessageWidget messageWidget = null;
				        if(queryString != null) {
				        	messageWidget = new  SendMessageWidget((InitiateActionContext)getActionContext());
				        } else {
				        	messageWidget = new  SendMessageWidget(null);
				        }
						/********************/

						messageWidget.setIntelliShareFieldConfiguration(intelliFieldConf);
						/*try {
							messageWidget.createComponent(getConfiguration(null, null));
						} catch (AppOpsException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}*/
						RootPanel.get().add(messageWidget);
						RootPanel.get().setWidgetPosition(messageWidget, 200, 50);
					}
				}
	
				
			});
		}catch(Exception e){
			e.printStackTrace();
		}
	} 
	
	
	private Configuration getConfiguration(String primaryCss, String secondaryCss){
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(LabelField.LABELFIELD_PRIMARYCSS, primaryCss);
		configuration.setPropertyByName(LabelField.LABELFIELD_DEPENDENTCSS, secondaryCss);
		
		configuration.setPropertyByName(IntelliThoughtWidget.IS_INTELLISHAREFIELD, true);
		configuration.setPropertyByName(IntelliThoughtWidget.IS_ATTACHMEDIAFIELD, true);
		return configuration;
	}
	
	private Configuration getIntelliShareConfiguration() {
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(IntelliThoughtWidget.IS_INTELLISHAREFIELD, true);
		configuration.setPropertyByName(IntelliThoughtWidget.IS_ATTACHMEDIAFIELD, true);
		return configuration;
	}
	private Configuration getIntelliFieldConfiguration(String primaryCss, String secondaryCss){
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(LabelField.LABELFIELD_PRIMARYCSS, primaryCss);
		configuration.setPropertyByName(LabelField.LABELFIELD_DEPENDENTCSS, secondaryCss);
		return configuration;
	}
	private Configuration getLabelFieldConfiguration(boolean allowWordWrap, String primaryCss, String secondaryCss, String debugId){
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(LabelField.LABELFIELD_WORDWRAP, allowWordWrap);
		configuration.setPropertyByName(LabelField.LABELFIELD_PRIMARYCSS, primaryCss);
		configuration.setPropertyByName(LabelField.LABELFIELD_DEPENDENTCSS, secondaryCss);
		configuration.setPropertyByName(LabelField.LABELFIELD_DEBUGID, debugId);
		return configuration;
	}
	
	/**** For Appops Showcase *****/
	public ActionContext getActionContext(){
		String queryString = Window.Location.getQueryString();
		String text = queryString.substring(queryString.indexOf("text=") + "text=".length() );
		text = text.replaceAll("%20", " ");
		IntelliThought intelliThought = new IntelliThought();
		intelliThought.setIntelliHtml(text);
		
		InitiateActionContext context = new InitiateActionContext();
		context.setType(new MetaType("ActionContext"));
		context.setIntelliThought(intelliThought);
		return context;
	}
}

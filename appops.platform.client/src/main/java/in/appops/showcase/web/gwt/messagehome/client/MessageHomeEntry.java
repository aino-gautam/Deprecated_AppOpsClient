package in.appops.showcase.web.gwt.messagehome.client;

import in.appops.client.common.components.MessagesHomeWidget;
import in.appops.client.common.fields.LabelField;
import in.appops.client.common.fields.LabelField.LabelFieldConstant;
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
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.EntityList;

import java.io.Serializable;
import java.util.HashMap;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

public class MessageHomeEntry implements EntryPoint{

	@Override
	public void onModuleLoad() {
		
		try{
			fecthUser();
		}catch(Exception e){
			e.printStackTrace();
		}
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
					    MessagesHomeWidget homeWidget = new MessagesHomeWidget();
						
						
						RootPanel.get().clear();
						RootPanel.get().add(homeWidget); 
						
						//RootPanel.get().setSize("100%","100%");
						RootPanel.get().setWidgetPosition(homeWidget, 200, 50);
					    
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

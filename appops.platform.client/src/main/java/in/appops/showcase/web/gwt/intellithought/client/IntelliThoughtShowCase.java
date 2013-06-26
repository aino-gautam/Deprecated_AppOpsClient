package in.appops.showcase.web.gwt.intellithought.client;

import in.appops.client.common.components.IntelliThoughtWidget;
import in.appops.client.common.fields.IntelliThoughtField;
import in.appops.client.common.fields.LabelField;
import in.appops.client.common.fields.LabelField.LabelFieldConstant;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.core.operation.ResponseActionContext;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;
import in.appops.platform.core.util.EntityList;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;

public class IntelliThoughtShowCase implements EntryPoint{
	private Image loaderImage;
	private HorizontalPanel basePanel;
	public void onModuleLoad() {
		basePanel = new HorizontalPanel();
		basePanel.setStylePrimaryName("fullWidth");
		RootPanel.get().add(basePanel);

		loaderImage = new Image("images/opptinLoader.gif");
		loaderImage.setStylePrimaryName("appops-intelliThoughtActionImage");
		loaderImage.setVisible(false);
		final IntelliThoughtWidget shareComponent = new IntelliThoughtWidget();
		
		Configuration intelliFieldConf = getIntelliFieldConfiguration("intelliShareField", null);
		Configuration intelliShareComponentConf = getIntelliShareConfiguration();
		shareComponent.setIntelliShareFieldConfiguration(intelliFieldConf);
//		List<String> mediaList = new LinkedList<String>();
//		mediaList.add("jpg_F66G3CFiewZvQ%2B5IcAShMUubfvU6AgjTACYtjfygJHU%3D");
//		mediaList.add("jpg_F66G3CFiewabyXTw8XqShBKnslkFI0JoIeTo2WKvRca4Y6cO1Kyg5XJmDlzyTJVh");
//		mediaList.add("jpg_F66G3CFiewZaXMm5lgdt66oS16Mu5twe9TLGcUNG5mgchwiTjRGxivmZBxeyEXDw");
		try {
			shareComponent.createComponent(intelliShareComponentConf);
			//shareComponent.populateAttachment(mediaList);

		} catch (AppOpsException e) {
			e.printStackTrace();
		}

		Map parameters = new HashMap();
		parameters.put("emailId", "nitish@ensarm.com");
		parameters.put("password", "nitish123");
		
		StandardAction action = new StandardAction(EntityList.class, "useraccount.LoginService.validateUser", parameters);
		
		DefaultExceptionHandler exceptionHandler = new DefaultExceptionHandler();
		DispatchAsync	dispatch = new StandardDispatchAsync(exceptionHandler);

		ResponseActionContext actionContext = new ResponseActionContext();
		actionContext.setEmbeddedAction(action);
		basePanel.add(loaderImage);
		basePanel.setCellHorizontalAlignment(loaderImage,HorizontalPanel.ALIGN_CENTER);
		loaderImage.setVisible(true);	
		dispatch.executeContextAction(actionContext, new AsyncCallback<Result>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(Result result) {
				loaderImage.setVisible(false);
				basePanel.clear();
				basePanel.add(shareComponent);
			}

			
		});
		
		
	}
	
	private Configuration getIntelliShareConfiguration() {
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(IntelliThoughtWidget.IS_INTELLISHAREFIELD, "true");
		configuration.setPropertyByName(IntelliThoughtWidget.IS_ATTACHMEDIAFIELD, "true");
		configuration.setPropertyByName(IntelliThoughtWidget.IS_SUGGESTIONACTION, "true");
		configuration.setPropertyByName(IntelliThoughtWidget.IS_PREDIFINEDACTION, "true");
		return configuration;
	}


	private Configuration getIntelliFieldConfiguration(String primaryCss, String secondaryCss){
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, primaryCss);
		configuration.setPropertyByName(LabelFieldConstant.BF_DCLS, secondaryCss);
		configuration.setPropertyByName(IntelliThoughtField.FIRE_EDITINITIATED_EVENT, "true");
		configuration.setPropertyByName(IntelliThoughtField.FIRE_THREECHARENTERED_EVENT, "true");
		configuration.setPropertyByName(IntelliThoughtField.FIRE_WORDENTERED_EVENT, "true");
		return configuration;
	}

}
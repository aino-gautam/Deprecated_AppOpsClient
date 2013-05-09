package in.appops.client.common.fields;

import in.appops.client.common.util.AppEnviornment;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.entity.Property;
import in.appops.platform.core.entity.type.MetaType;
import in.appops.platform.core.operation.ResponseActionContext;
import in.appops.platform.core.operation.Result;

import java.util.HashMap;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PostInButton extends Composite{
	
	private ToggleButton toggleButton ;
	private Entity interestedInEntity;
	private VerticalPanel basePanel;
	private final DefaultExceptionHandler exceptionHandler = new DefaultExceptionHandler();
	private final DispatchAsync	dispatch = new StandardDispatchAsync(exceptionHandler);
	private Entity savedInterestedEntity;

	/**
	 * constructor
	 */
	public PostInButton(){
		basePanel = new VerticalPanel();
		initWidget(basePanel);
	}
	
	/**
	 * constructor which takes the image for the button and the entity to bind to
	 * @param imageUrl Image url for the image to be displayed
	 * @param entity binding entity
	 */
	public PostInButton(Entity entity){
		this();
		this.interestedInEntity = entity;
		
	}
	
	/**
	 * creates the button
	 */
	public void createOutButton() {
		
		Image interestedInImage = new Image("images/opptinLogoInDarkGray.png");
		
		Image notInterestedInImage = new Image("images/opptinLogoInLightGray.png");
		
		interestedInImage.setStylePrimaryName("postInImage");
		notInterestedInImage.setStylePrimaryName("postInImage");

		toggleButton = new ToggleButton(interestedInImage,notInterestedInImage, new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						if (toggleButton.isDown()) {
							notInterestedIn();
						} else {
							interestedIn();
						}
					}
				});
		
		toggleButton.setStylePrimaryName("postInBtn");

		basePanel.add(toggleButton);
	}
	
	public void createInButton() {
		
		Image interestedInImage = new Image("images/opptinLogoInDarkGray.png");
		
		Image notInterestedInImage = new Image("images/opptinLogoInLightGray.png");
		
		interestedInImage.setStylePrimaryName("postInImage");
		notInterestedInImage.setStylePrimaryName("postInImage");

		toggleButton = new ToggleButton(notInterestedInImage,interestedInImage, new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						if (toggleButton.isDown()) {
							interestedIn();
						} else {
							notInterestedIn();
						}
					}
				});
		
		toggleButton.setStylePrimaryName("postInBtn");

		basePanel.add(toggleButton);
	}
	
	
	private void interestedIn(){
		
		HashMap  map = new HashMap();
		map.put("inRequestedOnEntity", interestedInEntity);
		StandardAction action = new StandardAction(Entity.class, "in.InService.areYouInterestedIn", map);
		ResponseActionContext actionContext = new ResponseActionContext();
		actionContext.setEmbeddedAction(action);
		actionContext.setSpace(AppEnviornment.getCurrentSpace());
		
		Entity actionEntity = new Entity();
		actionEntity.setType(new MetaType("Actions"));
		Key<Long> key = new Key<Long>(20L);
		Property<Key<Long>> actionKeyProp = new Property<Key<Long>>(key);
		actionEntity.setProperty("id", actionKeyProp);
		
		actionContext.setActionEntity(actionEntity);
		
		AsyncCallback<Result> callBack = new AsyncCallback<Result>() {
			
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}
			
			public void onSuccess(Result result) {
				Entity inEntity = (Entity)result.getOperationResult();
				savedInterestedEntity = inEntity;
									
			}
		};
						
		dispatch.executeContextAction(actionContext, callBack);
		
	}

	private void notInterestedIn(){
	
		HashMap  map = new HashMap();
		map.put("inEntity", interestedInEntity);
		
		StandardAction action = new StandardAction(Entity.class, "in.InService.deleteInRequest", map);
		ResponseActionContext actionContext = new ResponseActionContext();
		actionContext.setEmbeddedAction(action);
		actionContext.setSpace(AppEnviornment.getCurrentSpace());
		
		AsyncCallback<Result> callBack = new AsyncCallback<Result>() {
			
			
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}
			
			public void onSuccess(Result result) {
				Entity inEntity = (Entity)result.getOperationResult();
									
			}
		};
				
		dispatch.executeContextAction(actionContext, callBack);
	
}

	public Entity getSavedInterestedEntity() {
		return savedInterestedEntity;
	}

	public void setSavedInterestedEntity(Entity savedInterestedEntity) {
		this.savedInterestedEntity = savedInterestedEntity;
	}

}

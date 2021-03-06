package in.appops.client.common.components;

import in.appops.client.common.bound.EntityBound;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.platform.core.entity.Entity;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class IntelliThoughtSuggestion extends Composite implements EntityBound, ClickHandler{
	private VerticalPanel basePanel;
	private Entity entity;
	private String displayText;
	
	public IntelliThoughtSuggestion() {
		initialize();
		initWidget(basePanel);
	}
	
	public void createSuggestion() {
		displayText = entity.getPropertyByName("name");
		
		Label displayLabel = new Label(displayText);
		displayLabel.addClickHandler(this);
		displayLabel.setWidth("100%");
		basePanel.add(displayLabel);
	}

	private void initialize() {
		basePanel = new VerticalPanel();
	}

	@Override
	public Entity getEntity() {
		return entity;
	}

	@Override
	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public String getDisplayText() {
		return displayText;
	}

	public void setDisplayText(String displayText) {
		this.displayText = displayText;
	}

	@Override
	public void onClick(ClickEvent event) {
		FieldEvent fieldEvent = new FieldEvent();
		fieldEvent.setEventType(FieldEvent.SUGGESTION_CLICKED);			
		fieldEvent.setEventData(displayText);	
		AppUtils.EVENT_BUS.fireEventFromSource(fieldEvent, this);
	}
	
	public void addFieldEventHandler(FieldEventHandler handler){
		AppUtils.EVENT_BUS.addHandlerToSource(FieldEvent.TYPE, this, handler);
	}

}

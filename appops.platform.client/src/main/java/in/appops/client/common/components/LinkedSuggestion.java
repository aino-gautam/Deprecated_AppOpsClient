package in.appops.client.common.components;

import in.appops.client.common.bound.EntityListBound;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.shared.Configurable;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.EntityList;

import java.util.List;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class LinkedSuggestion extends PopupPanel implements Configurable, EntityListBound, MouseOverHandler,MouseOutHandler, FieldEventHandler{
	private FocusPanel focusPanel;
	private VerticalPanel basePanel;
	private EntityList entityList;
	private int currentSelectedEntity;
	private FieldEventHandler handler;
	public LinkedSuggestion(boolean isAutoHide) {
		super(isAutoHide);
		initialize();
		
		basePanel.setWidth("100%");
		focusPanel.setWidget(basePanel);
		this.add(focusPanel);
	}

	private void initialize() {
		focusPanel = new FocusPanel();
		basePanel = new VerticalPanel();
		basePanel.setBorderWidth(2);
	}
	
	public void populateSuggestions(){
		for(Entity linkedEntity : entityList){
			IntelliThoughtSuggestion suggestion = new IntelliThoughtSuggestion();
			suggestion.setEntity(linkedEntity);
			suggestion.createSuggestion();
			suggestion.setWidth("100%");
			suggestion.addFieldEventHandler(handler);
			suggestion.addDomHandler(this,  MouseOutEvent.getType());
			suggestion.addDomHandler(this, MouseOverEvent.getType());
			AppUtils.EVENT_BUS.addHandlerToSource(FieldEvent.TYPE, suggestion, this);
			basePanel.add(suggestion);
		}
		
	}
	
	public void populateSuggestions(List<String> list){
		for(String linkedEntity : list){
			Label display = new Label(linkedEntity);
			display.setWidth("100%");
			basePanel.add(display);
		}
		
	}

	public void temp(){
		basePanel.add(new Label("mumbai"));
		basePanel.add(new Label("Pune"));
		basePanel.add(new Label("bangalore"));
		basePanel.add(new Label("manchester"));
		basePanel.add(new Label("madrid"));
		basePanel.add(new Label("espana"));
	}

	@Override
	public Configuration getConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setConfiguration(Configuration conf) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public EntityList getEntityList() {
		return entityList;
	}

	@Override
	public void setEntityList(EntityList entityList) {
		this.entityList = entityList;
	}

	@Override
	public void addToList(Entity entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeFromList(Entity entity) {
		// TODO Auto-generated method stub
		
	}
	
	public void clearList(){
		basePanel.clear();
	}
	
	public void clearSelection(int index) {
		if(index >= 0) {
			Widget label = basePanel.getWidget(index);
			label.removeStyleName("selectedItem");
		}
	}
	
	public void setCurrentSelection(int index) {
		Widget label = basePanel.getWidget(index);
		label.setStylePrimaryName("selectedItem");
	}
	
	public void doSelection(int keyCode){
		if(keyCode ==  KeyCodes.KEY_DOWN){
			this.clearSelection(currentSelectedEntity);
			currentSelectedEntity++;
			if(currentSelectedEntity > (basePanel.getWidgetCount() - 1)){
				currentSelectedEntity = 0;
			}
			this.setCurrentSelection(currentSelectedEntity);
		}
    
		if(keyCode ==  KeyCodes.KEY_UP){
			this.clearSelection(currentSelectedEntity);
			currentSelectedEntity--;
			if(currentSelectedEntity < 0){
				currentSelectedEntity = (basePanel.getWidgetCount() - 1);
			}
			this.setCurrentSelection(currentSelectedEntity);
		}
	}
	
	public void setFirstSelection(){
		currentSelectedEntity = 0;
		this.setCurrentSelection(currentSelectedEntity);
	}
	
	public IntelliThoughtSuggestion getCurrentSelection(){
		IntelliThoughtSuggestion suggestion = (IntelliThoughtSuggestion)basePanel.getWidget(currentSelectedEntity);
		return suggestion;
	}
	
	public void setFocus(){
		focusPanel.setFocus(true);
	}


	@Override
	public void onMouseOut(MouseOutEvent event) {
		IntelliThoughtSuggestion sugg = (IntelliThoughtSuggestion)event.getSource(); 
		sugg.removeStyleName("selectedItem");
	}

	@Override
	public void onMouseOver(MouseOverEvent event) {
		IntelliThoughtSuggestion sugg = (IntelliThoughtSuggestion)event.getSource(); 
		sugg.addStyleName("selectedItem");		
	}
	
	public void addFieldEventHandler(FieldEventHandler handler){
		
	}

	@Override
	public void onFieldEvent(FieldEvent event) {
		// TODO Auto-generated method stub
		
	}

	public void addHandle(FieldEventHandler handler) {
		this.handler = handler;
	}

}
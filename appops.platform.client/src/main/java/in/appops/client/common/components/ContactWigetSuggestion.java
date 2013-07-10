package in.appops.client.common.components;

import in.appops.client.common.bound.EntityListBound;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.client.common.fields.LabelField;
import in.appops.client.common.gin.AppOpsGinjector;
import in.appops.client.common.snippet.SnippetFactory;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.shared.Configurable;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.EntityList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ContactWigetSuggestion extends PopupPanel implements Configurable, EntityListBound, MouseOverHandler,MouseOutHandler, FieldEventHandler{

	private FocusPanel focusPanel;
	private VerticalPanel basePanel;
	private EntityList entityList;
	private int currentSelectedEntity;
	private FieldEventHandler handler;
	private final AppOpsGinjector injector = GWT.create(AppOpsGinjector.class);
	
	public ContactWigetSuggestion(boolean isAutoHide) {
		super(isAutoHide);
		initialize();
		
		basePanel.setWidth("100%");
		focusPanel.setWidget(basePanel);
		this.add(focusPanel);
	}
	
	private void initialize() {
		focusPanel = new FocusPanel();
		basePanel = new VerticalPanel();
		
	}
	
	public void populateSuggestions(){
		SnippetFactory snippetFactory = injector.getSnippetFactory();
		for(Entity entity:entityList){
			
			ContactSuggestionSnippet boxSuggestion = new ContactSuggestionSnippet();
			boxSuggestion.setEntity(entity);
			boxSuggestion.createSuggestionSnippet(snippetFactory);
			boxSuggestion.addFieldEventHandler(handler);
			basePanel.add(boxSuggestion);
			
		}
	}

	public void showNoResult(){
		try{
			LabelField noResultLabelField = new LabelField();
			noResultLabelField.setFieldValue("No results");
			noResultLabelField.setConfiguration(getLabelFieldConfiguration(true, null, null, null));
			noResultLabelField.createField();
			basePanel.add(noResultLabelField);
			basePanel.setCellHorizontalAlignment(noResultLabelField, HasHorizontalAlignment.ALIGN_CENTER);
			basePanel.setCellVerticalAlignment(noResultLabelField, HasVerticalAlignment.ALIGN_MIDDLE);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void onFieldEvent(FieldEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMouseOut(MouseOutEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMouseOver(MouseOverEvent event) {
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

	@Override
	public Configuration getConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setConfiguration(Configuration conf) {
		// TODO Auto-generated method stub
		
	}
	public void clearSelection(int index) {
		if(index >= 0) {
			Widget label = basePanel.getWidget(index);
			label.removeStyleName("selectedItem");
		}
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
	
	public void clearList(){
		basePanel.clear();
	}
	public int getCurrentSelectedEntity() {
		return currentSelectedEntity;
	}

	public void setCurrentSelectedEntity(int currentSelectedEntity) {
		this.currentSelectedEntity = currentSelectedEntity;
	}
	public void setCurrentSelection(int index) {
		Widget label = basePanel.getWidget(index);
		label.setStylePrimaryName("selectedItem");
	}
	public void setFirstSelection(){
		currentSelectedEntity = 0;
		this.setCurrentSelection(currentSelectedEntity);
	}

	public void addHandle(FieldEventHandler handler) {
		this.handler = handler;
	}
	
	private Configuration getLabelFieldConfiguration(boolean allowWordWrap, String primaryCss, String secondaryCss, String debugId){
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(LabelField.LABELFIELD_WORDWRAP, allowWordWrap);
		configuration.setPropertyByName(LabelField.LABELFIELD_PRIMARYCSS, primaryCss);
		configuration.setPropertyByName(LabelField.LABELFIELD_DEPENDENTCSS, secondaryCss);
		configuration.setPropertyByName(LabelField.LABELFIELD_DEBUGID, debugId);
		return configuration;
	}
}

package in.appops.client.common.config.field.intellithought;

import in.appops.client.common.bound.EntityListBound;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.util.EntityList;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class LinkedSuggestion extends PopupPanel implements EntityListBound, MouseOverHandler,MouseOutHandler, FieldEventHandler{
	private FocusPanel focusPanel;
	private VerticalPanel basePanel;
	private EntityList entityList;
	protected int currentSelectedEntity;
	private FieldEventHandler handler;
	private Image loaderImage;
	private Logger logger = Logger.getLogger(getClass().getName());
	
	/**** Variables that will be initialized with configuration from parent.***/ 
	private String entPropToDisplay = null;
	private String suggestionLabelCss = null;
	private String linkedSuggestionPopupCss;
	private String loaderImageBlobId;
	private String loaderImageCss;
	private String suggestionHoverCss;

	public LinkedSuggestion(boolean isAutoHide , String entPropToDisplay, String suggestionLabelCss ,String linkedSuggestionPopupCss, String loaderImageBlobId,
			String loaderImageCss, String suggestionHoverCss) {
		super(isAutoHide);
		this.entPropToDisplay = entPropToDisplay;
		this.suggestionLabelCss =  suggestionLabelCss;
		this.linkedSuggestionPopupCss = linkedSuggestionPopupCss;
		this.loaderImageBlobId = loaderImageBlobId;
		this.loaderImageCss = loaderImageCss;
		this.suggestionHoverCss = suggestionHoverCss;
		initialize();
		
		loaderImage = new Image();
		loaderImage.setStylePrimaryName(getLoaderImageCss());
		loaderImage.setVisible(false);
		
		basePanel.setWidth("100%");
		focusPanel.setWidget(basePanel);
		this.setStylePrimaryName(getLinkedSuggestionPopupCss());
		this.add(focusPanel);
	}

	private void initialize() {
		focusPanel = new FocusPanel();
		basePanel = new VerticalPanel();
	}
	
	public void populateSuggestions(){
		try {
			logger.log(Level.INFO,"[LinkedSuggestion]:: In populateSuggestions  method ");
			basePanel.clear();

			for(Entity linkedEntity : entityList){
				IntelliThoughtSuggestion suggestion = new IntelliThoughtSuggestion();
				suggestion.setSuggestionLabelCss(suggestionLabelCss);
				suggestion.setEntity(linkedEntity);
				suggestion.setEntPropToDisplay(getEntPropToDisplay());
				suggestion.createSuggestion();
				suggestion.setWidth("100%");
				suggestion.addFieldEventHandler(handler);
				suggestion.addDomHandler(this,  MouseOutEvent.getType());
				suggestion.addDomHandler(this, MouseOverEvent.getType());
				AppUtils.EVENT_BUS.addHandlerToSource(FieldEvent.TYPE, suggestion, this);
				basePanel.add(suggestion);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[LinkedSuggestion]::Exception In populateSuggestions  method :"+e);
		}
		
	}
	
	public void populateSuggestions(List<String> list){
		try {
			logger.log(Level.INFO,"[LinkedSuggestion]:: In populateSuggestions  method ");
			for(String linkedEntity : list){
				Label display = new Label(linkedEntity);
				display.setWidth("100%");
				basePanel.add(display);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[LinkedSuggestion]::Exception In populateSuggestions  method :"+e);
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
	
	public void clearSelection(int index) {
		if(index >= 0) {
			Widget label = basePanel.getWidget(index);
			label.removeStyleName(getSuggestionHoverCss());
		}
	}
	
	public void setCurrentSelection(int index) {
		Widget label = basePanel.getWidget(index);
		label.setStylePrimaryName(getSuggestionHoverCss());
	}
	
	public void doSelection(int keyCode){
		try {
			logger.log(Level.INFO,"[LinkedSuggestion]:: In doSelection  method ");
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
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[LinkedSuggestion]::Exception In doSelection  method :"+e);
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
//		IntelliThoughtSuggestion sugg = (IntelliThoughtSuggestion)event.getSource(); 
//		sugg.removeStyleName("appops-intelliThoughtSuggestionSelection");
	}

	@Override
	public void onMouseOver(MouseOverEvent event) {
		IntelliThoughtSuggestion sugg = (IntelliThoughtSuggestion)event.getSource(); 
		clearSelection(currentSelectedEntity);
		sugg.addStyleName("appops-intelliThoughtSuggestionSelection");
		this.currentSelectedEntity = basePanel.getWidgetIndex(sugg);
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

	public void createUi() {
		basePanel.clear();
		basePanel.add(loaderImage);
		basePanel.setCellHorizontalAlignment(loaderImage,HorizontalPanel.ALIGN_CENTER);
		loaderImage.setVisible(true);		
	}

	public String getEntPropToDisplay() {
		return entPropToDisplay;
	}

	public void setEntPropToDisplay(String entPropToDisplay) {
		this.entPropToDisplay = entPropToDisplay;
	}

	public String getSuggestionLabelCss() {
		return suggestionLabelCss;
	}

	public void setSuggestionLabelCss(String suggestionLabelCss) {
		this.suggestionLabelCss = suggestionLabelCss;
	}

	public String getLinkedSuggestionPopupCss() {
		return linkedSuggestionPopupCss;
	}

	public void setLinkedSuggestionPopupCss(String linkedSuggestionPopupCss) {
		this.linkedSuggestionPopupCss = linkedSuggestionPopupCss;
	}

	public String getLoaderImageBlobId() {
		return loaderImageBlobId;
	}

	public void setLoaderImageBlobId(String loaderImageBlobId) {
		this.loaderImageBlobId = loaderImageBlobId;
	}

	public String getLoaderImageCss() {
		return loaderImageCss;
	}

	public void setLoaderImageCss(String loaderImageCss) {
		this.loaderImageCss = loaderImageCss;
	}

	public String getSuggestionHoverCss() {
		return suggestionHoverCss;
	}

	public void setSuggestionHoverCss(String suggestionHoverCss) {
		this.suggestionHoverCss = suggestionHoverCss;
	}

}
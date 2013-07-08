package in.appops.client.common.fields;

import in.appops.client.common.components.IntelliThoughtSuggestion;
import in.appops.client.common.components.LinkedSuggestion;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.core.constants.typeconstants.TypeConstants;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.operation.IntelliThought;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;
import in.appops.platform.core.util.EntityList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HorizontalPanel;


/**
 * 
 * @author nitish@ensarm.com
 * 
 */
public class IntelliThoughtField extends Composite implements Field, HasText, HasHTML, EventListener{
	private Configuration configuration;
	private String fieldValue;
	private LinkedSuggestion linkedSuggestion;
	private HorizontalPanel basePanel;
	private Element intelliText;
	private static int caretPosition;  
	private ArrayList<Entity> linkedUsers;
	private ArrayList<Entity> linkedSpaces;
	private ArrayList<Entity> linkedEntities;
	
	public static final String INTELLITEXTFIELD_VISIBLELINES = "intelliShareFieldVisibleLines";
	public static final String INTELLITEXTFIELD_PRIMARYCSS = "intelliShareFieldPrimaryCss";
	public static final String INTELLITEXTFIELD_DEPENDENTCSS = "intelliShareFieldDependentCss";
	public static final String INTELLITEXTFIELD_MAXCHARLENGTH = "maxLength";
	public static final String INTELLITEXTFIELD_CONTENTEDITABLE = "contenteditable";

	public static final String FIRE_THREECHARENTERED_EVENT = "fireThreeCharEnteredEvent";
	public static final String FIRE_WORDENTERED_EVENT = "fireWordEnteredEvent";
	public static final String FIRE_EDITINITIATED_EVENT = "fireEditInitatedEvent";

	private String isFireThreeCharEnteredEvent;
	private String isFireWordEnteredEvent;
	private String isFireEditInitatedEvent;

	public IntelliThoughtField(){
		initialize();
		initWidget(basePanel);
	}
	
	private void initialize() {
		basePanel = new HorizontalPanel();
		linkedSuggestion = new LinkedSuggestion(true);
		linkedEntities = new ArrayList<Entity>();
		linkedUsers = new ArrayList<Entity>();
		linkedSpaces = new ArrayList<Entity>();
	}
	
	@Override
	public void createField() throws AppOpsException {
		if(configuration != null) {
			String primaryCss = configuration.getPropertyByName(INTELLITEXTFIELD_PRIMARYCSS) != null ?  configuration.getPropertyByName(INTELLITEXTFIELD_PRIMARYCSS).toString() : null;  
			String dependentCss = configuration.getPropertyByName(INTELLITEXTFIELD_DEPENDENTCSS) != null ?  configuration.getPropertyByName(INTELLITEXTFIELD_DEPENDENTCSS).toString() : null;  
			String maxCharLength = configuration.getPropertyByName(INTELLITEXTFIELD_MAXCHARLENGTH) != null ?  configuration.getPropertyByName(INTELLITEXTFIELD_MAXCHARLENGTH).toString() : null;  
			
			isFireThreeCharEnteredEvent = configuration.getPropertyByName(FIRE_THREECHARENTERED_EVENT);
			isFireWordEnteredEvent = configuration.getPropertyByName(FIRE_WORDENTERED_EVENT);
			isFireEditInitatedEvent = configuration.getPropertyByName(FIRE_EDITINITIATED_EVENT);
			
			if(primaryCss != null){
				this.setStylePrimaryName(primaryCss);
			} 
	
			if(dependentCss != null){
				this.addStyleName(dependentCss);
			} 
	
			if(maxCharLength != null){
				this.getElement().setAttribute(INTELLITEXTFIELD_MAXCHARLENGTH, maxCharLength);
			}
			
			intelliText = DOM.createDiv();
			intelliText.setClassName("appops-intelliThoughtField");
			intelliText.setId("intelliTextField");
			intelliText.setAttribute(INTELLITEXTFIELD_CONTENTEDITABLE, "true");
			this.setText("Any Thoughts");
	
			basePanel.getElement().appendChild(intelliText);
			basePanel.setStylePrimaryName("appops-intelliThoughtFieldPanel");
			AppUtils.EVENT_BUS.addHandlerToSource(FieldEvent.TYPE, intelliText, this);
			
			Event.setEventListener(intelliText, this);
			Event.sinkEvents(intelliText, Event.ONCLICK | Event.KEYEVENTS);
			linkedSuggestion.addHandle(this);
		}
	}

	
	@Override
	public Configuration getConfiguration() {
		return this.configuration;
	}

	@Override
	public void setConfiguration(Configuration conf) {
		this.configuration = conf;
	}	
	
	@Override
	public void clearField() {
		this.setText("");
	}

	@Override
	public void resetField() {
		
	}

	@Override
	public String getFieldValue() {
		return this.fieldValue;
	}

	@Override
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	/**
	 * This returns the text in the contenteditable div.
	 * There seems to be an issue with innerText. String operation application were failing on the innerText like trim() etc.
	 * Hence, to get the text. First have taken the innerHTML and then replaced all the space characters with " ".
	 * Then putting this HTML in a HTML Element and taken its text value. 
	 */
	@Override
	public String getText() {
		String innerHtml = intelliText.getInnerHTML();
		innerHtml = innerHtml.replaceAll("&nbsp;", " ");

		HTML htmlEle = new HTML(innerHtml);
		String text = htmlEle.getText();
		return text;
	}

	/**
	 * Sets the text in the content editable element
	 */
	@Override
	public void setText(String text) {
		intelliText.setInnerText(text);
	}
	
	@Override
	public String getHTML() {
		String innerHtml = intelliText.getInnerHTML();
		return innerHtml;
	}

	@Override
	public void setHTML(String html) {
		intelliText.setInnerHTML(html);
	}

	private void fireIntelliThoughtFieldEvent(FieldEvent fieldEvent) {
		AppUtils.EVENT_BUS.fireEventFromSource(fieldEvent, intelliText);
	}

	/**
	 * Returns and Field Event with event type and event data
	 * @param type
	 * @param data
	 * @return
	 */
	private FieldEvent getFieldEvent(int type, String data){
		FieldEvent fieldEvent = new FieldEvent();
		fieldEvent.setEventType(type);			
		fieldEvent.setEventData(data);
		return fieldEvent;
	}

	
	/**
	 * Event received are handled here.
	 */
	@Override
	public void onFieldEvent(FieldEvent event) {
		int eventType = event.getEventType();
		String eventData = (String) event.getEventData();
		
		if(this.isVisible()) {
			if(eventType == FieldEvent.THREE_CHAR_ENTERED) {
				handleThreeCharEnteredEvent(eventData);
			} else if(eventType == FieldEvent.SUGGESTION_CLICKED ){
				linkSuggestion(eventData);
			}
		}
	}

	@Override
	public void onBrowserEvent(Event event) {
		super.onBrowserEvent(event);
		int eventCode = DOM.eventGetType(event);

        switch (eventCode) { 
	        case Event.ONCLICK: 
		    	if(this.getText().equalsIgnoreCase("Any Thoughts")){
					this.setText("");
				}
		    	if(Boolean.valueOf(isFireEditInitatedEvent)){
					FieldEvent focusEvent = getFieldEvent(FieldEvent.EDITINITIATED, null);
					fireIntelliThoughtFieldEvent(focusEvent);
		    	}
            break;
            
	        case Event.ONKEYDOWN:
	        	handleOnKeyDownEvent(event);
	        break;	

	        case Event.ONKEYUP:
	        	handleOnKeyUpEvent(event);
        	break;	
        }
    }
	
	
	private void handleOnKeyDownEvent(Event event) {
		int keyCode = event.getKeyCode();
		String elementValue = this.getText();
		caretPosition = IntelliThoughtUtil.getCaretPosition("intelliTextField");
		String textTillCaretPosition = elementValue.substring(0, caretPosition);
		String wordBeingTyped = IntelliThoughtUtil.getWordBeingTyped(textTillCaretPosition, caretPosition);
		
		if(keyCode == KeyCodes.KEY_DOWN || keyCode == KeyCodes.KEY_UP){
			if(linkedSuggestion.isShowing()){
				event.preventDefault();
				linkedSuggestion.doSelection(keyCode);
			}
			return;
    	}
    	if(keyCode == KeyCodes.KEY_ENTER){
    		/** This needs to be removed and handling of enter needs to be fixed**/
    		event.preventDefault();
			if(linkedSuggestion.isShowing()){
				IntelliThoughtSuggestion suggestion = linkedSuggestion.getCurrentSelection();
				Entity selectedEnt = suggestion.getEntity();
				collectSelectedSuggestion(selectedEnt);

				linkSuggestion(suggestion.getDisplayText());
				linkedSuggestion.hide();
			}
			return;
    	}
    	if(!elementValue.trim().equals("") && (keyCode == 32)) {
			if(linkedSuggestion.isShowing()){
				linkedSuggestion.hide();
			}
			if(!wordBeingTyped.trim().equals("")) {
				if(Boolean.valueOf(isFireWordEnteredEvent)){
					FieldEvent wordEntered = getFieldEvent(FieldEvent.WORDENTERED, getText().trim());
					fireIntelliThoughtFieldEvent(wordEntered);
				}
			}
			return;
		}
    	if(keyCode == KeyCodes.KEY_BACKSPACE){
			int start = textTillCaretPosition.trim().lastIndexOf(" ") + 1;
			String nodeType = IntelliThoughtUtil.getNodeType(intelliText, start, caretPosition);
			if(nodeType != null) {
				if(nodeType.equalsIgnoreCase("a")){
					IntelliThoughtUtil.setCaretPosition(intelliText, start+1, caretPosition, true);
				}
			}
    	}
	}
	
	private void collectSelectedSuggestion(Entity entity) {
		String typeName = entity.getType().getTypeName();
		typeName = typeName.replace(".", "#");
		String[] splittedArray = typeName.split("#");
		String actualTypeProp = splittedArray[splittedArray.length-1];
		
		linkedEntities.add(entity);
		if(actualTypeProp.equals(TypeConstants.SPACETYPE)){
			linkedSpaces.add(entity);
		} else if(actualTypeProp.equals(TypeConstants.USER)){
			linkedUsers.add(entity);
		}
		
	}

	private void linkSuggestion(String text) {
		String elementValue = this.getText();
		String textTillCaretPosition = elementValue.substring(0, caretPosition);
		
		if(linkedSuggestion.isShowing()){
			
			Anchor tag = new Anchor(text);
			tag.setStylePrimaryName("appops-LinkField");
			String tagHtml = tag.getElement().getString();
			
			int start = IntelliThoughtUtil.checkPreviousWord(textTillCaretPosition, text);
			
			if(start == -1){
				start = textTillCaretPosition.trim().lastIndexOf(" ") + 1;
			}
			IntelliThoughtUtil.setCaretPosition(intelliText, start, caretPosition, false);
			IntelliThoughtUtil.insertNodeAtCaret(tagHtml); 
		}
		linkedSuggestion.hide();
	}
	
	private void handleOnKeyUpEvent(Event event){
		int keyCode = event.getKeyCode();

		String elementValue = this.getText();

		caretPosition = IntelliThoughtUtil.getCaretPosition("intelliTextField");
		String textTillCaretPosition = elementValue.substring(0, caretPosition);
		String wordBeingTyped = IntelliThoughtUtil.getWordBeingTyped(textTillCaretPosition, caretPosition);
		
		if(keyCode == KeyCodes.KEY_DOWN || keyCode == KeyCodes.KEY_UP || keyCode == KeyCodes.KEY_ENTER || keyCode == KeyCodes.KEY_ESCAPE || keyCode == KeyCodes.KEY_ENTER){
			if(linkedSuggestion.isShowing() && keyCode == KeyCodes.KEY_ESCAPE){
				linkedSuggestion.hide();
			}
			return;
		}
		if(keyCode == KeyCodes.KEY_BACKSPACE){
			if(wordBeingTyped.length() <=  2){
				if(linkedSuggestion.isShowing()){
					linkedSuggestion.hide();
				}
			}
		}
		if(wordBeingTyped.length() >  2 && Character.isUpperCase(wordBeingTyped.charAt(0))) {

			//	TODO This is fire event to call the server
			if(Boolean.valueOf(isFireThreeCharEnteredEvent)){
				FieldEvent threeCharEntered = getFieldEvent(FieldEvent.THREE_CHAR_ENTERED, wordBeingTyped);
				fireIntelliThoughtFieldEvent(threeCharEntered);
			}
			
		}
	}
	
	public void addFieldEventHandler(FieldEventHandler handler){
		AppUtils.EVENT_BUS.addHandlerToSource(FieldEvent.TYPE, intelliText, handler);
	}

	
	@SuppressWarnings("unchecked")
	private void handleThreeCharEnteredEvent(String eventData) {

		final DefaultExceptionHandler	exceptionHandler	= new DefaultExceptionHandler();
		final DispatchAsync				dispatch			= new StandardDispatchAsync(exceptionHandler);

		Map map = new HashMap();
		map.put("search", "%"+ eventData +"%");
		
		final String wd = basePanel.getElement().getOffsetWidth() - 10 + "px";
		final int posy = basePanel.getElement().getAbsoluteTop() + basePanel.getElement().getOffsetHeight();
		final int posx = basePanel.getElement().getAbsoluteLeft();

		linkedSuggestion.createUi();
		if(!linkedSuggestion.isShowing()){
			linkedSuggestion.show();
			linkedSuggestion.setWidth(basePanel.getElement().getOffsetWidth() - 10 + "px");

			linkedSuggestion.setPopupPosition(posx, posy);
		}
		
		//This is the Server Call to fetch the suggestions		
		StandardAction action = new StandardAction(EntityList.class, "spacemanagement.SpaceManagementService.getLinkSuggestions", map);
		dispatch.execute(action, new AsyncCallback<Result<EntityList>>() {
			
			
			public void onFailure(Throwable caught) {
				linkedSuggestion.hide();
				caught.printStackTrace();
			}
			
			
			public void onSuccess(Result<EntityList> result) {
				EntityList linkedSuggestionList = result.getOperationResult();
				if(linkedSuggestionList != null && !linkedSuggestionList.isEmpty()) {
					linkedSuggestion.setEntityList(linkedSuggestionList);
					linkedSuggestion.populateSuggestions();
					linkedSuggestion.setFirstSelection();
				} else {
					linkedSuggestion.hide();
				}
			}
		});
		
	}
	
	public IntelliThought getIntelliThought(){
		IntelliThought intelliThought = new IntelliThought();
		
		intelliThought.setIntelliText(getText());
		intelliThought.setIntelliHtml(getHTML());
		intelliThought.setLinkedEntities(linkedEntities);
		intelliThought.setLinkedSpaces(linkedSpaces);
		intelliThought.setLinkedUsers(linkedUsers);
		
		return intelliThought;
	}
}
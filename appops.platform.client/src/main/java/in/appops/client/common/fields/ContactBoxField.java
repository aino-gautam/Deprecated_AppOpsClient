package in.appops.client.common.fields;

import in.appops.client.common.components.ContactWigetSuggestion;
import in.appops.client.common.contactmodel.ContactSelectorModel;
import in.appops.client.common.core.EntityListReceiver;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.entity.query.Query;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;
import in.appops.platform.core.util.EntityList;
import in.appops.platform.server.core.services.contact.constant.ContactConstant;

import java.util.HashMap;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

public class ContactBoxField extends Composite implements Field,HasText,EventListener,EntityListReceiver{

	private HorizontalPanel basePanel;
	private FlowPanel widgetFlowPanel;
	private Configuration configuration;
	private ContactWigetSuggestion contactWigetSuggestion;
	private Element userText;
	private String fieldValue;
	private static int caretPosition;  
	public static final String USERBOXFIELD_VISIBLELINES = "userBoxFieldVisibleLines";
	public static final String USERBOXFIELD_PRIMARYCSS = "userBoxFieldPrimaryCss";
	public static final String USERBOXFIELD_DEPENDENTCSS = "userBoxFieldDependentCss";
	public static final String USERBOXFIELD_MAXCHARLENGTH = "maxLength";
	public static final String USERBOXFIELD_CONTENTEDITABLE = "contenteditable";
	private HashMap<String, Entity> thoseHaveContactIdUSerIdMap ;
	private HashMap<String, Entity> thoseHaveContactIdMap ;
	private HashMap<String, String> plainEmailsMap ;
	
	public ContactBoxField() {
		initialize();
		initWidget(basePanel);
		
	}

	private void initialize() {
		basePanel = new HorizontalPanel();
		widgetFlowPanel = new FlowPanel();
		contactWigetSuggestion = new ContactWigetSuggestion(true);
		thoseHaveContactIdUSerIdMap = new HashMap<String, Entity>();
		thoseHaveContactIdMap = new HashMap<String, Entity>();
		plainEmailsMap = new HashMap<String, String>();
	}
	
	@Override
	public void createField() throws AppOpsException {
		
		String primaryCss = configuration.getPropertyByName(USERBOXFIELD_PRIMARYCSS) != null ?  configuration.getPropertyByName(USERBOXFIELD_PRIMARYCSS).toString() : null;  
		String dependentCss = configuration.getPropertyByName(USERBOXFIELD_DEPENDENTCSS) != null ?  configuration.getPropertyByName(USERBOXFIELD_DEPENDENTCSS).toString() : null;  
		String maxCharLength = configuration.getPropertyByName(USERBOXFIELD_MAXCHARLENGTH) != null ?  configuration.getPropertyByName(USERBOXFIELD_MAXCHARLENGTH).toString() : null;  
		
		if(primaryCss != null){
			this.setStylePrimaryName("intelliShareField");
		} 

		if(dependentCss != null){
			this.addStyleName(dependentCss);
		} 

		if(dependentCss != null){
			this.getElement().setAttribute(USERBOXFIELD_MAXCHARLENGTH, maxCharLength);
		}
		
		
		
		userText = DOM.createDiv();
		userText.setClassName("intelliTextField");
		userText.setId("intelliTextField");
		userText.setAttribute(USERBOXFIELD_CONTENTEDITABLE, "true");
		this.setText("name");

		widgetFlowPanel.getElement().appendChild(userText);
		basePanel.getElement().appendChild(widgetFlowPanel.getElement());
		
		AppUtils.EVENT_BUS.addHandlerToSource(FieldEvent.TYPE, userText, this);
		Event.setEventListener(userText, this);
		Event.sinkEvents(userText, Event.ONCLICK | Event.KEYEVENTS);
		contactWigetSuggestion.addHandle(this);
		//DOM.setStyleAttribute(basePanel.getElement(), "border-left", "1px solid");
		//DOM.setStyleAttribute(responsesImageField.getElement(), "margin", "5px");
	}
	
	
	
	@Override
	public void onBrowserEvent(Event event) {
		super.onBrowserEvent(event);
		int eventCode = DOM.eventGetType(event);

        switch (eventCode) { 
	        case Event.ONCLICK: 
		    	if(this.getText().equalsIgnoreCase("name")){
					this.setText("");
				}
				FieldEvent focusEvent = getFieldEvent(FieldEvent.EDITINITIATED, null);
				contactBoxFieldEvent(focusEvent);
            break;
            
	        case Event.ONKEYDOWN:
	        	handleOnKeyDownEvent(event);
	        break;	

	        case Event.ONKEYUP:
	        	handleOnKeyUpEvent(event);
        	break;	
        }
    }
	
	@Override
	public Configuration getConfiguration() {
		return configuration;
	}

	@Override
	public void setConfiguration(Configuration conf) {
		this.configuration = conf;
		
	}

	@Override
	public void onFieldEvent(FieldEvent event) {
		int eventType = event.getEventType();
		String eventData ;
		Entity snippetEntity;
		if(event.getEventData() instanceof String){
			eventData = (String) event.getEventData();
			if(eventType == FieldEvent.THREE_CHAR_ENTERED) {
				handleThreeCharEnteredEvent(eventData);
			} 
		}else{
			snippetEntity = (Entity) event.getEventData();
			 if(eventType == FieldEvent.SUGGESTION_CLICKED ){
				contactSnippetSuggestion(snippetEntity);
			}
		}
		
		
		
	}
	private void contactSnippetSuggestion(Entity snippetEntity) {
		
		if(snippetEntity!=null){
			
				Key key=(Key) snippetEntity.getProperty(ContactConstant.ID).getValue();
				key.getKeyValue();
				
				if(snippetEntity.getPropertyByName(ContactConstant.USERID)!=null){
					thoseHaveContactIdUSerIdMap.put(snippetEntity.getPropertyByName(ContactConstant.NAME).toString(), snippetEntity);
				}else{
					thoseHaveContactIdMap.put(snippetEntity.getPropertyByName(ContactConstant.NAME).toString(), snippetEntity);
				}
				
				//userId null then add in null user map 
				//both not null then added in map
				//plain email then add to message participants entity 
			
		 }else{
			// plainEmailsMap.p
		 }
		
		String elementValue = this.getText();
		String textTillCaretPosition = elementValue.substring(0, caretPosition);
		
		if(contactWigetSuggestion.isShowing()){
			HorizontalPanel horizontalPanel = new HorizontalPanel();
			//horizontalPanel.setBorderWidth(1);
			horizontalPanel.setStylePrimaryName("userSelectedWidget");
			horizontalPanel.getElement().setAttribute(USERBOXFIELD_CONTENTEDITABLE, "false");
			Label tag = new Label(snippetEntity.getPropertyByName(ContactConstant.NAME).toString());
			Image image = new Image("images/cross8.png");
			
			horizontalPanel.add(tag);
			horizontalPanel.add(image);
			
			
			String tagHtml = horizontalPanel.getElement().getString()+",";
			
			int start = IntelliThoughtUtil.checkPreviousWord(textTillCaretPosition, userText.getInnerText());
			
			if(start == -1){
				start = textTillCaretPosition.trim().lastIndexOf(" ") + 1;
			}
			IntelliThoughtUtil.setCaretPosition(userText, start, caretPosition, false);
			IntelliThoughtUtil.insertNodeAtCaret(tagHtml); 
		}
		contactWigetSuggestion.hide();
		
	}

	private void handleThreeCharEnteredEvent(String eventData) {
		Query query = new Query();
		query.setQueryName("getMessageContact");
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("search", "%"+eventData+"%");
		query.setQueryParameterMap(map);
		query.setListSize(8);
		ContactSelectorModel contactSelectorModel = new ContactSelectorModel(query,"contact.ContactService.getEntityList",0);
	    
		contactSelectorModel.getEntityList(0, this);
		
	}

	private FieldEvent getFieldEvent(int type, String data){
		FieldEvent fieldEvent = new FieldEvent();
		fieldEvent.setEventType(type);			
		fieldEvent.setEventData(data);
		return fieldEvent;
	}
	@Override
	public void clearField() {
		this.setText("");
		
	}

	@Override
	public void resetField() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getFieldValue() {
		return this.fieldValue;
		
	}

	@Override
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
		
	}

	

	@Override
	public String getText() {
		String innerHtml = userText.getInnerHTML();
		innerHtml = innerHtml.replaceAll("&nbsp;", " ");

		HTML htmlEle = new HTML(innerHtml);
		String text = htmlEle.getText();
		return text;
	}

	@Override
	public void setText(String text) {
		userText.setInnerText(text);
		
	}
	private void handleOnKeyUpEvent(Event event){
		int keyCode = event.getKeyCode();

		String elementValue = this.getText();

		caretPosition = IntelliThoughtUtil.getCaretPosition("intelliTextField");
		String textTillCaretPosition = elementValue.substring(0, caretPosition);
		String wordBeingTyped = IntelliThoughtUtil.getWordBeingTyped(textTillCaretPosition, caretPosition);
		
		if(keyCode == KeyCodes.KEY_DOWN || keyCode == KeyCodes.KEY_UP || keyCode == KeyCodes.KEY_ENTER || keyCode == KeyCodes.KEY_ESCAPE || keyCode == KeyCodes.KEY_ENTER){
			if(contactWigetSuggestion.isShowing() && keyCode == KeyCodes.KEY_ESCAPE){
				contactWigetSuggestion.hide();
			}
			return;
		}
		if(keyCode == KeyCodes.KEY_BACKSPACE){
			if(wordBeingTyped.length() <=  2){
				if(contactWigetSuggestion.isShowing()){
					contactWigetSuggestion.hide();
				}
			}
		}
		if(wordBeingTyped.length() >  2) {

			
			FieldEvent threeCharEntered = getFieldEvent(FieldEvent.THREE_CHAR_ENTERED, wordBeingTyped);
			contactBoxFieldEvent(threeCharEntered);
			
		}
	}
	
	
	private void contactBoxFieldEvent(FieldEvent threeCharEntered) {
		AppUtils.EVENT_BUS.fireEventFromSource(threeCharEntered, userText);
	}

	private void handleOnKeyDownEvent(Event event) {
		int keyCode = event.getKeyCode();
		String elementValue = this.getText();
		caretPosition = IntelliThoughtUtil.getCaretPosition("intelliTextField");
		String textTillCaretPosition = elementValue.substring(0, caretPosition);
		String wordBeingTyped = IntelliThoughtUtil.getWordBeingTyped(textTillCaretPosition, caretPosition);
		
		if(keyCode == KeyCodes.KEY_DOWN || keyCode == KeyCodes.KEY_UP){
			if(contactWigetSuggestion.isShowing()){
				event.preventDefault();
				contactWigetSuggestion.doSelection(keyCode);
			}
			return;
    	}
    	if(keyCode == KeyCodes.KEY_ENTER){

			if(contactWigetSuggestion.isShowing()){
				event.preventDefault();
				/*String text = userWigetSuggestion.getCurrentSelection();
				userWigetSuggestion(text);
				userWigetSuggestion.hide();*/
			}
			return;
    	}
    	if(!elementValue.trim().equals("") && (keyCode == 32)) {
			if(contactWigetSuggestion.isShowing()){
				contactWigetSuggestion.hide();
			}
			if(!wordBeingTyped.trim().equals("")) { 
				FieldEvent wordEntered = getFieldEvent(FieldEvent.WORDENTERED, wordBeingTyped.trim()); 
				contactBoxFieldEvent(wordEntered);
			}
			return;
		}
    	if(keyCode == KeyCodes.KEY_BACKSPACE){
			int start = textTillCaretPosition.trim().lastIndexOf(" ") + 1;
			String nodeType = IntelliThoughtUtil.getNodeType(userText, start, caretPosition);
			if(nodeType.equalsIgnoreCase("a")){
				IntelliThoughtUtil.setCaretPosition(userText, start+1, caretPosition, true);
			}else if(nodeType.equalsIgnoreCase("div")){
				IntelliThoughtUtil.setCaretPosition(userText, start+1, caretPosition, true);
			}
    	}
		
	}

	private void userWigetSuggestion(String text) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void noMoreData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEntityListReceived(EntityList entityList) {
	 
		final int posy = basePanel.getElement().getAbsoluteTop() + basePanel.getElement().getOffsetHeight();
		final int posx = basePanel.getElement().getAbsoluteLeft();
		contactWigetSuggestion.clearList();
		contactWigetSuggestion.setEntityList(entityList);
		contactWigetSuggestion.populateSuggestions();
		if(!contactWigetSuggestion.isShowing()){
			contactWigetSuggestion.show();
			contactWigetSuggestion.setWidth(basePanel.getElement().getOffsetWidth() - 10 + "px");

			contactWigetSuggestion.setPopupPosition(posx, posy);
			contactWigetSuggestion.setFirstSelection();
		}
		
	}

	@Override
	public void onEntityListUpdated() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCurrentView(Entity entity) {
		// TODO Auto-generated method stub
		
	}

	public HashMap<String, Entity> getThoseHaveContactIdUSerIdMap() {
		return thoseHaveContactIdUSerIdMap;
	}

	public void setThoseHaveContactIdUSerIdMap(
			HashMap<String, Entity> thoseHaveContactIdUSerIdMap) {
		this.thoseHaveContactIdUSerIdMap = thoseHaveContactIdUSerIdMap;
	}

	public HashMap<String, Entity> getThoseHaveContactIdMap() {
		return thoseHaveContactIdMap;
	}

	public void setThoseHaveContactIdMap(
			HashMap<String, Entity> thoseHaveContactIdMap) {
		this.thoseHaveContactIdMap = thoseHaveContactIdMap;
	}

	public HashMap<String, String> getPlainEmailsMap() {
		return plainEmailsMap;
	}

	public void setPlainEmailsMap(HashMap<String, String> plainEmailsMap) {
		this.plainEmailsMap = plainEmailsMap;
	}
}



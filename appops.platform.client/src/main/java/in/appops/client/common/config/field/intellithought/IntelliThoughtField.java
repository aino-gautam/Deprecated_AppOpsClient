package in.appops.client.common.config.field.intellithought;

import in.appops.client.common.config.field.BaseField;
import in.appops.client.common.config.field.ImageField.ImageFieldConstant;
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
import in.appops.platform.core.util.EntityList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasText;


/**
* Field class to represent a {@link IntelliThoughtField} .
* @author pallavi@ensarm.com
*<p>
<h3>Configuration</h3>
<a href="IntelliThoughtField.IntelliThoughtFieldConstant.html">Available configurations</a>
</p>
<p>
<h3>Example</h3>
IntelliThoughtField intellithoughtField = new IntelliThoughtField();;<br>
Configuration conf = new Configuration();
conf.setPropertyByName(IntelliThoughtFieldConstant.BF_PCLS, "intelliThoughtField");
conf.setPropertyByName(IntelliThoughtFieldConstant.BF_SUGGESTION_TEXT, "Any thoughts");
conf.setPropertyByName(IntelliThoughtFieldConstant.FIRE_EDITINITIATED_EVENT, true);
conf.setPropertyByName(IntelliThoughtFieldConstant.FIRE_THREECHARENTERED_EVENT, true);
conf.setPropertyByName(IntelliThoughtFieldConstant.FIRE_WORDENTERED_EVENT, true);
conf.setPropertyByName(IntelliThoughtFieldConstant.INTLTHT_ENTPROP, "name");
conf.setPropertyByName(IntelliThoughtFieldConstant.INTLTHT_OPRTION, "spacemanagement.SpaceManagementService.getLinkSuggestions");
conf.setPropertyByName(IntelliThoughtFieldConstant.INTLTHT_MAXCHARLEN, 3);
conf.setPropertyByName(IntelliThoughtFieldConstant.INTLTHT_QUERY_MAXRESULT, 10);
conf.setPropertyByName(IntelliThoughtFieldConstant.BF_ID, "intelliTextField");
conf.setPropertyByName(IntelliThoughtFieldConstant.INTLTHT_SUGGESTIONLBL_PCLS, "appops-intelliThoughtSuggestionLabel");
intellithoughtField.setConfiguration(conf);<br>
intellithoughtField.configure();<br>
intellithoughtField.create();<br>
</p>*/

public class IntelliThoughtField extends BaseField implements HasText, HasHTML, EventListener, FieldEventHandler{
	
	protected LinkedSuggestion linkedSuggestion;
	protected Element intelliText;
	protected static int caretPosition;  
	private ArrayList<Entity> linkedUsers;
	private ArrayList<Entity> linkedSpaces;
	private ArrayList<Entity> linkedEntities;
	
	private Logger logger = Logger.getLogger(getClass().getName());
	
	public IntelliThoughtField(){
		init();
	}
	
	/**
	 * Method initializes class variables. 
	 */
	private void init() {
		try {
			logger.log(Level.INFO,"[IntelliThoughtField]:: In init  method ");
			linkedSuggestion = new LinkedSuggestion(true , getEntPropToDisplay(), getSuggestionLblPrimaryCss(),getLinkedSuggestionPopupCss(),
					getSuggestionLoaderImgBlobId(), getSuggestionLoaderImagePrimCss(), getSuggestionHoverCss());
			linkedEntities = new ArrayList<Entity>();
			linkedUsers = new ArrayList<Entity>();
			linkedSpaces = new ArrayList<Entity>();
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[IntelliThoughtField]::Exception In init  method :"+e);
		}
	}
	
	@Override
	public void configure() {
		logger.log(Level.INFO,"[IntelliThoughtField]:: In configure  method ");
		try {
			if (getMaxCharLengh() != null) {
				this.getElement().setAttribute(IntelliThoughtFieldConstant.INTLTHT_MAXCHARLEN,getMaxCharLengh());
			}

			intelliText = DOM.createDiv();
			intelliText.setClassName(getBaseFieldPrimCss());
			intelliText.setId(getBaseFieldId());
			intelliText.setAttribute(IntelliThoughtFieldConstant.INTLTHT_CONTENTEDITABLE,isContentEditable());
			if(getSuggestionText()!=null){
				this.setText(getSuggestionText());
			}

			if (getBasePanelPrimCss() != null)
				getBasePanel().setStylePrimaryName(getBasePanelPrimCss());
			if (getBasePanelDependentCss() != null)
				getBasePanel().addStyleName(getBasePanelDependentCss());
			
			Event.setEventListener(intelliText, this);
			Event.sinkEvents(intelliText, Event.ONCLICK | Event.KEYEVENTS);
			AppUtils.EVENT_BUS.addHandlerToSource(FieldEvent.TYPE, intelliText, this);
			linkedSuggestion.addHandle(this);
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[IntelliThoughtField]::Exception In configure  method :"+e);
		}
	}
	
	@Override
	public void create() {
		try {
			logger.log(Level.INFO, "[IntelliThoughtField] ::In create method ");
			getBasePanel().getElement().appendChild(intelliText);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[IntelliThoughtField] ::Exception in create method :"+e);
		}
	}

	@Override
	public void clear() {
		this.setText("");
	}

	@Override
	public void reset() {
		
	}

	/**
	 * This returns the text in the contenteditable div.
	 * There seems to be an issue with innerText. String operation application were failing on the innerText like trim() etc.
	 * Hence, to get the text. First have taken the innerHTML and then replaced all the space characters with " ".
	 * Then putting this HTML in a HTML Element and taken its text value. 
	 */
	@Override
	public String getText() {
		try {
			logger.log(Level.INFO, "[IntelliThoughtField] ::In getText method ");
			String innerHtml = intelliText.getInnerHTML();
			innerHtml = innerHtml.replaceAll("&nbsp;", " ");

			HTML htmlEle = new HTML(innerHtml);
			String text = htmlEle.getText();
			return text;
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[IntelliThoughtField] ::Exception in getText method :"+e);
		}
		return null;
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
		try {
			logger.log(Level.INFO, "[IntelliThoughtField] ::In getHTML method ");
			String innerHtml = intelliText.getInnerHTML();
			return innerHtml;
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[IntelliThoughtField] ::Exception in getHTML method :"+e);
		}
		return null;
	}

	@Override
	public void setHTML(String html) {
		intelliText.setInnerHTML(html);
	}

	protected void fireIntelliThoughtFieldEvent(FieldEvent fieldEvent) {
		AppUtils.EVENT_BUS.fireEventFromSource(fieldEvent, intelliText);
	}

	/**
	 * Returns and Field Event with event type and event data
	 * @param type
	 * @param data
	 * @return
	 */
	protected FieldEvent getFieldEvent(int type, String data){
		try {
			logger.log(Level.INFO, "[IntelliThoughtField] ::In getFieldEvent method ");
			FieldEvent fieldEvent = new FieldEvent();
			fieldEvent.setEventType(type);			
			fieldEvent.setEventData(data);
			return fieldEvent;
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[IntelliThoughtField] ::Exception in getFieldEvent method :"+e);
		}
		return null;
	}

	
	/**
	 * Event received are handled here.
	 */
	@Override
	public void onFieldEvent(FieldEvent event) {
		try {
			logger.log(Level.INFO, "[IntelliThoughtField] ::In onFieldEvent method ");
			int eventType = event.getEventType();
			String eventData = (String) event.getEventData();
			
			if(this.isVisible()) {
				if(eventType == FieldEvent.THREE_CHAR_ENTERED) {
					handleThreeCharEnteredEvent(eventData);
				} else if(eventType == FieldEvent.SUGGESTION_CLICKED ){
					linkSuggestion(eventData);
				}
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[IntelliThoughtField] ::Exception in onFieldEvent method :"+e);
		}
	}

	@Override
	public void onBrowserEvent(Event event) {
		try {
			logger.log(Level.INFO, "[IntelliThoughtField] ::In onBrowserEvent method ");
			super.onBrowserEvent(event);
			int eventCode = DOM.eventGetType(event);

			switch (eventCode) { 
			    case Event.ONCLICK: 
			    	if(this.getText().equalsIgnoreCase(getSuggestionText())){
						this.setText("");
					}
			    	if(isFireEditInitatedEvent()){
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
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[IntelliThoughtField] ::Exception in onBrowserEvent method :"+e);
		}
    }
	
	/**
	 * Method handles key down event.
	 * @param event
	 */
	public void handleOnKeyDownEvent(Event event) {
		try {
			logger.log(Level.INFO, "[IntelliThoughtField] ::In handleOnKeyDownEvent method ");
			int keyCode = event.getKeyCode();
			String elementValue = this.getText();
			caretPosition = IntelliThoughtUtil.getCaretPosition(getBaseFieldId());
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
					if(isFireWordEnteredEvent()){
						FieldEvent wordEntered = getFieldEvent(FieldEvent.WORDENTERED, getText().trim());
						fireIntelliThoughtFieldEvent(wordEntered);
					}
				}
				return;
			}
			if(keyCode == KeyCodes.KEY_BACKSPACE){
				int start = textTillCaretPosition.trim().lastIndexOf(" ") + 1;
				String nodeType = IntelliThoughtUtil.getNodeType(intelliText, start, caretPosition);
				if(nodeType.equalsIgnoreCase("a")){
					IntelliThoughtUtil.setCaretPosition(intelliText, start+1, caretPosition, true);
				}
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[IntelliThoughtField] ::Exception in handleOnKeyDownEvent method :"+e);
		}
		
	}
	
	/**
	 * Method collects selected suggestion and add it to the liked entities list.
	 * @param entity
	 */
	protected void collectSelectedSuggestion(Entity entity) {
		try {
			logger.log(Level.INFO, "[IntelliThoughtField] ::In collectSelectedSuggestion method ");
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
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[IntelliThoughtField] ::Exception in collectSelectedSuggestion method :"+e);
		}
		
	}

	protected void linkSuggestion(String text) {
		try {
			logger.log(Level.INFO, "[IntelliThoughtField] ::In linkSuggestion method ");
			String elementValue = this.getText();
			String textTillCaretPosition = elementValue.substring(0, caretPosition);
			
			if(linkedSuggestion.isShowing()){
				
				Anchor tag = new Anchor(text);
				tag.setStylePrimaryName(getLinkedSuggestionPrimaryCss());
				String tagHtml = tag.getElement().getString();
				
				int start = IntelliThoughtUtil.checkPreviousWord(textTillCaretPosition, text);
				
				if(start == -1){
					start = textTillCaretPosition.trim().lastIndexOf(" ") + 1;
				}
				IntelliThoughtUtil.setCaretPosition(intelliText, start, caretPosition, false);
				IntelliThoughtUtil.insertNodeAtCaret(tagHtml); 
			}
			linkedSuggestion.hide();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[IntelliThoughtField] ::Exception in linkSuggestion method :"+e);
		}
		
	}
	
	/**
	 * Method handles key up event.
	 * @param event
	 */
	protected void handleOnKeyUpEvent(Event event){
		try {
			logger.log(Level.INFO, "[IntelliThoughtField] ::In handleOnKeyUpEvent method ");
			int keyCode = event.getKeyCode();

			String elementValue = this.getText();
			caretPosition = IntelliThoughtUtil.getCaretPosition(getBaseFieldId());
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
				if(isFireThreeCharEnteredEvent()){
					FieldEvent threeCharEntered = getFieldEvent(FieldEvent.THREE_CHAR_ENTERED, wordBeingTyped);
					fireIntelliThoughtFieldEvent(threeCharEntered);
				}
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[IntelliThoughtField] ::Exception in handleOnKeyUpEvent method :"+e);
		}
	}
	
	public void addFieldEventHandler(FieldEventHandler handler){
		AppUtils.EVENT_BUS.addHandlerToSource(FieldEvent.TYPE, intelliText, handler);
	}

	
	@SuppressWarnings("unchecked")
	private void handleThreeCharEnteredEvent(String eventData) {

		final DefaultExceptionHandler	exceptionHandler	= new DefaultExceptionHandler();
		final DispatchAsync				dispatch			= new StandardDispatchAsync(exceptionHandler);

		try {
			logger.log(Level.INFO, "[IntelliThoughtField] ::In handleThreeCharEnteredEvent method ");
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
			StandardAction action = new StandardAction(EntityList.class, getOperationName(), map);
			dispatch.execute(action, new AsyncCallback<Result<EntityList>>() {
				
				
				public void onFailure(Throwable caught) {
					linkedSuggestion.hide();
					caught.printStackTrace();
				}
				
				
				public void onSuccess(Result<EntityList> result) {
					EntityList linkedSuggestionList = result.getOperationResult();
					if(linkedSuggestionList != null && !linkedSuggestionList.isEmpty()) {
						linkedSuggestion.setEntityList(linkedSuggestionList);
						linkedSuggestion.setEntPropToDisplay(getEntPropToDisplay());
						linkedSuggestion.populateSuggestions();
						linkedSuggestion.setFirstSelection();
					} else {
						linkedSuggestion.hide();
					}
				}
			});
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[IntelliThoughtField] ::Exception in handleThreeCharEnteredEvent method :"+e);
		}
		
	}
	
	public IntelliThought getIntelliThought(){
		try {
			logger.log(Level.INFO, "[IntelliThoughtField] ::In getIntelliThought method ");
			IntelliThought intelliThought = new IntelliThought();
			
			intelliThought.setIntelliText(getText());
			intelliThought.setIntelliHtml(getHTML());
			intelliThought.setLinkedEntities(linkedEntities);
			intelliThought.setLinkedSpaces(linkedSpaces);
			intelliThought.setLinkedUsers(linkedUsers);
			
			return intelliThought;
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[IntelliThoughtField] ::Exception in getIntelliThought method :"+e);
		}
		return null;
	}

	/****************************************************************************/
	
	/**
	 * Method read maxCharLength from configuration and return.
	 * @return maxCharLength
	 */
	private String getMaxCharLengh() {
		String maxCharLength = null;
		try {
			logger.log(Level.INFO, "[IntelliThoughtField] ::In getMaxCharLengh method ");
			if (getConfigurationValue(IntelliThoughtFieldConstant.INTLTHT_MAXCHARLEN) != null) {
				maxCharLength = getConfigurationValue(IntelliThoughtFieldConstant.INTLTHT_MAXCHARLEN).toString();
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[IntelliThoughtField] ::Exception in getMaxCharLengh method :"+e);
		}
		return maxCharLength;
	}
	
	/**
	 * Method read ContentEditable property from configuration and return.
	 * @return isContentEditable
	 */
	private String isContentEditable() {
		String isContentEditable = "true";
		try {
			logger.log(Level.INFO, "[IntelliThoughtField] ::In isContentEditable method ");
			if (getConfigurationValue(IntelliThoughtFieldConstant.INTLTHT_CONTENTEDITABLE) != null) {
				isContentEditable = getConfigurationValue(IntelliThoughtFieldConstant.INTLTHT_CONTENTEDITABLE).toString();
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[IntelliThoughtField] ::Exception in isContentEditable method :"+e);
		}
		return isContentEditable;
	}
	
	/**
	 * Method read isFireThreeCharEnteredEvent property from configuration and return.
	 * @return isFireThreeCharEnteredEvent
	 */
	protected boolean isFireThreeCharEnteredEvent() {
		Boolean isFireThreeCharEnteredEvent = true;
		try {
			logger.log(Level.INFO, "[IntelliThoughtField] ::In isFireThreeCharEnteredEvent method ");
			if (getConfigurationValue(IntelliThoughtFieldConstant.FIRE_THREECHARENTERED_EVENT) != null) {
				
				isFireThreeCharEnteredEvent = Boolean.parseBoolean(getConfigurationValue(IntelliThoughtFieldConstant.INTLTHT_CONTENTEDITABLE).toString());
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[IntelliThoughtField] ::Exception in isFireThreeCharEnteredEvent method :"+e);
		}
		return isFireThreeCharEnteredEvent;
	}
	
	/**
	 * Method read isFireWordEnteredEvent property from configuration and return.
	 * @return isFireWordEnteredEvent
	 */
	protected boolean isFireWordEnteredEvent() {
		Boolean isFireWordEnteredEvent = true;
		try {
			logger.log(Level.INFO, "[IntelliThoughtField] ::In isFireWordEnteredEvent method ");
			if (getConfigurationValue(IntelliThoughtFieldConstant.FIRE_THREECHARENTERED_EVENT) != null) {
				
				isFireWordEnteredEvent = Boolean.parseBoolean(getConfigurationValue(IntelliThoughtFieldConstant.INTLTHT_CONTENTEDITABLE).toString());
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[IntelliThoughtField] ::Exception in isFireWordEnteredEvent method :"+e);
		}
		return isFireWordEnteredEvent;
	}
	
	/**
	 * Method read isFireEditInitatedEvent property from configuration and return.
	 * @return isFireEditInitatedEvent
	 */
	protected boolean isFireEditInitatedEvent() {
		Boolean isFireEditInitatedEvent = true;
		try {
			logger.log(Level.INFO, "[IntelliThoughtField] ::In isFireEditInitatedEvent method ");
			if (getConfigurationValue(IntelliThoughtFieldConstant.FIRE_THREECHARENTERED_EVENT) != null) {
				
				isFireEditInitatedEvent = Boolean.parseBoolean(getConfigurationValue(IntelliThoughtFieldConstant.INTLTHT_CONTENTEDITABLE).toString());
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[IntelliThoughtField] ::Exception in isFireEditInitatedEvent method :"+e);
		}
		return isFireEditInitatedEvent;
	}
	
	/**
	 * Method returns the query name;
	 * @return
	 */
	private String getQueryName() {
		String query = null;
		try {
			logger.log(Level.INFO,"[IntelliThoughtField]:: In getQueryName  method ");
			if(getConfigurationValue(IntelliThoughtFieldConstant.INTLTHT_QUERYNAME) != null) {
				query = (String) getConfigurationValue(IntelliThoughtFieldConstant.INTLTHT_QUERYNAME);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[IntelliThoughtField]::Exception In getQueryName  method :"+e);
		}
		return query;
	}
	
	/**
	 * Method returns the entity property to show in the listbox;
	 * @return
	 */
	private String getEntPropToDisplay() {
		String entprop = null;
		try {
			logger.log(Level.INFO,"[IntelliThoughtField]:: In getEntPropToDisplay  method ");
			if(getConfigurationValue(IntelliThoughtFieldConstant.INTLTHT_ENTPROP) != null) {
				entprop = (String) getConfigurationValue(IntelliThoughtFieldConstant.INTLTHT_ENTPROP);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[IntelliThoughtField]::Exception In getEntPropToDisplay  method :"+e);
		}
		return entprop;
	}
	
	/**
	 * Method returns the max result for query.
	 * @return
	 */
	private Integer getQueryMaxResult() {
		Integer maxResult = 10;
		try {
			logger.log(Level.INFO,"[IntelliThoughtField]:: In getQueryMaxResult  method ");
			if(getConfigurationValue(IntelliThoughtFieldConstant.INTLTHT_QUERY_MAXRESULT) != null) {
				maxResult =(Integer) getConfigurationValue(IntelliThoughtFieldConstant.INTLTHT_QUERY_MAXRESULT);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[IntelliThoughtField]::Exception In getQueryMaxResult  method :"+e);
		}
		return maxResult;
	}
	
	/**
	 * Method returns the operation to execute.
	 * @return
	 */
	private String getOperationName() {
		String operation = null;
		try {
			logger.log(Level.INFO,"[IntelliThoughtField]:: In getOperationName  method ");
			if(getConfigurationValue(IntelliThoughtFieldConstant.INTLTHT_OPRTION) != null) {
				operation =(String) getConfigurationValue(IntelliThoughtFieldConstant.INTLTHT_OPRTION);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[IntelliThoughtField]::Exception In getOperationName  method :"+e);
		}
		return operation;
	}
	
	/**
	 * Returns the primary style to be applied linked suggestion.
	 * If the style is not provided through configuration default is returned
	 * @return
	 */
	protected String getLinkedSuggestionPrimaryCss() {
		String primaryCss = "appops-LinkField";
		try {
			logger.log(Level.INFO,"[IntelliThoughtField]:: In getLinkedSuggestionPrimaryCss  method ");
			if(getConfigurationValue(IntelliThoughtFieldConstant.INTLTHT_LNKSUGGESTION_PCLS) != null) {
				primaryCss = getConfigurationValue(IntelliThoughtFieldConstant.INTLTHT_LNKSUGGESTION_PCLS).toString();
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[IntelliThoughtField]::Exception In getLinkedSuggestionPrimaryCss  method :"+e);

		}
		return primaryCss;
	}
	
	/**
	 * Returns the primary style to be applied to suggestion label.
	 * If the style is not provided through configuration default is returned
	 * @return
	 */
	protected String getSuggestionLblPrimaryCss() {
		String primaryCss = "appops-intelliThoughtSuggestionLabel";
		try {
			logger.log(Level.INFO,"[IntelliThoughtField]:: In getSuggestionLblPrimaryCss  method ");
			if(getConfigurationValue(IntelliThoughtFieldConstant.INTLTHT_SUGGESTIONLBL_PCLS) != null) {
				primaryCss = getConfigurationValue(IntelliThoughtFieldConstant.INTLTHT_SUGGESTIONLBL_PCLS).toString();
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[IntelliThoughtField]::Exception In getSuggestionLblPrimaryCss  method :"+e);

		}
		return primaryCss;
	}
	
	/**
	 * Returns the primary style to be applied to linked suggestion.
	 * If the style is not provided through configuration default is returned
	 * @return
	 */
	protected String getLinkedSuggestionPopupCss() {
		String primaryCss = "appops-intelliThoughtLinkedSuggestionPopup";
		try {
			logger.log(Level.INFO,"[IntelliThoughtField]:: In getLinkedSuggestionPopupCss  method ");
			if(getConfigurationValue(IntelliThoughtFieldConstant.INTLTHT_SUGGESTIONPOPUP_PCLS) != null) {
				primaryCss = getConfigurationValue(IntelliThoughtFieldConstant.INTLTHT_SUGGESTIONPOPUP_PCLS).toString();
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[IntelliThoughtField]::Exception In getLinkedSuggestionPopupCss  method :"+e);

		}
		return primaryCss;
	}
	
	/**
	 * Returns the primary style to be applied to linked suggestion.
	 * If the style is not provided through configuration default is returned
	 * @return
	 */
	protected String getSuggestionHoverCss() {
		String primaryCss = "appops-intelliThoughtSuggestionSelection";
		try {
			logger.log(Level.INFO,"[IntelliThoughtField]:: In getSuggestionHoverCss  method ");
			if(getConfigurationValue(IntelliThoughtFieldConstant.INTLTHT_SUGGESTION_HOVER_PCLS) != null) {
				primaryCss = getConfigurationValue(IntelliThoughtFieldConstant.INTLTHT_SUGGESTION_HOVER_PCLS).toString();
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[IntelliThoughtField]::Exception In getSuggestionHoverCss  method :"+e);

		}
		return primaryCss;
	}
	
	/**
	 * Method return the suggestion loader image blobId.
	 * @return
	 */
	private String getSuggestionLoaderImgBlobId() {
		String blobId = "images/opptinLoader.gif";
		try {
			logger.log(Level.INFO, "[ImageField] ::In getSuggestionLoaderBlobId method ");
			if (getConfigurationValue(ImageFieldConstant.IMGFD_BLOBID) != null) {
				blobId = getConfigurationValue(ImageFieldConstant.IMGFD_BLOBID).toString();
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[IntelliThoughtField]::Exception In getSuggestionLoaderBlobId  method :"+e);
		}
		return blobId;
	}
	
	
	/**
	 * Returns the primary style to be applied to the loader image.
	 * @return primary style of suggestion loader.
	 */
	protected String getSuggestionLoaderImagePrimCss() {
		String primaryCss = "appops-intelliThoughtActionImage";
		try {
			logger.log(Level.INFO,"[IntelliThoughtField]:: In getLoaderImagePrimCss  method ");
			if(getConfigurationValue(IntelliThoughtFieldConstant.INTLTHT_LOADERIMG_PCLS) != null) {
				primaryCss = getConfigurationValue(IntelliThoughtFieldConstant.INTLTHT_LOADERIMG_PCLS).toString();
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[IntelliThoughtField]::Exception In getLoaderImagePrimCss  method :"+e);
		}
		return primaryCss;
	}
	
	/**
	 * Overriden method from BaseField to set inline error.
	 */
	@Override
	public void setErrorInline () {
		try {
			logger.log(Level.INFO, "[IntelliThoughtField] ::In setErrorInline method ");
			intelliText.addClassName(getErrorMsgCls());
			intelliText.addClassName(getErrorIconCls());
			
			String errorMsg = "";
			if(!getActiveErrors().isEmpty()){
				for(String error : getActiveErrors()) {
					errorMsg = errorMsg + error + ". ";
				}
				intelliText.setTitle(errorMsg);
			}else
				intelliText.setTitle(getInvalidMsg());
			
			if(getErrorIconBlobId()!=null)
				intelliText.getStyle().setProperty("background", "white url("+ getErrorIconBlobId()+") no-repeat right	center");
			
		} catch (Exception e) {
		    logger.log(Level.SEVERE, "[IntelliThoughtField] ::Exception In setErrorInline method "+e);
		}
	}
	
	/**
	 * Overriden method from BaseField to clear inline msg .
	 */
	public void clearInlineMsg () {
		try {
			intelliText.removeClassName(getErrorMsgCls());
			intelliText.removeClassName(getErrorIconCls());
			intelliText.removeClassName(getValidFieldMsgCls());
			intelliText.removeClassName(getValidFieldIconCls());
			intelliText.setTitle(getTitle());
			
			if(intelliText.getStyle().getProperty("background")!=null)
				intelliText.getStyle().clearProperty("background");
			
		} catch (Exception e) {
			
			logger.log(Level.SEVERE, "[IntelliThoughtField] ::Exception In clearInlineMsg method "+e);
		}
	}
	
	/****************************************************************************/
	
	public interface IntelliThoughtFieldConstant  extends BaseFieldConstant{
		
		/** Specifies the max char user can enter intellithought field ***/
		public static final String INTLTHT_MAXCHARLEN = "maxLength";
		
		/** Specifies if contents are editable. Defaults to true. **/ 
		public static final String INTLTHT_CONTENTEDITABLE = "contenteditable";
		
		/** Specifies the query name. **/
		public static final String INTLTHT_QUERYNAME = "queryName";
		
		/** Specifies the name of the operation to execute to fetch the results.. **/
		public static final String INTLTHT_OPRTION = "operation";
		
		/** Specifies the result size of the query. **/
		public static final String INTLTHT_QUERY_MAXRESULT = "queryMaxresult";
		
		/** Specifies the entity property that will be displayed n suggestion. **/
		public static final String INTLTHT_ENTPROP = "propertyToDisplay";	
		
		/** Species if event should be fired when user enters 3 characters. **/
		public static final String FIRE_THREECHARENTERED_EVENT = "fireThreeCharEnteredEvent";
		
		/** Species if event should be fired when user press enter key. **/
		public static final String FIRE_WORDENTERED_EVENT = "fireWordEnteredEvent";
		
		/** Species if event should be fired when user starts editing intellithought field. **/
		public static final String FIRE_EDITINITIATED_EVENT = "fireEditInitatedEvent";		
		
		/** Species primary css used for linked suggestions. Defaults to appops-LinkField **/
		public static final String INTLTHT_LNKSUGGESTION_PCLS = "linkFieldCss";
		
		/** Species primary css used for suggestion label. **/
		public static final String INTLTHT_SUGGESTIONLBL_PCLS = "suggestionLblCss";
		
		/** Species primary css used for suggestion popup. **/
		public static final String INTLTHT_SUGGESTIONPOPUP_PCLS = "suggestionPopupCss";
		
		/** Species primary css that will be applied when user hover to that listed suggestion. **/
		public static final String INTLTHT_SUGGESTION_HOVER_PCLS = "suggestionHoverCss";
		
		/** Species loader image blobId. **/
		public static final String INTLTHT_LOADERIMG_BLOBID = "loaderImgBlobId";
		
		/** Species primary css used for loader image used while showing suggestions. **/
		public static final String INTLTHT_LOADERIMG_PCLS = "loaderImgPrimaryCss";
	}

}
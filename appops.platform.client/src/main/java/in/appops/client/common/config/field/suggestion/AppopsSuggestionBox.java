package in.appops.client.common.config.field.suggestion;

import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AppopsSuggestionBox extends Composite implements SelectionHandler<SuggestOracle.Suggestion>, ClickHandler, FocusHandler, KeyPressHandler,KeyUpHandler,BlurHandler{
	
	private VerticalPanel basePanel;
	private AppopsSuggestion selectedSuggestion;
	private SuggestionOracle oracle = new SuggestionOracle();
	private SuggestBox suggestBox = new SuggestBox(oracle);
	private Boolean isAutoSuggestion = false;
	private HandlerRegistration focusHandler = null ;
	private HandlerRegistration selectionHandler = null ;
	private HandlerRegistration keyPressHandler = null ;
	private HandlerRegistration keyUpHandler = null ;
	private Logger logger = Logger.getLogger(getClass().getName());

	public AppopsSuggestionBox() {
		basePanel = new VerticalPanel();
		initWidget(basePanel);
	}
	
	/***************************************** *******************************/
	public void createUi(){
		try {
			this.basePanel.add(suggestBox);
			selectionHandler = suggestBox.addSelectionHandler(this);
	     	keyUpHandler = suggestBox.getTextBox().addKeyUpHandler(this);
	     	keyPressHandler = suggestBox.getTextBox().addKeyPressHandler(this);
			focusHandler = suggestBox.getTextBox().addFocusHandler(this);
			
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[AppopsSuggestionBox] ::Exception in createUi method :"+e);
		}
	}
	
	/**
	 * Method removes registered handlers.
	 */
	
	public void removeRegisteredHandlers() {
		
		try {
						
			if(keyUpHandler!=null)
				keyUpHandler.removeHandler();
			
			if(keyPressHandler!=null)
				keyPressHandler.removeHandler();
			
			if(focusHandler!=null)
				focusHandler.removeHandler();
			
			if(selectionHandler!=null)
				selectionHandler.removeHandler();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[AppopsSuggestionBox] ::Exception in removeRegisteredHandlers method :"+e);
		}
	}
	
	public void clearSuggestionTextBox(){
		suggestBox.setText("");
	}
	
	public TextBoxBase getTextBox(){
		return suggestBox.getTextBox();
	}
	
	
	public void setSelectedSuggestion(AppopsSuggestion selectedSuggestion) {
		this.selectedSuggestion = selectedSuggestion;
	}

	public SuggestBox getSuggestBox() {
		return suggestBox;
	}

	public AppopsSuggestion getSelectedSuggestion() {
		return selectedSuggestion;
	}
	
	
	/****************************** ************************************************************/
	
	/**
	 *  Methods sets enable property to the suggestion box.
	 * @param isEnabled
	 */
	public void setEnabled(Boolean isEnabled) {
		try {
			suggestBox.getTextBox().setEnabled(isEnabled);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[AppopsSuggestionBox] ::Exception in setEnabled method :"+e);
		}
	}
	
	/**
	 * Methods sets the query name to oracle..
	 * @param queryName
	 */
	public void setQueryName(String queryName) {
		try {
			oracle.setQueryName(queryName);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[AppopsSuggestionBox] ::Exception in setQueryName method :"+e);
		}
	}
	
	/**
	 * Sets the operation name to oracle.
	 * @param name
	 */
	public void setOperationName(String name) {
		try {
			oracle.setOperationName(name);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[AppopsSuggestionBox] ::Exception in setOperationName method :"+e);
		}
	}
	
	/**
	 * Sets the query restrictions to set to oracle.
	 * @param name
	 */
	public void setQueryRestrictions(HashMap<String, Object> map) {
		try {
			oracle.setRestriction(map);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[AppopsSuggestionBox] ::Exception in setQueryRestrictions method :"+e);
		}
	}

	/**
	 * Method sets the max result of the query result.
	 * @param max
	 */
	public void setQueryMaxResult(int max) {
		try {
			oracle.setMaxResult(max);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[AppopsSuggestionBox] ::Exception in setQueryMaxResult method :"+e);
		}
	}

	/**
	 * Sets the property to display in suggestion box..
	 * @param propertyByName
	 */
	public void setPropertyToDisplay(String propertyByName) {
		try {
			oracle.setEntityPropToDisplay(propertyByName);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[AppopsSuggestionBox] ::Exception in setPropertyToDisplay method :"+e);
		}
	}

	/**
	 * Sets if autosuggetsion is true or false.
	 * @param val
	 */
	public void setAutoSuggestion(Boolean val) {
		try {
			isAutoSuggestion = val;
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[AppopsSuggestionBox] ::Exception in setAutoSuggestion method :"+e);
		}
	}
	
	/**
	 * Set if suggetion box works with static data or not.
	 * @param val
	 */
	public void setStaticSuggestionBox(Boolean val) {
		try {
			oracle.IsStaticSuggestionBox(val);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[AppopsSuggestionBox] ::Exception in setStaticSuggestionBox method :"+e);
		}
	}
	
	/**
	 * Sets the items to display in suggestion box.
	 * @param itemsToDisplay
	 */
	public void setItemsToDisplay(ArrayList<String> itemsToDisplay) {
		try {
			oracle.setItemsToDisplay(itemsToDisplay);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[AppopsSuggestionBox] ::Exception in setItemsToDisplay method :"+e);
		}
	}

	/**
	 * Sets the flag to oracle if suggestions to come using searchquery or not.
	 * @param val
	 */
	public void setIsSearchQuery(Boolean val) {
		try {
			oracle.IsSearchQuery(val);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[AppopsSuggestionBox] ::Exception in setIsSearchQuery method :"+e);
		}
	}
	
	/********************************** ***************************************/
	@Override
	public void onFocus(FocusEvent event) {
		try {
			if(event.getSource().equals(suggestBox.getTextBox())) {
				if(isAutoSuggestion){
					if(getSuggestBox().getText().equals(""))
						getSuggestBox().setText(" ");
					getSuggestBox().showSuggestionList();
					if(getSuggestBox().getText().equals(" "))
						getSuggestBox().setText("");
				}
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[AppopsSuggestionBox] ::Exception in onFocus method :"+e);
		}
	}
	
	@Override
	public void onClick(ClickEvent event) {
		try {
			if(event.getSource().equals(suggestBox.getTextBox())) {
				if(isAutoSuggestion){
					if(getSuggestBox().getText().equals("")){
						getSuggestBox().setText(" ");
					}
					getSuggestBox().showSuggestionList();
					if(getSuggestBox().getText().equals(" ")) {
						getSuggestBox().setText("");
					}
				}
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[AppopsSuggestionBox] ::Exception in onClick method :"+e);
		}
	}
	
	@Override
	public void onSelection(SelectionEvent<Suggestion> event) {
		
		try {
			AppopsSuggestion selectedSuggestion = (AppopsSuggestion) event.getSelectedItem();
			setSelectedSuggestion(selectedSuggestion);
			getSuggestBox().setText(selectedSuggestion.getDisplayString());
			
			FieldEvent fieldEvent = new FieldEvent();
			fieldEvent.setEventSource(this);
			fieldEvent.setEventData(selectedSuggestion);
			fieldEvent.setEventType(FieldEvent.VALUE_SELECTED);
			AppUtils.EVENT_BUS.fireEvent(fieldEvent);
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Level.SEVERE, "[AppopsSuggestionBox] ::Exception in onSelection method :"+e);
		}
	}
	
	@Override
	public void onKeyUp(KeyUpEvent event) {
		try {
			Integer keycode = event.getNativeKeyCode();
			
			if (keycode.equals(KeyCodes.KEY_BACKSPACE)|| keycode.equals(KeyCodes.KEY_DELETE)) {
				fireFieldEvent(FieldEvent.EDITINPROGRESS);
			}
			oracle.setCurrentText(suggestBox.getText());
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[AppopsSuggestionBox] ::Exception In onKeyUp method " + e);
		}
	}
	
	@Override
	public void onBlur(BlurEvent event) {
		try {
			fireFieldEvent(FieldEvent.EDITCOMPLETED);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[AppopsSuggestionBox] ::Exception In onBlur method "+e);
		}
	}

	@Override
	public void onKeyPress(KeyPressEvent event) {
		try {
						
			/** Scheduler is added because when user press backspace ,tab and delete keyPressEvent is not fired. **/ 
			Scheduler.get().scheduleDeferred(new ScheduledCommand() {
				@Override
				public void execute() {
					fireFieldEvent(FieldEvent.EDITINPROGRESS);
				}
			});
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[AppopsSuggestionBox] ::Exception In onKeyPress method "+e);
		}
	}
	
	private void fireFieldEvent(int eventType){
		try {
			FieldEvent fieldEvent = new FieldEvent();
			fieldEvent = new FieldEvent();
			fieldEvent.setEventSource(this);
			fieldEvent.setEventData(suggestBox.getValue());
			fieldEvent.setEventType(eventType);
			AppUtils.EVENT_BUS.fireEvent(fieldEvent);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[AppopsSuggestionBox] ::Exception In fireFieldEvent method "+e);
		}
	}
	
	public Boolean isSuggestionAlreadyExist(){
		
		if(oracle.getStore() != null && !oracle.getStore().isEmpty() ){
			for(AppopsSuggestion suggestion : oracle.getStore()){
				if(suggestion.getDisplayString().equalsIgnoreCase(suggestBox.getValue())){
					return true;
				}
			}
		}
		
		return false;
		
	}

	public void setOpParamMap(HashMap<String, Object> opParamMap) {
		try{
			if(opParamMap !=null)
				oracle.setOpParamMap(opParamMap);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setPrefixToDisplayText(String prefixToDisplayText) {
		try {
			oracle.setPrefixToDisplayText(prefixToDisplayText);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[AppopsSuggestionBox] ::Exception in setPrefixToDisplayText method :"+e);
		}
	}

	public void clearSuggestions() {
		try {
			oracle.getStore().clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SuggestionOracle getOracle() {
		return oracle;
	}
}
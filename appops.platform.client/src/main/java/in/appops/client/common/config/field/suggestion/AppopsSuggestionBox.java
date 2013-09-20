package in.appops.client.common.config.field.suggestion;

import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AppopsSuggestionBox extends Composite implements SelectionHandler<SuggestOracle.Suggestion>, ClickHandler, FocusHandler{
	
	private VerticalPanel basePanel;
	private AppopsSuggestion selectedSuggestion;
	private SuggestionOracle oracle = new SuggestionOracle();
	private SuggestBox suggestBox = new SuggestBox(oracle);
	private Boolean isAutoSuggestion = false;
	private HandlerRegistration clickHandler = null ;
	private HandlerRegistration focusHandler = null ;
	private HandlerRegistration selectionHandler = null ;
	private Logger logger = Logger.getLogger(getClass().getName());

	public AppopsSuggestionBox() {
		basePanel = new VerticalPanel();
		initWidget(basePanel);
	}
	
	/***************************************** *******************************/
	public void createUi(){
		try {
			logger.log(Level.INFO, "[AppopsSuggestionBox] ::In createUi method ");
			this.basePanel.add(suggestBox);
			selectionHandler = suggestBox.addSelectionHandler(this);
	//		clickHandler = suggestBox.getTextBox().addClickHandler(this);
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
			if(clickHandler!=null)
				clickHandler.removeHandler();
			
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
			logger.log(Level.INFO, "[AppopsSuggestionBox] ::In setEnabled method ");
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
			logger.log(Level.INFO, "[AppopsSuggestionBox] ::In setQueryName method ");
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
			logger.log(Level.INFO, "[AppopsSuggestionBox] ::In setOperationName method ");
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
			logger.log(Level.INFO, "[AppopsSuggestionBox] ::In setQueryRestrictions method ");
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
			logger.log(Level.INFO, "[AppopsSuggestionBox] ::In setQueryMaxResult method ");
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
			logger.log(Level.INFO, "[AppopsSuggestionBox] ::In setPropertyToDisplay method ");
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
			logger.log(Level.INFO, "[AppopsSuggestionBox] ::In setAutoSuggestion method ");
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
			logger.log(Level.INFO, "[AppopsSuggestionBox] ::In setStaticSuggestionBox method ");
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
			logger.log(Level.INFO, "[AppopsSuggestionBox] ::In setItemsToDisplay method ");
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
			logger.log(Level.INFO, "[AppopsSuggestionBox] ::In setIsSearchQuery method ");
			oracle.IsSearchQuery(val);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[AppopsSuggestionBox] ::Exception in setIsSearchQuery method :"+e);
		}
	}
	
	/********************************** ***************************************/
	@Override
	public void onFocus(FocusEvent event) {
		try {
			logger.log(Level.INFO, "[AppopsSuggestionBox] ::In onFocus method ");
			if(isAutoSuggestion){
				if(getSuggestBox().getText().equals(""))
					getSuggestBox().setText(" ");
				getSuggestBox().showSuggestionList();
				if(getSuggestBox().getText().equals(" "))
					getSuggestBox().setText("");
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[AppopsSuggestionBox] ::Exception in onFocus method :"+e);
		}
	}
	
	@Override
	public void onClick(ClickEvent event) {
		try {
			logger.log(Level.INFO, "[AppopsSuggestionBox] ::In onClick method ");
			if(getSuggestBox().getText().equals("")){
				getSuggestBox().setText(" ");
			}
			getSuggestBox().showSuggestionList();
			if(getSuggestBox().getText().equals(" ")) {
				getSuggestBox().setText("");
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[AppopsSuggestionBox] ::Exception in onClick method :"+e);
		}
	}
	
	@Override
	public void onSelection(SelectionEvent<Suggestion> event) {
		
		try {
			logger.log(Level.INFO, "[AppopsSuggestionBox] ::In onSelection method ");
			AppopsSuggestion selectedSuggestion = (AppopsSuggestion) event.getSelectedItem();
			setSelectedSuggestion(selectedSuggestion);
			getSuggestBox().setText(selectedSuggestion.getDisplayString());
			//getSuggestBox().getTextBox().setFocus(false);
			
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


}
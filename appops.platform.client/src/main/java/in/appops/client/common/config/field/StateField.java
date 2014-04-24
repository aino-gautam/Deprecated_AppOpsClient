package in.appops.client.common.config.field;


import in.appops.client.common.config.field.suggestion.AppopsSuggestionBox;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.user.client.ui.DockPanel;

/**
Field class to represent a {@link StateField}
@author pallavi@ensarm.com

<p>
<h3>Configuration</h3>
<a href="StateField.StateFieldConstant.html">Available configurations</a>
</p>

<p>
<h3>Example</h3>

StateField stateField = new StateField();<br>
Configuration configuration = new Configuration();<br>
configuration.setPropertyByName(StateFieldConstant.IS_STATIC_BOX, true);<br>
ArrayList<String> days = new ArrayList<String>();<br>
days.add("Sunday");<br>
days.add("Monday");<br>
days.add("Tuesday");<br>
days.add("Wednesday");<br>
configuration.setPropertyByName(StateFieldConstant.ITEMS_LIST, days);<br>
stateField.setConfiguration(conf);<br>
stateField.configure();<br>
stateField.create();<br>

</p>*/

public class StateField extends BaseField implements FieldEventHandler {

	private AppopsSuggestionBox appopsSuggestionBox;
	private Logger logger = Logger.getLogger(getClass().getName());

	public StateField(){
		appopsSuggestionBox = new AppopsSuggestionBox();
		AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE, this);
	}
	
	/****************************************** *******************************/
	
	@Override
	public void create() {
		
		try {
			getBasePanel().add(appopsSuggestionBox,DockPanel.CENTER);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[StateField] ::Exception in create method :"+e);
		}
	}
	
	@Override
	public void configure() {
		try {
			
			setSuggestionInline();
			if(isStaticSuggestionBox()){
				appopsSuggestionBox.setStaticSuggestionBox(isStaticSuggestionBox());
				appopsSuggestionBox.setItemsToDisplay(getFieldItemList());
				appopsSuggestionBox.setPropertyToDisplay(getEntPropToDisplay());
			}else{
				appopsSuggestionBox.setQueryName(getQueryName());
				appopsSuggestionBox.setPropertyToDisplay(getEntPropToDisplay());
				appopsSuggestionBox.setPrefixToDisplayText(getPrefixToDisplayText());
				appopsSuggestionBox.setOperationName(getOperationName());
				appopsSuggestionBox.setQueryRestrictions(getQueryRestrictions());
				appopsSuggestionBox.setIsSearchQuery(isSearchQuery());
				appopsSuggestionBox.setQueryMaxResult(getQueryMaxResult());
				appopsSuggestionBox.setOpParamMap(getOpParamMap());
			}
			
			if (getBaseFieldPrimCss() != null)
				appopsSuggestionBox.getTextBox().setStylePrimaryName(getBaseFieldPrimCss());
			if (getBaseFieldDependentCss() != null)
				appopsSuggestionBox.getTextBox().addStyleName(getBaseFieldDependentCss());

			if (getBasePanelPrimCss() != null)
				getBasePanel().setStylePrimaryName(getBasePanelPrimCss());
			if (getBasePanelDependentCss() != null)
				getBasePanel().addStyleName(getBasePanelDependentCss());
			
			removeRegisteredHandlers();
			appopsSuggestionBox.setAutoSuggestion(isAutosuggestion());
			appopsSuggestionBox.createUi();
			
			appopsSuggestionBox.setEnabled(isEnabled());
			
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[StateField] ::Exception in configure method :"+e);
		}
	}
	
	/**
	 * Method removed registered handlers from field
	 */
	@Override
	public void removeRegisteredHandlers() {
		
		try {
			appopsSuggestionBox.removeRegisteredHandlers();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[StateField] ::Exception in removeRegisteredHandlers method :"+e);
		}
	}

	@Override
	public void reset() {
		
	}
	
	@Override
	public void clear() {
		appopsSuggestionBox.clearSuggestionTextBox();
	}
	
	@Override
	public Object getValue() {
		String suggestion = appopsSuggestionBox.getSuggestBox().getText();
		
		return suggestion;
	}
	
	@Override
	public void setValue(Object value) {
		
		try { 
			super.setValue(value);
			clear();
			appopsSuggestionBox.getSuggestBox().setText(value.toString());
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[StateField] ::Exception In setValue method "+e);
			
		}
		
	}
	
	@Override
	protected void setSuggestionInline () {
		try {
			
			if(getSuggestionText()!=null)
				appopsSuggestionBox.getTextBox().getElement().setPropertyString("placeholder", getSuggestionText());
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[StateField] ::Exception In setSuggestionInline method "+e);
		}
	}
	
	/**
	 * Overriden method from BaseField to clear inline msg .
	 */
	public void clearInlineMsg () {
		try {
			appopsSuggestionBox.getTextBox().removeStyleName(getErrorMsgCls());
			appopsSuggestionBox.getTextBox().removeStyleName(getErrorIconCls());
			appopsSuggestionBox.getTextBox().removeStyleName(getValidFieldMsgCls());
			appopsSuggestionBox.getTextBox().removeStyleName(getValidFieldIconCls());
			appopsSuggestionBox.getTextBox().setTitle("");
			
			if(appopsSuggestionBox.getTextBox().getElement().getStyle().getProperty("background")!=null)
				appopsSuggestionBox.getTextBox().getElement().getStyle().clearProperty("background");
		} catch (Exception e) {
			
			logger.log(Level.SEVERE, "[StateField] ::Exception In clearInlineMsg method "+e);
		}
	}
	
	/**
	 * Overriden method from BaseField to set inline error.
	 */
	@Override
	public void setErrorInline () {
		try {
			appopsSuggestionBox.getTextBox().addStyleName(getErrorMsgCls());
			appopsSuggestionBox.getTextBox().addStyleName(getErrorIconCls());
			
			String errorMsg = "";
			if(!getErrors(getFieldValue()).isEmpty()){
				for(String error : getErrors(getFieldValue())) {
					errorMsg = errorMsg + error + ". ";
				}
				appopsSuggestionBox.getTextBox().setTitle(errorMsg);
			}else
				appopsSuggestionBox.getTextBox().setTitle(getInvalidMsg());
			
			if(getErrorIconBlobId()!=null)
				setCssPropertyToElement(appopsSuggestionBox.getTextBox(), getErrorIconBlobId());
		} catch (Exception e) {
		    logger.log(Level.SEVERE, "[StateField] ::Exception In setErrorInline method "+e);
		
		}
	}
	
	/**
	 * Method set the error message .
	 */
	public void setErrorMsg(){
		try {
			ArrayList<String> errors = new ArrayList<String>();
			errors.add(getInvalidMsg());
			markInvalid(errors);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[StateField] ::Exception in setErrorMsg method :"+e); 
		}
	}
	
	/**
	 * Method clear the error message .
	 */
	public void clearErrorMsg(){
		try {
			clearInvalidMarkers();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[StateField] ::Exception in clearErrorMsg method :"+e); 
		}
	}
	
	/****************************************************************************/
	
	public AppopsSuggestionBox getAppopsSuggestionBox() {
		return appopsSuggestionBox;
	}

	public void setAppopsSuggestionBox(AppopsSuggestionBox appopsSuggestionBox) {
		this.appopsSuggestionBox = appopsSuggestionBox;
	}
	
	/*************************************  ************************************/

	/**
	 * Method returns flag whether work with static data or with query. Defaults to true.
	 * @return
	 */
	private Boolean isStaticSuggestionBox() {
		Boolean isStatic = false;
		try {
			if(getConfigurationValue(StateFieldConstant.IS_STATIC_BOX) != null) {
				isStatic =(Boolean) getConfigurationValue(StateFieldConstant.IS_STATIC_BOX);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[StateField] ::Exception in isStaticSuggestionBox method :"+e);
		}
		return isStatic;
	}
	
	/**
	 * Method returns the max result for query.
	 * @return
	 */
	private Integer getQueryMaxResult() {
		Integer maxResult = 10;
		try {
			if(getConfigurationValue(StateFieldConstant.STFD_QUERY_MAXRESULT) != null) {
				maxResult =(Integer) getConfigurationValue(StateFieldConstant.STFD_QUERY_MAXRESULT);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[StateField] ::Exception in getQueryMaxResult method :"+e);
		}
		return maxResult;
	}
	
	/**
	 * Method returns the list of items to show in field;
	 * @return
	 */
	private ArrayList<String> getFieldItemList() {
		ArrayList<String> listOfItems = null;
		try {
			if(getConfigurationValue(StateFieldConstant.ITEMS_LIST) != null) {
				listOfItems = (ArrayList<String>) getConfigurationValue(StateFieldConstant.ITEMS_LIST);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[StateField] ::Exception in getFieldItemList method :"+e);
		}
		return listOfItems;
	}
	
	private HashMap<String, Object> getOpParamMap() {
		HashMap<String, Object> paramMap = null;
		try {
			if(getConfigurationValue(StateFieldConstant.STFD_OPPARAM_MAP) != null) {
				paramMap =  (HashMap<String, Object>) getConfigurationValue(StateFieldConstant.STFD_OPPARAM_MAP);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[StateField] ::Exception in getOpParamMap method :"+e);
		}
		return paramMap;
	}
	
	
	/**
	 * Method returns the list of restrictions to the query.;
	 * @return
	 */
	private HashMap<String, Object> getQueryRestrictions() {
		HashMap<String, Object> queryRestrictions = null;
		try {
			if(getConfigurationValue(StateFieldConstant.STFD_QUERY_RESTRICTION) != null) {
				queryRestrictions =  (HashMap<String, Object>) getConfigurationValue(StateFieldConstant.STFD_QUERY_RESTRICTION);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[StateField] ::Exception in getQueryRestrictions method :"+e);
		}
		return queryRestrictions;
	}
	
	/**
	 * Method returns the query string to bind with.
	 * @return
	 */
	private String getQueryName() {
		String queryname = null;
		try {
			if(getConfigurationValue(StateFieldConstant.STFD_QUERYNAME) != null) {
				queryname =(String) getConfigurationValue(StateFieldConstant.STFD_QUERYNAME);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[StateField] ::Exception in getQueryName method :"+e);
		}
		return queryname;
	}
	
	/**
	 * Method returns the entity property to show in the stateField;
	 * @return
	 */
	private String getEntPropToDisplay() {
		String entprop = null;
		try {
			if(getConfigurationValue(StateFieldConstant.STFD_ENTPROP) != null) {
				entprop = (String) getConfigurationValue(StateFieldConstant.STFD_ENTPROP);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[StateField] ::Exception in getEntPropToDisplay method :"+e);
		}
		return entprop;
	}
	
	/**
	 * Method returns the prefix word to that should be added before the display text;
	 * @return
	 */
	private String getPrefixToDisplayText() {
		String prefixText = null;
		try {
			if(getConfigurationValue(StateFieldConstant.PREFIX_TO_DISPLAYTEXT) != null) {
				prefixText = (String) getConfigurationValue(StateFieldConstant.PREFIX_TO_DISPLAYTEXT);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[StateField] ::Exception in getPrefixToDisplayText method :"+e);
		}
		return prefixText;
	}
	
	
	/**
	 * Method returns the operation to execute.
	 * @return
	 */
	private String getOperationName() {
		String operation = null;
		try {
			if(getConfigurationValue(StateFieldConstant.STFD_OPRTION) != null) {
				operation =(String) getConfigurationValue(StateFieldConstant.STFD_OPRTION);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[StateField] ::Exception in getOperationName method :"+e);
		}
		return operation;
	}
	
	/**
	 * Method returns flag whether autosuggestion is true or not. Defaults to true.
	 * @return
	 */
	private Boolean isAutosuggestion() {
		Boolean isautoSuggetion = true;
		try {
			if(getConfigurationValue(StateFieldConstant.IS_AUTOSUGGESTION) != null) {
				isautoSuggetion =(Boolean) getConfigurationValue(StateFieldConstant.IS_AUTOSUGGESTION);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[StateField] ::Exception in isAutosuggestion method :"+e);
		}
		return isautoSuggetion;
	}
	
	/**
	 * Method returns flag whether to use searchQuery . Defaults to false.
	 * @return
	 */
	private Boolean isSearchQuery() {
		Boolean isSearchQuery = false;
		try {
			if(getConfigurationValue(StateFieldConstant.IS_SEARCH_QUERY) != null) {
				isSearchQuery =(Boolean) getConfigurationValue(StateFieldConstant.IS_SEARCH_QUERY);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[StateField] ::Exception in isSearchQuery method :"+e);
		}
		return isSearchQuery;
	}
	/************************************ *************************************/
	
	public interface StateFieldConstant extends BaseFieldConstant{
		
		public static final String IS_STATIC_BOX = "isStaticBox";
		
		public static final String ITEMS_LIST = "itemsList";
		
		public static final String STFD_QUERYNAME = "queryName";
		
		public static final String STFD_OPRTION = "operation";
		
		public static final String STFD_QUERY_MAXRESULT = "queryMaxresult";
		
		public static final String STFD_ENTPROP = "propertyToDisplay";
		
		public static final String STFD_QUERY_RESTRICTION = "queryRestriction";
		
		public static final String IS_SEARCH_QUERY = "isSearchQuery";
		
		public static final String IS_AUTOSUGGESTION = "isAutoSuggestion";
		
		public static final String STFD_OPPARAM_MAP = "operationParamMap";
		
		public static final String PREFIX_TO_DISPLAYTEXT = "prefixToDisplayText";
	}
	
	@Override
	public void onFieldEvent(FieldEvent event) {
		try {
			int eventType = event.getEventType();
			if (event.getEventSource() instanceof AppopsSuggestionBox && event.getEventSource().equals(appopsSuggestionBox)) {
				
				FieldEvent fieldEvent = new FieldEvent();
				fieldEvent.setEventData(event.getEventData());
				fieldEvent.setEventSource(this);
				
				if (eventType == FieldEvent.VALUE_SELECTED) {
					fieldEvent.setEventType(FieldEvent.SUGGESTION_SELECTED);
				} else if (eventType == FieldEvent.EDITINPROGRESS) {
					fieldEvent.setEventType(FieldEvent.EDITINPROGRESS);
				} else if (eventType == FieldEvent.EDITCOMPLETED) {
					fieldEvent.setEventType(FieldEvent.EDITCOMPLETED);
				}
				AppUtils.EVENT_BUS.fireEvent(fieldEvent);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[StateField] ::Exception in onFieldEvent method :"+e);
		}
	}
	
	public Boolean isSuggestionAlreadyExist(){
		Boolean isSuggestionExist = false;
		try {
			isSuggestionExist = appopsSuggestionBox.isSuggestionAlreadyExist();
			return isSuggestionExist;
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[StateField] ::Exception in isSuggestionAlreadyExist method :"+e);
		}
		return isSuggestionExist;
		
	}
	
	public void clearSuggestions() {
		try {
			appopsSuggestionBox.clearSuggestions();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
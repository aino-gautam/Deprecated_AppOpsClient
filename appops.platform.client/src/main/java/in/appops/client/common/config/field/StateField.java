package in.appops.client.common.config.field;


import in.appops.client.common.config.field.suggestion.AppopsSuggestionBox;

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

public class StateField extends BaseField {

	private AppopsSuggestionBox appopsSuggestionBox;
	private Logger logger = Logger.getLogger(getClass().getName());

	public StateField(){
		
	}
	
	/****************************************** *******************************/
	
	@Override
	public void create() {
		
		try {
			logger.log(Level.INFO, "[StateField] ::In create method ");
			getBasePanel().add(appopsSuggestionBox,DockPanel.CENTER);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[StateField] ::Exception in create method :"+e);
		}
	}
	
	@Override
	public void configure() {
		try {
			logger.log(Level.INFO, "[StateField] ::In configure method ");
			appopsSuggestionBox = new AppopsSuggestionBox();
			setSuggestionInline();
			if(isStaticSuggestionBox()){
				appopsSuggestionBox.setStaticSuggestionBox(isStaticSuggestionBox());
				appopsSuggestionBox.setItemsToDisplay(getFieldItemList());
			}else{
				appopsSuggestionBox.setQueryName(getQueryName());
				appopsSuggestionBox.setPropertyToDisplay(getEntPropToDisplay());
				appopsSuggestionBox.setOperationName(getOperationName());
				appopsSuggestionBox.setQueryRestrictions(getQueryRestrictions());
				appopsSuggestionBox.setIsSearchQuery(isSearchQuery());
				appopsSuggestionBox.setQueryMaxResult(getQueryMaxResult());
			}
			
			if (getBaseFieldPrimCss() != null)
				appopsSuggestionBox.setStylePrimaryName(getBaseFieldPrimCss());
			if (getBaseFieldDependentCss() != null)
				appopsSuggestionBox.addStyleName(getBaseFieldDependentCss());

			if (getBasePanelPrimCss() != null)
				getBasePanel().setStylePrimaryName(getBasePanelPrimCss());
			if (getBasePanelDependentCss() != null)
				getBasePanel().addStyleName(getBasePanelDependentCss());
			
			appopsSuggestionBox.setAutoSuggestion(isAutosuggestion());
			appopsSuggestionBox.createUi();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[StateField] ::Exception in configure method :"+e);
		}
	}

	@Override
	public void reset() {
		
	}
	
	@Override
	public Object getValue() {
		String suggestion = null;
		if(appopsSuggestionBox.getSelectedSuggestion() != null){
			suggestion = appopsSuggestionBox.getSelectedSuggestion().getDisplayString();
		}else{
			suggestion = appopsSuggestionBox.getSuggestBox().getText();
		}
		return suggestion;
	}
	
	@Override
	protected void setSuggestionInline () {
		try {
			
			logger.log(Level.INFO, "[StateField] ::In setSuggestionInline method ");
			if(getSuggestionText()!=null)
				appopsSuggestionBox.getTextBox().getElement().setPropertyString("placeholder", getSuggestionText());
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[StateField] ::Exception In setSuggestionInline method "+e);
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
			logger.log(Level.INFO, "[StateField] ::In isStaticSuggestionBox method ");
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
			logger.log(Level.INFO, "[StateField] ::In getQueryMaxResult method ");
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
			logger.log(Level.INFO, "[StateField] ::In getFieldItemList method ");
			if(getConfigurationValue(StateFieldConstant.ITEMS_LIST) != null) {
				listOfItems = (ArrayList<String>) getConfigurationValue(StateFieldConstant.ITEMS_LIST);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[StateField] ::Exception in getFieldItemList method :"+e);
		}
		return listOfItems;
	}
	
	
	/**
	 * Method returns the list of restrictions to the query.;
	 * @return
	 */
	private HashMap<String, Object> getQueryRestrictions() {
		HashMap<String, Object> queryRestrictions = null;
		try {
			logger.log(Level.INFO, "[StateField] ::In getQueryRestrictions method ");
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
			logger.log(Level.INFO, "[StateField] ::In getQueryName method ");
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
			logger.log(Level.INFO, "[StateField] ::In getEntPropToDisplay method ");
			if(getConfigurationValue(StateFieldConstant.STFD_ENTPROP) != null) {
				entprop = (String) getConfigurationValue(StateFieldConstant.STFD_ENTPROP);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[StateField] ::Exception in getEntPropToDisplay method :"+e);
		}
		return entprop;
	}
	
	
	/**
	 * Method returns the operation to execute.
	 * @return
	 */
	private String getOperationName() {
		String operation = null;
		try {
			logger.log(Level.INFO, "[StateField] ::In getOperationName method ");
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
			logger.log(Level.INFO, "[StateField] ::In isAutosuggestion method ");
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
			logger.log(Level.INFO, "[StateField] ::In isSearchQuery method ");
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
	}
}
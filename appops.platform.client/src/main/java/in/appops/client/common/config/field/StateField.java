package in.appops.client.common.config.field;


import in.appops.client.common.config.field.suggestion.AppopsSuggestionBox;

import java.util.ArrayList;

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
	
	public StateField(){
		
	}
	
	/****************************************** *******************************/
	
	@Override
	public void create() {
		
		getBasePanel().add(appopsSuggestionBox,DockPanel.CENTER);
	}
	
	@Override
	public void configure() {
		appopsSuggestionBox = new AppopsSuggestionBox();
		appopsSuggestionBox.setStaticSuggestionBox(isStaticSuggestionBox());
		appopsSuggestionBox.setItemsToDisplay(getFieldItemList());
		appopsSuggestionBox.setAutoSuggestion(isAutosuggestion());
		appopsSuggestionBox.createUi();
	}

	@Override
	public void reset() {
		
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
		if(getConfigurationValue(StateFieldConstant.IS_STATIC_BOX) != null) {
			isStatic =(Boolean) getConfigurationValue(StateFieldConstant.IS_STATIC_BOX);
		}
		return isStatic;
	}
	
	/**
	 * Method returns the list of items to show in field;
	 * @return
	 */
	private ArrayList<String> getFieldItemList() {
		ArrayList<String> listOfItems = null;
		if(getConfigurationValue(StateFieldConstant.ITEMS_LIST) != null) {
			listOfItems = (ArrayList<String>) getConfigurationValue(StateFieldConstant.ITEMS_LIST);
		}
		return listOfItems;
	}
	
	/**
	 * Method returns the query string to bind with.
	 * @return
	 */
	private String getQuery() {
		String queryname = null;
		if(getConfigurationValue(StateFieldConstant.STFD_QUERY) != null) {
			queryname =(String) getConfigurationValue(StateFieldConstant.STFD_QUERY);
		}
		return queryname;
	}
	
	/**
	 * Method returns flag whether autosuggestion is true or not. Defaults to true.
	 * @return
	 */
	private Boolean isAutosuggestion() {
		Boolean isautoSuggetion = true;
		if(getConfigurationValue(StateFieldConstant.IS_AUTOSUGGESTION) != null) {
			isautoSuggetion =(Boolean) getConfigurationValue(StateFieldConstant.IS_AUTOSUGGESTION);
		}
		return isautoSuggetion;
	}
	
	/**
	 * Method returns flag whether to use searchQuery . Defaults to false.
	 * @return
	 */
	private Boolean isSearchQuery() {
		Boolean isSearchQuery = false;
		if(getConfigurationValue(StateFieldConstant.IS_SEARCH_QUERY) != null) {
			isSearchQuery =(Boolean) getConfigurationValue(StateFieldConstant.IS_SEARCH_QUERY);
		}
		return isSearchQuery;
	}
	/************************************ *************************************/
	
	public interface StateFieldConstant extends BaseFieldConstant{
		
		public static final String IS_STATIC_BOX = "isStaticBox";
		
		public static final String ITEMS_LIST = "itemsList";
		
		public static final String STFD_QUERY = "queryName";
		
		public static final String STFD_OPRTION = "operation";
		
		public static final String STFD_QUERY_MAXRESULT = "queryMaxresult";
		
		public static final String STFD_ENTPROP = "propertyToDisplay";
		
		public static final String STFD_QUERY_RESTRICTION = "queryRestriction";
		
		public static final String IS_SEARCH_QUERY = "isSearchQuery";
		
		public static final String IS_AUTOSUGGESTION = "isAutoSuggestion";
	}
}
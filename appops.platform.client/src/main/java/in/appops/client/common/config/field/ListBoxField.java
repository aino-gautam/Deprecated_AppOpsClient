package in.appops.client.common.config.field;

import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.query.Query;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.util.EntityList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.ListBox;

/**
Field class to represent a {@link ListBox}
@author pallavi@ensarm.com

<p>
<h3>Configuration</h3>
<a href="ListBoxField.ListBoxFieldConstant.html">Available configurations</a>
</p>

<p>
<h3>Example</h3>
ListBoxField staticListBox = new ListBoxField();
Configuration configuration = new Configuration();
ArrayList<String> items = new ArrayList<String>();
items.add("Private access");
items.add("Public");
items.add("Restricted");
items.add("Me");
configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_ITEMS,items);
staticListBox.setConfiguration(conf);<br>
staticListBox.configure();<br>
staticListBox.create();<br>

</p>*/
public class ListBoxField extends BaseField {

	//TODO: ListBox with query testing is left.
	
	private ListBox listBox;
	private HashMap<String, Object> nameVsEntity ;
	private final DefaultExceptionHandler exceptionHandler = new DefaultExceptionHandler();
	private final DispatchAsync	dispatch = new StandardDispatchAsync(exceptionHandler);

	public ListBoxField(){
		listBox = new ListBox();
	}
	
	/******************************** ****************************************/

	@Override
	public void create() {
		
		if(getListQueryName()!=null){
			excuteListQuery();
		}else{
			if(getStaticListOfItems()!=null){
				populateList(getStaticListOfItems());
			}
		}
		
		getBasePanel().add(listBox, DockPanel.CENTER);
	}
	
	@Override
	public void configure() {
		
		listBox.setVisibleItemCount(getVisibleItemCount());
		
		if(getBaseFieldPrimCss() != null)
			getBasePanel().setStylePrimaryName(getBaseFieldPrimCss());
		if(getBaseFieldCss() != null)
			getBasePanel().addStyleName(getBaseFieldCss());
	}
	
	@Override
	public void clear() {
		listBox.clear();
	}
	
	@Override
	public Object getValue() {
		String selectedItem = listBox.getItemText(listBox.getSelectedIndex());
		if(getListQueryName()!=null){
			return nameVsEntity.get(selectedItem);
		}
		return selectedItem;
	}
	
	/***************************** *****************************/

	/**
	 * Method returns the no of visible items in the listbox .Defaults to 3;
	 * @return
	 */
	private Integer getVisibleItemCount() {
		Integer noOfVisibleItems = 1;
		if(getConfigurationValue(ListBoxFieldConstant.LSTFD_VISIBLE_ITEM_CNT) != null) {
			noOfVisibleItems = (Integer) getConfigurationValue(ListBoxFieldConstant.LSTFD_VISIBLE_ITEM_CNT);
		}
		return noOfVisibleItems;
	}
	
	/**
	 * Method returns the static list of items for listbox;
	 * @return
	 */
	private ArrayList<String> getStaticListOfItems() {
		ArrayList<String> listOfItems = null;
		if(getConfigurationValue(ListBoxFieldConstant.LSTFD_ITEMS) != null) {
			listOfItems = (ArrayList<String>) getConfigurationValue(ListBoxFieldConstant.LSTFD_ITEMS);
		}
		return listOfItems;
	}
	
	/**
	 * Method returns the list query;
	 * @return
	 */
	private String getListQueryName() {
		String query = null;
		if(getConfigurationValue(ListBoxFieldConstant.LSTFD_QUERY) != null) {
			query = (String) getConfigurationValue(ListBoxFieldConstant.LSTFD_QUERY);
		}
		return query;
	}
	
	/**
	 * Method returns the entity property to show in the listbox;
	 * @return
	 */
	private String getEntPropToShow() {
		String entprop = null;
		if(getConfigurationValue(ListBoxFieldConstant.LSTFD_ENTPROP) != null) {
			entprop = (String) getConfigurationValue(ListBoxFieldConstant.LSTFD_ENTPROP);
		}
		return entprop;
	}
	
	/**
	 * Method returns the list of restrictions to the query.;
	 * @return
	 */
	private HashMap<String, Object> getQueryRestrictions() {
		HashMap<String, Object> queryRestrictions = null;
		if(getConfigurationValue(ListBoxFieldConstant.LSTFD_QUERY_RESTRICTION) != null) {
			queryRestrictions =  (HashMap<String, Object>) getConfigurationValue(ListBoxFieldConstant.LSTFD_QUERY_RESTRICTION);
		}
		return queryRestrictions;
	}
	
	/**
	 * Method returns the max result for query.
	 * @return
	 */
	private Integer getQueryMaxResult() {
		Integer maxResult = 10;
		if(getConfigurationValue(ListBoxFieldConstant.LSTFD_QUERY_MAXRESULT) != null) {
			maxResult =(Integer) getConfigurationValue(ListBoxFieldConstant.LSTFD_QUERY_MAXRESULT);
		}
		return maxResult;
	}
	
	
	/**
	 * Method returns the operation to execute.
	 * @return
	 */
	private String getOperationName() {
		String queryname = null;
		if(getConfigurationValue(ListBoxFieldConstant.LSTFD_OPRTION) != null) {
			queryname =(String) getConfigurationValue(ListBoxFieldConstant.LSTFD_OPRTION);
		}
		return queryname;
	}
	/***********************************************************************************/

	/**
	 * Method will be used if want to populate listbox with query result. 
	 * @param entityList
	 */
	private void populateEntityList(EntityList entityList){
		
		for(Entity entity : entityList){
				String item = entity.getPropertyByName(getEntPropToShow());
				nameVsEntity.put(item, entity);
				listBox.addItem(item);
		}
	}
	
	/**
	 * Method will be used to populate listbox with static list.
	 * @param listOfItems
	 */
	private void populateList(ArrayList<String> listOfItems){
		for(int count = 0; count<listOfItems.size() ;count ++){
			String item = listOfItems.get(count);
			listBox.addItem(item);
		}
	}
	
	
	private void excuteListQuery() {
		
		Query queryObj = new Query();
		queryObj.setQueryName(getListQueryName());
		queryObj.setListSize(getQueryMaxResult());
		queryObj.setQueryParameterMap(getQueryRestrictions());
		
		Map parameterMap = new HashMap();
		parameterMap.put("query", queryObj);
		
		StandardAction action = new StandardAction(Entity.class, getOperationName(), parameterMap);
		dispatch.execute(action, new AsyncCallback<Result>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(Result result) {
				
				if(result!=null){
					EntityList list   = (EntityList) result.getOperationResult();
					if(!list.isEmpty())
						populateEntityList(list);
				}
				
			}
		});
	}
	
	public interface ListBoxFieldConstant extends BaseFieldConstant{
		
		/** Specifies No of list items should be visible in the listbox. **/
		public static final String LSTFD_VISIBLE_ITEM_CNT = "visibleItems";
		
		/** Specifies the no of items in the list. **/
		public static final String LSTFD_ITEMS = "listOfItems";
		
		public static final String LSTFD_QUERY = "queryName";
		
		public static final String LSTFD_OPRTION = "operation";
		
		public static final String LSTFD_QUERY_MAXRESULT = "queryMaxresult";
		
		public static final String LSTFD_ENTPROP = "propertyToDisplay";
		
		public static final String LSTFD_QUERY_RESTRICTION = "queryRestriction";
		
	}


}
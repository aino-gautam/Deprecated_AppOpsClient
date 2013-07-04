package in.appops.client.common.config.field;

import in.appops.client.common.core.EntityListModel;
import in.appops.client.common.core.EntityListReceiver;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.query.Query;
import in.appops.platform.core.util.EntityList;
import java.util.ArrayList;
import java.util.HashMap;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.ListBox;

/**
 * Field class to represent listbox. 
 * @author pallavi@ensarm.com
 */
public class ListBoxField extends BaseField implements ChangeHandler,EntityListReceiver{

	private ListBox listBox;
	private HashMap<String, Object> nameVsEntity ;

	public ListBoxField(){
		listBox = new ListBox();
	}
	
	/******************************** ****************************************/

	@Override
	public void create() {
		
		if(getListQuery()!=null){
			excuteListQuery();
		}else{
			if(getStaticListOfItems()!=null){
				populateList(getStaticListOfItems());
			}
		}
		listBox.addChangeHandler(this);
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
		if(getListQuery()!=null){
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
	private String getListQuery() {
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
		EntityListModel listModel = new EntityListModel();
		Query query = new Query();
		query.setQueryName(getListQuery());
		listModel.setQueryToBind(query);
		listModel.getEntityList(10, this);
		listModel.getCurrentEntityList();
	}
	
	@Override
	public void onChange(ChangeEvent event) {
		
	}
	
	@Override
	public void noMoreData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEntityListReceived(EntityList entityList) {
		if(entityList!=null)
			populateEntityList(entityList);
		
	}

	@Override
	public void onEntityListUpdated() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCurrentView(Entity entity) {
		// TODO Auto-generated method stub
		
	}
	
	public interface ListBoxFieldConstant extends BaseFieldConstant{
		
		/** Specifies No of list items should be visible in the listbox. **/
		public static final String LSTFD_VISIBLE_ITEM_CNT = "visibleItems";
		
		/** Specifies the no of items in the list. **/
		public static final String LSTFD_ITEMS = "listOfItems";
		
		/** Specifies the query to execute. **/
		public static final String LSTFD_QUERY = "query";
		
		/** Specifies the property of the entity to display in the list **/
		public static final String LSTFD_ENTPROP = "propertyToDisplay";
		
	}


}
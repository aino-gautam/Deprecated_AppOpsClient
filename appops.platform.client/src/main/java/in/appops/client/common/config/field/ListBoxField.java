package in.appops.client.common.config.field;

import in.appops.client.common.config.field.ButtonField.ButtonFieldConstant;
import in.appops.client.common.config.field.ImageField.ImageFieldConstant;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.entity.query.Query;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.EntityList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
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
public class ListBoxField extends BaseField implements ChangeHandler,BlurHandler, KeyDownHandler{

	private ListBox listBox;
	private HashMap<String, Entity> nameVsEntity  = null;
	private final DefaultExceptionHandler exceptionHandler = new DefaultExceptionHandler();
	private final DispatchAsync	dispatch = new StandardDispatchAsync(exceptionHandler);
	private HandlerRegistration selectionHandler = null ;
	private HandlerRegistration blurHandler = null ;
	private HandlerRegistration keyDownHandler = null ;
	private HorizontalPanel boxPlusLoaderPanel ;
	private ImageField imageField ;
	private String selectDefaultValue;
	private Logger logger = Logger.getLogger(getClass().getName());
	public ListBoxField(){
		listBox = new ListBox();
		boxPlusLoaderPanel = new HorizontalPanel();
		imageField = new ImageField();
	}
	
	/******************************** ****************************************/

	/**
	 * Method checks whether user wants to execute listQuery or user provided the static list of items from configuration. If user is providing
	 * query name then it executes the query and populate the list . If user is providing static list of items  
	 * then it populates the listbox with entityList.
	 *  
	 */
	@Override
	public void create() {
		try {
			if (getOperationName() != null) {
				excuteListQuery();
			} else {
				if (getStaticListOfItems() != null) {
					Object obj = getStaticListOfItems();
					if (obj instanceof ArrayList) {
						ArrayList<Object> staticList = (ArrayList<Object>) obj;
						if(!staticList.isEmpty()) {
							if (staticList.get(0) instanceof String) {
								populateList((ArrayList<String>) obj);
							} else if (staticList.get(0) instanceof Entity) {
								EntityList list = (EntityList) obj;
								populateEntityList(list);
							}
						}
					} else if (obj instanceof EntityList) {
						EntityList list = (EntityList) obj;
						populateEntityList(list);
					}

				}
			}
			boxPlusLoaderPanel.clear();
			boxPlusLoaderPanel.add(listBox);
			boxPlusLoaderPanel.setCellWidth(listBox, "80%");
			boxPlusLoaderPanel.add(imageField);
			boxPlusLoaderPanel.setCellWidth(imageField, "20%");
			boxPlusLoaderPanel.setCellVerticalAlignment(imageField, HasVerticalAlignment.ALIGN_MIDDLE);
			getBasePanel().add(boxPlusLoaderPanel, DockPanel.CENTER);

		} catch (Exception e) {
			logger.log(Level.SEVERE,
					"[ListBoxField]::Exception In create  method :" + e);

		}
	}
	
	@Override
	public void configure() {
		try {
			listBox.clear();
			listBox.setVisibleItemCount(getVisibleItemCount());
			listBox.setEnabled(isEnabled());

			if (getDefaultValue() != null) {
				listBox.insertItem(getDefaultValue().toString(), 0);
				listBox.setSelectedIndex(0);
			}

			if (getBaseFieldPrimCss() != null)
				listBox.setStylePrimaryName(getBaseFieldPrimCss());
			if (getBaseFieldDependentCss() != null)
				listBox.addStyleName(getBaseFieldDependentCss());
			
			if (getBasePanelPrimCss() != null)
				getBasePanel().setStylePrimaryName(getBasePanelPrimCss());
			if (getBasePanelDependentCss() != null)
				getBasePanel().addStyleName(getBasePanelDependentCss());
			

				removeRegisteredHandlers();
				selectionHandler = listBox.addChangeHandler(this);
				blurHandler = listBox.addBlurHandler(this);
				keyDownHandler = listBox.addKeyDownHandler(this);
				
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[ListBoxField]::Exception In configure  method :" + e);
		}
	}
	
	@Override
	public void clear() {
		
		try {
			listBox.clear();
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[ListBoxField]::Exception In clear  method :"+e);
		}
	}
	
	/**
	 * Method removed registered handlers from field
	 */
	@Override
	public void removeRegisteredHandlers() {
		if(selectionHandler!=null)
			selectionHandler.removeHandler();
		
		if(blurHandler!=null)
			blurHandler.removeHandler();
		
		if(keyDownHandler!=null)
			keyDownHandler.removeHandler();
	}
	
	@Override
	public Object getValue() {
		String selectedItem = listBox.getItemText(listBox.getSelectedIndex());
		return selectedItem;
	}
	
	@Override
	public void setValue(Object value) {
		
		try {
			if(value!=null)
				listBox.setSelectedIndex(getIndexFromText(value.toString()));
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[ListBoxField]::Exception In setValue  method :"+e);
		}
	}
	
	public void setEnabled(Boolean isEnabled){
		try {
			listBox.setEnabled(isEnabled);
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[ListBoxField]::Exception In setEnabled  method :"+e);
		}
	}
	
	/***************************** *****************************/

	/**
	 * Method returns the no of visible items in the listbox .Defaults to 3;
	 * @return
	 */
	private Integer getVisibleItemCount() {
		Integer noOfVisibleItems = 1;
		try {
			if(getConfigurationValue(ListBoxFieldConstant.LSTFD_VISIBLE_ITEM_CNT) != null) {
				noOfVisibleItems = (Integer) getConfigurationValue(ListBoxFieldConstant.LSTFD_VISIBLE_ITEM_CNT);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[ListBoxField]::Exception In getVisibleItemCount  method :"+e);
		}
		return noOfVisibleItems;
	}
	
	/**
	 * Method returns the static list of items for listbox;
	 * @return
	 */

	private Object getStaticListOfItems() {
		Object listOfItems = null;
		try {
			if (getConfigurationValue(ListBoxFieldConstant.LSTFD_ITEMS) != null) {
				listOfItems = getConfigurationValue(ListBoxFieldConstant.LSTFD_ITEMS);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[ListBoxField]::Exception In getStaticListOfItems  method :"
							+ e);
		}
		return listOfItems;
	}
	
	
	/**
	 * Method returns the list query;
	 * @return
	 */
	private String getListQueryName() {
		String query = null;
		try {
			if(getConfigurationValue(ListBoxFieldConstant.LSTFD_QUERYNAME) != null) {
				query = (String) getConfigurationValue(ListBoxFieldConstant.LSTFD_QUERYNAME);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[ListBoxField]::Exception In getListQueryName  method :"+e);
		}
		return query;
	}
	
	/**
	 * Method returns the entity property to show in the listbox;
	 * @return
	 */
	private String getEntPropToShow() {
		String entprop = null;
		try {
			if(getConfigurationValue(ListBoxFieldConstant.LSTFD_ENTPROP) != null) {
				entprop = (String) getConfigurationValue(ListBoxFieldConstant.LSTFD_ENTPROP);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[ListBoxField]::Exception In getEntPropToShow  method :"+e);
		}
		return entprop;
	}
	
	/**
	 * Method returns the list of restrictions to the query.;
	 * @return
	 */
	private HashMap<String, Object> getQueryRestrictions() {
		HashMap<String, Object> queryRestrictions = null;
		try {
			if(getConfigurationValue(ListBoxFieldConstant.LSTFD_QUERY_RESTRICTION) != null) {
				queryRestrictions =  (HashMap<String, Object>) getConfigurationValue(ListBoxFieldConstant.LSTFD_QUERY_RESTRICTION);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[ListBoxField]::Exception In getQueryRestrictions  method :"+e);
		}
		return queryRestrictions;
	}
	
	/**
	 * Method returns the max result for query.
	 * @return
	 */
	private Integer getQueryMaxResult() {
		Integer maxResult = 100;
		try {
			if(getConfigurationValue(ListBoxFieldConstant.LSTFD_QUERY_MAXRESULT) != null) {
				maxResult =(Integer) getConfigurationValue(ListBoxFieldConstant.LSTFD_QUERY_MAXRESULT);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[ListBoxField]::Exception In getQueryMaxResult  method :"+e);
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
			if(getConfigurationValue(ListBoxFieldConstant.LSTFD_OPRTION) != null) {
				operation =(String) getConfigurationValue(ListBoxFieldConstant.LSTFD_OPRTION);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[ListBoxField]::Exception In getOperationName  method :"+e);
		}
		return operation;
	}
	
	/**
	 * Method returns the selected text for listbox;
	 * @return
	 */
	private String getDefaultSelectedText() {
		String selectedTxt = null;
		try {
			if(getConfigurationValue(ListBoxFieldConstant.LSTFD_SELECTED_TXT) != null) {
				selectedTxt = (String) getConfigurationValue(ListBoxFieldConstant.LSTFD_SELECTED_TXT);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[ListBoxField]::Exception In getDefaultSelectedText  method :"+e);
		}
		return selectedTxt;
	}
	/***********************************************************************************/

	/**
	 * Method will be used if want to populate listbox with query result. 
	 * @param entityList
	 */
	private void populateEntityList(EntityList entityList){
		try {
			imageField.setConfiguration(getImageVisibleConfiguration());
			imageField.configure();
			imageField.create();
			if(nameVsEntity==null)
				nameVsEntity = new HashMap<String, Entity>();
						
			listBox.clear();
			if (getDefaultValue() != null) {
				listBox.insertItem(getDefaultValue().toString(), 0);
				listBox.setSelectedIndex(0);
			}
			
			for(Entity entity : entityList){
				String item = entity.getPropertyByName(getEntPropToShow());
				Key keyId = (Key)entity.getPropertyByName("id");
				Long id = (Long) keyId.getKeyValue();
				nameVsEntity.put(id.toString(), entity);
				listBox.addItem(item, id.toString());
			}
			
			if(getDefaultSelectedText()!=null){
				listBox.setSelectedIndex(getIndexFromText(getDefaultSelectedText()));
			}
			if(selectDefaultValue != null) {
				setValue(selectDefaultValue);
				selectDefaultValue = null;
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[ListBoxField]::Exception In populateEntityList  method :"+e);
		}
		
	}
	
	/**
	 * Method will be used to populate listbox with static list.
	 * @param listOfItems
	 */
	private void populateList(ArrayList<String> listOfItems){
		try {
			imageField.setConfiguration(getImageVisibleConfiguration());
			imageField.configure();
			imageField.create();
			for(int count = 0; count<listOfItems.size() ;count ++){
				String item = listOfItems.get(count);
				listBox.addItem(item);
			}
			
			if(getDefaultSelectedText()!=null){
				listBox.setSelectedIndex(getIndexFromText(getDefaultSelectedText()));
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[ListBoxField]::Exception In populateList  method :"+e);
		}
		
	}
	
	/**
	 * Method creates the query object from query name ,set parameters to it and executes the list query.
	 */
	@SuppressWarnings("unchecked")
	private void excuteListQuery() {
		
		try {
			if(getListQueryName()!=null){
				Query queryObj = new Query();
				queryObj.setQueryName(getListQueryName());
				queryObj.setListSize(getQueryMaxResult());
				if(getQueryRestrictions()!=null)
					queryObj.setQueryParameterMap(getQueryRestrictions());
				
				Map parameterMap = new HashMap();
				parameterMap.put("query", queryObj);
				executeOperation(parameterMap);
			}else{
				executeOperation(null);
			}
			
			
			
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[ListBoxField]::Exception In excuteListQuery  method :"+e);
		}
	}
	
	/**
	 * Method returns associated entity of the selected text.
	 * @param itemText
	 * @return
	 */
	public Entity getAssociatedEntity(String itemText){
		
		for (Entity ent  : nameVsEntity.values()) {
			String propValue = ent.getPropertyByName(getEntPropToShow()).toString();
			if(propValue.equals(itemText)){
				return ent;
			}
		}
		return null;
		
	}
	
	
	/**
	 * Method returns the index of the item from item text.
	 * @param text
	 * @return
	 */
	private Integer getIndexFromText(String text){

		int indexToFind = -1;
		try {
			for (int i=0; i<listBox.getItemCount(); i++) {
			    if (listBox.getItemText(i).equals(text)) {
			        indexToFind = i;
			        break;
			    }
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[ListBoxField]::Exception In getIndexFromText  method :"+e);
		}
		return indexToFind;
	}
	
	@Override
	public void onChange(ChangeEvent event) {
		
		try {
				
			FieldEvent fieldEvent = new FieldEvent();
			String item = getValue().toString();
			String value = getSelectedValue().toString();
			SelectedItem selectedItem = new SelectedItem();
			selectedItem.setItemString(item);

			if(nameVsEntity != null && !nameVsEntity.isEmpty()) {
				Entity entity = nameVsEntity.get(value);
				if(nameVsEntity!=null){
					selectedItem.setAssociatedEntity(entity);
				}
			}
			
			fieldEvent.setEventSource(this);
			fieldEvent.setEventData(selectedItem);
			fieldEvent.setEventType(FieldEvent.VALUECHANGED);
			AppUtils.EVENT_BUS.fireEvent(fieldEvent);
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[ListBoxField]::Exception In onChange  method :"+e);
		}
		
	}


	@SuppressWarnings("unchecked")
	private void executeOperation(Map parameterMap) {
		try {
			
			
			imageField.setConfiguration(getImageConfiguration());
			imageField.configure();
			imageField.create();
			
			StandardAction action = new StandardAction(EntityList.class, getOperationName(), parameterMap);
			dispatch.execute(action, new AsyncCallback<Result<EntityList>>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.printStackTrace();
				}

				@Override
				public void onSuccess(Result<EntityList> result) {
					imageField.setConfiguration(getImageVisibleConfiguration());
					imageField.configure();
					imageField.create();
					if(result!=null){
						EntityList list   = result.getOperationResult();
						if(!list.isEmpty())
							populateEntityList(list);
					}
					
				}

				
			});
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[ListBoxField]::Exception In executeOperation  method :"+e);
		}
	}
	
	public String getSuggestionValueForListBox() {
		String defaultName = "-- Select --";
		try {
			if(getDefaultValue() != null) {
				defaultName = (String) getDefaultValue();
			} 
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[ListBoxField]::Exception In getSuggestionValueForListBox  method :"+e);
		}
		return defaultName;
	}
	
	public String getListBoxImageLoader() {
		String defaultImageLoader = "images/defaultLoader.gif";
		try {
			if(getConfigurationValue(ListBoxFieldConstant.LSTFD_LOADERIMG_BLOBID) != null) {
				defaultImageLoader = (String) getConfigurationValue(ListBoxFieldConstant.LSTFD_LOADERIMG_BLOBID);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[ListBoxField]::Exception In getListBoxImageLoader  method :"+e);
		}
		return defaultImageLoader;
	}
	
	public String getListBoxImageLoaderPcls() {
		String defaultPcls = "appops-listBoxLoaderPcls";
		try {
			if(getConfigurationValue(ListBoxFieldConstant.LSTFD_LOADERIMG_PCLS) != null) {
				defaultPcls = (String) getConfigurationValue(ListBoxFieldConstant.LSTFD_LOADERIMG_PCLS);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[ListBoxField]::Exception In getListBoxImageLoaderPcls  method :"+e);
		}
		return defaultPcls;
	}
		
	private Configuration getImageConfiguration(){
		Configuration configuration = new Configuration();
		try {
			configuration.setPropertyByName(ImageFieldConstant.IMGFD_BLOBID, getListBoxImageLoader());
			configuration.setPropertyByName(ImageFieldConstant.BF_PCLS,getListBoxImageLoaderPcls());
			
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[ListBoxField]::Exception In getImageConfiguration  method :"+e);
		}
		return configuration;
	}
	
	private Configuration getImageVisibleConfiguration() {
		Configuration configuration = new Configuration();
		try {
				configuration.setPropertyByName(ButtonFieldConstant.BF_VISIBLE, false);
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[ListBoxField]::Exception In getImageVisibleConfiguration  method :"+e);
		}
		return configuration;
	}
	/**
	 * Method returns id of the selected entity in the listbox.
	 * @return
	 */
	public Object getSelectedValue() {
		try {
			String selectedValue = listBox.getValue(listBox.getSelectedIndex());
			return selectedValue;
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public void onBlur(BlurEvent event) {
		try {
			FieldEvent fieldEvent = new FieldEvent();
			fieldEvent.setEventSource(this);
			fieldEvent.setEventData(getValue());
			fieldEvent.setEventType(FieldEvent.EDITCOMPLETED);
			AppUtils.EVENT_BUS.fireEvent(fieldEvent);
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[ListBoxField]::Exception In onBlur  method :"+e);
		}
	}
	
	public interface ListBoxFieldConstant extends BaseFieldConstant{
		
		/** Specifies No of list items should be visible in the listbox. **/
		public static final String LSTFD_VISIBLE_ITEM_CNT = "visibleItems";
		
		/** Specifies the no of items in the list. **/
		public static final String LSTFD_ITEMS = "listOfItems";
		
		public static final String LSTFD_QUERYNAME = "queryName";
		
		public static final String LSTFD_OPRTION = "operation";
		
		public static final String LSTFD_QUERY_MAXRESULT = "queryMaxresult";
		
		public static final String LSTFD_ENTPROP = "propertyToDisplay";
		
		public static final String LSTFD_QUERY_RESTRICTION = "queryRestriction";
		
		public static final String LSTFD_SELECTED_TXT = "defaultSelectedText";
		
		public static final String LSTFD_LOADERIMG_BLOBID="loaderImgBlobId";
		
		public static final String LSTFD_LOADERIMG_PCLS ="loaderImgPrimarycss";
		
		public static final String LSTFD_LOADERIMG_DCLS="loaderImgSecondarycss";
		
	}

	@Override
	public void onKeyDown(KeyDownEvent event) {
		try {
			Integer keycode= event.getNativeKeyCode();
			if(keycode.equals(KeyCodes.KEY_TAB)){
				FieldEvent fieldEvent = new FieldEvent();
				fieldEvent.setEventSource(this);
				fieldEvent.setEventData(getValue());
				fieldEvent.setEventType(FieldEvent.TAB_KEY_PRESSED);
				AppUtils.EVENT_BUS.fireEvent(fieldEvent);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[ListBoxField] ::Exception In onKeyDown method "+e);
		}
		
		
	}
	
	public boolean isFieldEnabled() {
		return isEnabled();
	}

	public void setSelectDefaultValue(String selectDefaultValue) {
		this.selectDefaultValue = selectDefaultValue;
	}
}
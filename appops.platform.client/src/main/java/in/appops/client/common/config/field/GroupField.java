package in.appops.client.common.config.field;

import in.appops.client.common.config.field.RadioButtonField.RadionButtonFieldConstant;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.platform.core.shared.Configuration;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.Widget;

/**
Field class to represent group field with Checkbox or Radiobutton.

<p>
<h3>Configuration</h3>
<a href="GroupField.GroupFieldConstant.html">Available configurations</a>
</p>

<p>
<h3>Example</h3>

<p>Following code results checkbox groupfield. </p>

GroupField groupField = new GroupField();<br>
		
Configuration groupFieldConfig = new Configuration();<br>
groupFieldConfig.setPropertyByName(GroupFieldConstant.GF_TYPE,GroupFieldConstant.GFTYPE_SINGLE_SELECT);<br>
groupFieldConfig.setPropertyByName(GroupFieldConstant.GF_ALIGNMENT,GroupFieldConstant.GF_ALIGN_HORIZONTAL);<br>
groupFieldConfig.setPropertyByName(GroupFieldConstant.GF_LIMIT,3);<br>
groupFieldConfig.setPropertyByName(GroupFieldConstant.GF_TYPE,GroupFieldConstant.GFTYPE_MULTISELECT);<br>
		
ArrayList<String> listOfItems = new ArrayList<String>();<br>
listOfItems.add("chk1");<br>
listOfItems.add("chk2");<br>
groupFieldConfig.setPropertyByName(GroupFieldConstant.GF_LIST_OF_ITEMS,listOfItems);<br>
		
Configuration childConfig1 = new Configuration();<br>
childConfig1.setPropertyByName(CheckBoxFieldConstant.BF_PCLS, "appops-CheckBoxField");<br>
childConfig1.setPropertyByName(CheckBoxFieldConstant.CF_DISPLAYTEXT, "cssStyle");<br>
childConfig1.setPropertyByName(CheckBoxFieldConstant.CF_CHECKED, true);<br>
		
Configuration childConfig2 = new Configuration();<br>
childConfig2.setPropertyByName(CheckBoxFieldConstant.BF_PCLS, "appops-CheckBoxField");<br>
childConfig2.setPropertyByName(CheckBoxFieldConstant.CF_DISPLAYTEXT, "configuration");<br>
		
groupFieldConfig.setPropertyByName("chk1",childConfig1);<br>
groupFieldConfig.setPropertyByName("chk2",childConfig2);<br>
groupField.setConfiguration(groupFieldConfig);<br>
groupField.configure();<br>
groupField.create()<br>
</p>*/

public class GroupField extends BaseField implements FieldEventHandler{

	private FlexTable flexTable;
	private Integer row = 0;
	private Integer column = 0;
	private ArrayList<Widget> fieldItems ;
	private ArrayList<Widget> selectedItems ;
	
	public GroupField() {
		 flexTable = new FlexTable();
	}

	@Override
	public void create() {
		
		selectedItems = new ArrayList<Widget>();
		
		getBasePanel().add(flexTable,DockPanel.CENTER);
		
	}
	
	/**
	 * Method create the RadioButton.
	 * @param name
	 * @param value
	 * @return RadioButton.
	 */
	private RadioButtonField getRadioButtonField(String id){
		
		RadioButtonField radioField = new RadioButtonField();
		Configuration childConfig = getChildConfiguration(id);
		
		if(childConfig!=null){
			if(childConfig.getPropertyByName(RadionButtonFieldConstant.RF_ID) == null)
				childConfig.setPropertyByName(RadionButtonFieldConstant.RF_ID, id);
		}
		
		radioField.setConfiguration(childConfig);
		radioField.configure();
		radioField.create();
		
		if(fieldItems==null)
			fieldItems = new ArrayList<Widget>();

		fieldItems.add(radioField);
		return radioField;
	}
	
	/**
	 * Method read the childConfiguration from group field configuration set it to checkboxfield and return checkboxField.
	 * @param id
	 * @param isChecked
	 * @return CheckboxField.
	 */
	private CheckboxField getCheckBoxField(String id){
		
		CheckboxField checkBoxField = new CheckboxField();
		Configuration childConfig = getChildConfiguration(id);
		
		if(childConfig!=null){
			if(childConfig.getPropertyByName(BaseFieldConstant.BF_ID) == null)
				childConfig.setPropertyByName(BaseFieldConstant.BF_ID, id);
		}
		
		checkBoxField.setConfiguration(childConfig);
		checkBoxField.configure();
		checkBoxField.create();
		
		if(fieldItems==null)
			fieldItems = new ArrayList<Widget>();

		fieldItems.add(checkBoxField);
		return checkBoxField;
	}
	
	/**
	 * Method returns group field alignment whether its horizontal/vertical. Bydefault it returns vertical alignment.
	 * @return group field alignment
	 */
	private String getGroupFieldAlignment() {
		
		if(getConfiguration()!=null){
			
			String fieldBasePanel =  getConfiguration().getPropertyByName(GroupFieldConstant.GF_ALIGNMENT);
			if(fieldBasePanel !=null){
				return fieldBasePanel; 
			}else{
				return GroupFieldConstant.GF_ALIGN_VERTICAL;
			}
		}
		return null;
	}
	
	/**
	 * Method return group field type whether its checkbox group or radiobutton group. Bydefault it will return checkbox group.
	 * @return group field type
	 */

	private String getGroupFieldType(){
		if(getConfiguration()!=null){
			
			String fieldType =  getConfiguration().getPropertyByName(GroupFieldConstant.GF_TYPE);
			if(fieldType !=null){
				return fieldType; 
			}else{
				return GroupFieldConstant.GFTYPE_SINGLE_SELECT;
			}
		}
		return null;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Method checks whether type is checkbox group then select all the items in the group field .
	 */
	public void selectAllGroupItems(){
		String groupFieldType = getGroupFieldType();
		if(groupFieldType.equals(GroupFieldConstant.GFTYPE_MULTISELECT)){
			
			for(int i= 0;i<fieldItems.size();i++){
				CheckboxField chkboxField = (CheckboxField) fieldItems.get(i);
				chkboxField.setValue(true);
			}
		}
		
	}
	
	/**
	 * Method checks whether type is checkbox group and then deselect all the items in the group field .
	 */
	public void deselectAllGroupItems(){
		
		String groupFieldType = getGroupFieldType();
		
		if(groupFieldType.equals(GroupFieldConstant.GFTYPE_MULTISELECT)){
			for(int i= 0;i<fieldItems.size();i++){
				CheckboxField chkboxField = (CheckboxField) fieldItems.get(i);
				chkboxField.setValue(false);
			}
		}
		
	}
	
	/**
	 * Overriden method from BaseField returns the selected items.
	 */
	
	@Override
	public Object getValue() {
		return selectedItems;
	}
	
	@Override
	public void configure() {
		
		if(getBaseFieldPrimCss()!=null)
			flexTable.setStylePrimaryName(getBaseFieldPrimCss());		
		if(getBaseFieldCss()!=null)
			flexTable.setStylePrimaryName(getBaseFieldCss());
		
		String groupFieldType = getGroupFieldType();
		
		String groupFieldAlignment = getGroupFieldAlignment();
		
		Integer fieldLimit = getFieldLimit();
		
		ArrayList<String> listOfItemsInGroupField = getListOfItemsInGroupField();
		
		if (listOfItemsInGroupField!=null) {
			if (groupFieldType.equals(GroupFieldConstant.GFTYPE_MULTISELECT)) {

				if (groupFieldAlignment.equals(GroupFieldConstant.GF_ALIGN_HORIZONTAL)) {

					for (int item = 0; item < listOfItemsInGroupField.size(); item++) {
						CheckboxField chkBoxField = getCheckBoxField(listOfItemsInGroupField.get(item));
						flexTable.setWidget(row, column, chkBoxField);
						
						if (column >= fieldLimit - 1) {
							row++;
							column = 0;
						} else {
							column++;
						}
					}

				} else {
					for (int item = 0; item < listOfItemsInGroupField.size(); item++) {
						CheckboxField chkBoxField = getCheckBoxField(listOfItemsInGroupField.get(item));
						flexTable.setWidget(row, column, chkBoxField);
						
						if (row >= fieldLimit - 1) {
							column++;
							row = 0;
						} else {
							row++;
						}
					}
				}

			} else if (groupFieldType.equals(GroupFieldConstant.GFTYPE_SINGLE_SELECT)) {

				if (groupFieldAlignment.equals(GroupFieldConstant.GF_ALIGN_HORIZONTAL)) {

					for (int item = 0; item < listOfItemsInGroupField.size(); item++) {
						RadioButtonField radioButton = getRadioButtonField(listOfItemsInGroupField.get(item));
						flexTable.setWidget(row, column, radioButton);
						if (column >= fieldLimit - 1) {
							row++;
							column = 0;
						} else {
							column++;
						}
					}
				} else {

					for (int item = 0; item < listOfItemsInGroupField.size(); item++) {
						RadioButtonField radioButton = getRadioButtonField(listOfItemsInGroupField.get(item));
						flexTable.setWidget(row, column, radioButton);
						if (row >= fieldLimit - 1) {
							column++;
							row = 0;
						} else {
							row++;
						}
					}
				}

			}
		}		
		
	}
	
	/**
	 * Method returns list of items in the group field.
	 * @return
	 */
	private ArrayList<String> getListOfItemsInGroupField() {
		
		if(getConfiguration()!=null){
			
			ArrayList<String> listOfItems =  getConfiguration().getPropertyByName(GroupFieldConstant.GF_LIST_OF_ITEMS);
			if(listOfItems !=null){
				return listOfItems; 
			}
		}
		return null;
	}

	/**
	 * Method will return the child configuration. 
	 * @param displayText 
	 * @return
	 */
	public Configuration getChildConfiguration(String id){
		
		if(getConfiguration()!=null){
			
			Configuration childConfig = getConfiguration().getPropertyByName(id);
			if(childConfig !=null){
				return childConfig;
			}else{
				return null;
			}
		}
		return null;
	}
	
	/**
	 * Method read the limit from the configuration. In case of horizontal alignment limit is no. of columns and in case of vertical alignment
	 * it is no.of rows.
	 * @return limit.
	 */

	private Integer getFieldLimit() {
		
		if(getConfiguration()!=null){
			
			Integer limit =  getConfiguration().getPropertyByName(GroupFieldConstant.GF_LIMIT);
			if(limit !=null){
				return limit; 
			}else{
				return 2;
			}
		}
		return null;
	}

	/****************************************************************************************************/
	
	public ArrayList<Widget> getListOfItems() {
		return fieldItems;
	}

	public void setListOfItems(ArrayList<Widget> listOfItems) {
		this.fieldItems = listOfItems;
	}
	
	@Override
	public void onFieldEvent(FieldEvent event) {
		int eventType = event.getEventType();
		switch (eventType) {
		case FieldEvent.CHECKBOX_SELECT: {
			CheckBox checkbox = (CheckBox) event.getEventData();
			selectedItems.add(checkbox);
			break;
		}
		case FieldEvent.CHECKBOX_DESELECT: {
			CheckBox checkbox = (CheckBox) event.getEventData();
			selectedItems.remove(checkbox);
			break;
		}
		case FieldEvent.RADIOBUTTON_SELECTED: {
			selectedItems.clear();
			RadioButton radioButton = (RadioButton) event.getEventData();
			selectedItems.add(radioButton);
			break;
		}
		default:
			break;
		}
		
	}
	/***********************************************************************************/

	public interface GroupFieldConstant extends BaseFieldConstant{
		
		public static final String GF_TYPE = "fieldType";
		
		public static final String GFTYPE_SINGLE_SELECT = "singleSelect";
		
		public static final String GFTYPE_MULTISELECT = "multiselect";
		
		public static final String GF_ALIGNMENT = "alignment";
		
		public static final String GF_ALIGN_VERTICAL = "alignVertical";
		
		public static final String GF_ALIGN_HORIZONTAL = "alignHorizontal";
		
		public static final String GF_LIMIT = "limit";
		
		public static final String GF_LIST_OF_ITEMS = "listOfItems";
		
	}

}

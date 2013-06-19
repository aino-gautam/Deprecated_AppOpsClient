package in.appops.showcase.web.gwt.fields.client;

import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.fields.CheckboxField;
import in.appops.client.common.fields.CheckboxField.CheckBoxFieldConstant;
import in.appops.client.common.fields.Field;
import in.appops.platform.core.shared.Configurable;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.Widget;

public class GroupField extends Composite implements Field,Configurable{

	private Configuration configuration;
	private String fieldValue;
	private FlexTable basePanel;
	private Integer row = 0;
	private Integer column = 0;
	private ArrayList<Widget> listOfItems ;
	
	public GroupField() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void create() throws AppOpsException {
		
		if(getConfiguration() == null)
			throw new AppOpsException("GroupField configuration unavailable");
		
		initWidget(basePanel);
	}
	
	/**
	 * Method create the RadioButton.
	 * @param name
	 * @param value
	 * @return RadioButton.
	 */
	private RadioButton getRadioButton(String name){
		RadioButton radioButton = new RadioButton("singleSelection");
		
		Configuration conf = getChildConfiguration(name);
		
		String primaryCss = conf.getPropertyByName(CheckBoxFieldConstant.CF_PRIMARYCSS);
		String dependentCss = conf.getPropertyByName(CheckBoxFieldConstant.CF_DEPENDENTCSS);
		String displayText = conf.getPropertyByName(CheckBoxFieldConstant.CF_DISPLAYTEXT);
		
		boolean isChecked = false;
		
		if(conf.getPropertyByName(CheckBoxFieldConstant.CF_CHECKED) !=null)
			isChecked = conf.getPropertyByName(CheckBoxFieldConstant.CF_CHECKED);
		
		if(primaryCss !=null)
			radioButton.setStylePrimaryName(primaryCss);
		
		if(dependentCss !=null)
			radioButton.addStyleName(dependentCss);
		
        radioButton.setText(name);
        radioButton.setValue(isChecked);
		if(listOfItems==null)
			listOfItems = new ArrayList<Widget>();
		
		listOfItems.add(radioButton);

		return radioButton;
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
			if(childConfig.getPropertyByName(CheckBoxFieldConstant.CF_ID) == null)
				childConfig.setPropertyByName(CheckBoxFieldConstant.CF_ID, id);
		}
		
		checkBoxField.setConfiguration(childConfig);
		checkBoxField.configure();
		
		if(listOfItems==null)
			listOfItems = new ArrayList<Widget>();

		listOfItems.add(checkBoxField);
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
				return GroupFieldConstant.GFTYPE_CHKBOXGROUP;
			}
		}
		return null;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
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
		if(groupFieldType.equals(GroupFieldConstant.GFTYPE_CHKBOXGROUP)){
			for(int i= 0;i<listOfItems.size();i++){
				CheckboxField chkboxField = (CheckboxField) listOfItems.get(i);
				chkboxField.setValue(true);
			}
		}
		
	}
	
	/**
	 * Method checks whether type is checkbox group and then deselect all the items in the group field .
	 */
	public void deselectAllGroupItems(){
		String groupFieldType = getGroupFieldType();
		if(groupFieldType.equals(GroupFieldConstant.GFTYPE_CHKBOXGROUP)){
			for(int i= 0;i<listOfItems.size();i++){
				CheckboxField chkboxField = (CheckboxField) listOfItems.get(i);
				chkboxField.setValue(false);
			}
		}
		
	}
	
	/**
	 * Method returns selected items in the group field .
	 */
	public void getSelectedItemsFromGroup(){
		
		String groupFieldType = getGroupFieldType();
		if(groupFieldType.equals(GroupFieldConstant.GFTYPE_CHKBOXGROUP)){
			
			for(int i= 0;i<listOfItems.size();i++){
				CheckBox chk = (CheckBox) listOfItems.get(i);
				chk.setValue(false);
			}
		}
		
	}

	@Override
	public void configure() {
		
		String groupFieldType = getGroupFieldType();
		
		String groupFieldAlignment = getGroupFieldAlignment();
		
		Integer fieldLimit = getFieldLimit();
		
		ArrayList<String> listOfItemsInGroupField = getListOfItemsInGroupField();
		
		if(basePanel==null)
			 basePanel = new FlexTable();
		
		
		if (listOfItemsInGroupField!=null) {
			if (groupFieldType.equals(GroupFieldConstant.GFTYPE_CHKBOXGROUP)) {

				if (groupFieldAlignment.equals(GroupFieldConstant.GF_ALIGN_HORIZONTAL)) {

					for (int item = 0; item < listOfItemsInGroupField.size(); item++) {
						CheckboxField chkBoxField = getCheckBoxField(listOfItemsInGroupField.get(item));
						basePanel.setWidget(row, column, chkBoxField);
						addClickHandler(chkBoxField);
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
						basePanel.setWidget(row, column, chkBoxField);
						addClickHandler(chkBoxField);
						if (row >= fieldLimit - 1) {
							column++;
							row = 0;
						} else {
							row++;
						}
					}
				}

			} else if (groupFieldType.equals(GroupFieldConstant.GFTYPE_RADIOGROUP)) {

				if (groupFieldAlignment.equals(GroupFieldConstant.GF_ALIGN_HORIZONTAL)) {

					for (int item = 0; item < listOfItemsInGroupField.size(); item++) {
						RadioButton radioButton = getRadioButton(listOfItemsInGroupField.get(item));
						basePanel.setWidget(row, column, radioButton);
						if (column >= fieldLimit - 1) {
							row++;
							column = 0;
						} else {
							column++;
						}
					}
				} else {

					for (int item = 0; item < listOfItemsInGroupField.size(); item++) {
						RadioButton radioButton = getRadioButton(listOfItemsInGroupField.get(item));
						basePanel.setWidget(row, column, radioButton);
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

	@Override
	public String getFieldValue() {
		return this.fieldValue;
	}

	@Override
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
	
	@Override
	public Configuration getConfiguration() {
		return this.configuration;
	}

	@Override
	public void setConfiguration(Configuration conf) {
		this.configuration = conf;
	}

	@Override
	public void onFieldEvent(FieldEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Method will return the primary css applied to field.
	 * @return
	 */
	public String getPrimaryCss(){
		
		if(getConfiguration()!=null){
			
			String primaryCss = getConfiguration().getPropertyByName(GroupFieldConstant.GF_PRIMARYCSS);
			if(primaryCss !=null){
				return primaryCss;
			}else{
				return null;
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

	private void addClickHandler(final CheckBox checkBox) {
		checkBox.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				FieldEvent fieldEvent = new FieldEvent();
				fieldEvent.setEventData(checkBox);
				boolean value = checkBox.getValue();
				if(value) {
					fieldEvent.setEventType(FieldEvent.CHECKBOX_SELECT);
				} else {
					fieldEvent.setEventType(FieldEvent.CHECKBOX_DESELECT);
				}
				AppUtils.EVENT_BUS.fireEvent(fieldEvent);
			}
		});
		
	}

	
	public interface GroupFieldConstant{
		
		public static final String GF_TYPE = "fieldType";
		public static final String GFTYPE_CHKBOXGROUP = "chkBoxGroup";
		public static final String GFTYPE_RADIOGROUP = "radioGroup";
		public static final String GF_ALIGNMENT = "alignment";
		public static final String GF_ALIGN_VERTICAL = "alignVertical";
		public static final String GF_ALIGN_HORIZONTAL = "alignHorizontal";
		public static final String GF_LIMIT = "limit";
		
		public static final String GF_PRIMARYCSS = "fieldPrimaryCss";
		public static final String GF_DEPENDENTCSS = "fieldDependentCss";
		public static final String GF_LIST_OF_ITEMS = "listOfItems";
		
	}

	public ArrayList<Widget> getListOfItems() {
		return listOfItems;
	}

	public void setListOfItems(ArrayList<Widget> listOfItems) {
		this.listOfItems = listOfItems;
	}


}

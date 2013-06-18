package in.appops.showcase.web.gwt.fields.client;

import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.fields.Field;
import in.appops.platform.core.shared.Configurable;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.RadioButton;

public class GroupField extends Composite implements Field,Configurable{

	private Configuration configuration;
	private String fieldValue;
	private FlexTable basePanel;
	private Integer row = 0;
	private Integer column = 0;
	

	@Override
	public void create() throws AppOpsException {
		
		if(getConfiguration() == null)
			throw new AppOpsException("GroupField configuration unavailable");
		
		initWidget(basePanel);
	}
	
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

	@Override
	public void configure() {
		
		String groupFieldAlignment = getGroupFieldAlignment();
				
		basePanel = new FlexTable();
		
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
	
	public void addItemToGroup(String text, boolean value) {
		
		String groupFieldType = getGroupFieldType();
		
		String groupFieldAlignment = getGroupFieldAlignment();
		
		Integer fieldLimit = getFieldLimit();
		
		if(groupFieldType.equals(GroupFieldConstant.GFTYPE_CHKBOXGROUP)){
		   		CheckBox checkBox = new CheckBox();
				checkBox.setText(text);
				checkBox.setValue(value);
				checkBox.setStylePrimaryName(getPrimaryCss());
				addClickHandler(checkBox);
				
			if (groupFieldAlignment.equals(GroupFieldConstant.GF_ALIGN_HORIZONTAL)) {

				basePanel.setWidget(row, column, checkBox);
				if (column >= fieldLimit-1) {
					row++;
					column = 0;
				}else{
					column++;
				}
			} else {
				basePanel.setWidget(row, column, checkBox);
				if (row >= fieldLimit-1) {
					column++;
					row = 0;
				}else{
					row++;
				}
			}
		   		
		 }else if(groupFieldType.equals(GroupFieldConstant.GFTYPE_RADIOGROUP)){
		   		
		   		RadioButton radioButton = new RadioButton("singleSelection");
				radioButton.setText(text);
				radioButton.setValue(value);
				radioButton.setStylePrimaryName(getPrimaryCss());
				
				if (groupFieldAlignment.equals(GroupFieldConstant.GF_ALIGN_HORIZONTAL)) {

					basePanel.setWidget(row, column, radioButton);
					if (column >= fieldLimit-1) {
						row++;
						column = 0;
					}else{
						column++;
					}
				} else {
					basePanel.setWidget(row, column, radioButton);
					if (row >= fieldLimit-1) {
						column++;
						row = 0;
					}else{
						row++;
					}
				}
				
		   	}
		   	
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
		public static final String GF_DEBUGID = "fieldDebugId";
		
	}

}

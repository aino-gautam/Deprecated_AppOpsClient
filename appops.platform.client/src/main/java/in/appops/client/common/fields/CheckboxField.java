package in.appops.client.common.fields;

import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.fields.GroupField.GroupFieldConstant;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.CheckBox;

public class CheckboxField extends CheckBox implements Field,ClickHandler{

	private Configuration configuration;
	private String fieldValue;
	
	public CheckboxField(){
		super();
		this.addClickHandler(this);
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
	public void create() throws AppOpsException {
		if(getConfiguration() == null)
			throw new AppOpsException("CheckBox configuration unavailable");
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
	 * Method will return the dependent css applied to field.
	 * @return
	 */
	public String getDependentCss(){
		
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
	 * Method will return the display text of checkbox.
	 * @return
	 */
	public String getDisplayText(){
		
		if(getConfiguration()!=null){
			
			String displayText = getConfiguration().getPropertyByName(CheckBoxFieldConstant.CF_DISPLAYTEXT);
			if(displayText !=null){
				return displayText;
			}else{
				return "";
			}
		}
		return null;
	}
	
	/**
	 * Method will return the display text of checkbox.
	 * @return
	 */
	public String getFieldId(){
		
		if(getConfiguration()!=null){
			
			String id = getConfiguration().getPropertyByName(CheckBoxFieldConstant.CF_ID);
			if(id !=null){
				return id;
			}else{
				return getDisplayText();
			}
		}
		return null;
	}
	
	/**
	 * Method will check whether checkbox is checked or not.
	 * @return
	 */
	public Boolean isFieldChecked(){
		
		if(getConfiguration()!=null){
			
			Boolean isChecked = getConfiguration().getPropertyByName(CheckBoxFieldConstant.CF_CHECKED);
			if(isChecked !=null){
				return isChecked;
			}else{
				return false;
			}
		}
		return null;
	}

	@Override
	public void clear() {
		this.setValue(false);
	}

	@Override
	public void reset() {
		this.setValue(Boolean.valueOf(getFieldValue()));
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
	public void onFieldEvent(FieldEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(ClickEvent event) {
		FieldEvent fieldEvent = new FieldEvent();
		fieldEvent.setEventData(this);
		boolean value = this.getValue();
		if(value) {
			fieldEvent.setEventType(FieldEvent.CHECKBOX_SELECT);
		} else {
			fieldEvent.setEventType(FieldEvent.CHECKBOX_DESELECT);
		}
		AppUtils.EVENT_BUS.fireEvent(fieldEvent);
	}

	@Override
	public void configure() {
		
		this.setValue(isFieldChecked());
			
		this.setText(getDisplayText());
		if(getPrimaryCss()!=null)
			this.setStylePrimaryName(getPrimaryCss());
		if(getDependentCss()!=null)
			this.addStyleName(getDependentCss());
	}
	
	
	public interface CheckBoxFieldConstant{
		
		public static final String CF_DISPLAYTEXT = "fieldDisplayText";
		public static final String CF_ID = "fieldId";
		public static final String CF_CHECKED = "fieldChecked";
		public static final String CF_PRIMARYCSS = "fieldPrimaryCss";
		public static final String CF_DEPENDENTCSS = "fieldDependentCss";
		
	}
}
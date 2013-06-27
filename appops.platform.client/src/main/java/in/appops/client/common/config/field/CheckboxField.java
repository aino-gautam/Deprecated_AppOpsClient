package in.appops.client.common.config.field;

import in.appops.client.common.config.field.BaseField;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DockPanel;

public class CheckboxField extends BaseField{

	private CheckBox checkBox;
	
	public CheckboxField(){
		checkBox = new CheckBox();
	}
	
	@Override
	public void create() {
		
		basePanel.add(checkBox,DockPanel.CENTER);
		
	}
	
	/**
	 * Method will return the display text of checkbox.
	 * @return
	 */
	public String getDisplayText(){
		
		String displayText = "";
		if(getConfigurationValue(CheckBoxFieldConstant.CF_DISPLAYTEXT) != null) {
			
			displayText = (String) getConfigurationValue(CheckBoxFieldConstant.CF_DISPLAYTEXT);
		}
		return displayText;
	}
	
	/**
	 * Method will check whether checkbox is checked or not.
	 * @return
	 */
	public Boolean isFieldChecked(){
		
		Boolean isChecked = false;
		
		if(getConfigurationValue(CheckBoxFieldConstant.CF_CHECKED) != null) {
			
			isChecked = (Boolean) getConfigurationValue(CheckBoxFieldConstant.CF_CHECKED);
		}
		return isChecked;
	}


	@Override
	public void reset() {
		this.setValue(Boolean.valueOf(getFieldValue()));
	}


	@Override
	public void setValue(Object value) {
		super.setValue(value);
		checkBox.setValue((Boolean) value);
	}
	
	@Override
	public Object getValue() {
		return checkBox.getValue();
	}
	
	@Override
	public void configure() {
		
		checkBox.setValue(isFieldChecked());
			
		checkBox.setText(getDisplayText());
		
		if(getBaseFieldPrimCss()!=null)
			this.setStylePrimaryName(getBaseFieldPrimCss());
		if(getBaseFieldCss()!=null)
			this.addStyleName(getBaseFieldCss());
	}
	
	
	public interface CheckBoxFieldConstant extends BaseFieldConstant{
		
		public static final String CF_DISPLAYTEXT = "displayText";
		
		public static final String CF_CHECKED = "isChecked";
		
	}
}
package in.appops.client.common.fields;

import in.appops.client.common.config.field.BaseField;

import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.RadioButton;

public class RadioButtonField extends BaseField{

	private RadioButton radioBtn;
	
	public RadioButtonField(){
		radioBtn = new RadioButton("singleSelection");
	}
	
	@Override
	public void create() {
		basePanel.add(radioBtn,DockPanel.CENTER);
	}
	
	/**
	 * Method will return the display text of checkbox.
	 * @return
	 */
	public String getDisplayText(){
		
		String displayText = "";
		if(getConfigurationValue(RadionButtonFieldConstant.RF_DISPLAYTEXT) != null) {
			
			displayText = (String) getConfigurationValue(RadionButtonFieldConstant.RF_DISPLAYTEXT);
		}
		return displayText;
	}
	
	/**
	 * Method will return the display text of checkbox.
	 * @return
	 */
	public String getFieldId(){
		
		String  id = getDisplayText();
		
		if(getConfigurationValue(RadionButtonFieldConstant.RF_ID) != null) {
			
			id = (String) getConfigurationValue(RadionButtonFieldConstant.RF_ID);
		}
		return id;
	}
	
	/**
	 * Method will check whether checkbox is checked or not.
	 * @return
	 */
	public Boolean isFieldChecked(){
		
		Boolean isChecked = false;
		
		if(getConfigurationValue(RadionButtonFieldConstant.RF_CHECKED) != null) {
			
			isChecked = (Boolean) getConfigurationValue(RadionButtonFieldConstant.RF_CHECKED);
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
		radioBtn.setValue((Boolean) value);
	}
	
	@Override
	public Object getValue() {
		return radioBtn.getValue();
	}
	@Override
	public void configure() {
		
		radioBtn.setValue(isFieldChecked());
			
		radioBtn.setText(getDisplayText());
		
		if(getBaseFieldPrimCss()!=null)
			this.setStylePrimaryName(getBaseFieldPrimCss());
		if(getBaseFieldCss()!=null)
			this.addStyleName(getBaseFieldCss());
	}
	
	
	public interface RadionButtonFieldConstant extends BaseFieldConstant{
		
		/** Display text for radio button **/
		public static final String RF_DISPLAYTEXT = "displayText";
		
		/** Specifies the id for radio button **/
		public static final String RF_ID = "id";
		
		/** Specify isChecked property for radio button **/
		public static final String RF_CHECKED = "isChecked";
		
	}
}
package in.appops.client.common.config.field;

import java.util.logging.Level;
import java.util.logging.Logger;

import in.appops.client.common.config.field.BaseField;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.RadioButton;

public class RadioButtonField extends BaseField implements ValueChangeHandler{

	private RadioButton radioBtn;
	private Logger logger = Logger.getLogger(getClass().getName());

	public RadioButtonField(){
		radioBtn = new RadioButton("singleSelection");
	}
	
	@Override
	public void create() {
		try {
			logger.log(Level.INFO, "[RadioButtonField] ::In create method ");
			radioBtn.addValueChangeHandler(this);
			basePanel.add(radioBtn,DockPanel.CENTER);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[RadioButtonField] ::Exception in create method :"+e);
		}
	}
	
	/**
	 * Method will return the display text of checkbox.
	 * @return
	 */
	public String getDisplayText(){
		
		String displayText = "";
		try {
			logger.log(Level.INFO, "[RadioButtonField] ::In getDisplayText method ");
			if(getConfigurationValue(RadionButtonFieldConstant.RF_DISPLAYTEXT) != null) {
				
				displayText = (String) getConfigurationValue(RadionButtonFieldConstant.RF_DISPLAYTEXT);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[RadioButtonField] ::Exception in getDisplayText method :"+e);
		}
		return displayText;
	}
	
	/**
	 * Method will return the display text of checkbox.
	 * @return
	 */
	public String getFieldId(){
		
		String  id = getDisplayText();
		
		try {
			logger.log(Level.INFO, "[RadioButtonField] ::In getFieldId method ");
			if(getConfigurationValue(RadionButtonFieldConstant.RF_ID) != null) {
				
				id = (String) getConfigurationValue(RadionButtonFieldConstant.RF_ID);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[RadioButtonField] ::Exception in getFieldId method :"+e);
		}
		return id;
	}
	
	/**
	 * Method will check whether checkbox is checked or not.
	 * @return
	 */
	public Boolean isFieldChecked(){
		
		Boolean isChecked = false;
		
		try {
			logger.log(Level.INFO, "[RadioButtonField] ::In isFieldChecked method ");
			if(getConfigurationValue(RadionButtonFieldConstant.RF_CHECKED) != null) {
				
				isChecked = (Boolean) getConfigurationValue(RadionButtonFieldConstant.RF_CHECKED);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[RadioButtonField] ::Exception in isFieldChecked method :"+e);
		}
		return isChecked;
	}


	@Override
	public void reset() {
		try {
			logger.log(Level.INFO, "[RadioButtonField] ::In reset method ");
			this.setValue(Boolean.valueOf(getFieldValue()));
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[RadioButtonField] ::Exception in reset method :"+e);
		}
	}

	@Override
	public void setValue(Object value) {
		try {
			logger.log(Level.INFO, "[RadioButtonField] ::In setValue method ");
			super.setValue(value);
			radioBtn.setValue((Boolean) value);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[RadioButtonField] ::Exception in setValue method :"+e);
		}
	}
	
	@Override
	public Object getValue() {
		return radioBtn.getValue();
	}
	@Override
	public void configure() {
		
		try {
			logger.log(Level.INFO, "[RadioButtonField] ::In configure method ");
			radioBtn.setValue(isFieldChecked());
				
			radioBtn.setText(getDisplayText());
			
			if(getBaseFieldPrimCss()!=null)
				this.setStylePrimaryName(getBaseFieldPrimCss());
			if(getBaseFieldCss()!=null)
				this.addStyleName(getBaseFieldCss());
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[RadioButtonField] ::Exception in configure method :"+e);
		}
	}
	
	
	public interface RadionButtonFieldConstant extends BaseFieldConstant{
		
		/** Display text for radio button **/
		public static final String RF_DISPLAYTEXT = "displayText";
		
		/** Specifies the id for radio button **/
		public static final String RF_ID = "id";
		
		/** Specify isChecked property for radio button **/
		public static final String RF_CHECKED = "isChecked";
		
	}


	@Override
	public void onValueChange(ValueChangeEvent event) {
		
		try {
			logger.log(Level.INFO, "[RadioButtonField] ::In onValueChange method ");
			RadioButton radioButton = (RadioButton) event.getSource();
			
			FieldEvent fieldEvent = new FieldEvent();
			fieldEvent.setEventType(FieldEvent.RADIOBUTTON_SELECTED);
			
			fieldEvent.setEventData(radioButton);
			AppUtils.EVENT_BUS.fireEvent(fieldEvent);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[RadioButtonField] ::Exception in onValueChange method :"+e);
		}
		
	}
}
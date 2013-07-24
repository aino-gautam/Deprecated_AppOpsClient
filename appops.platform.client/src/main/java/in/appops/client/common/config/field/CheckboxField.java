package in.appops.client.common.config.field;

import java.util.logging.Level;
import java.util.logging.Logger;

import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DockPanel;

public class CheckboxField extends BaseField implements ValueChangeHandler{

	private CheckBox checkBox;
	public Logger logger = Logger.getLogger(getClass().getName());

	public CheckboxField(){
		checkBox = new CheckBox();
	}
	
	@Override
	public void create() {
		logger.log(Level.INFO, "[CheckboxField] ::In create method ");
		checkBox.addValueChangeHandler(this);
		basePanel.add(checkBox,DockPanel.CENTER);
		
	}
	
	/**
	 * Method will return the display text of checkbox.
	 * @return
	 */
	public String getDisplayText(){
		
		String displayText = "";
		try {
			logger.log(Level.INFO, "[CheckboxField] ::In getDisplayText method ");
			if(getConfigurationValue(CheckBoxFieldConstant.CF_DISPLAYTEXT) != null) {
				
				displayText = (String) getConfigurationValue(CheckBoxFieldConstant.CF_DISPLAYTEXT);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[CheckboxField] ::Exception in getDisplayText method :"+e);
		}
		return displayText;
	}
	
	/**
	 * Method will check whether checkbox is checked or not.
	 * @return
	 */
	public Boolean isFieldChecked(){
		
		Boolean isChecked = false;
		
		try {
			logger.log(Level.INFO, "[CheckboxField] ::In isFieldChecked method ");
			if(getConfigurationValue(CheckBoxFieldConstant.CF_CHECKED) != null) {
				
				isChecked = (Boolean) getConfigurationValue(CheckBoxFieldConstant.CF_CHECKED);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[CheckboxField] ::Exception in isFieldChecked method :"+e);
		}
		return isChecked;
	}


	@Override
	public void reset() {
		logger.log(Level.INFO, "[CheckboxField] ::In reset method ");
		this.setValue(Boolean.valueOf(getFieldValue()));
	}


	@Override
	public void setValue(Object value) {
		try {
			logger.log(Level.INFO, "[CheckboxField] ::In setValue method ");
			super.setValue(value);
			checkBox.setValue((Boolean) value);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[CheckboxField] ::Exception in setValue method :"+e);
		}
	}
	
	@Override
	public Object getValue() {
		logger.log(Level.INFO, "[CheckboxField] ::In getValue method ");
		return checkBox.getValue();
	}
	
	@Override
	public void configure() {
		
		try {
			logger.log(Level.INFO, "[CheckboxField] ::In configure method ");
			checkBox.setValue(isFieldChecked());
				
			checkBox.setText(getDisplayText());
			
			if(getBaseFieldPrimCss()!=null)
				this.setStylePrimaryName(getBaseFieldPrimCss());
			if(getBaseFieldCss()!=null)
				this.addStyleName(getBaseFieldCss());
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[CheckboxField] ::Exception in configure method :"+e);
		}
	}
	
	
	public interface CheckBoxFieldConstant extends BaseFieldConstant{
		
		public static final String CF_DISPLAYTEXT = "displayText";
		
		public static final String CF_CHECKED = "isChecked";
		
	}

	@Override
	public void onValueChange(ValueChangeEvent event) {
		
		try {
			logger.log(Level.INFO, "[CheckboxField] ::In onValueChange method ");
			CheckBox checkBox = (CheckBox) event.getSource();
			boolean checked = checkBox.getValue();
			FieldEvent fieldEvent = new FieldEvent();
			if(checked)
				fieldEvent.setEventType(FieldEvent.CHECKBOX_SELECT);
			else
				fieldEvent.setEventType(FieldEvent.CHECKBOX_DESELECT);
			
			fieldEvent.setEventData(checkBox);
			AppUtils.EVENT_BUS.fireEvent(fieldEvent);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[CheckboxField] ::Exception in onValueChange method :"+e);
		}
		
	}
}
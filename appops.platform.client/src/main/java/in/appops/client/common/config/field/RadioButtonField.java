package in.appops.client.common.config.field;

import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.RadioButton;

public class RadioButtonField extends BaseField implements ValueChangeHandler, BlurHandler{

	private RadioButton radioBtn;
	private Logger logger = Logger.getLogger(getClass().getName());
	private HandlerRegistration changeHandler  =  null;
	private HandlerRegistration blurHandler  =  null;
	
	/*******************  Fields ID *****************************/
	
	private static String SINGLE_SELECTION = "singleSelection";

	public RadioButtonField(){
		
	}
	
	@Override
	public void create() {
		logger.log(Level.INFO, "[RadioButtonField] ::In create method ");
		try {
			changeHandler = radioBtn.addValueChangeHandler(this);
			blurHandler = radioBtn.addBlurHandler(this);
			getBasePanel().add(radioBtn,DockPanel.CENTER);
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
	 * Method will return the group ID in which the radioButton will be added.
	 * @return
	 */
	public String getGroupId(){
		
		String name = SINGLE_SELECTION;
		
		if(getConfigurationValue(RadionButtonFieldConstant.RF_GROUPID) != null) {
			
			name = (String) getConfigurationValue(RadionButtonFieldConstant.RF_GROUPID);
		}
		return name;
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

	/**
	 * Method removed registered handlers from field
	 */
	@Override
	public void removeRegisteredHandlers() {
		
		if(changeHandler!=null)
			changeHandler.removeHandler();
		
		if(blurHandler!=null)
			blurHandler.removeHandler();
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
		
		radioBtn = new RadioButton(getGroupId());
		try {
			logger.log(Level.INFO, "[RadioButtonField] ::In configure method ");
			radioBtn.setValue(isFieldChecked());
				
			radioBtn.setText(getDisplayText());
			
			if(getBaseFieldPrimCss()!=null)
				radioBtn.setStylePrimaryName(getBaseFieldPrimCss());
			if(getBaseFieldDependentCss()!=null)
				radioBtn.addStyleName(getBaseFieldDependentCss());
			if(getTabIndex()!=null)
				radioBtn.setTabIndex(getTabIndex());
			
			if (getBasePanelPrimCss() != null)
				getBasePanel().setStylePrimaryName(getBasePanelPrimCss());
			if (getBasePanelDependentCss() != null)
				getBasePanel().addStyleName(getBasePanelDependentCss());
			
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[RadioButtonField] ::Exception in configure method :"+e);
		}
	}
	

	@Override
	public void onValueChange(ValueChangeEvent event) {
		
		FieldEvent fieldEvent = new FieldEvent();
		fieldEvent.setEventType(FieldEvent.RADIOBUTTON_SELECTED);
		fieldEvent.setEventSource(this);
		AppUtils.EVENT_BUS.fireEvent(fieldEvent);
		
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
			logger.log(Level.SEVERE,"[RadioButtonField]::Exception In onBlur  method :"+e);
		}
		
	}
	
	public interface RadionButtonFieldConstant extends BaseFieldConstant{
		
		/** Display text for radio button **/
		public static final String RF_DISPLAYTEXT = "displayText";
		
		/** Specify isChecked property for radio button **/
		public static final String RF_CHECKED = "isChecked";
		
		/** Specify the group name for the radioButton **/
		public static final String RF_GROUPID = "groupId";
		
	}

}
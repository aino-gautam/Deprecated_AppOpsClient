package in.appops.client.common.config.field;

import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DockPanel;

public class CheckboxField extends BaseField implements ValueChangeHandler, BlurHandler, KeyDownHandler{

	private CheckBox checkBox;
	public Logger logger = Logger.getLogger(getClass().getName());
	private HandlerRegistration changeHandler  =  null;
	private HandlerRegistration blurHandler  =  null;
	private HandlerRegistration keydownHandler  =  null;
	
	/*******************  Fields ID *****************************/
	private static String MULTI_SELECTION = "multiSelection";

	public CheckboxField(){
		checkBox = new CheckBox();
	}
	
	@Override
	public void create() {
		logger.log(Level.INFO, "[CheckboxField] ::In create method ");
		changeHandler = checkBox.addValueChangeHandler(this);
		blurHandler = checkBox.addBlurHandler(this);
		keydownHandler = checkBox.addKeyDownHandler(this);
		getBasePanel().add(checkBox,DockPanel.CENTER);
		
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

	/**
	 * Method will return the group ID in which the checkbox will be added.
	 * @return
	 */
	public String getGroupId(){
		
		String id = MULTI_SELECTION;
		if(getConfigurationValue(CheckBoxFieldConstant.CF_GROUPID) != null) {
			
			id = (String) getConfigurationValue(CheckBoxFieldConstant.CF_GROUPID);
		}
		return id;
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
		
		if(keydownHandler!=null)
			keydownHandler.removeHandler();
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
			checkBox.setValue(isFieldChecked());

			checkBox.setText(getDisplayText());

			if (getBaseFieldId() != null)
				checkBox.setName(getBaseFieldId());
			else
				checkBox.setName(getDisplayText());

			if (getBaseFieldPrimCss() != null)
				checkBox.setStylePrimaryName(getBaseFieldPrimCss());
			if (getBaseFieldDependentCss() != null)
				checkBox.addStyleName(getBaseFieldDependentCss());
			
			if (getBasePanelPrimCss() != null)
				getBasePanel().setStylePrimaryName(getBasePanelPrimCss());
			if (getBasePanelDependentCss() != null)
				getBasePanel().addStyleName(getBasePanelDependentCss());
			
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[CheckboxField] ::Exception in configure method :" + e);
		}
	}
	
	@Override
	public void onValueChange(ValueChangeEvent event) {
		
		try {
			CheckBox checkBox = (CheckBox) event.getSource();
			boolean checked = checkBox.getValue();
			FieldEvent fieldEvent = new FieldEvent();
			if(checked)
				fieldEvent.setEventType(FieldEvent.CHECKBOX_SELECT);
			else
				fieldEvent.setEventType(FieldEvent.CHECKBOX_DESELECT);
			
			checkBox.setFocus(true);
			fieldEvent.setEventData(checkBox.getName());
			fieldEvent.setEventSource(this);
			AppUtils.EVENT_BUS.fireEvent(fieldEvent);
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Level.SEVERE,"[CheckboxField]::Exception In onValueChange  method :"+e);
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
			logger.log(Level.SEVERE,"[CheckboxField]::Exception In onBlur  method :"+e);
		}
		
	}
	
	public interface CheckBoxFieldConstant extends BaseFieldConstant{
		
		public static final String CF_DISPLAYTEXT = "displayText";
		
		public static final String CF_CHECKED = "isChecked";
		
		public static final String CF_GROUPID = "groupId";
		
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
			logger.log(Level.SEVERE, "[CheckboxField] ::Exception In onKeyDown method "+e);
		}
		
	}
	
	public void setFieldFocus() {
		checkBox.setFocus(true);
	}
}
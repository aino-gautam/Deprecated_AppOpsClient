package in.appops.client.common.fields;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.AppopsEvent;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;

/**
 * Field class to represent a {@link TextBox}, {@link TextArea} or {@link PasswordTextBox}
 * @author nairutee
 *
 */
public class TextField extends Composite implements Field, FocusHandler, ValueChangeHandler, BlurHandler{

	private Configuration configuration;
	private String fieldValue;
	private TextBox textBox;
	private PasswordTextBox passwordTextBox;
	private TextArea textArea;
	private String fieldType;
	private String tempFieldValue;
	private HandlerManager handlerManager;
	
	public static final String TEXTFIELD_VISIBLELINES = "textFieldVisibleLines";
	public static final String TEXTFIELD_READONLY = "textFieldReadOnly";
	public static final String TEXTFIELD_PRIMARYCSS = "textFieldPrimaryCss";
	public static final String TEXTFIELD_DEPENDENTCSS = "textFieldDependentCss";
	public static final String TEXTFIELD_DEBUGID = "textFieldDebugId";
	public static final String TEXTFIELD_TYPE = "textFieldType";
	
	public static final String TEXTFIELDTYPE_TEXTBOX = "textbox";
	public static final String TEXTFIELDTYPE_PASSWORDTEXTBOX = "passowrdTextbox";
	public static final String TEXTFIELDTYPE_TEXTAREA = "textarea";
	
	static EventBus eventBus = GWT.create(SimpleEventBus.class); 
	
	public TextField(){
		handlerManager = new HandlerManager(this);
	}
	
	/**
	 * creates the field UI
	 * @throws AppOpsException 
	 */
	public void createField() throws AppOpsException {
		if(getConfiguration() == null)
			throw new AppOpsException("TextField configuration unavailable");
		
		Integer visibleLines = (Integer) getConfiguration().getPropertyByName(TEXTFIELD_VISIBLELINES);
		fieldType = getConfiguration().getPropertyByName(TEXTFIELD_TYPE).toString();
		if(visibleLines == 1){
			if(fieldType.equalsIgnoreCase(TEXTFIELDTYPE_TEXTBOX)){
				textBox = new TextBox();
				textBox.setText(getFieldValue());
				textBox.setReadOnly((Boolean) getConfiguration().getPropertyByName(TEXTFIELD_READONLY));
				if(getConfiguration().getPropertyByName(TEXTFIELD_PRIMARYCSS) != null)
					textBox.setStylePrimaryName(getConfiguration().getPropertyByName(TEXTFIELD_PRIMARYCSS).toString());
				if(getConfiguration().getPropertyByName(TEXTFIELD_DEPENDENTCSS) != null)
					textBox.addStyleName(getConfiguration().getPropertyByName(TEXTFIELD_DEPENDENTCSS).toString());
				if(getConfiguration().getPropertyByName(TEXTFIELD_DEBUGID) != null)
					textBox.ensureDebugId(getConfiguration().getPropertyByName(TEXTFIELD_DEBUGID).toString());
				
				initWidget(textBox);
				textBox.addFocusHandler(this);
				textBox.addBlurHandler(this);
				textBox.addValueChangeHandler(this);
			}else if(fieldType.equalsIgnoreCase(TEXTFIELDTYPE_PASSWORDTEXTBOX)){
				passwordTextBox = new PasswordTextBox();
				passwordTextBox.setText(getFieldValue());
				passwordTextBox.setReadOnly((Boolean) getConfiguration().getPropertyByName(TEXTFIELD_READONLY));
				if(getConfiguration().getPropertyByName(TEXTFIELD_PRIMARYCSS) != null)
					passwordTextBox.setStylePrimaryName(getConfiguration().getPropertyByName(TEXTFIELD_PRIMARYCSS).toString());
				if(getConfiguration().getPropertyByName(TEXTFIELD_DEPENDENTCSS) != null)
					passwordTextBox.addStyleName(getConfiguration().getPropertyByName(TEXTFIELD_DEPENDENTCSS).toString());
				if(getConfiguration().getPropertyByName(TEXTFIELD_DEBUGID) != null)
					passwordTextBox.ensureDebugId(getConfiguration().getPropertyByName(TEXTFIELD_DEBUGID).toString());
				
				initWidget(passwordTextBox);
				passwordTextBox.addFocusHandler(this);
				passwordTextBox.addBlurHandler(this);
				passwordTextBox.addValueChangeHandler(this);
			}
		}else{
			textArea = new TextArea();
			textArea.setVisibleLines(visibleLines);
			textArea.setText(getFieldValue());
			textArea.setReadOnly((Boolean) getConfiguration().getPropertyByName(TEXTFIELD_READONLY));
			
			if(getConfiguration().getPropertyByName(TEXTFIELD_PRIMARYCSS) != null)
				textArea.setStylePrimaryName(getConfiguration().getPropertyByName(TEXTFIELD_PRIMARYCSS).toString());
			if(getConfiguration().getPropertyByName(TEXTFIELD_DEPENDENTCSS) != null)
				textArea.addStyleName(getConfiguration().getPropertyByName(TEXTFIELD_DEPENDENTCSS).toString());
			if(getConfiguration().getPropertyByName(TEXTFIELD_DEBUGID) != null)
				textArea.ensureDebugId(getConfiguration().getPropertyByName(TEXTFIELD_DEBUGID).toString());
			
			initWidget(textArea);
			textArea.addFocusHandler(this);
			textArea.addBlurHandler(this);
			textArea.addValueChangeHandler(this);
		}
	}
	
	/**
	 * resets the field to the original value that has been set via setFieldValue()
	 */
	@Override
	public void resetField() {
		if(fieldType.equalsIgnoreCase(TEXTFIELDTYPE_TEXTBOX))
			textBox.setText(getFieldValue());
		else if(fieldType.equalsIgnoreCase(TEXTFIELDTYPE_PASSWORDTEXTBOX))
			passwordTextBox.setText(getFieldValue());
		else
			textArea.setText(getFieldValue());
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
	public String getFieldValue() {
		return this.fieldValue;
	}

	@Override
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	/**
	 * clears the field if it has any values
	 */
	@Override
	public void clearField() {
		if(fieldType.equalsIgnoreCase(TEXTFIELDTYPE_TEXTBOX))
			textBox.setText("");
		else if(fieldType.equalsIgnoreCase(TEXTFIELDTYPE_PASSWORDTEXTBOX))
			passwordTextBox.setText("");
		else
			textArea.setText("");
	}

	@Override
	public void onFocus(FocusEvent event) {
		FieldEvent fieldEvent = new FieldEvent();
		fieldEvent.setEventType(FieldEvent.EDITINITIATED);
		if(event.getSource() instanceof TextBox){
			fieldEvent.setEventData(textBox.getText());
		} else if(event.getSource() instanceof TextArea){
			fieldEvent.setEventData(textArea.getText());
		} else if(event.getSource() instanceof PasswordTextBox){
			fieldEvent.setEventData(passwordTextBox.getText());
		}
		AppUtils.EVENT_BUS.fireEvent(fieldEvent);
	}

	@Override
	public void onValueChange(ValueChangeEvent event) {
		FieldEvent fieldEvent = new FieldEvent();
		fieldEvent.setEventType(FieldEvent.EDITINPROGRESS);
		if(event.getSource() instanceof TextBox){
			fieldEvent.setEventData(textBox.getText());
		} else if(event.getSource() instanceof TextArea){
			fieldEvent.setEventData(textArea.getText());
		} else if(event.getSource() instanceof PasswordTextBox){
			fieldEvent.setEventData(passwordTextBox.getText());
		}
		AppUtils.EVENT_BUS.fireEvent(fieldEvent);
	}

	@Override
	public void onBlur(BlurEvent event) {
		FieldEvent fieldEvent = new FieldEvent();
		fieldEvent.setEventType(FieldEvent.EDITCOMPLETED);
		if(event.getSource() instanceof TextBox){
			fieldEvent.setEventData(textBox.getText());
		} else if(event.getSource() instanceof TextArea){
			fieldEvent.setEventData(textArea.getText());
		} else if(event.getSource() instanceof PasswordTextBox){
			fieldEvent.setEventData(passwordTextBox.getText());
		}
		AppUtils.EVENT_BUS.fireEvent(fieldEvent);
	}
	
	public String getTempFieldValue() {
		return tempFieldValue;
	}

	public void setTempFieldValue(String tempFieldValue) {
		this.tempFieldValue = tempFieldValue;
	}

	@Override
	public void onFieldEvent(FieldEvent event) {
		// TODO Auto-generated method stub
		
	}

}
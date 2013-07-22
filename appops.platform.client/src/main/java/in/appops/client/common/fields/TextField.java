package in.appops.client.common.fields;

import in.appops.client.common.config.field.BaseField;
import in.appops.client.common.config.field.NumericTextbox;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;

import java.util.ArrayList;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
Field class to represent a TextBox, TextArea ,NumerixBox ,EmailBox or PasswordTextBox

<h3>CSS Style Rules</h3>
<ul class='css'>
<li>.appops-TextField { primary style }</li>
<li>.appops-errorTopBottomCls { style to show errors outside the field }</li>
<li>.appops-errorIconCls { class with error icon set in the background  }</li>
<li>.appops-validFieldTopBottomCls { style to show valid messages outside the field }</li>
<li>.appops-validFieldIconCls { style class with valid field icon set in the background }</li>
<li>.appops-validFieldInline { style class to show valid field  }</li>
<li>.appops-suggestionText { style to show suggestion text }</li>
</ul>

<p>
<h3>Configuration</h3>
<a href="TextField.TextFieldConstant.html">Available configurations</a>
</p>

<p>
<h3>Example</h3>

<p>Following code results in a emailbox with inline suggestion with text "Enter email" ,If data entered in a field is 
not valid then it will show error on right side.  </p>

TextField emailTextField = new TextField();<br>
Configuration conf = new Configuration();<br>
conf.setPropertyByName(TextFieldConstant.TF_VISIBLELINES, visibleLines);<br>
conf.setPropertyByName(TextFieldConstant.BF_READONLY, readOnly);<br>
conf.setPropertyByName(TextFieldConstant.TF_TYPE, textFieldType);<br>
conf.setPropertyByName(TextFieldConstant.BF_PCLS, primaryCss);<br>
conf.setPropertyByName(TextFieldConstant.BF_DCLS, secondaryCss);<br>
conf.setPropertyByName(TextFieldConstant.BF_SUGGESTION_POS, TextFieldConstant.BF_SUGGESTION_INLINE);<br>
conf.setPropertyByName(TextFieldConstant.BF_SUGGESTION_TEXT, "Enter email");<br>
conf.setPropertyByName(TextFieldConstant.BF_VALIDATEONCHANGE, true);<br>
conf.setPropertyByName(TextFieldConstant.BF_ERRPOS, TextFieldConstant.BF_SIDE);<br>
emailTextField.setConfiguration(conf);<br>
emailTextField.configure();<br>
emailTextField.create();
</p>*/

public class TextField extends BaseField implements BlurHandler, KeyUpHandler,KeyPressHandler{

	private TextBox textBox;
	private PasswordTextBox passwordTextBox;
	private TextArea textArea;
	private NumericTextbox numericTextbox;
		
	public TextField(){
		
	}
	
	/**
	 * creates the field UI according the configuration set to it;
	 */
	public void create() {
		
			basePanel.add(getWidget(),DockPanel.CENTER);
	}
	
	/**
	 * Method read the configuration and set it to field .
	 */
	@Override
	public void configure() {
		
		String fieldType = getTextFieldType();
		
		if (fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_TXTBOX)	|| fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_EMAILBOX)) {
			createTextBox();
			
		} else if (fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_PSWBOX)) {
			createPasswordBox();

		} else if (fieldType.equalsIgnoreCase(TextFieldConstant.TFTTYPE_TXTAREA)) {
			createTextArea();
			
		}else if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_NUMERIC)){
			createNumericTextBox();
			
		}
		
		setSuggestion();
		if(getDefaultValue()!=null){
			setValue(getDefaultValue());
			setOriginalValue(getDefaultValue());
			setFieldValue(getDefaultValue().toString());
		}
		
	}
	
	/** 
	 * Method creates the textbox.
	 */
	private void createTextBox(){
		
		textBox = new TextBox();
		textBox.setReadOnly(isReadOnly());
		if(getBaseFieldPrimCss()!= null)
			textBox.setStylePrimaryName(getBaseFieldPrimCss());
		if(getBaseFieldCss() != null)
			textBox.addStyleName(getBaseFieldCss());
		
		textBox.setMaxLength(getFieldMaxLength());
		
		if(getTabIndex()!=null)
			textBox.setTabIndex(getTabIndex());
		
		textBox.addKeyPressHandler(this);
		
		//In case of simple textbox no validation is required.
			if(isValidateOnBlur()){
				textBox.addBlurHandler(this);
			}
			if(isValidateOnChange()){
				textBox.addKeyUpHandler(this);
			}
						
	}
	
	/** 
	 * Method creates the textArea.
	 */
	private void createTextArea(){
		
		textArea = new TextArea();
		
		Integer visibleLines = getNoOfVisibleLines();
		textArea.setVisibleLines(visibleLines);
		textArea.setReadOnly(isReadOnly());

		if(getBaseFieldPrimCss() != null)
			textArea.setStylePrimaryName(getBaseFieldPrimCss());
		if(getBaseFieldCss()!= null)
			textArea.addStyleName(getBaseFieldCss());
		if(getFieldCharWidth()!=null)
			textArea.setCharacterWidth(getFieldCharWidth());
				
	}
	
	/** 
	 * Method creates the passwordbox also add keypress handler and add blur and keyup handler if its set true in the configuration.
	 */
	private void createPasswordBox(){
		passwordTextBox = new PasswordTextBox();
		passwordTextBox.setReadOnly(isReadOnly());
		if(getBaseFieldPrimCss()!= null)
			passwordTextBox.setStylePrimaryName(getBaseFieldPrimCss());
		if(getBaseFieldCss() != null)
			passwordTextBox.addStyleName(getBaseFieldCss());
				
		passwordTextBox.setMaxLength(getFieldMaxLength());
		if(getTabIndex()!=null)
			passwordTextBox.setTabIndex(getTabIndex());
		
		
		/*** Events fired by passwordTextBox ****/
		
		passwordTextBox.addKeyPressHandler(this);
		
		if(isValidateOnBlur()){
			passwordTextBox.addBlurHandler(this);
		}
		if(isValidateOnChange()){
			passwordTextBox.addKeyUpHandler(this);
		}
	}
	
	/**
	 * Method creates the numericTextBox also add keypress and blur handler default and add keyup 
	 * handler if isValidateOnChange is set as true in the configuration .
	 */
	private void createNumericTextBox(){
		
		numericTextbox = new NumericTextbox(this);
		numericTextbox.setConfiguration(getConfiguration());
		numericTextbox.setReadOnly(isReadOnly());
		if(getBaseFieldPrimCss()!= null)
			numericTextbox.setStylePrimaryName(getBaseFieldPrimCss());
		if(getBaseFieldCss() != null)
			numericTextbox.addStyleName(getBaseFieldCss());
				
		numericTextbox.setMaxLength(getFieldMaxLength());
		
				
		if(getTabIndex()!=null)
			numericTextbox.setTabIndex(getTabIndex());
		
		/*** Events fired by passwordTextBox ****/
		
		numericTextbox.addKeyPressHandler(numericTextbox);
				
		if(isValidateOnChange()){
			numericTextbox.addKeyUpHandler(this);
		}
		
		numericTextbox.addBlurHandler(this);
		
	}
	
	/**
	 * resets the field to the original value that has been set via setFieldValue()
	 */
	@Override
	public void reset() {
		
		String fieldType = getTextFieldType();
		if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_TXTBOX) || fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_EMAILBOX)  ) {
			textBox.setText(getValue().toString());
		}else if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_PSWBOX))
			passwordTextBox.setText(getValue().toString());
		else if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_NUMERIC))
			numericTextbox.setText(getValue().toString());
		else
			textArea.setText(getValue().toString());
	}
	
	/**
	 * clears the field .
	 */
	@Override
	public void clear() {
		
		String fieldType = getTextFieldType();
		
		if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_TXTBOX)  || fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_EMAILBOX))
			textBox.setText("");
		else if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_PSWBOX))
			passwordTextBox.setText("");
		else if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_NUMERIC))
			numericTextbox.setValue("");
		else
			textArea.setText("");
	}
	
	/**
	 * Overriden method from BaseField returns the field value.
	 */
	@Override
	public String getFieldValue() {
		
		String fieldType = getTextFieldType();
		
		if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_TXTBOX) || fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_EMAILBOX))
			return textBox.getText();
		else if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_PSWBOX))
			return passwordTextBox.getText();
		else if( fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_NUMERIC)){
			return numericTextbox.getText();
		}else
			return textArea.getText();
		
	}
	
	/**
	 * Overriden method from BaseField sets the field value.
	 */
	@Override
	public void setFieldValue(String fieldValue) {
		
		String fieldType = getTextFieldType();
		
		if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_TXTBOX) || fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_EMAILBOX))
			textBox.setText(fieldValue);
		else if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_PSWBOX))
			 passwordTextBox.setText(fieldValue);
		else if( fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_NUMERIC)){
			numericTextbox.setText(fieldValue);
		}else
			textArea.setText(fieldValue);
		
	}
	
	/**
	 * Overriden method from BaseField returns the converted field value.
	 */
	@Override
	public void setValue(Object value) {
		
		super.setValue(value);
		clear();
		setFieldValue(value.toString());
		
	}
	
	/**
	 * Overriden method from BaseField returns the converted field value.
	 */
	@Override
	public Object getValue() {
		
		String fieldType = getTextFieldType();
		
		if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_NUMERIC)){
			if(validate()){
				if(numericTextbox.isAllowDecimal())
					return Double.parseDouble(numericTextbox.getText());
				else
					return Integer.parseInt(numericTextbox.getText());
			}
		}
		return getFieldValue();
		
	}
	
	/**
	 * Overriden method from BaseField returns the list of errors.
	 */
	@Override
	public ArrayList<String> getErrors(String value) {
		
		String fieldType = getTextFieldType();
		String errorText = null;
		ArrayList<String> errors = new ArrayList<String>();
		
		if (fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_EMAILBOX)) {
			errorText = validateEmail(getFieldValue());
			if(errorText!=null)
				errors.add(errorText);
		} else if (fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_PSWBOX)) {
			errorText = validatePassword(getFieldValue());
			if(errorText!=null)
				errors.add(errorText);
		} else if (fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_NUMERIC)) {
			errors.addAll(numericTextbox.validate());
		}
				
		return errors;
		
	}
	
	/**
	 * Overriden method from BaseField to set inline error.
	 */
	@Override
	public void setErrorInline () {
		getWidget().addStyleName(getErrorMsgCls());
		getWidget().addStyleName(getErrorIconCls());
		getWidget().setTitle(getInvalidMsg());
		
		if(getErrorIconBlobId()!=null)
			setCssPropertyToElement(getWidget(), getErrorIconBlobId());
	}
	
	/**
	 * Overriden method from BaseField to set inline msg for valid field.
	 */
	@Override
	protected void setValidationMsgInline () {
		getWidget().addStyleName(getValidFieldMsgCls());
		getWidget().addStyleName(getValidFieldIconCls());
		
		if(getValidIconBlobId()!=null)
			setCssPropertyToElement(getWidget(), getValidIconBlobId());
	}
	
	/**
	 * Overriden method from BaseField to clear inline msg .
	 */
	public void clearInlineMsg () {
		getWidget().removeStyleName(getErrorMsgCls());
		getWidget().removeStyleName(getErrorIconCls());
		getWidget().removeStyleName(getValidFieldMsgCls());
		getWidget().removeStyleName(getValidFieldIconCls());
		getWidget().setTitle("");
		
		if(getWidget().getElement().getStyle().getProperty("background")!=null)
			getWidget().getElement().getStyle().clearProperty("background");
	}
	
	/**
	 * Overriden method from BaseField to set inline suggestion for field.
	 */
	@Override
	protected void setSuggestionInline () {
		if(getSuggestionText()!=null)
			getWidget().getElement().setPropertyString("placeholder", getSuggestionText());
	}
	
	
	/** 
	 * Method used to validate an email.
	 * @return 
	 */
	private String validateEmail(Object value) {
		if (value.equals("")) {
			if (!isAllowBlank()) {
				return getBlankFieldText();
			}
		}else {
			if (!value.toString().matches(getEmailRegex())) {
				return getInvalidEmailText();
			}
		}
		return null;
	}
	
	/**
	 * Method validates the password.
	 */
	private String validatePassword(Object value){
		
		if (value.equals("")) {
			if (!isAllowBlank()) {
				return getBlankFieldText();
			}
		} else {
			if (value.toString().length() < getMinLength())
				return getMinLengthErrorText();
			
		}
		return null;
	}
	
			
	/**
	 * Method read regex expression from configuration. if it is not in the configuration then it returns DEC_REGEX_EXP;
	 * @return default regex expression.
	 */
	
	private String getEmailRegex(){
		if(getConfiguration()!=null){
			
			String regexExp =  getConfiguration().getPropertyByName(TextFieldConstant.EMAIL_REGEX_EXP);
			if(regexExp !=null){
				return regexExp; 
			}else{
				return TextFieldConstant.EMAIL_REGEX_EXP;
			}
		}
		return null;
		
	}
	
	/**
	 * Methos checks the field type and return that widget.
	 */
	public Widget getWidget() {
		
		String fieldType = getTextFieldType();
		if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_TXTBOX) || fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_EMAILBOX) ) {
			return textBox;
		} else if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_PSWBOX)) {
			return passwordTextBox;
		} else if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_NUMERIC)){
			return numericTextbox;
		}else{
			return textArea;
		}
	}
	/**
	 * Method checks whether field has visiblelines set in the configuration and returns .if it is not
	 * set in configuration then returns default value of it i.e 1
	 * @return noOfVisibleLines
	 */
	private Integer getNoOfVisibleLines(){
		
		Integer noOfVisibleLines = 1;
		if (getConfigurationValue(TextFieldConstant.TF_VISLINES) != null) {
			noOfVisibleLines = (Integer) getConfigurationValue(TextFieldConstant.TF_VISLINES);
		}
		return noOfVisibleLines;
	}
	
	/**
	 * Method will return textfieldType whether its textbox/passwordbox/emailbox. Default value will be textbox.
	 * @return
	 */
	private String getTextFieldType(){
		String fieldType = TextFieldConstant.TFTYPE_TXTBOX;
		if (getConfigurationValue(TextFieldConstant.TF_TYPE) != null) {
			fieldType = (String) getConfigurationValue(TextFieldConstant.TF_TYPE);
		}
		return fieldType;
	}
	
	/**
	 * Method read the maxlength set in the configuration and return . Default value is 255;
	 * @return
	 */
	
	private Integer getFieldMaxLength(){
		
		Integer maxLength = 255;
		if (getConfigurationValue(TextFieldConstant.TF_MAXLENGTH) != null) {
			maxLength = (Integer) getConfigurationValue(TextFieldConstant.TF_MAXLENGTH);
		}
		return maxLength;
	}
	
	/**
	 * Method read the field minimum length set in the configuration and return . Default value is 6;
	 * @return
	 */
	
	private Integer getMinLength(){
		
		Integer minLength = 6;
		if (getConfigurationValue(TextFieldConstant.TF_MINLENGTH) != null) {
			minLength = (Integer) getConfigurationValue(TextFieldConstant.TF_MINLENGTH);
		}
		return minLength;
	}
	
	/**
	 * Returns if field should be validated or not.
	 * @return
	 */
	private Boolean isValidateField(){
		Boolean validate = true;
		if (getConfigurationValue(TextFieldConstant.VALIDATEFIELD) != null) {
			validate = (Boolean) getConfigurationValue(TextFieldConstant.VALIDATEFIELD);
		}
		return validate;
	}
	
	/**
	 * Method read the charWidth set in the configuration and return . Default value is 255;
	 * @return
	 */
	
	private Integer getFieldCharWidth(){
		
		Integer charWidth = 255;
		if (getConfigurationValue(TextFieldConstant.TF_CHARWIDTH) != null) {
			charWidth = (Integer) getConfigurationValue(TextFieldConstant.TF_CHARWIDTH);
		}
		return charWidth;
	}
	
	/**
	 * Method will show suggestion title based on the suggestion position. 
	 */
	@Override
	protected void setSuggestion() {

		if (getSuggestionPosition().equals(BaseFieldConstant.BF_SUGGESTION_INLINE)) {
			setSuggestionInline();
		} else {
			if(getSuggestionText()!=null){
				Label suggestionLbl = new Label(getSuggestionText());
				suggestionLbl.setStylePrimaryName(getSuggestionMsgCls());
				setErrorOrSuggestion(suggestionLbl, getSuggestionPosition());
			}
		}

	}
		
	/**
	 * Method read and return error text for 
	 * @return
	 */
	
	private String getMinLengthErrorText(){
		String minValueText = "The minimum length for this field is "+ getMinLength();
		if (getConfigurationValue(TextFieldConstant.MIN_LEGTH_ERROR_TEXT) != null) {
			minValueText = (String) getConfigurationValue(TextFieldConstant.MIN_LEGTH_ERROR_TEXT);
		}
		return minValueText;
	}
	
	/**
	 * Method read and return error text for invalid email.
	 * @return
	 */
	private String getInvalidEmailText(){
		
		String invalidEmailText = "Invalid email";
		if (getConfigurationValue(TextFieldConstant.INVALID_EMAIL_TEXT) != null) {
			invalidEmailText = (String) getConfigurationValue(TextFieldConstant.INVALID_EMAIL_TEXT);
		}
		return invalidEmailText;
	}

	private void setFocus(){
		
		String fieldType = getTextFieldType();
		if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_TXTBOX) || fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_EMAILBOX)  ) {
			textBox.setFocus(true);
		}else if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_PSWBOX))
			passwordTextBox.setFocus(true);
		else if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_NUMERIC))
			numericTextbox.setFocus(true);
		else
			textArea.setFocus(true);
	}
	
	public String getFieldText() {
		
		String fieldType = getTextFieldType();
		
		if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_TXTBOX) || fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_EMAILBOX) || fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_NUMERIC) ) {
			return textBox.getText();
		} else if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_PSWBOX)) {
			return passwordTextBox.getText();
		} else {
			return textArea.getText();
		}
	}
	
	/**
	 * Method return the event which will be fired when user press enter key.  
	 * @return
	 */
	private Integer getEnterEvent() {
		Integer eventType = 0;
		if (getConfigurationValue(TextFieldConstant.TF_ENTER_EVENT) != null) {
			eventType = (Integer) getConfigurationValue(TextFieldConstant.TF_ENTER_EVENT);
		}
		return eventType;
	}
	
	/**
	 * Method return the event which will be fired when user change some value in field.  
	 * @return
	 */
	private Integer getValueChangedEvent() {
		Integer eventType = 0;
		if (getConfigurationValue(TextFieldConstant.TF_VALUE_CHANGED_EVENT) != null) {
			eventType = (Integer) getConfigurationValue(TextFieldConstant.TF_VALUE_CHANGED_EVENT);
		}
		return eventType;
	}
	
	/************     Events in which field is interested ******************************/
	@Override
	public void onKeyUp(KeyUpEvent event) {
		
		Integer keycode= event.getNativeKeyCode();
		if(keycode.equals(KeyCodes.KEY_BACKSPACE) || keycode.equals(KeyCodes.KEY_TAB)|| keycode.equals(KeyCodes.KEY_DELETE)){
			if(isValidateField()){
				if(validate())
					setValue(getValue());
			}else{
				setValue(getValue());
			}
			setFocus();
		}
				
	}
	
	@Override
	public void onBlur(BlurEvent event) {
		if(isValidateField()){
			if(validate()){
				if(numericTextbox!=null && numericTextbox.isAllowDecimal()){
					setValue(numericTextbox.fixPrecision());
				}
			}
		}else{
			setValue(getValue());
		}
		
	}

	@Override
	public void onKeyPress(KeyPressEvent event) {
		final int charCode= event.getUnicodeCharCode();
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				if (isValidateField()) {
					if (isValidateOnChange()) {
						if (validate())
							setValue(getValue());
						setFocus();
					}
				} else {
					setValue(getValue());
					setFocus();
				}
				
				if(isDirty()){
					if (charCode == KeyCodes.KEY_ENTER) {
						if (getEnterEvent() != 0) {
							FieldEvent fieldEvent = new FieldEvent();
							fieldEvent.setEventType(getEnterEvent());
							fieldEvent.setEventData(getValue());
							AppUtils.EVENT_BUS.fireEvent(fieldEvent);
						}
					}else if (getValueChangedEvent() != 0) {
						FieldEvent fieldEvent = new FieldEvent();
						fieldEvent.setEventType(getValueChangedEvent());
						fieldEvent.setEventData(getValue());
						AppUtils.EVENT_BUS.fireEvent(fieldEvent);
					}
				}
			}
		});
		
	}
	
	/****************** Textfield constants  *******************/
	
	public interface TextFieldConstant extends BaseFieldConstant {
		
		/** Specifies no of visible lines for textArea. **/
		public static final String TF_VISLINES = "visibleLines";
		
		/** Specifies max length for the field**/
		public static final String TF_MAXLENGTH = "maxlength";
		
		/** Specifies minimum length for the field**/
		public static final String TF_MINLENGTH = "minlength";
		
		/** Specifies character width for the field**/
		public static final String TF_CHARWIDTH = "charWidth";
		
		/** Specifies which field to use .Defaults to textbox**/
		public static final String TF_TYPE = "fieldType";
		
		/***  1.textbox   2.numeric box  3.emailbox 4.textarea  this are the types provided by textfield. ***/ 
		public static final String TFTYPE_TXTBOX = "txtbox";
		
		public static final String TFTYPE_PSWBOX = "passowrdTxtbox";
		
		public static final String TFTTYPE_TXTAREA = "txtarea";
		
		public static final String TFTYPE_EMAILBOX = "emailbox";
		
		public static final String TFTYPE_NUMERIC = "numeric";
		
		/** Specifies error text to be shown when user enters value less than min length**/
		public static final String MIN_LEGTH_ERROR_TEXT = "minLengthErrorText";
		
		/** Specifies error text to be shown for invalid email**/
		public static final String INVALID_EMAIL_TEXT = "invalidEmailText";
		
		/** Specifies email field regex **/
		public static final String EMAIL_REGEX_EXP = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
		
		/** Specifies whether decimal to allow decimal value. Defaults to false **/
		public static final String ALLOWDEC = "allowDecimal";
		
		/** Specifies min value for the field . Defaults to java min float value.**/
		public static final String MINVALUE = "minValue";
		
		/** Specifies max value for the field . Defaults to java max float value.**/
		public static final String MAXVALUE = "maxValue";
		
		public static final String PROPERTY_BY_FIELD_NAME = "propertyByFieldName";
		
		/** Specifies no. of precisions to use. **/
		public static final String DEC_PRECISION = "decPrecision";
		
		/** Specifies max value error text to be shown **/
		public static final String MAX_VALUE_TEXT = "maxValueText";
		
		/** Specifies min value error text to be shown **/
		public static final String MIN_VALUE_TEXT = "minValueText";
		
		/** Specifies negative value error text to be shown **/
		public static final String NEGATIVE_VALUE_TEXT = "negativevalTxt";
		
		/** Specifies the event that will be fired when user enters **/
		public static final String TF_ENTER_EVENT = "enterEvent";
		
		/** Specifies whether field should be validated or not**/
		public static final String VALIDATEFIELD = "validateField";
		
		/** Specifies the event that will be fired when field value is changed and not as original value that was set**/
		public static final String TF_VALUE_CHANGED_EVENT = "valueChangedEvent";
				
	}
}
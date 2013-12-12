package in.appops.client.common.config.field.textfield;

import in.appops.client.common.config.field.BaseField;
import in.appops.client.common.config.field.NumericTextbox;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;
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

public class TextField extends BaseField implements BlurHandler, KeyUpHandler,KeyPressHandler,KeyDownHandler{

	private TextBox textBox;
	private PasswordTextBox passwordTextBox;
	private TextArea textArea;
	private NumericTextbox numericTextbox;
	private HandlerRegistration keyPressHandler  = null;
	private HandlerRegistration keyUpHandler  = null;
	private HandlerRegistration blurHandler  = null;
	private HandlerRegistration keyDownHandler  = null;
	public Logger logger = Logger.getLogger(getClass().getName());
	private FieldEvent fieldEvent;

	public TextField(){

	}

	/**
	 * creates the field UI according the configuration set to it;
	 */
	public void create() {
		try {
			getBasePanel().add(getWidget(),DockPanel.CENTER);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[TextField] ::Exception In create method "+e);

		}
	}

	/**
	 * Method read the configuration and set it to field .
	 */
	@Override
	public void configure() {

		try {

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


			if (getBasePanelPrimCss() != null)
				getBasePanel().setStylePrimaryName(getBasePanelPrimCss());
			if (getBasePanelDependentCss() != null)
				getBasePanel().addStyleName(getBasePanelDependentCss());

		} catch (Exception e) {
			logger.log(Level.SEVERE, "[TextField] ::Exception In configure method "+e);

		}

	}

	/** 
	 * Method creates the textbox.
	 */
	private void createTextBox(){


		try {
			textBox = new TextBox();
			textBox.setReadOnly(isReadOnly());
			textBox.setEnabled(isEnabled());

			if (getBaseFieldPrimCss() != null)
				textBox.setStylePrimaryName(getBaseFieldPrimCss());
			if (getBaseFieldDependentCss() != null)
				textBox.addStyleName(getBaseFieldDependentCss());

			textBox.setMaxLength(getFieldMaxLength());

			if (getTabIndex() != null)
				textBox.setTabIndex(getTabIndex());

			keyPressHandler = textBox.addKeyPressHandler(this);
			blurHandler = textBox.addBlurHandler(this);
			keyUpHandler = textBox.addKeyUpHandler(this);
			keyDownHandler = textBox.addKeyDownHandler(this);

		} catch (Exception e) {
			logger.log(Level.SEVERE,"[TextField] ::Exception In createTextBox method " + e);

		}
	}

	/** 
	 * Method creates the textArea.
	 */
	private void createTextArea(){

		try {
			textArea = new TextArea();

			Integer visibleLines = getNoOfVisibleLines();
			textArea.setVisibleLines(visibleLines);
			textArea.setReadOnly(isReadOnly());
			textArea.setEnabled(isEnabled());

			if(getBaseFieldPrimCss() != null)
				textArea.setStylePrimaryName(getBaseFieldPrimCss());
			if(getBaseFieldDependentCss()!= null)
				textArea.addStyleName(getBaseFieldDependentCss());
			if(getFieldCharWidth()!=null)
				textArea.setCharacterWidth(getFieldCharWidth());

			if (getTabIndex() != null)
				textArea.setTabIndex(getTabIndex());

			keyPressHandler = textArea.addKeyPressHandler(this);
			blurHandler = textArea.addBlurHandler(this);
			keyUpHandler = textArea.addKeyUpHandler(this);
			keyDownHandler = textArea.addKeyDownHandler(this);

		} catch (Exception e) {
			logger.log(Level.SEVERE, "[TextField] ::Exception In createTextArea method "+e);

		}

	}

	/** 
	 * Method creates the passwordbox also add keypress handler and add blur and keyup handler if its set true in the configuration.
	 */
	private void createPasswordBox(){
		try {
			passwordTextBox = new PasswordTextBox();
			passwordTextBox.setReadOnly(isReadOnly());
			passwordTextBox.setEnabled(isEnabled());
			if (getBaseFieldPrimCss() != null)
				passwordTextBox.setStylePrimaryName(getBaseFieldPrimCss());
			if (getBaseFieldDependentCss() != null)
				passwordTextBox.addStyleName(getBaseFieldDependentCss());

			passwordTextBox.setMaxLength(getFieldMaxLength());
			if (getTabIndex() != null)
				passwordTextBox.setTabIndex(getTabIndex());

			/*** Events fired by passwordTextBox ****/

			keyPressHandler = passwordTextBox.addKeyPressHandler(this);
			blurHandler = passwordTextBox.addBlurHandler(this);
			keyUpHandler = passwordTextBox.addKeyUpHandler(this);
			keyDownHandler = passwordTextBox.addKeyDownHandler(this);

		} catch (Exception e) {
			logger.log(Level.SEVERE,"[TextField] ::Exception In createPasswordBox method " + e);

		}
	}

	/**
	 * Method creates the numericTextBox also add keypress and blur handler default and add keyup 
	 * handler if isValidateOnChange is set as true in the configuration .
	 */
	private void createNumericTextBox(){
		try {
			numericTextbox = new NumericTextbox(this);
			numericTextbox.setConfiguration(getConfiguration());
			numericTextbox.setReadOnly(isReadOnly());
			numericTextbox.setEnabled(isEnabled());
			if (getBaseFieldPrimCss() != null)
				numericTextbox.setStylePrimaryName(getBaseFieldPrimCss());
			if (getBaseFieldDependentCss() != null)
				numericTextbox.addStyleName(getBaseFieldDependentCss());

			numericTextbox.setMaxLength(getFieldMaxLength());

			if (getTabIndex() != null)
				numericTextbox.setTabIndex(getTabIndex());

			/*** Events fired by passwordTextBox ****/

			keyPressHandler = numericTextbox.addKeyPressHandler(numericTextbox);
			keyUpHandler = numericTextbox.addKeyUpHandler(this);
			blurHandler = numericTextbox.addBlurHandler(this);
			keyDownHandler = numericTextbox.addKeyDownHandler(this);

		} catch (Exception e) {

			logger.log(Level.SEVERE,"[TextField] ::Exception In createNumericTextBox method "+ e);
		}
	}

	/**
	 * resets the field to the original value that has been set via setFieldValue()
	 */
	@Override
	public void reset() {
		try {
			String fieldType = getTextFieldType();
			if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_TXTBOX) || fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_EMAILBOX)  ) {
				textBox.setText(getValue().toString());
			}else if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_PSWBOX))
				passwordTextBox.setText(getValue().toString());
			else if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_NUMERIC))
				numericTextbox.setText(getValue().toString());
			else
				textArea.setText(getValue().toString());
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[TextField] ::Exception In reset method "+e);

		}
	}

	/**
	 * clears the field .
	 */
	@Override
	public void clear() {

		try {  
			String fieldType = getTextFieldType();

			if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_TXTBOX)  || fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_EMAILBOX))
				textBox.setText("");
			else if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_PSWBOX))
				passwordTextBox.setText("");
			else if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_NUMERIC))
				numericTextbox.setValue("");
			else
				textArea.setText("");
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[TextField] ::Exception In clear method "+e);

		}
	}


	/**
	 * Overriden method from BaseField remove registered handlers from the field.
	 */
	@Override
	public void removeRegisteredHandlers() {
		if(keyUpHandler!=null)
			keyUpHandler.removeHandler();

		if(blurHandler!=null)
			blurHandler.removeHandler();

		if(keyPressHandler!=null)
			keyPressHandler.removeHandler();
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

		try { 

			super.setValue(value);
			clear();
			setFieldValue(value.toString());
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[TextField] ::Exception In setValue method "+e);

		}

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
				else{
					if(numericTextbox.getText().toString().trim().equals(""))
						return 0;
					else
						return Integer.parseInt(numericTextbox.getText());
				}
			}
		}
		return getFieldValue();

	}

	/**
	 * Overriden method from BaseField returns the list of errors.
	 */
	@Override
	public ArrayList<String> getErrors(String value) {
		ArrayList<String> errors = new ArrayList<String>();
		try {

			String fieldType = getTextFieldType();
			String errorText = null;


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
			}else if (fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_TXTBOX)) {
				if (value.equals("")) {
					if (!isAllowBlank()) {
						errorText = getBlankFieldText();
						errors.add(errorText);
					}
				}
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[TextField] ::Exception In getErrors method "+e);
		}
		return errors;
	}

	/**
	 * Overriden method from BaseField to set inline error.
	 */
	@Override
	public void setErrorInline () {
		try {
			getWidget().addStyleName(getErrorMsgCls());
			getWidget().addStyleName(getErrorIconCls());

			String errorMsg = "";
			if(!getErrors(getFieldValue()).isEmpty()){
				for(String error : getErrors(getFieldValue())) {
					errorMsg = errorMsg + error + ". ";
				}
				getWidget().setTitle(errorMsg);
			}else
				getWidget().setTitle(getInvalidMsg());

			if(getErrorIconBlobId()!=null)
				setCssPropertyToElement(getWidget(), getErrorIconBlobId());
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[TextField] ::Exception In setErrorInline method "+e);

		}
	}

	/**
	 * Overriden method from BaseField to set inline msg for valid field.
	 */
	@Override
	protected void setValidationMsgInline () {
		try {
			getWidget().addStyleName(getValidFieldMsgCls());
			getWidget().addStyleName(getValidFieldIconCls());

			if(getValidIconBlobId()!=null)
				setCssPropertyToElement(getWidget(), getValidIconBlobId());
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[TextField] ::Exception In setValidationMsgInline method "+e);

		}
	}

	/**
	 * Overriden method from BaseField to clear inline msg .
	 */
	public void clearInlineMsg () {
		try {
			getWidget().removeStyleName(getErrorMsgCls());
			getWidget().removeStyleName(getErrorIconCls());
			getWidget().removeStyleName(getValidFieldMsgCls());
			getWidget().removeStyleName(getValidFieldIconCls());
			getWidget().setTitle("");

			if(getWidget().getElement().getStyle().getProperty("background")!=null)
				getWidget().getElement().getStyle().clearProperty("background");
		} catch (Exception e) {

			logger.log(Level.SEVERE, "[TextField] ::Exception In clearInlineMsg method "+e);
		}
	}

	/**
	 * Overriden method from BaseField to set inline suggestion for field.
	 */
	@Override
	protected void setSuggestionInline () {
		try {

			if(getSuggestionText()!=null)
				getWidget().getElement().setPropertyString("placeholder", getSuggestionText());
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[TextField] ::Exception In setSuggestionInline method "+e);
		}
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
		try {

			String regexExp = TextFieldConstant.EMAIL_REGEX_EXP;
			if(getConfiguration()!=null){

				regexExp =  getConfiguration().getPropertyByName(TextFieldConstant.EMAIL_REGEX);
				if(regexExp !=null){
					return regexExp; 
				}else{
					return TextFieldConstant.EMAIL_REGEX;
				}
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[TextField] ::Exception In getEmailRegex method "+e);
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
		try {

			if (getConfigurationValue(TextFieldConstant.TF_VISLINES) != null) {
				noOfVisibleLines = (Integer) getConfigurationValue(TextFieldConstant.TF_VISLINES);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[TextField] ::Exception In getNoOfVisibleLines method "+e);
		}
		return noOfVisibleLines;
	}

	/**
	 * Method will return textfieldType whether its textbox/passwordbox/emailbox. Default value will be textbox.
	 * @return
	 */
	private String getTextFieldType(){
		String fieldType = TextFieldConstant.TFTYPE_TXTBOX;
		try {
			if (getConfigurationValue(TextFieldConstant.TF_TYPE) != null) {
				fieldType = (String) getConfigurationValue(TextFieldConstant.TF_TYPE);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[TextField] ::Exception In getTextFieldType method "+e);
		}
		return fieldType;
	}

	/**
	 * Method read the maxlength set in the configuration and return . Default value is 255;
	 * @return
	 */

	private Integer getFieldMaxLength(){

		Integer maxLength = 255;
		try {
			if (getConfigurationValue(TextFieldConstant.TF_MAXLENGTH) != null) {
				maxLength = (Integer) getConfigurationValue(TextFieldConstant.TF_MAXLENGTH);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[TextField] ::Exception In getFieldMaxLength method "+e);
		}
		return maxLength;
	}

	/**
	 * Method read the field minimum length set in the configuration and return . Default value is 6;
	 * @return
	 */

	private Integer getMinLength(){

		Integer minLength = 6;
		try {
			if (getConfigurationValue(TextFieldConstant.TF_MINLENGTH) != null) {
				minLength = (Integer) getConfigurationValue(TextFieldConstant.TF_MINLENGTH);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[TextField] ::Exception In getMinLength method "+e);
		}
		return minLength;
	}

	/**
	 * Returns if field should be validated or not.
	 * @return
	 */
	private Boolean isValidateField(){
		Boolean validate = false;
		try {
			if (getConfigurationValue(TextFieldConstant.VALIDATEFIELD) != null) {
				validate = (Boolean) getConfigurationValue(TextFieldConstant.VALIDATEFIELD);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[TextField] ::Exception In isValidateField method "+e);
		}
		return validate;
	}

	/**
	 * Method read the charWidth set in the configuration and return . Default value is 255;
	 * @return
	 */

	private Integer getFieldCharWidth(){

		Integer charWidth = 255;
		try {
			if (getConfigurationValue(TextFieldConstant.TF_CHARWIDTH) != null) {
				charWidth = (Integer) getConfigurationValue(TextFieldConstant.TF_CHARWIDTH);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[TextField] ::Exception In getFieldCharWidth method "+e);
		}
		return charWidth;
	}

	/**
	 * Method will show suggestion title based on the suggestion position. 
	 */
	@Override
	protected void setSuggestion() {

		try {
			if (getSuggestionPosition().equals(BaseFieldConstant.BF_SUGGESTION_INLINE)) {
				setSuggestionInline();
			} else {
				if(getSuggestionText()!=null){
					Label suggestionLbl = new Label(getSuggestionText());
					suggestionLbl.setStylePrimaryName(getSuggestionMsgCls());
					setErrorOrSuggestion(suggestionLbl, getSuggestionPosition());
				}
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[TextField] ::Exception In setSuggestion method "+e);
		}

	}

	/**
	 * Method read and return error text for 
	 * @return
	 */

	private String getMinLengthErrorText(){
		String minValueText = "The minimum length for this field is "+ getMinLength();
		try {
			if (getConfigurationValue(TextFieldConstant.MIN_LEGTH_ERROR_TEXT) != null) {
				minValueText = (String) getConfigurationValue(TextFieldConstant.MIN_LEGTH_ERROR_TEXT);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[TextField] ::Exception In getMinLengthErrorText method "+e);
		}
		return minValueText;
	}

	/**
	 * Method read and return error text for invalid email.
	 * @return
	 */
	private String getInvalidEmailText(){

		String invalidEmailText = "Invalid email";
		try {
			if (getConfigurationValue(TextFieldConstant.INVALID_EMAIL_TEXT) != null) {
				invalidEmailText = (String) getConfigurationValue(TextFieldConstant.INVALID_EMAIL_TEXT);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[TextField] ::Exception In getInvalidEmailText method "+e);
		}
		return invalidEmailText;
	}

	/**
	 * Method sets the focus to the field.
	 */
	public void setFocus(){

		try {
			String fieldType = getTextFieldType();
			if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_TXTBOX) || fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_EMAILBOX)  ) {
				textBox.setFocus(true);
			}else if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_PSWBOX))
				passwordTextBox.setFocus(true);
			else if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_NUMERIC))
				numericTextbox.setFocus(true);
			else
				textArea.setFocus(true);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[TextField] ::Exception In setFocus method "+e);
		}
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


	/************     Events in which field is interested ******************************/
	@Override
	public void onKeyUp(KeyUpEvent event) {

		try {
			Integer keycode= event.getNativeKeyCode();
			FieldEvent fieldEvent = new FieldEvent();
			fieldEvent.setEventSource(this);
			fieldEvent.setEventData(getValue());
			if(keycode.equals(KeyCodes.KEY_BACKSPACE) || keycode.equals(KeyCodes.KEY_DELETE)){
				if(isValidateField()){
					if(isValidateOnChange()){
						if(validate())
							setValue(getValue());
						setFocus();
					}
				}

				if(isDirty()){
					fieldEvent.setEventType(FieldEvent.EDITINPROGRESS);
				}
			}/*
			TODO : need validation focus goes to next tf and it become source
			else if(keycode.equals(KeyCodes.KEY_TAB)){
				fieldEvent.setEventType(FieldEvent.TAB_KEY_PRESSED);
			}*/

			AppUtils.EVENT_BUS.fireEvent(fieldEvent);

		} catch (Exception e) {
			logger.log(Level.SEVERE, "[TextField] ::Exception In onKeyUp method "+e);
		}

	}

	@Override
	public void onBlur(BlurEvent event) {
		try {
			if(isValidateField()){
				if(isValidateOnBlur()){
					if(validate()){
						if(numericTextbox!=null && numericTextbox.isAllowDecimal()){
							setValue(numericTextbox.fixPrecision());
						}
					}
				}
			}

			FieldEvent fieldEvent = new FieldEvent();
			fieldEvent.setEventSource(this);
			fieldEvent.setEventData(getValue());
			fieldEvent.setEventType(FieldEvent.EDITCOMPLETED);
			AppUtils.EVENT_BUS.fireEvent(fieldEvent);

		} catch (Exception e) {
			logger.log(Level.SEVERE, "[TextField] ::Exception In onBlur method "+e);
		}

	}

	@Override
	public void onKeyPress(KeyPressEvent event) {
		try {
			final int charCode= event.getUnicodeCharCode();

			fieldEvent = new FieldEvent();
			fieldEvent.setEventSource(this);
			fieldEvent.setEventData(getValue());

			/** Scheduler is added because when user press backspace ,tab and delete keyPressEvent is not fired. **/ 
			Scheduler.get().scheduleDeferred(new ScheduledCommand() {
				@Override
				public void execute() {
					if (isValidateField()) {
						if (isValidateOnChange()) {
							if (validate())
								setValue(getValue());
							setFocus();
						}
					}

					if(isDirty()){
						if (charCode == KeyCodes.KEY_ENTER)
							fieldEvent.setEventType(FieldEvent.ENTERED_HIT);
						else 
							fieldEvent.setEventType(FieldEvent.EDITINPROGRESS);

						AppUtils.EVENT_BUS.fireEvent(fieldEvent);
					}

				}
			});
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[TextField] ::Exception In onKeyPress method "+e);
		}

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
		public static final String EMAIL_REGEX = "emailRegex";

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

		/** Specifies whether field should be validated or not**/
		public static final String VALIDATEFIELD = "validateField";

	}


	@Override
	public void onKeyDown(KeyDownEvent event) {
		try{
			Integer keycode= event.getNativeKeyCode();

			if(keycode.equals(KeyCodes.KEY_TAB)){
				FieldEvent fieldEvent = new FieldEvent();
				fieldEvent.setEventSource(this);
				fieldEvent.setEventData(getValue());
				fieldEvent.setEventType(FieldEvent.TAB_KEY_PRESSED);

				AppUtils.EVENT_BUS.fireEvent(fieldEvent);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setFieldEnabled(boolean isEnable) {
		String fieldType = getTextFieldType();
		if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_TXTBOX) || fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_EMAILBOX) ) {
			textBox.setEnabled(isEnable);
		} else if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_PSWBOX)) {
			passwordTextBox.setEnabled(isEnable);
		} else if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_NUMERIC)){
			numericTextbox.setEnabled(isEnable);
		}else{
			textArea.setEnabled(isEnable);
		}
	}
	
	public void setSelectionRange(int startIndex, int endIndex) {
		if(getWidget() instanceof TextArea) {
			textArea.setSelectionRange(startIndex, endIndex);
		}
	}
}
package in.appops.client.common.fields;

import in.appops.client.common.config.field.BaseField;

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
	private Widget selectedWidget = null;
		
	public TextField(){
		
	}
	
	/**
	 * creates the field UI according the configuration set to it;
	 */
	public void create() {
		
		if(selectedWidget!=null)
			
			basePanel.add(selectedWidget,DockPanel.CENTER);
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
		textBox.setTabIndex(getTabIndex());
		
		textBox.addKeyPressHandler(this);
		
		if(isValidateOnBlur()){
			textBox.addBlurHandler(this);
		}
		if(isValidateOnChange()){
			textBox.addKeyUpHandler(this);
		}
		selectedWidget =textBox;
				
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
					
		selectedWidget =textArea;
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
		passwordTextBox.setTabIndex(getTabIndex());
		
		/*** Events fired by passwordTextBox ****/
		
		passwordTextBox.addKeyPressHandler(this);
		
		if(isValidateOnBlur()){
			passwordTextBox.addBlurHandler(this);
		}
		if(isValidateOnChange()){
			passwordTextBox.addKeyUpHandler(this);
		}
		selectedWidget =passwordTextBox;
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
		numericTextbox.setTabIndex(getTabIndex());
		
		/*** Events fired by passwordTextBox ****/
		
		numericTextbox.addKeyPressHandler(numericTextbox);
				
		if(isValidateOnChange()){
			numericTextbox.addKeyUpHandler(this);
		}
		
		numericTextbox.addBlurHandler(this);
		
		selectedWidget = numericTextbox;
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
	 * Overriden method from BaseField returns the list of errors.
	 */
	@Override
	public ArrayList<String> getErrors(Object value) {
		
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
	protected void setErrorInline () {
		getWidget().setStylePrimaryName(getErrorMsgCls());
		getWidget().addStyleName(getErrorIconCls());
		
		if(getErrorIconBlobId()!=null)
			setCssPropertyToElement(getWidget(), getErrorIconBlobId());
	}
	
	/**
	 * Overriden method from BaseField to set inline msg for valid field.
	 */
	@Override
	protected void setValidationMsgInline () {
		getWidget().setStylePrimaryName(getValidFieldMsgCls());
		getWidget().addStyleName(getValidFieldIconCls());
		
		if(getValidIconBlobId()!=null)
			setCssPropertyToElement(getWidget(), getValidIconBlobId());
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
		
		if(getConfiguration()!=null){
			
			Integer noOfVisibleLines =  (Integer) getConfiguration().getPropertyByName(TextFieldConstant.TF_VISLINES);
			if(noOfVisibleLines !=null){
				return noOfVisibleLines; 
			}else{
				return 1;
			}
		}
		return null;
	}
	
	/**
	 * Method will return textfieldType whether its textbox/passwordbox/emailbox. Default value will be textbox.
	 * @return
	 */
	private String getTextFieldType(){
		
		if(getConfiguration()!=null){
			
			String fieldType = getConfiguration().getPropertyByName(TextFieldConstant.TF_TYPE);
			if(fieldType !=null){
				return fieldType;
			}else{
				return TextFieldConstant.TFTYPE_TXTBOX;
			}
		}
		return null;
		
	}
	
	/**
	 * Method read the maxlength set in the configuration and return . Default value is 255;
	 * @return
	 */
	
	private Integer getFieldMaxLength(){
		
		if(getConfiguration()!=null){
			Integer maxLength = (Integer) getConfiguration().getPropertyByName(TextFieldConstant.TF_MAXLENGTH);
			if(maxLength !=null){
				return maxLength;
			}else{
				return 255;
			}
		}
		return null;
	}
	
	/**
	 * Method read the field minimum length set in the configuration and return . Default value is 6;
	 * @return
	 */
	
	private Integer getMinLength(){
		
		if(getConfiguration()!=null){
			
			Integer minLength = (Integer) getConfiguration().getPropertyByName(TextFieldConstant.TF_MINLENGTH);
			if(minLength !=null){
				return minLength;
			}else{
				return 6;
			}
		}
		return null;
	}
	
	/**
	 * Method read the charWidth set in the configuration and return . Default value is 255;
	 * @return
	 */
	
	private Integer getFieldCharWidth(){
		
		if(getConfiguration()!=null){
			
			Integer charWidth = (Integer) getConfiguration().getPropertyByName(TextFieldConstant.TF_CHARWIDTH);
			if(charWidth !=null){
				return charWidth;
			}else{
				return 255;
			}
		}
		return null;
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
		
		if(getConfiguration()!=null){
			String minValueText = getConfiguration().getPropertyByName(TextFieldConstant.MIN_LEGTH_ERROR_TEXT);
			if(minValueText !=null){
				return minValueText;
			}else{
				return "The minimum length for this field is "+ getMinLength();
			}
		}
		return null;
	}
	
	/**
	 * Method read and return error text for invalid email.
	 * @return
	 */
	private String getInvalidEmailText(){
		
		if(getConfiguration()!=null){
			String maxValueText = getConfiguration().getPropertyByName(TextFieldConstant.INVALID_EMAIL_TEXT);
			if(maxValueText !=null){
				return maxValueText;
			}else{
				return "Invalid email";
			}
		}
		return null;
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
	
	/************     Events in which field is interested ******************************/
	@Override
	public void onKeyUp(KeyUpEvent event) {
		
		Integer keycode= event.getNativeKeyCode();
		if(keycode.equals(KeyCodes.KEY_BACKSPACE) || keycode.equals(KeyCodes.KEY_TAB)|| keycode.equals(KeyCodes.KEY_DELETE)){
			validate();
			 setFocus();
		}
				
	}
	
	@Override
	public void onBlur(BlurEvent event) {
		
		 setValue(getValue());
		 if(validate()){
			 if(numericTextbox!=null && numericTextbox.isAllowDecimal()){
				 setFieldValue(numericTextbox.fixPrecision());
			 }
		 }
			 
	}

	@Override
	public void onKeyPress(KeyPressEvent event) {
		
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {    
			  @Override
			  public void execute() {
				  
				  if(isValidateOnChange()){
					  setValue(getValue());
					  validate();
					  setFocus();
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
		
		
	}
}
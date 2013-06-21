package in.appops.client.common.fields;

import in.appops.client.common.event.FieldEvent;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
Field class to represent a TextBox, TextArea or PasswordTextBox

<h3>CSS Style Rules</h3>
<ul class='css'>
<li>.appops-TextField { primary style }</li>
<li>.appops-Inline-Error-TextBox { style to show inline error icon }</li>
<li>.appops-Validation-Correct-TextBox { style set when entered value in field is correct }</li>
<li>.appops-Error-Text { style to show error text message }</li>
<li>.appops-suggestionText { style to show suggestion text }</li>
<li>.appops-Error-TextBox { style to show error with red border }</li>
</ul>

<p>
<h3>Configuration</h3>
<a href="TextFieldConstant.html">Available configurations</a>
</p>

<p>
<h3>Example</h3>

<p>Following code results in a emailbox with inline suggestion with text "Enter email" ,If data entered in a field is not valid then it will show iconic error style reprentation.  </p>

TextField emailTextField = new TextField();<br>
Configuration conf = new Configuration();
conf.setPropertyByName(TextFieldConstant.TF_VISIBLELINES, 1);
conf.setPropertyByName(TextFieldConstant.TF_READONLY, false);
conf.setPropertyByName(TextFieldConstant.TF_TYPE, TFTYPE_EMAILBOX);
conf.setPropertyByName(TextFieldConstant.TF_PRIMARYCSS, "appops-TextField");
conf.setPropertyByName(TextFieldConstant.TF_DEPENDENTCSS, null);
conf.setPropertyByName(TextFieldConstant.TEXTFIELD_SUGGESTION_STYLE, TextFieldConstant.INLINE_SUGGESTION);
conf.setPropertyByName(TextFieldConstant.TF_SUGGESTION_TEXT, "Enter email");
conf.setPropertyByName(TextFieldConstant.VALIDATION_STYLE, TextFieldConstant.ICONIC_STYLE);
conf.setPropertyByName(TextFieldConstant.ICONIC_STYLE, TextFieldConstant.ICON_WITH_ERROR_MSG);
conf.setPropertyByName(TextFieldConstant.VALIDATION_MSG_POSITION, TextFieldConstant.SIDE);
conf.setPropertyByName(TextFieldConstant.TF_ERROR_TEXT, "Data entered is not valid..");
emailTextField.setConfiguration(conf);<br>
emailTextField.configure();<br>
emailTextField.create();
</p>*/

public class TextField extends Composite implements Field, BlurHandler, KeyUpHandler,KeyPressHandler{

	private Configuration configuration;
	private String fieldValue;
	private TextBox textBox;
	private PasswordTextBox passwordTextBox;
	private TextArea textArea;
	private DockPanel basepanel ;
	private Widget selectedWidget = null;
	private HorizontalPanel topWidget = null;
	private HorizontalPanel sideWidget = null;
	private HorizontalPanel bottomWidget = null;
	
	public static final String ERROR_PANEL_CSS = "errorPlaceHolder";
	public static final String INLINE_ERROR_CSS = "appops-Inline-Error-TextBox";
	public static final String VALIDATIONCORRECT_CSS = "appops-Validation_Correct-TextBox";
	public static final String VALIDATION_TEXT_CSS = "appops-Validation-Text";
	public static final String ERROR_TEXT_CSS = "appops-Error_Text";
	public static final String SUGGESTION_TEXT_CSS = "appops-suggestionText";
	public static final String ERROR_ICON = "images/validation_error_icon.jpg";
	public static final String VALIATION_ICON = "images/validation_icon.png";
	
	
	public TextField(){
		
	}
	
	/**
	 * creates the field UI according the configuration set to it;
	 * @throws AppOpsException 
	 */
	public void create() throws AppOpsException {
		if(getConfiguration() == null)
			throw new AppOpsException("TextField configuration unavailable");
		
		if(basepanel==null)
			basepanel = new DockPanel();
		
		if(selectedWidget!=null)
			basepanel.add(selectedWidget,DockPanel.CENTER);
		
		initWidget(basepanel);
	}
	
	
	/**
	 * Method read the configuration and set it to field .
	 */
	@Override
	public void configure() {
		
		String fieldType = getTextFieldType();
		
		if (fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_TXTBOX)	|| fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_NUMERIC)
				|| fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_EMAILBOX)) {
		
			createTextBox();

		} else if (fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_PSWBOX)) {
			createPasswordBox();

		} else if (fieldType.equalsIgnoreCase(TextFieldConstant.TFTTYPE_TXTAREA)) {
			createTextArea();
		}
		
	}
	
	private void createTextBox(){
		
		textBox = new TextBox();
		textBox.setReadOnly(isFieldReadOnly());
		if(getPrimaryCss() != null)
			textBox.setStylePrimaryName(getPrimaryCss());
		if(getDependentCss() != null)
			textBox.addStyleName(getDependentCss());
		
		textBox.setMaxLength(getFieldMaxLength());
		textBox.setTabIndex(getTabIndex());
		showSuggestionText(textBox);
		
		selectedWidget =textBox;
		
		/*** Events fired by textbox ****/
		
		textBox.addKeyPressHandler(this);
		
		if(getValidationEvent().equals(TextFieldConstant.VALIDATE_ON_BLUR)){
			textBox.addBlurHandler(this);
		}else{
			textBox.addKeyUpHandler(this);
		}
	}
	
	private void createPasswordBox(){
		passwordTextBox = new PasswordTextBox();
		passwordTextBox.setReadOnly(isFieldReadOnly());
		if(getPrimaryCss() != null)
			passwordTextBox.setStylePrimaryName(getPrimaryCss());
		if(getDependentCss() != null)
			passwordTextBox.addStyleName(getDependentCss());
		
		passwordTextBox.setMaxLength(getFieldMaxLength());
		passwordTextBox.setTabIndex(getTabIndex());
		showSuggestionText(passwordTextBox);
		
		/*** Events fired by passwordTextBox ****/
		
		passwordTextBox.addKeyPressHandler(this);
		
		if(getValidationEvent().equals(TextFieldConstant.VALIDATE_ON_BLUR)){
			passwordTextBox.addBlurHandler(this);
		}else{
			passwordTextBox.addKeyUpHandler(this);
		}
		selectedWidget =passwordTextBox;
	}
	
	private void createTextArea(){
		
		textArea = new TextArea();
		
		Integer visibleLines = getNoOfVisibleLines();
		textArea.setVisibleLines(visibleLines);
		textArea.setReadOnly(isFieldReadOnly());
		
		if(getPrimaryCss() != null)
			textArea.setStylePrimaryName(getPrimaryCss());
		if(getDependentCss()!= null)
			textArea.addStyleName(getDependentCss());
		if(getFieldCharWidth()!=null)
			textArea.setCharacterWidth(getFieldCharWidth());
		
		showSuggestionText(textArea);
		
		/*** Events fired by textArea ****/
		
		textArea.addKeyPressHandler(this);
		
		if(getValidationEvent().equals(TextFieldConstant.VALIDATE_ON_BLUR)){
			textArea.addBlurHandler(this);
		}else{
			textArea.addKeyUpHandler(this);
		}
		
		selectedWidget =textArea;
	}
	
	/**
	 * resets the field to the original value that has been set via setFieldValue()
	 */
	@Override
	public void reset() {
		
		String fieldType = getTextFieldType();
		if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_TXTBOX))
			textBox.setText(getFieldValue());
		else if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_PSWBOX))
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
		
		String fieldType = getTextFieldType();
		
		if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_TXTBOX))
			textBox.setText(fieldValue);
		else if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_PSWBOX))
			passwordTextBox.setText(fieldValue);
		else
			textArea.setText(fieldValue);
		
		return this.fieldValue;
	}

	@Override
	public void setFieldValue(String fieldValue) {
		
		this.fieldValue = fieldValue;
		
		String fieldType = getTextFieldType();
		
		if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_TXTBOX))
			textBox.setText(fieldValue);
		else if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_PSWBOX))
			passwordTextBox.setText(fieldValue);
		else
			textArea.setText(fieldValue);
	
	}

	/**
	 * clears the field if it has any values
	 */
	@Override
	public void clear() {
		String fieldType = getTextFieldType();
		
		if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_TXTBOX) || fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_NUMERIC) || fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_EMAILBOX))
			textBox.setText("");
		else if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_PSWBOX))
			passwordTextBox.setText("");
		else
			textArea.setText("");
	}
	
	private void setSuggestionText(Widget widget, String suggestionText){
		
		widget.getElement().setPropertyString("placeholder", suggestionText);
	}

	/**
	 * Method validate the field. and set validation message or error title according to it.
	 */
	private void validateField() {

		String fieldType = getTextFieldType();
		
		removeErrorText();

		if (fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_EMAILBOX)) {
			validateEmail();
		} else if (fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_PSWBOX)) {
			validatePassword();
		} else if (fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_NUMERIC)) {
			if (textBox.getText().equals("")) {
				if (!isFieldAllowBlank()) {
					showError(getBlankFieldText());
				}
			} else {
				validateNumber();
			}
		}
	}
	
	private void validateEmail() {
		if (textBox.getText().equals("")) {
			if (!isFieldAllowBlank()) {
				showError(getBlankFieldText());
			}
		}else {
			if (textBox.getText().matches(getEmailRegex())) {
				showValidFieldMessage();
			} else {
				showError(getInvalidEmailText());
			}
		}
	}
	
	private void validatePassword(){
		
		if (passwordTextBox.getText().equals("")) {
			if (!isFieldAllowBlank()) {
				showError(getBlankFieldText());
			}
		} else {
			if (passwordTextBox.getText().length() < getMinLength())
				showError(getMinLengthErrorText());
			else
				showValidFieldMessage();
		}
	}
	
	private void validateNumber(){
		
		String numericFieldType = getNumFieldType();
		Integer maxValue = getMaxValue();
				
		if (numericFieldType.equals(TextFieldConstant.NUMFIELD_DEC)) {
			
			if(textBox.getText().matches(getDecimalRegex())){
				Double fieldvalue = Double.parseDouble(textBox.getText());
				
				if(fieldvalue < getMinValue()){
					showError(getMinValueErrorText());
				}else if(maxValue!=null && fieldvalue > maxValue){
					showError(getMaxValueErrorTxt());
				}
				Boolean allowNegative = isFieldAllowNegative();
				if(!allowNegative && fieldvalue < 0 ){
					showError(getNegativeValTxt());
				}
			}else{
				showError(getErrorText());
			}
		} else if (numericFieldType.equals(TextFieldConstant.NUMFIELD_INT)) {
			
			if(textBox.getText().matches(getIntegerRegexExp())){
				Integer fieldvalue = Integer.parseInt(textBox.getText());
				
				if(fieldvalue<getMinValue()){
					showError(getMinValueErrorText());
				}else if(maxValue!=null && fieldvalue>maxValue){
					showError(getMaxValueErrorTxt());
				}
				
				Boolean allowNegative = isFieldAllowNegative();
				if(!allowNegative && fieldvalue < 0 ){
					showError(getNegativeValTxt());
				}
			}else{
				showError(getErrorText());
			}
		}
		
	}
	
		
	/**
	 * Method read regex expression from configuration. if it is not in the configuration then it returns DEC_REGEX_EXP;
	 * @return default regex expression.
	 */
	private String getDecimalRegex(){
		if(getConfiguration()!=null){
			
			String regexExp =  getConfiguration().getPropertyByName(TextFieldConstant.DEC_REGEX_EXP);
			Integer precision = getNoOfDecimalPrecision();
			if(regexExp !=null){
				return regexExp; 
			}else{
				return TextFieldConstant.DEC_REGEX_EXP.replaceAll("precision", precision.toString()); 
			}
		}
		return null;
		
	}
	
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
	 * Method read regex expression from configuration. if it is not in the configuration then it returns INT_REGEX_EXP;
	 * @return default regex expression.
	 */
	private String getIntegerRegexExp(){
		
		if(getConfiguration()!=null){
			
			String regexExp =  getConfiguration().getPropertyByName(TextFieldConstant.INT_REGEX_EXP);
			if(regexExp !=null){
				return regexExp; 
			}else{
				return TextFieldConstant.INT_REGEX_EXP;
			}
		}
		return null;
	}
	
	
	private void removeErrorText(){
		if (getConfiguration() != null) {
			String errorStyle = getErrorStyle();
			if (errorStyle != null) {

				if (errorStyle.equals(TextFieldConstant.ICONIC_STYLE)) {
					String iconicType = getIconicStyle();
					if(iconicType.equals(TextFieldConstant.ICONICSTYLE_INLINE)){
						getWidget().removeStyleName(INLINE_ERROR_CSS);
						getWidget().removeStyleName(VALIDATIONCORRECT_CSS);
						getWidget().getElement().getStyle().clearProperty("background");
					}else if(iconicType.equals(TextFieldConstant.ICONICSTYLE_ICON_WITH_ERROR_MSG)){
						
						String msgPosition = getErrorOrValidationMsgPosition();
						clearErrorOrValidationMsgText(msgPosition);
					}
				}else if (errorStyle.equals(TextFieldConstant.ONLY_MSG)) {
					String msgPosition = getErrorOrValidationMsgPosition();
					clearErrorOrValidationMsgText(msgPosition);
				}
			}
		}
	}

	@Override
	public void onFieldEvent(FieldEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	public Widget getWidget() {
		
		String fieldType = getTextFieldType();
		if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_TXTBOX) || fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_EMAILBOX) 
				|| fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_NUMERIC)) {
			return textBox;
		} else if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_PSWBOX)) {
			return passwordTextBox;
		} else {
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
			
			Integer noOfVisibleLines =  (Integer) getConfiguration().getPropertyByName(TextFieldConstant.TF_VISIBLELINES);
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
	 * Method will return the primary css applied to field.
	 * @return
	 */
	private String getPrimaryCss(){
		
		if(getConfiguration()!=null){
			
			String primaryCss = getConfiguration().getPropertyByName(TextFieldConstant.TF_PRIMARYCSS);
			if(primaryCss !=null){
				return primaryCss;
			}else{
				return null;
			}
		}
		return null;
	}
	
	/**
	 * Method will return the dependent css applied to field.
	 * @return
	 */
	private String getDependentCss(){
		
		if(getConfiguration()!=null){
			
			String dependentCss = getConfiguration().getPropertyByName(TextFieldConstant.TF_DEPENDENTCSS);
			if(dependentCss !=null){
				return dependentCss;
			}else{
				return null;
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
	 * Method return whether field is readonly or not .Bydefault it will return false;
	 * @return
	 */
	
	private Boolean isFieldReadOnly(){
		
		if(getConfiguration()!=null){
			
			Boolean isReadOnly = (Boolean) getConfiguration().getPropertyByName(TextFieldConstant.TF_READONLY);
			if(isReadOnly !=null){
				return isReadOnly;
			}else{
				return false;
			}
		}
		return null;
	}
	
	
	/**
	 * Method will show suggestion title based on the suggestion style i.e inline/top/bottom which is set in the configuration. 
	 */
	private void showSuggestionText(Widget widget){
		
		if(getConfiguration()!=null){
			
			String suggestionStyle =getSuggestionStyle(); 
			
			if(suggestionStyle !=null){
				
				String suggestionText = getSuggestionText();
				if (suggestionText != null) {
					if (suggestionStyle.equals(TextFieldConstant.SUGGESTIONSTYLE_INLINE)) {
							setSuggestionText(getWidget(), suggestionText);
					}else{
						Label suggestionLabel = new Label(suggestionText);
						suggestionLabel.setStylePrimaryName(getSuggestionTextCss());
						addWidgetToPosition(suggestionLabel,suggestionStyle);
					}
				}
			}
		}
		
	}
	
	/**
	 * Method return the validation icon blobId set in the configuration. o.w return default validation blobId.
	 * @return
	 */
	
	private String getValidationIconBlobId(){
		
		if(getConfiguration()!=null){
			
			String validationIconBlobId = getConfiguration().getPropertyByName(TextFieldConstant.VALIDATION_ICON_BLOBID);
			if(validationIconBlobId !=null){
				return validationIconBlobId;
			}else{
				return VALIATION_ICON;
			}
		}
		return null;
	}
	
	/**
	 * Method return the error icon blobId set in the configuration. o.w return default error blobId.
	 * @return
	 */
	
	private String getErrorIconBlobId(){
		
		if(getConfiguration()!=null){
			
			String errorIconBlobId = getConfiguration().getPropertyByName(TextFieldConstant.VALIDATION_ICON_BLOBID);
			if(errorIconBlobId !=null){
				return errorIconBlobId;
			}else{
				return ERROR_ICON;
			}
		}
		return null;
	}
	
	/**
	 * Method return the suggestion title css set in the configuration. o.w return default error blobId.
	 * @return
	 */
	private String getSuggestionTextCss() {
		if (getConfiguration() != null) {

			String suggestionTitleCss = getConfiguration().getPropertyByName(TextFieldConstant.SUGGESTION_TEXT_CSS);
			if (suggestionTitleCss != null) {
				return suggestionTitleCss;
			} else {
				return SUGGESTION_TEXT_CSS;
			}
		}
		return null;
	}
	
	/**
	 * Method will return the suggestion which is set in configuration o.w return default style i.e suggestion on top. 
	 */
	private String getSuggestionStyle(){
		
		if(getConfiguration()!=null){
			
			String suggestionStyle = getConfiguration().getPropertyByName(TextFieldConstant.TF_SUGGESTION_STYLE);
			if(suggestionStyle !=null){
				return suggestionStyle;
			}
		}
		return TextFieldConstant.SUGGESTION_ON_TOP;
		
	}
	
	/**
	 * Method will return suggestion title.
	 * @return
	 */
	private String getSuggestionText() {

		if (getConfiguration() != null) {

			String suggestionText = getConfiguration().getPropertyByName(TextFieldConstant.TF_SUGGESTION_TEXT);
			if (suggestionText != null) {
				return suggestionText;
			}
		}
		return null;
	}
	
	/**
	 * Method will show error title based on the error style i.e iconic/title set in the configuration. 
	 */
	
	private HorizontalPanel getIconWithMsg(String blobId,String displayText){
		
		HorizontalPanel panel = new HorizontalPanel();
		
		Configuration conf = new Configuration();
		conf.setPropertyByName(ImageField.IMAGEFIELD_BLOBID, blobId);
		
		ImageField imageField = new ImageField();
		imageField.setConfiguration(conf);
		
		panel.add(imageField);
		if(displayText!=null){
			Label lbl = new Label(displayText);
			lbl.setStylePrimaryName(ERROR_TEXT_CSS);
			panel.add(lbl);
		}
		
		try {
			imageField.create();
		} catch (AppOpsException e) {
			
		}
		return panel;
		
	}
	
	/** 
	 * Method shows the validation message after field is validated correctly.
	 */
	
	private void showValidFieldMessage() {

			String errorStyle = getErrorStyle();
			if (errorStyle != null) {

				if (errorStyle.equals(TextFieldConstant.ICONIC_STYLE)) {
					
					String iconicType = getIconicStyle();
					if(iconicType.equals(TextFieldConstant.ICONICSTYLE_INLINE)){
						
						getWidget().removeStyleName(INLINE_ERROR_CSS);
						getWidget().addStyleName(VALIDATIONCORRECT_CSS);
						getWidget().getElement().getStyle().setProperty("background", "white url("+ getValidationIconBlobId()+") no-repeat right	center");
					    getWidget().setTitle("");
					}else if(iconicType.equals(TextFieldConstant.ICONICSTYLE_ICON_WITH_ERROR_MSG)){
						
						String msgPosition = getErrorOrValidationMsgPosition();
						addWidgetToPosition(getIconWithMsg(getValidationIconBlobId(),null), msgPosition);
					}
					
				}  else if (errorStyle.equals(TextFieldConstant.ONLY_MSG)) {
					
					String msgPosition = getErrorOrValidationMsgPosition();
					
					Label correctTextLbl = new Label(getValidValueText());
					correctTextLbl.setStylePrimaryName(VALIDATION_TEXT_CSS);
					
					addWidgetToPosition(correctTextLbl, msgPosition);
				}
			}
	}
	
	/** 
	 * Method return error text from configuration o.w return default error text i.e "Invalid field value";
	 */

	private String getErrorText(){
		
		if(getConfiguration()!=null){
			String errorText = getConfiguration().getPropertyByName(TextFieldConstant.TF_ERROR_TEXT);
			if(errorText !=null){
				
				return errorText;
			}else{
				return "Invalid field value";
			}
		}
		return null;
	}
	
	private String getValidValueText(){
		
		if(getConfiguration()!=null){
			String validValueText = getConfiguration().getPropertyByName(TextFieldConstant.TF_VALIDVALUE_TEXT);
			if(validValueText !=null){
				
				return validValueText;
			}else{
				return "OK";
			}
		}
		return null;
	}
	
	private String getIconicStyle(){
		
		if(getConfiguration()!=null){
			String iconicType = getConfiguration().getPropertyByName(TextFieldConstant.ICONIC_STYLE);
			if(iconicType !=null){
				
				return iconicType;
			}else{
				return TextFieldConstant.ICONICSTYLE_INLINE;
			}
		}
		return null;
	}
	
	private String getErrorStyle(){
		
		if(getConfiguration()!=null){
			String validationStyle = getConfiguration().getPropertyByName(TextFieldConstant.ERROR_STYLE);
			if(validationStyle !=null){
				
				return validationStyle;
			}else{
				return TextFieldConstant.ICONIC_STYLE;
			}
		}
		return null;
	}
	
	/** 
	 * Method set the error according to the error style i.e if its TextFieldConstant.INLINE_ICONIC_ERROR_STYLE or TextFieldConstant.OUTLINE_ICONIC_ERROR_STYLE then field will show error in the form of error icon. if 
	 * its   TextFieldConstant.ERROR_TITLE_STYLE then according to the position it will set the error title.
	 */
	private void showError(String errorText) {

		if (getConfiguration() != null) {

			String errorStyle = getErrorStyle();

			if (errorStyle != null) {
				if (errorStyle.equals(TextFieldConstant.ICONIC_STYLE)) {
					String iconicType = getIconicStyle();
					if(iconicType.equals(TextFieldConstant.ICONICSTYLE_INLINE)){
						if (getErrorIconBlobId() != null) {
							
							getWidget().addStyleName(INLINE_ERROR_CSS);
							getWidget().getElement().getStyle().setProperty("background","white url(" + getErrorIconBlobId()+ ") no-repeat right center");
						}
						getWidget().setTitle(errorText);
					}else if(iconicType.equals(TextFieldConstant.ICONICSTYLE_ICON_WITH_ERROR_MSG)){
						
						String msgPosition = getErrorOrValidationMsgPosition();
						addWidgetToPosition(getIconWithMsg(getErrorIconBlobId(),errorText), msgPosition);
					}
				} else if (errorStyle.equals(TextFieldConstant.ONLY_MSG)) {
					
					String msgPosition = getErrorOrValidationMsgPosition();
					Label errorLabel = new Label(errorText);
					errorLabel.setStylePrimaryName(ERROR_TEXT_CSS);
					addWidgetToPosition(errorLabel, msgPosition);
					
				}
			}
		}

	}
	
	private String getValidationEvent() {
		
		if(getConfiguration()!=null){
			String validationStyle = getConfiguration().getPropertyByName(TextFieldConstant.VALIDATION_EVENT);
			if(validationStyle !=null){
				return validationStyle;
			}else{
				return TextFieldConstant.VALIDATE_ON_CHANGE;
			}
		}
		return null;
	}
	
	private Boolean isFieldAllowBlank() {
		
		if(getConfiguration()!=null){
			Boolean allowBlank = getConfiguration().getPropertyByName(TextFieldConstant.TF_ALLOW_BLANK);
			if(allowBlank !=null){
				return allowBlank;
			}else{
				return true;
			}
		}
		return null;
	}
	
	private Integer getTabIndex() {
		
		if(getConfiguration()!=null){
			Integer tabIndex = getConfiguration().getPropertyByName(TextFieldConstant.TF_TABINDEX);
			if(tabIndex !=null){
				return tabIndex;
			}else{
				return 1;
			}
		}
		return null;
	}
	
	private String getErrorOrValidationMsgPosition() {
		
		if(getConfiguration()!=null){
			String msgPosition = getConfiguration().getPropertyByName(TextFieldConstant.ERROR_MSG_POSITION);
			if(msgPosition !=null){
				return msgPosition;
			}else{
				return TextFieldConstant.SIDE;
			}
		}
		return null;
	}
	
	private String getNumFieldType(){
		
		if(getConfiguration()!=null){
			String numFieldType = getConfiguration().getPropertyByName(TextFieldConstant.NUMFIELD_TYPE);
			if(numFieldType !=null){
				return numFieldType;
			}else{
				return TextFieldConstant.NUMFIELD_INT;
			}
		}
		return null;
	}
	
	private Integer getMinValue(){
		
		if(getConfiguration()!=null){
			Integer numFieldType = getConfiguration().getPropertyByName(TextFieldConstant.TF_MINVALUE);
			if(numFieldType !=null){
				return numFieldType;
			}else{
				return 0;
			}
		}
		return null;
	}
	
	private Integer getMaxValue(){
		
		if(getConfiguration()!=null){
			Integer numFieldType = getConfiguration().getPropertyByName(TextFieldConstant.TF_MAXVALUE);
			if(numFieldType !=null){
				return numFieldType;
			}else{
				return null;
			}
		}
		return null;
	}
	
	private Boolean isFieldAllowNegative(){
		
		if(getConfiguration()!=null){
			Boolean isNegative = getConfiguration().getPropertyByName(TextFieldConstant.NUMFIELD_NEGATIVE);
			if(isNegative !=null){
				return isNegative;
			}else{
				return false;
			}
		}
		return null;
	}
	
	private void addWidgetToPosition(Widget widget,String position){
		
		if(basepanel==null)
			basepanel = new DockPanel();
		
		if(position.equals(TextFieldConstant.UNDER) || position.equals(TextFieldConstant.SUGGESTION_IN_BOTTOM)){
			if(bottomWidget ==null){
				bottomWidget = new HorizontalPanel();
				basepanel.add(bottomWidget,DockPanel.SOUTH);
			}
			
			bottomWidget.clear();
			bottomWidget.add(widget);
		}else if(position.equals(TextFieldConstant.TOP) ||  position.equals(TextFieldConstant.SUGGESTION_ON_TOP)){
			if(topWidget ==null){
				topWidget = new HorizontalPanel();
				basepanel.add(topWidget,DockPanel.NORTH);
			}
			
			topWidget.clear();
			topWidget.add(widget);
		}else if(position.equals(TextFieldConstant.SIDE)){
			if(sideWidget ==null){
				sideWidget = new HorizontalPanel();
				sideWidget.setStylePrimaryName(ERROR_PANEL_CSS);
				basepanel.add(sideWidget,DockPanel.EAST);
			}
			
			sideWidget.clear();
			sideWidget.add(widget);
		}
	}
	
	private void clearErrorOrValidationMsgText(String position){
		
		if(position.equals(TextFieldConstant.UNDER)){
			if(bottomWidget!=null)
				bottomWidget.clear();
		}else if(position.equals(TextFieldConstant.TOP)){
			if(topWidget!=null)
				topWidget.clear();
		}else if(position.equals(TextFieldConstant.SIDE)){
			if(sideWidget!=null)
				sideWidget.clear();
		}
		
	}
	
	private String getMaxValueErrorTxt(){
		
		if(getConfiguration()!=null){
			String maxValueText = getConfiguration().getPropertyByName(TextFieldConstant.MAX_VALUE_TEXT);
			if(maxValueText !=null){
				return maxValueText;
			}else{
				return "The maximum value for this field is "+ getMaxValue();
			}
		}
		return null;
	}
	
	private String getMinValueErrorText(){
		
		if(getConfiguration()!=null){
			String maxValueText = getConfiguration().getPropertyByName(TextFieldConstant.MIN_VALUE_TEXT);
			if(maxValueText !=null){
				return maxValueText;
			}else{
				return "The minimum value for this field is "+ getMinValue();
			}
		}
		return null;
	}
	
	private String getMinLengthErrorText(){
		
		if(getConfiguration()!=null){
			String maxValueText = getConfiguration().getPropertyByName(TextFieldConstant.TF_MINLENGTH);
			if(maxValueText !=null){
				return maxValueText;
			}else{
				return "The minimum length for this field is "+ getMinLength();
			}
		}
		return null;
	}
	
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
	
	private String getBlankFieldText(){
				
		if(getConfiguration()!=null){
			String invalidFieldText = getConfiguration().getPropertyByName(TextFieldConstant.BLANK_FIELD_TXT);
			if(invalidFieldText !=null){
				return invalidFieldText;
			}else{
				return "Field is required";
			}
		}
		return null;
	}
	
	private String getNegativeValTxt(){
		
		
		if(getConfiguration()!=null){
			String negativeValueText = getConfiguration().getPropertyByName(TextFieldConstant.NEGATIVE_VALUE_TEXT);
			if(negativeValueText !=null){
				return negativeValueText;
			}else{
				return "Field value cannot be -ve";
			}
		}
		return null;
	}
	
	private Integer getNoOfDecimalPrecision(){
		
		
		if(getConfiguration()!=null){
			Integer decimalPrecision = getConfiguration().getPropertyByName(TextFieldConstant.DEC_PRECISION);
			if(decimalPrecision !=null){
				return decimalPrecision;
			}else{
				return 2;
			}
		}
		return null;
	}
	

	public interface TextFieldConstant{
		
		public static final String TF_VISIBLELINES = "visibleLines";
		public static final String TF_READONLY = "readOnly";
		public static final String TF_ALLOW_BLANK = "allowBlank";
		public static final String TF_TABINDEX = "tabIndex";
		public static final String TF_PRIMARYCSS = "primaryCss";
		public static final String TF_DEPENDENTCSS = "dependentCss";
		public static final String TF_MAXLENGTH = "maxlength";
		public static final String TF_MINLENGTH = "minlength";
		public static final String TF_CHARWIDTH = "charWidth";
		public static final String TF_TYPE = "fieldType";
		
		public static final String TFTYPE_TXTBOX = "txtbox";
		public static final String TFTYPE_PSWBOX = "passowrdTxtbox";
		public static final String TFTTYPE_TXTAREA = "txtarea";
		public static final String TFTYPE_EMAILBOX = "emailbox";
		public static final String TFTYPE_NUMERIC = "numeric";
		
		public static final String ERROR_STYLE = "errorStyle";
		public static final String ICONIC_STYLE = "iconic";
		public static final String ONLY_MSG = "onlyMsg";
		
		public static final String ICONICSTYLE_INLINE = "inlineIcon";
		public static final String ICONICSTYLE_ICON_WITH_ERROR_MSG = "iconWithErrorMsg";
		
		public static final String ERROR_MSG_POSITION = "errorMsgPosition";
		public static final String UNDER = "under";
		public static final String TOP = "top";
		public static final String SIDE = "side";
		
		public static final String TF_ERROR_TEXT = "errorTxt";
		public static final String TF_VALIDVALUE_TEXT = "validValueTxt";
		public static final String BLANK_FIELD_TXT = "blankFieldTxt";
		public static final String NEGATIVE_VALUE_TEXT = "negativeFieldTxt";
		public static final String INVALID_EMAIL_TEXT = "invalidEmailText";
		public static final String MAX_VALUE_TEXT = "maxValueText";
		public static final String MIN_VALUE_TEXT = "minValueText";
		
		public static final String ERROR_ICON_BLOBID = "errorIconBlobId";
		public static final String VALIDATION_ICON_BLOBID = "iconBlobId";
		
		public static final String TF_SUGGESTION_STYLE = "suggestionStyle";
		public static final String SUGGESTIONSTYLE_INLINE = "inlineSuggestion";
		public static final String SUGGESTION_ON_TOP = "suggestionOnTop";
		public static final String SUGGESTION_IN_BOTTOM = "suggestionInBottom";
		public static final String TF_SUGGESTION_TEXT = "suggestionText";
		public static final String SUGGESTION_TEXT_CSS = "suggestionTitleCss";
		
		
		public static final String NUMFIELD_TYPE = "numFieldType";
		public static final String NUMFIELD_DEC = "decNumField";
		public static final String NUMFIELD_INT = "intNumField";
		public static final String NUMFIELD_NEGATIVE = "negativeNumField";
		public static final String TF_MINVALUE = "minValue";
		public static final String TF_MAXVALUE = "maxValue";
		public static final String PROPERTY_BY_FIELD_NAME = "propertyByFieldName";
		public static final String DEC_PRECISION = "decPrecision";
		
		public static final String DEC_REGEX_EXP = "^[0-9]+(\\.[0-9]{1,precision})?$";
		public static final String INT_REGEX_EXP = "[\\d]*";
		public static final String EMAIL_REGEX_EXP = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
		
		public static final String VALIDATION_EVENT = "validationEvent";
		public static final String VALIDATE_ON_BLUR = "validationOnBlur";
		public static final String VALIDATE_ON_CHANGE = "validationOnChange";
		
	}

	@Override
	public void onKeyUp(KeyUpEvent event) {
		
		Integer keycode= event.getNativeKeyCode();
		if(keycode.equals(KeyCodes.KEY_BACKSPACE) || keycode.equals(KeyCodes.KEY_TAB)|| keycode.equals(KeyCodes.KEY_DELETE)){
			validateField();
		}
				
	}


	@Override
	public void onKeyPress(KeyPressEvent event) {
		String fieldType = getTextFieldType();
		Character charCode = event.getCharCode();
		final int unicharCode = event.getUnicodeCharCode();
		if(fieldType.equals(TextFieldConstant.TFTYPE_NUMERIC)){
			
			if(!Character.isDigit(charCode)){
				
				String numFieldType = getNumFieldType();
				
				Boolean isNegative = isFieldAllowNegative();
								
				if(numFieldType.equals(TextFieldConstant.NUMFIELD_DEC)){
					if(!(charCode.equals('.') || (charCode.equals('-') && isNegative))){
						event.preventDefault();
					}
				}
				
				if(numFieldType.equals(TextFieldConstant.NUMFIELD_INT)){
					if(!(charCode.equals('-') && isNegative)){
						event.preventDefault();
				}
				
		  }
		}
		}
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {    
			  @Override
			  public void execute() {
				  String eventType = getValidationEvent();
				  if(!eventType.equals(TextFieldConstant.VALIDATE_ON_BLUR)){
					  validateField();
				  }else if(eventType.equals(TextFieldConstant.VALIDATE_ON_BLUR) && unicharCode==KeyCodes.KEY_ENTER){
					  validateField();
				  }
		}
		});
	}
	
	public String getFieldText() {
		
		String fieldType = getTextFieldType();
		
		if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_TXTBOX)) {
			return textBox.getText();
		} else if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_PSWBOX)) {
			return passwordTextBox.getText();
		} else {
			return textArea.getText();
		}
	}
	
	@Override
	public void onBlur(BlurEvent event) {
		 validateField();
	}
	


}
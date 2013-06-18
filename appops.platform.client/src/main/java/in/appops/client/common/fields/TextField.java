package in.appops.client.common.fields;

import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * Field class to represent a {@TextBox}, {@TextArea} or {@PasswordTextBox}
 * @author nairutee
 * 
 */
public class TextField extends Composite implements Field, FocusHandler, ValueChangeHandler, BlurHandler, KeyDownHandler, MouseWheelHandler,KeyUpHandler{

	private Configuration configuration;
	private String fieldValue;
	private TextBox textBox;
	private PasswordTextBox passwordTextBox;
	private TextArea textArea;
	private String fieldType;
	private String tempFieldValue;
	private HandlerManager handlerManager;
	private DockPanel basepanel ;
	private Widget selectedWidget = null;
	private HorizontalPanel topWidget = new HorizontalPanel();
	private HorizontalPanel rightWidget = new HorizontalPanel();
	private HorizontalPanel bottomWidget = new HorizontalPanel();
	
	static EventBus eventBus = GWT.create(SimpleEventBus.class); 
	
	public TextField(){
		handlerManager = new HandlerManager(this);
	}
	
	
	/**
	 * creates the field UI according the configuration set to it;
	 * @throws AppOpsException 
	 */
	public void create() throws AppOpsException {
		if(getConfiguration() == null)
			throw new AppOpsException("TextField configuration unavailable");
		
		basepanel = new DockPanel();
		
		basepanel.add(topWidget,DockPanel.NORTH);
		if(getConfiguration().getPropertyByName(TextFieldConstant.ERROR_STYLE) != null) {
			basepanel.add(rightWidget,DockPanel.EAST);
		}
		basepanel.add(bottomWidget,DockPanel.SOUTH);
		
		rightWidget.setStylePrimaryName("errorPlaceHolder");
		
		if(selectedWidget!=null)
			basepanel.add(selectedWidget,DockPanel.CENTER);
		initWidget(basepanel);
	}
	
	
	/**
	 * Method read the configuration and set it to field .
	 */
	@Override
	public void configure() {
		Integer visibleLines = getNoOfVisibleLines();
		fieldType = getTextFieldType();
		
			if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_TXTBOX) || fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_NUMERIC) ){
				textBox = new TextBox();
				textBox.setText(getFieldValue());
				textBox.setReadOnly(isFieldReadOnly());
				if(getPrimaryCss() != null)
					textBox.setStylePrimaryName(getPrimaryCss());
				if(getDependentCss() != null)
					textBox.addStyleName(getDependentCss());
				if(getFieldDeugId() != null)
					textBox.ensureDebugId(getFieldDeugId());
				if(getFieldMaxLength()!=null)
					textBox.setMaxLength(getFieldMaxLength());
				
				selectedWidget =textBox;
				textBox.addFocusHandler(this);
				textBox.addBlurHandler(this);
				textBox.addValueChangeHandler(this);
				textBox.addKeyDownHandler(this);
				textBox.addMouseWheelHandler(this);
				textBox.addKeyUpHandler(this);
			}else if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_EMAILBOX)){
				textBox = new TextBox();
				textBox.setText(getFieldValue());
				textBox.setReadOnly(isFieldReadOnly());
				if(getPrimaryCss() != null)
					textBox.setStylePrimaryName(getPrimaryCss());
				if(getDependentCss() != null)
					textBox.addStyleName(getDependentCss());
				if(getFieldDeugId() != null)
					textBox.ensureDebugId(getFieldDeugId());
				if(getFieldMaxLength()!=null)
					textBox.setMaxLength(getFieldMaxLength());
								
				selectedWidget =textBox;
				textBox.addFocusHandler(this);
				textBox.addBlurHandler(this);
				textBox.addValueChangeHandler(this);
			}else if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_PSWBOX)){
				passwordTextBox = new PasswordTextBox();
				passwordTextBox.setText(getFieldValue());
				passwordTextBox.setReadOnly(isFieldReadOnly());
				if(getPrimaryCss() != null)
					passwordTextBox.setStylePrimaryName(getPrimaryCss());
				if(getDependentCss() != null)
					passwordTextBox.addStyleName(getDependentCss());
				if(getFieldDeugId() != null)
					passwordTextBox.ensureDebugId(getFieldDeugId());
				if(getFieldMaxLength()!=null)
					passwordTextBox.setMaxLength(getFieldMaxLength());
				
				passwordTextBox.addFocusHandler(this);
				passwordTextBox.addBlurHandler(this);
				passwordTextBox.addValueChangeHandler(this);
				
				selectedWidget =passwordTextBox;
			
		}else if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTTYPE_TXTAREA)){
			textArea = new TextArea();
			textArea.setVisibleLines(visibleLines);
			textArea.setText(getFieldValue());
			textArea.setReadOnly(isFieldReadOnly());
			
			if(getPrimaryCss() != null)
				textArea.setStylePrimaryName(getPrimaryCss());
			if(getDependentCss()!= null)
				textArea.addStyleName(getDependentCss());
			if(getFieldDeugId() != null)
				textArea.ensureDebugId(getFieldDeugId());
			if(getFieldCharWidth()!=null)
				textArea.setCharacterWidth(getFieldCharWidth());
			
			selectedWidget =textArea;
			
			textArea.addFocusHandler(this);
			textArea.addBlurHandler(this);
			textArea.addValueChangeHandler(this);
		}		
		setFieldSuggestionTitle();
	}
	
	
	/**
	 * resets the field to the original value that has been set via setFieldValue()
	 */
	@Override
	public void reset() {
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
		return this.fieldValue;
	}

	@Override
	public void setFieldValue(String fieldValue) {
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
		if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_TXTBOX) || fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_NUMERIC) || fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_EMAILBOX))
			textBox.setText("");
		else if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_PSWBOX))
			passwordTextBox.setText("");
		else
			textArea.setText("");
	}

	@Override
	public void onFocus(FocusEvent event) {
		
		clearSuggestionText();
		
		FieldEvent fieldEvent = new FieldEvent();
		fieldEvent.setEventType(FieldEvent.EDITINITIATED);
		if(event.getSource() instanceof PasswordTextBox){
			fieldEvent.setEventData(passwordTextBox.getText());
		}else if(event.getSource() instanceof TextBox){
			fieldEvent.setEventData(textBox.getText());
		} else if(event.getSource() instanceof TextArea){
			fieldEvent.setEventData(textArea.getText());
		} 
		//AppUtils.EVENT_BUS.fireEvent(fieldEvent);
	}

	@Override
	public void onValueChange(ValueChangeEvent event) {
		FieldEvent fieldEvent = new FieldEvent();
		fieldEvent.setEventType(FieldEvent.EDITINPROGRESS);
		if(event.getSource() instanceof PasswordTextBox){
			fieldEvent.setEventData(passwordTextBox.getText());
		}else if(event.getSource() instanceof TextBox){
			fieldEvent.setEventData(textBox.getText());
		} else if(event.getSource() instanceof TextArea){
			fieldEvent.setEventData(textArea.getText());
		}
		validateField();
		
		AppUtils.EVENT_BUS.fireEvent(fieldEvent);
	}
	
	/**
	 * Method validate the field. and set validation message or error title according to it.
	 */
	private void validateField() {

		if (fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_EMAILBOX)) {

			if (!textBox.getText().equals("") && !textBox.getText().equals(getFieldSuggestionTitle())) {
				if (textBox.getText().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
					setFieldValidationMessage();
				} else {
					setFieldErrorTitle();
				}
			}
		} else if (fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_PSWBOX)) {
			
			if (!passwordTextBox.getText().equals("") && !passwordTextBox.getText().equals(getFieldSuggestionTitle())) {
				if (passwordTextBox.getText().length() < getFieldMinimumLength())
					setFieldErrorTitle();
				else
					setFieldValidationMessage();
			}
		} else if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_NUMERIC)){
			if (!textBox.getText().equals("")) {
				String regexExp = null;
				String numericFieldType = getNumericFieldType();
				if (numericFieldType.equals(TextFieldConstant.DEC_NUMFIELD)) {
					regexExp = TextFieldConstant.DEC_REGEX_EXP;
				} else if (numericFieldType.equals(TextFieldConstant.INT_NUMFIELD)) {
					regexExp = TextFieldConstant.INT_REGEX_EXP;
				}
				
				if (!textBox.getText().matches(regexExp)) {
					setFieldErrorTitle();
				} else
					setFieldValidationMessage();
			} else {
				removeErrorMessagesIfPresent();
			}
		}
	}
	
	private void removeErrorMessagesIfPresent(){
		if (getConfiguration() != null) {
			String errorStyle = getConfiguration().getPropertyByName(TextFieldConstant.ERROR_STYLE);
			if (errorStyle != null) {

				if (errorStyle.equals(TextFieldConstant.INLINE_ICONIC_ERROR_STYLE)) {
					getWidget().removeStyleName("appops-Inline-Error-TextBox");
					getWidget().removeStyleName("appops-Validation_Correct-TextBox");
					getWidget().getElement().getStyle().clearProperty("background");
				} else if (errorStyle.equals(TextFieldConstant.OUTLINE_ICONIC_ERROR_STYLE)) {
					rightWidget.clear();
				} else if (errorStyle.equals(TextFieldConstant.VALIDATION_MSG_TITLE_STYLE)) {
					String errorTitleStyle = getErrorTitleStyle();
					if (errorTitleStyle.equals(TextFieldConstant.VALIDATION_MSG_IN_BOTTOM)) {
						bottomWidget.clear();
					} else if (errorTitleStyle.equals(TextFieldConstant.VALIDATION_MSG_ON_TOP)) {
						topWidget.clear();
					} else if (errorTitleStyle.equals(TextFieldConstant.VALIDATION_MSG_ON_RIGHT)) {
						rightWidget.clear();
					}
				}
			}
		}
	}

	@Override
	public void onBlur(BlurEvent event) {
		
		checkFieldSuggestionTitleAndSet();
		
		FieldEvent fieldEvent = new FieldEvent();
		fieldEvent.setEventType(FieldEvent.EDITCOMPLETED);
		 if(event.getSource() instanceof PasswordTextBox){
			fieldEvent.setEventData(passwordTextBox.getText());
		}else if(event.getSource() instanceof TextBox){
			fieldEvent.setEventData(textBox.getText());
		} else if(event.getSource() instanceof TextArea){
			fieldEvent.setEventData(textArea.getText());
		} 
		// validateField();
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
	
	public String getFieldText() {
		if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_TXTBOX)) {
			setTempFieldValue(textBox.getText());
		} else if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_PSWBOX)) {
			setTempFieldValue(passwordTextBox.getText());
		} else {
			setTempFieldValue(textArea.getText());
		}
		
		return getTempFieldValue();
	}
	
	public Widget getWidget() {
		
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
	public Integer getNoOfVisibleLines(){
		
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
	public String getTextFieldType(){
		
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
	public String getPrimaryCss(){
		
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
	public String getDependentCss(){
		
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
	
	public String getFieldDeugId(){
		
		if(getConfiguration()!=null){
			
			String degugId = getConfiguration().getPropertyByName(TextFieldConstant.TF_DEBUGID);
			if(degugId !=null){
				return degugId;
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
	
	private Integer getFieldMinimumLength(){
		
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
	
	public Boolean isFieldReadOnly(){
		
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
	 * Method checks whether suggesion title is present if not set it to it. This is application only for inline suggestion. 
	 */
	private void checkFieldSuggestionTitleAndSet(){
		
			if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_TXTBOX) || fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_EMAILBOX)
					|| fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_NUMERIC)){
				if(textBox.getText().length() == 0 && getFieldSuggestionStyle().equals(TextFieldConstant.INLINE_SUGGESTION))
					setFieldSuggestionTitle();
			}else if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_PSWBOX)){
				if(passwordTextBox.getText().length() == 0 && getFieldSuggestionStyle().equals(TextFieldConstant.INLINE_SUGGESTION))
					setFieldSuggestionTitle();
			}else{
				if(textArea.getText().length() == 0 && getFieldSuggestionStyle().equals(TextFieldConstant.INLINE_SUGGESTION))
					setFieldSuggestionTitle();
			}
	}
	
	/**
	 * Method will show suggestion title based on the suggestion style i.e inline/top/bottom set in the configuration. 
	 */
	public void setFieldSuggestionTitle(){
		
		if(getConfiguration()!=null){
			
			String suggestionStyle =getFieldSuggestionStyle(); 
			if(suggestionStyle !=null){
				
				String suggestionTitle = getFieldSuggestionTitle();
				if (suggestionTitle != null) {
					Label suggestionLabel = new Label(suggestionTitle);
					suggestionLabel.setStylePrimaryName(getFieldSuggestionTitleCss());
					if (suggestionStyle.equals(TextFieldConstant.INLINE_SUGGESTION)) {
						if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_TXTBOX) || fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_EMAILBOX)
								|| fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_NUMERIC)){
							textBox.setText(suggestionTitle);
							textBox.addStyleName(getFieldSuggestionTitleCss());
						}else if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_PSWBOX)){
							passwordTextBox.setText(suggestionTitle);
							passwordTextBox.setStylePrimaryName(getFieldSuggestionTitleCss());
						}else{
							textArea.setText(suggestionTitle);
							textArea.setStylePrimaryName(getFieldSuggestionTitleCss());
						}
						
					} else if (suggestionStyle.equals(TextFieldConstant.SUGGESTION_ON_TOP)) {
						topWidget.add(suggestionLabel);
					} else if (suggestionStyle.equals(TextFieldConstant.SUGGESTION_IN_BOTTOM)) {
						basepanel.add(suggestionLabel, DockPanel.SOUTH);
					}
				}
			}
		}
		
	}
	
	/**
	 * Method return the validation icon blobId set in the configuration. o.w return default validation blobId.
	 * @return
	 */
	
	private String getFieldValidationIconBlobId(){
		
		if(getConfiguration()!=null){
			
			String validationIconBlobId = getConfiguration().getPropertyByName(TextFieldConstant.VALIDATION_ICON_BLOBID);
			if(validationIconBlobId !=null){
				return validationIconBlobId;
			}else{
				return "images/validation_icon.png";
			}
		}
		return null;
	}
	
	/**
	 * Method return the error icon blobId set in the configuration. o.w return default error blobId.
	 * @return
	 */
	
	private String getFieldErrorIconBlobId(){
		
		if(getConfiguration()!=null){
			
			String errorIconBlobId = getConfiguration().getPropertyByName(TextFieldConstant.VALIDATION_ICON_BLOBID);
			if(errorIconBlobId !=null){
				return errorIconBlobId;
			}else{
				return "images/validation_error_icon.jpg";
			}
		}
		return null;
	}
	
	/**
	 * Method return the suggestion title css set in the configuration. o.w return default error blobId.
	 * @return
	 */
	private String getFieldSuggestionTitleCss() {
		if (getConfiguration() != null) {

			String suggestionTitleCss = getConfiguration().getPropertyByName(TextFieldConstant.SUGGESTION_TITLE_CSS);
			if (suggestionTitleCss != null) {
				return suggestionTitleCss;
			} else {
				return "suggestionText";
			}
		}
		return null;
	}
	
	/**
	 * Method clear the suggestion text.
	 */
	
	private void clearSuggestionText() {
		
		String suggestionTitle = getFieldSuggestionTitle();
		if(suggestionTitle!=null){
			if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_TXTBOX) || fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_EMAILBOX)
					|| fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_NUMERIC)){
				if(textBox.getText().equals(suggestionTitle)){
					textBox.setText("");
					textBox.removeStyleName(getFieldSuggestionTitleCss());
				}
			}else if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_PSWBOX)){
				if(passwordTextBox.getText().equals(suggestionTitle)){
					passwordTextBox.setText("");
					passwordTextBox.removeStyleName(getFieldSuggestionTitleCss());
				}
			}else{
				if(textArea.getText().equals(suggestionTitle)){
					textArea.setText("");
					textArea.removeStyleName(getFieldSuggestionTitleCss());
				}
			}
		}
		
	}
	/**
	 * Method will return the suggestion which is set in configuration o.w return default style i.e suggestion on top. 
	 */
	public String getFieldSuggestionStyle(){
		
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
	public String getFieldSuggestionTitle() {

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
	
	private ImageField getImageField(String blobId,String displayText){
		Configuration conf = new Configuration();
		conf.setPropertyByName(ImageField.IMAGEFIELD_BLOBID, blobId);
		if(displayText!=null)
			conf.setPropertyByName(ImageField.IMAGEFIELD_DISPLAYTEXT,displayText );
		ImageField imageField = new ImageField();
		imageField.setConfiguration(conf);
		try {
			imageField.create();
		} catch (AppOpsException e) {
			
		}
		return imageField;
		
	}
	
	/** 
	 * Method set validation message after field is validated.
	 */
	
	public void setFieldValidationMessage() {

		if (getConfiguration() != null) {
			String errorStyle = getConfiguration().getPropertyByName(TextFieldConstant.ERROR_STYLE);
			if (errorStyle != null) {

				if (errorStyle.equals(TextFieldConstant.INLINE_ICONIC_ERROR_STYLE)) {
					getWidget().removeStyleName("appops-Inline-Error-TextBox");
					getWidget().addStyleName("appops-Validation_Correct-TextBox");
					getWidget().getElement().getStyle().setProperty("background", "white url("+ getFieldValidationIconBlobId()+") no-repeat right	center");
				} else if (errorStyle.equals(TextFieldConstant.OUTLINE_ICONIC_ERROR_STYLE)) {
					rightWidget.clear();
					rightWidget.add(getImageField(getFieldValidationIconBlobId(),null));
				} else if (errorStyle.equals(TextFieldConstant.VALIDATION_MSG_TITLE_STYLE)) {
					
					String correctValueText = getFieldCorrectValueText();
					Label correctTextLbl = new Label(correctValueText);
					correctTextLbl.setStylePrimaryName("appops-Validation_Text");
					
					String errorTitleStyle = getErrorTitleStyle();
					if (errorTitleStyle.equals(TextFieldConstant.VALIDATION_MSG_IN_BOTTOM)) {
						bottomWidget.clear();
						bottomWidget.add(correctTextLbl);
					} else if (errorTitleStyle.equals(TextFieldConstant.VALIDATION_MSG_ON_TOP)) {
						topWidget.clear();
						topWidget.add(correctTextLbl);
					} else if (errorTitleStyle.equals(TextFieldConstant.VALIDATION_MSG_ON_RIGHT)) {
						rightWidget.clear();
						rightWidget.add(correctTextLbl);
					}
				}
			}
		}

	}
	
	/** 
	 * Method return error text from configuration o.w return default error text. "Field value is not correct or too small to fit..";
	 */

	public String getFieldErrorText(){
		
		if(getConfiguration()!=null){
			String errorText = getConfiguration().getPropertyByName(TextFieldConstant.TF_ERROR_TEXT);
			if(errorText !=null){
				
				return errorText;
			}else{
				return "Field value is not correct or too small to fit..";
			}
		}
		return null;
	}
	
	public String getFieldCorrectValueText(){
		
		if(getConfiguration()!=null){
			String correctValueText = getConfiguration().getPropertyByName(TextFieldConstant.TF_CORRECTVALUE_TEXT);
			if(correctValueText !=null){
				
				return correctValueText;
			}else{
				return "OK";
			}
		}
		return null;
	}
	
	/** 
	 * Method set the error according to the error style i.e if its TextFieldConstant.INLINE_ICONIC_ERROR_STYLE or TextFieldConstant.OUTLINE_ICONIC_ERROR_STYLE then field will show error in the form of error icon. if 
	 * its   TextFieldConstant.ERROR_TITLE_STYLE then according to the position it will set the error title.
	 */
	public void setFieldErrorTitle() {

		if (getConfiguration() != null) {

			String errorStyle = getConfiguration().getPropertyByName(TextFieldConstant.ERROR_STYLE);

			if (errorStyle != null) {
				if (errorStyle.equals(TextFieldConstant.INLINE_ICONIC_ERROR_STYLE)) {
					if (getFieldErrorIconBlobId() != null) {

						getWidget().addStyleName("appops-Inline-Error-TextBox");
						getWidget().getElement().getStyle().setProperty("background","white url(" + getFieldErrorIconBlobId()+ ") no-repeat right center");
					}
					getWidget().setTitle(getFieldErrorText());
				} else if (errorStyle.equals(TextFieldConstant.OUTLINE_ICONIC_ERROR_STYLE)) {
					rightWidget.clear();
					if (getFieldErrorIconBlobId() != null) {
						rightWidget.add(getImageField(getFieldErrorIconBlobId(),getFieldErrorText()));
					}
				} else if (errorStyle.equals(TextFieldConstant.VALIDATION_MSG_TITLE_STYLE)) {
					String errorTitleStyle = getErrorTitleStyle();
					String errorText = getFieldErrorText();
					Label errorLabel = new Label(errorText);

					if (errorTitleStyle.equals(TextFieldConstant.VALIDATION_MSG_IN_BOTTOM)) {
						bottomWidget.clear();
						bottomWidget.add(errorLabel);
					} else if (errorTitleStyle.equals(TextFieldConstant.VALIDATION_MSG_ON_TOP)) {
						topWidget.clear();
						topWidget.add(errorLabel);
					} else if (errorTitleStyle.equals(TextFieldConstant.VALIDATION_MSG_ON_RIGHT)) {
						rightWidget.clear();
						rightWidget.add(errorLabel);
					}
					errorLabel.setStylePrimaryName("appops-Error_Text");
				}
			}
		}

	}
	
	private String getErrorTitleStyle(){
		
		if(getConfiguration()!=null){
			String errorTitleStyle = getConfiguration().getPropertyByName(TextFieldConstant.VALIDATION_MSG_TITLE_STYLE);
			if(errorTitleStyle !=null){
				return errorTitleStyle;
			}else{
				return TextFieldConstant.VALIDATION_MSG_ON_RIGHT;
			}
		}
		return null;
	}
	
	private String getNumericFieldType(){
		
		if(getConfiguration()!=null){
			String numFieldType = getConfiguration().getPropertyByName(TextFieldConstant.NUMFIELD_TYPE);
			if(numFieldType !=null){
				return numFieldType;
			}else{
				return TextFieldConstant.INT_NUMFIELD;
			}
		}
		return null;
	}
	
	public interface TextFieldConstant{
		public static final String TF_VISIBLELINES = "fieldVisibleLines";
		public static final String TF_READONLY = "fieldReadOnly";
		public static final String TF_PRIMARYCSS = "fieldPrimaryCss";
		public static final String TF_DEPENDENTCSS = "fieldDependentCss";
		public static final String TF_DEBUGID = "fieldDebugId";
		public static final String TF_TYPE = "fieldType";
		
		public static final String TFTYPE_TXTBOX = "txtbox";
		public static final String TFTYPE_PSWBOX = "passowrdTxtbox";
		public static final String TFTTYPE_TXTAREA = "txtarea";
		public static final String TFTYPE_EMAILBOX = "emailbox";
		public static final String TFTYPE_NUMERIC = "numeric";
		public static final String PROPERTY_BY_FIELD_NAME = "propertyByFieldName";
		public static final String TF_MAXLENGTH = "fieldMaxlength";
		public static final String TF_MINLENGTH = "fieldMinlength";
		public static final String TF_CHARWIDTH = "fieldCharWidth";
		
		public static final String TF_SINGLELINE = "singleLineTextField";
		public static final String TF_MULTILINE = "multilineTextField";
		
		public static final String ERROR_STYLE = "errorStyle";
		public static final String INLINE_ICONIC_ERROR_STYLE = "inlineIconicErrorStyle";
		public static final String VALIDATION_ICON_BLOBID = "iconBlobId";
		public static final String ERROR_ICON_BLOBID = "errorIconBlobId";
		public static final String OUTLINE_ICONIC_ERROR_STYLE = "outlineIconicErrorStyle";
		public static final String VALIDATION_MSG_TITLE_STYLE = "validationMsgTitleStyle";
		public static final String TF_ERROR_TEXT = "errorTitleText";
		public static final String TF_CORRECTVALUE_TEXT = "correctValueText";
		public static final String VALIDATION_MSG_IN_BOTTOM = "validationMsgInBottom";
		public static final String VALIDATION_MSG_ON_TOP = "validationMsgOnTop";
		public static final String VALIDATION_MSG_ON_RIGHT = "validationMsgOnRight";
		
		public static final String TF_SUGGESTION_STYLE = "suggestionStyle";
		public static final String SUGGESTION_TITLE_CSS = "suggestionTitleCss";
		public static final String INLINE_SUGGESTION = "inlineSuggestion";
		public static final String SUGGESTION_ON_TOP = "suggestionOnTop";
		public static final String SUGGESTION_IN_BOTTOM = "suggestionInBottom";
		public static final String TF_SUGGESTION_TEXT = "suggestionText";
		
		public static final String NUMFIELD_TYPE = "numFieldType";
		public static final String DEC_NUMFIELD = "decNumField";
		public static final String INT_NUMFIELD = "intNumField";
		public static final String DEC_REGEX_EXP = "^[0-9]+(\\.[0-9]{1,4})?$";
		public static final String INT_REGEX_EXP = "[\\d]*";
		
		
	}

	@Override
	public void onKeyDown(KeyDownEvent event) {
		FieldEvent fieldEvent = new FieldEvent();
		
		int keyCode = event.getNativeKeyCode();
		if(keyCode == KeyCodes.KEY_UP){
			fieldEvent.setEventData("increment");
		} else if((keyCode == KeyCodes.KEY_DOWN)) {
			fieldEvent.setEventData("decrement");
		}
		fieldEvent.setEventType(FieldEvent.EDITINITIATED);
		
		
		
		AppUtils.EVENT_BUS.fireEvent(fieldEvent);
	}


	@Override
	public void onMouseWheel(MouseWheelEvent event) {
		FieldEvent fieldEvent = new FieldEvent();

		if(event.isNorth()){
			fieldEvent.setEventData("increment");
		} else if(event.isSouth()) {
			fieldEvent.setEventData("decrement");
		}
		fieldEvent.setEventType(FieldEvent.EDITINITIATED);
		AppUtils.EVENT_BUS.fireEvent(fieldEvent);
	}


	@Override
	public void onKeyUp(KeyUpEvent event) {
		TextBox txt = (TextBox) event.getSource();
		txt.getText();
		if(fieldType.equalsIgnoreCase(TextFieldConstant.TFTYPE_NUMERIC)){
			validateField();
		}
		
	}



	/*@Override
	public void onKeyPress(KeyPressEvent event) {
		if(!Character.isDigit(event.getCharCode()) && Character.isLetter(event.getCharCode())){
			validateField();
		}
		
	}*/

}
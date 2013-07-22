package in.appops.client.common.config.field;

import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.shared.Configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;


/**
Represents base class for all the fields.
 
<h3>CSS Style Rules</h3>
<ul class='css'>
<li>.appops-errorTopBottomCls { style to show errors outside the field }</li>
<li>.appops-errorIconCls { class with error icon set in the background  }</li>
<li>.appops-validFieldTopBottomCls { style to show valid messages outside the field }</li>
<li>.appops-validFieldIconCls { style class with valid field icon set in the background }</li>
<li>.appops-validFieldInline { style class to show valid field  }</li>
<li>.appops-suggestionText { style to show suggestion text }</li>
</ul>

<p>
<h3>Configuration</h3>
<a href="BaseField.BaseFieldConstant.html">Available configurations</a>
</p>
*/

public class BaseField extends Composite implements Field {
	
	public interface BaseFieldConstant {
		
		/** Sets an id to the field **/
		public static final String BF_ID = "baseFieldId";
		
		/** Style class primary for field. **/
		public static final String BF_PCLS = "baseFieldPrimaryCss";
		
		/** Style class dependent for field **/
		public static final String BF_DCLS = "baseFieldDependentCss";
		
		/** A value to initialize this field with. **/
		public static final String BF_DEFVAL = "defaultValue";
		
		/** Prevents displaying valid marker **/
		public static final String BF_SHOW_VALID_FIELD = "showValidField";
		
		/** Specifies the position of the error marker. viz. {@link BaseFieldConstant#BF_SIDE}, {@link BaseFieldConstant#BF_TOP}, {@link BaseFieldConstant#BF_BOTTOM}
		 *  Defaults to {@link BaseFieldConstant#SP_ERRINLINE} **/
		public static final String BF_ERRPOS = "errorPosition";
		
		/** Specifies the position of the suggestion. viz. {@link BaseFieldConstant#BF_SIDE}, {@link BaseFieldConstant#BF_TOP}, {@link BaseFieldConstant#BF_BOTTOM}
		 *  Defaults to {@link BaseFieldConstant#BF_SUGGESTION_INLINE} **/
		public static final String BF_SUGGESTION_POS = "suggestionPosition";
		
		/** Error marker to be shown above the field. {@link BaseFieldConstant#BF_ERRPOS} **/
		public static final String BF_TOP = "top";
		
		/** Error marker to be shown right side of the field. {@link BaseFieldConstant#BF_ERRPOS} **/
		public static final String BF_SIDE = "side";
		
		/** Error marker to be shown below the field. {@link BaseFieldConstant#BF_ERRPOS} **/
		public static final String BF_BOTTOM = "bottom";
		
		/** Error marker to be shown inline of field. {@link BaseFieldConstant#BF_ERRPOS} **/
		public static final String BF_ERRINLINE = "errorInline";
		
		/** Suggestion to be shown inline of field. {@link BaseFieldConstant#BF_ERRPOS} **/
		public static final String BF_SUGGESTION_INLINE = "suggestionInline";
		
		/** Suggestion text to be shown. {@link BaseFieldConstant#BF_ERRPOS} **/
		public static final String BF_SUGGESTION_TEXT = "suggestionText";
		
		/** Blank field error text to be shown.**/
		public static final String BF_BLANK_TEXT = "blankFieldErrTxt";
		
		/**
		 * Specifies whether the field be disabled.
		 * Defaults to <code>false</code>
		 */
		public static final String BF_ENABLED = "disabled";
		
		/** Specifies whether this field should be validated immediately whenever a change in its value is detected. 
		 *  When set to true, it would allow the field to show feedback about the validity of its contents immediately as the user is typing.
		 *  Default would be <code>true</code> otherwise specified.
		 **/
		public static final String BF_VALIDATEONCHANGE = "validateOnChange";
		
		/** Specifies whether this field should be validated when it looses focus. 
		 *  Default would be <code>true</code> otherwise specified.
		 **/
		public static final String BF_VALIDATEONBLUR = "validateOnBlur";

		/** Error msg to be shown when marking invalid if no message is provided. <br>
		 *  Default is {@literal The value in this field is invalid}.
		 **/
		public static final String BF_INVLDMSG = "invalidMsg";

		/** Set the fields readOnly **/
		public static final String BF_READONLY = "readOnly";

		/** Set the fields error msg css class **/
		public static final String BF_ERRMSGCLS = "errorMsgCls";
		
		/** Set the fields error icon css **/
		public static final String BF_ERRMSGICONCLS = "errorMsgIconCls";
		
		/** Set the fields suggestion msg css class **/
		public static final String BF_SUGGESTION_MSG_CLS = "sugegstionMsgCls";
		
		/** Set the field tab index **/
		public static final String BF_TABINDEX = "tabIndex";
		
		/** Specifies whether field allow blank or not. Defaults to false  **/
		public static final String BF_ALLOWBLNK = "allowBlank";
		
		/** Specifies the error icon to use  **/
		public static final String BF_ERRICON_BLOB = "errorIcon";
		
		/** Set whether valid markers to show or not **/
		public static final String BF_VALID_FIELD_MSGICONCLS = "validFieldMsgIcon";
		
		/** Specifies validation icon blob to use to show valid field value**/
		public static final String BF_VALIDATION_ICON_BLOBID = "validIconBlobId";
		
		/** Specifies text to be shown when user enters valid value**/
		public static final String BF_VALIDVALUE_TEXT = "validValueTxt";
		
		/** Set the fields valid field msg css class **/
		public static final String BF_VALID_FIELD_MSGCLS = "validFieldMsgCls";
		
		String BF_BINDPROP = "bindProperty";
		String BF_VISIBLE = "visible";

		
	}
	
	
	/**************** Properties *******************/
	
	/** The original value of the field as configured in the value configuration **/
	private Object originalValue = null;
	
	/** value of the field **/
	private Object value;

	/** List of current active error displayed **/
	private ArrayList<String> activeErrors;
	
	private Entity entity;
	
	protected DockPanel basePanel;
	private HTML label;
	private HorizontalPanel topWidget = null;
	private HorizontalPanel sideWidget = null;
	private HorizontalPanel bottomWidget = null;
	
	private Configuration configuration;

	protected Long bindId;	
	
	
	public BaseField() {
		initialize();
		initWidget(basePanel);
	}
	
	/**
	 * Initializes the member variables
	 */
	protected void initialize() {
		basePanel = new DockPanel();
		label = new HTML();
		activeErrors = new ArrayList<String>();
	}

	
	/****************************** *********************************/
	
	/**
	 * Returns configuration if present else creates a new one
	 */
	@Override
	public Configuration getConfiguration() {
		if(configuration != null) {
			return configuration;
		}
		else {
			return new Configuration();	
		}
	}

	/**
	 * Sets the configuration for the field
	 */
	@Override
	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	/**
	 * Returns true if the configuration is provided.
	 * @param configKey - The configuration to check
	 * @return
	 */
	protected boolean hasConfiguration(String configKey) {
		if(configuration != null && configuration.getPropertyByName(configKey) != null) {
			return true;
		}
		return false;
	}
	
	/**
	 * clears the field .
	 */
	
	protected void clear() { }
	
	/**
	 * Returns the value of the configuration if the configuration is provided.
	 * @param configKey - The configuration whose value it to be retrieved
	 * @return
	 */
	protected Serializable getConfigurationValue(String configKey) {
		if(hasConfiguration(configKey)) {
			return configuration.getPropertyByName(configKey);
		}
		return null;
	}
	
	/**
	 * Returns the id of the field.
	 * @return
	 */
	protected Integer getBaseFieldId() {
		Integer fieldId = null;
		if(getConfigurationValue(BaseFieldConstant.BF_ID) != null) {
			fieldId = (Integer) getConfigurationValue(BaseFieldConstant.BF_ID);
		}
		return fieldId;
	}
	
	/**
	 * Returns the primary style to be applied to the base field.
	 * If the style is not provided through configuration default is returned
	 * @return
	 */
	protected String getBaseFieldPrimCss() {
		String primaryCss = null;
		if(getConfigurationValue(BaseFieldConstant.BF_PCLS) != null) {
			primaryCss = getConfigurationValue(BaseFieldConstant.BF_PCLS).toString();
		}
		return primaryCss;
	}
	

	/**
	 * Returns the dependent style to be applied to the spinner field.
	 * If the style is not provided through configuration default is returned
	 * @return
	 */
	protected String getBaseFieldCss() {
		String depCss = null;
		if(getConfigurationValue(BaseFieldConstant.BF_DCLS) != null) {
			depCss = getConfigurationValue(BaseFieldConstant.BF_DCLS).toString();
		}
		return depCss;
	}
	
	/**
	 * Returns the default value to be set to the field, which is provided through configuration.
	 * This method can be overridden to suite different types of fields.
	 * @return
	 */
	protected Object getDefaultValue() {
		Object defaultValue = null;
		if(getConfigurationValue(BaseFieldConstant.BF_DEFVAL) != null) {
			defaultValue = getConfigurationValue(BaseFieldConstant.BF_DEFVAL);
		}
		return defaultValue;
	}
	
	/**
	 * Returns true if the valid field message to show;
	 * Defaults to false.
	 * @return
	 */
	protected boolean isShowValidField() {
		boolean isShowValidField = false;
		if(getConfigurationValue(BaseFieldConstant.BF_SHOW_VALID_FIELD) != null) {
			isShowValidField = (Boolean) getConfigurationValue(BaseFieldConstant.BF_SHOW_VALID_FIELD);
		}
		return isShowValidField;
	}

	
	/**
	 * Returns the position where the errors should be displayed
	 * Defaults to {@link BaseFieldConstant#BF_ERRINLINE}
	 * Values - {@link BaseFieldConstant#BF_ERRINLINE}, {@link BaseFieldConstant#BF_TOP}, {@link BaseFieldConstant#BF_BOTTOM}, {@link BaseFieldConstant#BF_SIDE}
	 * @return
	 */
	protected String getErrorPosition() {
		String errPos = BaseFieldConstant.BF_ERRINLINE;
		if(getConfigurationValue(BaseFieldConstant.BF_ERRPOS) != null) {
			errPos = getConfigurationValue(BaseFieldConstant.BF_ERRPOS).toString();
		}
		return errPos;
	}
	
	protected String getInvalidMsg() {
		String invalidMsg = "The value in this field is invalid";
		if(getConfigurationValue(BaseFieldConstant.BF_INVLDMSG) != null) {
			invalidMsg = getConfigurationValue(BaseFieldConstant.BF_INVLDMSG).toString();
		}
		return invalidMsg;
	}

	/**
	 * Returns if field is readonly or not. Defaults to false.
	 * @return
	 */
	protected boolean isReadOnly() {
		boolean readOnly = false;
		if(getConfigurationValue(BaseFieldConstant.BF_READONLY) != null) {
			readOnly = (Boolean)getConfigurationValue(BaseFieldConstant.BF_READONLY);
		}
		return readOnly;
	}

	protected boolean isEnabled() {
		boolean enabled = true;
		if(getConfigurationValue(BaseFieldConstant.BF_ENABLED) != null) {
			enabled = (Boolean)getConfigurationValue(BaseFieldConstant.BF_ENABLED);
		}
		return enabled;
	}
	
	/**
	 * Returns the error message css style. 
	 * @return
	 */
	protected String getErrorMsgCls() {
		String errorCss = getErrorPosition() == BaseFieldConstant.BF_BOTTOM || getErrorPosition() == BaseFieldConstant.BF_TOP  || getErrorPosition() == BaseFieldConstant.BF_SIDE 
				? "appops-errorTopBottomCls" : "appops-errorInvalidInline";
		if(getConfigurationValue(BaseFieldConstant.BF_ERRMSGCLS) != null) {
			errorCss = getConfigurationValue(BaseFieldConstant.BF_ERRMSGCLS).toString();
		}
		return errorCss;
	}
	
	/**
	 * Returns the error icon css style to use.
	 * @return
	 */
	protected String getErrorIconCls() {
		
		String errorCss = "appops-errorIconCls";
		if(getConfigurationValue(BaseFieldConstant.BF_ERRMSGICONCLS) != null) {
			errorCss = getConfigurationValue(BaseFieldConstant.BF_ERRMSGICONCLS).toString();
		}
		return errorCss;
	}
	
	/**
	 * Returns the valid field message css style.
	 * @return
	 */
	protected String getValidFieldMsgCls() {
		String errorCss = getErrorPosition() == BaseFieldConstant.BF_BOTTOM || getErrorPosition() == BaseFieldConstant.BF_TOP  || getErrorPosition() == BaseFieldConstant.BF_SIDE 
				? "appops-validFieldTopBottomCls" : "appops-validFieldInline";
		if(getConfigurationValue(BaseFieldConstant.BF_VALID_FIELD_MSGCLS) != null) {
			errorCss = getConfigurationValue(BaseFieldConstant.BF_VALID_FIELD_MSGCLS).toString();
		}
		return errorCss;
	}
	
	/**
	 * Returns the css style with checkmark icon in to use . 
	 */
	protected String getValidFieldIconCls() {
		
		String errorCss = "appops-validFieldIconCls";
		if(getConfigurationValue(BaseFieldConstant.BF_VALID_FIELD_MSGICONCLS) != null) {
			errorCss = getConfigurationValue(BaseFieldConstant.BF_VALID_FIELD_MSGICONCLS).toString();
		}
		return errorCss;
	}
	
	/**
	 * Returns the suggestion text css style.
	 * @return
	 */
	protected String getSuggestionMsgCls() {
		
		String errorCss = "appops-suggestionText";
		
		if(getConfigurationValue(BaseFieldConstant.BF_SUGGESTION_MSG_CLS) != null) {
			errorCss = getConfigurationValue(BaseFieldConstant.BF_SUGGESTION_MSG_CLS).toString();
		}
		return errorCss;
	}
	
	public boolean isValidateOnChange() {
		boolean validateOnChng = true;
		if(getConfigurationValue(BaseFieldConstant.BF_VALIDATEONCHANGE) != null) {
			validateOnChng = (Boolean)getConfigurationValue(BaseFieldConstant.BF_VALIDATEONCHANGE);
		}
		return validateOnChng;
	}
	
	public boolean isValidateOnBlur() {
		boolean validateOnBlur = true;
		if(getConfigurationValue(BaseFieldConstant.BF_VALIDATEONBLUR) != null) {
			validateOnBlur = (Boolean)getConfigurationValue(BaseFieldConstant.BF_VALIDATEONBLUR);
		}
		return validateOnBlur;
	}
	
	/**
	 * Returns whether field allow blank value . Defaults to true.
	 * @return
	 */
	public boolean isAllowBlank() {
		boolean allowBlank = true;
		if(getConfigurationValue(BaseFieldConstant.BF_ALLOWBLNK) != null) {
			allowBlank = (Boolean)getConfigurationValue(BaseFieldConstant.BF_ALLOWBLNK);
		}
		return allowBlank;
	}
	
	/**
	 * Returns the tab index of the field.
	 * @return
	 */
	protected Integer getTabIndex() {
		
		Integer pos = null;
		
		if(getConfigurationValue(BaseFieldConstant.BF_TABINDEX) != null) {
			pos = (Integer) getConfigurationValue(BaseFieldConstant.BF_TABINDEX);
		}
		return pos;
	}
	
	/**
	 * Returns the suggestion position.Defaults to {BF_SUGGESTION_INLINE}
	 * @return
	 */
	protected String getSuggestionPosition() {
		
		String pos = BaseFieldConstant.BF_SUGGESTION_INLINE;
		
		if(getConfigurationValue(BaseFieldConstant.BF_SUGGESTION_POS) != null) {
			pos = getConfigurationValue(BaseFieldConstant.BF_SUGGESTION_POS).toString();
		}
		return pos;
	}
	
	/**
	 * Returns the suggestion text for the field. If not set returns null;
	 * @return
	 */
	protected String getSuggestionText() {
		
		if(getConfigurationValue(BaseFieldConstant.BF_SUGGESTION_TEXT) != null) {
			return getConfigurationValue(BaseFieldConstant.BF_SUGGESTION_TEXT).toString();
		}
		return null;
	}

	/**
	 *  Method read blanks field error message which is set in the configuration.
	 * @return blanks field error message
	 */
	protected String getBlankFieldText() {
		String blankFdText = "Field is required";

		if (getConfigurationValue(BaseFieldConstant.BF_BLANK_TEXT) != null) {
			blankFdText = getConfigurationValue(BaseFieldConstant.BF_BLANK_TEXT).toString();
		}
		return blankFdText;
	}

	/**
	 * Returns the error icon blob to use.
	 * @return
	 */
	protected String getErrorIconBlobId(){
		
			if(getConfigurationValue(BaseFieldConstant.BF_ERRICON_BLOB) !=null){
				return getConfigurationValue(BaseFieldConstant.BF_ERRICON_BLOB).toString();
			}
		return null;
	}
	
	protected String getValidIconBlobId(){
		
		if(getConfigurationValue(BaseFieldConstant.BF_VALIDATION_ICON_BLOBID) !=null){
			return getConfigurationValue(BaseFieldConstant.BF_VALIDATION_ICON_BLOBID).toString();
		}
	return null;
}
	
	/**
	 * Method will show error title based on the error style i.e iconic/title set in the configuration. 
	 */
	private String getValidValueText() {

		String validValueTxt = "OK";

		if (getConfigurationValue(BaseFieldConstant.BF_VALIDVALUE_TEXT) != null) {
			validValueTxt = getConfigurationValue(
					BaseFieldConstant.BF_VALIDVALUE_TEXT).toString();
		}
		return validValueTxt;

	}
	/***********************************************/

	
	public String getBindProperty() {
		String bindProp = null;
		if(getConfigurationValue(BaseFieldConstant.BF_BINDPROP) != null) {
			bindProp = getConfigurationValue(BaseFieldConstant.BF_BINDPROP).toString();
		}
		return bindProp;
	}
	
	public boolean isFieldVisible() {
		boolean visible = true;
		if(getConfigurationValue(BaseFieldConstant.BF_VISIBLE) != null) {
			visible = (Boolean) getConfigurationValue(BaseFieldConstant.BF_VISIBLE);
		}
		return visible;
	}

	/***********************************************/
	
	
	@Override
	public void configure() {
		/** Apply Css to the spinner base container, if not configured value default css applied **/
		basePanel.setStylePrimaryName(getBaseFieldPrimCss());
		basePanel.addStyleName(getBaseFieldCss());
		basePanel.setVisible(isFieldVisible());
	}

		
	/**
	 * This would be overridden to set the error display inline to the field
	 */
	protected void setErrorInline () { }
	
	/**
	 * This would be overridden to set the error display inline to the field
	 */
	protected void setValidationMsgInline () { }
	
	/**
	 * This would be overridden to set the suggestion display inline to the field
	 */
	protected void setSuggestionInline () { }
	
	/**
	 * Method clear the inline error message.
	 */
	protected void clearInlineMsg () {
		getWidget().addStyleName(getErrorMsgCls());
		getWidget().addStyleName(getErrorIconCls());
		
		if(getErrorIconBlobId()!=null)
			setCssPropertyToElement(getWidget(), getErrorIconBlobId());
	}
	
	/**
	 * This would be overridden to set the suggestion to the field.
	 */
	protected void setSuggestion() { }
	
	/**
	 * Method set the error or sugegstion to the position provided.
	 * @param widget
	 * @param position
	 */
	protected void setErrorOrSuggestion(Widget widget,String position){
			
		if(position.equals(BaseFieldConstant.BF_BOTTOM)){
			if(bottomWidget ==null){
				bottomWidget = new HorizontalPanel();
				basePanel.add(bottomWidget,DockPanel.SOUTH);
			}
			
			bottomWidget.clear();
			bottomWidget.add(widget);
		}else if(position.equals(BaseFieldConstant.BF_TOP)){
			if(topWidget ==null){
				topWidget = new HorizontalPanel();
				basePanel.add(topWidget,DockPanel.NORTH);
			}
			
			topWidget.clear();
			topWidget.add(widget);
		}else if(position.equals(BaseFieldConstant.BF_SIDE)){
			if(sideWidget ==null){
				sideWidget = new HorizontalPanel();
				basePanel.add(sideWidget,DockPanel.EAST);
			}
			
			sideWidget.clear();
			sideWidget.add(widget);
		}
	}
	
	/**
	 * Method validates the field.
	 */
	@Override
	public boolean validate() {
		ArrayList<String> errors = getErrors(getFieldValue());
		if(errors.isEmpty()) {
			clearInvalidMarkers();
			if(isShowValidField())
				markValid();
			return true;
		}
		markInvalid(errors);
		return false;
	}
	
	private String getErrorDisplayable(ArrayList<String> errors) {
		String errorMsg = "";
		for(String error : errors) {
			errorMsg = errorMsg + error + "<br>";
		}
		return errorMsg;
	}

	/**
	 * Method used to set invalid messages.
	 */
	@Override
	public void markInvalid(ArrayList<String> errors) {
		
		Set<String> set1 = new HashSet<String>();
		set1.addAll(activeErrors);
		Set<String> set2 = new HashSet<String>();
		set2.addAll(errors);
		
		if(!set1.equals(set2)) {
			setActiveErrors(errors);
			if(getErrorPosition().equals(BaseFieldConstant.BF_ERRINLINE)) {
				setErrorInline();
			} else {
				label = new HTML();
				label.setHTML(getErrorDisplayable(errors));
				label.setStylePrimaryName(getErrorMsgCls());
				label.addStyleName(getErrorIconCls());
				
				if(getErrorIconBlobId()!=null)
					setCssPropertyToElement(label, getErrorIconBlobId());
				
				setErrorOrSuggestion(label, getErrorPosition());
			}
		}
	}
	
	/**
	 * Method set the valid message.
	 */
	@Override
	public void markValid() {
		    clearInvalidMarkers();
			if(getErrorPosition().equals(BaseFieldConstant.BF_ERRINLINE)) {
				setValidationMsgInline();
			} else {
				label = new HTML();
				label.setStylePrimaryName(getValidFieldMsgCls());
				label.addStyleName(getValidFieldIconCls());
				
				if(getValidIconBlobId()!=null)
					setCssPropertyToElement(label, getValidIconBlobId());
				
				setErrorOrSuggestion(label, getErrorPosition());
			}
		}
	
	
	protected void setCssPropertyToElement(Widget widget, String value){
		widget.getElement().getStyle().setProperty("background", "white url("+ value+") no-repeat right	center");
	}
	
	/**
	 * Removes the invalid markers
	 */
	protected void clearInvalidMarkers() {
		getActiveErrors().clear();
		clearError();
	}

	/** 
	 * Method clears the error messages.
	 */
	public void clearError(){
		
		String position = getErrorPosition();
		
		if(position.equals(BaseFieldConstant.BF_BOTTOM)){
			if(bottomWidget!=null)
				bottomWidget.clear();
		}else if(position.equals(BaseFieldConstant.BF_TOP)){
			if(topWidget!=null)
				topWidget.clear();
		}else if(position.equals(BaseFieldConstant.BF_SIDE)){
			if(sideWidget!=null)
				sideWidget.clear();
		}else{
			clearInlineMsg();
		}
		
	}
	
	@Override
	public void create() {

	}

	@Override
	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public Object getValue() {
		return value;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public ArrayList<String> getErrors(String fieldValue) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * This would allow necessary modifications before the actual value is set.
	 * @param value
	 * @return
	 */
	protected Object transformValue(Object value) {
		return null;
	}

	public ArrayList<String> getActiveErrors() {
		return activeErrors;
	}

	public void setActiveErrors(ArrayList<String> activeErrors) {
		this.activeErrors = activeErrors;
	}

	@Override
	public boolean isValid() {
		ArrayList<String> errors = getErrors(getFieldValue());
		if(errors.isEmpty()) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isDirty() {
		if(!getValue().equals(originalValue)) {
			return true;
		}
		return false;
	}

	@Override
	public void resetOriginalValue() {
		this.originalValue = getValue();
	}

	
	@Override
	public String getFieldValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFieldValue(String fieldValue) {
		// TODO Auto-generated method stub
		
	}

	protected DockPanel getBasePanel() {
		return basePanel;
	}

	public void setBasePanel(DockPanel basePanel) {
		this.basePanel = basePanel;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}
	
	public void setBindId(Long id) {
		this.bindId = id;
	}

	public Object getOriginalValue() {
		return originalValue;
	}

	public void setOriginalValue(Object originalValue) {
		this.originalValue = originalValue;
	}
	
}
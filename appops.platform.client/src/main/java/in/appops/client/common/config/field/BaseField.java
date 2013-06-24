package in.appops.client.common.config.field;

import in.appops.platform.core.shared.Configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class BaseField extends Composite implements Field {
	
	public interface BaseFieldConstant {
		/** Style class primary for field. **/
		public static final String BF_PCLS = "baseFieldPrimaryCss";
		
		/** Style class dependent for field **/
		public static final String BF_DCLS = "baseFieldDependentCss";
		
		/** A value to initialize this field with. **/
		public static final String BF_DEFVAL = "defaultValue";
		
		/** Prevents displaying error marker **/
		public static final String BF_PREVENTMARKERS = "preventInvalidMarker";
		
		/** Specifies the position of the error marker. viz. {@link BaseFieldConstant#BF_ERRSIDE}, {@link BaseFieldConstant#BF_ERRTOP}, {@link BaseFieldConstant#BF_ERRBOTTOM}
		 *  Defaults to {@link BaseFieldConstant#SP_ERRINLINE} **/
		public static final String BF_ERRPOS = "errorPosition";
		
		/** Error marker to be shown above the field. {@link BaseFieldConstant#BF_ERRPOS} **/
		public static final String BF_ERRTOP = "errorTop";
		
		/** Error marker to be shown right side of the field. {@link BaseFieldConstant#BF_ERRPOS} **/
		public static final String BF_ERRSIDE = "errorSide";
		
		/** Error marker to be shown inline of field. {@link BaseFieldConstant#BF_ERRPOS} **/
		public static final String BF_ERRINLINE = "errorInline";
		
		/** Error marker to be shown below the field. {@link BaseFieldConstant#BF_ERRPOS} **/
		public static final String BF_ERRBOTTOM = "errorBottom";

		/**
		 * Specifies whether the field be disabled.
		 * Defaults to <code>false</code>
		 */
		public static final String BF_DISABLED = "disabled";
		
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

		/** Set the fields readOnly **/
		public static final String BF_ERRMSGCLS = "errorMsgCls";

	}
	
	/**************** Properties *******************/
	
	/** The original value of the field as configured in the value configuration **/
	private Object originalValue;
	
	/** value of the field **/
	private Object value;

	/** List of current active error displayed **/
	private ArrayList<String> activeErrors;
	
	
	protected VerticalPanel basePanel;
	protected HTML errorLabel;
	protected Label fieldLabel;
	
	protected Configuration configuration;	
	
	
	public BaseField() {
		initialize();
		initWidget(basePanel);
	}
	
	/**
	 * Initializes the member variables
	 */
	protected void initialize() {
		basePanel = new VerticalPanel();
		errorLabel = new HTML();
		activeErrors = new ArrayList<String>();
	}

	
	/****************************** All Configurations methods here *********************************/
	
	/**
	 * Returns configuration is present else creates a new one
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
	 * Returns the primary style to be applied to the spinner field.
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
	 * Returns true if the invalid data errors are not to be displayed.
	 * Defaults to false.
	 * @return
	 */
	protected boolean isPreventMarkers() {
		boolean preventMarkers = false;
		if(getConfigurationValue(BaseFieldConstant.BF_PREVENTMARKERS) != null) {
			preventMarkers = (Boolean) getConfigurationValue(BaseFieldConstant.BF_PREVENTMARKERS);
		}
		return preventMarkers;
	}

	
	/**
	 * Returns the position where the errors should be displayed
	 * Defaults to {@link BaseFieldConstant#BF_ERRINLINE}
	 * Values - {@link BaseFieldConstant#BF_ERRINLINE}, {@link BaseFieldConstant#BF_ERRTOP}, {@link BaseFieldConstant#BF_ERRBOTTOM}, {@link BaseFieldConstant#BF_ERRSIDE}
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

	protected boolean isReadOnly() {
		boolean readOnly = false;
		if(getConfigurationValue(BaseFieldConstant.BF_READONLY) != null) {
			readOnly = (Boolean)getConfigurationValue(BaseFieldConstant.BF_READONLY);
		}
		return readOnly;
	}

	protected boolean isDisabled() {
		boolean disabled = false;
		if(getConfigurationValue(BaseFieldConstant.BF_DISABLED) != null) {
			disabled = (Boolean)getConfigurationValue(BaseFieldConstant.BF_DISABLED);
		}
		return disabled;
	}
	
	protected String getErrorMsgCls() {
		String errorCss = getErrorPosition() == BaseFieldConstant.BF_ERRBOTTOM || getErrorPosition() == BaseFieldConstant.BF_ERRTOP ? "appops-errorTopBottomCls" :
							getErrorPosition() == BaseFieldConstant.BF_ERRBOTTOM ? "appops-errorRightCls" : "appops-errorInvalidInline";
		if(getConfigurationValue(BaseFieldConstant.BF_ERRMSGCLS) != null) {
			errorCss = getConfigurationValue(BaseFieldConstant.BF_ERRMSGCLS).toString();
		}
		return errorCss;
	}

	/******************** End of Configuration Methods ***************************/
	
	
	@Override
	public void configure() {
		/** Apply Css to the spinner base container, if not configured value default css applied **/
		basePanel.setStylePrimaryName(getBaseFieldPrimCss());
		basePanel.addStyleName(getBaseFieldCss());
	}

	/**
	 * Added the error display to the top position
	 */
	protected void setErrorTop() {
		basePanel.remove(errorLabel);
		basePanel.insert(errorLabel, 0);
		errorLabel.setStylePrimaryName(getErrorMsgCls());
		errorLabel.setVisible(true);
	}
	
	/**
	 * Added the error display to the bottom position
	 */
	protected void setErrorBottom() {
		basePanel.remove(errorLabel);
		basePanel.add(errorLabel);
		errorLabel.setStylePrimaryName(getErrorMsgCls());
		errorLabel.setVisible(true);
	}
	
	/**
	 * This would be overridden to set the error display to the side of the field 
	 */
	protected void setErrorSide() { }
	
	/**
	 * This would be overridden to set the error display inline to the field
	 */
	protected void setErrorInline () { }

	@Override
	public boolean validate() {
		ArrayList<String> errors = getErrors(getValue());
		if(errors.isEmpty()) {
			clearInvalidMarkers();
			return true;
		}
		markInvalid(errors);
		return true;
	}
	
	private String getErrorDisplayable(ArrayList<String> errors) {
		String errorMsg = "";
		for(String error : errors) {
			errorMsg = errorMsg + error + "<br>";
		}
		return errorMsg;
	}

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
			} else if(getErrorPosition().equals(BaseFieldConstant.BF_ERRBOTTOM)) {
				errorLabel.setHTML(getErrorDisplayable(errors));
				setErrorBottom();
			} else if(getErrorPosition().equals(BaseFieldConstant.BF_ERRTOP)) {
				errorLabel.setHTML(getErrorDisplayable(errors));
				setErrorTop();
			} else if(getErrorPosition().equals(BaseFieldConstant.BF_ERRSIDE)) {
				setErrorSide();
			}
			// TODO Check how to use setError();
		}
	}
	
	/**
	 * Removes the invalid markers
	 */
	protected void clearInvalidMarkers() {
		getActiveErrors().clear();
		errorLabel.setText("");
		errorLabel.setVisible(false);
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
	public ArrayList<String> getErrors(Object fieldValue) {
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
		ArrayList<String> errors = getErrors(getValue());
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
	
}
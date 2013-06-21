package in.appops.client.common.config.field;

import in.appops.platform.core.shared.Configuration;

import java.io.Serializable;
import java.util.ArrayList;

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
		
		/** Prevents displaying error messages **/
		public static final String BF_PREVENTMARKERS = "preventInvalidMarker";
		
		/** Specifies where to place the error marker. viz. {@link BaseFieldConstant#SP_ERRSIDE}, {@link BaseFieldConstant#SP_ERRTOP}, {@link BaseFieldConstant#SP_ERRBOTTOM}
		 *  Defaults to {@link BaseFieldConstant#SP_ERRINLINE} **/
		public static final String BF_ERRPOS = "errorPosition";
		
		/** Error marker to be shown above the field. {@link BaseFieldConstant#SP_ERRPOS} **/
		public static final String BF_ERRTOP = "errorTop";
		
		/** Error marker to be shown right side of the field. {@link BaseFieldConstant#SP_ERRPOS} **/
		public static final String BF_ERRSIDE = "errorSide";
		
		/** Error marker to be shown inline of field. {@link BaseFieldConstant#SP_ERRPOS} **/
		public static final String BF_ERRINLINE = "errorInline";
		
		/** Error marker to be shown below the field. {@link BaseFieldConstant#SP_ERRPOS} **/
		public static final String BF_ERRBOTTOM = "errorBotton";

	}
	
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
	}

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

	@Override
	public void configure() {
		/** Apply Css to the spinner base container, if not configured value default css applied **/
		basePanel.setStylePrimaryName(getBaseFieldPrimCss());
		basePanel.addStyleName(getBaseFieldCss());
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
		Object defaultValue = "";
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
	
	/**
	 * Added the error display to the top position
	 */
	protected void setErrorTop() {
		basePanel.insert(errorLabel, 0);
		errorLabel.setStylePrimaryName("appops-SpinnerErrorTopBottomCls");
		errorLabel.setVisible(false);
	}
	
	/**
	 * Added the error display to the bottom position
	 */
	protected void setErrorBottom() {
		basePanel.add(errorLabel);
		errorLabel.setVisible(false);
		errorLabel.setStylePrimaryName("appops-SpinnerErrorTopBottomCls");
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
		boolean isValid = false;
		ArrayList<String> errors = getErrors(getValue());
		if(errors.isEmpty()) {
			isValid = true;
		}
		if(!isPreventMarkers()) {
			if(isValid) {
				clearInvalidMarkers();
			} else {
				markInvalid(errors);
			}
		}
		return isValid;
	}

	@Override
	public void markInvalid(ArrayList<String> errors) {
		String errorMsg = "";
		for(String error : errors) {
			errorMsg = errorMsg + error + "<br>";
		}
		if(getErrorPosition().equals(BaseFieldConstant.BF_ERRINLINE)) {
			setErrorInline();
		} else {
			if(!getErrorPosition().equals(BaseFieldConstant.BF_ERRSIDE)) {
				errorLabel.setHTML(errorMsg);
			}
			errorLabel.setVisible(true);
		}
	}
	
	/**
	 * Removes the invalid markers
	 */
	protected void clearInvalidMarkers() {
		errorLabel.setText("");
		errorLabel.setVisible(false);
	}

	
	@Override
	public void create() {

	}

	@Override
	public void setValue(Object value) {

	}

	@Override
	public Object getValue() {
		// TODO Auto-generated method stub
		return null;
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
}

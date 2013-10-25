package in.appops.client.common.config.field;

import in.appops.client.common.config.model.PropertyModel;
import in.appops.platform.core.util.AppOpsException;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Label;


/**
Field class to represent a {@link Label}
@author pallavi@ensarm.com

<p>
<h3>Configuration</h3>
<a href="LabelField.LabelFieldConstant.html">Available configurations</a>
</p>

<p>
<h3>Example</h3>

LabelField lblField = new LabelField();<br>
Configuration conf = new Configuration();<br>
conf.setPropertyByName(LabelFieldConstant.LBLFD_ISWORDWRAP, true);<br>
conf.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, "Config label");<br>
conf.setPropertyByName(LabelFieldConstant.BF_PCLS, "primaryCss");<br>
conf.setPropertyByName(LabelFieldConstant.BF_DCLS, "secondaryCss");<br>
lblField.setConfiguration(conf);<br>
lblField.configure();<br>
lblField.create();<br>

</p>*/

public class LabelField extends BaseField  {

	private Label label;
	private Logger logger = Logger.getLogger(getClass().getName());

	public LabelField() {
		label = new Label();
	}
	
	/******************************** ****************************************/
	/**
	 * creates the field UI
	 * @throws AppOpsException 
	 */
	@Override
	public void create() {
		
		try {
			logger.log(Level.INFO,"[LabelField]:: In create  method ");
			getBasePanel().add(label, DockPanel.CENTER);
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[LabelField]::Exception In create  method :"+e);
		}
	}
	
	@Override
	public void configure() {
		try {
			super.configure();
			
			if(getDefaultValue() != null) {
				setValue(getDefaultValue());
			} else if(getBindProperty() != null && !getBindProperty().toString().equals("")){
				Object value = ((PropertyModel)model).getPropertyValue(getBindProperty());
				setValue(value);
			}
			
			label.setTitle(getLblTitle());

			if (getBaseFieldPrimCss() != null)
				label.setStylePrimaryName(getBaseFieldPrimCss());
			if (getBaseFieldDependentCss() != null)
				label.addStyleName(getBaseFieldDependentCss());

			if (getBasePanelPrimCss() != null)
				getBasePanel().setStylePrimaryName(getBasePanelPrimCss());
			if (getBasePanelDependentCss() != null)
				getBasePanel().addStyleName(getBasePanelDependentCss());

			label.setVisible(isFieldVisible());
		} catch (Exception e) {
			logger.log(Level.SEVERE,
					"[LabelField]::Exception In configure  method :" + e);
		}
		
	}
	
	/**************** **********************/
	
	/**
	 * Method returns title for the label.
	 * @return
	 */
	private String getLblTitle() {
		
		String title = "";
		try {
			logger.log(Level.INFO,"[LabelField]:: In getLblTitle  method ");
			if(viewConfiguration.getConfigurationValue(LabelFieldConstant.LBLFD_TITLE) != null) {
				
				title = viewConfiguration.getConfigurationValue(LabelFieldConstant.LBLFD_TITLE).toString();
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[LabelField]::Exception In getLblTitle  method :"+e);
		}
		return title;
	}
	
	
	private String getLabelFieldCss() {
		String depCss = null;
		try {
			logger.log(Level.INFO,"[LabelField]:: In getLabelFieldCss  method ");
			if(viewConfiguration.getConfigurationValue(LabelFieldConstant.LBLFD_FCSS) != null) {
				depCss = viewConfiguration.getConfigurationValue(LabelFieldConstant.LBLFD_FCSS).toString();
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[LabelField]::Exception In getLabelFieldCss  method :"+e);
		}
		return depCss;
	}
	
	/**
	 * Method read the wordwrap value from configuration and return. Defaults to false;
	 * @return
	 */
	private Boolean isWordWrap() {
		
		Boolean isWordWrap = false;
		
		try {
			logger.log(Level.INFO,"[LabelField]:: In isWordWrap  method ");
			if(viewConfiguration.getConfigurationValue(LabelFieldConstant.LBLFD_ISWORDWRAP) != null) {
				
				isWordWrap = (Boolean) viewConfiguration.getConfigurationValue(LabelFieldConstant.LBLFD_ISWORDWRAP);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[LabelField]::Exception In isWordWrap  method :"+e);
		}
		return isWordWrap;
	}
	
	@Override
	public void setValue(Object value) {
		try {
			logger.log(Level.INFO,"[LabelField]:: In setValue  method ");
			super.setValue(value);
			label.setText(value.toString());
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[LabelField]::Exception In setValue  method :"+e);
		}
	}
	
	@Override
	public Object getValue() {
		return label.getText();
	}
	/********************************************************************************/
	
	public interface LabelFieldConstant extends BaseFieldConstant{
		
		/** Specifies if wordwrap is allowed for label or not. **/
		public static final String LBLFD_ISWORDWRAP = "isWordWrap";
		
		/** Display text for label **/
		public static final String LBLFD_DISPLAYTXT = "displayTxt";
		
		/** Title text for label **/
		public static final String LBLFD_TITLE = "title";
		
		public static final String LBLFD_FCSS = "labelfieldcss";
		
	}

}
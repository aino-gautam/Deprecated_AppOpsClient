package in.appops.client.common.config.field;

import in.appops.client.common.config.field.BaseField;
import in.appops.platform.core.util.AppOpsException;

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
	
	public LabelField() {
		label = new Label();
	}
	
	/******************************** BaseField Overriden methods ****************************************/
	/**
	 * creates the field UI
	 * @throws AppOpsException 
	 */
	@Override
	public void create() {
		
		getBasePanel().add(label, DockPanel.CENTER);
	}
	
	@Override
	public void configure() {
		
		setValue(getDisplayText());
		label.setWordWrap(isWordWrap());
		label.setTitle(getLblTitle());
		
		if(getBaseFieldPrimCss() != null)
			getBasePanel().setStylePrimaryName(getBaseFieldPrimCss());
		if(getBaseFieldCss() != null)
			getBasePanel().addStyleName(getBaseFieldCss());
		
	}
	
	/**************** Configuration method **********************/
	/**
	 * Method returns diplaytext for label.
	 * @return
	 */
	private String getDisplayText() {
		
		String displayTxt = "";
		if(getConfigurationValue(LabelFieldConstant.LBLFD_DISPLAYTXT) != null) {
			
			displayTxt = getConfigurationValue(LabelFieldConstant.LBLFD_DISPLAYTXT).toString();
		}
		return displayTxt;
	}
	
	/**
	 * Method returns title for the label.
	 * @return
	 */
	private String getLblTitle() {
		
		String title = "";
		if(getConfigurationValue(LabelFieldConstant.LBLFD_TITLE) != null) {
			
			title = getConfigurationValue(LabelFieldConstant.LBLFD_TITLE).toString();
		}
		return title;
	}
	
	/**
	 * Method read the wordwrap value from configuration and return. Defaults to false;
	 * @return
	 */
	private Boolean isWordWrap() {
		
		Boolean isWordWrap = false;
		
		if(getConfigurationValue(LabelFieldConstant.LBLFD_ISWORDWRAP) != null) {
			
			isWordWrap = (Boolean) getConfigurationValue(LabelFieldConstant.LBLFD_ISWORDWRAP);
		}
		return isWordWrap;
	}
	
	@Override
	public void setValue(Object value) {
		super.setValue(value);
		label.setText(value.toString());
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
		
	}

}
package in.appops.client.common.fields;

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
Configuration conf = new Configuration();
conf.setPropertyByName(LabelFieldConstant.LBLFIELD_WORDWRAP, true);<br>
conf.setPropertyByName(LabelFieldConstant.LBLFIELD_DISPLAYTXT, "Config label");<br>
conf.setPropertyByName(LabelFieldConstant.BF_PCLS, primaryCss);<br>
conf.setPropertyByName(LabelFieldConstant.BF_DCLS, secondaryCss);<br>
lblField.setConfiguration(conf);<br>
lblField.configure();<br>
lblField.create();<br>

</p>*/

public class LabelField extends BaseField  {

	private Label label;
	
	public LabelField() {
		label = new Label();
		
	}
	
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
		
		label.setText(getDisplayText());
		label.setWordWrap(isWordWrap());
		label.setTitle(getLblTitle());
		
		if(getBaseFieldPrimCss() != null)
			this.setStylePrimaryName(getBaseFieldPrimCss());
		if(getBaseFieldCss() != null)
			this.addStyleName(getBaseFieldCss());
		
	}
	
	/**
	 * Method returns diplaytext for label.
	 * @return
	 */
	private String getDisplayText() {
		
		String displayTxt = "";
		if(getConfigurationValue(LabelFieldConstant.LBLFIELD_DISPLAYTXT) != null) {
			
			displayTxt = getConfigurationValue(LabelFieldConstant.LBLFIELD_DISPLAYTXT).toString();
		}
		return displayTxt;
	}
	
	/**
	 * Method returns title for the label.
	 * @return
	 */
	private String getLblTitle() {
		
		String title = "";
		if(getConfigurationValue(LabelFieldConstant.LBLFIELD_TITLE) != null) {
			
			title = getConfigurationValue(LabelFieldConstant.LBLFIELD_TITLE).toString();
		}
		return title;
	}
	
	/**
	 * Method read the wordwrap value from configuration and return. Defaults to false;
	 * @return
	 */
	private Boolean isWordWrap() {
		
		Boolean isWordWrap = false;
		
		if(getConfigurationValue(LabelFieldConstant.LBLFIELD_WORDWRAP) != null) {
			
			isWordWrap = (Boolean) getConfigurationValue(LabelFieldConstant.LBLFIELD_WORDWRAP);
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
	
	public interface LabelFieldConstant extends BaseFieldConstant{
		
		public static final String LBLFIELD_WORDWRAP = "labelWordWrap";
		
		public static final String LBLFIELD_DISPLAYTXT = "displayTxt";
		
		public static final String LBLFIELD_TITLE = "title";
		
	}

}
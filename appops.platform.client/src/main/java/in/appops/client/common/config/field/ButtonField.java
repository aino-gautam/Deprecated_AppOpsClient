package in.appops.client.common.config.field;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockPanel;

public class ButtonField extends BaseField implements ClickHandler{
	
	private Button button ;
	
	public ButtonField() {
		button = new Button();
	}
	
	/******************************** BaseField Overriden methods ****************************************/
	/**
	 * creates the field UI
	 */
	@Override
	public void create(){
		
		button.addClickHandler(this);
		
		getBasePanel().add(button,DockPanel.CENTER);
	}

	/**
	 * resets the field to the original value that has been set via setFieldValue()
	 */
	@Override
	public void reset() {
		setFieldValue(getValue().toString());
	}
	

	@Override
	public void configure() {
		
		setFieldValue(getDisplayText());
		if(getBtnTitle()!=null)
			button.setTitle(getBtnTitle());
		
		if(getBaseFieldPrimCss()!= null)
			getBasePanel().setStylePrimaryName(getBaseFieldPrimCss());
		if(getBaseFieldCss() != null)
			getBasePanel().addStyleName(getBaseFieldCss());
		
	}
	
	/**
	 * clears the field .
	 */
	@Override
	public void clear() {
		setFieldValue("");
	}
	
	/**
	 * Overriden method from BaseField sets the value to button.
	 */
	@Override
	public void setValue(Object value) {
		
		super.setValue(value);
		clear();
		setFieldValue(value.toString());
		
	}
	
	/**
	 * Overriden method from BaseField returns the converted field value.
	 */
	@Override
	public Object getValue() {
		return getFieldValue();
	}
	
	
	/**
	 * Overriden method from BaseField set the text to button.
	 */
	@Override
	public void setFieldValue(String value) {
		button.setText(value);
		
	}
	
	/**
	 * Overriden method from BaseField which return button text.
	 */
	@Override
	public String getFieldValue() {
		return button.getText();
	}
	
	/**************** Configuration method **********************/
	
	/**
	 * Method return the display text of button.  
	 * @return
	 */
	private String getDisplayText() {
		String displayTxt = "";
		if (getConfigurationValue(ButtonFieldConstant.BTNFIELD_DISPLAYTEXT) != null) {
			displayTxt = getConfigurationValue(ButtonFieldConstant.BTNFIELD_DISPLAYTEXT).toString();
		}
		return displayTxt;
	}
	
	/**
	 * Method return the title text set in the configuration.  
	 * @return
	 */
	private String getBtnTitle() {
		String btnTitle = null;
		if (getConfigurationValue(ButtonFieldConstant.BTNFIELD_TITLE) != null) {
			btnTitle = getConfigurationValue(ButtonFieldConstant.BTNFIELD_TITLE).toString();
		}
		return btnTitle;
	}
	
	/**************************   *******************************/
	
	@Override
	public void onClick(ClickEvent event) {
		Window.alert(" Configurable button");
	}
	
	
	public interface ButtonFieldConstant extends BaseFieldConstant{
		
		/** Specifies display text for button **/
		public static final String BTNFIELD_DISPLAYTEXT = "displayText";
		
		/** Specifies the title to display in the tooltip  **/
		public static final String BTNFIELD_TITLE = "title";
						
	}

}

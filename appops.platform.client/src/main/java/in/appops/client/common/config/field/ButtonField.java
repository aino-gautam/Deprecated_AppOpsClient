package in.appops.client.common.config.field;

import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockPanel;

/**
* Field class to represent a {@link Button} .
* @author pallavi@ensarm.com
*
*<p>
<h3>Configuration</h3>
<a href="ButtonField.ButtonFieldConstant.html">Available configurations</a>
</p>

<p>
<h3>Example</h3>
ButtonField btnField = new ButtonField();<br>
Configuration configuration = new Configuration();<br>
configuration.setPropertyByName(ButtonFieldConstant.BTNFD_DISPLAYTEXT, "Configure");<br>
configuration.setPropertyByName(ButtonFieldConstant.BF_PCLS,"appops-Button");<br>
configuration.setPropertyByName(ButtonFieldConstant.BTNFD_TITLE, "Configurable Button");<br>
configuration.setPropertyByName(ButtonFieldConstant.BTNFD_CLICK_EVENT,FieldEvent.LOCATION_CHANGED);<br>
btnField.setConfiguration(conf);<br>
btnField.configure();<br>
btnField.create();<br>

</p>*/
public class ButtonField extends BaseField implements ClickHandler{
	
	private Button button ;
	
	public ButtonField() {
		button = new Button();
	}
	
	/******************************** ****************************************/
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
	
	/**************************************/
	
	/**
	 * Method return the display text of button.  
	 * @return
	 */
	private String getDisplayText() {
		String displayTxt = "";
		if (getConfigurationValue(ButtonFieldConstant.BTNFD_DISPLAYTEXT) != null) {
			displayTxt = getConfigurationValue(ButtonFieldConstant.BTNFD_DISPLAYTEXT).toString();
		}
		return displayTxt;
	}
	
	/**
	 * Method return the title text set in the configuration.  
	 * @return
	 */
	private String getBtnTitle() {
		String btnTitle = null;
		if (getConfigurationValue(ButtonFieldConstant.BTNFD_TITLE) != null) {
			btnTitle = getConfigurationValue(ButtonFieldConstant.BTNFD_TITLE).toString();
		}
		return btnTitle;
	}
	
	/**
	 * Method return the button field event type.  
	 * @return
	 */
	private Integer getBtnClickEvent() {
		Integer eventType = null;
		if (getConfigurationValue(ButtonFieldConstant.BTNFD_CLICK_EVENT) != null) {
			eventType = (Integer) getConfigurationValue(ButtonFieldConstant.BTNFD_CLICK_EVENT);
		}
		return eventType;
	}
	
	/**************************   *******************************/
	
	@Override
	public void onClick(ClickEvent event) {
		
		int eventType = getBtnClickEvent();
		FieldEvent fieldEvent = new FieldEvent();
		fieldEvent.setEventType(eventType);
		AppUtils.EVENT_BUS.fireEvent(fieldEvent);
		
	}
	
	
	public interface ButtonFieldConstant extends BaseFieldConstant{
		
		/** Specifies display text for button **/
		public static final String BTNFD_DISPLAYTEXT = "displayText";
		
		/** Specifies the title to display in the tooltip  **/
		public static final String BTNFD_TITLE = "title";
		
		/** Specifies the event on button click  **/
		public static final String BTNFD_CLICK_EVENT = "clickEvent";
		
	}

}

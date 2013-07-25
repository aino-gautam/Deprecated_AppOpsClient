package in.appops.client.common.config.field;

import java.util.logging.Level;
import java.util.logging.Logger;

import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
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
	private HandlerRegistration clickHandler = null ;
	private Logger logger = Logger.getLogger(getClass().getName());

	public ButtonField() {
		button = new Button();
	}
	
	/******************************** ****************************************/
	/**
	 * creates the field UI
	 */
	@Override
	public void create(){
		
		try {
			if(getBtnClickEvent()!=0)
				clickHandler = button.addClickHandler(this);
			
			getBasePanel().add(button,DockPanel.CENTER);
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[ButtonField]::Exception In create  method :"+e);
		}
	}

	/**
	 * resets the field to the original value that has been set via setFieldValue()
	 */
	@Override
	public void reset() {
		try {
			
			logger.log(Level.INFO,"[ButtonField]:: In reset  method ");
			setFieldValue(getValue().toString());
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[ButtonField]::Exception In reset  method :"+e);
		}
	}
	

	@Override
	public void configure() {
		try {
			logger.log(Level.INFO,"[ButtonField]:: In configure  method ");
			setFieldValue(getDisplayText());
			if(getBtnTitle()!=null)
				button.setTitle(getBtnTitle());
			
			if(getBaseFieldPrimCss()!= null)
				button.setStylePrimaryName(getBaseFieldPrimCss());
			if(getBaseFieldCss() != null)
				button.addStyleName(getBaseFieldCss());
			
			button.setEnabled(isEnabled());
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[ButtonField]::Exception In configure  method :"+e);
		}
		 
	}
	
	/**
	 * clears the field text.
	 */
	@Override
	public void clear() {
		
		try {
			logger.log(Level.INFO,"[ButtonField]:: In clear  method ");
			setFieldValue("");
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[ButtonField]::Exception In clear  method :"+e);
		}
	}
	
	/**
	 * Method removed registered handlers from field
	 */
	@Override
	public void removeRegisteredHandlers() {
		
		if(clickHandler!=null)
			clickHandler.removeHandler();
	}
	
	/**
	 * Overriden method from BaseField sets the value to button.
	 */
	@Override
	public void setValue(Object value) {
		try {
			super.setValue(value);
			setFieldValue(value.toString());
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[ButtonField]::Exception In setValue  method :"+e);
		}
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
		try {
			logger.log(Level.INFO,"[ButtonField]:: In getDisplayText  method ");
			if (getConfigurationValue(ButtonFieldConstant.BTNFD_DISPLAYTEXT) != null) {
				displayTxt = getConfigurationValue(ButtonFieldConstant.BTNFD_DISPLAYTEXT).toString();
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[ButtonField]::Exception In creagetDisplayTextte  method :"+e);
		}
		return displayTxt;
	}
	
	/**
	 * Method return the title text set in the configuration.  
	 * @return
	 */
	private String getBtnTitle() {
		String btnTitle = null;
		try {
			logger.log(Level.INFO,"[ButtonField]:: In getBtnTitle  method ");
			if (getConfigurationValue(ButtonFieldConstant.BTNFD_TITLE) != null) {
				btnTitle = getConfigurationValue(ButtonFieldConstant.BTNFD_TITLE).toString();
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[ButtonField]::Exception In getBtnTitle  method :"+e);
		}
		return btnTitle;
	}
	
	/**
	 * Method return the button field event type.  
	 * @return
	 */
	private Integer getBtnClickEvent() {
		Integer eventType = 0;
		try {
			logger.log(Level.INFO,"[ButtonField]:: In getBtnClickEvent  method ");
			if (getConfigurationValue(ButtonFieldConstant.BTNFD_CLICK_EVENT) != null) {
				eventType = (Integer) getConfigurationValue(ButtonFieldConstant.BTNFD_CLICK_EVENT);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[ButtonField]::Exception In getBtnClickEvent  method :"+e);
		}
		return eventType;
	}
	
	/**************************   *******************************/
	
	@Override
	public void onClick(ClickEvent event) {
		
		try {
			logger.log(Level.INFO,"[ButtonField]:: In onClick  method ");
			int eventType = getBtnClickEvent();
			FieldEvent fieldEvent = new FieldEvent();
			fieldEvent.setEventType(eventType);
			AppUtils.EVENT_BUS.fireEvent(fieldEvent);
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[ButtonField]::Exception In onClick  method :"+e);
		}
		
	}
	
	/*********************************************************************************/
	
	public interface ButtonFieldConstant extends BaseFieldConstant{
		
		/** Specifies display text for button **/
		public static final String BTNFD_DISPLAYTEXT = "displayText";
		
		/** Specifies the title to display in the tooltip  **/
		public static final String BTNFD_TITLE = "title";
		
		/** Specifies the event on button click  **/
		public static final String BTNFD_CLICK_EVENT = "clickEvent";
		
	}

}

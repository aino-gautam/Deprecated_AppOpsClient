package in.appops.client.common.config.field;

import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
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
btnField.setConfiguration(conf);<br>
btnField.configure();<br>
btnField.create();<br>

</p>*/
public class ButtonField extends BaseField implements ClickHandler, BlurHandler, KeyUpHandler{
	
	private Button button ;
	private HandlerRegistration clickHandler = null ;
	private HandlerRegistration blurHandler = null ;
	private HandlerRegistration keyUpHandler  = null;
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
			super.create();
			clickHandler = button.addClickHandler(this);
			blurHandler = button.addBlurHandler(this);
			keyUpHandler = button.addKeyUpHandler(this);
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
	public void onKeyUp(KeyUpEvent event) {
		try {
			Integer keycode= event.getNativeKeyCode();
			if(keycode.equals(KeyCodes.KEY_TAB)){
				FieldEvent fieldEvent = new FieldEvent();
				fieldEvent.setEventSource(this);
				fieldEvent.setEventData(getValue());
				fieldEvent.setEventType(FieldEvent.TAB_KEY_PRESSED);
				AppUtils.EVENT_BUS.fireEvent(fieldEvent);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[ButtonField] ::Exception In onKeyUp method "+e);
		}
	}
	

	@Override
	public void configure() {
		try {
			logger.log(Level.INFO,"[ButtonField]:: In configure  method ");
			super.configure();
			setFieldValue(getDisplayText());
			
			button.setEnabled(isEnabled());
			
			if(getBtnTitle()!=null)
				button.setTitle(getBtnTitle());
			
			if(getBaseFieldPrimCss()!= null)
				button.setStylePrimaryName(getBaseFieldPrimCss());
			if(getBaseFieldDependentCss() != null)
				button.addStyleName(getBaseFieldDependentCss());
			
			if (getBasePanelPrimCss() != null)
				getBasePanel().setStylePrimaryName(getBasePanelPrimCss());
			if (getBasePanelDependentCss() != null)
				getBasePanel().addStyleName(getBasePanelDependentCss());
			
			
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
		
		if(blurHandler!=null)
			blurHandler.removeHandler();
		
		if(keyUpHandler!=null)
			keyUpHandler.removeHandler();
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
			if (viewConfiguration.getConfigurationValue(ButtonFieldConstant.BTNFD_DISPLAYTEXT) != null) {
				displayTxt = viewConfiguration.getConfigurationValue(ButtonFieldConstant.BTNFD_DISPLAYTEXT).toString();
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
			if (viewConfiguration.getConfigurationValue(ButtonFieldConstant.BTNFD_TITLE) != null) {
				btnTitle = viewConfiguration.getConfigurationValue(ButtonFieldConstant.BTNFD_TITLE).toString();
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[ButtonField]::Exception In getBtnTitle  method :"+e);
		}
		return btnTitle;
	}
		
	/**************************   *******************************/
	
	@Override
	public void onClick(ClickEvent event) {
		try {
			logger.log(Level.INFO,"[ButtonField]:: In onClick  method ");
			FieldEvent fieldEvent = new FieldEvent();
			fieldEvent.setEventSource(this);
			fieldEvent.setEventType(FieldEvent.CLICKED);
			fireLocalEvent(fieldEvent);
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[ButtonField]::Exception In onClick  method :"+e);
		}
		
	}
	
	@Override
	public void onBlur(BlurEvent event) {
		try {
			FieldEvent fieldEvent = new FieldEvent();
			fieldEvent.setEventSource(this);
			fieldEvent.setEventData(getValue());
			fieldEvent.setEventType(FieldEvent.EDITCOMPLETED);
			AppUtils.EVENT_BUS.fireEvent(fieldEvent);
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[ButtonField]::Exception In onBlur  method :"+e);
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

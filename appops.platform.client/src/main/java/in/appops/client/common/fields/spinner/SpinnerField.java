package in.appops.client.common.fields.spinner;

import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.client.common.fields.Field;
import in.appops.platform.core.shared.Configuration;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SpinnerField extends Composite implements Field, KeyDownHandler, FieldEventHandler, MouseWheelHandler, MouseDownHandler, MouseUpHandler, MouseOutHandler{
	
	protected Configuration configuration;

	protected HorizontalPanel basePanel;
	protected TextBox textbox;
	protected VerticalPanel imagePanel;
	protected FocusPanel upArrowPanel;
	protected FocusPanel downArrowPanel;
	
	private int initialSpeed = 7;
	private boolean enabled;
	private boolean spinUp;
	
	protected Map<String, String> errorMap;
	
	protected SpinnerField(){
		initialize();
		initWidget(basePanel);
	}

	/**
	 * Instantiates the member variables
	 */
	private void initialize() {
		textbox = new TextBox();
		imagePanel = new VerticalPanel();
		upArrowPanel = new FocusPanel();
		downArrowPanel = new FocusPanel();
		basePanel = new HorizontalPanel();
		errorMap = new HashMap<String, String>();
	}
	
	@Override
	public Configuration getConfiguration() {
		return this.configuration;
	}

	@Override
	public void setConfiguration(Configuration conf) {
		this.configuration = conf;
	}

	public void configure() {
		/** Apply Css to the spinner base container, if not configured value default css applied **/
		basePanel.setStylePrimaryName(getSpinnerPrimCss());
		basePanel.addStyleName(getSpinnerDepCss());
		
		/** Apply Css to the spinner box, if not configured value default css applied **/
		textbox.setStylePrimaryName(getBoxPrimCss());
		textbox.addStyleName(getBoxDepCss());
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
	protected String getSpinnerPrimCss() {
		String primaryCss = "appops-SpinnerFieldPrimary";
		if(getConfigurationValue(SpinnerConfigurationConstant.SPINNER_PRIMARYCSS) != null) {
			primaryCss = getConfigurationValue(SpinnerConfigurationConstant.SPINNER_PRIMARYCSS).toString();
		}
		return primaryCss;
	}

	/**
	 * Returns the dependent style to be applied to the spinner field.
	 * If the style is not provided through configuration default is returned
	 * @return
	 */
	protected String getSpinnerDepCss() {
		String depCss = "appops-SpinnerFieldDependent";
		if(getConfigurationValue(SpinnerConfigurationConstant.SPINNER_DEPENDENTCSS) != null) {
			depCss = getConfigurationValue(SpinnerConfigurationConstant.SPINNER_DEPENDENTCSS).toString();
		}
		return depCss;
	}

	/**
	 * Returns the primary style to be applied to the textbox of the spinner field.
	 * If the style is not provided through configuration default is returned
	 * @return
	 */
	protected String getBoxPrimCss() {
		String primaryCss = "appops-SpinnerBoxPrimary";
		if(getConfigurationValue(SpinnerConfigurationConstant.BOX_PRIMARYCSS) != null) {
			primaryCss = getConfigurationValue(SpinnerConfigurationConstant.BOX_PRIMARYCSS).toString();
		}
		return primaryCss;
	}
	
	/**
	 * Returns the dependent style to be applied to the textbox of the spinner field.
	 * If the style is not provided through configuration default is returned
	 * @return
	 */
	protected String getBoxDepCss() {
		String dependentCss = "appops-SpinnerBoxDependent";
		if(getConfigurationValue(SpinnerConfigurationConstant.BOX_DEPENDENTCSS) != null) {
			dependentCss = getConfigurationValue(SpinnerConfigurationConstant.BOX_DEPENDENTCSS).toString();
		}
		return dependentCss;
	}
	
	/**
	 * Returns whether the spinner is to be operated in a cirular manner.
	 * If max value is reached, restarts from the min value and vice versa. Defaults to false
	 * @return
	 */
	protected boolean isCircular() {
		boolean circular = false;
		if(getConfigurationValue(SpinnerConfigurationConstant.CIRCULAR) != null) {
			circular = (Boolean)getConfigurationValue(SpinnerConfigurationConstant.CIRCULAR);
		}
		return circular;
	}


	
	@Override
	public void create() {

		if(!errorMap.isEmpty()) {
			// Iterate through map, and display all error msg
			
		} else {
			upArrowPanel.addStyleName("appops-SpinnerUpArrow");
			downArrowPanel.addStyleName("appops-SpinnerDownArrow");
			
			imagePanel.add(upArrowPanel);
			imagePanel.add(downArrowPanel);
			imagePanel.setCellVerticalAlignment(upArrowPanel, HasVerticalAlignment.ALIGN_MIDDLE);
			imagePanel.setCellVerticalAlignment(downArrowPanel, HasVerticalAlignment.ALIGN_MIDDLE);
			
			textbox.addKeyDownHandler(this);
			
			upArrowPanel.addMouseDownHandler(this);
			upArrowPanel.addMouseUpHandler(this);
			upArrowPanel.addMouseWheelHandler(this);
			upArrowPanel.addMouseOutHandler(this);
			//upArrowPanel.addBlurHandler(this);
			
			downArrowPanel.addMouseDownHandler(this);
			downArrowPanel.addMouseUpHandler(this);
			downArrowPanel.addMouseWheelHandler(this);
			downArrowPanel.addMouseOutHandler(this);
	
			upArrowPanel.addKeyDownHandler(this);
			downArrowPanel.addKeyDownHandler(this);
			
			basePanel.add(textbox);
			basePanel.add(imagePanel);
		    
		    NodeList<com.google.gwt.dom.client.Element> nodeList = basePanel.getElement().getElementsByTagName("td");
		    Node td1Node = nodeList.getItem(0);
		    Element td1Element = (Element) Element.as(td1Node);
		    td1Element.setClassName("appops-spinner-border-box");
	
		    imagePanel.setWidth("17px");
		}
	}

	/**
	 * Returns the default value to be set to the spinner textbox, which is provided through configuration.
	 * This method can be overriden to suite different types of spinners.
	 * Defaults to empty string.
	 * @return
	 */
	protected Object getDefaultValue() {
		Object defaultValue = "";
		if(getConfigurationValue(SpinnerConfigurationConstant.DEFAULT_VALUE) != null) {
			defaultValue = getConfigurationValue(SpinnerConfigurationConstant.DEFAULT_VALUE);
		}
		return defaultValue;
	}

	@Override
	public String getFieldValue() {
		return null;
	}

	@Override
	public void setFieldValue(String fieldValue) {
		//this.fieldValue = fieldValue;
	}

	@Override
	public void onFieldEvent(FieldEvent event) {

//		if(event.getEventType() == FieldEvent.EDITINITIATED) {
//			if(event.getEventData().toString().equals(SpinnerConfigurationConstant.INCREMENT)) {
//				spinUp();
//			} else {
//				spinDown();
//			}
//		}
	}

	protected Long getValue() {
		return null;
	}
	
	protected void spinUp() {

	}
	
	protected void spinDown() {
		
	}


	@Override
	public void onKeyDown(KeyDownEvent event) {
		int keyCode = event.getNativeKeyCode();
		if(keyCode == KeyCodes.KEY_UP){
			spinUp();
		} else if((keyCode == KeyCodes.KEY_DOWN)) {
			spinDown();
		}
	}


	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onMouseWheel(MouseWheelEvent event) {
		if(event.isNorth()) {
			spinUp();
		} else if(event.isSouth()) {
			spinDown();
		}
	}

	@Override
	public void onMouseDown(MouseDownEvent event) {

		if(event.getSource().equals(upArrowPanel)) {
			spinUp = true;
			spinUp();
			setEnabled(true);
		} else if(event.getSource().equals(downArrowPanel)) {
			spinUp = false;
			spinDown();
			setEnabled(true);
		}
		if(enabled) {
			timer.cancel();
			timer.scheduleRepeating(30);
		}
	}

	@Override
	public void onMouseUp(MouseUpEvent event) {
		if(enabled) {
			timer.cancel();
			setEnabled(false);
		}
	}
	
	public void setEnabled(boolean enabled) { 
		this.enabled = enabled;
		if(!enabled) {
			timer.cancel();
		}
	}
	
	private final Timer timer = new Timer() {
		private int counter = 0;
		private int speed = initialSpeed;

		@Override
		public void cancel() {
			super.cancel();
			speed = initialSpeed;
			counter = 0;
		}

		@Override
		public void run() {
			counter++;
			if (speed <= 0 || counter % speed == 0) {
				speed--;
				counter = 0;
				if (spinUp) {
					spinUp();
				} else {
					spinDown();
				}
			}

		}
	};

	@Override
	public void onMouseOut(MouseOutEvent arg0) {
		if(enabled) {
			timer.cancel();
			setEnabled(false);
		}		
	}
}

package in.appops.client.common.config.field.rangeslider;

import in.appops.client.common.fields.slider.ResizableWidget;
import in.appops.client.common.fields.slider.ResizableWidgetCollection;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ImageBundle;


public class StringRangeSlider extends FocusPanel implements ResizableWidget,ClickHandler,MouseWheelHandler{

	private double curretnValueInTxtBox;
	private double curValue;
	private Image knobImage = new Image();
	private KeyTimer keyTimer = new KeyTimer();
	private List<Element> labelElements = new ArrayList<Element>();
	private LabelFormatter labelFormatter;
	private HTMLPanel htmlPanel = null;
	private int lineLeftOffset = 0;
	private double maxValue;
	private double minValue;
	private int numLabels = 0;
	private int numTicks = 0;
	private boolean slidingKeyboard = false;
	private boolean slidingMouse = false;
	private boolean enabled = true;
	private SliderBarImages images;
	private double stepSize;
	private List<Element> tickElements = new ArrayList<Element>();
	private ArrayList<String> listOfOption = null;
	private String sliderBarLineCss = null;
	private Logger logger = Logger.getLogger(getClass().getName());

	public StringRangeSlider(double minValue, double maxValue) {
		this(minValue, maxValue, null);
	}

	public StringRangeSlider(double minValue, double maxValue, LabelFormatter labelFormatter) {
		this(minValue, maxValue, labelFormatter,(SliderBarImages) GWT.create(SliderBarImages.class));
	}

	public StringRangeSlider(double minValue, double maxValue,LabelFormatter labelFormatter, SliderBarImages images) {
		super();
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.images = images;
		this.labelFormatter = labelFormatter;
	}
	
	/**
	 * Methods creates the  string range slider UI.
	 */
	public void createUI(){
		
		try {
			logger.log(Level.INFO, "[StringRangeSlider] ::In createUI method ");
			int numLabels = (int)((getMaxValue()-getMinValue())/getStepSize());
			setNumLabels(numLabels);
			
			this.setStylePrimaryName("gwt-SliderBar-shell");
			
			htmlPanel = new HTMLPanel("");
			htmlPanel.setStylePrimaryName(getSliderBarLineCss());
			this.getElement().appendChild(htmlPanel.getElement());
			DOM.setStyleAttribute(htmlPanel.getElement(), "position", "absolute");

			images.slider().applyTo(knobImage);
			Element knobElement = knobImage.getElement();
			DOM.appendChild(getElement(), knobElement);
			DOM.setStyleAttribute(knobElement, "position", "absolute");
			DOM.setElementProperty(knobElement, "className", getSliderBarLineCss());

			sinkEvents(Event.MOUSEEVENTS | Event.KEYEVENTS | Event.FOCUSEVENTS);
			
			addClickHandler(this);
			addMouseWheelHandler(this);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[StringRangeSlider] ::Exception in createUI method :"+e);

		}
	}

	public double getTotalRange() {
		logger.log(Level.INFO, "[StringRangeSlider] ::In getTotalRange method ");
		if (minValue > maxValue) {
			return 0;
		} else {
			return maxValue - minValue;
		}
	}

	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public void onBrowserEvent(Event event) {
		try {
			super.onBrowserEvent(event);
			logger.log(Level.INFO, "[StringRangeSlider] ::In onBrowserEvent method ");
			if (enabled) {
				switch (DOM.eventGetType(event)) {
				case Event.ONBLUR:
					keyTimer.cancel();
					if (slidingMouse) {
						DOM.releaseCapture(getElement());
						slidingMouse = false;
						slideKnob(event);
						stopSliding(true, true);
					} else if (slidingKeyboard) {
						slidingKeyboard = false;
						stopSliding(true, true);
					}
					unhighlight();
					break;

				case Event.ONFOCUS:
					highlight();
					break;

				case Event.ONMOUSEWHEEL:
					int velocityY = DOM.eventGetMouseWheelVelocityY(event);
					DOM.eventPreventDefault(event);
					if (velocityY > 0)
						shiftRight(1);
					else
						shiftLeft(1);
					break;
				case Event.ONMOUSEDOWN:
					setFocus(true);
					slidingMouse = true;
					DOM.setCapture(getElement());
					startSliding(true, true);
					DOM.eventPreventDefault(event);
					slideKnob(event);
					break;
				case Event.ONMOUSEUP:
					if (slidingMouse) {
						DOM.releaseCapture(getElement());
						slidingMouse = false;
						slideKnob(event);
						stopSliding(true, true);
					}
					break;
				case Event.ONMOUSEMOVE:
					if (slidingMouse)
						slideKnob(event);
					break;
				}
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[StringRangeSlider] ::Exception in onBrowserEvent method :"+e);

		}
	}

	public void onResize(int width, int height) {
		try {
			logger.log(Level.INFO, "[StringRangeSlider] ::In onResize method ");
			int lineWidth = DOM.getElementPropertyInt(htmlPanel.getElement(), "offsetWidth");
			lineLeftOffset = (width / 2) - (lineWidth / 2);
			DOM.setStyleAttribute(htmlPanel.getElement(), "left", lineLeftOffset + "px");

			drawLabels();
			drawTicks();
			drawKnob();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[StringRangeSlider] ::Exception in onResize method :"+e);

		}
	}

	public void redraw() {
		try {
			logger.log(Level.INFO, "[StringRangeSlider] ::In redraw method ");
			if (isAttached()) {
				int width = DOM.getElementPropertyInt(getElement(), "clientWidth");
				int height = DOM.getElementPropertyInt(getElement(), "clientHeight");
				onResize(width, height);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[StringRangeSlider] ::Exception in redraw method :"+e);

		}
	}

	public void setCurrentValue(double curValue) {
		setCurrentValue(curValue, true);
	}

	public void setCurrentValue(double curValue, boolean fireEvent) {
		try {
			logger.log(Level.INFO, "[StringRangeSlider] ::In setCurrentValue method ");
			this.curValue = Math.max(minValue, Math.min(maxValue, curValue));
			double remainder = (this.curValue - minValue) % stepSize;
			this.curValue -= remainder;

			if ((remainder > (stepSize / 2)) && ((this.curValue + stepSize) <= maxValue)) {
				this.curValue += stepSize;
			}

			drawKnob();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[StringRangeSlider] ::Exception in setCurrentValue method :"+e);

		}

	}

	public void setEnabled(boolean enabled) {
		try {
			logger.log(Level.INFO, "[StringRangeSlider] ::In setEnabled method ");
			this.enabled = enabled;
			if (enabled)
				images.slider().applyTo(knobImage);
			else
				images.sliderDisabled().applyTo(knobImage);
			redraw();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[StringRangeSlider] ::Exception in setEnabled method :"+e);

		}
	}

	public void setLabelFormatter(LabelFormatter labelFormatter) {
		this.labelFormatter = labelFormatter;
	}

	public void setMaxValue(double maxValue) {
		try {
			logger.log(Level.INFO, "[StringRangeSlider] ::In setMaxValue method ");
			this.maxValue = maxValue;
			drawLabels();
			resetCurrentValue();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[StringRangeSlider] ::Exception in setMaxValue method :"+e);
		}
	}

	public void setMinValue(double minValue) {
		try {
			logger.log(Level.INFO, "[StringRangeSlider] ::In setMinValue method ");
			this.minValue = minValue;
			drawLabels();
			resetCurrentValue();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[StringRangeSlider] ::Exception in setMinValue method :"+e);
		}
	}

	public void setNumLabels(int numLabels) {
		try {
			logger.log(Level.INFO, "[StringRangeSlider] ::In setNumLabels method ");
			this.numLabels = numLabels;
			drawLabels();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[StringRangeSlider] ::Exception in setNumLabels method :"+e);
		}
	}

	public void setNumTicks(int numTicks) {
		try {
			logger.log(Level.INFO, "[StringRangeSlider] ::In setNumTicks method ");
			this.numTicks = numTicks;
			drawTicks();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[StringRangeSlider] ::Exception in setNumTicks method :"+e);
		}
	}

	public void setStepSize(double stepSize) {
		try {
			logger.log(Level.INFO, "[StringRangeSlider] ::In setStepSize method ");
			this.stepSize = stepSize;
			resetCurrentValue();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[StringRangeSlider] ::Exception in setStepSize method :"+e);
		}
	}

	public void shiftLeft(int numSteps) {
		setCurrentValue(getCurrentValue() - numSteps * stepSize);
	}

	public void shiftRight(int numSteps) {
		setCurrentValue(getCurrentValue() + numSteps * stepSize);
	}

	public double getCurrentValue() {
		return curValue;
	}

	public LabelFormatter getLabelFormatter() {
		return labelFormatter;
	}

	public double getMaxValue() {
		return maxValue;
	}

	public double getMinValue() {
		return minValue;
	}

	public int getNumLabels() {
		return numLabels;
	}

	public int getNumTicks() {
		return numTicks;
	}

	public double getStepSize() {
		return stepSize;
	}

	protected String formatLabel(double value) {
		String str = listOfOption.get(((int)value)-1).trim();
		if (labelFormatter != null)
			return labelFormatter.formatLabel(this, value);
		 else 
			return str;
	}

	public ArrayList<String> getListOfOption() {
		return listOfOption;
	}

	public void setListOfOption(ArrayList<String> listOfOption) {
		this.listOfOption = listOfOption;
	}

	protected double getKnobPercent() {
		double percent = 0;
		try {
			logger.log(Level.INFO, "[StringRangeSlider] ::In getKnobPercent method ");
			if (maxValue <= minValue)
				return 0;
			percent = (curValue - minValue) / (maxValue - minValue);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[StringRangeSlider] ::Exception in getKnobPercent method :"+e);	
		}
		return Math.max(0.0, Math.min(1.0, percent));
	}

	@Override
	protected void onLoad() {
		try {
			logger.log(Level.INFO, "[StringRangeSlider] ::In onLoad method ");
			DOM.setStyleAttribute(getElement(), "position", "relative");
			ResizableWidgetCollection.get().add(this);
			redraw();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[StringRangeSlider] ::Exception in onLoad method :"+e);
		}
	}

	@Override
	protected void onUnload() {
		try {
			logger.log(Level.INFO, "[StringRangeSlider] ::In onUnload method ");
			ResizableWidgetCollection.get().remove(this);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[StringRangeSlider] ::Exception in onUnload method :"+e);
		}
	}

	private void drawKnob() {
		try {
			logger.log(Level.INFO, "[StringRangeSlider] ::In drawKnob method ");
			if (!isAttached())
				return;
			Element knobElement = knobImage.getElement();
			int lineWidth = DOM.getElementPropertyInt(htmlPanel.getElement(), "offsetWidth");
			int knobWidth = DOM.getElementPropertyInt(knobElement, "offsetWidth");
			int knobLeftOffset = (int) (lineLeftOffset + (getKnobPercent() * lineWidth) - (knobWidth / 2));
			knobLeftOffset = Math.min(knobLeftOffset, lineLeftOffset + lineWidth - (knobWidth / 2) - 1);
			DOM.setStyleAttribute(knobElement, "left", knobLeftOffset + "px");
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[StringRangeSlider] ::Exception in drawKnob method :"+e);
		}
	}

	private void drawLabels() {
		try {
			logger.log(Level.INFO, "[StringRangeSlider] ::In drawLabels method ");
			if (!isAttached()) {
				return;
			}

			int lineWidth = DOM.getElementPropertyInt(htmlPanel.getElement(), "offsetWidth");
			if (numLabels > 0) {
				for (int i = 0; i <= numLabels; i++) {
					Element label = null;
					if (i < labelElements.size())
						label = labelElements.get(i);
					else {
						label = DOM.createDiv();
						DOM.setStyleAttribute(label, "position", "absolute");
						DOM.setStyleAttribute(label, "display", "none");
						if (enabled)
							DOM.setElementProperty(label, "className", "gwt-SliderBar-label");
						else
							DOM.setElementProperty(label, "className", "gwt-SliderBar-label-disabled");
						DOM.appendChild(getElement(), label);
						labelElements.add(label);
					}

					double value = minValue + (getTotalRange() * i / numLabels);
					DOM.setStyleAttribute(label, "visibility", "hidden");
					DOM.setStyleAttribute(label, "display", "");
					
					String lblString = formatLabel(value);
					System.out.println("in drawLabl() lable: "+lblString);
					DOM.setElementProperty(label, "innerHTML", lblString);

					DOM.setStyleAttribute(label, "left", "0px");

					int labelWidth = DOM.getElementPropertyInt(label, "offsetWidth");
					int labelLeftOffset = lineLeftOffset + (lineWidth * i / numLabels) - (labelWidth / 2);
					labelLeftOffset = Math.min(labelLeftOffset, lineLeftOffset + lineWidth	- labelWidth);
					labelLeftOffset = Math.max(labelLeftOffset, lineLeftOffset);
					DOM.setStyleAttribute(label, "left", labelLeftOffset + "px");
					DOM.setStyleAttribute(label, "visibility", "visible");
				}

				for (int i = (numLabels + 1); i < labelElements.size(); i++)
					DOM.setStyleAttribute(labelElements.get(i), "display", "none");
			} else {
				for (Element elem : labelElements)
					DOM.setStyleAttribute(elem, "display", "none");
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[StringRangeSlider] ::Exception in drawLabels method :"+e);
		}
	}

	private void drawTicks() {
		try {
			logger.log(Level.INFO, "[StringRangeSlider] ::In drawTicks method ");
			if (!isAttached())
				return;
			int lineWidth = DOM.getElementPropertyInt(htmlPanel.getElement(), "offsetWidth");
			if (numTicks > 0) {
				for (int i = 0; i <= numTicks; i++) {
					Element tick = null;
					if (i < tickElements.size()) {
						tick = tickElements.get(i);
					} else {
						tick = DOM.createDiv();
						DOM.setStyleAttribute(tick, "position", "absolute");
						DOM.setStyleAttribute(tick, "display", "none");
						DOM.appendChild(getElement(), tick);
						tickElements.add(tick);
					}
					if (enabled)
						DOM.setElementProperty(tick, "className", "gwt-SliderBar-tick");
					else
						DOM.setElementProperty(tick, "className","gwt-SliderBar-tick gwt-SliderBar-tick-disabled");

					DOM.setStyleAttribute(tick, "visibility", "hidden");
					DOM.setStyleAttribute(tick, "display", "");
					int tickWidth = DOM.getElementPropertyInt(tick, "offsetWidth");
					int tickLeftOffset = lineLeftOffset + (lineWidth * i / numTicks)- (tickWidth / 2);
					tickLeftOffset = Math.min(tickLeftOffset, lineLeftOffset + lineWidth - tickWidth);
					DOM.setStyleAttribute(tick, "left", tickLeftOffset + "px");
					DOM.setStyleAttribute(tick, "visibility", "visible");
				}

				for (int i = (numTicks + 1); i < tickElements.size(); i++)
					DOM.setStyleAttribute(tickElements.get(i), "display", "none");
			} else {
				for (Element elem : tickElements)
					DOM.setStyleAttribute(elem, "display", "none");
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[StringRangeSlider] ::Exception in drawTicks method :"+e);
		}
	}

	private void highlight() {
		try {
			logger.log(Level.INFO, "[StringRangeSlider] ::In highlight method ");
			String styleName = getStylePrimaryName();
			DOM.setElementProperty(getElement(), "className", styleName + " "+ styleName + "-focused");
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[StringRangeSlider] ::Exception in highlight method :"+e);
		}
	}

	private void resetCurrentValue() {
		try {
			logger.log(Level.INFO, "[StringRangeSlider] ::In resetCurrentValue method ");
			setCurrentValue(getCurrentValue());
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[StringRangeSlider] ::Exception in resetCurrentValue method :"+e);	
		}
	}

	private void slideKnob(Event event) {
		try {
			logger.log(Level.INFO, "[StringRangeSlider] ::In slideKnob method ");
			int x = DOM.eventGetClientX(event);
			if (x > 0) {
				int lineWidth = DOM.getElementPropertyInt(htmlPanel.getElement(), "offsetWidth");
				int lineLeft = DOM.getAbsoluteLeft(htmlPanel.getElement());
				double percent = (double) (x - lineLeft) / lineWidth * 1.0;
				setCurrentValue(getTotalRange() * percent + minValue, true);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[StringRangeSlider] ::Exception in slideKnob method :"+e);
		}
	}

	private void startSliding(boolean highlight, boolean fireEvent) {
		try {
			logger.log(Level.INFO, "[StringRangeSlider] ::In startSliding method ");
			if (highlight) {
				DOM.setElementProperty(knobImage.getElement(), "className", "gwt-SliderBar-knob gwt-SliderBar-knob-sliding");
				images.sliderSliding().applyTo(knobImage);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[StringRangeSlider] ::Exception in startSliding method :"+e);
		}
	}

	private void stopSliding(boolean unhighlight, boolean fireEvent) {
		try {
			logger.log(Level.INFO, "[StringRangeSlider] ::In stopSliding method ");
			if (unhighlight) {
				DOM.setElementProperty(knobImage.getElement(), "className","gwt-SliderBar-knob");
				images.slider().applyTo(knobImage);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[StringRangeSlider] ::Exception in stopSliding method :"+e);
		}
	}

	public HTMLPanel getHtmlPanel() {
		return htmlPanel;
	}

	public void setHtmlPanel(HTMLPanel htmlPanel) {
		this.htmlPanel = htmlPanel;
	}

	private void unhighlight() {
		DOM.setElementProperty(getElement(), "className", getStylePrimaryName());
	}


	public double getCurretnValueInTxtBox() {
		return curretnValueInTxtBox;
	}

	public void setCurretnValueInTxtBox(double curretnValueInTxtBox) {
		this.curretnValueInTxtBox = curretnValueInTxtBox;
	}


	private class KeyTimer extends Timer {
		private boolean firstRun = true;
		private int repeatDelay = 30;
		private boolean shiftRight = false;
		private int multiplier = 1;

		@Override
		public void run() {
			try {
				logger.log(Level.INFO, "[KeyTimer] ::In run method ");
				if (firstRun) {
					firstRun = false;
					startSliding(true, false);
				}

				if (shiftRight)
					setCurrentValue(curValue + multiplier * stepSize);
				else
					setCurrentValue(curValue - multiplier * stepSize);
				schedule(repeatDelay);
			} catch (Exception e) {
				logger.log(Level.SEVERE, "[KeyTimer] ::Exception in run method :"+e);
			}
		}

		public void schedule(int delayMillis, boolean shiftRight, int multiplier) {
			try {
				logger.log(Level.INFO, "[KeyTimer] ::In schedule method ");
				firstRun = true;
				this.shiftRight = shiftRight;
				this.multiplier = multiplier;
				super.schedule(delayMillis);
			} catch (Exception e) {
				logger.log(Level.SEVERE, "[KeyTimer] ::Exception in schedule method :"+e);
			}
		}
	}

	public static interface LabelFormatter {
		String formatLabel(StringRangeSlider slider, double value);
	}

	public static interface SliderBarImages extends ImageBundle {

		AbstractImagePrototype slider();

		AbstractImagePrototype sliderDisabled();

		AbstractImagePrototype sliderSliding();

		AbstractImagePrototype user();

		AbstractImagePrototype users();

	}

	@Override
	public void onMouseWheel(MouseWheelEvent event) {
		
	}

	@Override
	public void onClick(ClickEvent event) {
		
	}

	public String getSliderBarLineCss() {
		return sliderBarLineCss;
	}

	public void setSliderBarLineCss(String sliderBarLineCss) {
		this.sliderBarLineCss = sliderBarLineCss;
	}
}

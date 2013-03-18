package in.appops.client.common.fields.slider;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ImageBundle;

import java.util.ArrayList;
import java.util.List;


public class NumericRangeSlider extends FocusPanel implements ResizableWidget{

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

	public NumericRangeSlider(double minValue, double maxValue) {
		this(minValue, maxValue, null);
	}

	public NumericRangeSlider(double minValue, double maxValue, LabelFormatter labelFormatter) {
		this(minValue, maxValue, labelFormatter,(SliderBarImages) GWT.create(SliderBarImages.class));
	}

	public NumericRangeSlider(double minValue, double maxValue,LabelFormatter labelFormatter, SliderBarImages images) {
		super();
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.images = images;
		setLabelFormatter(labelFormatter);

		this.setStylePrimaryName("gwt-SliderBar-shell");
		
		htmlPanel = new HTMLPanel("");
		htmlPanel.setStylePrimaryName("gwt-SliderBar-line");
		this.getElement().appendChild(htmlPanel.getElement());
		DOM.setStyleAttribute(htmlPanel.getElement(), "position", "absolute");

		images.slider().applyTo(knobImage);
		Element knobElement = knobImage.getElement();
		DOM.appendChild(getElement(), knobElement);
		DOM.setStyleAttribute(knobElement, "position", "absolute");
		DOM.setElementProperty(knobElement, "className", "gwt-SliderBar-line");

		sinkEvents(Event.MOUSEEVENTS | Event.KEYEVENTS | Event.FOCUSEVENTS);
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

	public double getTotalRange() {
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
		super.onBrowserEvent(event);
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
	}

	public void onResize(int width, int height) {
		int lineWidth = DOM.getElementPropertyInt(htmlPanel.getElement(), "offsetWidth");
		lineLeftOffset = (width / 2) - (lineWidth / 2);
		DOM.setStyleAttribute(htmlPanel.getElement(), "left", lineLeftOffset + "px");

		drawLabels();
		drawTicks();
		drawKnob();
	}

	public void redraw() {
		if (isAttached()) {
			int width = DOM.getElementPropertyInt(getElement(), "clientWidth");
			int height = DOM.getElementPropertyInt(getElement(), "clientHeight");
			onResize(width, height);
		}
	}

	public void setCurrentValue(double curValue) {
		setCurrentValue(curValue, true);
	}

	public void setCurrentValue(double curValue, boolean fireEvent) {
		this.curValue = Math.max(minValue, Math.min(maxValue, curValue));
		double remainder = (this.curValue - minValue) % stepSize;
		this.curValue -= remainder;

		if ((remainder > (stepSize / 2)) && ((this.curValue + stepSize) <= maxValue)) {
			this.curValue += stepSize;
		}

		drawKnob();

	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		if (enabled)
			images.slider().applyTo(knobImage);
		else
			images.sliderDisabled().applyTo(knobImage);
		redraw();
	}

	public void setLabelFormatter(LabelFormatter labelFormatter) {
		this.labelFormatter = labelFormatter;
	}

	public void setMaxValue(double maxValue) {
		this.maxValue = maxValue;
		drawLabels();
		resetCurrentValue();
	}

	public void setMinValue(double minValue) {
		this.minValue = minValue;
		drawLabels();
		resetCurrentValue();
	}

	public void setNumLabels(int numLabels) {
		this.numLabels = numLabels;
		drawLabels();
	}

	public void setNumTicks(int numTicks) {
		this.numTicks = numTicks;
		drawTicks();
	}

	public void setStepSize(double stepSize) {
		this.stepSize = stepSize;
		resetCurrentValue();
	}

	public void shiftLeft(int numSteps) {
		setCurrentValue(getCurrentValue() - numSteps * stepSize);
	}

	public void shiftRight(int numSteps) {
		setCurrentValue(getCurrentValue() + numSteps * stepSize);
	}

	protected String formatLabel(double value) {
		if (labelFormatter != null) {
			return labelFormatter.formatLabel(this, value);
		} else {
			return (int) (10 * value) / 10.0 + "";
		}
	}

	protected double getKnobPercent() {
		if (maxValue <= minValue)
			return 0;
		double percent = (curValue - minValue) / (maxValue - minValue);
		return Math.max(0.0, Math.min(1.0, percent));
	}

	@Override
	protected void onLoad() {
		DOM.setStyleAttribute(getElement(), "position", "relative");
		ResizableWidgetCollection.get().add(this);
		redraw();
	}

	@Override
	protected void onUnload() {
		ResizableWidgetCollection.get().remove(this);
	}

	private void drawKnob() {
		if (!isAttached())
			return;
		Element knobElement = knobImage.getElement();
		int lineWidth = DOM.getElementPropertyInt(htmlPanel.getElement(), "offsetWidth");
		int knobWidth = DOM.getElementPropertyInt(knobElement, "offsetWidth");
		int knobLeftOffset = (int) (lineLeftOffset + (getKnobPercent() * lineWidth) - (knobWidth / 2));
		knobLeftOffset = Math.min(knobLeftOffset, lineLeftOffset + lineWidth - (knobWidth / 2) - 1);
		DOM.setStyleAttribute(knobElement, "left", knobLeftOffset + "px");
	}

	private void drawLabels() {
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
					if (enabled) {
						DOM.setElementProperty(label, "className", "gwt-SliderBar-label");
					} else {
						DOM.setElementProperty(label, "className", "gwt-SliderBar-label-disabled");
					}
					DOM.appendChild(getElement(), label);
					labelElements.add(label);
				}

				double value = minValue + (getTotalRange() * i / numLabels);
				DOM.setStyleAttribute(label, "visibility", "hidden");
				DOM.setStyleAttribute(label, "display", "");
				DOM.setElementProperty(label, "innerHTML", formatLabel(value));

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
	}

	private void drawTicks() {
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
	}

	private void highlight() {
		String styleName = getStylePrimaryName();
		DOM.setElementProperty(getElement(), "className", styleName + " "+ styleName + "-focused");
	}

	private void resetCurrentValue() {
		setCurrentValue(getCurrentValue());
	}

	private void slideKnob(Event event) {
		int x = DOM.eventGetClientX(event);
		if (x > 0) {
			int lineWidth = DOM.getElementPropertyInt(htmlPanel.getElement(), "offsetWidth");
			int lineLeft = DOM.getAbsoluteLeft(htmlPanel.getElement());
			double percent = (double) (x - lineLeft) / lineWidth * 1.0;
			setCurrentValue(getTotalRange() * percent + minValue, true);
		}
	}

	private void startSliding(boolean highlight, boolean fireEvent) {
		if (highlight) {
			//DOM.setElementProperty(htmlPanel.getElement(), "className", "gwt-SliderBar-line gwt-SliderBar-line-sliding");
			//htmlPanel.addStyleName("gwt-SliderBar-line gwt-SliderBar-line-sliding");
			DOM.setElementProperty(knobImage.getElement(), "className", "gwt-SliderBar-knob gwt-SliderBar-knob-sliding");
			images.sliderSliding().applyTo(knobImage);
		}
	}

	private void stopSliding(boolean unhighlight, boolean fireEvent) {
		if (unhighlight) {
			DOM.setElementProperty(knobImage.getElement(), "className","gwt-SliderBar-knob");
			images.slider().applyTo(knobImage);
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
			if (firstRun) {
				firstRun = false;
				startSliding(true, false);
			}

			if (shiftRight)
				setCurrentValue(curValue + multiplier * stepSize);
			else
				setCurrentValue(curValue - multiplier * stepSize);
			schedule(repeatDelay);
		}

		public void schedule(int delayMillis, boolean shiftRight, int multiplier) {
			firstRun = true;
			this.shiftRight = shiftRight;
			this.multiplier = multiplier;
			super.schedule(delayMillis);
		}
	}

	public static interface LabelFormatter {
		String formatLabel(NumericRangeSlider slider, double value);
	}

	public static interface SliderBarImages extends ImageBundle {

		AbstractImagePrototype slider();

		AbstractImagePrototype sliderDisabled();

		AbstractImagePrototype sliderSliding();

		AbstractImagePrototype user();

		AbstractImagePrototype users();

	}

}

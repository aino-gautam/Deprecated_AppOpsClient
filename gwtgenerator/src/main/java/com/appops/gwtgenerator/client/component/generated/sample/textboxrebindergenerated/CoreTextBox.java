/*
 * Copyright 2007 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.appops.gwtgenerator.client.component.generated.sample.textboxrebindergenerated;

import com.appops.gwtgenerator.client.config.annotation.Appopsify;
import com.appops.gwtgenerator.client.config.annotation.Attribute;
import com.appops.gwtgenerator.client.config.annotation.Event;
import com.appops.gwtgenerator.client.config.annotation.FiresEventSet;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.TextBoxBase;

/**
 * A standard single-line text box.
 * 
 * <p>
 * <img class='gallery' src='TextBox.png'/>
 * </p>
 * 
 * <h3>CSS Style Rules</h3>
 * <ul class='css'>
 * <li>.gwt-TextBox { primary style }</li>
 * <li>.gwt-TextBox-readonly { dependent style set when the text box is read-only }</li>
 * </ul>
 * 
 * <p>
 * <h3>Example</h3>
 * {@example com.google.gwt.examples.TextBoxExample}
 * </p>
 */
@Appopsify()
@FiresEventSet({

@Event(type = ValueChangeEvent.class), @Event(type = ClickEvent.class), @Event(type = FocusEvent.class), @Event(type = BlurEvent.class), @Event(type = DoubleClickEvent.class)

})
public class CoreTextBox extends TextBoxBase {
	
	/**
	 * Creates an empty text box.
	 */
	public CoreTextBox() {
		super(DOM.createInputText());
		setStyleName("gwt-TextBox");
	}
	
	/**
	 * Gets the maximum allowable length of the text box.
	 * 
	 * @return the maximum length, in characters
	 */
	public int getMaxLength() {
		return DOM.getElementPropertyInt(getElement(), "maxLength");
	}
	
	/**
	 * Gets the number of visible characters in the text box.
	 * 
	 * @return the number of visible characters
	 */
	public int getVisibleLength() {
		return DOM.getElementPropertyInt(getElement(), "size");
	}
	
	/**
	 * Sets the maximum allowable length of the text box.
	 * 
	 * @param length the maximum length, in characters
	 */
	@Attribute(defaultValue = "100", alternateValues = { "50", "600" })
	public void setMaxLength(int length) {
		DOM.setElementPropertyInt(getElement(), "maxLength", length);
	}
	
	/**
	 * Sets the number of visible characters in the text box.
	 * 
	 * @param length the number of visible characters
	 */
	public void setVisibleLength(int length) {
		DOM.setElementPropertyInt(getElement(), "size", length);
	}
	
	@Override
	@Attribute(defaultValue = "E-N-T-E-R text here!",  alternateValues = { "Olaa", "miya" })
	public void setText(String text) {
		super.setText(text);
	}
}
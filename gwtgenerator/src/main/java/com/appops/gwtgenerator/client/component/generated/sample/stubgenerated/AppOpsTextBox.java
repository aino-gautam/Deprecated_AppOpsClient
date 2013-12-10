package com.appops.gwtgenerator.client.component.generated.sample.stubgenerated;

import com.appops.gwtgenerator.client.component.presenter.Presenter;
import com.appops.gwtgenerator.client.config.annotation.Tag;

@Tag(tagname = "TextBox", library = "core", classname = "null")
public class AppOpsTextBox extends com.google.gwt.user.client.ui.TextBox implements com.appops.gwtgenerator.client.generator.Dynamic {
	public AppOpsTextBox() {
		super();
	}
	
	@Override
	public Object im(String methodName, Object[] parameters) throws Exception {
		if (methodName.equalsIgnoreCase("getAbsoluteLeft")) {
			return getAbsoluteLeft();
		}
		if (methodName.equalsIgnoreCase("getAbsoluteTop")) {
			return getAbsoluteTop();
		}
		if (methodName.equalsIgnoreCase("getClass")) {
			return getClass();
		}
		if (methodName.equalsIgnoreCase("getCursorPos")) {
			return getCursorPos();
		}
		if (methodName.equalsIgnoreCase("getDirection")) {
			return getDirection();
		}
		if (methodName.equalsIgnoreCase("getDirectionEstimator")) {
			return getDirectionEstimator();
		}
		if (methodName.equalsIgnoreCase("getElement")) {
			return getElement();
		}
		if (methodName.equalsIgnoreCase("getLayoutData")) {
			return getLayoutData();
		}
		if (methodName.equalsIgnoreCase("getMaxLength")) {
			return getMaxLength();
		}
		if (methodName.equalsIgnoreCase("getName")) {
			return getName();
		}
		if (methodName.equalsIgnoreCase("getOffsetHeight")) {
			return getOffsetHeight();
		}
		if (methodName.equalsIgnoreCase("getOffsetWidth")) {
			return getOffsetWidth();
		}
		if (methodName.equalsIgnoreCase("getParent")) {
			return getParent();
		}
		if (methodName.equalsIgnoreCase("getSelectedText")) {
			return getSelectedText();
		}
		if (methodName.equalsIgnoreCase("getSelectionLength")) {
			return getSelectionLength();
		}
		if (methodName.equalsIgnoreCase("getStyleName")) {
			return getStyleName();
		}
		if (methodName.equalsIgnoreCase("getStylePrimaryName")) {
			return getStylePrimaryName();
		}
		if (methodName.equalsIgnoreCase("getTabIndex")) {
			return getTabIndex();
		}
		if (methodName.equalsIgnoreCase("getText")) {
			return getText();
		}
		if (methodName.equalsIgnoreCase("getTitle")) {
			return getTitle();
		}
		if (methodName.equalsIgnoreCase("getValue")) {
			return getValue();
		}
		if (methodName.equalsIgnoreCase("getValueOrThrow")) {
			return getValueOrThrow();
		}
		if (methodName.equalsIgnoreCase("getVisibleLength")) {
			return getVisibleLength();
		}
		if (methodName.equalsIgnoreCase("isAttached")) {
			return isAttached();
		}
		if (methodName.equalsIgnoreCase("isEnabled")) {
			return isEnabled();
		}
		if (methodName.equalsIgnoreCase("isOrWasAttached")) {
			return isOrWasAttached();
		}
		if (methodName.equalsIgnoreCase("isReadOnly")) {
			return isReadOnly();
		}
		if (methodName.equalsIgnoreCase("isVisible")) {
			return isVisible();
		}
		if (methodName.equalsIgnoreCase("setAccessKey")) {
			setAccessKey((parameters[0].toString()).charAt(0));
		}
		if (methodName.equalsIgnoreCase("setAlignment")) {
			setAlignment((com.google.gwt.user.client.ui.ValueBoxBase.TextAlignment) parameters[0]);
		}
		if (methodName.equalsIgnoreCase("setCursorPos")) {
			setCursorPos(Integer.parseInt(parameters[0].toString()));
		}
		if (methodName.equalsIgnoreCase("setDirection")) {
			setDirection((com.google.gwt.i18n.client.HasDirection.Direction) parameters[0]);
		}
		if (methodName.equalsIgnoreCase("setDirectionEstimator")) {
			setDirectionEstimator(Boolean.parseBoolean(parameters[0].toString()));
		}
		if (methodName.equalsIgnoreCase("setDirectionEstimator")) {
			setDirectionEstimator((com.google.gwt.i18n.shared.DirectionEstimator) parameters[0]);
		}
		if (methodName.equalsIgnoreCase("setElement")) {
			setElement((com.google.gwt.dom.client.Element) parameters[0]);
		}
		if (methodName.equalsIgnoreCase("setElement")) {
			setElement((com.google.gwt.user.client.Element) parameters[0]);
		}
		if (methodName.equalsIgnoreCase("setEnabled")) {
			setEnabled(Boolean.parseBoolean(parameters[0].toString()));
		}
		if (methodName.equalsIgnoreCase("setFocus")) {
			setFocus(Boolean.parseBoolean(parameters[0].toString()));
		}
		if (methodName.equalsIgnoreCase("setHeight")) {
			setHeight((java.lang.String) parameters[0]);
		}
		if (methodName.equalsIgnoreCase("setKey")) {
			setKey((parameters[0].toString()).charAt(0));
		}
		if (methodName.equalsIgnoreCase("setLayoutData")) {
			setLayoutData((java.lang.Object) parameters[0]);
		}
		if (methodName.equalsIgnoreCase("setMaxLength")) {
			setMaxLength(Integer.parseInt(parameters[0].toString()));
		}
		if (methodName.equalsIgnoreCase("setName")) {
			setName((java.lang.String) parameters[0]);
		}
		
		if (methodName.equalsIgnoreCase("setPixelSize")) {
			setPixelSize(Integer.parseInt(parameters[0].toString()), Integer.parseInt(parameters[1].toString()));
		}
		if (methodName.equalsIgnoreCase("setReadOnly")) {
			setReadOnly(Boolean.parseBoolean(parameters[0].toString()));
		}
		if (methodName.equalsIgnoreCase("setSelectionRange")) {
			setSelectionRange(Integer.parseInt(parameters[0].toString()), Integer.parseInt(parameters[1].toString()));
		}
		if (methodName.equalsIgnoreCase("setSize")) {
			setSize((java.lang.String) parameters[0], (java.lang.String) parameters[1]);
		}
		if (methodName.equalsIgnoreCase("setStyleDependentName")) {
			setStyleDependentName((java.lang.String) parameters[0], Boolean.parseBoolean(parameters[1].toString()));
		}
		if (methodName.equalsIgnoreCase("setStyleName")) {
			setStyleName((java.lang.String) parameters[0]);
		}
		if (methodName.equalsIgnoreCase("setStyleName")) {
			setStyleName((java.lang.String) parameters[0], Boolean.parseBoolean(parameters[1].toString()));
		}
		if (methodName.equalsIgnoreCase("setStylePrimaryName")) {
			setStylePrimaryName((java.lang.String) parameters[0]);
		}
		if (methodName.equalsIgnoreCase("setTabIndex")) {
			setTabIndex(Integer.parseInt(parameters[0].toString()));
		}
		if (methodName.equalsIgnoreCase("setText")) {
			setText((java.lang.String) parameters[0]);
		}
		if (methodName.equalsIgnoreCase("setTextAlignment")) {
			setTextAlignment((com.google.gwt.user.client.ui.TextBoxBase.TextAlignConstant) parameters[0]);
		}
		if (methodName.equalsIgnoreCase("setTitle")) {
			setTitle((java.lang.String) parameters[0]);
		}
		if (methodName.equalsIgnoreCase("setValue")) {
			setValue((java.lang.String) parameters[0]);
		}
		if (methodName.equalsIgnoreCase("setValue")) {
			setValue((java.lang.String) parameters[0], Boolean.parseBoolean(parameters[1].toString()));
		}
		if (methodName.equalsIgnoreCase("setVisible")) {
			setVisible(Boolean.parseBoolean(parameters[0].toString()));
		}
		if (methodName.equalsIgnoreCase("setVisibleLength")) {
			setVisibleLength(Integer.parseInt(parameters[0].toString()));
		}
		if (methodName.equalsIgnoreCase("setWidth")) {
			setWidth((java.lang.String) parameters[0]);
		}
		return null;
	}
	
	public Presenter	presenter	= null;
	
	@Override
	public Presenter getPresenter() {
		return presenter;
	}
	
	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}
}

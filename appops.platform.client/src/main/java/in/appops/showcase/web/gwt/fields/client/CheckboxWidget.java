package in.appops.showcase.web.gwt.fields.client;

import in.appops.client.common.config.field.CheckboxField;
import in.appops.client.common.config.field.CheckboxField.CheckBoxFieldConstant;
import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.platform.core.shared.Configuration;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class CheckboxWidget extends Composite implements FieldEventHandler{

	private HorizontalPanel basePanel;
	private CheckboxField checkboxfield;
	private LabelField notifyLabel;
	
	public CheckboxWidget() {
		initialize();
		createUI();
		initWidget(basePanel);
		AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE, this);
	}

	private void createUI() {
		checkboxfield = new CheckboxField();
		Configuration config = getCheckboxFieldConfiguration("Allow permissions");
		checkboxfield.setFieldValue("true");
		checkboxfield.setConfiguration(config);
		checkboxfield.create();
		
		notifyLabel = new LabelField();
		Boolean value = (Boolean) checkboxfield.getValue();
		if(value) {
			notifyLabel.setFieldValue("(Selected)");
		} else {
			notifyLabel.setFieldValue("(Not selected)");
		}
		notifyLabel.setConfiguration(getLabelFieldConfiguration(true, "appops-LabelField", null, null));
		notifyLabel.create();
		notifyLabel.addStyleName("CheckboxNotifyLabelAlignment");
		
		basePanel.add(checkboxfield);
		basePanel.add(notifyLabel);
	}

	private void initialize() {
		basePanel = new HorizontalPanel(); 
	}
	
	public Configuration getCheckboxFieldConfiguration(String text) {
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(CheckBoxFieldConstant.CF_DISPLAYTEXT, text);
		return configuration;
	}
	
	private Configuration getLabelFieldConfiguration(boolean allowWordWrap, String primaryCss, String secondaryCss, String debugId){
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(LabelFieldConstant.LBLFD_ISWORDWRAP, allowWordWrap);
		configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, primaryCss);
		configuration.setPropertyByName(LabelFieldConstant.BF_DCLS, secondaryCss);
		return configuration;
	}

	@Override
	public void onFieldEvent(FieldEvent event) {
		int eventType = event.getEventType();
		switch (eventType) {
		case FieldEvent.CHECKBOX_SELECT: {
			CheckBox checkbox = (CheckBox) event.getEventData();
			if(checkbox.equals(checkboxfield)) {
				notifyLabel.setFieldValue("(Selected)");
				notifyLabel.reset();
			}
			break;
		}
		case FieldEvent.CHECKBOX_DESELECT: {
			CheckBox checkbox = (CheckBox) event.getEventData();
			if(checkbox.equals(checkboxfield)) {
				notifyLabel.setFieldValue("(Not selected)");
				notifyLabel.reset();
			}
			break;
		}
		default:
			break;
		}
	}
}

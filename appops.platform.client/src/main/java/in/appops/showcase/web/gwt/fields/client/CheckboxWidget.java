package in.appops.showcase.web.gwt.fields.client;

import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.CheckBoxSelectEvent;
import in.appops.client.common.event.handlers.CheckBoxSelectEventHandler;
import in.appops.client.common.fields.CheckboxField;
import in.appops.client.common.fields.LabelField;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class CheckboxWidget extends Composite implements CheckBoxSelectEventHandler{

	private HorizontalPanel basePanel;
	private CheckboxField checkboxfield;
	private LabelField notifyLabel;
	
	public CheckboxWidget() {
		initialize();
		createUI();
		initWidget(basePanel);
		AppUtils.EVENT_BUS.addHandler(CheckBoxSelectEvent.TYPE, this);
	}

	private void createUI() {
		try {
			checkboxfield = new CheckboxField();
			Configuration config = getCheckboxFieldConfiguration("Allow permissions");
			checkboxfield.setFieldValue("true");
			checkboxfield.setConfiguration(config);
			checkboxfield.createField();
			
			notifyLabel = new LabelField();
			boolean value = checkboxfield.getValue();
			if(value) {
				notifyLabel.setFieldValue("(Selected)");
			} else {
				notifyLabel.setFieldValue("(Not selected)");
			}
			notifyLabel.setConfiguration(getLabelFieldConfiguration(true, "appops-LabelField", null, null));
			notifyLabel.createField();
			notifyLabel.addStyleName("CheckboxNotifyLabelAlignment");
			
			basePanel.add(checkboxfield);
			basePanel.add(notifyLabel);
		} catch (AppOpsException e) {
			e.printStackTrace();
		}
	}

	private void initialize() {
		basePanel = new HorizontalPanel(); 
	}
	
	public Configuration getCheckboxFieldConfiguration(String text) {
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(CheckboxField.CHECKBOXFIELD_DISPLAYTEXT, text);
		return configuration;
	}
	
	private Configuration getLabelFieldConfiguration(boolean allowWordWrap, String primaryCss, String secondaryCss, String debugId){
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(LabelField.LABELFIELD_WORDWRAP, allowWordWrap);
		configuration.setPropertyByName(LabelField.LABELFIELD_PRIMARYCSS, primaryCss);
		configuration.setPropertyByName(LabelField.LABELFIELD_DEPENDENTCSS, secondaryCss);
		configuration.setPropertyByName(LabelField.LABELFIELD_DEBUGID, debugId);
		return configuration;
	}

	@Override
	public void onSelect(CheckBoxSelectEvent event) {
		int eventType = event.getEventType();
		switch (eventType) {
		case CheckBoxSelectEvent.SELECTED: {
			CheckBox checkbox = (CheckBox) event.getEventData();
			if(checkbox.equals(checkboxfield)) {
				notifyLabel.setFieldValue("(Selected)");
				notifyLabel.resetField();
			}
			break;
		}
		case CheckBoxSelectEvent.DESELECTED: {
			CheckBox checkbox = (CheckBox) event.getEventData();
			if(checkbox.equals(checkboxfield)) {
				notifyLabel.setFieldValue("(Not selected)");
				notifyLabel.resetField();
			}
			break;
		}
		default:
			break;
		}
	}
}
package in.appops.showcase.web.gwt.fields.client;

import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.ValueChangeEvent;
import in.appops.client.common.event.handlers.ValueChangeEventHandler;
import in.appops.client.common.fields.LabelField;
import in.appops.client.common.fields.SpinnerField;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SpinnerWidget extends Composite implements ValueChangeEventHandler{

	private VerticalPanel basePanel;
	private SpinnerField spinnerField;
	private LabelField spinnerFieldValueLabel;
	private SpinnerField percentSpinnerField;
	private LabelField spinnerPercentValueLabel;
	
	public SpinnerWidget() {
		initialize();
		initWidget(basePanel);
		AppUtils.EVENT_BUS.addHandler(ValueChangeEvent.TYPE, this);
	}

	private void initialize() {
		basePanel = new VerticalPanel();
	}
	
	public void createValueSpinner() {
		try {
			spinnerField = new SpinnerField();
			spinnerField.setFieldValue("3");
			Configuration spinnerConfig = getSpinnerFieldConfiguration(SpinnerField.SPINNERFIELD_VALUESPINNER);
			spinnerField.setConfiguration(spinnerConfig);

			spinnerFieldValueLabel = new LabelField();
			spinnerFieldValueLabel.setConfiguration(getLabelFieldConfiguration(true, "appops-LabelField", null, null));
			spinnerFieldValueLabel.addStyleName("spinnerFieldValueLabel");
			basePanel.add(spinnerField);
			basePanel.add(spinnerFieldValueLabel);
			
			spinnerField.createField();
			spinnerFieldValueLabel.createField();
			spinnerFieldValueLabel.setFieldValue("Value set is: " + spinnerField.getValue());
			spinnerFieldValueLabel.resetField();
		} catch (AppOpsException e) {
			e.printStackTrace();
		}
	}
	
	public void createPercentSpinner() {
		try {
			percentSpinnerField = new SpinnerField();
			percentSpinnerField.setFieldValue("3");
			Configuration percentSpinnerConfig = getSpinnerFieldConfiguration(SpinnerField.SPINNERFIELD_PERCENTSPINNER);
			percentSpinnerField.setConfiguration(percentSpinnerConfig);
			
			spinnerPercentValueLabel = new LabelField();
			spinnerPercentValueLabel.setConfiguration(getLabelFieldConfiguration(true, "appops-LabelField", null, null));
			spinnerPercentValueLabel.addStyleName("spinnerFieldValueLabel");
			basePanel.add(percentSpinnerField);
			basePanel.add(spinnerPercentValueLabel);
			
			percentSpinnerField.createField();
			spinnerPercentValueLabel.createField();
			spinnerPercentValueLabel.setFieldValue("Value set is: " + percentSpinnerField.getValue() + "%");
			spinnerPercentValueLabel.resetField();
		} catch (AppOpsException e) {
			e.printStackTrace();
		}
	}
	
	protected Configuration getSpinnerFieldConfiguration(String spinnerfieldMode) {
		Configuration config = new Configuration();
		config.setPropertyByName(SpinnerField.SPINNERFIELDMODE, spinnerfieldMode);
		return config;
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
	public void onValueChange(ValueChangeEvent event) {
		int eventType = event.getEventType();
		switch (eventType) {
		case ValueChangeEvent.VALUECHANGED: {
			if(spinnerField != null && spinnerFieldValueLabel !=null) {
				spinnerFieldValueLabel.setFieldValue("Value set is: " + spinnerField.getValue());
				spinnerFieldValueLabel.resetField();
			}
			if(percentSpinnerField != null && spinnerPercentValueLabel !=null) {
				spinnerPercentValueLabel.setFieldValue("Value set is: " + percentSpinnerField.getValue() + "%");
				spinnerPercentValueLabel.resetField();
			}
			break;
		}
		default:
			break;
		}
	}
}

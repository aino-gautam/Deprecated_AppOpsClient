/*package in.appops.showcase.web.gwt.fields.client;

import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.client.common.config.fields.LabelField;
import in.appops.client.common.fields.SpinnerField;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SpinnerWidget extends Composite implements FieldEventHandler, ClickHandler{

	private VerticalPanel basePanel;
	private SpinnerField spinnerField;
	private LabelField spinnerFieldValueLabel;
	private SpinnerField percentSpinnerField;
	private LabelField spinnerPercentValueLabel;
	private ListBox modeListbox;
	private String valueSpinnerMode = "ValueSpinner";
	private String percentSpinnerMode = "PercentSpinner";
	private Button preview;
	
	public SpinnerWidget() {
		initialize();
		createUI();
		initWidget(basePanel);
		AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE, this);
	}

	private void initialize() {
		basePanel = new VerticalPanel();
		modeListbox = new ListBox();
		preview = new Button("Preview");
	}
	
	public void createUI() {
		try {
			HorizontalPanel selectModePanel = new HorizontalPanel();
			
			LabelField modeLabel = new LabelField();
			modeLabel.setFieldValue("Select mode");
			modeLabel.setConfiguration(getLabelFieldConfiguration(true, "groupCheckboxTitle", null, null));
			modeLabel.create();
			selectModePanel.add(modeLabel);
			
			modeListbox.addItem("--Select--");
			modeListbox.addItem(valueSpinnerMode);
			modeListbox.addItem(percentSpinnerMode);
			selectModePanel.add(modeListbox);
			modeListbox.setStylePrimaryName("selectModeListbox");
			basePanel.add(selectModePanel);
			selectModePanel.setWidth("100%");
			
			basePanel.add(preview);
			preview.setStylePrimaryName("appops-Button");
			preview.addStyleName("previewButton");
			preview.addClickHandler(this);
		} catch (AppOpsException e) {
			e.printStackTrace();
		}
	}
	
	public void createValueSpinner() {
		try {
			basePanel.clear();
			spinnerField = new SpinnerField();
			spinnerField.setFieldValue("3");
			Configuration spinnerConfig = getSpinnerFieldConfiguration(SpinnerField.SPINNERFIELD_VALUESPINNER);
			spinnerField.setConfiguration(spinnerConfig);

			spinnerFieldValueLabel = new LabelField();
			spinnerFieldValueLabel.setConfiguration(getLabelFieldConfiguration(true, "appops-LabelField", null, null));
			spinnerFieldValueLabel.addStyleName("spinnerFieldValueLabel");
			basePanel.add(spinnerField);
			basePanel.add(spinnerFieldValueLabel);
			
			spinnerField.create();
			spinnerFieldValueLabel.create();
			spinnerFieldValueLabel.setFieldValue("Value set is: " + spinnerField.getValue());
			spinnerFieldValueLabel.reset();
		} catch (AppOpsException e) {
			e.printStackTrace();
		}
	}
	
	public void createPercentSpinner() {
		try {
			basePanel.clear();
			percentSpinnerField = new SpinnerField();
			percentSpinnerField.setFieldValue("3");
			Configuration percentSpinnerConfig = getSpinnerFieldConfiguration(SpinnerField.SPINNERFIELD_PERCENTSPINNER);
			percentSpinnerField.setConfiguration(percentSpinnerConfig);
			
			spinnerPercentValueLabel = new LabelField();
			spinnerPercentValueLabel.setConfiguration(getLabelFieldConfiguration(true, "appops-LabelField", null, null));
			spinnerPercentValueLabel.addStyleName("spinnerFieldValueLabel");
			basePanel.add(percentSpinnerField);
			basePanel.add(spinnerPercentValueLabel);
			
			percentSpinnerField.create();
			spinnerPercentValueLabel.create();
			spinnerPercentValueLabel.setFieldValue("Value set is: " + percentSpinnerField.getValue() + "%");
			spinnerPercentValueLabel.reset();
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
	public void onClick(ClickEvent event) {
		if(event.getSource().equals(preview)) {
			int index = modeListbox.getSelectedIndex();
			String mode = modeListbox.getValue(index);
			if(index > 0) {
				if(mode.equals(valueSpinnerMode)) {
					createValueSpinner();
				} else if(mode.equals(percentSpinnerMode)) {
					createPercentSpinner();
				}
			}
		}
	}

	@Override
	public void onFieldEvent(FieldEvent event) {
		int eventType = event.getEventType();
		switch (eventType) {
		case FieldEvent.VALUECHANGED: {
			if(spinnerField != null && spinnerFieldValueLabel !=null) {
				spinnerFieldValueLabel.setFieldValue("Value set is: " + spinnerField.getValue());
				spinnerFieldValueLabel.reset();
			}
			if(percentSpinnerField != null && spinnerPercentValueLabel !=null) {
				spinnerPercentValueLabel.setFieldValue("Value set is: " + percentSpinnerField.getValue() + "%");
				spinnerPercentValueLabel.reset();
			}
			break;
		}
		default:
			break;
		}
	}
}
*/
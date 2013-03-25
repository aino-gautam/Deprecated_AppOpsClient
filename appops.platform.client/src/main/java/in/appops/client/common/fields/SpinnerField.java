package in.appops.client.common.fields;

import in.appops.client.common.event.FieldEvent;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SpinnerField extends Composite implements Field, ClickHandler{
	
	private Configuration configuration;
	private String fieldValue;
	private String fieldMode;
	private HorizontalPanel basePanel;
	private TextBox textbox;
	private VerticalPanel imagePanel;
	private FocusPanel upArrowPanel;
	private FocusPanel downArrowPanel;
	
	public static final String SPINNERFIELDMODE = "spinnerFieldMode";
	public static final String SPINNERFIELD_VALUESPINNER = "spinnerFieldMode";
	
	public SpinnerField(){
		basePanel = new HorizontalPanel();
		initWidget(basePanel);
	}
	
	@Override
	public Configuration getConfiguration() {
		return this.configuration;
	}

	@Override
	public void setConfiguration(Configuration conf) {
		this.configuration = conf;
	}

	@Override
	public void createField() throws AppOpsException {
		
		if(getConfiguration() == null)
			throw new AppOpsException("SpinnerField configuration unavailable");
		
		if(getConfiguration().getPropertyByName(SPINNERFIELDMODE) != null)
			fieldMode = getConfiguration().getPropertyByName(SPINNERFIELDMODE).toString();
			
		if(fieldMode.equals(SPINNERFIELD_VALUESPINNER)) {
			
			textbox = new TextBox();
			imagePanel = new VerticalPanel();
			
			if(getFieldValue() != null) {
				textbox.setText(getFieldValue());
			} else {
				textbox.setText("0");
			}
			
			upArrowPanel = new FocusPanel();
			upArrowPanel.setStylePrimaryName("spinnerUpArrow");
			downArrowPanel = new FocusPanel();
			downArrowPanel.setStylePrimaryName("spinnerDownArrow");
			imagePanel.add(upArrowPanel);
			imagePanel.add(downArrowPanel);
			
			upArrowPanel.addClickHandler(this);
			downArrowPanel.addClickHandler(this);
			
			basePanel.add(textbox);
			basePanel.add(imagePanel);
			basePanel.setCellVerticalAlignment(imagePanel, HasVerticalAlignment.ALIGN_MIDDLE);
		}
	}

	@Override
	public void clearField() {
		if(fieldMode.equals(SPINNERFIELD_VALUESPINNER)) {
			textbox.setText("0");
		}
	}

	@Override
	public void resetField() {
		if(fieldMode.equals(SPINNERFIELD_VALUESPINNER)) {
			textbox.setText(getFieldValue());
		}
	}

	@Override
	public String getFieldValue() {
		return this.fieldValue;
	}

	@Override
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	@Override
	public void onFieldEvent(FieldEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(ClickEvent event) {
		if(event.getSource().equals(upArrowPanel)) {
			incrementCount();
		} else if(event.getSource().equals(downArrowPanel)) {
			decrementCount();
		}
	}

	private void incrementCount() {
		int currentCount = Integer.valueOf(textbox.getText());
		currentCount = currentCount + 1;
		textbox.setText(String.valueOf(currentCount));
	}

	private void decrementCount() {
		int currentCount = Integer.valueOf(textbox.getText());
		if(currentCount != 0) {
			currentCount = currentCount - 1;
			textbox.setText(String.valueOf(currentCount));
		}
	}
}

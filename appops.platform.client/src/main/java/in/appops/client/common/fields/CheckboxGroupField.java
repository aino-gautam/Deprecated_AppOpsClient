package in.appops.client.common.fields;

import in.appops.client.common.event.FieldEvent;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CheckboxGroupField extends Composite implements Field{

	private Configuration configuration;
	private String fieldValue;
	private String checkboxSelectMode;
	private VerticalPanel verticalBasePanel;
	private HorizontalPanel horizontalBasePanel;
	private String checkboxBasepanel;
	
	public static final String CHECKBOX_SELECT_MODE = "checkboxSelectMode";
	public static final String CHECKBOX_SINGLESELECT = "checkboxSingleSelect";
	public static final String CHECKBOX_MULTISELECT = "checkboxMultiSelect";
	public static final String CHECKBOX_BASEPANEL = "checkboxBasepanel";
	public static final String CHECKBOX_VERTICALBASEPANEL = "checkboxVerticalBasepanel";
	public static final String CHECKBOX_HORIZONTALBASEPANEL = "checkboxHorizontalBasepanel";
	
	public CheckboxGroupField(){
		
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
			throw new AppOpsException("Statefield configuration unavailable");
		
		if(getConfiguration().getPropertyByName(CHECKBOX_SELECT_MODE) != null) 
			checkboxSelectMode = getConfiguration().getPropertyByName(CHECKBOX_SELECT_MODE).toString();
		
		if(getConfiguration().getPropertyByName(CHECKBOX_BASEPANEL) != null)
			checkboxBasepanel = getConfiguration().getPropertyByName(CHECKBOX_BASEPANEL).toString();
		
		if(checkboxBasepanel != null) {
			if(checkboxBasepanel.equals(CHECKBOX_HORIZONTALBASEPANEL)) {
				horizontalBasePanel = new HorizontalPanel();
				initWidget(horizontalBasePanel);
			} else if(checkboxBasepanel.equals(CHECKBOX_VERTICALBASEPANEL)) {
				verticalBasePanel = new VerticalPanel();
				initWidget(verticalBasePanel);
			}
		}
	}

	@Override
	public void clearField() {
		if(checkboxSelectMode.equals(CHECKBOX_SINGLESELECT)) {
			
		}else if(checkboxSelectMode.equals(CHECKBOX_MULTISELECT)) {
			
		}
	}

	@Override
	public void resetField() {
		if(checkboxSelectMode.equals(CHECKBOX_SINGLESELECT)) {
			
		}else if(checkboxSelectMode.equals(CHECKBOX_MULTISELECT)) {
			
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
	
	public void addCheckItem(String value) {
		
		if(checkboxSelectMode.equals(CHECKBOX_SINGLESELECT)) {
			
			RadioButton radioButton = new RadioButton("singleSelection");
			radioButton.setText(value);
			if(checkboxBasepanel.equals(CHECKBOX_HORIZONTALBASEPANEL)) {
				horizontalBasePanel.add(radioButton);
			} else if(checkboxBasepanel.equals(CHECKBOX_VERTICALBASEPANEL)) {
				verticalBasePanel.add(radioButton);
			}
		}else if(checkboxSelectMode.equals(CHECKBOX_MULTISELECT)) {
			
			CheckBox checkBox = new CheckBox();
			checkBox.setText(value);
			if(checkboxBasepanel.equals(CHECKBOX_HORIZONTALBASEPANEL)) {
				horizontalBasePanel.add(checkBox);
			} else if(checkboxBasepanel.equals(CHECKBOX_VERTICALBASEPANEL)) {
				verticalBasePanel.add(checkBox);
			}
		}
	}
	
}
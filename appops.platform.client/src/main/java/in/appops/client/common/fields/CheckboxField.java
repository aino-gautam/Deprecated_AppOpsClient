package in.appops.client.common.fields;

import in.appops.client.common.event.FieldEvent;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;

import com.google.gwt.user.client.ui.CheckBox;

public class CheckboxField extends CheckBox implements Field{

	private Configuration configuration;
	private String fieldValue;
	
	public static final String CHECKBOXFIELD_DISPLAYTEXT = "checkBoxFieldDisplayText";
	public static final String CHECKBOXFIELD_PRIMARYCSS = "textFieldPrimaryCss";
	public static final String CHECKBOXFIELD_DEPENDENTCSS = "textFieldDependentCss";
	
	public CheckboxField(){
		super();
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
			throw new AppOpsException("CheckBox configuration unavailable");
		
		this.setValue(Boolean.valueOf(getFieldValue()));
			
		if(getConfiguration().getPropertyByName(CHECKBOXFIELD_DISPLAYTEXT) != null)
			this.setText(getConfiguration().getPropertyByName(CHECKBOXFIELD_DISPLAYTEXT).toString());
		
		if(getConfiguration().getPropertyByName(CHECKBOXFIELD_PRIMARYCSS) != null)
			this.setStylePrimaryName(getConfiguration().getPropertyByName(CHECKBOXFIELD_PRIMARYCSS).toString());
		if(getConfiguration().getPropertyByName(CHECKBOXFIELD_DEPENDENTCSS) != null)
			this.addStyleName(getConfiguration().getPropertyByName(CHECKBOXFIELD_DEPENDENTCSS).toString());
	}

	@Override
	public void clearField() {
		this.setValue(false);
	}

	@Override
	public void resetField() {
		this.setValue(Boolean.valueOf(getFieldValue()));
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
}
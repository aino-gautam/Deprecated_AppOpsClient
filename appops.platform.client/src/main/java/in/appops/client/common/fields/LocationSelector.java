package in.appops.client.common.fields;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;

import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.platform.core.shared.Configuration;

public class LocationSelector extends Composite implements Field {

	private Configuration configuration;
	private String fieldValue;
	private TextBox textBox;
	
	public LocationSelector(){
		
	}
	
	@Override
	public void createField() {
		// TODO will need a map + textbox to enter a location

	}
	
	@Override
	public void clearField() {
		textBox.setText("");
	}

	@Override
	public void resetField() {
		textBox.setText(getFieldValue());
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

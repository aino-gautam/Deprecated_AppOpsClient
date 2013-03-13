package in.appops.client.common.fields;

import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;

public class DateTimeField extends Composite implements Field{

	private Configuration configuration;
	private String fieldValue;
	
	public static final String DATETIMEFIELD_DATEONLY = "dateTimeFieldDateOnly";
	public static final String DATETIMEFIELD_DATEFORMAT = "dateTimeFieldDateFormat";
	
			
	public DateTimeField(){
		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearField() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resetField() {
		// TODO Auto-generated method stub
		
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

package in.appops.client.common.fields;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.GwtEvent.Type;

import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.platform.core.shared.Configurable;
import in.appops.platform.core.util.AppOpsException;

public interface Field extends Configurable {
	
	public void createField() throws AppOpsException;
	
	public void clearField();
	
	public void resetField();
	
	public String getFieldValue();
	
	public void setFieldValue(String fieldValue);
	
	public HandlerRegistration addFieldHandler(FieldEventHandler handler, Type<FieldEventHandler> type);
	
}

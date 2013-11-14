package in.appops.client.common.fields;

import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.platform.core.shared.Configurable;
import in.appops.platform.core.util.AppOpsException;

public interface Field extends Configurable, FieldEventHandler{
	
	public void create() throws AppOpsException;
	
	public void clear();
	
	public void reset();
	
	public void configure();
	
	public String getFieldValue();
	
	public void setFieldValue(String fieldValue);
}

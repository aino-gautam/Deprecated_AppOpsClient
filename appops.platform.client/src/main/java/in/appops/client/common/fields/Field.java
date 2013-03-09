package in.appops.client.common.fields;

import in.appops.platform.core.shared.Configurable;
import in.appops.platform.core.util.AppOpsException;

public interface Field extends Configurable {
	
	public void createField() throws AppOpsException;
	
	public void clearField();
	
	public void resetField();
	
	public String getFieldValue();
	
	public void setFieldValue(String fieldValue);
	
}

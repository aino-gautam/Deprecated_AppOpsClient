package in.appops.client.common.config.field.spinner;

public interface SpinnerModel {
	
	void setValue(Object value);
	
	Object getValue();
	
	void spinUp();
	
	void spinDown();
	
	void setCircular(boolean circular);
	
	boolean isCircular();
}

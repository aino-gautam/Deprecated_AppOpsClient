package in.appops.client.common.components;

/**
 * @author nitish@ensarm.com
 */
public interface IActionLabel {
	int WIDGET = 1;
	int OPERATION = 2;
	int QUERY = 3;
	
	int getBindType();
	void setBindType(int bindType);
	
	String getBindValue();
	void setBindValue(String bindValue);
}

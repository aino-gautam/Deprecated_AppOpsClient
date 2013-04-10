package in.appops.client.common.components;

/**
 * @author nitish@ensarm.com
 */
public interface ActionLabel {
	public static int WIDGET = 1;
	public static int OPERATION = 2;
	public static int QUERY = 3;
	
	public int getBindType();
	public void setBindType(int bindType);
	
	public String getBindValue();
	public void setBindValue(String bindValue);
}

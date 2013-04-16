package in.appops.client.common.components;

import in.appops.platform.core.entity.Entity;

/**
 * @author nitish@ensarm.com
 */
public interface IActionLabel {
	int WIDGET = 1;
	int OPERATION = 2;
	int QUERY = 3;
	
	int getBindType();
	void setBindType(int bindType);
	
	Entity getBindValue();
	void setBindValue(Entity bindValue);
}

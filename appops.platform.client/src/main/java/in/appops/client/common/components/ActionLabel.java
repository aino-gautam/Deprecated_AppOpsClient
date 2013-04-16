package in.appops.client.common.components;

import in.appops.platform.core.entity.Entity;

import com.google.gwt.user.client.ui.Label;

/**
 * 
 * @author nitish@ensarm.com
 * TODO - This will enhanced/changed as required.
 */
public class ActionLabel extends Label implements IActionLabel{
	private int bindType;
	private Entity bindValue;
	
	public static final int WIDGET = 1;
	public static final int OPERATION = 2;
	public static final int QUERY = 3;
	
//	public ActionLabelImpl(){ }
	
	public ActionLabel(int bindType, Entity bindValue){
		this.bindType = bindType;
		this.bindValue = bindValue;
	}

	@Override
	public int getBindType() {
		return bindType;
	}

	@Override
	public void setBindType(int bindType) {
		this.bindType = bindType;
	}

	@Override
	public Entity getBindValue() {
		return bindValue;
	}

	@Override
	public void setBindValue(Entity bindValue) {
		this.bindValue = bindValue;
	}
	
}

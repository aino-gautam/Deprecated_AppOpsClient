package in.appops.client.common.config.field.spinner;

import java.util.ArrayList;

public class ListModel implements SpinnerModel {
	private ArrayList valueList;
	private Object value;
	private int currentIndex;
	private boolean circular;
	
	
	@Override
	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public Object getValue() {
		value = valueList.get(currentIndex);
		return value;
	}

	@Override
	public void spinUp() {
		if(getCurrentIndex() + 1 < getValueList().size()) {
			setCurrentIndex(getCurrentIndex() + 1);
		} else {
			if(isCircular()) {
				setCurrentIndex(0);
			} 
		}
		value = valueList.get(getCurrentIndex());
	}

	@Override
	public void spinDown() {
		if(getCurrentIndex() - 1 >= 0) {
			setCurrentIndex(getCurrentIndex() - 1);
		} else {
			if(isCircular()) {
				setCurrentIndex(getValueList().size() - 1);
			} 
		}
		value = valueList.get(getCurrentIndex());
	}

	@Override
	public void setCircular(boolean circular) {
		this.circular = circular;
	}

	@Override
	public boolean isCircular() {
		return circular;
	}

	public ArrayList getValueList() {
		return valueList;
	}

	public void setValueList(ArrayList valueList) {
		this.valueList = valueList;
	}

	public int getCurrentIndex() {
		return currentIndex;
	}

	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
		
	}

}

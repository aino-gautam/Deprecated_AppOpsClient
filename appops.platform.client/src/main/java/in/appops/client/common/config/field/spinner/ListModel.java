package in.appops.client.common.config.field.spinner;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ListModel implements SpinnerModel {
	private ArrayList valueList;
	private Object value;
	private int currentIndex;
	private boolean circular;
	private Logger logger = Logger.getLogger(getClass().getName());
	
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
		try {
			logger.log(Level.INFO, "[ListModel] ::In spinUp method ");
			if(getCurrentIndex() + 1 < getValueList().size()) {
				setCurrentIndex(getCurrentIndex() + 1);
			} else {
				if(isCircular()) {
					setCurrentIndex(0);
				} 
			}
			value = valueList.get(getCurrentIndex());
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[ListModel] ::Exception in spinUp method :"+e);
		}
	}

	@Override
	public void spinDown() {
		try {
			logger.log(Level.INFO, "[ListModel] ::In spinDown method ");
			if(getCurrentIndex() - 1 >= 0) {
				setCurrentIndex(getCurrentIndex() - 1);
			} else {
				if(isCircular()) {
					setCurrentIndex(getValueList().size() - 1);
				} 
			}
			value = valueList.get(getCurrentIndex());
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[ListModel] ::Exception in spinDown method :"+e);
		}
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

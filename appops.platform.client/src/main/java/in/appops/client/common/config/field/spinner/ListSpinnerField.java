package in.appops.client.common.config.field.spinner;

import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;

import java.util.ArrayList;

/**
 * List Spinner field to set list of value to the spinner
 * @author nitish@ensarm.com
 *
 */
public class ListSpinnerField extends SpinnerField{
	
	private int currentIndex;

	public ListSpinnerField() {
		super();
	}
	
	@Override
	public void configure() {
		super.configure();
		
		spinnerBox.setText(getValue(getDefaultValueIndex()).toString());
		spinnerBox.setReadOnly(true);
		currentIndex = getDefaultValueIndex();
	}

	
	private Object getValue(int index) {
		ArrayList valueList = getValueList();
		return valueList.get(index);
	}
	

	/**** Configuration get() methods ****/
	
	private int getDefaultValueIndex() {
		int index = 0;
		if(getConfigurationValue(SpinnerFieldConstant.SP_VALUEIDX) != null) {
			int val = (Integer)getConfigurationValue(SpinnerFieldConstant.SP_VALUEIDX);
			if(val < getValueList().size()) {
				index = val;
			}
		}
		return index;
	}
	
	private ArrayList getValueList() {
		ArrayList valueList = null;
		if(getConfigurationValue(SpinnerFieldConstant.SP_VALUELIST) != null) {
			valueList = (ArrayList)getConfigurationValue(SpinnerFieldConstant.SP_VALUELIST);
		}
		return valueList;
	}
	
	/*******************************************/
	
	
	@Override
	protected void spinUp() {
		try {
			if(currentIndex + 1 < getValueList().size()) {
				currentIndex = currentIndex + 1;
			} else {
				if(isCircular()) {
					currentIndex = 0;
				} 
			}
			spinnerBox.setText(getValue(currentIndex).toString());
			fireSpinUpEvent();
		} catch (Exception e) {
			
		}
	}
	
	private void fireSpinUpEvent() {
		FieldEvent fieldEvent = new FieldEvent();
		fieldEvent.setEventType(FieldEvent.SPN_SPINUP);
		AppUtils.EVENT_BUS.fireEventFromSource(fieldEvent, this);
	}

	private void fireSpinDownEvent() {
		FieldEvent fieldEvent = new FieldEvent();
		fieldEvent.setEventType(FieldEvent.SPN_SPINDOWN);
		AppUtils.EVENT_BUS.fireEventFromSource(fieldEvent, this);
	}

	@Override
	protected void spinDown() {
		if(currentIndex - 1 >= 0) {
			currentIndex = currentIndex - 1;
		} else {
			if(isCircular()) {
				currentIndex = getValueList().size() - 1;
			} 
		}
		spinnerBox.setText(getValue(currentIndex).toString());
		fireSpinDownEvent();
	}
	
	public void changeValue(int index) {
		String val = (String) getValue(index);
		spinnerBox.setText(val);
		currentIndex = index;
		
	}


}
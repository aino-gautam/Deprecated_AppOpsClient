package in.appops.client.common.fields.spinner;

import java.util.ArrayList;

public class ListSpinnerField extends SpinnerField{
	
	private int currentIndex;

	public ListSpinnerField() {
		super();
	}
	
	@Override
	public void configure() {
		super.configure();
		
		textbox.setText(getValue(getDefaultValueIndex()).toString());
		textbox.setReadOnly(true);
		currentIndex = getDefaultValueIndex();
	}

	
	private Object getValue(int index) {
		ArrayList valueList = getValueList();
		return valueList.get(index);
	}
	

	/**** Configuration get() methods ****/
	
	private int getDefaultValueIndex() {
		int index = 0;
		if(getConfigurationValue(SpinnerConfigurationConstant.DEFAULT_VALIND) != null) {
			int val = (Integer)getConfigurationValue(SpinnerConfigurationConstant.DEFAULT_VALIND);
			if(val < getValueList().size()) {
				index = val;
			}
		}
		return index;
	}
	
	private ArrayList getValueList() {
		ArrayList valueList = null;
		if(getConfigurationValue(SpinnerConfigurationConstant.VALUELIST) != null) {
			valueList = (ArrayList)getConfigurationValue(SpinnerConfigurationConstant.VALUELIST);
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
			textbox.setText(getValue(currentIndex).toString());
		} catch (Exception e) {
			
		}
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
		textbox.setText(getValue(currentIndex).toString());
	}


}

package in.appops.client.common.fields;

import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;

import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.TextBox;

public class NumberField extends TextBox implements Field, KeyUpHandler{

	private Configuration configuration;
	private String fieldValue;
	
	public static final String NUMBERFIELD_VALIDATOR = "[\\d]*";
	public static final String NUMBERFIELD_PRIMARYCSS = "numberFieldPrimaryCss";
	public static final String NUMBERFIELD_DEPENDENTCSS = "numberFieldDependentCss";
	
	public NumberField(){
		// will have a validator provided via configuration
		// which will validate the the input in the textbox is numbers only
		super();
	}
	
	@Override
	public Configuration getConfiguration() {
		return this.configuration;
	}

	@Override
	public void setConfiguration(Configuration conf) {
		this.configuration = conf;
	}

	@Override
	public void create() throws AppOpsException {
		if(getConfiguration() == null)
			throw new AppOpsException("NumberField configuration unavailable");
		
		if(getFieldValue() != null)
			this.setText(getFieldValue());
		
		if(getConfiguration().getPropertyByName(NUMBERFIELD_PRIMARYCSS) != null)
			this.setStylePrimaryName(getConfiguration().getPropertyByName(NUMBERFIELD_PRIMARYCSS).toString());
		if(getConfiguration().getPropertyByName(NUMBERFIELD_DEPENDENTCSS) != null)
			this.addStyleName(getConfiguration().getPropertyByName(NUMBERFIELD_DEPENDENTCSS).toString());
		this.setWidth("40px");
		this.addKeyUpHandler(this);
	}

	@Override
	public void clear() {
		this.setText("");
	}

	@Override
	public void reset() {
		this.setText(getFieldValue());
	}

	@Override
	public String getFieldValue() {
		return this.fieldValue;
	}

	@Override
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	@Override
	public void onFieldEvent(FieldEvent event) {
		
	}

	private void isValid(String text) {
		FieldEvent fieldEvent = new FieldEvent();
		fieldEvent.setEventData(this.getText());
		if(text.matches(NUMBERFIELD_VALIDATOR)) {
			fieldEvent.setEventType(FieldEvent.EDITSUCCESS);
		} else {
			fieldEvent.setEventType(FieldEvent.EDITFAIL);
		}
		AppUtils.EVENT_BUS.fireEvent(fieldEvent);
	}

	@Override
	public void onKeyUp(KeyUpEvent event) {
		String text = this.getText();
		isValid(text);
	}

	@Override
	public void configure() {
		// TODO Auto-generated method stub
		
	}
}
